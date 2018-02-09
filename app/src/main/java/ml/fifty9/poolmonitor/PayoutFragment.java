package ml.fifty9.poolmonitor;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ml.fifty9.poolmonitor.model.statsaddress.Pool;


/**
 * A simple {@link Fragment} subclass.
 */
public class PayoutFragment extends Fragment {
    private List<String> payments;
    private TextView paymentText;

    public PayoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_payout, container, false);
        paymentText = view.findViewById(R.id.payment);

        Pool pool = ((ParentActivity)this.getActivity()).getPoolPOJO();
        payments = pool.getPayments();

        if(payments.size() == 0){
            paymentText.setText("Empty Payments");
        }else{
            Log.d("Payout Fragment",payments.get(0));
        }

        return view;
    }

}
