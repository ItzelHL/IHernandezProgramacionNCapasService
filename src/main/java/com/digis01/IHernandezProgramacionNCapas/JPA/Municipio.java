package com.digis01.IHernandezProgramacionNCapas.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class Municipio 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idmunicipio")
    private int idMunicipio;
    
    @Column(name = "nombre")
    private String Nombre;
    
    @ManyToOne
    @JoinColumn(name = "idestado")
    public Estado estado;
    
    public Municipio(){}

    public Municipio(int idMunicipio, String nombre)
    {
        this.idMunicipio = idMunicipio;
        this.Nombre = nombre;
    }

    public Municipio(int idMunicipio, String nombre, Estado estado) 
    {
        this.idMunicipio = idMunicipio;
        this.Nombre = nombre;
        this.estado = estado;
    }
    
    public void setIdMunicipio(int idMunicipio)
    {
        this.idMunicipio = idMunicipio;
    }
    public int getIdMunicipio()
    {
        return idMunicipio;
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
