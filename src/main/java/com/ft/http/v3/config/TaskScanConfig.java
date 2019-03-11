package com.ft.http.v3.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.ft.http.v3.credential.Credential;
import com.ft.http.v3.task.NewTask;
import com.ft.http.v3.weakpassword.NewCracks;

import java.util.List;

@JacksonXmlRootElement(localName = "taskscan")
public class TaskScanConfig {
    @JsonCreator
    public TaskScanConfig(@JsonProperty("taskconfigs") List<TaskConfig> taskconfigs,
                          @JsonProperty("description") String description,
                          @JsonProperty("engineId") int engineId,
                          @JsonProperty("importance") String importance,
                          @JsonProperty("name") String name,
                          @JsonProperty("scanTemplateId") String scanTemplateId,
                          @JsonProperty("toolcategory") String toolcategory,
                          @JsonProperty("output") String output,
                          @JsonProperty("taskcode") String taskcode) {
        this.taskconfigs = taskconfigs;
        this.description = description;
        this.engineId = engineId;
        this.importance = importance;
        this.name = name;
        this.scanTemplateId = scanTemplateId;
        this.toolcategory = toolcategory;
        this.output = output;
        this.taskcode = taskcode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TaskConfig> getTaskconfigs() {
        return taskconfigs;
    }

    public String getDescription() {
        return description;
    }

    public int getEngineId() {
        return engineId;
    }

    public String getImportance() {
        return importance;
    }

    public String getName() {
        return name;
    }

    public String getScanTemplateId() {
        return scanTemplateId;
    }

    public String getToolcategory() {
        return toolcategory;
    }

    public String getOutput() {
        return output;
    }

    public String getTaskcode() {
        return taskcode;
    }

    @JacksonXmlElementWrapper(localName = "taskconfigItems")
    @JacksonXmlProperty(localName = "taskconfig")
    private List<TaskConfig> taskconfigs;

    private String description;
    private int    engineId;
    private String importance;
    private String name;
    private String scanTemplateId;
    private String toolcategory;
    private String output;
    private String taskcode;
}
