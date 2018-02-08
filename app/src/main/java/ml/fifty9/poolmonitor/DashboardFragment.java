package ml.fifty9.poolmonitor;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ml.fifty9.poolmonitor.model.Charts;
import ml.fifty9.poolmonitor.model.Pool;

/**
 * A simple {@link Fragment} subclass.
 * @author HemantJ, Aditya Gupta
 */
//TODO make the UI nice :p
public class DashboardFragment extends Fragment {

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_dashboard, container, false);
        Charts chart = ((ParentActivity)this.getActivity()).getChart();
        TextView head = (TextView) view.findViewById(R.id.main_text);
        head.setText(chart.getPayments().toString());
        return view;
    }


}
