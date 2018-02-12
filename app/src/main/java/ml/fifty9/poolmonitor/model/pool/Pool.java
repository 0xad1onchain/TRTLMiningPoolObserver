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
        private Long totalBlocks;
        @SerializedName("payments")
        @Expose
        private List<String> payments = null;
        @SerializedName("totalPayments")
        @Expose
        private Long totalPayments;
        @SerializedName("totalMinersPaid")
        @Expose
        private Long totalMinersPaid;
        @SerializedName("miners")
        @Expose
        private Long miners;
        @SerializedName("hashrate")
        @Expose
        private Long hashrate;
        @SerializedName("roundHashes")
        @Expose
        private Long roundHashes;
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

        public Long getTotalBlocks() {
            return totalBlocks;
        }

        public void setTotalBlocks(Long totalBlocks) {
            this.totalBlocks = totalBlocks;
        }

        public List<String> getPayments() {
            return payments;
        }

        public void setPayments(List<String> payments) {
            this.payments = payments;
        }

        public Long getTotalPayments() {
            return totalPayments;
        }

        public void setTotalPayments(Long totalPayments) {
            this.totalPayments = totalPayments;
        }

        public Long getTotalMinersPaid() {
            return totalMinersPaid;
        }

        public void setTotalMinersPaid(Long totalMinersPaid) {
            this.totalMinersPaid = totalMinersPaid;
        }

        public Long getMiners() {
            return miners;
        }

        public void setMiners(Long miners) {
            this.miners = miners;
        }

        public Long getHashrate() {
            return hashrate;
        }

        public void setHashrate(Long hashrate) {
            this.hashrate = hashrate;
        }

        public Long getRoundHashes() {
            return roundHashes;
        }

        public void setRoundHashes(Long roundHashes) {
            this.roundHashes = roundHashes;
        }

        public String getLastBlockFound() {
            return lastBlockFound;
        }

        public void setLastBlockFound(String lastBlockFound) {
            this.lastBlockFound = lastBlockFound;
        }
}
