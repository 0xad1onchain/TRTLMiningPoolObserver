package ml.fifty9.poolmonitor.service;

import android.content.Context;
import android.content.SharedPreferences;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import ml.fifty9.poolmonitor.model.statsaddress.Charts;
import ml.fifty9.poolmonitor.model.statsaddress.Pool;
import ml.fifty9.poolmonitor.model.statsaddress.Stats;
import ml.fifty9.poolmonitor.util.RetrofitAPI;
import ml.fifty9.poolmonitor.util.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by HemantJ on 11/02/18.
 */

public class ReminderTasks {

    public static final String ACTION_TURTLE = "turtle-coin";
    private static RetrofitAPI api;
    private static SharedPreferences sharedPreferences, walletPref;
    private static Stats stats;

    public static void executeTasks(Context context, String action){
        if(action.equals(ACTION_TURTLE)){
            getDataFromAPI(context);
        }
    }

    private static void getDataFromAPI(Context context) {
        String walletText,pool, path;
        sharedPreferences = context.getSharedPreferences("URL_PREFS", 0);
        walletPref = context.getSharedPreferences("WALLET_PREFS",0);

        walletText = walletPref.getString("wallet","");
        pool = sharedPreferences.getString("url","");
        api = RetrofitService.getAPI(pool);


        api.queryDashboardStats(walletText)
                .enqueue(new Callback<Pool>() {
                    @Override
                    public void onResponse(Call<Pool> call, Response<Pool> response) {
                        stats = response.body().getStats();
                        try {
                            String path = "TurtleCoin";
                            try {
                                URI uri = new URI(pool);
                                path = uri.getHost();
                            }
                            catch (Exception e) {e.printStackTrace();}

                            String paidString = stats.getPaid();
                            String balanceString = stats.getBalance();
                            String lastShareString = stats.getLastShare();
                            balanceString = getBalanceString(balanceString);
                            paidString = getPaidString(paidString);
                            String finalString = paidString + "\nLast Share Submitted: " + getDate(lastShareString) + "\nPool: " + path;
                            NotificationUtils.remindUserAboutTRTL(context, balanceString, finalString,stats.getHashes());
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<Pool> call, Throwable t) {

                    }

                    public String convertCoin(String coin) {
                        int coins = Integer.parseInt(coin);
                        coins = coins/100;
                        return String.valueOf(coins) + " TRTL";
                    }

                    public String getDate(String timeStampString) {

                        Calendar cal = Calendar.getInstance();
                        java.util.Date currentLocalTime = cal.getTime();
                        TimeZone tz = cal.getTimeZone();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        sdf.setTimeZone(tz);
                        Long timeLong = Long.parseLong(timeStampString);
                        java.util.Date time=new java.util.Date((long)timeLong*1000);
                        String localTime = sdf.format(time);

                        Date startDate = time;
                        Date endDate   = cal.getTime();

                        long duration  = endDate.getTime() - startDate.getTime();
                        long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
                        long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);

                        if (diffInMinutes > 60) {
                            localTime = diffInHours + " Hours ago";
                        }
                        else {
                            localTime = diffInMinutes + " Minutes ago";
                        }

                        return localTime;
                    }

                    public String getBalanceString(String balance) {
                        String balanceString;
                        if (null == balance) {
                            balanceString = "Balance: 0 TRTL";
                        }
                        else {
                            balanceString = "Balance: " + convertCoin(balance);
                        }

                        return balanceString;
                    }

                    public String getPaidString(String paid) {
                        String balanceString;
                        if (null == paid) {
                            balanceString = "Paid: 0 TRTL";
                        }
                        else {
                            balanceString = "Paid: " + convertCoin(paid);
                        }

                        return balanceString;
                    }
                });
    }

}
