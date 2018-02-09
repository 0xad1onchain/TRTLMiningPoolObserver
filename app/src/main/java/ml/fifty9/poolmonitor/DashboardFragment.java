package ml.fifty9.poolmonitor;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ml.fifty9.poolmonitor.model.Charts;
import ml.fifty9.poolmonitor.model.Pool;
import ml.fifty9.poolmonitor.model.Stats;

/**
 * A simple {@link Fragment} subclass.
 * @author HemantJ, Aditya Gupta
 */
//TODO make the UI nice :p
public class DashboardFragment extends Fragment {

    private TextView hashRate, lastShare, paid, balance, submittedHashes;
    List<List<Integer>> hashes;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_dashboard, container, false);
        Charts chart = ((ParentActivity)this.getActivity()).getChart();
        Stats stats = ((ParentActivity)this.getActivity()).getStats();
        hashes = chart.getHashrate();

        hashRate = view.findViewById(R.id.hash_rate);
        lastShare = view.findViewById(R.id.last_share_text);
        paid = view.findViewById(R.id.paid_text);
        balance = view.findViewById(R.id.balance_text);
        submittedHashes = view.findViewById(R.id.hash_submitted_text);


        balance.setText(convertCoin(stats.getBalance()));
        paid.setText(convertCoin(stats.getPaid()));
        submittedHashes.setText(stats.getHashes());
        lastShare.setText(getDate(stats.getLastShare()));
        hashRate.setText(getHashRate());

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


        String hashRate = String.valueOf(totalHashes / totalTime) + " H/s";

        return hashRate;

    }

    public String convertCoin(String coin) {
        int coins = Integer.parseInt(coin);
        coins = coins/100;
        return String.valueOf(coins);
    }

    public String getDate(String timeStampString) {

        Long timeLong = Long.parseLong(timeStampString);
        java.util.Date time=new java.util.Date((long)timeLong*1000);
        return time.toString();
    }


}
