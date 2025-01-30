package com.example.cochesnetproject.ui.home;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.cochesnetproject.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.jetbrains.annotations.Nullable;

public class AgregarCocheDialogFragment extends DialogFragment {
    private EditText marcaInput, modeloInput, precioInput, descripcionInput;
    private ImageView imagePreview;
    private Button btnSelectImage;
    private Uri imagenSeleccionadaUri = null;

    private static final int REQUEST_CODE_PICK_IMAGE = 100;

    private OnCocheAgregadoListener listener;

    public interface OnCocheAgregadoListener {
        void onCocheAgregado(ElementoCoche coche);
    }

    public void setOnCocheAgregadoListener(OnCocheAgregadoListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_coche, null);

        marcaInput = view.findViewById(R.id.input_marca);
        modeloInput = view.findViewById(R.id.input_modelo);
        precioInput = view.findViewById(R.id.input_precio);
        descripcionInput = view.findViewById(R.id.input_descripcion);
        imagePreview = view.findViewById(R.id.image_preview);
        btnSelectImage = view.findViewById(R.id.btn_select_image);

        btnSelectImage.setOnClickListener(v -> seleccionarImagen());

        return new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Añadir Coche")
                .setView(view)
                .setPositiveButton("Agregar", (dialog, which) -> agregarCoche())
                .setNegativeButton("Cancelar", null)
                .create();
    }

    private void seleccionarImagen() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            imagenSeleccionadaUri = data.getData();

            if (imagenSeleccionadaUri != null) {
                imagePreview.setImageURI(imagenSeleccionadaUri);
            } else {
                Toast.makeText(getContext(), "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void agregarCoche() {
        String marca = marcaInput.getText().toString().trim();
        String modelo = modeloInput.getText().toString().trim();
        String precioStr = precioInput.getText().toString().trim();
        String descripcion = descripcionInput.getText().toString().trim();

        if (!marca.isEmpty() && !modelo.isEmpty() && !precioStr.isEmpty() && imagenSeleccionadaUri != null) {
            float precio = Float.parseFloat(precioStr);
            ElementoCoche nuevoCoche = new ElementoCoche(marca, modelo, precio, descripcion, imagenSeleccionadaUri.toString());

            if (listener != null) {
                listener.onCocheAgregado(nuevoCoche);
            }

            Toast.makeText(getContext(), "Coche añadido", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Todos los campos y la imagen son obligatorios", Toast.LENGTH_SHORT).show();
        }
    }
}
