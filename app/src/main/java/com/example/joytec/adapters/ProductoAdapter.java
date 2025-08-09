package com.example.joytec.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.joytec.R;
import com.example.joytec.models.ProductoResponse;

import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {

    private Context context;
    private List<ProductoResponse> productos;
    private OnProductoClickListener listener;

    public interface OnProductoClickListener {
        void onEditarClick(ProductoResponse producto);
        void onEliminarClick(ProductoResponse producto);
        void onDetallesClick(ProductoResponse producto);
    }

    public ProductoAdapter(Context context, List<ProductoResponse> productos) {
        this.context = context;
        this.productos = productos;
    }

    public void setOnProductoClickListener(OnProductoClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_item_producto, parent, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        ProductoResponse producto = productos.get(position);

        holder.tvNombre.setText(producto.getNombre());
        holder.tvPrecio.setText("$" + String.valueOf(producto.getPrecio()));
        holder.tvStock.setText("Stock: " + producto.getExistencias());
        holder.tvCategoria.setText("Categoría: " + (producto.getNombre_categoria() != null ? producto.getNombre_categoria() : "Sin categoría"));

        if (producto.getNombre_material() != null) {
            holder.tvMaterial.setText("Material: " + producto.getNombre_material());
            holder.tvMaterial.setVisibility(View.VISIBLE);
        } else {
            holder.tvMaterial.setVisibility(View.GONE);
        }


        holder.btnEditar.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditarClick(producto);
            }
        });

        holder.btnEliminar.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEliminarClick(producto);
            }
        });

        holder.btnDetalles.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDetallesClick(producto);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public void updateProductos(List<ProductoResponse> nuevosProductos) {
        this.productos = nuevosProductos;
        notifyDataSetChanged();
    }

    public static class ProductoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvPrecio, tvStock, tvCategoria, tvMaterial;
        Button btnEditar, btnEliminar, btnDetalles;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombreProducto);
            tvPrecio = itemView.findViewById(R.id.tvPrecioProducto);
            tvStock = itemView.findViewById(R.id.tvStockProducto);;
            btnEditar = itemView.findViewById(R.id.btnEditarProducto);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);

        }
    }
}