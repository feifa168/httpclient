package com.ft.http.v3.assets;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

// "vulnerabilities": {
//     "critical": 8,
//     "exploits": 11,
//     "malwareKits": 0,
//     "moderate": 4,
//     "severe": 14,
//     "total": 26
// }
public class Vulnerabilities {
    @JsonCreator
    public Vulnerabilities(@JsonProperty("critical") int critical,
                           @JsonProperty("exploits") int exploits,
                           @JsonProperty("malwareKits") int malwareKits,
                           @JsonProperty("moderate") int moderate,
                           @JsonProperty("severe") int severe,
                           @JsonProperty("total") int total) {
        this.critical = critical;
        this.exploits = exploits;
        this.malwareKits = malwareKits;
        this.moderate = moderate;
        this.severe = severe;
        this.total = total;
    }

    public int getCritical() {
        return critical;
    }

    public int getExploits() {
        return exploits;
    }

    public int getMalwareKits() {
        return malwareKits;
    }

    public int getModerate() {
        return moderate;
    }

    public int getSevere() {
        return severe;
    }

    public int getTotal() {
        return total;
    }

    private int critical;
    private int exploits;
    private int malwareKits;
    private int moderate;
    private int severe;
    private int total;
}
