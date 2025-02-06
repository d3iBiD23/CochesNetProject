package com.example.cochesnetproject.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.cochesnetproject.databinding.FragmentDashboardBinding;
import com.example.cochesnetproject.ui.home.ElementoCoche;
import com.example.cochesnetproject.ui.home.ElementoCocheAdapter;
import com.example.cochesnetproject.ui.home.ElementosCocheViewModel;
import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {
    private FragmentDashboardBinding binding;
    private ElementosCocheViewModel viewModel;
    private ElementoCocheAdapter adapter;
    private List<ElementoCoche> listaOriginal = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(ElementosCocheViewModel.class);

        configurarRecyclerView();
        configurarBuscador();

        return binding.getRoot();
    }

    private void configurarRecyclerView() {
        adapter = new ElementoCocheAdapter(new ArrayList<>(), coche -> {});
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        // Ocultar RecyclerView al inicio
        binding.recyclerView.setVisibility(View.GONE);

        // Obtener la lista original de la base de datos
        viewModel.obtener().observe(getViewLifecycleOwner(), coches -> {
            if (coches != null) {
                listaOriginal = new ArrayList<>(coches);
            }
        });
    }

    private void configurarBuscador() {
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    adapter.actualizarDatos(new ArrayList<>());  // Limpiar la lista
                    binding.recyclerView.setVisibility(View.GONE); // Ocultar RecyclerView
                } else {
                    filtrarCoches(newText);
                }
                return false;
            }
        });
    }

    private void filtrarCoches(String query) {
        if (listaOriginal == null) {
            Toast.makeText(getContext(), "No hay datos disponibles", Toast.LENGTH_SHORT).show();
            return;
        }

        List<ElementoCoche> cochesFiltrados = new ArrayList<>();
        for (ElementoCoche coche : listaOriginal) {
            if (coche.marca.toLowerCase().contains(query.toLowerCase()) ||
                    coche.modelo.toLowerCase().contains(query.toLowerCase())) {
                cochesFiltrados.add(coche);
            }
        }

        adapter.actualizarDatos(cochesFiltrados);

        // Mostrar RecyclerView solo si hay resultados
        if (!cochesFiltrados.isEmpty()) {
            binding.recyclerView.setVisibility(View.VISIBLE);
        } else {
            binding.recyclerView.setVisibility(View.GONE);
        }
    }
}