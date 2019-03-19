package com.ft.http.v3.update;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

//{
//    "success": false,
//    "msg": "文件解压失败！"
//}
//{
//  "success" : false,
//  "msg" : "此更新包不适合当前版本"
//}
// {
//    "error": "File format not correct."
//}
public class OfflineUpdateResult {
    @JsonCreator
    public OfflineUpdateResult(@JsonProperty("success") boolean success,
                               @JsonProperty("msg") String msg,
                               @JsonProperty("error") String error) {
        this.success = success;
        this.msg = msg;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMsg() {
        return msg;
    }

    public String getError() {
        return error;
    }

    private boolean success;
    private String msg;
    private String error;
}
