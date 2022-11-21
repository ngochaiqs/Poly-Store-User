package com.poly_store_user.model.EventBus;

import com.poly_store_user.model.SanPham;

public class SuaXoaEvent {
    SanPham sanPham;

    public SuaXoaEvent(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }
}
