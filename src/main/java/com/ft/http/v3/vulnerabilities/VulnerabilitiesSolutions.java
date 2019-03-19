package com.ft.http.v3.vulnerabilities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

//{
//    "resources": [
//        "generic-icmp-timestamp-disable-hpux",
//        "generic-icmp-timestamp-disable-ios",
//        "generic-icmp-timestamp-disable-irix",
//        "generic-icmp-timestamp-disable-linux",
//        "generic-icmp-timestamp-disable-nt",
//        "generic-icmp-timestamp-disable-openbsd",
//        "generic-icmp-timestamp-disable-pix",
//        "generic-icmp-timestamp-disable-solaris",
//        "generic-icmp-timestamp-disable-via-firewall",
//        "generic-icmp-timestamp-disable-vista_2k8",
//        "generic-icmp-timestamp-disable-w2k",
//        "generic-icmp-timestamp-disable-xp-2k3"
//    ]
//}
public class VulnerabilitiesSolutions {

    @JsonCreator
    public VulnerabilitiesSolutions(@JsonProperty("resources") List<String> resources) {
        this.resources = resources;
    }

    public List<String> getResources() {
        return resources;
    }

    private List<String> resources;
}
