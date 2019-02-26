package com.ft.http.v3.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.ft.http.v3.credential.Credential;
import com.ft.http.v3.task.NewTask;

import java.util.List;

@JacksonXmlRootElement(localName = "taskscan")
public class TaskScanConfig {
    @JsonCreator
    public TaskScanConfig(@JsonProperty("task") NewTask task,
                          @JsonProperty("credentials") List<Credential> credentials) {
        this.task = task;
        this.credentials = credentials;
    }

    public NewTask getTask() {
        return task;
    }

    public List<Credential> getCredential() {
        return credentials;
    }

    private NewTask task;
    @JacksonXmlElementWrapper(localName = "credentialsItem")
    @JacksonXmlProperty(localName = "credential")
    private List<Credential> credentials;
}
