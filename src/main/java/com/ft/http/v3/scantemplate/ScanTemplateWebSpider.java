package com.ft.http.v3.scantemplate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ScanTemplateWebSpider {

    public static class ScanTemplateWebSpiderPaths {
        private String  boostrap;
        private String  excluded;
        private boolean honorRobotDirectives;

        @JsonCreator
        public ScanTemplateWebSpiderPaths(@JsonProperty("boostrap") String boostrap,
                                          @JsonProperty("excluded") String excluded,
                                          @JsonProperty("honorRobotDirectives") boolean honorRobotDirectives) {
            this.boostrap = boostrap;
            this.excluded = excluded;
            this.honorRobotDirectives = honorRobotDirectives;
        }
    }
    public static class ScanTemplateWebSpiderPatterns {
        private String sensitiveContent;
        private String sensitiveField;

        @JsonCreator
        public ScanTemplateWebSpiderPatterns(@JsonProperty("sensitiveContent") String sensitiveContent,
                                             @JsonProperty("sensitiveField") String sensitiveField) {
            this.sensitiveContent = sensitiveContent;
            this.sensitiveField = sensitiveField;
        }
    }
    public static class ScanTemplateWebSpiderPerformance {
        private List<String>    httpDaemonsToSkip;
        private int     maximumDirectoryLevels;
        private int     maximumForeignHosts;
        private int     maximumLinkDepth;
        private int     maximumPages;
        private int     maximumRetries;
        private String  maximumTime;
        private String  responseTimeout;
        private int     threadsPerServer;

        @JsonCreator
        public ScanTemplateWebSpiderPerformance(@JsonProperty("httpDaemonsToSkip") List<String> httpDaemonsToSkip,
                                                @JsonProperty("maximumDirectoryLevels") int maximumDirectoryLevels,
                                                @JsonProperty("maximumForeignHosts") int maximumForeignHosts,
                                                @JsonProperty("maximumLinkDepth") int maximumLinkDepth,
                                                @JsonProperty("maximumPages") int maximumPages,
                                                @JsonProperty("maximumRetries") int maximumRetries,
                                                @JsonProperty("maximumTime") String maximumTime,
                                                @JsonProperty("responseTimeout") String responseTimeout,
                                                @JsonProperty("threadsPerServer") int threadsPerServer) {
            this.httpDaemonsToSkip = httpDaemonsToSkip;
            this.maximumDirectoryLevels = maximumDirectoryLevels;
            this.maximumForeignHosts = maximumForeignHosts;
            this.maximumLinkDepth = maximumLinkDepth;
            this.maximumPages = maximumPages;
            this.maximumRetries = maximumRetries;
            this.maximumTime = maximumTime;
            this.responseTimeout = responseTimeout;
            this.threadsPerServer = threadsPerServer;
        }
    }

    @JsonCreator
    public ScanTemplateWebSpider(@JsonProperty("dontScanMultiUseDevices") boolean dontScanMultiUseDevices,
                                 @JsonProperty("includeQueryStrings") boolean includeQueryStrings,
                                 @JsonProperty("paths") ScanTemplateWebSpiderPaths paths,
                                 @JsonProperty("patterns") ScanTemplateWebSpiderPatterns patterns,
                                 @JsonProperty("performance") ScanTemplateWebSpiderPerformance performance,
                                 @JsonProperty("testCommonUsernamesAndPasswords") boolean testCommonUsernamesAndPasswords,
                                 @JsonProperty("testXssInSingleScan") boolean testXssInSingleScan,
                                 @JsonProperty("userAgent") String userAgent) {
        this.dontScanMultiUseDevices = dontScanMultiUseDevices;
        this.includeQueryStrings = includeQueryStrings;
        this.paths = paths;
        this.patterns = patterns;
        this.performance = performance;
        this.testCommonUsernamesAndPasswords = testCommonUsernamesAndPasswords;
        this.testXssInSingleScan = testXssInSingleScan;
        this.userAgent = userAgent;
    }

    private boolean dontScanMultiUseDevices;
    private boolean includeQueryStrings;
    private ScanTemplateWebSpiderPaths          paths;
    private ScanTemplateWebSpiderPatterns       patterns;
    private ScanTemplateWebSpiderPerformance    performance;
    private boolean testCommonUsernamesAndPasswords;
    private boolean testXssInSingleScan;
    private String  userAgent;
}
