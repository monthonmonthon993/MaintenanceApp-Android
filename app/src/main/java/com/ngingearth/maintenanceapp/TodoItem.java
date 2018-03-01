package com.ngingearth.maintenanceapp;

/**
 * Created by NgiNG on 7/11/2560.
 */

public class TodoItem {
    public String Id;
    public String Text;
    public String Pass;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

}
