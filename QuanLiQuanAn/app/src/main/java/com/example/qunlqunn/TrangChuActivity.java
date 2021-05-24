package com.example.qunlqunn;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.qunlqunn.FragmentApp.HienThiBanAnFagment;
import com.example.qunlqunn.FragmentApp.HienThiNhanVienFragment;
import com.example.qunlqunn.FragmentApp.HienThiThucDonFragment;
import com.google.android.material.navigation.NavigationView;

public class TrangChuActivity  extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trangchu);

        drawerLayout = findViewById(R.id.Drawerlayout);
        navigationView = findViewById(R.id.navigation_trangchu);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.mo,R.string.dong);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setItemIconTintList(null);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction HienthiBanAn = fragmentManager.beginTransaction();
        HienThiBanAnFagment hienthiBanAnFragment = new HienThiBanAnFagment();
        HienthiBanAn.replace(R.id.content,hienthiBanAnFragment);
        HienthiBanAn.commit();
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer((GravityCompat.START));
        }
        else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itTrangChu:
                FragmentTransaction HienthiBanAn = fragmentManager.beginTransaction();
                HienThiBanAnFagment hienthiBanAnFragment = new HienThiBanAnFagment();
                HienthiBanAn.replace(R.id.content,hienthiBanAnFragment);
                HienthiBanAn.commit();
                item.setCheckable(true);
                drawerLayout.closeDrawers();
                ;break;
            case R.id.itThucDon:
                FragmentTransaction tranHienThiThucDon = fragmentManager.beginTransaction();
                HienThiThucDonFragment hienThiThucDonFragment = new HienThiThucDonFragment();
                tranHienThiThucDon.setCustomAnimations(R.anim.hieuung_activity_vao, R.anim.hieuung_activity_ra);
                tranHienThiThucDon.replace(R.id.content,hienThiThucDonFragment);
                tranHienThiThucDon.commit();
                item.setChecked(true);
                drawerLayout.closeDrawers();
                ;break;

            case R.id.itNhanVien:
                FragmentTransaction tranNhanVien = fragmentManager.beginTransaction();
                HienThiNhanVienFragment hienThiNhanVienFragment = new HienThiNhanVienFragment();
                tranNhanVien.setCustomAnimations(R.anim.hieuung_activity_vao,R.anim.hieuung_activity_ra);
                tranNhanVien.replace(R.id.content,hienThiNhanVienFragment);
                tranNhanVien.commit();

                item.setChecked(true);
                drawerLayout.closeDrawers();
                ;break;
            case R.id.idDangxuat:
                Intent Dangxuat = new Intent(TrangChuActivity.this,DangNhapActivity.class);
                startActivity(Dangxuat);
        }
        return true;
    }
}
