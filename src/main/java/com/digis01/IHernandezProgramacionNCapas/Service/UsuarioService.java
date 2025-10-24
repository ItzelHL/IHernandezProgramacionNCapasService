package com.digis01.IHernandezProgramacionNCapas.Service;

import com.digis01.IHernandezProgramacionNCapas.Repository.IRepositoryUsuario;
import com.digis01.IHernandezProgramacionNCapas.Model.Usuario;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService
{
    private final IRepositoryUsuario iRepositoryUsuario;
    
    public UsuarioService(IRepositoryUsuario iRepositoryUsuario)
    {
        this.iRepositoryUsuario = iRepositoryUsuario;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
    {
        Usuario usuario = iRepositoryUsuario.findByUsername(username);
        
        return User.withUsername(usuario.getUsername())
                .password(usuario.getPassword())
                .roles(usuario.Rol.getNombre())
                .accountLocked(!(usuario.getStatus() == 1))
                .disabled(!(usuario.getStatus() == 1))
                .build();
    }
    
    public Usuario getByUsername(String username) 
    {
        return iRepositoryUsuario.findByUsername(username);
    }
}