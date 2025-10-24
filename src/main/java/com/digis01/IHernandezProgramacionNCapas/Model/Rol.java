package com.digis01.IHernandezProgramacionNCapas.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Rol 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idrol")
    private int IdRol;
    
    @Column(name = "nombre")
    private String Nombre;
    
    public Rol(){}

    public Rol(int idRol, String nombre) 
    {
        this.IdRol = idRol;
        this.Nombre = nombre;
    }

    public void setIdRol(int idRol) 
    {
        this.IdRol = idRol;
    }
    public int getIdRol() 
    {
        return IdRol;
    }
    
    public void setNombre(String nombre) 
    {
        this.Nombre = nombre;
    }
    public String getNombre() 
    {
        return Nombre;
    }
}