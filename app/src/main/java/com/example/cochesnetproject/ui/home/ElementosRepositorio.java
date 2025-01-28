package com.example.cochesnetproject.ui.home;

import java.util.ArrayList;
import java.util.List;

public class ElementosRepositorio {
    private final List<ElementoCoche> elementos = new ArrayList<>();

    public ElementosRepositorio() {
        // Datos de ejemplo
        elementos.add(new ElementoCoche("Toyota", "Corolla"));
        elementos.add(new ElementoCoche("Honda", "Civic"));
        elementos.add(new ElementoCoche("Ford", "Focus"));
    }

    public List<ElementoCoche> obtener() {
        return new ArrayList<>(elementos);
    }

    public interface Callback {
        void cuandoFinalice(List<ElementoCoche> elementos);
    }

    public void insertar(ElementoCoche elemento, Callback callback) {
        elementos.add(elemento);
        callback.cuandoFinalice(obtener());
    }

    public void eliminar(ElementoCoche elemento, Callback callback) {
        elementos.remove(elemento);
        callback.cuandoFinalice(obtener());
    }

    public void actualizar(ElementoCoche elemento, float precio, Callback callback) {
        int index = elementos.indexOf(elemento);
        if (index != -1) {
            elementos.get(index).precio = precio;
        }
        callback.cuandoFinalice(obtener());
    }
}