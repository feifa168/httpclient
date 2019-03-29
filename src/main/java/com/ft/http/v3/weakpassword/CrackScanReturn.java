package com.ft.http.v3.weakpassword;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

// {
//    "error": "Crack task name already exists."
// }
// 14
public class CrackScanReturn {

    @JsonCreator
    public CrackScanReturn(@JsonProperty("id") int id,
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
