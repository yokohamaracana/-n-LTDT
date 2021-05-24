package com.example.qunlqunn;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.qunlqunn.DAO.NhanVienDAO;
import com.example.qunlqunn.DAO.QuyenDAO;
import com.example.qunlqunn.DTO.NhanVienDTO;
import com.example.qunlqunn.DTO.QuyenDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DangKyActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edTenDangNhapDK, edMatKhauDK, edNgaySinhDK,edSDT;
    Button btnDongYDK, btnThoatDK;
    RadioGroup rgGioiTinh;
    RadioButton rdNam,rdNu;
    TextView txtTieuDeDangKy;
    String sGioiTinh;
    Spinner spinQuyen;
    NhanVienDAO nhanVienDAO;
    QuyenDAO quyenDAO;
    int manv = 0;
    List<QuyenDTO> quyenDTOList;
    List<String> dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangky);

        edTenDangNhapDK = findViewById(R.id.edTenDangNhapDK);
        edMatKhauDK = findViewById(R.id.edMatKhauDK);
        edNgaySinhDK = findViewById(R.id.edNgaySinhDK);
        txtTieuDeDangKy = findViewById(R.id.txtTieuDeDangKy);
        rdNam = findViewById(R.id.rdNam);
        rdNu = findViewById(R.id.rdNu);
        edSDT = findViewById(R.id.edSDT);
        btnDongYDK = findViewById(R.id.btnDongYDK);
        btnThoatDK = findViewById(R.id.btnThoatDK);
        rgGioiTinh = findViewById(R.id.rgGioiTinh);
        spinQuyen = findViewById(R.id.spinQuyen);
        btnDongYDK.setOnClickListener(this);
        btnThoatDK.setOnClickListener(this);
        edNgaySinhDK.setOnClickListener(this);

        nhanVienDAO = new NhanVienDAO(this);
        quyenDAO = new QuyenDAO(this);

        quyenDTOList = quyenDAO.LayDanhSachQuyen();
        dataAdapter = new ArrayList<String>();

        for (int i = 0; i < quyenDTOList.size(); i++) {
            String tenquyen = quyenDTOList.get(i).getTenQuyen();
            dataAdapter.add(tenquyen);
        }

        manv = getIntent().getIntExtra("manv", 0);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataAdapter);
        spinQuyen.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        if (manv != 0) {
            txtTieuDeDangKy.setText(getResources().getString(R.string.capnhatnhanvien));
            NhanVienDTO nhanVienDTO = nhanVienDAO.LayDanhSachNhanVienTheoMa(manv);

            edTenDangNhapDK.setText(nhanVienDTO.getTENDN());
            edMatKhauDK.setText(nhanVienDTO.getMATKHAU());
            edNgaySinhDK.setText(nhanVienDTO.getNGAYSINH());
            edSDT.setText(String.valueOf(nhanVienDTO.getSDT()));

            String gioitinh = nhanVienDTO.getGIOITINH();
            if (gioitinh.equals("Nam")) {
                rdNam.setChecked(true);
            } else {
                rdNu.setChecked(true);
            }

            edNgaySinhDK.setText(nhanVienDTO.getNGAYSINH());
            edSDT.setText(String.valueOf(nhanVienDTO.getSDT()));
        }
    }
    private void DongYThemNhanVien(){
        String sTenDangNhap = edTenDangNhapDK.getText().toString();
        String sMatKhau = edMatKhauDK.getText().toString();
        switch (rgGioiTinh.getCheckedRadioButtonId()){
            case R.id.rdNam:
                sGioiTinh = "Nam";
                break;
            case R.id.rdNu:
                sGioiTinh = "Nữ";
                break;
        }
        String sNgaySinh = edNgaySinhDK.getText().toString();
        String sSDT = edSDT.getText().toString();

        if(sTenDangNhap == null || sTenDangNhap.equals("")){
            Toast.makeText(DangKyActivity.this,getResources().getString(R.string.loinhaptendangnhap), Toast.LENGTH_SHORT).show();
        }else if(sMatKhau == null || sMatKhau.equals("")){
            Toast.makeText(DangKyActivity.this,getResources().getString(R.string.loinhapmatkhau), Toast.LENGTH_SHORT).show();
        }else{
            NhanVienDTO nhanVienDTO = new NhanVienDTO();
            nhanVienDTO.setTENDN(sTenDangNhap);
            nhanVienDTO.setMATKHAU(sMatKhau);
            nhanVienDTO.setGIOITINH(sGioiTinh);
            nhanVienDTO.setNGAYSINH(sNgaySinh);
            nhanVienDTO.setSDT(sSDT);

            int vitri = spinQuyen.getSelectedItemPosition();
            int maquyen = quyenDTOList.get(vitri).getMaQuyen();
            nhanVienDTO.setMAQUYEN(maquyen);

            long kiemtra = nhanVienDAO.ThemNhanVien(nhanVienDTO);
            if(kiemtra != 0){
                Toast.makeText(DangKyActivity.this,getResources().getString(R.string.themthanhcong), Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(DangKyActivity.this,getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
            }

            DangNhap();
        }
    }

    private void SuaNhanVien(){
        String sTenDangNhap = edTenDangNhapDK.getText().toString();
        String sMatKhau = edMatKhauDK.getText().toString();
        String sNgaySinh = edNgaySinhDK.getText().toString();
        String sSDT = edSDT.getText().toString();
        switch (rgGioiTinh.getCheckedRadioButtonId()){
            case R.id.rdNam:
                sGioiTinh = "Nam";
                break;
            case R.id.rdNu:
                sGioiTinh = "Nữ";
                break;
        }

        NhanVienDTO nhanVienDTO = new NhanVienDTO();
        nhanVienDTO.setMANV(manv);
        nhanVienDTO.setTENDN(sTenDangNhap);
        nhanVienDTO.setMATKHAU(sMatKhau);
        nhanVienDTO.setSDT(sSDT);
        nhanVienDTO.setNGAYSINH(sNgaySinh);
        nhanVienDTO.setGIOITINH(sGioiTinh);

        boolean kiemtra = nhanVienDAO.SuaNhanVien(nhanVienDTO);
        if(kiemtra){
            Toast.makeText(DangKyActivity.this,getResources().getString(R.string.suathanhcong),Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(DangKyActivity.this,getResources().getString(R.string.loi),Toast.LENGTH_SHORT).show();
        }
    }
    private void DangNhap(){
        Intent iDangNhap = new Intent(DangKyActivity.this,DangNhapActivity.class);
        startActivity(iDangNhap);
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnDongYDK:
                if(manv != 0){
                    SuaNhanVien();
                }else{
                    DongYThemNhanVien();
                }
                ;break;
            case R.id.btnThoatDK:
                finish();
                break;
            case R.id.edNgaySinhDK:
                ChooseDay();
                break;
        }
    }

    @SuppressLint({"NewApi", "LocalSuppress", "SimpleDateFormat"})
    public void ChooseDay(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            if (!edNgaySinhDK.getText().toString().equals(""))
                cal.setTime(sdf.parse(edNgaySinhDK.getText().toString()));
            else
                cal.getTime();

            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog, (datePicker, yearSelected, monthSelected, daySelected) -> {
                monthSelected = monthSelected + 1;
                Date date = StringToDate(daySelected + "/" + monthSelected + "/" + yearSelected, "dd/MM/yyyy");
                edNgaySinhDK.setText(new SimpleDateFormat("dd/MM/yyyy").format(date));
            }, year, month, day);

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setTitle("Chọn ngày sinh");

            dialog.show();
        } catch (ParseException e) {
            Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SimpleDateFormat")
    public Date StringToDate(String dob, String format){
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            return formatter.parse(dob);
        } catch (ParseException e) {
            Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }

}
