package com.poly_store_user.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.poly_store_user.R;
import com.poly_store_user.adapter.DonHangAdapter;
import com.poly_store_user.retrofit.ApiBanHang;
import com.poly_store_user.retrofit.RetrofitClient;
import com.poly_store_user.utils.Utils;

import org.w3c.dom.Text;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FragmentDHXuLy extends Fragment {
    TextView tv;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    RecyclerView redonhang;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentdhxuly_layout, container, false);
        tv = view.findViewById(R.id.tvXuLy);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        redonhang = view.findViewById(R.id.recycleview_donhangxuly);
        redonhang.setLayoutManager(new LinearLayoutManager(getContext()));
        donHangXuLy();
        return view;
    }

    private void donHangXuLy() {
        compositeDisposable.add(apiBanHang.xemDonHang(Utils.nguoidung_current.getMaND(),0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        donHangModel -> {
                            if (donHangModel.isSuccess()) {
                                tv.setText("Có " + donHangModel.getResult().size() + " đơn hàng đang xử lý!");
                                DonHangAdapter adapter = new DonHangAdapter(getContext(), donHangModel.getResult());
                                redonhang.setAdapter(adapter);
                            } else {
                                tv.setText("Không có đơn hàng nào đang xử lý!");
                            }
                        },
                        throwable -> {

                        }
                ));
    }

}