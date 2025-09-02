package com.digis01.IHernandezProgramacionNCapas.JPA;

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
    private int IdEstado;
    
    @Column(name = "nombre")
    private String Nombre;
    
    @ManyToOne
    @JoinColumn(name = "idpais")
    public Pais Pais;
    
    public Estado(){}

    public Estado(int idEstado, String nombre) 
    {
        this.IdEstado = idEstado;
        this.Nombre = nombre;
    }

    public Estado(int idEstado, String nombre, Pais pais) 
    {
        this.IdEstado = idEstado;
        this.Nombre = nombre;
        this.Pais = pais;
    }
    
    public void setIdEstado(int idEstado) 
    {
        this.IdEstado = idEstado;
    }
    public int getIdEstado() 
    {
        return IdEstado;
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
