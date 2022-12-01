package com.nothapp.appfuniture.appfuniture.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.appfuniture.databinding.ActivityDetailsBinding;
import com.nothapp.appfuniture.appfuniture.Model.GioHang;
import com.nothapp.appfuniture.appfuniture.Model.Popular;
import com.example.appfuniture.R;
import com.nothapp.appfuniture.appfuniture.Utils.Util;

import java.text.NumberFormat;
import java.util.Locale;

public class DetailsActivity extends AppCompatActivity {
    private ActivityDetailsBinding binding;
    private Popular popular;
    private int numberOder = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_details);
        onShowData();
        onBack();
        customNumberOder();
        onClickMuaNgay();
    }

    private void onClickMuaNgay() {
        binding.layoutMuaNgay.setOnClickListener(new View.OnClickListener() { //Xử lý sự kiện nút mua ngay
            @Override
            public void onClick(View view) {
                if (Util.gioHangArrayList.size()>0){
                    int soLuongSp = Integer.parseInt(binding.txtNumber.getText().toString()); //Lấy ra số lượng sản phẩm
                    boolean exits = false;
                    //Kiểm tra nếu người dùng đặt cùng 1 sản phẩm thì sẽ tăng số lượng sản phẩm đó lên
                    for(int i=0;i<Util.gioHangArrayList.size();i++){
                        if (Util.gioHangArrayList.get(i).getTenSP().equals(popular.getNamePopular())){
                            Util.gioHangArrayList.get(i).setSoLuongSP(Util.gioHangArrayList.get(i).getSoLuongSP()+soLuongSp);
                            Util.gioHangArrayList.get(i).setGiaSp(Util.gioHangArrayList.get(i).getSoLuongSP()*popular.getMoneyPopular());
                            exits = true;
                        }
                    }
                    if (!exits){
                        int sl = Integer.parseInt(binding.txtNumber.getText().toString());
                        int giaMoi =sl*popular.getMoneyPopular();
                        Util.gioHangArrayList.add(new GioHang(popular.getImgPopular(),popular.getNamePopular(),giaMoi,sl));
                    }
                }else {
                    int sl = Integer.parseInt(binding.txtNumber.getText().toString());
                    int giaMoi =sl*popular.getMoneyPopular();
                    Util.gioHangArrayList.add(new GioHang(popular.getImgPopular(),popular.getNamePopular(),giaMoi,sl));
                }
                startActivity(new Intent(DetailsActivity.this,GioHangActivity.class));
            }
        });
    }

    private void customNumberOder() {
        //Nút tăng số lượng
        binding.btnCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberOder = numberOder+1;
                binding.txtNumber.setText(String.valueOf(numberOder));
            }
        });
        //Nút giảm số lượng
        binding.btnTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numberOder>1){
                    numberOder =numberOder-1;
                }
                binding.txtNumber.setText(String.valueOf(numberOder));
            }
        });
    }

    private void onBack() {
        //Nút quay lại
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailsActivity.this,MainActivity.class));
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void onShowData() {
        //Nhận dữ liệu và hiển thị lên màn hình
        Intent intent = getIntent();
        popular = (Popular) intent.getSerializableExtra("popular");
        if (popular!=null){
            binding.nameDetails.setText(popular.getNamePopular());
            binding.imgDetails.setImageResource(popular.getImgPopular());
            binding.txtMoneyPopular.setText(NumberFormat.getNumberInstance(Locale.getDefault()).format(popular.getMoneyPopular()) +" vnd");
            binding.rating.setRating(popular.getRating());
        }
    }
}