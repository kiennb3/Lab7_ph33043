package com.kiendtph33043.retrofit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.kiendtph33043.lab7.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Demo71 extends AppCompatActivity {
    EditText txtName,txtPrice,txtDes;
    Button btnI,btnU,btnD,btnS;
    TextView tvKQ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_demo71);
        txtName=findViewById(R.id.demo71TxtName);
        txtPrice=findViewById(R.id.demo71TxtPrice);
        txtDes=findViewById(R.id.demo71TxtDes);
        btnI=findViewById(R.id.demo71BtnInsert);
        btnU=findViewById(R.id.demo71BtnUpdate);
        btnD=findViewById(R.id.demo71BtnDelete);
        btnS=findViewById(R.id.demo71BtnSelect);
        tvKQ=findViewById(R.id.demo71TvKQ);
        btnI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertRetrofit();
            }
        });
        btnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectRetrofit();
            }
        });
    }
    String strKQ="";
    List<Prod> ls;
    private void selectRetrofit() {
        //b1. tao doi tuong Retrofit
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.1.102/a/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //b2. goi ham select trong interface
        //b2.1. tao doi tuong
        InterSelectProd interSelectProd
                =retrofit.create(InterSelectProd.class);
        //b2.2 chuan bi ham
        Call<SvrResponseProd> call=interSelectProd.getProd();
        //b2.3 thuc thi ham
        call.enqueue(new Callback<SvrResponseProd>() {
            //thanh cong
            @Override
            public void onResponse(Call<SvrResponseProd> call, Response<SvrResponseProd> response) {
                SvrResponseProd svrResponseProd=response.body();//lay ket qua tra ve tu server
                //convert sang dang list
                ls=new ArrayList<>(Arrays.asList(svrResponseProd.getProducts()));
                for(Prod p: ls )//doc du lieu tu list va dua vao chuoi
                {
                    strKQ += "Id: "+p.getPid()+"; Name: "+p.getName()+"; Price: "+p.getPrice()+"\n";
                }
                //dua ket qua len man hinh
                tvKQ.setText(strKQ);
            }
            //that bai
            @Override
            public void onFailure(Call<SvrResponseProd> call, Throwable t) {
                tvKQ.setText(t.getMessage());
            }
        });

    }

    private void insertRetrofit() {
        //b1. Tao doi tuong chua du lieu
        Prd prd=new Prd();
        //b2. dua du lieu vao doi tuong
        prd.setName(txtName.getText().toString());
        prd.setPrice(txtPrice.getText().toString());
        prd.setDescription(txtDes.getText().toString());
        //b3. tao doi tuong retrofit
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.1.102/a/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        //b4. goi ham insert trong interface
        //4.0. Tao doi tuong kieu retrofit
        InterInsertPrd insertPrdObj=retrofit.create(InterInsertPrd.class);
        //4.1. chuan bi ham
        Call<SvrResponPrd> call=insertPrdObj.insertPrd(prd.getName(),prd.getPrice(),prd.getDescription());
        //4.2 thuc thi ham
        call.enqueue(new Callback<SvrResponPrd>() {
            //thanh cong
            @Override
            public void onResponse(Call<SvrResponPrd> call, Response<SvrResponPrd> response) {
                SvrResponPrd svrResponsePrd=response.body();//lay ket qua ma server tra ve
                tvKQ.setText(svrResponsePrd.getMessage());
            }
            //that bai
            @Override
            public void onFailure(Call<SvrResponPrd> call, Throwable t) {
                tvKQ.setText(t.getMessage());
            }
        });
    }
    }
