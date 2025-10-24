package com.digis01.IHernandezProgramacionNCapas.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class Estado 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idestado")
    private int idEstado;
    
    @Column(name = "nombre")
    private String Nombre;
    
    @ManyToOne
    @JoinColumn(name = "idpais")
    public Pais pais;
    
    public Estado(){}

    public Estado(int idEstado, String nombre) 
    {
        this.idEstado = idEstado;
        this.Nombre = nombre;
    }

    public Estado(int idEstado, String nombre, Pais pais) 
    {
        this.idEstado = idEstado;
        this.Nombre = nombre;
        this.pais = pais;
    }
    
    public void setIdEstado(int idEstado) 
    {
        this.idEstado = idEstado;
    }
    public int getIdEstado() 
    {
        return idEstado;
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