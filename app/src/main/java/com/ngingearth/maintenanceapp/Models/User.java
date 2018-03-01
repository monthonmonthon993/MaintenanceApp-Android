package com.ngingearth.maintenanceapp.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Barenz on 2/21/2018.
 */

public class User {
    @SerializedName("userid")
    private String userid;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("emp_image")
    private String empImage;

    @SerializedName("department")
    private String department;

    @SerializedName ("image_url")
    private String imageUrl;


    public User(String userid, String username, String password, String empImage, String department, String imageUrl) {
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.empImage = empImage;
        this.department = department;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserName() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUserid() {
        return userid;
    }

    public String getEmpImage() {
        return empImage;
    }

    public String getDepartment() {
        return department;
    }

    public void setUserId(String userId) {
        this.userid = userId;
    }

    public void setUserName(String name) {
        this.username = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmpImage(String imageUrl) {
        this.empImage = imageUrl;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

}
