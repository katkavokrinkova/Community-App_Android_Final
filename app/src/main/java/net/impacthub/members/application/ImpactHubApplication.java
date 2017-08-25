package net.impacthub.members.application;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.salesforce.androidsdk.app.SalesforceSDKManager;
import com.salesforce.androidsdk.push.PushNotificationInterface;

import net.impacthub.members.R;
import net.impacthub.members.application.salesforce.SalesforceApplication;
import net.impacthub.members.ui.controllers.MainTabsActivity;
import net.impacthub.members.ui.splash.SplashActivity;


public class ImpactHubApplication extends SalesforceApplication<SplashActivity> {

    public ImpactHubApplication() {
        super(SplashActivity.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        SalesforceSDKManager.getInstance().setPushNotificationReceiver(new PushNotificationInterface() {
            @Override
            public void onPushMessageReceived(Bundle message) {
                Log.d("PUSH--MESSAGE", message.toString());
                Context context = getApplicationContext();

                NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                builder.setContentTitle("Impact Hub");
                builder.setContentText(message.getString("test"));
                builder.setSmallIcon(com.salesforce.androidsdk.R.drawable.sf__icon);
//                builder.setSound(getDefaultUri(TYPE_NOTIFICATION));

                Intent notificationIntent = new Intent(context, MainTabsActivity.class);
//                notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                notificationIntent.putExtra("message", message);
                PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
                builder.setContentIntent(contentIntent);

                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(R.id.unique_id, builder.build());

            }
        });
    }
}
