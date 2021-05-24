package com.example.qunlqunn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qunlqunn.DAO.QuyenDAO;
import com.example.qunlqunn.Database.CreateDatabase;

public class ManHinhDoiActivity extends AppCompatActivity {
    private SharedPreferences mMoLanDau;
    private SharedPreferences.Editor editor;

    private QuyenDAO quyenDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cho);

         Thread thread = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                Log.d("kiemtra", e.getMessage());
            } finally {
                mMoLanDau = getSharedPreferences("SPR_MOLANDAU", 0);
                if (mMoLanDau != null) {
                    boolean firstOpen = mMoLanDau.getBoolean("MOLANDAU", true);

                    if (firstOpen){
                        CreateDatabase createDatabase = new CreateDatabase(this);
                        createDatabase.open();

                        quyenDAO = new QuyenDAO(this);
                        quyenDAO.ThemQuyen("Quản lý");
                        quyenDAO.ThemQuyen("Nhân viên");


                        editor = mMoLanDau.edit();
                        editor.putBoolean("MOLANDAU", false);
                        editor.apply();
                    }

                    Intent iDangNhap = new Intent(this, DangNhapActivity.class);
                    startActivity(iDangNhap);
                    finish();
                }
            }
        });
        thread.start();
    }
}
