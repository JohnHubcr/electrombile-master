package com.zenchn.electrombile.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.zenchn.electrombile.adapter.bean.ContactInfo;

import java.util.ArrayList;
import java.util.List;

public class ContentProviderUtil {
    /**
     * 获取手机中所有的联系人信息
     *
     * @param context
     * @return
     */
    public static List<ContactInfo> getAllContacts(Context context) throws Exception {
        List<ContactInfo> contactInfos = new ArrayList<>();
        ContentResolver contentResolver = context.getContentResolver();
        Uri rawUri = Uri.parse("content://com.android.contacts/raw_contacts");
        Cursor rawCursor = contentResolver.query(rawUri, new String[]{"_id"}, null, null, null);

        while (rawCursor.moveToNext()) {
            ContactInfo contactInfo = new ContactInfo();
            String rawContactId = rawCursor.getString(0);
            Uri dataUri = Uri.parse("content://com.android.contacts/data");
            Cursor dataCursor = contentResolver.query(dataUri, new String[]{"mimetype", "data1"},
                    "raw_contact_id = ?", new String[]{rawContactId}, null);
            while (dataCursor.moveToNext()) {
                String mimeType = dataCursor.getString(0);
                if ("vnd.android.cursor.item/phone_v2".equals(mimeType)) {
                    String phoneNum = dataCursor.getString(1);
                    contactInfo.setPhone(phoneNum);
                } else if ("vnd.android.cursor.item/name".equals(mimeType)) {
                    String name = dataCursor.getString(1);
                    contactInfo.setName(name);
                }
            }
            dataCursor.close();
            contactInfos.add(contactInfo);
        }

        rawCursor.close();
        return contactInfos;
    }

}