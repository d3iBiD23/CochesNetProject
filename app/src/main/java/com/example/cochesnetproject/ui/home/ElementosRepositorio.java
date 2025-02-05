package com.example.cochesnetproject.ui.home;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ElementosRepositorio {
    private final ElementoCocheDao cocheDao;
    private final LiveData<List<ElementoCoche>> coches;
    private final ExecutorService executorService;

    public interface OnDataOperationCallback {
        void onSuccess();
        void onFailure(Exception e);
    }

    public ElementosRepositorio(Application application) {
        ElementoCocheDatabase db = ElementoCocheDatabase.getInstance(application);
        cocheDao = db.cocheDao();
        coches = cocheDao.obtenerTodos();  // LiveData observa automáticamente la BD
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<ElementoCoche>> obtener() {
        return coches; // Devuelve LiveData para que el ViewModel lo observe
    }

    public void insertar(ElementoCoche coche, OnDataOperationCallback callback) {
        executorService.execute(() -> {
            try {
                cocheDao.insertar(coche);
                callback.onSuccess();
            } catch (Exception e) {
                callback.onFailure(e);
            }
        });
    }

    public void eliminar(ElementoCoche coche, OnDataOperationCallback callback) {
        executorService.execute(() -> {
            try {
                cocheDao.eliminar(coche);
                callback.onSuccess();
            } catch (Exception e) {
                callback.onFailure(e);
            }
        });
    }
}