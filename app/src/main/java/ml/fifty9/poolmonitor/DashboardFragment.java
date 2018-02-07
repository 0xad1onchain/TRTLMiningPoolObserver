package ml.fifty9.poolmonitor;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.reactivex.disposables.CompositeDisposable;
import ml.fifty9.poolmonitor.model.Pool;
import ml.fifty9.poolmonitor.util.RetrofitAPI;
import ml.fifty9.poolmonitor.util.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * @author HemantJ, Aditya Gupta
 */
//TODO make the UI nice :p
public class DashboardFragment extends Fragment {
    private RetrofitAPI retrofitAPI;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_dashboard, container, false);
        retrofitAPI = RetrofitService.getAPI();
        callAPI();
        return view;
    }

    private void callAPI() {
        retrofitAPI.queryDashboardStats("TRTLux32vos9TtGNRQTux4WLFQTBX2ii3RMzGsrbN2dKatwucquqQRmUUzXkuiNyEA5NCqPUiP3SeSeJVT9bpW3SGyWKmRcoT3Z")
                    .enqueue(new Callback<Pool>() {
                        @Override
                        public void onResponse(Call<Pool> call, Response<Pool> response) {
                            /**
                             * Get response from the API
                             * use response.body.getCharts() for various payments and hashrates
                             * use response.body.getStats() to get details like hashes,lastShare, balance, paid
                             */
                            Log.d("Response from API",
                                    response.body().getCharts().getPayments().get(0).get(0).toString());
                        }

                        @Override
                        public void onFailure(Call<Pool> call, Throwable t) {
                            Log.d("Error",t.getMessage());
                        }
        });
    }

}
