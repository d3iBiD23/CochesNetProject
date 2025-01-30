package com.example.cochesnetproject.ui.home;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ElementoCocheDao {
    @Insert
    void insertar(ElementoCoche coche);

    @Delete
    void eliminar(ElementoCoche coche);

    @Query("SELECT * FROM coches")
    LiveData<List<ElementoCoche>> obtenerTodos();
}
