package ml.fifty9.poolmonitor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author HemantJ
 */

public class Pool {

    @SerializedName("stats")
    @Expose
    private Stats stats;

    @SerializedName("payments")
    @Expose
    private List<String> payments = null;

    @SerializedName("charts")
    @Expose
    private Charts charts;

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public List<String> getPayments() {
        return payments;
    }

    public void setPayments(List<String> payments) {
        this.payments = payments;
    }

    public Charts getCharts() {
        return charts;
    }

    public Pool getPool(){
        return this;
    }

    public void setCharts(Charts charts) {
        this.charts = charts;
    }
}
