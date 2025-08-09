package com.example.joytec.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.joytec.R;
import com.example.joytec.activities.productos.EditarProductoActivity; // Importa la nueva actividad
import com.example.joytec.models.Producto;

import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {

    private List<Producto> productosList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEliminarClick(Producto producto);
    }

    public ProductoAdapter(List<Producto> productosList, OnItemClickListener listener) {
        this.productosList = productosList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto, parent, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        Producto producto = productosList.get(position);
        holder.textViewNombre.setText(producto.getNombre());
        holder.textViewPrecio.setText("Precio: $" + String.valueOf(producto.getPrecio()));
        holder.textViewCategoria.setText("Categoría: " + producto.getNombre_categoria());

        holder.buttonVerDetalle.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, EditarProductoActivity.class);
            intent.putExtra("PRODUCTO_ID", producto.getId_producto());
            context.startActivity(intent);
        });

        holder.buttonEliminar.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEliminarClick(producto);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productosList.size();
    }

    public static class ProductoViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNombre;
        TextView textViewPrecio;
        TextView textViewCategoria;
        Button buttonVerDetalle;
        Button buttonEliminar;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNombre = itemView.findViewById(R.id.textViewNombre);
            textViewPrecio = itemView.findViewById(R.id.textViewPrecio);
            textViewCategoria = itemView.findViewById(R.id.textViewCategoria);
            buttonVerDetalle = itemView.findViewById(R.id.buttonVerDetalle);
            buttonEliminar = itemView.findViewById(R.id.buttonEliminar);
        }
    }
}