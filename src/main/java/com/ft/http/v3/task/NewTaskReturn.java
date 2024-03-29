package com.ft.http.v3.task;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

//{
//    "error": "Internal Server Error"
//}
//{
//    "id": 49
//}
public class NewTaskReturn {
    @JsonCreator
    public NewTaskReturn(@JsonProperty("id") int id,
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
