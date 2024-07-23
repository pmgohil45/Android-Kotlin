package com.example.contact.contact.recycler_adpter;

public class Contact_recycler{
    private String name,number,email,organization,address,web_address,notes,nickName;

    public Contact_recycler(int i, String name, String number, String email, String organization, String address, String web_address, String notes, String nickName) {
        this.name = name;
        this.number = number;
        this.email = email;
        this.organization = organization;
        this.address = address;
        this.web_address = web_address;
        this.notes = notes;
        this.nickName = nickName;
    }

    public Contact_recycler(String string, String string1) {

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWeb_address() {
        return web_address;
    }

    public void setWeb_address(String web_address) {
        this.web_address = web_address;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
