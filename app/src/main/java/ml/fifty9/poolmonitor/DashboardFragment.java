package ml.fifty9.poolmonitor;


import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import ml.fifty9.poolmonitor.model.statsaddress.Charts;
import ml.fifty9.poolmonitor.model.statsaddress.Stats;

/**
 * A simple {@link Fragment} subclass.
 * @author HemantJ, Aditya Gupta
 */
//TODO (Aditya) make the UI nice :p
public class DashboardFragment extends Fragment {

    private TextView hashRate, lastShare, paid, balance, submittedHashes;
    LineChart hashChart;
    List<List<Integer>> hashes;
    Charts chart;
    Stats stats;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_dashboard, container, false);

        hashRate = view.findViewById(R.id.hash_rate);
        lastShare = view.findViewById(R.id.last_share_text);
        paid = view.findViewById(R.id.paid_text);
        balance = view.findViewById(R.id.balance_text);
        submittedHashes = view.findViewById(R.id.hash_submitted_text);
        hashChart = (LineChart) view.findViewById(R.id.hash_rate_chart);

        try {
            chart = ((ParentActivity)this.getActivity()).getChart();
            stats = ((ParentActivity)this.getActivity()).getStats();
            hashes = chart.getHashrate();

            displayStats();
            populateChart();
        }
        catch (Exception e) {
            Log.d("E", e.getLocalizedMessage());
        }

        return view;
    }

    public String getHashRate() {

        int totalTime = 0;
        int totalHashes = 0;

        for (int i = 0; i < hashes.size(); i++) {
            List<Integer> entry = hashes.get(i);
            totalHashes = totalHashes + entry.get(1) * entry.get(2);
            totalTime = totalTime + entry.get(2);
        }

        String hashRate = String.valueOf(totalHashes / totalTime) + " H/sec";

        return hashRate;

    }

    public String convertCoin(String coin) {
        int coins = Integer.parseInt(coin);
        coins = coins/100;
        return String.valueOf(coins) + " TRTL";
    }

    public String getDate(String timeStampString) {

        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        sdf.setTimeZone(tz);
        Long timeLong = Long.parseLong(timeStampString);
        java.util.Date time=new java.util.Date((long)timeLong*1000);
        String localTime = sdf.format(time);

        return localTime;
    }

    public void displayStats() {

        String bal = stats.getBalance();
        if (null == bal)
            balance.setText(R.string.null_value_string);
        else
            balance.setText(convertCoin(bal));

        String paidBal = stats.getPaid();
        if (null == paidBal)
            paid.setText(R.string.null_value_string);
        else
            paid.setText(convertCoin(paidBal));

        String hashes = stats.getHashes();
        if (null == hashes)
            submittedHashes.setText(R.string.null_value_string);
        else
            submittedHashes.setText(stats.getHashes());

        String lastShareString = stats.getLastShare();
        if (null == lastShareString)
            lastShare.setText(R.string.null_value_string);
        else
            lastShare.setText(getDate(stats.getLastShare()));

        String hashRateString = getHashRate();
        if (null == hashRateString)
            hashRate.setText(R.string.null_value_string);
        else
            hashRate.setText(getHashRate());

    }

    public void populateChart() {

        if (null == hashes) {
            Log.d("data","No hash data found");
        }

        else {

            List<Integer> hashList = new ArrayList<Integer>();

            List<Entry> entries = new ArrayList<Entry>();

            for (int i = 0; i < hashes.size(); i++) {
                for (int j = 0; j < hashes.get(i).get(2); j++) {
                    hashList.add(hashes.get(i).get(1));
                }
            }

            for (int i = 0; i< hashList.size(); i++) {
                entries.add(new Entry(i, hashList.get(i)));
            }

            LineDataSet dataSet = new LineDataSet(entries, "");
            dataSet.disableDashedLine();
            dataSet.disableDashedHighlightLine();
            dataSet.setColor(Color.GRAY);
            dataSet.setFillColor(Color.GRAY);
            dataSet.setDrawFilled(true);
            dataSet.setFillAlpha(255);
            dataSet.setValueTextColor(Color.BLACK);
            dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            dataSet.setDrawValues(false);
            dataSet.setDrawCircles(false);
            dataSet.setCircleRadius(0);

            LineData lineData = new LineData(dataSet);
            lineData.setValueTextSize(0);
            lineData.setDrawValues(false);

            Legend legend = hashChart.getLegend();
            legend.setEnabled(false);

            YAxis leftAxis = hashChart.getAxisLeft();
//            leftAxis.setDrawLabels(false); // no axis labels
//            leftAxis.setDrawAxisLine(false); // no axis line
//            leftAxis.setDrawGridLines(false); // no grid lines
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
