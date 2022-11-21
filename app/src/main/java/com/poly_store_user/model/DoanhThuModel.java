package com.poly_store_user.model;

import java.util.List;

public class DoanhThuModel {
    boolean success;
    String message;
    List<DoanhThu> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DoanhThu> getResult() {
        return result;
    }

    public void setResult(List<DoanhThu> result) {
        this.result = result;
    }
}
