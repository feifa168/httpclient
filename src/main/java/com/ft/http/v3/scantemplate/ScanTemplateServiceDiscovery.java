package com.ft.http.v3.scantemplate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ScanTemplateServiceDiscovery {
    public static class ScanTemplateServiceDiscoveryTcp {
        @JsonCreator
        public ScanTemplateServiceDiscoveryTcp(@JsonProperty("additionalPorts") List<String> additionalPorts,
                                               @JsonProperty("excludedPorts") List<String> excludedPorts,
                                               @JsonProperty("method") String method,
                                               @JsonProperty("ports") String ports) {
            this.additionalPorts = additionalPorts;
            this.excludedPorts = excludedPorts;
            this.method = method;
            this.ports = ports;
        }

        public List<String> getAdditionalPorts() {
            return additionalPorts;
        }

        public void setAdditionalPorts(List<String> additionalPorts) {
            this.additionalPorts = additionalPorts;
        }

        public List<String> getExcludedPorts() {
            return excludedPorts;
        }

        public void setExcludedPorts(List<String> excludedPorts) {
            this.excludedPorts = excludedPorts;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getPorts() {
            return ports;
        }

        public void setPorts(String ports) {
            this.ports = ports;
        }

        private List<String>    additionalPorts;
        private List<String>    excludedPorts;
        private String          method;
        private String          ports;
    }
    public static class ScanTemplateServiceDiscoveryUdp {
        private List<String>    additionalPorts;
        private List<String>    excludedPorts;
        private String          ports;

        @JsonCreator
        public ScanTemplateServiceDiscoveryUdp(@JsonProperty("additionalPorts") List<String> additionalPorts,
                                               @JsonProperty("excludedPorts") List<String> excludedPorts,
                                               @JsonProperty("ports") String ports) {
            this.additionalPorts = additionalPorts;
            this.excludedPorts = excludedPorts;
            this.ports = ports;
        }

        public List<String> getAdditionalPorts() {
            return additionalPorts;
        }

        public void setAdditionalPorts(List<String> additionalPorts) {
            this.additionalPorts = additionalPorts;
        }

        public List<String> getExcludedPorts() {
            return excludedPorts;
        }

        public void setExcludedPorts(List<String> excludedPorts) {
            this.excludedPorts = excludedPorts;
        }

        public String getPorts() {
            return ports;
        }

        public void setPorts(String ports) {
            this.ports = ports;
        }
    }

    @JsonCreator
    public ScanTemplateServiceDiscovery(@JsonProperty("serviceNameFile") String serviceNameFile,
                                        @JsonProperty("tcp") ScanTemplateServiceDiscoveryTcp tcp,
                                        @JsonProperty("udp") ScanTemplateServiceDiscoveryUdp udp) {
        this.serviceNameFile = serviceNameFile;
        this.tcp = tcp;
        this.udp = udp;
    }

    public String getServiceNameFile() {
        return serviceNameFile;
    }

    public void setServiceNameFile(String serviceNameFile) {
        this.serviceNameFile = serviceNameFile;
    }

    public ScanTemplateServiceDiscoveryTcp getTcp() {
        return tcp;
    }

    public void setTcp(ScanTemplateServiceDiscoveryTcp tcp) {
        this.tcp = tcp;
    }

    public ScanTemplateServiceDiscoveryUdp getUdp() {
        return udp;
    }

    public void setUdp(ScanTemplateServiceDiscoveryUdp udp) {
        this.udp = udp;
    }

    private String serviceNameFile;
    private ScanTemplateServiceDiscoveryTcp tcp;
    private ScanTemplateServiceDiscoveryUdp udp;
}
