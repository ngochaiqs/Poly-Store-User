package com.poly_store_user.model;

import java.util.List;

public class NguoiDungModel {
    boolean success;
    String message;
    List<NguoiDung> result;

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

    public List<NguoiDung> getResult() {
        return result;
    }

    public void setResult(List<NguoiDung> result) {
        this.result = result;
    }
}
