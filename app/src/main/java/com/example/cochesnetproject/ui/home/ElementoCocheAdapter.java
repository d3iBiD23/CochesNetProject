package com.example.cochesnetproject.ui.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cochesnetproject.databinding.ViewholderElementoBinding;

import java.util.List;

public class ElementoCocheAdapter extends RecyclerView.Adapter<ElementoCocheAdapter.ElementoViewHolder> {

    private List<ElementoCoche> elementosCoche;

    public ElementoCocheAdapter(List<ElementoCoche> elementosCoche) {
        this.elementosCoche = elementosCoche;
    }

    @NonNull
    @Override
    public ElementoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Usamos ViewBinding para inflar el layout
        ViewholderElementoBinding binding = ViewholderElementoBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new ElementoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ElementoViewHolder holder, int position) {
        ElementoCoche elementoCoche = elementosCoche.get(position);
        holder.bind(elementoCoche);
    }

    @Override
    public int getItemCount() {
        return elementosCoche != null ? elementosCoche.size() : 0;
    }

    public void actualizarDatos(List<ElementoCoche> nuevosElementos) {
        this.elementosCoche = nuevosElementos;
        notifyDataSetChanged();
    }

    static class ElementoViewHolder extends RecyclerView.ViewHolder {
        private final ViewholderElementoBinding binding;

        public ElementoViewHolder(ViewholderElementoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ElementoCoche elementoCoche) {
            binding.marca.setText(String.format("%s %s", elementoCoche.marca, elementoCoche.modelo));
            binding.precio.setRating(elementoCoche.precio / 10); // Asumiendo que el precio se escala a 5 estrellas
        }
    }
}
