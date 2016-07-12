package com.opalinskiy.ostap.pastebin.interactor.models;

/**
 * Created by Evronot on 12.07.2016.
 */
public class PasteCodeParams {
    private String pasteCode;
    private String name;
    private String syntax;
    private String expiration;
    private String exposure;

    public PasteCodeParams setPasteCode(String pasteCode) {
        this.pasteCode = pasteCode;
        return this;
    }

    public PasteCodeParams setName(String name) {
        this.name = name;
        return this;
    }

    public PasteCodeParams setSyntax(String syntax) {
        this.syntax = syntax;
        return this;
    }

    public PasteCodeParams setExpiration(String expiration) {
        this.expiration = expiration;
        return this;
    }

    public PasteCodeParams setExposure(String exposure) {
        this.exposure = exposure;
        return this;
    }

    public String getPasteCode() {
        return pasteCode;
    }

    public String getName() {
        return name;
    }

    public String getSyntax() {
        return syntax;
    }

    public String getExpiration() {
        return expiration;
    }

    public String getExposure() {
        return exposure;
    }
}
