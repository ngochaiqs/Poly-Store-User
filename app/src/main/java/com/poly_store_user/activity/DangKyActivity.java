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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

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

public class DangKyActivity extends AppCompatActivity {
    EditText email, matKhau, reMatKhau, sdt, tenND, diaChi;
    TextInputLayout line1, line2, line3, line4, line5, line6;
    TextView txtDangNhap;
    AppCompatButton button;
    ApiBanHang apiBanHang;
    FirebaseAuth firebaseAuth;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        initView();
        initControll();
    }
    private void initControll(){
        txtDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DangNhapActivity.class);
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dangKy();
            }
        });
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        matKhau.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        reMatKhau.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        sdt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        tenND.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        diaChi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    line2.setError(null);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        matKhau.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    line3.setError(null);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        reMatKhau.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    line4.setError(null);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        sdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    line5.setError(null);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        tenND.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    line1.setError(null);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        diaChi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    line6.setError(null);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private void dangKy() {
        String str_tenND = tenND.getText().toString().trim();
        String str_email = email.getText().toString().trim();
        String str_matKhau = matKhau.getText().toString().trim();
        String str_reMatKhau = reMatKhau.getText().toString().trim();
        String str_sdt = sdt.getText().toString().trim();
        String str_diaChi = diaChi.getText().toString().trim();

        if (TextUtils.isEmpty(str_tenND)) {
            line1.setError("Vui lòng nhập tên người dùng!");
        }else if(TextUtils.isEmpty(str_email)) {
            line2.setError("Vui lòng nhập Email!");
        }else if(!str_email.matches(emailPattern)) {
            line2.setError("Địa chỉ Email không hợp lệ!");
        } else if (TextUtils.isEmpty(str_matKhau)) {
            line3.setError("Vui lòng nhập mật khẩu!");
        } else if (str_matKhau.length() < 6) {
            line3.setError("Mật khẩu tối thiểu 6 ký tự!");
        } else if (TextUtils.isEmpty(str_reMatKhau)) {
            line4.setError("Vui lòng nhập lại mật khẩu!");
        } else if (str_reMatKhau.length() < 6) {
            line4.setError("Mật khẩu tối thiểu 6 ký tự!");
        }else if(TextUtils.isEmpty(str_sdt)) {
            line5.setError("Vui lòng nhập số điện thoại!");
        }else if(TextUtils.isEmpty(str_diaChi)) {
            line6.setError("Vui lòng nhập địa chỉ!");

        }else{
            if (str_matKhau.equals(str_reMatKhau)){
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.createUserWithEmailAndPassword(str_email,str_matKhau)
                        .addOnCompleteListener(DangKyActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    if (user != null) {
                                        postData(str_tenND,str_email, str_matKhau, str_sdt, str_diaChi, user.getUid());

                                    }


                                }else {
                                    line2.setError("Email đã tồn tại");
                                }
                            }

                        });


            }else{
                line3.setError("Mật khẩu chưa khớp");
                line4.setError("Mật khẩu chưa khớp");

            }
        }
    }
    private void  postData(String str_tenND, String str_email, String str_matKhau, String str_sdt, String str_diaChi, String uid){
        //post data
        final LoadingDialog loadingDialog = new LoadingDialog(DangKyActivity.this);
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
                                Toast.makeText(getApplicationContext(),nguoiDungModel.getMessage(),Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), DangNhapActivity.class);
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
    private void initView(){
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        email = findViewById(R.id.email);
        matKhau = findViewById(R.id.matKhau);
        reMatKhau = findViewById(R.id.reMatKhau);
        button = findViewById(R.id.btndangky);
        sdt = findViewById(R.id.sdt);
        tenND = findViewById(R.id.tenND);
        txtDangNhap = findViewById(R.id.txtdangnhap);
        diaChi = findViewById(R.id.diaChi);
        line1 = findViewById(R.id.line1);
        line2 = findViewById(R.id.line2);
        line3 = findViewById(R.id.line3);
        line4 = findViewById(R.id.line4);
        line5 = findViewById(R.id.line5);
        line6 = findViewById(R.id.line6);
    }

    @Override
    protected void onDestroy(){
        compositeDisposable.clear();
        super.onDestroy();;
    }
}