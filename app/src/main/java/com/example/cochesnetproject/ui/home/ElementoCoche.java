package com.example.cochesnetproject.ui.home;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "coches")
public class ElementoCoche {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String marca;
    public String modelo;
    public float precio;
    public String descripcion;
    public String imageUrl;

    public ElementoCoche(String marca, String modelo, float precio, String descripcion, String imageUrl) {
        this.marca = marca;
        this.modelo = modelo;
        this.precio = precio;
        this.descripcion = descripcion;
        this.imageUrl = imageUrl;
    }
}