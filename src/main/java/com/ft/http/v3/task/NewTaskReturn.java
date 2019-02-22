package com.ft.http.v3.task;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NewTaskReturn {
    @JsonCreator
    public NewTaskReturn(@JsonProperty("id") int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    private int id;
}
