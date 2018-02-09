package ml.fifty9.poolmonitor.model.pool;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by HemantJ on 10/02/18.
 */

public class Port {
    @SerializedName("port")
    @Expose
    private Integer port;

    @SerializedName("difficulty")
    @Expose
    private Integer difficulty;

    @SerializedName("desc")
    @Expose
    private String desc;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
