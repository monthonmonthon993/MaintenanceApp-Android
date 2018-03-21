package com.ngingearth.maintenanceapp.Models;

import com.ngingearth.maintenanceapp.Remote.RetrofitClient;
import com.ngingearth.maintenanceapp.RequestInterface;

import static com.ngingearth.maintenanceapp.Constants.EMP_API;

/**
 * Created by Barenz on 2/22/2018.
 */

public class ApiUtils {
    public static final String BASE_URL_API = EMP_API;

    public static RequestInterface getRequestService() {
        return RetrofitClient.getClient(BASE_URL_API).create(RequestInterface.class);
    }
}
