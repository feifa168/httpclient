package com.ft.http.v3.scantemplate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public class ScanTemplateAssetDiscovery {
    private boolean collectWhoisInformation;
    private double  fingerprintMinimumCertainty;
    private int     fingerprintRetries;
    private boolean ipFingerprintingEnabled;
    private boolean sendArpPings;
    private boolean sendIcmpPings;
    @JacksonXmlElementWrapper(localName = "tcpPort")
    @JacksonXmlProperty(localName = "tcpItem")
    private List<Integer> tcpPorts;
    private boolean treatTcpResetAsAsset;
    @JacksonXmlElementWrapper(localName = "udpPort")
    @JacksonXmlProperty(localName = "udpItem")
    private List<Integer> udpPorts;

    @JsonCreator
    public ScanTemplateAssetDiscovery(@JsonProperty("collectWhoisInformation") boolean collectWhoisInformation,
                                      @JsonProperty("fingerprintMinimumCertainty") double fingerprintMinimumCertainty,
                                      @JsonProperty("fingerprintRetries") int fingerprintRetries,
                                      @JsonProperty("ipFingerprintingEnabled") boolean ipFingerprintingEnabled,
                                      @JsonProperty("sendArpPings") boolean sendArpPings,
                                      @JsonProperty("sendIcmpPings") boolean sendIcmpPings,
                                      @JsonProperty("tcpPorts") List<Integer> tcpPorts,
                                      @JsonProperty("treatTcpResetAsAsset") boolean treatTcpResetAsAsset,
                                      @JsonProperty("udpPorts") List<Integer> udpPorts) {
        this.collectWhoisInformation = collectWhoisInformation;
        this.fingerprintMinimumCertainty = fingerprintMinimumCertainty;
        this.fingerprintRetries = fingerprintRetries;
        this.ipFingerprintingEnabled = ipFingerprintingEnabled;
        this.sendArpPings = sendArpPings;
        this.sendIcmpPings = sendIcmpPings;
        this.tcpPorts = tcpPorts;
        this.treatTcpResetAsAsset = treatTcpResetAsAsset;
        this.udpPorts = udpPorts;
    }

    public boolean isCollectWhoisInformation() {
        return collectWhoisInformation;
    }

    public double getFingerprintMinimumCertainty() {
        return fingerprintMinimumCertainty;
    }

    public int getFingerprintRetries() {
        return fingerprintRetries;
    }

    public boolean isIpFingerprintingEnabled() {
        return ipFingerprintingEnabled;
    }

    public boolean isSendArpPings() {
        return sendArpPings;
    }

    public boolean isSendIcmpPings() {
        return sendIcmpPings;
    }

    public List<Integer> getTcpPorts() {
        return tcpPorts;
    }

    public boolean isTreatTcpResetAsAsset() {
        return treatTcpResetAsAsset;
    }

    public List<Integer> getUdpPorts() {
        return udpPorts;
    }

    public void setCollectWhoisInformation(boolean collectWhoisInformation) {
        this.collectWhoisInformation = collectWhoisInformation;
    }

    public void setFingerprintMinimumCertainty(double fingerprintMinimumCertainty) {
        this.fingerprintMinimumCertainty = fingerprintMinimumCertainty;
    }

    public void setFingerprintRetries(int fingerprintRetries) {
        this.fingerprintRetries = fingerprintRetries;
    }

    public void setIpFingerprintingEnabled(boolean ipFingerprintingEnabled) {
        this.ipFingerprintingEnabled = ipFingerprintingEnabled;
    }

    public void setSendArpPings(boolean sendArpPings) {
        this.sendArpPings = sendArpPings;
    }

    public void setSendIcmpPings(boolean sendIcmpPings) {
        this.sendIcmpPings = sendIcmpPings;
    }

    public void setTcpPorts(List<Integer> tcpPorts) {
        this.tcpPorts = tcpPorts;
    }

    public void setTreatTcpResetAsAsset(boolean treatTcpResetAsAsset) {
        this.treatTcpResetAsAsset = treatTcpResetAsAsset;
    }

    public void setUdpPorts(List<Integer> udpPorts) {
        this.udpPorts = udpPorts;
    }
}
