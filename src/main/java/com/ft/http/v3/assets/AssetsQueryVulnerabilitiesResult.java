package com.ft.http.v3.assets;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ft.http.v3.task.Page;
import com.ft.http.v3.task.PageAndResources;

import java.util.List;

//  {
//      "id": "windows-hotfix-ms07-029",
//      "title": "MS07-029: Windows DNS RPC接口存在漏洞可允许远程代码执行(935966)",
//      "cvss": 10,
//      "cve": "CVE-2007-1748",
//      "publishDate": "2007-05-08",
//      "riskScore": 909,
//      "severity": 10,
//      "exploit": 5,
//      "malware": 0,
//      "instances": 1
//  }
public class AssetsQueryVulnerabilitiesResult implements PageAndResources<AssetsScanVulnerabilities> {
    @JsonCreator
    public AssetsQueryVulnerabilitiesResult(@JsonProperty("page") Page page,
                            @JsonProperty("resources") List<AssetsScanVulnerabilities> resources) {
        this.page = page;
        this.resources = resources;
    }

    public Page getPage() {
        return page;
    }

    public List<AssetsScanVulnerabilities> getResources() {
        return resources;
    }

    private Page page;
    private List<AssetsScanVulnerabilities> resources;
}
