package ml.fifty9.poolmonitor.model.pool;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by HemantJ on 10/02/18.
 */

public class Pool {

        @SerializedName("stats")
        @Expose
        private Stats stats;
        @SerializedName("blocks")
        @Expose
        private List<String> blocks = null;
        @SerializedName("totalBlocks")
        @Expose
        private Integer totalBlocks;
        @SerializedName("payments")
        @Expose
        private List<String> payments = null;
        @SerializedName("totalPayments")
        @Expose
        private Integer totalPayments;
        @SerializedName("totalMinersPaid")
        @Expose
        private Integer totalMinersPaid;
        @SerializedName("miners")
        @Expose
        private Integer miners;
        @SerializedName("hashrate")
        @Expose
        private Integer hashrate;
        @SerializedName("roundHashes")
        @Expose
        private Integer roundHashes;
        @SerializedName("lastBlockFound")
        @Expose
        private String lastBlockFound;

        public Stats getStats() {
            return stats;
        }

        public void setStats(Stats stats) {
            this.stats = stats;
        }

        public List<String> getBlocks() {
            return blocks;
        }

        public void setBlocks(List<String> blocks) {
            this.blocks = blocks;
        }

        public Integer getTotalBlocks() {
            return totalBlocks;
        }

        public void setTotalBlocks(Integer totalBlocks) {
            this.totalBlocks = totalBlocks;
        }

        public List<String> getPayments() {
            return payments;
        }

        public void setPayments(List<String> payments) {
            this.payments = payments;
        }

        public Integer getTotalPayments() {
            return totalPayments;
        }

        public void setTotalPayments(Integer totalPayments) {
            this.totalPayments = totalPayments;
        }

        public Integer getTotalMinersPaid() {
            return totalMinersPaid;
        }

        public void setTotalMinersPaid(Integer totalMinersPaid) {
            this.totalMinersPaid = totalMinersPaid;
        }

        public Integer getMiners() {
            return miners;
        }

        public void setMiners(Integer miners) {
            this.miners = miners;
        }

        public Integer getHashrate() {
            return hashrate;
        }

        public void setHashrate(Integer hashrate) {
            this.hashrate = hashrate;
        }

        public Integer getRoundHashes() {
            return roundHashes;
        }

        public void setRoundHashes(Integer roundHashes) {
            this.roundHashes = roundHashes;
        }

        public String getLastBlockFound() {
            return lastBlockFound;
        }

        public void setLastBlockFound(String lastBlockFound) {
            this.lastBlockFound = lastBlockFound;
        }
}
