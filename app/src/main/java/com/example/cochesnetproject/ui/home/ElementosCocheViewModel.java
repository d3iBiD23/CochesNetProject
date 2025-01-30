package com.example.cochesnetproject.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ElementosCocheViewModel extends AndroidViewModel {
    private final ElementosRepositorio elementosRepositorio;
    private final LiveData<List<ElementoCoche>> listElementosLiveData;

    public ElementosCocheViewModel(@NonNull Application application) {
        super(application);
        elementosRepositorio = new ElementosRepositorio(application);
        listElementosLiveData = elementosRepositorio.obtener();
    }

    public LiveData<List<ElementoCoche>> obtener() {
        return listElementosLiveData;
    }

    public void insertar(ElementoCoche coche) {
        elementosRepositorio.insertar(coche);
    }

    public void eliminar(ElementoCoche coche) {
        elementosRepositorio.eliminar(coche);
    }
}
