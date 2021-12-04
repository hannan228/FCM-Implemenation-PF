package jav.app.fcmimplementation;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static android.content.ContentValues.TAG;
import static jav.app.fcmimplementation.App.FCM_CHANNEL_ID;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String token) {
        Log.d("Token",""+token);
//        Add your token in your sharepreferences.
        getSharedPreferences("_", MODE_PRIVATE).edit().putString("fcm_token", token).apply();
        super.onNewToken(token);
    }

    public static String getToken(Context context) {
        return context.getSharedPreferences("_", MODE_PRIVATE).getString("fcm_token", "empty");
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        Log.d("message", "onMessageReceived: called");

        Log.d("message", "onMessageReceived: Message received from: " + remoteMessage.getFrom());


        if (remoteMessage.getFrom().length()>0) {
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "my_channel_01")
                    .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                    .setContentTitle(title)
                    .setContentText(""+body)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            // Add as notification
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0, builder.build());

        }

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "onMessageReceived: Data: " + remoteMessage.getData().toString());
//            Intent intent = new Intent(this, MainActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
//
//            for (String key : remoteMessage.getData().keySet()) {
//                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "my_channel_01")
//                        .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
//                        .setContentTitle("title"+key)
//                        .setContentText(""+remoteMessage.getData().toString())
//                        .setContentIntent(pendingIntent)
//                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//                // Add as notification
//                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                manager.notify(0, builder.build());
//
//                Log.d(TAG, "onMessageReceived: Key: " + key + " Data: " + remoteMessage.getData().toString());
//            }
            Log.d(TAG, "onMessageReceived: Data"+remoteMessage.getData().toString());
        }

        super.onMessageReceived(remoteMessage);
    }
}
