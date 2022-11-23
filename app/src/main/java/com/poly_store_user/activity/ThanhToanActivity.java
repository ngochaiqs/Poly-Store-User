package com.poly_store_user.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.poly_store_user.R;
import com.poly_store_user.model.NotiSendData;
import com.poly_store_user.retrofit.ApiBanHang;
import com.poly_store_user.retrofit.ApiPushNofication;
import com.poly_store_user.retrofit.RetrofitClient;
import com.poly_store_user.retrofit.RetrofitClientNoti;
import com.poly_store_user.utils.Utils;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThanhToanActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView txttongtien;
    EditText edtdiachi, edtTenND, edtSDTND;
    TextInputLayout tilTenTT, tilSDTTT, tilDiaChiTT;
    AppCompatButton btndathang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    long tongtien;
    int totalItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        initView();
        initControl();
        countItem();
    }

    private void countItem() {
        totalItem = 0;
        for (int i = 0; i < Utils.mangmuahang.size(); i++) {
            totalItem = totalItem + Utils.mangmuahang.get(i).getSoluongGH();

        }
    }

    private void initControl() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tongtien = getIntent().getLongExtra("tongtien", 0);
        txttongtien.setText(decimalFormat.format(tongtien) + " đ");
        edtSDTND.setText(Utils.nguoidung_current.getSDT());
        edtTenND.setText(Utils.nguoidung_current.getTenND());
        edtdiachi.setText(Utils.nguoidung_current.getDiaChi());

        edtdiachi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    hideKeyboard(view);
                }
            }
        });
        edtSDTND.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    hideKeyboard(view);
                }
            }
        });
        edtTenND.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    hideKeyboard(view);
                }
            }
        });
        edtTenND.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tilTenTT.setError(null);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        edtSDTND.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tilSDTTT.setError(null);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        edtdiachi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tilDiaChiTT.setError(null);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        btndathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_diachi = edtdiachi.getText().toString().trim();
                String str_ten = edtTenND.getText().toString().trim();
                String str_sdt = edtSDTND.getText().toString().trim();

                if (TextUtils.isEmpty(str_diachi)) {
                    tilDiaChiTT.setError("Vui lòng nhập địa chỉ!");
                }else if(TextUtils.isEmpty(str_ten)) {
                    tilTenTT.setError("Vui lòng nhập tên!");
                }else if(TextUtils.isEmpty(str_sdt)) {
                    tilSDTTT.setError("Vui lòng nhập số điện thoại!");
                } else {
                    final LoadingDialog loadingDialog = new LoadingDialog(ThanhToanActivity.this);
                    loadingDialog.startLoadingDialog();
                    String str_email = Utils.nguoidung_current.getEmail();
                    int maND = Utils.nguoidung_current.getMaND();

                    Log.d("==/ Thông tin thanh toán:", new Gson().toJson(Utils.mangmuahang));
                    compositeDisposable.add(apiBanHang.datHang(str_email, str_sdt, String.valueOf(tongtien), maND, str_ten, str_diachi, totalItem, new Gson().toJson(Utils.mangmuahang))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(nguoiDungModel -> {
                                guiThongBaoAdmin();
                                Log.d("===///", "soLuong: " + totalItem);
                                loadingDialog.dismissDialog();
                                Toast.makeText(getApplicationContext(), "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
                                Utils.mangmuahang.clear();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }, throwable -> {
                                loadingDialog.dismissDialog();
                                Toast.makeText(getApplicationContext(), "Đặt hàng thất bại!", Toast.LENGTH_SHORT).show();
                            }));
                }
            }
        });
    }

    private void guiThongBaoAdmin() {
        //getToken
        compositeDisposable.add(apiBanHang.getToken(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        nguoiDungModel -> {
                            if (nguoiDungModel.isSuccess()) {
                                for (int i = 0; i < nguoiDungModel.getResult().size(); i++) {
                                    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                                    Map<String, String> data = new HashMap<>();
                                    data.put("title", "Thông báo đơn hàng mới");
                                    data.put("body", "Nhận được đơn hàng mới từ " + Utils.nguoidung_current.getTenND() + " gồm " + totalItem + " sản phẩm" + " với tổng tiền " + decimalFormat.format(tongtien) + " đ." + " Vui lòng kiểm tra đơn hàng!");
                                    Log.d("==///", "Token Admin: " + nguoiDungModel.getResult().get(i).getToken());
                                    NotiSendData notiSendData = new NotiSendData(nguoiDungModel.getResult().get(i).getToken(), data);
                                    ApiPushNofication apiPushNofication = RetrofitClientNoti.getInstance().create(ApiPushNofication.class);
                                    compositeDisposable.add(apiPushNofication.sendNofitication(notiSendData)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(
                                                    notiResponse -> {
                                                    },
                                                    throwable -> {
                                                        Log.d("Logg", throwable.getMessage());
                                                    }
                                            ));
                                }
                            }

                        },
                        throwable -> {
                            Log.d("loggg", throwable.getMessage());
                        }
                ));
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    private void initView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        toolbar = findViewById(R.id.toobar);
        txttongtien = findViewById(R.id.txttongtien);
        edtSDTND = findViewById(R.id.edtSDTND);
        edtdiachi = findViewById(R.id.edtdiachi);
        btndathang = findViewById(R.id.btndathang);
        edtTenND = findViewById(R.id.edtTenND);
        tilTenTT = findViewById(R.id.tilTenTT);
        tilSDTTT = findViewById(R.id.tilSDTTT);
        tilDiaChiTT = findViewById(R.id.tilDiaChiTT);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}