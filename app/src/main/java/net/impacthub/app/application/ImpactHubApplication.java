package net.impacthub.app.application;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.gson.Gson;
import com.salesforce.androidsdk.app.SalesforceSDKManager;
import com.salesforce.androidsdk.push.PushNotificationInterface;

import net.impacthub.app.R;
import net.impacthub.app.application.salesforce.SalesforceApplication;
import net.impacthub.app.model.vo.notifications.NotificationPayloadVO;
import net.impacthub.app.model.vo.notifications.ReceivedNotification;
import net.impacthub.app.ui.common.PushNotificationObservable;
import net.impacthub.app.ui.controllers.MainTabsActivity;
import net.impacthub.app.ui.delegate.ShortcutBadgerHelper;
import net.impacthub.app.ui.splash.SplashActivity;


public class ImpactHubApplication extends SalesforceApplication<SplashActivity> {

    public ImpactHubApplication() {
        super(SplashActivity.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        SalesforceSDKManager.getInstance().setPushNotificationReceiver(new PushNotificationInterface() {
            @Override
            public void onPushMessageReceived(final Bundle message) {
                Log.d("PUSH--MESSAGE", message.toString());
                final Context context = getApplicationContext();

                NotificationPayloadVO payloadVO = new Gson().fromJson(message.getString("payload"), NotificationPayloadVO.class);
                ReceivedNotification receivedNotification = new ReceivedNotification(payloadVO);
                receivedNotification.setNotificationTitle(message.getString("contentTitle"));
                receivedNotification.setNotificationMessage(message.getString("contentText"));

                PushNotificationObservable.PushNotificationObserver observer = PushNotificationObservable.getInstance().getNotificationObserver();
                if (observer != null) {
                    if (!observer.onConsumePushNotification(receivedNotification)) {
                        notifyOnThePhone(context, receivedNotification);
                    }
                } else {
                    notifyOnThePhone(context, receivedNotification);
                }
            }
        });
    }

    private void notifyOnThePhone(Context context, ReceivedNotification receivedNotification) {
        ShortcutBadgerHelper.increaseBadgeCount(context);
        createNotification(context, receivedNotification);
    }

    private void createNotification(Context context, ReceivedNotification notification) {
        String notificationMessage = notification.getNotificationMessage();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(notification.getNotificationTitle());
        builder.setContentText(notificationMessage);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        //builder.setSound(getDefaultUri(TYPE_NOTIFICATION));

        Intent notificationIntent = new Intent(context, MainTabsActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        notificationIntent.putExtra(MainTabsActivity.EXTRA_PUSH_NOTIFICATION_MESSAGE, notificationMessage);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(contentIntent);

        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setAutoCancel(true);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(R.id.unique_id, builder.build());
    }
}
