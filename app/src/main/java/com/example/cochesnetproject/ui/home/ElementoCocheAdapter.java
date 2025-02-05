package com.example.cochesnetproject.ui.home;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cochesnetproject.R;
import com.example.cochesnetproject.databinding.ViewholderElementoBinding;

import java.util.List;

import com.bumptech.glide.Glide;

public class ElementoCocheAdapter extends RecyclerView.Adapter<ElementoCocheAdapter.ElementoViewHolder> {

    private List<ElementoCoche> elementosCoche;
    private OnItemSwipedListener listener;

    public interface OnItemSwipedListener {
        void onItemSwiped(ElementoCoche coche);
    }

    public ElementoCocheAdapter(List<ElementoCoche> elementosCoche, OnItemSwipedListener listener) {
        this.elementosCoche = elementosCoche;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ElementoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new CocheDiffCallback(this.elementosCoche, nuevosElementos));
        this.elementosCoche = nuevosElementos;
        diffResult.dispatchUpdatesTo(this);
    }


    public void eliminarElemento(int position) {
        listener.onItemSwiped(elementosCoche.get(position));
    }

    static class ElementoViewHolder extends RecyclerView.ViewHolder {
        private final ViewholderElementoBinding binding;

        public ElementoViewHolder(ViewholderElementoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ElementoCoche elementoCoche) {
            binding.marca.setText(elementoCoche.marca + " " + elementoCoche.modelo);
            binding.precio.setText(String.format("Precio: %.2f €", elementoCoche.precio));

            // Cargar la imagen desde la URI almacenada
            Glide.with(binding.fotoCoche.getContext())
                    .load(elementoCoche.imageUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_home_black_24dp)
                    .into(binding.fotoCoche);
        }

    }
}
