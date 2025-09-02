package com.digis01.IHernandezProgramacionNCapas.JPA;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private int IdUsuario;

    @Lob
    @Column(name = "imagenusuario")
    private String Imagen;

    @Column(name = "username")
    private String Username;

    @Column(name = "nombre")
    private String Nombre;

    @Column(name = "apellidopaterno")
    private String ApellidoPaterno;

    @Column(name = "apellidomaterno")
    private String ApellidoMaterno;

    @Column(name = "fechanacimiento")
    private Date FechaNacimiento;

    @Column(name = "sexo")
    private String Sexo;

    @Column(name = "curp")
    private String Curp;

    @Column(name = "email")
    private String Email;

    @Column(name = "password")
    private String Password;

    @Column(name = "telefono")
    private String Telefono;

    @Column(name = "celular")
    private String Celular;

    @ManyToOne
    @JoinColumn(name = "idrol")
    public Rol Rol;

    @OneToMany(mappedBy = "Usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Direccion> Direcciones = new ArrayList<>();

    public Usuario() {}

    public Usuario(int idUsuario, String imagen, String username, String nombre, String apellidoPaterno, String apellidoMaterno, 
            Date fechaNacimiento, String sexo, String curp, String email, String password, String telefono, String celular, 
            Rol rol, List<Direccion> direccion) 
    {
        this.IdUsuario = idUsuario;
        this.Imagen = imagen;
        this.Username = username;
        this.Nombre = nombre;
        this.ApellidoPaterno = apellidoPaterno;
        this.ApellidoMaterno = apellidoMaterno;
        this.FechaNacimiento = fechaNacimiento;
        this.Sexo = sexo;
        this.Curp = curp;
        this.Email = email;
        this.Password = password;
        this.Telefono = telefono;
        this.Celular = celular;
        this.Rol = rol;
        this.Direcciones = direccion;
    }

    public void setIdUsuario(int idUsuario) 
    {
        this.IdUsuario = idUsuario;
    }
    public int getIdUsuario() 
    {
        return IdUsuario;
    }

    public void setImagen(String imagen) 
    {
        this.Imagen = imagen;
    }
    public String getImagen() 
    {
        return Imagen;
    }

    public void setUsername(String username) 
    {
        this.Username = username;
    }
    public String getUsername() 
    {
        return Username;
    }

    public void setNombre(String nombre) 
    {
        this.Nombre = nombre;
    }
    public String getNombre()
    {
        return Nombre;
    }

    public void setApellidoPaterno(String apellidoPaterno) 
    {
        this.ApellidoPaterno = apellidoPaterno;
    }
    public String getApellidoPaterno() 
    {
        return ApellidoPaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) 
    {
        this.ApellidoMaterno = apellidoMaterno;
    }
    public String getApellidoMaterno() 
    {
        return ApellidoMaterno;
    }

    public void setFechaNacimiento(Date fechaNacimiento) 
    {
        this.FechaNacimiento = fechaNacimiento;
    }
    public Date getFechaNacimiento() 
    {
        return FechaNacimiento;
    }

    public void setSexo(String sexo) 
    {
        this.Sexo = sexo;
    }
    public String getSexo()
    {
        return Sexo;
    }

    public void setCurp(String curp) 
    {
        this.Curp = curp;
    }
    public String getCurp() 
    {
        return Curp;
    }

    public void setEmail(String email) 
    {
        this.Email = email;
    }
    public String getEmail() 
    {
        return Email;
    }

    public void setPassword(String password) 
    {
        this.Password = password;
    }
    public String getPassword() 
    {
        return Password;
    }

    public void setTelefono(String telefono) 
    {
        this.Telefono = telefono;
    }
    public String getTelefono() 
    {
        return Telefono;
    }

    public void setCelular(String celular) 
    {
        this.Celular = celular;
    }
    public String getCelular() 
    {
        return Celular;
    }
}
