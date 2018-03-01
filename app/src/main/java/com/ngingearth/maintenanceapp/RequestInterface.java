package com.ngingearth.maintenanceapp;

import com.ngingearth.maintenanceapp.Models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by Barenz on 2/21/2018.
 */

public interface RequestInterface {

    @Headers("Content-Type:application/json")
    @GET("get_all_employee.php")
    Call<List<User>> getEmployee();
}
