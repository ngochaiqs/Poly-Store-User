package com.poly_store_user.retrofit;

import com.poly_store_user.model.DoanhThuModel;
import com.poly_store_user.model.DonHangModel;
import com.poly_store_user.model.LoaiSPModel;
import com.poly_store_user.model.MessageModel;
import com.poly_store_user.model.NguoiDungModel;
import com.poly_store_user.model.SanPhamModel;
import com.poly_store_user.model.ThongKeModel;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiBanHang {
    @GET("getloaisp.php")
    Observable<LoaiSPModel> getLoaiSp();

    @GET("getsanpham.php")
    Observable<SanPhamModel> getSanPham();

    @GET("thongke.php")
    Observable<ThongKeModel> getThongKe();

    @POST("chitietdonhang.php")
    @FormUrlEncoded
    Observable<SanPhamModel> getSanPham(
        @Field("page") int page,
        @Field("maLoai") int maLoai
    );

    @POST("doanhThu.php")
    @FormUrlEncoded
    Observable<DoanhThuModel> getDoanhThu(
            @Field("tuNgay") String tuNgay,
            @Field("denNgay") String denNgay
    );

    @POST("dangky.php")
    @FormUrlEncoded
    Observable<NguoiDungModel> dangKy(
            @Field("tenND") String tenND,
            @Field("email") String email,
            @Field("matKhau") String matKhau,
            @Field("SDT") String SDT,
            @Field("diaChi") String diaChi,
            @Field("uid") String uid
    );

    @POST("dangnhap.php")
    @FormUrlEncoded
    Observable<NguoiDungModel> dangNhap(
            @Field("email") String email,
            @Field("matKhau") String matKhau
    );

    @POST("quenmatkhau.php")
    @FormUrlEncoded
    Observable<NguoiDungModel> quenMK(
            @Field("email") String email

    );

    @POST("donhang.php")
    @FormUrlEncoded
    Observable<NguoiDungModel> datHang(
            @Field("email") String email,
            @Field("SDT") String SDT,
            @Field("tongTien") String tongTien,
            @Field("maND") int maND,
            @Field("tenND") String tenND,
            @Field("diaChi") String diaChi,
            @Field("soLuong") int soLuong,
            @Field("chiTiet") String chiTiet
    );

    @POST("updateorder.php")
    @FormUrlEncoded
    Observable<MessageModel> updateOrder(
            @Field("maDH") int maDH,
            @Field("trangThai") int trangThai
    );
    @POST("xemdonhang.php")
    @FormUrlEncoded
    Observable<DonHangModel> xemDonHang(
            @Field("maND") int maND,
            @Field("trangThai") int trangThai

    );

    @POST("timkiem.php")
    @FormUrlEncoded
    Observable<SanPhamModel> timKiem(
            @Field("timKiem") String timKiem

    );


    @POST("xoa.php")
    @FormUrlEncoded
    Observable<SanPhamModel> xoaSanPham(
            @Field("maSP") int maSP

    );

    @POST("doimatkhau.php")
    @FormUrlEncoded
    Observable<NguoiDungModel> doiMatKhau(
            @Field("email") String email,
            @Field("matKhau") String matKhau,
            @Field("matKhauMoi") String matKhauMoi

    );

    @POST("themsp.php")
    @FormUrlEncoded
    Observable<MessageModel> themSP(
            @Field("tenSP") String tenSP,
            @Field("giaSP") String giaSP,
            @Field("hinhAnhSP") String hinhAnhSP,
            @Field("moTa") String moTa,
            @Field("maLoai") int maLoai
    );

    @POST("updatesp.php")
    @FormUrlEncoded
    Observable<MessageModel> updatesp(
            @Field("tenSP") String tenSP,
            @Field("giaSP") String giaSP,
            @Field("hinhAnhSP") String hinhAnhSP,
            @Field("moTa") String moTa,
            @Field("maLoai") int maLoai,
            @Field("maSP") int maSP

    );

    @POST("gettoken.php")
    @FormUrlEncoded
    Observable<NguoiDungModel> getToken(
            @Field("status") int status
    );

    @POST("updatetoken.php")
    @FormUrlEncoded
    Observable<MessageModel> updateToken(
            @Field("maND") int maND,
            @Field("token") String token
    );
    @Multipart
    @POST("upload.php")
    Call<MessageModel> uploadFile(
            @Part MultipartBody.Part file
    );
}
