package com.ngingearth.maintenanceapp;

/**
 * Created by NgiNG on 7/11/2560.
 */

public class UserProfile {
    public String Id;
    public String Name;
    public String Password;
    public String Position;
    public int Code;
    public String Surname;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

}
