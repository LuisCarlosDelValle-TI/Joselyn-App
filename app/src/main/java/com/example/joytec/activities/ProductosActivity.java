package com.example.joytec.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.joytec.R;

public class ProductosActivity extends AppCompatActivity {

    private Button btnRegistrarProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_producto);

        btnRegistrarProducto = findViewById(R.id.btnAgregarProducto);

        btnRegistrarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent producto = new Intent(ProductosActivity.this, RegistroProductosActivity.class);
                startActivity(producto);
            }
        });
    }
}