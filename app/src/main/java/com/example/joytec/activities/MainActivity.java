package com.example.joytec;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.joytec.activities.LoginActivity;
import com.example.joytec.activities.empleados.EmpleadosActivity;
import com.example.joytec.activities.productos.ProductosActivity;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar vistas
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        // Configurar toolbar
        setSupportActionBar(toolbar);

        // Configurar navigation drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Configurar listener del navigation view
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_categorias) {
            Toast.makeText(this, "Categorías - Por implementar", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_clientes) {
            Toast.makeText(this, "Clientes - Por implementar", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_empleados) {
            Intent intent = new Intent(this, EmpleadosActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_productos) {
            Intent intent = new Intent(this, ProductosActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_proveedores) {
            Toast.makeText(this, "Proveedores - Por implementar", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_usuarios) {
            Toast.makeText(this, "Usuarios - Por implementar", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_compras) {
            Toast.makeText(this, "Compras - Por implementar", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_ventas) {
            Toast.makeText(this, "Ventas - Por implementar", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_logout) {
            // FUNCIONALIDAD DE CERRAR SESIÓN
            cerrarSesion();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void cerrarSesion() {
        // Limpiar datos de sesión
        SharedPreferences sharedPreferences = getSharedPreferences("user_session", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show();

        // Volver a LoginActivity
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}