package ml.fifty9.poolmonitor.model.pool;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by HemantJ on 10/02/18.
 */

public class Config {

    @SerializedName("ports")
    @Expose
    private List<Port> ports = null;

    @SerializedName("hashrateWindow")
    @Expose
    private Long hashrateWindow;

    @SerializedName("fee")
    @Expose
    private Double fee;

    @SerializedName("coin")
    @Expose
    private String coin;

    @SerializedName("coinUnits")
    @Expose
    private Long coinUnits;

    @SerializedName("coinDifficultyTarget")
    @Expose
    private Long coinDifficultyTarget;

    @SerializedName("symbol")
    @Expose
    private String symbol;

    @SerializedName("depth")
    @Expose
    private Long depth;

    @SerializedName("donation")
    @Expose
    private Donation donation;

    @SerializedName("version")
    @Expose
    private String version;

    @SerializedName("minPaymentThreshold")
    @Expose
    private Long minPaymentThreshold;

    @SerializedName("denominationUnit")
    @Expose
    private Long denominationUnit;

    @SerializedName("blockTime")
    @Expose
    private Long blockTime;

    @SerializedName("slushMiningEnabled")
    @Expose
    private Boolean slushMiningEnabled;

    @SerializedName("weight")
    @Expose
    private Long weight;

    public List<Port> getPorts() {
        return ports;
    }

    public void setPorts(List<Port> ports) {
        this.ports = ports;
    }

    public Long getHashrateWindow() {
        return hashrateWindow;
    }

    public void setHashrateWindow(Long hashrateWindow) {
        this.hashrateWindow = hashrateWindow;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public Long getCoinUnits() {
        return coinUnits;
    }

    public void setCoinUnits(Long coinUnits) {
        this.coinUnits = coinUnits;
    }

    public Long getCoinDifficultyTarget() {
        return coinDifficultyTarget;
    }

    public void setCoinDifficultyTarget(Long coinDifficultyTarget) {
        this.coinDifficultyTarget = coinDifficultyTarget;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Long getDepth() {
        return depth;
    }

    public void setDepth(Long depth) {
        this.depth = depth;
    }

    public Donation getDonation() {
        return donation;
    }

    public void setDonation(Donation donation) {
        this.donation = donation;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Long getMinPaymentThreshold() {
        return minPaymentThreshold;
    }

    public void setMinPaymentThreshold(Long minPaymentThreshold) {
        this.minPaymentThreshold = minPaymentThreshold;
    }

    public Long getDenominationUnit() {
        return denominationUnit;
    }

    public void setDenominationUnit(Long denominationUnit) {
        this.denominationUnit = denominationUnit;
    }

    public Long getBlockTime() {
        return blockTime;
    }

    public void setBlockTime(Long blockTime) {
        this.blockTime = blockTime;
    }

    public Boolean getSlushMiningEnabled() {
        return slushMiningEnabled;
    }

    public void setSlushMiningEnabled(Boolean slushMiningEnabled) {
        this.slushMiningEnabled = slushMiningEnabled;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }
}
