package com.ngingearth.maintenanceapp.Models;

import com.ngingearth.maintenanceapp.Remote.RetrofitClient;
import com.ngingearth.maintenanceapp.RequestInterface;

/**
 * Created by Barenz on 2/22/2018.
 */

public class ApiUtils {
    public static final String BASE_URL_API = "https://barenz.000webhostapp.com/employee_management/api/";

    public static RequestInterface getRequestService() {
        return RetrofitClient.getClient(BASE_URL_API).create(RequestInterface.class);
    }
}
