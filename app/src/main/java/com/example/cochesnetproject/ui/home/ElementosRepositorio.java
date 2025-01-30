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

    public ElementosRepositorio(Application application) {
        ElementoCocheDatabase db = ElementoCocheDatabase.getInstance(application);
        cocheDao = db.cocheDao();
        coches = cocheDao.obtenerTodos();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<ElementoCoche>> obtener() {
        return coches;
    }

    public void insertar(ElementoCoche coche) {
        executorService.execute(() -> cocheDao.insertar(coche));
    }

    public void eliminar(ElementoCoche coche) {
        executorService.execute(() -> cocheDao.eliminar(coche));
    }
}