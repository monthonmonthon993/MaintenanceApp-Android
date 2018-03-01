package com.ngingearth.maintenanceapp;

/**
 * Created by NgiNG on 24/10/2560.
 */

public class Data {

    String time;
    String temperature;
    String humidity;


    public Data(String time, String temperature, String humidity) {
        this.time = time;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public String getTime() {
        return time;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getHumidity() {
        return humidity;
    }


}
