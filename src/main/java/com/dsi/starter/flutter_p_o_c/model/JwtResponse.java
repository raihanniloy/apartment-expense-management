package com.dsi.starter.flutter_p_o_c.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class JwtResponse implements Serializable {
    @JsonProperty("status")
    private String status;
    @JsonProperty("jwt")
    private String jwt;
    @JsonProperty("admin")
    private boolean admin;

    @JsonProperty("error")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;

    public JwtResponse(String status, String jwt, boolean admin, String error) {
        this.status = status;
        this.jwt = jwt;
        this.admin = admin;
        this.error = error;
    }

    public JwtResponse(){

    }

    public String getJwt(){
        return jwt;
    }

    public boolean isAdmin() {
        return admin;
    }

    public String getError() {
        return error;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "JwtResponse{" +
                "status='" + status + '\'' +
                ", jwt='" + jwt + '\'' +
                ", admin=" + admin +
                ", error='" + error + '\'' +
                '}';
    }
}
