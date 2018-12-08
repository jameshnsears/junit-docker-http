package com.github.jameshnsears.docker.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Message {
    @SerializedName("message")
    @Expose
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}
