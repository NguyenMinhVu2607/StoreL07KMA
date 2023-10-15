package com.example.storel07.Model;

public class InfoUser {
    String address;
    String mail;
    String name;

    String number;

    public InfoUser() {
    }

    public InfoUser(String address, String mail, String name, String number) {
        this.address = address;
        this.mail = mail;
        this.name = name;
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}