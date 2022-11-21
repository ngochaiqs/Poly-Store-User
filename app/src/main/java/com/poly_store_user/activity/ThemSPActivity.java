package com.poly_store_user.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.poly_store_user.R;
import com.poly_store_user.databinding.ActivityThemSpBinding;
import com.poly_store_user.model.MessageModel;
import com.poly_store_user.model.SanPham;
import com.poly_store_user.retrofit.ApiBanHang;
import com.poly_store_user.retrofit.RetrofitClient;
import com.poly_store_user.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemSPActivity extends AppCompatActivity {
    Spinner spinner;
    int loai=0;
    ActivityThemSpBinding binding;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    SanPham sanPhamSua;
    boolean flag = false;
    String mediaPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThemSpBinding.inflate(getLayoutInflater());
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);

        setContentView(binding.getRoot());
        initView();
        initData();
        ActionToolBar();

        Intent intent = getIntent();
        sanPhamSua = (SanPham) intent.getSerializableExtra("sua");
        if(sanPhamSua == null ){
            // them moi
            flag = false;
        }else {
            // sua
            flag = true;
            binding.btnthemsp.setText("Sửa sản phẩm");
            binding.toolbarThemSP.setTitle("Sửa sản phẩm");
//             show data
            binding.motathemsp.setText(sanPhamSua.getMoTa());
            binding.giaspthemsp.setText(sanPhamSua.getGiaSP()+"");
            binding.tenspthemsp.setText(sanPhamSua.getTenSP());
            binding.hinhanh.setText(sanPhamSua.getHinhAnhSP());
            binding.spinnerLoaiThemsp.setSelection(sanPhamSua.getMaLoai());
        }


    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    private void ActionToolBar() {
        setSupportActionBar(binding.toolbarThemSP);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbarThemSP.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData(){
        List<String> stringList = new ArrayList<>();
        stringList.add("Vui lòng chọn sản phẩm");
        stringList.add("Áo khoác");
        stringList.add("Áo thun");
        stringList.add("Áo sơ mi");
        stringList.add("Quần jean");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, stringList);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loai = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.btnthemsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag == false){
                    themsanpham();
                }else {
                    suaSanPham();
                }
            }
        });
        binding.tenspthemsp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                }
            }
        });
        binding.giaspthemsp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                }
            }
        });
        binding.motathemsp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                }
            }
        });
        binding.tilHASP.setStartIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(ThemSPActivity.this)
                        .crop()	    			//Cắt ảnh
                        .compress(3072)			//Dung lượng ảnh tối đa
                        .maxResultSize(800, 1066)	//Kích thước ảnh sẽ được chọn
                        .start();
            }
        });
        binding.tenspthemsp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.tilTenSP.setError(null);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        binding.giaspthemsp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.tilGiaSP.setError(null);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        binding.motathemsp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.tilMoTaSP.setError(null);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        binding.hinhanh.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.tilHASP.setError(null);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mediaPath = data.getDataString();
        uploadMultipleFile();
        Log.d("LOG","onActivityResult: " +mediaPath);
    }

    private void suaSanPham() {
        String str_ten = binding.tenspthemsp.getText().toString().trim();
        String str_gia = binding.giaspthemsp.getText().toString().trim();
        String str_mota = binding.motathemsp.getText().toString().trim();
        String str_hinhanh = binding.hinhanh.getText().toString().trim();
        if (TextUtils.isEmpty(str_ten)) {
            binding.tilTenSP.setError("Vui lòng nhập tên sản phẩm!");
        }else if(TextUtils.isEmpty(str_gia)) {
            binding.tilGiaSP.setError("Vui lòng nhập giá sản phẩm!");
        }else if(TextUtils.isEmpty(str_mota)) {
            binding.tilMoTaSP.setError("Vui lòng nhập mô tả sản phẩm!");
        }else if(TextUtils.isEmpty(str_hinhanh)) {
            binding.tilHASP.setError("Vui lòng nhập thêm hình ảnh sản phẩm!");
        }else if(loai == 0) {
            Toast.makeText(this, "Vui lòng chọn loại sản phẩm!", Toast.LENGTH_SHORT).show();
        } else {
            final LoadingDialog loadingDialog = new LoadingDialog(ThemSPActivity.this);
            loadingDialog.startLoadingDialog();
            compositeDisposable.add(apiBanHang.updatesp(str_ten,str_gia,str_hinhanh,str_mota,loai,sanPhamSua.getMaSP())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            messageModel -> {
                                if (messageModel.isSuccess()){
                                    loadingDialog.dismissDialog();
                                    Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(ThemSPActivity.this, QuanLiActivity.class);
                                    startActivity(intent);
                                }else {
                                    loadingDialog.dismissDialog();
                                    Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_LONG).show();
                                }

                            },
                            throwable -> {
                                loadingDialog.dismissDialog();
                                Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                            }
                    ));
        }
    }

    private void themsanpham() {
        String str_ten = binding.tenspthemsp.getText().toString().trim();
        String str_gia = binding.giaspthemsp.getText().toString().trim();
        String str_mota = binding.motathemsp.getText().toString().trim();
        String str_hinhanh = binding.hinhanh.getText().toString().trim();
        if (TextUtils.isEmpty(str_ten)) {
            binding.tilTenSP.setError("Vui lòng nhập tên sản phẩm!");
        }else if(TextUtils.isEmpty(str_gia)) {
            binding.tilGiaSP.setError("Vui lòng nhập giá sản phẩm!");
        }else if(TextUtils.isEmpty(str_mota)) {
            binding.tilMoTaSP.setError("Vui lòng nhập mô tả sản phẩm!");
        }else if(TextUtils.isEmpty(str_hinhanh)) {
            binding.tilHASP.setError("Vui lòng nhập thêm hình ảnh sản phẩm!");
        }else if(loai == 0) {
            Toast.makeText(this, "Vui lòng chọn loại sản phẩm!", Toast.LENGTH_SHORT).show();
        } else {
            final LoadingDialog loadingDialog = new LoadingDialog(ThemSPActivity.this);
            loadingDialog.startLoadingDialog();
            compositeDisposable.add(apiBanHang.themSP(str_ten,str_gia,str_hinhanh,str_mota,(loai))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            messageModel -> {
                                if (messageModel.isSuccess()){
                                    loadingDialog.dismissDialog();
                                    Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(ThemSPActivity.this, QuanLiActivity.class);
                                    startActivity(intent);
                                }else {
                                    loadingDialog.dismissDialog();
                                    Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_LONG).show();
                                }

                            },
                            throwable -> {
                                loadingDialog.dismissDialog();
                                Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                            }
                    ));
        }
    }

    private String getPath(Uri uri){
        String result;
        Cursor cursor = getContentResolver().query(uri, null, null , null, null);
        if (cursor == null){
            result = uri.getPath();
        }else{
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(index);
            cursor.close();
        }
        return result;
    }

    private void uploadMultipleFile() {
        final LoadingDialog loadingDialog = new LoadingDialog(ThemSPActivity.this);
        loadingDialog.startLoadingDialog();
        Uri uri = Uri.parse(mediaPath);

        // Map is used to multipart the file using okhttp3.RequestBody
        File file = new File(getPath(uri));

        // Parsing any Media type file
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload1 = MultipartBody.Part.createFormData("file", file.getName(), requestBody1);

        retrofit2.Call<MessageModel> call = apiBanHang.uploadFile(fileToUpload1);
        call.enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(retrofit2.Call<MessageModel> call, Response<MessageModel> response) {
                MessageModel serverResponse = response.body();
                if (serverResponse != null) {
                    if (serverResponse.isSuccess()) {
                        binding.hinhanh.setText(serverResponse.getName());
                        loadingDialog.dismissDialog();
                    } else {
                        loadingDialog.dismissDialog();
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                } else {
                    loadingDialog.dismissDialog();
                    Log.v("Response", serverResponse.toString());
                }
            }


            @Override
            public void onFailure(Call<MessageModel> call, Throwable t) {
                Log.v("log", t.getMessage());

            }
        });
    }



    private void initView(){
        spinner = findViewById(R.id.spinner_loai_themsp);
    }
    @Override
    protected void onDestroy(){
        compositeDisposable.clear();
        super.onDestroy();
    }
}
