package com.example.lab3;

public class Message {
    public String message;
    public boolean isSent;
    long id;


    public Message(String message, boolean isSent) {
        System.out.println("Inside const message ----> "+message);
        System.out.println("Inside const isSent ----> "+isSent);
        this.message = message;
        this.isSent = isSent;
    }

    public Message(long id, String message, boolean isSent) {
        this.message = message;
        this.isSent = isSent;
        this.id = id;
    }

    public Message() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                ", isSent=" + isSent +
                ", id=" + id +
                '}';
    }
}
