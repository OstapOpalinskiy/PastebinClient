package com.opalinskiy.ostap.pastebin.interactor.models;


import com.google.gson.annotations.SerializedName;

public class Paste {

    @SerializedName("paste_format_short")
    private String pasteFormatShort;
    @SerializedName("paste_hits")
    private String pasteHits;
    @SerializedName("paste_url")
    private String pasteUrl;
    @SerializedName("paste_format_long")
    private String pasteFormatLong;
    @SerializedName("paste_key")
    private String pasteKey;
    @SerializedName("paste_size")
    private String pasteSize;
    @SerializedName("paste_date")
    private String pasteDate;
    @SerializedName("paste_private")
    private String pastePrivate;
    @SerializedName("paste_title")
    private String pasteTitle;
    @SerializedName("paste_expire_date")
    private String pasteExpireDate;

    public String getPasteFormatShort()
    {
        return pasteFormatShort;
    }

    public void setPasteFormatShort(String pasteFormatShort)
    {
        this.pasteFormatShort = pasteFormatShort;
    }

    public String getPasteHits()
    {
        return pasteHits;
    }

    public void setPasteHits(String pasteHits)
    {
        this.pasteHits = pasteHits;
    }

    public String getPasteUrl()
    {
        return pasteUrl;
    }

    public void setPasteUrl(String pasteUrl)
    {
        this.pasteUrl = pasteUrl;
    }

    public String getPasteFormatLong()
    {
        return pasteFormatLong;
    }

    public void setPasteFormatLong(String pasteFormatLong)
    {
        this.pasteFormatLong = pasteFormatLong;
    }

    public String getPasteKey()
    {
        return pasteKey;
    }

    public void setPasteKey(String pasteKey)
    {
        this.pasteKey = pasteKey;
    }

    public String getPasteSize()
    {
        return pasteSize;
    }

    public void setPasteSize(String pasteSize)
    {
        this.pasteSize = pasteSize;
    }

    public String getPasteDate()
    {
        return pasteDate;
    }

    public void setPasteDate(String pasteDate)
    {
        this.pasteDate = pasteDate;
    }

    public String getPastePrivate()
    {
        return pastePrivate;
    }

    public void setPastePrivate(String pastePrivate)
    {
        this.pastePrivate = pastePrivate;
    }

    public String getPasteTitle()
    {
        return pasteTitle;
    }

    public void setPasteTitle(String pasteTitle)
    {
        this.pasteTitle = pasteTitle;
    }

    public String getPasteExpireDate()
    {
        return pasteExpireDate;
    }

    public void setPasteExpireDate(String pasteExpireDate)
    {
        this.pasteExpireDate = pasteExpireDate;
    }

    @Override
    public String toString()
    {
        return "pasteFormatShort = "+ pasteFormatShort +", pasteHits = "+ pasteHits +", pasteUrl = "+ pasteUrl +", pasteFormatLong = "+ pasteFormatLong +", pasteKey = "+ pasteKey +", pasteSize = "+ pasteSize +", pasteDate = "+ pasteDate +", pastePrivate = "+ pastePrivate +", pasteTitle = "+ pasteTitle +", pasteExpireDate = "+ pasteExpireDate +"";
    }
}