package com.opalinskiy.ostap.pastebin.interactor.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Evronot on 08.06.2016.
 */
public class User implements Serializable
{
    @Expose
    private String userKey;
    @SerializedName("content")
    private String[] content;
    @SerializedName("user_name")
    private String userName;
    @SerializedName("user_expiration")
    private String userExpiration;
    @SerializedName("user_format_short")
    private String userFormatShort;
    @SerializedName("user_location")
    private String userLocation;
    @SerializedName("user_private")
    private String userPrivate;
    @SerializedName("user_email")
    private String userEmail;
    @SerializedName("user_account_type")
    private String userAccountType;
    @SerializedName("user_avatar_url")
    private String userAvatarUrl;
    @SerializedName("user_web_site")
    private String userWebsite;

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String[] getContent ()
    {
        return content;
    }

    public void setContent (String[] content)
    {
        this.content = content;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getUserExpiration()
    {
        return userExpiration;
    }

    public void setUserExpiration(String userExpiration)
    {
        this.userExpiration = userExpiration;
    }

    public String getUserFormatShort()
    {
        return userFormatShort;
    }

    public void setUserFormatShort(String userFormatShort)
    {
        this.userFormatShort = userFormatShort;
    }

    public String getUserLocation()
    {
        return userLocation;
    }

    public void setUserLocation(String userLocation)
    {
        this.userLocation = userLocation;
    }

    public String getUserPrivate()
    {
        return userPrivate;
    }

    public void setUserPrivate(String userPrivate)
    {
        this.userPrivate = userPrivate;
    }

    public String getUserEmail()
    {
        return userEmail;
    }

    public void setUserEmail(String userEmail)
    {
        this.userEmail = userEmail;
    }

    public String getUserAccountType()
    {
        return userAccountType;
    }

    public void setUserAccountType(String userAccountType)
    {
        this.userAccountType = userAccountType;
    }

    public String getUserAvatarUrl()
    {
        return userAvatarUrl;
    }

    public void setUserAvatarUrl(String userAvatarUrl)
    {
        this.userAvatarUrl = userAvatarUrl;
    }

    public String getUserWebsite()
    {
        return userWebsite;
    }

    public void setUserWebsite(String userWebsite)
    {
        this.userWebsite = userWebsite;
    }

    @Override
    public String toString() {
        return "User{" +
                "content=" + Arrays.toString(content) +
                ", userName='" + userName + '\'' +
                ", userExpiration='" + userExpiration + '\'' +
                ", userFormatShort='" + userFormatShort + '\'' +
                ", userLocation='" + userLocation + '\'' +
                ", userPrivate='" + userPrivate + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userAccountType='" + userAccountType + '\'' +
                ", userAvatarUrl='" + userAvatarUrl + '\'' +
                ", userWebsite='" + userWebsite + '\'' +
                '}';
    }
}
