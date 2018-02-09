package ml.fifty9.poolmonitor.model.pool;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by HemantJ on 10/02/18.
 */

public class StatExample {

    @SerializedName("config")
    @Expose
    private Config config;
    @SerializedName("network")
    @Expose
    private Network network;
    @SerializedName("pool")
    @Expose
    private Pool pool;
    @SerializedName("charts")
    @Expose
    private Charts charts;

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public Pool getPool() {
        return pool;
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    public Charts getCharts() {
        return charts;
    }

    public void setCharts(Charts charts) {
        this.charts = charts;
    }

}
