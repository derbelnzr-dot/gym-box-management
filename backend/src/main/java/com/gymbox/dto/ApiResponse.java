package com.gymbox.dto;

import java.util.Map;

public class ApiResponse {

    private String status;
    private String message;
    private Map<String, Object> data;

    public ApiResponse() {
    }

    public ApiResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public static ApiResponse success(String message) {
        return new ApiResponse("success", message);
    }

    public static ApiResponse success(String message, Map<String, Object> data) {
        ApiResponse r = new ApiResponse("success", message);
        r.setData(data);
        return r;
    }

    public static ApiResponse error(String message) {
        return new ApiResponse("error", message);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}