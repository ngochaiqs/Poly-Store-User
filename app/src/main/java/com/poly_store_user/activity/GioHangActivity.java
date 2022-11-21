package com.poly_store_user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.poly_store_user.R;
import com.poly_store_user.adapter.GioHangAdapter;
import com.poly_store_user.model.EventBus.TinhTongEvent;
import com.poly_store_user.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;

public class GioHangActivity extends AppCompatActivity {
        TextView giohangtrong, tongtien;
        Toolbar toolbar;
        RecyclerView recyclerViewl;
        Button btnmuahang;
        GioHangAdapter adapter;
        long tongtiensp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        initView();
        initControl();
        tinhTongTien();

    }

    private void tinhTongTien() {
       tongtiensp = 0;
        for (int i = 0; i<Utils.mangmuahang.size(); i++){
            tongtiensp = tongtiensp+ (Utils.mangmuahang.get(i).getGiaspGH()* Utils.mangmuahang.get(i).getSoluongGH());

        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tongtien.setText(decimalFormat.format(tongtiensp) + " đ");
    }

    private void initControl(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recyclerViewl.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewl.setLayoutManager(layoutManager);
        if(Utils.manggiohang.size()==0){
            giohangtrong.setVisibility(View.VISIBLE);

        }else {
            adapter = new GioHangAdapter(getApplicationContext(),Utils.manggiohang);
            recyclerViewl.setAdapter(adapter);
        }
        btnmuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ThanhToanActivity.class);
                intent.putExtra("tongtien", tongtiensp);
                Utils.manggiohang.clear();
                startActivity(intent);
            }
        });

    }


    private void initView(){
        giohangtrong = findViewById(R.id.txtgiohangtrong);
        tongtien = findViewById(R.id.txttongtien);
        toolbar = findViewById(R.id.toobar);
        recyclerViewl = findViewById(R.id.recycleviewgiohang);
        btnmuahang = findViewById(R.id.btnmuahang);

    }
    protected void onStart(){
        super.onStart();
        EventBus.getDefault().register(this);

    }
    protected void onStop(){
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(sticky = true , threadMode = ThreadMode.MAIN)
    public void eventTinhTien(TinhTongEvent event){
        if (event != null){
            tinhTongTien();
        }
    }
}