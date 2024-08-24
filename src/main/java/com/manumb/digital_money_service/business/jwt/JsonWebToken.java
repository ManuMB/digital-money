package com.manumb.digital_money_service.business.jwt;

public class JsonWebToken {

    private String jwt;

    public JsonWebToken() {
    }

    public JsonWebToken(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

}
