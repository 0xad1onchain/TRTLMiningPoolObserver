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
    private List<List<Integer>> hashrate = null;
    @SerializedName("workers")
    @Expose
    private List<List<Integer>> workers = null;
    @SerializedName("difficulty")
    @Expose
    private List<List<Integer>> difficulty = null;

    public List<List<Integer>> getHashrate() {
        return hashrate;
    }

    public void setHashrate(List<List<Integer>> hashrate) {
        this.hashrate = hashrate;
    }

    public List<List<Integer>> getWorkers() {
        return workers;
    }

    public void setWorkers(List<List<Integer>> workers) {
        this.workers = workers;
    }

    public List<List<Integer>> getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(List<List<Integer>> difficulty) {
        this.difficulty = difficulty;
    }
}
