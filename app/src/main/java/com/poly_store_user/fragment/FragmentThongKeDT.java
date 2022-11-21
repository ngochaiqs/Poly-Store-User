package com.poly_store_user.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.poly_store_user.R;
import com.poly_store_user.retrofit.ApiBanHang;
import com.poly_store_user.retrofit.RetrofitClient;
import com.poly_store_user.utils.Utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FragmentThongKeDT extends Fragment {
    AppCompatButton btnDoanhThu;
    TextInputLayout tilTuNgay, tilDenNgay;
    EditText edtTuNgay, edtDenNgay;
    TextView tvDoanhThu;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
    int mYear, mMonth, mDay;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragmenttkdt_layout, container, false);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        edtTuNgay = view.findViewById(R.id.edtTuNgay);
        edtDenNgay = view.findViewById(R.id.edtDenNgay);
        tvDoanhThu = view.findViewById(R.id.tvDoanhThu);
        btnDoanhThu = view.findViewById(R.id.btnDoanhThu);
        tilTuNgay = view.findViewById(R.id.tilTuNgay);
        tilDenNgay = view.findViewById(R.id.tilDenNgay);
        tilTuNgay.setStartIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), 0, mDateTuNgay, mYear, mMonth, mDay);
                dialog.show();
            }
        });
        edtTuNgay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tilTuNgay.setError(null);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        edtDenNgay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tilDenNgay.setError(null);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        tilDenNgay.setStartIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), 0, mDateDenNgay, mYear, mMonth, mDay);
                dialog.show();
            }
        });

        btnDoanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tuNgay = edtTuNgay.getText().toString().trim();
                String denNgay = edtDenNgay.getText().toString().trim();

                if (TextUtils.isEmpty(tuNgay)) {
                    tilTuNgay.setError("Vui lòng nhập thời gian bắt đầu!");
                }else if(TextUtils.isEmpty(denNgay)) {
                    tilDenNgay.setError("Vui lòng nhập thời gian kết thúc!");
                }else {
                    compositeDisposable.add(apiBanHang.getDoanhThu(tuNgay, denNgay)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    doanhThuModel -> {
                                        if (doanhThuModel.isSuccess()){
                                            String doanhThu = doanhThuModel.getResult().get(0).getDoanhThu();
                                            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                                            tvDoanhThu.setText(decimalFormat.format(Double.parseDouble(doanhThu)) + " đ");
                                            Toast.makeText(getActivity(),doanhThuModel.getMessage(),Toast.LENGTH_SHORT).show();

                                        }else {
                                            Toast.makeText(getActivity(), doanhThuModel.getMessage(), Toast.LENGTH_SHORT).show();
                                        }

                                    },
                                    throwable -> {
                                        Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                            ));
                }
            }
        });

        return view;
    }
    DatePickerDialog.OnDateSetListener mDateTuNgay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYear = year;
            mMonth = month;
            mDay = dayOfMonth;
            GregorianCalendar calendar = new GregorianCalendar(mYear, mMonth, mDay);
            edtTuNgay.setText(simpleDateFormat.format(calendar.getTime()));
        }
    };
    DatePickerDialog.OnDateSetListener mDateDenNgay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYear = year;
            mMonth = month;
            mDay = dayOfMonth;
            GregorianCalendar calendar = new GregorianCalendar(mYear, mMonth, mDay);
            edtDenNgay.setText(simpleDateFormat.format(calendar.getTime()));
        }
    };
}
