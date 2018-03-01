package com.ngingearth.maintenanceapp.Models;

/**
 * Created by Barenz on 2/21/2018.
 */

public class Machine {
    private int id;
    private String name;
    private String code;
    private String image_machine;
    private String image_detail;
    private String url_machine;
    private String url_detail;
    private String type;

    public Machine() {}

    public Machine(int id, String name, String code, String image_machine, String image_detail, String url_machine, String url_detail, String type) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.image_machine = image_machine;
        this.image_detail = image_detail;
        this.url_machine = url_machine;
        this.url_detail = url_detail;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl_machine() {
        return url_machine;
    }

    public void setUrl_machine(String url_machine) {
        this.url_machine = url_machine;
    }

    public String getUrl_detail() {
        return url_detail;
    }

    public void setUrl_detail(String url_detail) {
        this.url_detail = url_detail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImage_machine() {
        return image_machine;
    }

    public void setImage_machine(String image_machine) {
        this.image_machine = image_machine;
    }

    public String getImage_detail() {
        return image_detail;
    }

    public void setImage_detail(String image_detail) {
        this.image_detail = image_detail;
    }
}
