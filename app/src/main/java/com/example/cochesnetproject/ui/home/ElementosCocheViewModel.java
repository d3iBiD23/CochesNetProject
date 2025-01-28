package com.example.cochesnetproject.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class ElementosCocheViewModel extends AndroidViewModel {
    ElementosRepositorio elementosRepositorio;
    MutableLiveData<List<ElementoCoche>> listElementosMutableLiveData = new MutableLiveData<>();

    public ElementosCocheViewModel(@NonNull Application application) {
        super(application);

        elementosRepositorio = new ElementosRepositorio();
        listElementosMutableLiveData.setValue(elementosRepositorio.obtener());
    }

    MutableLiveData<List<ElementoCoche>> obtener() {
        return listElementosMutableLiveData;
    }

    void insertar(ElementoCoche elementoCoche) {
        elementosRepositorio.insertar(elementoCoche, new ElementosRepositorio.Callback() {
            @Override
            public void cuandoFinalice(List<ElementoCoche> elementos) {
                listElementosMutableLiveData.setValue(elementos);
            }
        });
    }

    void eliminar(ElementoCoche elementoCoche) {
        elementosRepositorio.eliminar(elementoCoche, new ElementosRepositorio.Callback() {
            @Override
            public void cuandoFinalice(List<ElementoCoche> elementos) {
                listElementosMutableLiveData.setValue(elementos);
            }
        });
    }

    void actualizar(ElementoCoche elementoCoche, float precio) {
        elementosRepositorio.actualizar(elementoCoche, precio, new ElementosRepositorio.Callback() {
            @Override
            public void cuandoFinalice(List<ElementoCoche> elementos) {
                listElementosMutableLiveData.setValue(elementos);
            }
        });
    }
}
