package com.digis01.IHernandezProgramacionNCapas.JPA;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class Direccion 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddireccion")
    private int IdDireccion;   
    
    @Column(name = "calle")
    private String Calle;
    
    @Column(name = "numerointerior")
    private String NumeroInterior;

    @Column(name = "numeroexterior")
    private String NumeroExterior;
    
    @ManyToOne
    @JoinColumn(name = "idcolonia", nullable = false)
    public Colonia Colonia;
    
    @ManyToOne
    @JoinColumn(name = "idusuario", nullable = false)
    @JsonIgnore 
    public Usuario Usuario;

    public Direccion(){}
    
    public Direccion( int idDireccion)
    {
        this.IdDireccion = idDireccion;
    }
    
    public Direccion(int idDireccion, String calle, String numeroInterior, String numeroExterior) 
    {
        this.IdDireccion = idDireccion;
        this.Calle = calle;
        this.NumeroInterior = numeroInterior;
        this.NumeroExterior = numeroExterior;
    }

    public Direccion(int idDireccion, String calle, String numeroInterior, String numeroExterior, Colonia colonia, Usuario usuario) 
    {
        this.IdDireccion = idDireccion;
        this.Calle = calle;
        this.NumeroInterior = numeroInterior;
        this.NumeroExterior = numeroExterior;
        this.Colonia = colonia;
        this.Usuario = usuario;
    }

    public void setIdDireccion(int idDireccion) 
    {
        this.IdDireccion = idDireccion;
    }
    public int getIdDireccion() 
    {
        return IdDireccion;
    }

    public void setCalle(String calle) 
    {
        this.Calle = calle;
    }
    public String getCalle() 
    {
        return Calle;
    }

    public void setNumeroInterior(String numeroInterior) 
    {
        this.NumeroInterior = numeroInterior;
    }
    public String getNumeroInterior() 
    {
        return NumeroInterior;
    }
 
    public void setNumeroExterior(String numeroExterior) 
    {
        this.NumeroExterior = numeroExterior;
    }
    public String getNumeroExterior() 
    {
        return NumeroExterior;
    }
}