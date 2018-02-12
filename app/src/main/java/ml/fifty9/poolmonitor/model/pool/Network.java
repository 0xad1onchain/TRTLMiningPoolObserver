package ml.fifty9.poolmonitor.model.pool;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by HemantJ on 10/02/18.
 */

public class Network {
    @SerializedName("difficulty")
    @Expose
    private Long difficulty;
    @SerializedName("height")
    @Expose
    private Long height;
    @SerializedName("timestamp")
    @Expose
    private Long timestamp;
    @SerializedName("reward")
    @Expose
    private Long reward;
    @SerializedName("hash")
    @Expose
    private String hash;

    public Long getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Long difficulty) {
        this.difficulty = difficulty;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getReward() {
        return reward;
    }

    public void setReward(Long reward) {
        this.reward = reward;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

}
