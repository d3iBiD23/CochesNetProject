package com.example.cochesnetproject.ui.home;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.List;

public class ElementosCocheViewModel extends AndroidViewModel {
    private final ElementosRepositorio elementosRepositorio;
    private final LiveData<List<ElementoCoche>> listElementosLiveData;
    private final MutableLiveData<String> mensajeLiveData = new MutableLiveData<>();

    public ElementosCocheViewModel(@NonNull Application application) {
        super(application);
        elementosRepositorio = new ElementosRepositorio(application);
        listElementosLiveData = elementosRepositorio.obtener(); // LiveData se actualiza automáticamente
    }

    public LiveData<List<ElementoCoche>> obtener() {
        return listElementosLiveData;
    }

    public LiveData<String> obtenerMensaje() {
        return mensajeLiveData; // Para mostrar mensajes de éxito o error en la UI
    }

    public void insertar(ElementoCoche coche) {
        elementosRepositorio.insertar(coche, new ElementosRepositorio.OnDataOperationCallback() {
            @Override
            public void onSuccess() {
                mensajeLiveData.postValue("Coche agregado correctamente.");
            }

            @Override
            public void onFailure(Exception e) {
                mensajeLiveData.postValue("Error al agregar el coche: " + e.getMessage());
            }
        });
    }

    public void eliminar(ElementoCoche coche) {
        elementosRepositorio.eliminar(coche, new ElementosRepositorio.OnDataOperationCallback() {
            @Override
            public void onSuccess() {
                mensajeLiveData.postValue("Coche eliminado correctamente.");
            }

            @Override
            public void onFailure(Exception e) {
                mensajeLiveData.postValue("Error al eliminar el coche: " + e.getMessage());
            }
        });
    }
}