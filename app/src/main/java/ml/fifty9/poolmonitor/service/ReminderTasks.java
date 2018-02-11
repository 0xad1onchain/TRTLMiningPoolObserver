package ml.fifty9.poolmonitor.service;

import android.content.Context;
import android.content.SharedPreferences;

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
        String walletText,pool;
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
                        NotificationUtils.remindUserAboutTRTL(context, String.valueOf(Integer.valueOf(stats.getPaid()) / 100) + " TRTL",stats.getHashes());
                    }

                    @Override
                    public void onFailure(Call<Pool> call, Throwable t) {

                    }
                });
    }
}
