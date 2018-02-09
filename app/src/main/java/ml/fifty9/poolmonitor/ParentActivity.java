package ml.fifty9.poolmonitor;

import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import ml.fifty9.poolmonitor.model.Charts;
import ml.fifty9.poolmonitor.model.Pool;
import ml.fifty9.poolmonitor.model.Stats;
import ml.fifty9.poolmonitor.util.RetrofitAPI;
import ml.fifty9.poolmonitor.util.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParentActivity extends AppCompatActivity {
    private SectionPagerAdapter mSectionPagerAdapter;
    private ViewPager mViewPager;
    private Toolbar toolbar;
    private SharedPreferences sharedPreferences, walletPref;
    private String wallet,pool,walletText;
    private RetrofitAPI retrofitAPI;
    private Charts chartObj;
    private Stats statObj;
    private Pool poolPOJO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);

        sharedPreferences = this.getSharedPreferences("URL_PREFS", 0);
        walletPref = this.getSharedPreferences("WALLET_PREFS",0);

        walletText = walletPref.getString("wallet","");
        pool = sharedPreferences.getString("url","");
        retrofitAPI = RetrofitService.getAPI(pool);
        callAPI();
    }

    public Charts getChart() {
        return this.chartObj;
    }

    public Stats getStats() {
        return this.statObj;
    }

    public Pool getPoolPOJO(){
        return this.poolPOJO;
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

    private void callAPI() {
        retrofitAPI.queryDashboardStats(walletText)
                .enqueue(new Callback<Pool>() {
                    @Override
                    public void onResponse(Call<Pool> call, Response<Pool> response) {
                        Log.d("Response from API",
                                response.body().getCharts().getPayments().get(0).get(0).toString());
                        setAPIObjects(response);
                        inflateTabs();
                    }

                    @Override
                    public void onFailure(Call<Pool> call, Throwable t) {
                        Log.d("Error",t.getMessage());
                    }
                });
    }

    private void setAPIObjects(Response<Pool> response) {
        chartObj = response.body().getCharts();
        statObj = response.body().getStats();
        poolPOJO = response.body().getPool();
    }

    private void inflateTabs() {
        mSectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.container);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pool Monitor");

        mViewPager.setAdapter(mSectionPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

}

