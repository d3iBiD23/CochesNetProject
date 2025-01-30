package com.example.cochesnetproject.ui.home;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cochesnetproject.R;
import com.example.cochesnetproject.databinding.FragmentHomeBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ElementosCocheViewModel viewModel;
    private ElementoCocheAdapter adapter;
    private Uri imagenSeleccionadaUri = null;

    private ImageView imagePreview;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(ElementosCocheViewModel.class);

        configurarRecyclerView();
        configurarFAB();

        return binding.getRoot();
    }

    private void configurarRecyclerView() {
        adapter = new ElementoCocheAdapter(new ArrayList<>(), coche -> viewModel.eliminar(coche));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        viewModel.obtener().observe(getViewLifecycleOwner(), coches -> adapter.actualizarDatos(coches));
    }

    private void configurarFAB() {
        requireActivity().findViewById(R.id.fab_add).setOnClickListener(view -> mostrarDialogoAgregarCoche());    }

    private void seleccionarImagen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_MEDIA_IMAGES)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 101);
                return;
            }
        } else {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
                return;
            }
        }

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, 100);
    }


    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, 100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                abrirGaleria(); // Si el usuario acepta, abrir la galería
            } else {
                Toast.makeText(getContext(), "Permiso denegado, no se puede seleccionar imagen", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void mostrarDialogoAgregarCoche() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_coche, null);
        EditText marcaInput = dialogView.findViewById(R.id.input_marca);
        EditText modeloInput = dialogView.findViewById(R.id.input_modelo);
        EditText precioInput = dialogView.findViewById(R.id.input_precio);
        EditText descripcionInput = dialogView.findViewById(R.id.input_descripcion);
        imagePreview = dialogView.findViewById(R.id.image_preview); // Guardar referencia
        Button btnSelectImage = dialogView.findViewById(R.id.btn_select_image);

        btnSelectImage.setOnClickListener(v -> seleccionarImagen());

        new MaterialAlertDialogBuilder(getContext())
                .setTitle("Añadir Coche")
                .setView(dialogView)
                .setPositiveButton("Agregar", (dialog, which) -> {
                    String marca = marcaInput.getText().toString().trim();
                    String modelo = modeloInput.getText().toString().trim();
                    String precioStr = precioInput.getText().toString().trim();
                    String descripcion = descripcionInput.getText().toString().trim();

                    if (!marca.isEmpty() && !modelo.isEmpty() && !precioStr.isEmpty() && imagenSeleccionadaUri != null) {
                        float precio = Float.parseFloat(precioStr);
                        ElementoCoche nuevoCoche = new ElementoCoche(marca, modelo, precio, descripcion, imagenSeleccionadaUri.toString());
                        viewModel.insertar(nuevoCoche);
                        Toast.makeText(getContext(), "Coche añadido", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Todos los campos y la imagen son obligatorios", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK && data != null) {
            imagenSeleccionadaUri = data.getData();

            if (imagePreview != null) { // Evita la excepción asegurándote de que no sea null
                imagePreview.setImageURI(imagenSeleccionadaUri);
            }
        }
    }

}
