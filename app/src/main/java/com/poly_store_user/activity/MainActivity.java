package com.poly_store_user.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.nex3z.notificationbadge.NotificationBadge;
import com.poly_store_user.R;
import com.poly_store_user.adapter.LoaiSPAdapter;
import com.poly_store_user.adapter.SanPhamAdapter;
import com.poly_store_user.model.LoaiSP;
import com.poly_store_user.model.NguoiDung;
import com.poly_store_user.model.NotiSendData;
import com.poly_store_user.model.SanPham;
import com.poly_store_user.retrofit.ApiBanHang;
import com.poly_store_user.retrofit.ApiPushNofication;
import com.poly_store_user.retrofit.RetrofitClient;
import com.poly_store_user.retrofit.RetrofitClientNoti;
import com.poly_store_user.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewMain;
    NavigationView navigationView;
    ListView lvMain;
    DrawerLayout drawerLayout;
    LoaiSPAdapter loaiSPAdapter;
    List<LoaiSP> loaiSPList;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    List<SanPham> sanPhamList;
    SanPhamAdapter sanPhamAdapter;
    NotificationBadge badge;
    FrameLayout frameLayout;
    ImageView imgsearch, imageMess, imgAoThun, imgAoKhoac, imgAoSoMi, imgQuanJean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Paper.init(this);
        if (Paper.book().read("user") != null){
            NguoiDung nguoiDung = Paper.book().read("user");
            Utils.nguoidung_current = nguoiDung;

        }
       // getTokenChat();
        getToken();
        AnhXa();
        ActionViewFlipper();
        ActionBar();

        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        Paper.init(this);
        if (Paper.book().read("user") !=null){
            NguoiDung user = Paper.book().read("user");
            Utils.nguoidung_current = user;
        }

        if(isConnected(this)){
            ActionViewFlipper();
            getLoaiSanPham();
            getSanPham();
            getClickMenu();
        }else {
            Toast.makeText(getApplicationContext(), "Không có Internet", Toast.LENGTH_SHORT).show();
        }
    }
    private void getToken(){
        FirebaseMessaging.getInstance().getToken()
                .addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        if(!TextUtils.isEmpty(s)){
                            compositeDisposable.add(apiBanHang.updateToken( Utils.nguoidung_current.getMaND(),s)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(
                                            messageModel -> {
                                              Log.d("///===","Token: "+s);
                                            },
                                            throwable -> {
                                                Log.d("log",throwable.getMessage());
                                            }

                                    ));


                        }
                    }
                });
    }
    private void getTokenChat() {
        //getToken
        compositeDisposable.add(apiBanHang.getToken(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        nguoiDungModel -> {
                            if (nguoiDungModel.isSuccess()){
                                Utils.ID_RECEIVED = String.valueOf(nguoiDungModel.getResult().get(0).getMaND()) ;
                            }
                        }, throwable -> {}
                ));
    }



    private void getClickMenu() {
        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Intent trangChu = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(trangChu);
                        break;
                    case 1:
                        Intent aoKhoac = new Intent(MainActivity.this, AoKhoacActivity.class);
                        aoKhoac.putExtra("maLoai",1);
                        startActivity(aoKhoac);
                        break;
                    case 2:
                        Intent aoThun = new Intent(MainActivity.this, AoThunActivity.class);
                        aoThun.putExtra("maLoai",2);
                        startActivity(aoThun);
                        break;
                    case 3:
                        Intent aoSoMi = new Intent(MainActivity.this, AoSoMiActivity.class);
                        aoSoMi.putExtra("maLoai",3);
                        startActivity(aoSoMi);
                        break;
                    case 4:
                        Intent quanJean = new Intent(MainActivity.this, QuanJeanActivity.class);
                        quanJean.putExtra("maLoai",4);
                        startActivity(quanJean);
                        break;
                    case 5:
                        Intent lienHe = new Intent(MainActivity.this, LienHeActivity.class);
                        startActivity(lienHe);
                        break;
                    case 6:
                        Intent donHang = new Intent(MainActivity.this, XemDonActivity.class);
                        startActivity(donHang);
                        break;
                    case 7:
                        Intent quanli = new Intent(getApplicationContext(), QuanLiActivity.class);
                        startActivity(quanli);
                        break;
                    case 8:
                        Intent thongke = new Intent(getApplicationContext(), ThongKeActivity.class);
                        startActivity(thongke);
                        break;
                    case 9:
                        Intent themNguoiDung = new Intent(getApplicationContext(), ThemNguoiDungActivity.class);
                        startActivity(themNguoiDung);
                        break;
                    case 10:
                        Intent chat = new Intent(getApplicationContext(), ChatActivity.class);
                        startActivity(chat);
                        break;
                    case 11:
                        Intent doiMatKhau = new Intent(getApplicationContext(), DoiMatKhauActivity.class);
                        startActivity(doiMatKhau);
                        break;
                    case 12:
                        // xóa key nguoidung
                        Paper.book().delete("user");
                        FirebaseAuth.getInstance().signOut();
                        Intent dangnhap = new Intent(getApplicationContext(), DangNhapActivity.class);
                        startActivity(dangnhap);
                        finish();
                        break;
                }
            }
        });

    }

    private void getSanPham() {
        compositeDisposable.add(apiBanHang.getSanPham()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sanPhamModel -> {
                    if (sanPhamModel.isSuccess()) {
                        sanPhamList = sanPhamModel.getResult();
                        sanPhamAdapter = new SanPhamAdapter(getApplicationContext(), sanPhamList);
                        recyclerViewMain.setAdapter(sanPhamAdapter);
                    }
                }, throwable -> {
                    Toast.makeText(getApplicationContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                }));
    }

    private void getLoaiSanPham() {
        compositeDisposable.add(apiBanHang.getLoaiSp()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        loaiSPModel -> {
                            if (loaiSPModel.isSuccess()){
                                loaiSPList = loaiSPModel.getResult();
                                loaiSPList.add(new LoaiSP("Quản lý","https://cdn-icons-png.flaticon.com/512/3429/3429694.png"));
                                loaiSPList.add(new LoaiSP("Thống kê","https://cdn-icons-png.flaticon.com/512/2936/2936690.png"));
                                loaiSPList.add(new LoaiSP("Thêm người dùng","https://cdn-icons-png.flaticon.com/512/4175/4175032.png"));
                                loaiSPList.add(new LoaiSP("Chat","https://cdn-icons-png.flaticon.com/512/589/589708.png"));
                                loaiSPList.add(new LoaiSP("Đổi mật khẩu","https://cdn-icons-png.flaticon.com/512/3585/3585217.png"));
                                loaiSPList.add(new LoaiSP("Đăng xuất","https://cdn-icons-png.flaticon.com/512/159/159707.png"));
                                loaiSPAdapter = new LoaiSPAdapter(getApplicationContext(),loaiSPList);
                                lvMain.setAdapter(loaiSPAdapter);
                            }
                        }
                ));
    }

    public void AnhXa() {
        imgAoKhoac = findViewById(R.id.imgAoKhoac);
        imgAoSoMi = findViewById(R.id.imgAoSoMi);
        imgAoThun = findViewById(R.id.imgAoThun);
        imgQuanJean = findViewById(R.id.imgQuanJean);
        imageMess = findViewById(R.id.image_mess);
        imgsearch = findViewById(R.id.imgsearch);
        toolbar = findViewById(R.id.toolbarMain);
        viewFlipper = findViewById(R.id.viewLipper);
        recyclerViewMain = findViewById(R.id.recyclerviewMain);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerViewMain.setLayoutManager(layoutManager);
        recyclerViewMain.setHasFixedSize(true);
        navigationView = findViewById(R.id.navigationView);
        lvMain = findViewById(R.id.lvMain);
        drawerLayout = findViewById(R.id.drawerlayout);
         badge = findViewById(R.id.menu_sl);
        frameLayout = findViewById(R.id.framegiohang);
        //khoi tao list
        loaiSPList = new ArrayList<>();
        sanPhamList = new ArrayList<>();
        if (Utils.manggiohang == null){
            Utils.manggiohang = new ArrayList<>();

        }else {
            int totalItem = 0;
            for (int i=0; i<Utils.manggiohang.size(); i++){
                totalItem= totalItem + Utils.manggiohang.get(i).getSoluongGH();

            }
            badge.setText(String.valueOf(totalItem));
        }

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent giohang = new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(giohang);
            }
        });
        imgsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TimKiemActivity.class);
                startActivity(intent);
            }
        });
        imageMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                startActivity(intent);
            }
        });
        imgAoKhoac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent aoKhoac = new Intent(MainActivity.this, AoKhoacActivity.class);
                aoKhoac.putExtra("maLoai",1);
                startActivity(aoKhoac);
            }
        });
        imgAoSoMi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent aoSoMi = new Intent(MainActivity.this, AoSoMiActivity.class);
                aoSoMi.putExtra("maLoai",3);
                startActivity(aoSoMi);
            }
        });
        imgAoThun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent aoThun = new Intent(MainActivity.this, AoThunActivity.class);
                aoThun.putExtra("maLoai",2);
                startActivity(aoThun);
            }
        });
        imgQuanJean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent quanJean = new Intent(MainActivity.this, QuanJeanActivity.class);
                quanJean.putExtra("maLoai",4);
                startActivity(quanJean);
            }
        });
        
    }

    @Override
    protected void onResume() {
        super.onResume();
        int totalItem = 0;
        for (int i=0; i<Utils.manggiohang.size(); i++ ){
            totalItem= totalItem + Utils.manggiohang.get(i).getSoluongGH();

        }
     badge.setText(String.valueOf(totalItem));
    }

    private void ActionViewFlipper(){
        List<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://360boutique.vn/wp-content/uploads/2022/06/Web.jpg");
        mangquangcao.add("https://360boutique.vn/wp-content/uploads/2022/07/Banner-web.jpg");
        mangquangcao.add("https://360boutique.vn/wp-content/uploads/2022/06/web-1.jpg");
        for (int i = 0; i<mangquangcao.size(); i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);
    }

    private void ActionBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    public boolean isConnected (Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null && wifi.isConnected()) || (mobile != null && mobile.isConnected())) {
            return true;
        } else {
            return false;
        }
    }

}