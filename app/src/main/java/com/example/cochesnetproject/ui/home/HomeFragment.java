package com.example.cochesnetproject.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cochesnetproject.R;
import com.example.cochesnetproject.databinding.FragmentHomeBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private ElementosCocheViewModel viewModel;
    private ElementoCocheAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(ElementosCocheViewModel.class);

        configurarRecyclerView();
        configurarFAB();  // Asegurar que el botón flotante funcione
        observarMensajes();

        return binding.getRoot();
    }

    private void configurarRecyclerView() {
        adapter = new ElementoCocheAdapter(new ArrayList<>(), coche -> viewModel.eliminar(coche));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) { return false; }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                adapter.eliminarElemento(position);
            }
        }); itemTouchHelper.attachToRecyclerView(binding.recyclerView);

                // Observa LiveData para actualizar automáticamente el RecyclerView
        viewModel.obtener().observe(getViewLifecycleOwner(), coches -> {
            if (coches != null) {
                adapter.actualizarDatos(coches);
            }
        });
    }

    private void configurarFAB() {
        FloatingActionButton fab = requireActivity().findViewById(R.id.fab_add);
        if (fab != null) {
            fab.setOnClickListener(view -> mostrarDialogoAgregarCoche());
        }
    }

    private void mostrarDialogoAgregarCoche() {
        AgregarCocheDialogFragment dialog = new AgregarCocheDialogFragment();
        dialog.setOnCocheAgregadoListener(coche -> viewModel.insertar(coche));  // Enviar a ViewModel
        dialog.show(getParentFragmentManager(), "AgregarCocheDialog");
    }

    private void observarMensajes() {
        viewModel.obtenerMensaje().observe(getViewLifecycleOwner(), mensaje -> {
            if (mensaje != null) {
                Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show();
            }
        });
    }
}