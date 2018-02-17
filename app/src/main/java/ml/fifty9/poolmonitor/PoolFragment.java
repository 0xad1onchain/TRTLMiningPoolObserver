package ml.fifty9.poolmonitor;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.net.URI;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import ml.fifty9.poolmonitor.model.pool.Charts;
import ml.fifty9.poolmonitor.model.pool.Config;
import ml.fifty9.poolmonitor.model.pool.Network;
import ml.fifty9.poolmonitor.model.pool.Pool;


/**
 * A simple {@link Fragment} subclass.
 */
public class PoolFragment extends Fragment {

    Charts chart;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    Network network;
    Config config;
    Pool pool;
    TextView poolFeeText, paymentThresholdText, minersText, poolHashRateText, minersPaidText, totalPaymentsText, difficultyText, rewardText, updateTimeText, heightText, poolNameText;
    List<List<Long>> hashes;
    LineChart hashChart;
    private SharedPreferences poolPreferences;


    public PoolFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pool, container, false);

        poolPreferences = this.getActivity().getSharedPreferences("URL_PREFS", 0);
        String poolString = poolPreferences.getString("url","");

        mSwipeRefreshLayout = view.findViewById(R.id.swipePoolRefreshLayout);
        poolFeeText = view.findViewById(R.id.fee_text);
        poolNameText = view.findViewById(R.id.pool_name_text);
        paymentThresholdText = view.findViewById(R.id.payment_threshold);
        minersPaidText = view.findViewById(R.id.miners_paid_text);
        minersText = view.findViewById(R.id.miners_text);
        poolHashRateText = view.findViewById(R.id.pool_hashrate_text);
        totalPaymentsText = view.findViewById(R.id.total_payments);
        difficultyText = view.findViewById(R.id.difficulty_text);
        rewardText = view.findViewById(R.id.reward_text);
        updateTimeText = view.findViewById(R.id.update_time_text);
        heightText = view.findViewById(R.id.height_text);
        hashChart = view.findViewById(R.id.hash_rate_chart);

        if (poolString.isEmpty()) {

        }
        else {
            try {
                URI uri = new URI(poolString);
                String path = uri.getHost();
                if (path.equals("api.z-pool.com")) {
                    path = "z-pool.com";
                }
                poolNameText.setText(path);
            }
            catch (Exception e) {e.printStackTrace();}
        }

        try {
            chart = ((ParentActivity)this.getActivity()).getChartPOJO();
            network = ((ParentActivity)this.getActivity()).getNetwork();
            config = ((ParentActivity)this.getActivity()).getConfig();
            pool = ((ParentActivity)this.getActivity()).getPoolPOJO();
            Log.d("LOOG", config.getCoin());
            hashes = chart.getHashrate();

            displayStats();
            populateChart();
        }
        catch (Exception e) {
            Log.d("E", e.getLocalizedMessage());
        }

        // Refresh items
        mSwipeRefreshLayout.setOnRefreshListener(this::refresh);

        return view;
    }

    public void refresh() {
        ((ParentActivity)this.getActivity()).callAPI();
    }


    public String convertCoin(Long coins) {
        coins = coins/100;
        return String.valueOf(coins) + " TRTL";
    }

    public String by100 (Long coin) {
        return String.valueOf(coin/100);
    }

    public String getDate(long timeLong) {

        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        sdf.setTimeZone(tz);
        java.util.Date time=new java.util.Date((long)timeLong*1000);
        String localTime = sdf.format(time);

        return localTime;
    }

    private void displayStats() {
        poolFeeText.setText(config.getFee().toString() + "%");
        paymentThresholdText.setText(convertCoin(config.getMinPaymentThreshold()));
        minersText.setText(String.valueOf(pool.getMiners()));
        poolHashRateText.setText(getReadableHashString(pool.getHashrate()));
        minersPaidText.setText(String.valueOf(pool.getTotalMinersPaid()));
        totalPaymentsText.setText(String.valueOf(pool.getTotalPayments()));
        difficultyText.setText(String.valueOf(network.getDifficulty()));
        rewardText.setText(convertCoin(network.getReward()));
        updateTimeText.setText(getDate(network.getTimestamp()));
        heightText.setText(String.valueOf(network.getHeight()));
    }

    public String getReadableHashString(Long hashrate) {
        int i = 0;
        Float floatHashrate = Float.valueOf(hashrate);
        String[] byteUnits = {" H", " KH", " MH", " GH", " TH", " PH" };
        while (floatHashrate > 1000){
            floatHashrate = floatHashrate / 1000;
            i++;
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.##");
        return decimalFormat.format(floatHashrate) + byteUnits[i] + "/s";
    }

    public void populateChart() {

        if (null == hashes) {
            Log.d("data","No hash data found");
        }

        else {

            List<Entry> entries = new ArrayList<Entry>();
            long i = 0;
            for (List<Long> hash : hashes) {
                entries.add(new Entry(i, hash.get(1)));
                i = i+1;
            }

            LineDataSet dataSet = new LineDataSet(entries, "");
            dataSet.disableDashedLine();
            dataSet.disableDashedHighlightLine();
            dataSet.setColor(Color.GRAY);
            dataSet.setFillColor(Color.GRAY);
            dataSet.setDrawFilled(true);
            dataSet.setFillAlpha(255);
            dataSet.setValueTextColor(Color.BLACK);
            dataSet.setDrawValues(false);
            dataSet.setDrawCircles(false);
            dataSet.setCircleRadius(0);

            LineData lineData = new LineData(dataSet);
            lineData.setValueTextSize(0);
            lineData.setDrawValues(false);

            Legend legend = hashChart.getLegend();
            legend.setEnabled(false);

            YAxis leftAxis = hashChart.getAxisLeft();
            leftAxis.disableAxisLineDashedLine();
            leftAxis.removeAllLimitLines();
            leftAxis.disableGridDashedLine();

            YAxis rightAxis = hashChart.getAxisRight();
            rightAxis.setDrawLabels(false); // no axis labels
            rightAxis.setDrawAxisLine(false); // no axis line
            rightAxis.setDrawGridLines(false); // no grid lines
            rightAxis.disableAxisLineDashedLine();
            rightAxis.removeAllLimitLines();
            rightAxis.disableGridDashedLine();

            XAxis xAxis = hashChart.getXAxis();
            xAxis.setDrawLabels(false); // no axis labels
            xAxis.setDrawAxisLine(false); // no axis line
            xAxis.setDrawGridLines(false); // no grid lines
            xAxis.disableAxisLineDashedLine();
            xAxis.removeAllLimitLines();
            xAxis.disableGridDashedLine();


            hashChart.setBackgroundColor(Color.WHITE);
            Description description = new Description();
            description.setTextAlign(Paint.Align.LEFT);
            description.setText("Hashes Submitted");
            description.setPosition(0,0);
            hashChart.setDescription(description);
            hashChart.setData(lineData);
            hashChart.invalidate();
            hashChart.fitScreen();
        }
    }

}
