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
import com.example.joytec.models.EmpleadoResponse;

import java.util.List;

public class EmpleadoAdapter extends RecyclerView.Adapter<EmpleadoAdapter.EmpleadoViewHolder> {

    private Context context;
    private List<EmpleadoResponse> empleados;
    private OnEmpleadoClickListener listener;

    public interface OnEmpleadoClickListener {
        void onEditarClick(EmpleadoResponse empleado);
        void onEliminarClick(EmpleadoResponse empleado);
        void onDetallesClick(EmpleadoResponse empleado);
    }

    public EmpleadoAdapter(Context context, List<EmpleadoResponse> empleados) {
        this.context = context;
        this.empleados = empleados;
    }

    public void setOnEmpleadoClickListener(OnEmpleadoClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public EmpleadoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_item_empleado, parent, false);
        return new EmpleadoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmpleadoViewHolder holder, int position) {
        EmpleadoResponse empleado = empleados.get(position);

        holder.tvNombre.setText(empleado.getNombreCompleto());
        holder.tvTelefono.setText("Tel: " + empleado.getTelefono());
        holder.tvSalario.setText("Salario: $" + String.valueOf(empleado.getSalario()));


        holder.btnEliminar.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEliminarClick(empleado);
            }
        });

        holder.btnDetalles.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDetallesClick(empleado);
            }
        });
    }

    @Override
    public int getItemCount() {
        return empleados.size();
    }

    public void updateEmpleados(List<EmpleadoResponse> nuevosEmpleados) {
        this.empleados = nuevosEmpleados;
        notifyDataSetChanged();
    }

    public static class EmpleadoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvTelefono, tvSalario;
        Button btnEditar, btnEliminar, btnDetalles;

        public EmpleadoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombreEmpleado);
            tvTelefono = itemView.findViewById(R.id.tvTelefonoEmpleado);
            tvSalario = itemView.findViewById(R.id.tvSalarioEmpleado);
            btnEditar = itemView.findViewById(R.id.btnEditarEmpleado);
            btnEliminar = itemView.findViewById(R.id.btnEliminarEmpleado);
            btnDetalles = itemView.findViewById(R.id.btnDetallesEmpleado);
        }
    }
}