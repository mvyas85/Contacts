
package com.example.swapusingmenu;

import java.util.ArrayList;

import android.app.Activity;
import android.app.TabActivity;
import android.content.ContentProviderOperation;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.*;
import android.provider.ContactsContract.*;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;

public class AndContacts extends TabActivity {

	Button saveb,cancelb;
	EditText txtname,txtemail,txtphone,txtpostaladd;
	TabHost mTabHost = null;

	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.andcon);
        
        mTabHost = getTabHost(); 
        
        mTabHost.addTab(mTabHost.newTabSpec("tab_test1").setIndicator("Contacts", getResources().getDrawable(R.drawable.contact)).setContent(R.id.contactsLayout)); 
        mTabHost.addTab(mTabHost.newTabSpec("tab_test2").setIndicator("Music", getResources().getDrawable(R.drawable.music)).setContent(R.id.musicLayout)); 
        mTabHost.addTab(mTabHost.newTabSpec("tab_test3").setIndicator("Video", getResources().getDrawable(R.drawable.video)).setContent(R.id.videoLayout)); 
         
        mTabHost.setCurrentTab(0); 
    	saveb = (Button) findViewById(R.id.buttonsave);
    	cancelb = (Button) findViewById(R.id.buttoncancel);
    	txtname = (EditText) findViewById(R.id.txtname);  	
    	txtemail = (EditText) findViewById(R.id.txtemail);
    	txtphone = (EditText) findViewById(R.id.txtphone);
    	txtpostaladd = (EditText) findViewById(R.id.txtpostaladdress);
     
        saveb.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				Intent result = new Intent();
				result.putExtra("Name",txtname.getText().toString());
				addcontact();
				setResult(RESULT_OK, result);
				
				finish();
			}
		});
        
        cancelb.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				
				setResult(RESULT_CANCELED, null);
				finish();
			}
		});
    }


	protected void addcontact() {
		ArrayList ops = new 
                ArrayList();
   int rawContactInsertIndex = ops.size();

   ops.add(ContentProviderOperation.newInsert(RawContacts.CONTENT_URI)
        .withValue(RawContacts.ACCOUNT_TYPE, null)
        .withValue(RawContacts.ACCOUNT_NAME,null )
      .build());

   //phone field
   ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
           .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 
                       rawContactInsertIndex)
           .withValue(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
           .withValue(Phone.NUMBER, txtphone.getText().toString())
           .build());

   //name field
   ops.add(ContentProviderOperation.newInsert(Data.CONTENT_URI)
           .withValueBackReference(Data.RAW_CONTACT_ID, rawContactInsertIndex)
           .withValue(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE)
           .withValue(StructuredName.DISPLAY_NAME, 
                       txtname.getText().toString())
           .build());
   
   //email field
   ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI) 
           .withValueBackReference(Data.RAW_CONTACT_ID, 0)

   .withValue(ContactsContract.Data.MIMETYPE,ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
   .withValue(ContactsContract.CommonDataKinds.Email.DATA, txtemail.getText().toString())
   .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_HOME)
   .build());
   
   
   try {
       //update data to the database, operations are run in batchmode
       getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
   } catch (RemoteException e) {
       e.printStackTrace();
   } catch (OperationApplicationException e) {
       e.printStackTrace();
   }
		
	}
	
}
