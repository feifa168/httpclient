package com.ft.http.v3.task;

import com.ft.http.v3.scan.ScanResult;

import java.util.List;

public interface PageAndResources<T> {
    Page getPage();
    List<T> getResources();
}
