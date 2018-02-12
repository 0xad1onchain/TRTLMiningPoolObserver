package ml.fifty9.poolmonitor.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import ml.fifty9.poolmonitor.R;

/**
 * Created by HemantJ on 11/02/18.
 */

public class NotificationUtils {
    private static final int TRTL_MINING_INTENT_ID = 123;
    private static final String TRTL_NOTIFICATION_CHANNEL = "trtl";
    private static final int TRTL_MINING_REMINDER_ID = 1234;

    public static void remindUserAboutTRTL(Context context, String balanceTitle, String content, String hashRate){
        NotificationManager notificationManager = (NotificationManager)
                                                    context.getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= 26){
            NotificationChannel mChannel = new NotificationChannel(
                    TRTL_NOTIFICATION_CHANNEL,
                    "Primary",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, TRTL_NOTIFICATION_CHANNEL)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_stat_name)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(balanceTitle)
                .setContentText(content)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(content))
                .setOngoing(true)
                .setAutoCancel(false);

        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        notificationManager.notify(TRTL_MINING_REMINDER_ID, builder.build());
    }

    private static Bitmap largeIcon(Context context) {
        Resources res = context.getResources();
        Bitmap largeIcon = BitmapFactory.decodeResource(res, R.drawable.turtlecoin_symbol_color);
        return largeIcon;
    }
}
