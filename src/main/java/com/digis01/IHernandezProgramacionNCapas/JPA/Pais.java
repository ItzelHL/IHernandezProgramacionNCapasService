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
    private int IdPais;
    
    @Column(name = "nombre")
    private String Nombre;
    
    public Pais(){}

    public Pais(int idPais, String nombre) 
    {
        this.IdPais = idPais;
        this.Nombre = nombre;
    }
    
    public void setIdPais(int idPais) 
    {
        this.IdPais = idPais;
    }
    public int getIdPais() 
    {
        return IdPais;
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
