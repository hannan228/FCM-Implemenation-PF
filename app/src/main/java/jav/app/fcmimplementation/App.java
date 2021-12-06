package jav.app.fcmimplementation;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

public class App extends Application {
    public static final String FCM_CHANNEL_ID = "FCM_CHANNEL_ID";
    public static final String Legacy_SERVER_KEY = "AAAACOS9gEE:APA91bG3fHyPdUHztgEszRaDUozQPHTkBB694fOOxFN7wXsyh8BF-cnf8ejpWlqOSmwXOrcCK2t_rmTbGv54XV5xpGC171d8xVN8ebB9FydOLMwzuaoK8E9xw2WDm9DAnbFnQRsE6IQE";

    private static final String TAG = "App";
    
    @Override
    public void onCreate() {
        super.onCreate();

//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            NotificationChannel fcmChannel = new NotificationChannel(
//                    FCM_CHANNEL_ID, "FCM_Channel", NotificationManager.IMPORTANCE_HIGH);
//            Log.d(TAG, "onCreate: here messAGE");
//            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//
//            manager.createNotificationChannel(fcmChannel);
//        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "new message";
            String description = "this is a message";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(FCM_CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }
}
