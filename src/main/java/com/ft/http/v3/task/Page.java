package com.ft.http.v3.task;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Page {

    @JsonCreator
    public Page(@JsonProperty("number") int number,
                @JsonProperty("size") int size,
                @JsonProperty("totalPages") int totalPages,
                @JsonProperty("totalResources") int totalResources) {
        this.number = number;
        this.size = size;
        this.totalPages = totalPages;
        this.totalResources = totalResources;
    }

    public int getNumber() {
        return number;
    }

    public int getSize() {
        return size;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalResources() {
        return totalResources;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setTotalResources(int totalResources) {
        this.totalResources = totalResources;
    }

    private int number;
    private int size;
    private int totalPages;
    private int totalResources;
}
