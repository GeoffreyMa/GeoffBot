package com.example.geoffbot.Models;

public class ChatModel {
    public String message;
    public boolean isSend;

    public ChatModel(String message, boolean isSend) {
        this.message = message;
        this.isSend = isSend;
    }

    public ChatModel() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message1) {
        this.message = message1;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }
}
