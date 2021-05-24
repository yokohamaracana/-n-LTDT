package com.example.qunlqunn;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.qunlqunn.DAO.NhanVienDAO;

public class DangNhapActivity  extends AppCompatActivity implements View.OnClickListener {
    Button btnDangNhap, btnDangKy;
    EditText edTenDangNhap, edMatKhau;
    NhanVienDAO nhanVienDAO;
    private final int STORAGE_PERMISSION_CODE = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangnhap);
        btnDangNhap = findViewById(R.id.btnDNDN);
        btnDangKy = findViewById(R.id.btnDKDN);
        edTenDangNhap = findViewById(R.id.edTenDangNhapDN);
        edMatKhau = findViewById(R.id.edMatKhauDN);

        nhanVienDAO = new NhanVienDAO(this);
        btnDangNhap.setOnClickListener(this);
        btnDangKy.setOnClickListener(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            requestStoragePermission();
        HienthiButton();
    }
    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                    .setTitle("Ứng dụng cần được cấp quyền")
                    .setMessage("Ứng dụng cần được cấp quyền truy cập bộ nhớ để có thể sử dụng ứng dụng tốt hơn!")
                    .setPositiveButton("Ok", (dialog, which) -> ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE))
                    .setNegativeButton("Hủy", (dialog, which) -> System.exit(0))
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Đã được cấp quyền!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Ứng dụng bị từ chối cấp quyền!", Toast.LENGTH_SHORT).show();
                requestStoragePermission();
            }
        }
    }
    private void HienthiButton(){
        boolean kiemtra = nhanVienDAO.KiemTraNhanVien();
        if (kiemtra){
            btnDangKy.setVisibility(View.GONE);
            btnDangNhap.setVisibility(View.VISIBLE);
        }
        else {
            btnDangNhap.setVisibility(View.GONE);
            btnDangKy.setVisibility(View.VISIBLE);
        }
    }
    private void DangNhap(){
        String sTenDangNhap = edTenDangNhap.getText().toString();
        String sMatKhau = edMatKhau.getText().toString();
        int kiemtra = NhanVienDAO.KiemTraDangNhap(sTenDangNhap,sMatKhau);
        int maquyen = nhanVienDAO.LayQuyenNhanVien(kiemtra);
        if (kiemtra !=0){
            SharedPreferences sharedPreferences = getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("maquyen",maquyen);
            editor.apply();

            Intent iTrangChu = new Intent(DangNhapActivity.this,TrangChuActivity.class);
            iTrangChu.putExtra("tendn",edTenDangNhap.getText().toString());
            iTrangChu.putExtra("manhanvien",kiemtra);
            startActivity(iTrangChu);
            overridePendingTransition(R.anim.hieuung_activity_vao,R.anim.hieuung_activity_ra);
        }
        else {
            Toast.makeText(DangNhapActivity.this,"Sai tài khoản hoặc mật khẩu",Toast.LENGTH_SHORT).show();
        }
    }
    private void DangKy(){
        Intent iDangky = new Intent(DangNhapActivity.this,DangKyActivity.class);
        startActivity(iDangky);
    }
    protected void onResume(){
        super.onResume();
        HienthiButton();
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnDNDN:
                DangNhap();
                break;
            case  R.id.btnDKDN:
                DangKy();
                ;break;
        }
    }
}