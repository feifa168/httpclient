package com.ft.http.v3.scan;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

// {
//    "id": 14
// }
// {
//    "error": "Bad Request"
//}

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewScanReturn {

    @JsonCreator
    public NewScanReturn(@JsonProperty("id") int id,
                         @JsonProperty("error") String error) {
        this.id = id;
        this.error = error;
    }

    public int getId() {
        return id;
    }

    public String getError() {
        return error;
    }

    private int id;
    private String error;
}
