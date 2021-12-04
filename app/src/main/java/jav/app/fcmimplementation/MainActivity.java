package jav.app.fcmimplementation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessagingService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannel();

    }
    private void sendFCMPush() {
        final String Legacy_SERVER_KEY = "AAAACOS9gEE:APA91bG3fHyPdUHztgEszRaDUozQPHTkBB694fOOxFN7wXsyh8BF-cnf8ejpWlqOSmwXOrcCK2t_rmTbGv54XV5xpGC171d8xVN8ebB9FydOLMwzuaoK8E9xw2WDm9DAnbFnQRsE6IQE";
        String msg = "you are selected for rescue service. Please go to your map to view your destination";
        String title = "Rescue request";
        String token = "fz4ZfeHDRHGMtVGzKYwr7A:APA91bGOOmrcM6saksXSv34ybsXz7OIW5tXYSaoP9AS0xeSAgsiU6gw3ThfPn1hTOzsAwUenbGX4tk6DFZFcCABj-WYJu0MrRgoCLyzgBjviXno9CNEEEaEl1x3I3azd92GiyEKBlnKm";
        String token2 = "fpN6XouXR_WCzcTBzRclpy:APA91bEgS-IriBtCWhNQIld5Of6qOqArGYWQfXRaC_pbBcBqg3EKDtITMGOHtkCbHBKPRc3s41fThQQVtCYmNwaXGEBTLPcFWmIhziOWzfzv7hZRNI1OeNfry_EfJgX3Y7PQ_iqxbEaP";

        JSONObject obj = null;
        JSONObject objData = null;
        JSONObject dataobjData = null;
        try {
            obj = new JSONObject();
            objData = new JSONObject();

            objData.put("body", msg);
            objData.put("title", title);
//            objData.put("sound",);
            objData.put("icon", R.drawable.ic_baseline_emergency_24); //   icon_name image must be there in drawable
            objData.put("tag", token);
            objData.put("priority", "high");

            dataobjData = new JSONObject();
            dataobjData.put("text", msg);
            dataobjData.put("title", title);

            obj.put("to", token2);
            //obj.put("priority", "high");

            obj.put("notification", objData);
            obj.put("data", dataobjData);
            Log.e("!_@rj@_@@_PASS:>", obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsObjRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, "https://fcm.googleapis.com/fcm/send", obj,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("!_@@_SUCESS", response + "");
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("!_@@_Errors--", error + "");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "key=" + Legacy_SERVER_KEY);
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        int socketTimeout = 1000 * 60;// 60 seconds
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsObjRequest.setRetryPolicy(policy);
        requestQueue.add(jsObjRequest);
    }

    public void sendNotification(View view) {
        sendFCMPush();
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "new message";
            String description = "this is a message";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("my_channel_01", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}

