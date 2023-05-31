package com.ahmfarisi.kampuskita.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ahmfarisi.kampuskita.API.APIRequestData;
import com.ahmfarisi.kampuskita.API.RetroServer;
import com.ahmfarisi.kampuskita.Model.ModelResponse;
import com.ahmfarisi.kampuskita.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahActivity extends AppCompatActivity {
    private String yId, yNama, yKota, yAlamat;
    private EditText etNama, etKota, etAlamat;
    private Button btnUbah;
    private String nama, kota, alamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah);

        //tangkap kiriman dari sebelah
        Intent ambil = getIntent();
        yId = ambil.getStringExtra("xId");
        yNama = ambil.getStringExtra("xNama");
        yKota = ambil.getStringExtra("xKota");
        yAlamat= ambil.getStringExtra("xAlamat");

        etNama = findViewById(R.id.et_nama);
        etAlamat = findViewById(R.id.et_alamat);
        etKota = findViewById(R.id.et_kota);
        btnUbah = findViewById(R.id.btn_ubah);

        etNama.setText(yNama);
        etKota.setText(yKota);
        etAlamat.setText(yAlamat);

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = etNama.getText().toString();
                kota = etKota.getText().toString();
                alamat = etAlamat.getText().toString();

                if(nama.trim().isEmpty()){
                    etNama.setError("Nama Tidak Boleh Kosong");
                }
                else if(kota.trim().isEmpty()){
                    etKota.setError("Kota Tidak Boleh Kosong");
                }
                else if (alamat.trim().isEmpty()) {
                    etAlamat.setError("Alamat Tidak Boleh Kosong");
                }
                else{
                    ubahKampus();
                }
            }
        });

    }

    private void ubahKampus(){
        APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = API.ardUpdate(yId, nama, kota, alamat);

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode, pesan;
                kode = response.body().getKode();
                pesan = response.body().getPesan();

                Toast.makeText(UbahActivity.this, "Kode : " + kode + " Pesan : " + pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(UbahActivity.this, "Error: Gagal Menghubungi Server!", Toast.LENGTH_SHORT).show();
                Log.d("DISINI", "Errornya itu: " + t.getMessage());
            }
        });
    }
}