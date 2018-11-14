package com.community.jboss.leadmanagement.data.providers;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.community.jboss.leadmanagement.data.daos.ContactDao;
import com.community.jboss.leadmanagement.data.entities.Contact;
import com.community.jboss.leadmanagement.utils.DbUtil;

import java.util.List;

public class ContactsWidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private List<Contact> contactList;
    private LiveData<List<Contact>> contacts;
    private Observer<List<Contact>> contactsObserver;

    public ContactsWidgetDataProvider(Context context){
        this.context = context;
        ContactDao contactDao = DbUtil.contactDao(context);
        contacts = contactDao.getContacts();
        contacts.observeForever(contacts -> contactList = this.contacts.getValue());
        //playWithTheData();
    }

    @Override
    public void onCreate() {
        //playWithTheData();
    }

    @Override
    public void onDataSetChanged() {
        //playWithTheData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(contactList != null)
        return contactList.size();
        else return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews view = new RemoteViews(context.getPackageName(),android.R.layout.simple_list_item_1);
        view.setTextViewText(android.R.id.text1, contactList.get(position).getName());
        return view;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    //private void playWithTheData() {
    //    contactNames.clear();
    //    if (contactsObserver!=null){
    //        try {
    //            contacts.removeObserver(contactsObserver);
    //            contactsObserver = null;
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //    }
    //    contactsObserver = onContacts -> {
    //        if (onContacts != null) {
    //            contactNames.clear();
    //            for (int i = 0; i < onContacts.size(); i++) {
    //                contactNames.add(onContacts.get(i).getName());
    //            }
    //        } else {
    //            contactNames.add("How about adding some contacts first?");
    //        }
    //    };
    //    contacts.observeForever(contactsObserver);
    //}
}