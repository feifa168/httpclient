package com.ft.http.v3.credential;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

// {
//    "id": 14
// }
// {
//    "error": "Internal error."
//}
public class CredentialReturn {
    @JsonCreator
    public CredentialReturn(@JsonProperty("id") int id,
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