package com.ft.http.v3.buildJsonObj;

import java.io.IOException;

public interface BuildJsonObj<T> {
    public T buildObj(byte[] jsonByte, String className) throws ClassNotFoundException, IOException;
}
