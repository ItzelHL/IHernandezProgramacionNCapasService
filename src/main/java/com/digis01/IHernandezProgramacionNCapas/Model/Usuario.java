package com.digis01.IHernandezProgramacionNCapas.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class Usuario 
{
    @JsonProperty("status")
    @Column(name = "status")
    private int status;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private int idUsuario;

    @Lob
    @Column(name = "imagenusuario")
    private String imagen;

    @Column(name = "username")
    private String username;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellidopaterno")
    private String apellidoPaterno;

    @Column(name = "apellidomaterno")
    private String apellidoMaterno;

    @Column(name = "fechanacimiento")
    private Date fechaNacimiento;

    @Column(name = "sexo")
    private String sexo;

    @Column(name = "curp")
    private String curp;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "celular")
    private String celular;

    @ManyToOne
    @JoinColumn(name = "idrol")
    public Rol Rol;

    @OneToMany(mappedBy = "Usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Direccion> Direcciones = new ArrayList<>();
    
    @Column(name = "token")
    private String token;

    public Usuario() {}

    public Usuario(int status, int idUsuario, String imagen, String username, String nombre, String apellidoPaterno, String apellidoMaterno, 
            Date fechaNacimiento, String sexo, String curp, String email, String password, String telefono, String celular, List<Direccion> direcciones, String token) 
    {
        this.status = status;
        this.idUsuario = idUsuario;
        this.imagen = imagen;
        this.username = username;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.curp = curp;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.celular = celular;
        this.Direcciones = direcciones;
        this.token = token;
    }

    public void setStatus(int status) 
    {
        this.status = status;
    }
    public int getStatus() 
    {
        return status;
    }

    public void setIdUsuario(int idUsuario) 
    {
        this.idUsuario = idUsuario;
    }
    public int getIdUsuario() 
    {
        return idUsuario;
    }

    public void setImagen(String imagen) 
    {
        this.imagen = imagen;
    }
    public String getImagen() 
    {
        return imagen;
    }

    public void setUsername(String username) 
    {
        this.username = username;
    }
    public String getUsername() 
    {
        return username;
    }

    public void setNombre(String nombre) 
    {
        this.nombre = nombre;
    }
    public String getNombre()
    {
        return nombre;
    }

    public void setApellidoPaterno(String apellidoPaterno) 
    {
        this.apellidoPaterno = apellidoPaterno;
    }
    public String getApellidoPaterno() 
    {
        return apellidoPaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) 
    {
        this.apellidoMaterno = apellidoMaterno;
    }
    public String getApellidoMaterno() 
    {
        return apellidoMaterno;
    }

    public void setFechaNacimiento(Date fechaNacimiento) 
    {
        this.fechaNacimiento = fechaNacimiento;
    }
    public Date getFechaNacimiento() 
    {
        return fechaNacimiento;
    }

    public void setSexo(String sexo) 
    {
        this.sexo = sexo;
    }
    public String getSexo()
    {
        return sexo;
    }

    public void setCurp(String curp) 
    {
        this.curp = curp;
    }
    public String getCurp() 
    {
        return curp;
    }

    public void setEmail(String email) 
    {
        this.email = email;
    }
    public String getEmail() 
    {
        return email;
    }

    public void setPassword(String password) 
    {
        this.password = password;
    }
    public String getPassword() 
    {
        return password;
    }

    public void setTelefono(String telefono) 
    {
        this.telefono = telefono;
    }
    public String getTelefono() 
    {
        return telefono;
    }

    public void setCelular(String celular) 
    {
        this.celular = celular;
    }
    public String getCelular() 
    {
        return celular;
    }
    
    public void setToken(String token)
    {
        this.token = token;
    }
    public String getToken()
    {
        return token;
    }
}