package com.example.joytec.activities.clientes;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import com.example.joytec.activities.productos.ProductosActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import com.example.joytec.R;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.joytec.activities.empleados.EmpleadosActivity;

public class ItemListClientes extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_list_clientes);

        // Configura Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Drawer y NavigationView
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);

        // Ícono de hamburguesa
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.morado_josselyn));


        // Botones
        Button btnAgregarCliente = findViewById(R.id.btnAgregarCliente);
        btnAgregarCliente.setOnClickListener(v -> {
            Intent intent = new Intent(ItemListClientes.this, RegistroClienteActivity.class);
            startActivity(intent);
        });

        Button btnDetallesCliente = findViewById(R.id.btnDetallesCliente);
        btnDetallesCliente.setOnClickListener(v -> {
            Intent intent = new Intent(ItemListClientes.this, DetallesCliente.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_empleados) {
            startActivity(new Intent(this, EmpleadosActivity.class));
        } else if (id == R.id.nav_productos) {
            startActivity(new Intent(this, ProductosActivity.class));
        } else if (id == R.id.nav_clientes) {
            startActivity(new Intent(this, ItemListClientes.class)); // ya estás aquí, se puede omitir o refrescar
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
