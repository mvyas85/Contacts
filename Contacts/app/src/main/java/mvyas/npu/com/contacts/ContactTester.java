package mvyas.npu.com.contacts;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

public class ContactTester extends Activity {
	private static final int AND_CON_ACTIVITY = 1;
	private static final int PICK_CON_ACTIVITY = 2;
	
	Button and_conb, pick_conb;
	TextView result_con;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
    	and_conb = (Button) findViewById(R.id.and_conb);
    	result_con = (TextView) findViewById(R.id.result_text);
    	pick_conb = (Button) findViewById (R.id.pick_conb);
        
        and_conb.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ContactTester.this,AndContacts.class);
		        startActivityForResult(intent, AND_CON_ACTIVITY);
				
			}
		});
        pick_conb.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_PICK,Uri.parse("content://contacts/"));
				startActivityForResult(intent, PICK_CON_ACTIVITY);  
				
			}
		});
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
    	super.onActivityResult(requestCode, resultCode, data);
    	switch (requestCode)
        {
	        case AND_CON_ACTIVITY:
	        {
	        	if(resultCode == Activity.RESULT_OK)
	        	{
	        		String new_con = data.getStringExtra("Name");
	        		result_con.setText("Contact Added : "+new_con);
	        		
	        	}
	        	break;
	        }
	        case PICK_CON_ACTIVITY : 
	        {
	          if (resultCode == Activity.RESULT_OK) 
	          {
		            Uri contactData = data.getData();
		            Cursor c = managedQuery(contactData, null, null, null, null);
		            c.moveToFirst();
		            String new_con = c.getString(c.getColumnIndexOrThrow(People.NAME));
		            result_con.setText("You have selected : "+new_con);
	          }
	          break;
	        }
        }
    	
    }
    
    @Override //Option menu/Action menu
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return true;
    }
}
