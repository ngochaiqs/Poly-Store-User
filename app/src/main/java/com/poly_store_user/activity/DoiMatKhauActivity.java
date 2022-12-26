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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;
import com.poly_store_user.R;
import com.poly_store_user.retrofit.ApiBanHang;
import com.poly_store_user.retrofit.RetrofitClient;
import com.poly_store_user.utils.Utils;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DoiMatKhauActivity extends AppCompatActivity {
    EditText edtMKCu, edtMKMoi, edtReMKMoi, edtEmail;
    TextInputLayout line3DMK, line4DMK, line5DMK;
    AppCompatButton btnDoiMatKhau;
    Toolbar toolbar;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);

        AnhXa();
        initControl();
        ActionToolBar();
    }

    private void initControl() {
        edtEmail.setText(Paper.book().read("email"));
        btnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doiMatKhau();
            }
        });
        edtEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        edtMKCu.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        edtMKMoi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        edtReMKMoi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        edtMKCu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                line3DMK.setError(null);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        edtMKMoi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                line4DMK.setError(null);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        edtReMKMoi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                line5DMK.setError(null);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void doiMatKhau() {
        String str_MatKhauCu = edtMKCu.getText().toString().trim();
        String str_email = edtEmail.getText().toString().trim();
        String str_matKhauMoi = edtMKMoi.getText().toString().trim();
        String str_reMatKhauMoi = edtReMKMoi.getText().toString().trim();


        if (TextUtils.isEmpty(str_MatKhauCu)) {
            line3DMK.setError("Vui lòng nhập mật khẩu cũ!");
        }else if(TextUtils.isEmpty(str_email)) {
            Toast.makeText(getApplicationContext(), "Chưa nhập Email!", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(str_matKhauMoi) ) {
            line4DMK.setError("Vui lòng nhập mật khẩu mới!");
        } else if (str_matKhauMoi.length() < 6) {
            line4DMK.setError("Mật khẩu phải có ít nhất 6 ký tự!");
        } else if (str_reMatKhauMoi.length() < 6) {
            line5DMK.setError("Mật khẩu phải có ít nhất 6 ký tự!");
        } else if (TextUtils.isEmpty(str_reMatKhauMoi)) {
            line5DMK.setError("Vui lòng xác nhận mật khẩu mới!");
        }else{
            if (str_matKhauMoi.equals(str_reMatKhauMoi)){

                postData(str_email, str_MatKhauCu, str_matKhauMoi);
            }else{
                Toast.makeText(getApplicationContext(), "Mật khẩu mới chưa khớp!", Toast.LENGTH_SHORT).show();
                line4DMK.setError("Mật khẩu mới chưa khớp!");
                line5DMK.setError("Mật khẩu mới chưa khớp!");
            }
        }
    }
    private void  postData(String str_email, String str_MatKhauCu,String str_matKhauMoi){
        //post data
        final LoadingDialog loadingDialog = new LoadingDialog(DoiMatKhauActivity.this);
        loadingDialog.startLoadingDialog();
        compositeDisposable.add(apiBanHang.doiMatKhau(str_email,str_MatKhauCu, str_matKhauMoi)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        nguoiDungModel -> {
                            if (nguoiDungModel.isSuccess()){
                                //Utils.nguoidung_current.setMatKhau(str_matKhauMoi);
                                Log.d("===///", "matkhaucu: " + str_MatKhauCu);
                                Log.d("===///", "matkhaumoi: " + str_matKhauMoi);
                                   Log.d("===///", "matkhaumoi: " + Utils.nguoidung_current.getMatKhau());

                                loadingDialog.dismissDialog();
                                Toast.makeText(getApplicationContext(),nguoiDungModel.getMessage(),Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                loadingDialog.dismissDialog();
                                Toast.makeText(getApplicationContext(), nguoiDungModel.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> {
                            loadingDialog.dismissDialog();
                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void AnhXa() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        edtMKCu = findViewById(R.id.edtMatKhauCu);
        edtMKMoi = findViewById(R.id.edtMatKhauMoi);
        edtReMKMoi = findViewById(R.id.edtreMatKhauMoi);
        edtEmail = findViewById(R.id.edtEmailDoiMK);
        btnDoiMatKhau = findViewById(R.id.btnDoiMK);
        toolbar = findViewById(R.id.toolbarDoiMK);
        line3DMK = findViewById(R.id.line3DMK);
        line4DMK = findViewById(R.id.line4DMK);
        line5DMK = findViewById(R.id.line5DMK);
    }
    @Override
    protected void onDestroy(){
        compositeDisposable.clear();
        super.onDestroy();;
    }
}