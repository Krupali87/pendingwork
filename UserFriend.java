package com.app.gpsphonelocator_new;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "userfriend_table")
public class UserFriend {

    @PrimaryKey
    public int uid;

    public String name;
    public String securityCode;
    public String phoneNumber;

    public UserFriend(String name, String securityCode, String phoneNumber) {
        this.name = name;
        this.securityCode = securityCode;
        this.phoneNumber = phoneNumber;
    }
    public String getname() {
        return name;
    }
    public void  setname(String name) {
        this.name = name;
    }

    public String getsecurityCode() {
        return securityCode;
    }
    public void  setsecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getphoneNumber() {
        return phoneNumber;
    }

    public void  setphoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return uid;
    }

    public void setId(int id) {
        this.uid = id;
    }







}
