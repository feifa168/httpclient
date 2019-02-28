package com.ft.http.v3.credential;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SharedCredential {
    @JsonCreator
    public SharedCredential(@JsonProperty("account") Object account,
                            @JsonProperty("description") String description,
                            @JsonProperty("hostRestriction") String hostRestriction,
                            @JsonProperty("name") String name,
                            @JsonProperty("portRestriction") int portRestriction,
                            @JsonProperty("id") int id,
                            @JsonProperty("taskAssignment") String taskAssignment,
                            @JsonProperty("tasks") List<Integer> tasks) {
        this.account = account;
        this.description = description;
        this.hostRestriction = hostRestriction;
        this.name = name;
        this.portRestriction = portRestriction;

        this.id = id;
        this.taskAssignment = taskAssignment;
        this.tasks = tasks;
    }

    private Object account;
    private String description;
    private String hostRestriction;
    private String name;
    private int    portRestriction;

    private int     id;
    private String  taskAssignment;
    private List<Integer> tasks;
}
