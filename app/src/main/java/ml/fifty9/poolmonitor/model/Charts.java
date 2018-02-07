package ml.fifty9.poolmonitor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author HemantJ
 */

public class Charts {
    @SerializedName("payments")
    @Expose
    private List<List<Integer>> payments = null;

    @SerializedName("hashrate")
    @Expose
    private List<List<Integer>> hashrate = null;

    public List<List<Integer>> getPayments() {
        return payments;
    }

    public void setPayments(List<List<Integer>> payments) {
        this.payments = payments;
    }

    public List<List<Integer>> getHashrate() {
        return hashrate;
    }

    public void setHashrate(List<List<Integer>> hashrate) {
        this.hashrate = hashrate;
    }
}
