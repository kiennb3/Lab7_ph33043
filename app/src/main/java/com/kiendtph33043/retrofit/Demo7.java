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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;

import com.kiendtph33043.lab7.R;

import java.lang.ref.Cleaner;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class Demo7 extends AppCompatActivity {

    EditText txtName,txtPrice,txtDes;
    Button btnI,btnU,btnD,btnS;
    TextView txtKQ;
    private Converter.Factory Gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_demo7);

        txtName=findViewById(R.id.demo7TxtName);
        txtPrice=findViewById(R.id.demo7TxtPrice);
        txtDes=findViewById(R.id.demo7TxtDes);
        btnI=findViewById(R.id.btnIn);
        btnU=findViewById(R.id.btnUp);
        btnD=findViewById(R.id.btnDe);
        btnS=findViewById(R.id.btnSe);
        txtKQ=findViewById(R.id.txtKQ);

        btnI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertRetrofit();
            }
        });

    }

    private void insertRetrofit() {
        //b1 tạo đối tượng chứa dữ liệu
        Prd prd= new Prd();
        //b2. đưa dữ liệu vào đối tượng
        prd.setName(txtName.getText().toString());
        prd.setPrice(txtPrice.getText().toString());
        prd.setDescription(txtDes.getText().toString());
        //b3 tạo đối tượng retrofit

        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("http://192.168.81.2/a/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        //b4 gọi hàm insert trong interface
        //4.o tạo đối tượng retrofit
        InterInsertPrd insertPrdObj= retrofit.create(InterInsertPrd.class);
        //4.1 chuẩn bị hàm
        Call<SvrResponPrd> call=insertPrdObj.insertPrd(prd.getName(),prd.getPrice(),prd.getDescription());
        //4.2 thực thi hàm
        call.enqueue(new Callback<SvrResponPrd>() {
            //thành công
            @Override
            public void onResponse(Call<SvrResponPrd> call, Response<SvrResponPrd> response) {
                SvrResponPrd svrResponPrd= response.body();//lấy kết quả mà server trả về
                txtKQ.setText(svrResponPrd.getMessage());
            }
            //thất bại
            @Override
            public void onFailure(Call<SvrResponPrd> call, Throwable t) {
                txtKQ.setText(t.getMessage());
            }
        });
    }
}