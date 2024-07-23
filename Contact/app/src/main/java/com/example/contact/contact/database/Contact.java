package com.example.contact.contact.database;

public class Contact
{
    int id;
    String name,phone,email,organization,address,webaddress,notes,nickname;

    public Contact(String name, String phone, String email, String organization, String address, String webaddress, String notes, String nickname) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.organization = organization;
        this.address = address;
        this.webaddress = webaddress;
        this.notes = notes;
        this.nickname = nickname;
    }

    public Contact(int id, String name, String phone, String email, String organization, String address, String webaddress, String notes, String nickname) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.organization = organization;
        this.address = address;
        this.webaddress = webaddress;
        this.notes = notes;
        this.nickname = nickname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getWebaddress() {
        return webaddress;
    }

    public void setWebaddress(String webaddress) {
        this.webaddress = webaddress;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
