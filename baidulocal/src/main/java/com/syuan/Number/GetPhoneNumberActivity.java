package com.syuan.Number;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ListView;

import com.syuan.adapter.NumberListAdapter;
import com.syuan.baidulocal.R;
import com.syuan.enity.Person;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.phoneNumber;

public class GetPhoneNumberActivity extends AppCompatActivity {
    private List<Person> list;
    private ListView lv;
    private NumberListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_phone_number);
        init();
    }

    private void init() {
        lv= (ListView) findViewById(R.id.number_lv);

        list=new ArrayList<>();
        adapter=new NumberListAdapter(this);
        lv.setAdapter(adapter);
        getPhoneContacts();
//        getSIMContacts();
        adapter.add(list);
        adapter.notifyDataSetChanged();
    }

    private void getPhoneContacts() {
        ContentResolver resolver = this.getContentResolver();

        // 获取手机联系人
//        Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                new String[] { ContactsContract.CommonDataKinds.Phone.CONTACT_ID, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
//                        ContactsContract.CommonDataKinds.Phone.NUMBER },
//                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + "=?" + " AND " + ContactsContract.CommonDataKinds.Phone.TYPE + "='"
//                        + ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE + "'", new String[] { "name" }, null);
        Cursor phoneCursor=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {
                String number = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                // 当手机号码为空的或者为空字段 跳过当前循环
                if (TextUtils.isEmpty(number))
                    continue;
                // 得到联系人名称
                String username = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                Person info=new Person(username,number);
                list.add(info);
            }
            phoneCursor.close();
        }
    }

    private void getSIMContacts() {
        ContentResolver resolver = this.getContentResolver();
        // 获取Sims卡联系人
        Uri uri = Uri.parse("content://icc/adn");
        Cursor phoneCursor = resolver.query(uri,
                new String[] { ContactsContract.CommonDataKinds.Phone.CONTACT_ID, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.NUMBER },
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + "=?" + " AND " + ContactsContract.CommonDataKinds.Phone.TYPE + "='"
                        + ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE + "'", new String[] { "name" }, null);
        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {
                String number = phoneCursor.getString(2);
                // 当手机号码为空的或者为空字段 跳过当前循环
                if (TextUtils.isEmpty(number))
                    continue;
                // 得到联系人名称
                String username = phoneCursor.getString(1);
                Person info=new Person(username,number);
                list.add(info);
            }
            phoneCursor.close();
        }
    }
}
