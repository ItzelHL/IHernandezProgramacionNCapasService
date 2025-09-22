package com.digis01.IHernandezProgramacionNCapas.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Pais 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpais")
    private int idPais;
    
    @Column(name = "nombre")
    private String Nombre;
    
    public Pais(){}

    public Pais(int idPais, String nombre) 
    {
        this.idPais = idPais;
        this.Nombre = nombre;
    }
    
    public void setIdPais(int idPais) 
    {
        this.idPais = idPais;
    }
    public int getIdPais() 
    {
        return idPais;
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
