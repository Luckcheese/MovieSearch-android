package com.luckcheese.moviesearch.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BaseRequestResponse implements Serializable {

    @SerializedName("Response")
    private boolean success;

    @SerializedName("Error")
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
