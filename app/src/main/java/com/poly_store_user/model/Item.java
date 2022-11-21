package com.poly_store_user.model;

public class Item {
    int maCTSP;
    String tenSP;
    int soLuong;
    String hinhAnhSP;

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getHinhAnhSP() {
        return hinhAnhSP;
    }

    public void setHinhAnhSP(String hinhAnh) {
        this.hinhAnhSP = hinhAnhSP;
    }

    public int getMaSP() {
        return maCTSP;
    }

    public void setMaSP(int maSP) {
        this.maCTSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }
}
