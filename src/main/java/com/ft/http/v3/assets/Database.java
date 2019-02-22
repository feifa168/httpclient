package com.ft.http.v3.assets;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

// "databases": [
//  {
//      "description": null,
//      "id": -1,
//      "name": "information_schema"
//  },
//  {
//      "description": null,
//      "id": -1,
//      "name": "mysql"
//  }
//  ]
public class Database {

    @JsonCreator
    public Database(@JsonProperty("description") String description,
                    @JsonProperty("id") int id,
                    @JsonProperty("name") String name) {
        this.description = description;
        this.id = id;
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    private String description;
    private int id;
    private String name;
}
