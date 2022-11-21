package com.poly_store_user.utils;

import com.poly_store_user.model.GioHang;
import com.poly_store_user.model.NguoiDung;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    //public static final String BASE_URL = "https://luongngochai.000webhostapp.com/";
    public static final String BASE_URL = "http://172.16.4.139/polystore/";
    public static NguoiDung nguoidung_current = new NguoiDung();
    public static List<GioHang> manggiohang;
    public static List<GioHang> mangmuahang = new ArrayList<>();

    public static String ID_RECEIVED;
    public static final String SENDID = "idsend";
    public static final String RECEIVEDID = "idreceived";
    public static final String MESS = "message";
    public static final String DATETIME = "datetime";
    public static final String PATH_CHAT = "chat";
}
