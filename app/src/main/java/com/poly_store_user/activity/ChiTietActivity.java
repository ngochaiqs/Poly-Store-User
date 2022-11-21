package com.poly_store_user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.nex3z.notificationbadge.NotificationBadge;
import com.poly_store_user.R;
import com.poly_store_user.model.GioHang;
import com.poly_store_user.model.SanPham;
import com.poly_store_user.utils.Utils;

import java.text.DecimalFormat;

public class ChiTietActivity extends AppCompatActivity {
    TextView tensp, giasp, mota;
    Button btnthem;
    ImageView imghinhanh;
    Spinner spinner;
    Toolbar toolbar;
    SanPham sanPham;
    NotificationBadge badge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.poly_store_user.R.layout.activity_chi_tiet);
        initView();
        ActionToolBar();
        initData();
        initControl();
    }

    private void initControl() {
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                themGioHang();

            }
        });
    }

    private void themGioHang() {
        if (Utils.manggiohang.size() > 0) {
            boolean flag = false;
            int soluong = 1;
            for (int i = 0; i < Utils.manggiohang.size(); i++) {
                if (Utils.manggiohang.get(i).getMaspGH() == sanPham.getMaSP()) {
                    Utils.manggiohang.get(i).setSoluongGH(soluong + Utils.manggiohang.get(i).getSoluongGH());
                    long gia = Long.parseLong(sanPham.getGiaSP());
                    Utils.manggiohang.get(i).setGiaspGH(gia);
                    flag = true;
                }
            }
            if (flag == false) {
//                long gia = Long.parseLong(sanPham.getGiaSP());
                GioHang gioHang = new GioHang();
                gioHang.setGiaspGH(Long.parseLong(sanPham.getGiaSP()));
                gioHang.setSoluongGH(soluong);
                gioHang.setMaspGH(sanPham.getMaSP());
                gioHang.setTenspGH(sanPham.getTenSP());
                gioHang.setHinhspGH(sanPham.getHinhAnhSP());
                Utils.manggiohang.add(gioHang);
            }

        } else {
            int soluong = 1;
//            long gia = Long.parseLong(sanPham.getGiaSP());
            GioHang gioHang = new GioHang();
            gioHang.setGiaspGH(Long.parseLong(sanPham.getGiaSP()));
            gioHang.setSoluongGH(soluong);
            gioHang.setMaspGH(sanPham.getMaSP());
            gioHang.setTenspGH(sanPham.getTenSP());
            gioHang.setHinhspGH(sanPham.getHinhAnhSP());
            Utils.manggiohang.add(gioHang);

        }
        int totalItem = 0;
        for (int i=0; i<Utils.manggiohang.size(); i++ ){
            totalItem= totalItem + Utils.manggiohang.get(i).getSoluongGH();

        }
        badge.setText(String.valueOf(totalItem));
    }

    private void initData() {
        sanPham = (SanPham) getIntent().getSerializableExtra("chitiet");
        tensp.setText(sanPham.getTenSP());
        mota.setText(sanPham.getMoTa());
        giasp.setText(sanPham.getGiaSP());
        if (sanPham.getHinhAnhSP().contains("http")){
            Glide.with(getApplicationContext()).load(sanPham.getHinhAnhSP()).into(imghinhanh);
        }else{
            String hinh = Utils.BASE_URL+"images/"+sanPham.getHinhAnhSP();
            Glide.with(getApplicationContext()).load(hinh).into(imghinhanh);
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        giasp.setText("Giá: " + decimalFormat.format(Double.parseDouble(sanPham.getGiaSP())) + " đ");
    }

    private void initView() {
        tensp = findViewById(R.id.txttensp);
        giasp = findViewById(R.id.txtgiasp);
        mota = findViewById(R.id.txtmotachitiet);
        btnthem = findViewById(R.id.btnthemvaogiohang);
        imghinhanh = findViewById(R.id.imageChitiet);
        toolbar = findViewById(R.id.toolbarchitiet);
        badge = findViewById(R.id.menu_sl);
        FrameLayout frameLayoutgiohang = findViewById(R.id.framegiohang);
        frameLayoutgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent giohang = new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(giohang);
            }
        });

        if (Utils.manggiohang != null){
            int totalItem = 0;
            for (int i=0; i<Utils.manggiohang.size(); i++ ){
                totalItem = totalItem + Utils.manggiohang.get(i).getSoluongGH();

            }
            badge.setText(String.valueOf(totalItem));
        }



        }


    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.manggiohang != null){
            int totalItem = 0;
            for (int i=0; i<Utils.manggiohang.size(); i++ ){
                totalItem = totalItem + Utils.manggiohang.get(i).getSoluongGH();

            }
            badge.setText(String.valueOf(totalItem));
        }

    }
}