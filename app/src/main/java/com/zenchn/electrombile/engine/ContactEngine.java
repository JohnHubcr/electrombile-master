package com.zenchn.electrombile.engine;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.zenchn.electrombile.engine.callback.ContactCallback;
import com.zenchn.electrombile.adapter.bean.ContactInfo;
import com.zenchn.electrombile.kit.LogKit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 作    者：wangr on 2017/2/27 16:22
 * 描    述：查询本地联系人的引擎
 * 修订记录：
 */

public class ContactEngine {

    private ContactEngine() {
    }

    private static class SingletonInstance {
        private static final ContactEngine INSTANCE = new ContactEngine();
    }

    public static ContactEngine getInstance() {
        return SingletonInstance.INSTANCE;
    }

    public void queryContact(Context context, final ContactCallback callback) {

        final ContentResolver contentResolver = context.getContentResolver();

        Observable.create(new Observable.OnSubscribe<Cursor>() {
            @Override
            public void call(Subscriber<? super Cursor> subscriber) {
                Uri rawUri = Uri.parse("content://com.android.contacts/raw_contacts");
                Cursor rawCursor = contentResolver.query(rawUri, new String[]{"_id"}, null, null, null);
                if (rawCursor != null) {
                    LogKit.logEngine("Hi , Query Start！");
                    while (rawCursor.moveToNext()) {
                        String rawContactId = rawCursor.getString(0);
                        Uri dataUri = Uri.parse("content://com.android.contacts/data");
                        subscriber.onNext(contentResolver.query(dataUri, new String[]{"mimetype", "data1"}, "raw_contact_id = ?", new String[]{rawContactId}, null));
                    }
                    rawCursor.close();
                }
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .map(new Func1<Cursor, ContactInfo>() {
                    @Override
                    public ContactInfo call(Cursor cursor) {
                        ContactInfo contactInfo = null;
                        if (cursor != null) {
                            contactInfo = new ContactInfo();
                            while (cursor.moveToNext()) {
                                String mimeType = cursor.getString(0);
                                if ("vnd.android.cursor.item/phone_v2".equals(mimeType)) {
                                    String phone = cursor.getString(1);
                                    contactInfo.setPhone(phone);
                                } else if ("vnd.android.cursor.item/name".equals(mimeType)) {
                                    String name = cursor.getString(1);
                                    contactInfo.setName(name);
                                }
                            }
                            cursor.close();
                        }
                        return contactInfo;
                    }
                })
                .subscribe(new Action1<ContactInfo>() {
                    @Override
                    public void call(ContactInfo contactInfo) {
                        if (contactInfo != null) {
                            callback.addContactInfo(contactInfo);
                            LogKit.logEngine("success:" + contactInfo.toString());
                        } else {
                            LogKit.logEngine("success:" + "Current contactInfo is a null object !");
                        }
                    }

                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        callback.onQueryCompleted();
                        LogKit.logEngine("Sorry , Query Error! \n" + "details:" + throwable.toString());
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        callback.onQueryCompleted();
                        LogKit.logEngine("OK , Query Completed!");
                    }
                });
    }
}
