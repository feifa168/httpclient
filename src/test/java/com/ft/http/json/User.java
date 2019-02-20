package com.ft.http.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


//@JsonInclude(JsonInclude.Include.NON_NULL)
class RoleList {
    private int Roleid;
    private String name;

    public RoleList() {
    }

    public RoleList(int roleid, String name) {
        Roleid = roleid;
        this.name = name;
    }

//    @JsonCreator
//    public RoleList(@JsonProperty("Roleid") int roleid,
//                    @JsonProperty("name") String name) {
//        Roleid = roleid;
//        this.name = name;
//    }

    @Override
    public String toString() {
        StringBuilder txt = new StringBuilder(64);
        txt.append("{")
                .append("Roleid:").append(Roleid)
                .append(", name:").append(name)
                .append("}");
        return txt.toString();
    }

    public int getRoleid() {
        return Roleid;
    }

    public void setRoleid(int roleid) {
        Roleid = roleid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    @JsonCreator
    public User(@JsonProperty("UserID") int userID,
                @JsonProperty("LoginName") String loginName,
                @JsonProperty("IsDelete") boolean isDelete,
                @JsonProperty("rol") RoleList[] rol) {
        UserID = userID;
        LoginName = loginName;
        IsDelete = isDelete;
        this.rol = rol;
    }

    @Override
    public String toString() {
        StringBuilder txt = new StringBuilder(128);
        txt.append("{")
                .append("userID:").append(UserID)
                .append(", LoginName:").append(LoginName)
                .append(", IsDelete:").append(IsDelete)
                .append(", rol:").append(rol)
                .append("}");
        return txt.toString();
    }

    private int UserID;
    private String LoginName;
    private boolean IsDelete;
    private RoleList[] rol;
}