package ml.fifty9.poolmonitor;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import ml.fifty9.poolmonitor.model.PayoutsPOJO;
import ml.fifty9.poolmonitor.model.statsaddress.Pool;


/**
 * A simple {@link Fragment} subclass.
 */
public class PayoutFragment extends Fragment {
    private List<String> payments;
    private TextView paymentText;
    private String[] paymentDetails;
    private ArrayList<PayoutsPOJO> payouts;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private View view;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public PayoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_payout, container, false);

        mSwipeRefreshLayout = view.findViewById(R.id.swipePayoutRefreshLayout);
        recyclerView = view.findViewById(R.id.recyclerView);
        this.view = view.findViewById(R.id.container);
        payouts = new ArrayList<>();

        Pool pool = ((ParentActivity)this.getActivity()).getPool();
        payments = pool.getPayments();

        if(payments.size() == 0 || null == payments){
            recyclerView.setVisibility(View.GONE);
            Snackbar.make(this.view, "Payouts Empty",Snackbar.LENGTH_INDEFINITE).show();
//            paymentText.setVisibility(View.VISIBLE);
//            paymentText.setText("Empty Payments");
        }else{
            recyclerView.setVisibility(View.VISIBLE);
            for(int i = 0; i < payments.size(); i+=2){
                paymentDetails = payments.get(i).split(":",3);
                String id = paymentDetails[0];
                String price = String.valueOf(Float.valueOf(paymentDetails[1])/100) + " TRTL";
                String timeStamp = payments.get(i+1);
                payouts.add(new PayoutsPOJO(id,price,timeStamp));
            }
            adapter = new RecyclerAdapter(payouts);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter.notifyDataSetChanged();
        }

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refresh();
            }
        });

        return view;
    }

    public void refresh() {
        ((ParentActivity)this.getActivity()).callAPI();
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{
        private ArrayList<PayoutsPOJO> payouts;

        public RecyclerAdapter(ArrayList<PayoutsPOJO> payouts){
            this.payouts = payouts;
        }

        @Override
        public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.payouts_item,parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
            PayoutsPOJO payoutsPOJO = payouts.get(position);
            TextView price, time, paymentId;

            price = holder.price;
            price.setText(payoutsPOJO.getPrice());

            time = holder.time;
            Calendar cal = Calendar.getInstance();
            TimeZone tz = cal.getTimeZone();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            sdf.setTimeZone(tz);
            java.util.Date datetime = new java.util.Date((Long.valueOf(payoutsPOJO.getTimeStamp())* 1000));
            String localTime = sdf.format(datetime);
            time.setText(localTime);

            paymentId = holder.paymentId;
            paymentId.setMovementMethod(LinkMovementMethod.getInstance());
            String text = "<a href='https://turtle-coin.com/?hash=" + payoutsPOJO.getPaymentId() + "#blockchain_transaction'>" + payoutsPOJO.getPaymentId() + "</a>";
            paymentId.setText(Html.fromHtml(text));
        }

        @Override
        public int getItemCount() {
            return payouts.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            private TextView price, time, paymentId;
            public ViewHolder(View itemView) {
                super(itemView);
                price = itemView.findViewById(R.id.price);
                time = itemView.findViewById(R.id.time);
                paymentId = itemView.findViewById(R.id.paymentId);
            }
        }
    }

}
