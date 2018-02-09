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
    private Integer hashrateWindow;

    @SerializedName("fee")
    @Expose
    private Double fee;

    @SerializedName("coin")
    @Expose
    private String coin;

    @SerializedName("coinUnits")
    @Expose
    private Integer coinUnits;

    @SerializedName("coinDifficultyTarget")
    @Expose
    private Integer coinDifficultyTarget;

    @SerializedName("symbol")
    @Expose
    private String symbol;

    @SerializedName("depth")
    @Expose
    private Integer depth;

    @SerializedName("donation")
    @Expose
    private Donation donation;

    @SerializedName("version")
    @Expose
    private String version;

    @SerializedName("minPaymentThreshold")
    @Expose
    private Integer minPaymentThreshold;

    @SerializedName("denominationUnit")
    @Expose
    private Integer denominationUnit;

    @SerializedName("blockTime")
    @Expose
    private Integer blockTime;

    @SerializedName("slushMiningEnabled")
    @Expose
    private Boolean slushMiningEnabled;

    @SerializedName("weight")
    @Expose
    private Integer weight;

    public List<Port> getPorts() {
        return ports;
    }

    public void setPorts(List<Port> ports) {
        this.ports = ports;
    }

    public Integer getHashrateWindow() {
        return hashrateWindow;
    }

    public void setHashrateWindow(Integer hashrateWindow) {
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

    public Integer getCoinUnits() {
        return coinUnits;
    }

    public void setCoinUnits(Integer coinUnits) {
        this.coinUnits = coinUnits;
    }

    public Integer getCoinDifficultyTarget() {
        return coinDifficultyTarget;
    }

    public void setCoinDifficultyTarget(Integer coinDifficultyTarget) {
        this.coinDifficultyTarget = coinDifficultyTarget;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
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

    public Integer getMinPaymentThreshold() {
        return minPaymentThreshold;
    }

    public void setMinPaymentThreshold(Integer minPaymentThreshold) {
        this.minPaymentThreshold = minPaymentThreshold;
    }

    public Integer getDenominationUnit() {
        return denominationUnit;
    }

    public void setDenominationUnit(Integer denominationUnit) {
        this.denominationUnit = denominationUnit;
    }

    public Integer getBlockTime() {
        return blockTime;
    }

    public void setBlockTime(Integer blockTime) {
        this.blockTime = blockTime;
    }

    public Boolean getSlushMiningEnabled() {
        return slushMiningEnabled;
    }

    public void setSlushMiningEnabled(Boolean slushMiningEnabled) {
        this.slushMiningEnabled = slushMiningEnabled;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
