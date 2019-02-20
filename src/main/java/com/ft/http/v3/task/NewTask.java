package com.ft.http.v3.task;

// {
//   "description": "",
//   "engineId": 3,
//   "importance": "normal",
//   "name": "SCAN_56_2019-02-19 11:14:21",
//   "scan": {
//     "assets": {
//       "includedTargets": {
//         "addresses": ["172.16.39.152"]
//       }
//     }
//   },
//   "scanTemplateId": "full-audit-without-web-spider"
// }

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewTask {

    public static class Scan {

        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class Assets {

            public static class Targets {
                @JsonCreator
                public Targets(@JsonProperty("addresses") List<String> addresses) {
                    this.addresses = addresses;
                }

                private List<String> addresses;
            }

            @JsonCreator
            public Assets(@JsonProperty("includedTargets") Targets includedTargets,
                          @JsonProperty("excludedTargets") Targets excludedTargets) {
                this.includedTargets = includedTargets;
                this.excludedTargets = excludedTargets;
            }

            private Targets includedTargets;
            private Targets excludedTargets;
        }

        @JsonCreator
        public Scan(@JsonProperty("assets") Assets assets) {
            this.assets = assets;
        }

        private Assets assets;
    }

    @JsonCreator
    public NewTask(@JsonProperty("description") String description,
                   @JsonProperty("engineId") int engineId,
                   @JsonProperty("importance") String importance,
                   @JsonProperty("name") String name,
                   @JsonProperty("scan") Scan scan,
                   @JsonProperty("connection") Connection connection,
                   @JsonProperty("scanTemplateId") String scanTemplateId) {
        this.description = description;
        this.engineId = engineId;
        this.importance = importance;
        this.name = name;
        this.scan = scan;
        this.connection = connection;
        this.scanTemplateId = scanTemplateId;
    }

//    @JsonCreator
//    public NewTask(@JsonProperty("description") String description,
//                   @JsonProperty("engineId") int engineId,
//                   @JsonProperty("importance") String importance,
//                   @JsonProperty("name") String name,
//                   @JsonProperty("connection") Connection connection,
//                   @JsonProperty("scanTemplateId") String scanTemplateId) {
//        this.description = description;
//        this.engineId = engineId;
//        this.importance = importance;
//        this.name = name;
//        this.connection = connection;
//        this.scanTemplateId = scanTemplateId;
//    }

    public static class Connection {
        @JsonCreator
        public Connection(@JsonProperty("id") int id,
                          @JsonProperty("name") String name,
                          @JsonProperty("type") String type) {
            this.id = id;
            this.name = name;
            this.type = type;
        }

        private int id;
        private String name;
        private String type;
    }

    private String description;
    private int    engineId;
    private String importance;
    private String name;
    private Scan   scan;
    private Connection connection;
    private String scanTemplateId;
}
