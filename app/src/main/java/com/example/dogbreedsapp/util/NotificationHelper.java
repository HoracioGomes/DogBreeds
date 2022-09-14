package com.example.dogbreedsapp.util;

import android.content.Context;

public class NotificationHelper {
    private static NotificationHelper instance;
    private Context context;

    private NotificationHelper(Context context) {
        this.context = context;
    }

    public static NotificationHelper getInstance(Context context) {
        if (instance == null) {
            instance = new NotificationHelper(context);
        }
        return instance;
    }

}
