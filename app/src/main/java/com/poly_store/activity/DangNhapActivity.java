package com.poly_store.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.poly_store.R;
import com.poly_store.retrofit.ApiBanHang;
import com.poly_store.retrofit.RetrofitClient;
import com.poly_store.utils.Utils;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DangNhapActivity extends AppCompatActivity {

    TextView txtdangki, txtresetMK;
    EditText email, matKhau;
    AppCompatButton btndangnhap;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        initView();
        initControll();
    }

    private void initControll(){
        txtdangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DangKyActivity.class);
                startActivity(intent);
            }
        });

        txtresetMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QuenMatKhauActivity.class);
                startActivity(intent);
            }
        });

        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_email = email.getText().toString().trim();
                String str_matKhau = matKhau.getText().toString().trim();

                if (TextUtils.isEmpty(str_email)){
                    Toast.makeText(getApplicationContext(), "Ban chua nhap Email", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(str_matKhau)){
                    Toast.makeText(getApplicationContext(), "Ban chua nhap mat khau", Toast.LENGTH_SHORT).show();
                }else {
                    //save

                    Paper.book().write("email",str_email);
                    Paper.book().write("matKhau",str_matKhau);
                    compositeDisposable.add(apiBanHang.dangNhap(str_email, str_matKhau)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    nguoiDungModel -> {
                                        if (nguoiDungModel.isSuccess()){


                                            Utils.nguoidung_current = nguoiDungModel.getResult().get(0);
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    },
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                            ));
                }
            }
        });
    }

    private void initView(){
        Paper.init(this);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        txtdangki = findViewById(R.id.txtdangky);
        txtresetMK = findViewById(R.id.txtresetMK);

        email = findViewById(R.id.email);
        matKhau = findViewById(R.id.matKhau);
        btndangnhap = findViewById(R.id.btndangnhap);

        //read data
        if (Paper.book().read("email") != null && Paper.book().read("matKhau") != null){
            email.setText(Paper.book().read("email"));
            matKhau.setText(Paper.book().read("matKhau"));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.nguoidung_current.getEmail() != null && Utils.nguoidung_current.getMatKhau() != null){
            email.setText(Utils.nguoidung_current.getEmail());
            matKhau.setText(Utils.nguoidung_current.getMatKhau());
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}