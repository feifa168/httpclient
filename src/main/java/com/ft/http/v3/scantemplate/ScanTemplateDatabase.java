package com.ft.http.v3.scantemplate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public class ScanTemplateDatabase {
    @JsonCreator
    public ScanTemplateDatabase(@JsonProperty("db2") String db2,
                    @JsonProperty("oracle") List<String> oracle,
                    @JsonProperty("postgres") String postgres) {
        this.db2 = db2;
        this.oracle = oracle;
        this.postgres = postgres;
    }

    private String db2;
    @JacksonXmlElementWrapper(localName = "oracles")
    @JacksonXmlProperty(localName = "item")
    private List<String> oracle;
    private String postgres;
}
