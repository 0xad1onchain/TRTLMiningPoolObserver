package ml.fifty9.poolmonitor.model.pool;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by HemantJ on 10/02/18.
 */

public class Network {
    @SerializedName("difficulty")
    @Expose
    private Integer difficulty;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("timestamp")
    @Expose
    private Integer timestamp;
    @SerializedName("reward")
    @Expose
    private Integer reward;
    @SerializedName("hash")
    @Expose
    private String hash;

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getReward() {
        return reward;
    }

    public void setReward(Integer reward) {
        this.reward = reward;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

}
