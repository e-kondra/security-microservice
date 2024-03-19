package com.autoservice.security.swagger;

public class HTMLResponseMessages {

    public static final String HTTP_200 = "Successful request gets this response code and returns requested data. " +
            "Example data can be seen below";
    public static final String HTTP_201 = "Successful request saves posted data and returns that data with this " +
            "response code. Example data can be seen below";
    public static final String HTTP_204_WITH_DATA = "Successful request deletes requested data and returns that data " +
            "with this response code. Example data can be seen below";
    public static final String HTTP_204_WITHOUT_DATA = "Successful request deletes requested data and returns this " +
            "response code";
    public static final String HTTP_400 = "Unsuccessful request responds with this code. Passed data has errors - " +
            "fields have incorrect values or there are missing fields";
    public static final String HTTP_404 = "Unsuccessful request responds with this code. Requested resource has not " +
            "been found";
    public static final String HTTP_422 = "Unsuccessful request responds with this code. Requested method can't be " +
            "executed " ;
    public static final String HTTP_409 = "Unsuccessful request responds with this code. Passed data is conflicting " +
            "with already existent data";
    public static final String HTTP_500 = "Unsuccessful request responds with this code. Internal server error. " +
            "Check the response headers for information";

}