package ml.fifty9.poolmonitor;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jaredrummler.android.widget.AnimatedSvgView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import ml.fifty9.poolmonitor.model.pool.Config;
import ml.fifty9.poolmonitor.model.pool.Network;
import ml.fifty9.poolmonitor.model.statsaddress.Charts;
import ml.fifty9.poolmonitor.model.statsaddress.Pool;
import ml.fifty9.poolmonitor.model.statsaddress.Stats;
import ml.fifty9.poolmonitor.model.pool.StatExample;
import ml.fifty9.poolmonitor.service.ReminderTasks;
import ml.fifty9.poolmonitor.service.ReminderUtility;
import ml.fifty9.poolmonitor.service.TRTLService;
import ml.fifty9.poolmonitor.util.RetrofitAPI;
import ml.fifty9.poolmonitor.util.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParentActivity extends AppCompatActivity{
    private SectionPagerAdapter mSectionPagerAdapter;
    private ViewPager mViewPager;
    private Toolbar toolbar;
    private AnimatedSvgView svgView;
    private SharedPreferences poolPref, walletPref, notifPref;
    private String pool,walletText;
    private RetrofitAPI retrofitAPI;
    private View view;
    private final int TRTL_MINING_REMINDER_ID = 1234;
    private ml.fifty9.poolmonitor.model.statsaddress.Charts chartObj;
    private ml.fifty9.poolmonitor.model.statsaddress.Stats statObj;
    private ml.fifty9.poolmonitor.model.statsaddress.Pool poolObj;

    private ml.fifty9.poolmonitor.model.pool.Charts chartPOJO;
    private ml.fifty9.poolmonitor.model.pool.Config configPOJO;
    private ml.fifty9.poolmonitor.model.pool.Network networkPOJO;
    private ml.fifty9.poolmonitor.model.pool.Pool poolPOJO;

    private boolean addressCall = false;
    private boolean statsCall = false;
    private boolean notificationCallOK = false;
    static final int SETTINGS = 13;
    private NotificationManager manager;
    private Snackbar connectionErrorSnackbar, noWalletSnackbar;
    private Intent notifIntent;

    SharedPreferences.OnSharedPreferenceChangeListener poolChangeListener, notifListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notifPref = this.getSharedPreferences("NOTIFS", 0);
        notifIntent = new Intent(ParentActivity.this, TRTLService.class);
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        setContentView(R.layout.activity_parent);
        mSectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        svgView = findViewById(R.id.animated_svg_view);
        view = findViewById(R.id.main_content);

        connectionErrorSnackbar = Snackbar.make(view, "Error in Connection",Snackbar.LENGTH_INDEFINITE);
        noWalletSnackbar = Snackbar.make(view, "Wallet not found",Snackbar.LENGTH_INDEFINITE);
        svgView.start();

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pool Monitor");



        poolPref = this.getSharedPreferences("URL_PREFS", 0);
        walletPref = this.getSharedPreferences("WALLET_PREFS",0);

        updateUserInfo();

        if(walletText.isEmpty() || pool.isEmpty()){
            Intent walletIntent = new Intent(this, WalletActivity.class);
            startActivityForResult(walletIntent, 1);

        }
        else {
            ReminderUtility.scheduleReminder(this);
            retrofitAPI = RetrofitService.getAPI(pool);
            callAPI();
        }


        notifListener = (prefs, key) -> {
            if (key.equals("ison")) {
                boolean isOn = prefs.getBoolean("ison", false);

                if (isOn) {
                    if (notificationCallOK) {
                        notifIntent.setAction(ReminderTasks.ACTION_TURTLE);
                        startService(notifIntent);
                    }
                }
                else {
                    stopService(notifIntent);
                    manager.cancelAll();
                }
            }
//                setUpSharedPrefs(prefs);
        };

        poolChangeListener = (prefs, key) -> {

            Boolean firstTime = prefs.getBoolean("first", true);
            if (!firstTime) {
                if (key.equals("url")) {
                    String pool = prefs.getString("url", "null");
                    updateUserInfo();
                    retrofitAPI = RetrofitService.getAPI(pool);
                    callAPI();
                }
            }
            else {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("first", false);
                editor.apply();
            }
        };

        notifPref.registerOnSharedPreferenceChangeListener(notifListener);
        poolPref.registerOnSharedPreferenceChangeListener(poolChangeListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        notifPref.registerOnSharedPreferenceChangeListener(notifListener);
        poolPref.registerOnSharedPreferenceChangeListener(poolChangeListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        notifPref.registerOnSharedPreferenceChangeListener(notifListener);
        poolPref.registerOnSharedPreferenceChangeListener(poolChangeListener);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_CANCELED) {
                updateUserInfo();
                retrofitAPI = RetrofitService.getAPI(pool);
                callAPI();
            }
        }
    }

    public void updateUserInfo() {
        walletText = walletPref.getString("wallet","");
        pool = poolPref.getString("url","");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_parent,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public ml.fifty9.poolmonitor.model.statsaddress.Charts getChart() {
        return this.chartObj;
    }

    public Stats getStats() {
        return this.statObj;
    }

    public ml.fifty9.poolmonitor.model.statsaddress.Pool getPool(){
        return this.poolObj;
    }

    public Network getNetwork(){
        return this.networkPOJO;
    }

    public Config getConfig(){
        return this.configPOJO;
    }

    public ml.fifty9.poolmonitor.model.pool.Charts getChartPOJO() { return this.chartPOJO; }

    public ml.fifty9.poolmonitor.model.pool.Pool getPoolPOJO() { return this.poolPOJO; }


    public void setUpSharedPrefs(SharedPreferences preference){
        if (preference.getBoolean("ison", false)) {
            if (notificationCallOK) {
                notifIntent.setAction(ReminderTasks.ACTION_TURTLE);
                startService(notifIntent);
            }
        } else {
            stopService(notifIntent);
            manager.cancelAll();
        }

    }

    private class SectionPagerAdapter extends FragmentPagerAdapter {

        private SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new DashboardFragment();
                case 1:
                    return new PoolFragment();
                case 2:
                    return new PayoutFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Dashboard";
                case 1:
                    return "Pool";
                case 2:
                    return "Payouts";
            }
            return null;
        }

    }

    protected void callAPI() {
        retrofitAPI.queryDashboardStats(walletText)
                .enqueue(new Callback<Pool>() {
                    @Override
                    public void onResponse(Call<Pool> call, Response<Pool> response) {

                        try {
                            setAPIObjectsWallet(response);
                        }
                        catch (Exception e) {

                        }
                        addressCall = true;
                        if (statsCall == true) {

                            if (null == statObj) {
                                noWalletSnackbar.show();
                                notificationCallOK = false;
                            }
                            else {
                                noWalletSnackbar.dismiss();
                                connectionErrorSnackbar.dismiss();
                                inflateTabs();
                                statsCall = false;
                                addressCall = false;
                                notificationCallOK = true;
                                setUpSharedPrefs(notifPref);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Pool> call, Throwable t) {
                        svgView.setVisibility(View.GONE);
                        connectionErrorSnackbar.show();


                    }
                });

        retrofitAPI.queryStats()
                .enqueue(new Callback<StatExample>() {
                    @Override
                    public void onResponse(Call<StatExample> call, Response<StatExample> response) {


                        try {
                            setAPIObjectsStats(response);
                        }
                        catch (Exception e) {

                        }

                        statsCall = true;
                        if (addressCall == true) {
                            if (null == statObj) {
                                noWalletSnackbar.show();
                                notificationCallOK = false;

                            }
                            else {
                                noWalletSnackbar.dismiss();
                                connectionErrorSnackbar.dismiss();
                                inflateTabs();
                                statsCall = false;
                                addressCall = false;
                                notificationCallOK = true;
                                setUpSharedPrefs(notifPref);
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<StatExample> call, Throwable t) {
                        svgView.setVisibility(View.GONE);
                        connectionErrorSnackbar.show();
                        Log.d("Error",t.getMessage());
                    }
                });
    }

    private void setAPIObjectsWallet(Response<Pool> response) {
        chartObj = response.body().getCharts();
        statObj = response.body().getStats();
        poolObj = response.body().getPool();
    }

    private void setAPIObjectsStats(Response<StatExample> response) {
        chartPOJO = response.body().getCharts();
        configPOJO = response.body().getConfig();
        networkPOJO = response.body().getNetwork();
        poolPOJO = response.body().getPool();
    }

    private void inflateTabs() {
        svgView.setVisibility(View.GONE);
        mViewPager = findViewById(R.id.container);
        mSectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setVisibility(View.VISIBLE);
        tabLayout.setupWithViewPager(mViewPager);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.change_address:
                startActivity(new Intent(ParentActivity.this, WalletActivity.class));
                return true;
            case R.id.settings:
                startActivity(new Intent(ParentActivity.this, SettingsActivity.class));
                return true;
            case R.id.refresh:
                callAPI();
                return true;
            case R.id.about:
                startActivityForResult(new Intent(ParentActivity.this, AboutActivity.class), SETTINGS);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
