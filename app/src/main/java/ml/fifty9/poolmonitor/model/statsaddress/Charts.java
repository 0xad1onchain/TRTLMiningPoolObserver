package ml.fifty9.poolmonitor.model.statsaddress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author HemantJ
 */

public class Charts {
    @SerializedName("payments")
    @Expose
    private List<List<Long>> payments = null;

    @SerializedName("hashrate")
    @Expose
    private List<List<Long>> hashrate = null;

    public List<List<Long>> getPayments() {
        return payments;
    }

    public void setPayments(List<List<Long>> payments) {
        this.payments = payments;
    }

    public List<List<Long>> getHashrate() {
        return hashrate;
    }

    public void setHashrate(List<List<Long>> hashrate) {
        this.hashrate = hashrate;
    }
}
