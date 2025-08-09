package com.example.joytec.activities.productos;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.joytec.R;
import com.example.joytec.models.Categoria;
import com.example.joytec.models.Producto;
import com.example.joytec.network.ApiClient;
import com.example.joytec.network.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormularioProductoActivity extends AppCompatActivity {

    private EditText etNombre, etPrecio, etStock, etExistencias;
    private Spinner spnCategoria, spnMaterial;
    private Button btnGuardar;
    private ApiService apiService;


    private List<Categoria> categoriasList = new ArrayList<>();


    private List<String> nombresMateriales = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_producto);

        apiService = ApiClient.getApiService();

        etNombre = findViewById(R.id.etNombre);
        etPrecio = findViewById(R.id.etPrecio);
        etStock = findViewById(R.id.etStock);
        etExistencias = findViewById(R.id.etExistencias);
        spnCategoria = findViewById(R.id.spnCategoria);
        spnMaterial = findViewById(R.id.spnMaterial);
        btnGuardar = findViewById(R.id.btnGuardar);


        categoriasList.add(new Categoria(1, "Anillo"));
        categoriasList.add(new Categoria(2, "Pulsera"));
        categoriasList.add(new Categoria(3, "Collar"));
        categoriasList.add(new Categoria(4, "Arete"));


        List<String> nombresCategorias = new ArrayList<>();
        for (Categoria categoria : categoriasList) {
            nombresCategorias.add(categoria.getNombre_categoria());
        }
        ArrayAdapter<String> adapterCategorias = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, nombresCategorias);
        adapterCategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCategoria.setAdapter(adapterCategorias);


        nombresMateriales.add("Plata");
        nombresMateriales.add("Oro");
        nombresMateriales.add("Acero");
        nombresMateriales.add("Titanio");


        ArrayAdapter<String> adapterMateriales = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, nombresMateriales);
        adapterMateriales.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMaterial.setAdapter(adapterMateriales);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarProducto();
            }
        });
    }

    private void guardarProducto() {

        if (etNombre.getText().toString().isEmpty() || etPrecio.getText().toString().isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        String nombre = etNombre.getText().toString();
        double precio = Double.parseDouble(etPrecio.getText().toString());
        int stock = Integer.parseInt(etStock.getText().toString());
        int existencias = Integer.parseInt(etExistencias.getText().toString());
        int selectedCategoriaPosition = spnCategoria.getSelectedItemPosition();
        int idCategoria = categoriasList.get(selectedCategoriaPosition).getId_categoria();


        String nombreMaterial = spnMaterial.getSelectedItem().toString();

        Producto nuevoProducto = new Producto();
        nuevoProducto.setNombre(nombre);
        nuevoProducto.setPrecio(precio);
        nuevoProducto.setStock(stock);
        nuevoProducto.setExistencias(existencias);
        nuevoProducto.setId_categoria(idCategoria);
        nuevoProducto.setNombre_material(nombreMaterial);

        apiService.crearProducto(nuevoProducto).enqueue(new Callback<Producto>() {
            @Override
            public void onResponse(Call<Producto> call, Response<Producto> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(FormularioProductoActivity.this, "Producto registrado correctamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FormularioProductoActivity.this, ProductosActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(FormularioProductoActivity.this, "Error al registrar producto: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Producto> call, Throwable t) {
                Toast.makeText(FormularioProductoActivity.this, "Fallo en la conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}