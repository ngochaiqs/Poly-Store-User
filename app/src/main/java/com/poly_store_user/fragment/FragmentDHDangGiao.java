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

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FragmentDHDangGiao extends Fragment {
    TextView tvDangGiao;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    RecyclerView redonhang;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentdhdanggiao_layout, container, false);
        tvDangGiao = view.findViewById(R.id.tvDangGiao);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        redonhang = view.findViewById(R.id.recycleview_donhangdanggiao);
        redonhang.setLayoutManager(new LinearLayoutManager(getContext()));
        donHangDangGiao();
        return view;
    }

    private void donHangDangGiao() {
        compositeDisposable.add(apiBanHang.xemDonHang(Utils.nguoidung_current.getMaND(),2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        donHangModel -> {
                            if (donHangModel.isSuccess()) {
                                tvDangGiao.setText("Có " + donHangModel.getResult().size() + " đơn hàng đang giao!");
                                DonHangAdapter adapter = new DonHangAdapter(getContext(), donHangModel.getResult());
                                redonhang.setAdapter(adapter);
                            } else {
                                tvDangGiao.setText("Không có đơn hàng nào đang giao!");
                            }
                        },
                        throwable -> {

                        }
                ));
    }
}
