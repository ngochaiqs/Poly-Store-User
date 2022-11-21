package com.poly_store_user.model;

public class LoaiSP {
    int maLoai;

    public LoaiSP(String tenLoai, String hinhAnh) {
        this.tenLoai = tenLoai;
        this.hinhAnh = hinhAnh;
    }

    String tenLoai;
    String hinhAnh;

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}
