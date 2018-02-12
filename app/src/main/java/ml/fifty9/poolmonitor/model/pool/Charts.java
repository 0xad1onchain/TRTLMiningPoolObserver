package ml.fifty9.poolmonitor.model.pool;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by HemantJ on 10/02/18.
 */

public class Charts {

    @SerializedName("hashrate")
    @Expose
    private List<List<Long>> hashrate = null;
    @SerializedName("workers")
    @Expose
    private List<List<Long>> workers = null;
    @SerializedName("difficulty")
    @Expose
    private List<List<Long>> difficulty = null;

    public List<List<Long>> getHashrate() {
        return hashrate;
    }

    public void setHashrate(List<List<Long>> hashrate) {
        this.hashrate = hashrate;
    }

    public List<List<Long>> getWorkers() {
        return workers;
    }

    public void setWorkers(List<List<Long>> workers) {
        this.workers = workers;
    }

    public List<List<Long>> getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(List<List<Long>> difficulty) {
        this.difficulty = difficulty;
    }
}
