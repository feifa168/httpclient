package com.ft.http.v3.scantemplate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ScanTemplateDiscoveryPerformance {

    public static class ScanTemplateDiscoveryPerformancePacketsRate {
        private boolean defeatRateLimit;
        private int     maximum;
        private int     minimum;

        @JsonCreator
        public ScanTemplateDiscoveryPerformancePacketsRate(@JsonProperty("defeatRateLimit") boolean defeatRateLimit,
                                                           @JsonProperty("maximum") int maximum,
                                                           @JsonProperty("minimum") int minimum) {
            this.defeatRateLimit = defeatRateLimit;
            this.maximum = maximum;
            this.minimum = minimum;
        }

        public boolean isDefeatRateLimit() {
            return defeatRateLimit;
        }

        public int getMaximum() {
            return maximum;
        }

        public int getMinimum() {
            return minimum;
        }

        public void setDefeatRateLimit(boolean defeatRateLimit) {
            this.defeatRateLimit = defeatRateLimit;
        }

        public void setMaximum(int maximum) {
            this.maximum = maximum;
        }

        public void setMinimum(int minimum) {
            this.minimum = minimum;
        }
    }
    public static class ScanTemplateDiscoveryPerformanceParallelism {
        private int     maximum;
        private int     minimum;

        @JsonCreator
        public ScanTemplateDiscoveryPerformanceParallelism(@JsonProperty("maximum") int maximum,
                                                         @JsonProperty("minimum") int minimum) {
            this.maximum = maximum;
            this.minimum = minimum;
        }

        public int getMaximum() {
            return maximum;
        }

        public void setMaximum(int maximum) {
            this.maximum = maximum;
        }

        public int getMinimum() {
            return minimum;
        }

        public void setMinimum(int minimum) {
            this.minimum = minimum;
        }
    }
    public static class ScanTemplateDiscoveryPerformanceScanDelay {
        private String  maximum;
        private String  minimum;

        @JsonCreator
        public ScanTemplateDiscoveryPerformanceScanDelay(@JsonProperty("maximum") String maximum,
                                                         @JsonProperty("minimum") String minimum) {
            this.maximum = maximum;
            this.minimum = minimum;
        }

        public String getMaximum() {
            return maximum;
        }

        public void setMaximum(String maximum) {
            this.maximum = maximum;
        }

        public String getMinimum() {
            return minimum;
        }

        public void setMinimum(String minimum) {
            this.minimum = minimum;
        }
    }
    public static class ScanTemplateDiscoveryPerformanceTimeout {
        private String  initial;
        private String  maximum;
        private String  minimum;

        @JsonCreator
        public ScanTemplateDiscoveryPerformanceTimeout(@JsonProperty("initial") String initial,
                                                       @JsonProperty("maximum") String maximum,
                                                       @JsonProperty("minimum") String minimum) {
            this.initial = initial;
            this.maximum = maximum;
            this.minimum = minimum;
        }

        public String getInitial() {
            return initial;
        }

        public void setInitial(String initial) {
            this.initial = initial;
        }

        public String getMaximum() {
            return maximum;
        }

        public void setMaximum(String maximum) {
            this.maximum = maximum;
        }

        public String getMinimum() {
            return minimum;
        }

        public void setMinimum(String minimum) {
            this.minimum = minimum;
        }
    }

    @JsonCreator
    public ScanTemplateDiscoveryPerformance(@JsonProperty("packetRate") ScanTemplateDiscoveryPerformancePacketsRate packetRate,
                                            @JsonProperty("parallelism") ScanTemplateDiscoveryPerformanceParallelism parallelism,
                                            @JsonProperty("retryLimit") int retryLimit,
                                            @JsonProperty("scanDelay") ScanTemplateDiscoveryPerformanceScanDelay scanDelay,
                                            @JsonProperty("timeout") ScanTemplateDiscoveryPerformanceTimeout timeout) {
        this.packetRate = packetRate;
        this.parallelism = parallelism;
        this.retryLimit = retryLimit;
        this.scanDelay = scanDelay;
        this.timeout = timeout;
    }

    public ScanTemplateDiscoveryPerformancePacketsRate getPacketRate() {
        return packetRate;
    }

    public void setPacketRate(ScanTemplateDiscoveryPerformancePacketsRate packetRate) {
        this.packetRate = packetRate;
    }

    public ScanTemplateDiscoveryPerformanceParallelism getParallelism() {
        return parallelism;
    }

    public void setParallelism(ScanTemplateDiscoveryPerformanceParallelism parallelism) {
        this.parallelism = parallelism;
    }

    public int getRetryLimit() {
        return retryLimit;
    }

    public void setRetryLimit(int retryLimit) {
        this.retryLimit = retryLimit;
    }

    public ScanTemplateDiscoveryPerformanceScanDelay getScanDelay() {
        return scanDelay;
    }

    public void setScanDelay(ScanTemplateDiscoveryPerformanceScanDelay scanDelay) {
        this.scanDelay = scanDelay;
    }

    public ScanTemplateDiscoveryPerformanceTimeout getTimeout() {
        return timeout;
    }

    public void setTimeout(ScanTemplateDiscoveryPerformanceTimeout timeout) {
        this.timeout = timeout;
    }

    private ScanTemplateDiscoveryPerformancePacketsRate packetRate;
    private ScanTemplateDiscoveryPerformanceParallelism parallelism;
    private int retryLimit;
    private ScanTemplateDiscoveryPerformanceScanDelay   scanDelay;
    private ScanTemplateDiscoveryPerformanceTimeout     timeout;
}
