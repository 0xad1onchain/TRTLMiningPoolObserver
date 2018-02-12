package ml.fifty9.poolmonitor;

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

public class ParentActivity extends AppCompatActivity {
    private SectionPagerAdapter mSectionPagerAdapter;
    private ViewPager mViewPager;
    private Toolbar toolbar;
    private AnimatedSvgView svgView;
    private SharedPreferences sharedPreferences, walletPref;
    private String pool,walletText;
    private RetrofitAPI retrofitAPI;
    private View view;
    private ml.fifty9.poolmonitor.model.statsaddress.Charts chartObj;
    private ml.fifty9.poolmonitor.model.statsaddress.Stats statObj;
    private ml.fifty9.poolmonitor.model.statsaddress.Pool poolObj;

    private ml.fifty9.poolmonitor.model.pool.Charts chartPOJO;
    private ml.fifty9.poolmonitor.model.pool.Config configPOJO;
    private ml.fifty9.poolmonitor.model.pool.Network networkPOJO;
    private ml.fifty9.poolmonitor.model.pool.Pool poolPOJO;

    private boolean addressCall = false;
    private boolean statsCall = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);

        svgView = findViewById(R.id.animated_svg_view);
        view = findViewById(R.id.main_content);
        svgView.start();

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pool Monitor");

        sharedPreferences = this.getSharedPreferences("URL_PREFS", 0);
        walletPref = this.getSharedPreferences("WALLET_PREFS",0);

        walletText = walletPref.getString("wallet","");
        pool = sharedPreferences.getString("url","");
        if(walletText.isEmpty() || pool.isEmpty()){
            startActivity(new Intent(ParentActivity.this, WalletActivity.class));
        }else{
            ReminderUtility.scheduleReminder(this);
            retrofitAPI = RetrofitService.getAPI(pool);
            callAPI();
            setUpSharedPrefs();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("Menu", "Function Started");
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

    public void setUpSharedPrefs(){
        Intent intent = new Intent(ParentActivity.this, TRTLService.class);
        intent.setAction(ReminderTasks.ACTION_TURTLE);
        startService(intent);
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
                    return "Payout";
            }
            return null;
        }

    }



    protected void callAPI() {
        retrofitAPI.queryDashboardStats(walletText)
                .enqueue(new Callback<Pool>() {
                    @Override
                    public void onResponse(Call<Pool> call, Response<Pool> response) {
                        setAPIObjectsWallet(response);
                        addressCall = true;
                        if (statsCall == true) {
                            inflateTabs();
                            statsCall = false;
                            addressCall = false;
                        }
                    }

                    @Override
                    public void onFailure(Call<Pool> call, Throwable t) {
                        svgView.setVisibility(View.GONE);
                        Snackbar.make(view, "Error in Connection",Snackbar.LENGTH_INDEFINITE).show();
                        Log.d("Error",t.getMessage());
                    }
                });

        retrofitAPI.queryStats()
                .enqueue(new Callback<StatExample>() {
                    @Override
                    public void onResponse(Call<StatExample> call, Response<StatExample> response) {
                        Log.d("Stats", response.body().getConfig().
                                getPorts().get(0).getDifficulty().toString());
                        setAPIObjectsStats(response);
                        Log.d("LOOG", configPOJO.getCoin());

                        statsCall = true;
                        if (addressCall == true) {
                            inflateTabs();
                            statsCall = false;
                            addressCall = false;
                        }

                    }

                    @Override
                    public void onFailure(Call<StatExample> call, Throwable t) {
                        svgView.setVisibility(View.GONE);
                        Snackbar.make(view, "Error in Connection",Snackbar.LENGTH_INDEFINITE).show();
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
        mSectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.container);


        mViewPager.setAdapter(mSectionPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tabs);
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
                startActivity(new Intent(ParentActivity.this, AboutActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
