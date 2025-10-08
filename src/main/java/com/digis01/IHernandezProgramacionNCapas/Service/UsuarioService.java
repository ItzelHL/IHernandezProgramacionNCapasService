package com.digis01.IHernandezProgramacionNCapas.Service;

import com.digis01.IHernandezProgramacionNCapas.DAO.IRepositoryUsuario;
import com.digis01.IHernandezProgramacionNCapas.JPA.Usuario;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService
{
    private final IRepositoryUsuario iRepositoryUsuario;
    
    public UsuarioService(IRepositoryUsuario iUsuario)
    {
        this.iRepositoryUsuario = iUsuario;
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
    
}