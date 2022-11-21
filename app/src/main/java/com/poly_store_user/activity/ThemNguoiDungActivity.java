package com.poly_store_user.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.poly_store_user.R;
import com.poly_store_user.retrofit.ApiBanHang;
import com.poly_store_user.retrofit.RetrofitClient;
import com.poly_store_user.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThemNguoiDungActivity extends AppCompatActivity {
    EditText emailND, matKhauND, reMatKhauND, sdtND, tenNDND, diaChiND;
    TextInputLayout tilTenTND, tilSDTTND, tilDiaChiTND, tilEmailTND, tilMatKhauTND, tilReMatKhauTND;
    Toolbar toolbar;
    AppCompatButton buttonThemND;
    ApiBanHang apiBanHang;
    FirebaseAuth firebaseAuth;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_nguoi_dung);
        initView();
        initControll();
        ActionToolBar();
    }
    private void initControll(){
        buttonThemND.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dangKy();
            }
        });
        emailND.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    hideKeyboard(view);
                }
            }
        });
        matKhauND.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    hideKeyboard(view);
                }
            }
        });
        reMatKhauND.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    hideKeyboard(view);
                }
            }
        });
        sdtND.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    hideKeyboard(view);
                }
            }
        });
        tenNDND.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    hideKeyboard(view);
                }
            }
        });
        diaChiND.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    hideKeyboard(view);
                }
            }
        });
        tenNDND.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tilTenTND.setError(null);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        sdtND.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tilSDTTND.setError(null);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        diaChiND.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tilDiaChiTND.setError(null);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        emailND.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tilEmailTND.setError(null);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        matKhauND.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tilMatKhauTND.setError(null);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        reMatKhauND.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tilReMatKhauTND.setError(null);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    private void dangKy() {
        String str_tenND = tenNDND.getText().toString().trim();
        String str_email = emailND.getText().toString().trim();
        String str_matKhau = matKhauND.getText().toString().trim();
        String str_reMatKhau = reMatKhauND.getText().toString().trim();
        String str_sdt = sdtND.getText().toString().trim();
        String str_diaChi = diaChiND.getText().toString().trim();

        if (TextUtils.isEmpty(str_tenND)) {
            tilTenTND.setError("Vui lòng nhập tên người dùng!");
        }else if(TextUtils.isEmpty(str_email)) {
            tilEmailTND.setError("Vui lòng nhập Email!");
        } else if (TextUtils.isEmpty(str_matKhau)) {
            tilMatKhauTND.setError("Vui lòng nhập mật khẩu!");
        } else if (TextUtils.isEmpty(str_reMatKhau)) {
            tilReMatKhauTND.setError("Vui lòng nhập lại mật khẩu!");
        }else if(TextUtils.isEmpty(str_sdt)) {
            tilSDTTND.setError("Vui lòng nhập số điện thoại!");
        }else if(TextUtils.isEmpty(str_diaChi)) {
            tilDiaChiTND.setError("Vui lòng nhập địa chỉ!");


        }else{
            if (str_matKhau.equals(str_reMatKhau)){
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.createUserWithEmailAndPassword(str_email,str_matKhau)
                        .addOnCompleteListener(ThemNguoiDungActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    if (user != null) {
                                        postData(str_tenND,str_email, str_matKhau, str_sdt, str_diaChi, user.getUid());

                                    }


                                }else {
                                    Toast.makeText(getApplicationContext(),"Email đã tồn tại!",Toast.LENGTH_SHORT).show();
                                }
                            }

                        });


            }else{
                Toast.makeText(getApplicationContext(), "Mật khẩu chưa khớp!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void  postData(String str_tenND, String str_email, String str_matKhau, String str_sdt,String str_diaChi, String uid){
        //post data
        final LoadingDialog loadingDialog = new LoadingDialog(ThemNguoiDungActivity.this);
        loadingDialog.startLoadingDialog();
        compositeDisposable.add(apiBanHang.dangKy(str_tenND,str_email,str_matKhau,str_sdt,str_diaChi, uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        nguoiDungModel -> {
                            if (nguoiDungModel.isSuccess()){
                                Utils.nguoidung_current.setEmail(str_email);
                                Utils.nguoidung_current.setMatKhau(str_matKhau);
                                loadingDialog.dismissDialog();
                                Toast.makeText(getApplicationContext(),"Thêm người dùng thành công!",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                loadingDialog.dismissDialog();
                                Toast.makeText(getApplicationContext(), "Thêm người dùng thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> {
                            loadingDialog.dismissDialog();
                            Toast.makeText(getApplicationContext(), "Thêm người dùng thất bại!", Toast.LENGTH_SHORT).show();
                        }
                ));

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
    private void initView(){
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        emailND = findViewById(R.id.emailND);
        matKhauND = findViewById(R.id.matKhauND);
        reMatKhauND = findViewById(R.id.reMatKhauND);
        buttonThemND = findViewById(R.id.btnthemND);
        sdtND = findViewById(R.id.sdtND);
        tenNDND = findViewById(R.id.tenNDND);
        toolbar = findViewById(R.id.toolbarNguoiDung);
        diaChiND = findViewById(R.id.diaChiND);
        tilTenTND = findViewById(R.id.tilTenTND);
        tilEmailTND = findViewById(R.id.tilEmailTND);
        tilMatKhauTND = findViewById(R.id.tilMatKhauTND);
        tilReMatKhauTND = findViewById(R.id.tilReMatKhauTND);
        tilSDTTND = findViewById(R.id.tilSDTTND);
        tilDiaChiTND = findViewById(R.id.tilDiaChiTND);
    }

    @Override
    protected void onDestroy(){
        compositeDisposable.clear();
        super.onDestroy();;
    }
}