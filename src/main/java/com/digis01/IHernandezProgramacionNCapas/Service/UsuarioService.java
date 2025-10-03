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
    private final IRepositoryUsuario iUsuario;
    
    public UsuarioService(IRepositoryUsuario iUsuario)
    {
        this.iUsuario = iUsuario;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
    {
        Usuario usuario = iUsuario.findByUsername(username);
        
        return User.withUsername(usuario.getUsername())
                .password(usuario.getPassword())
                .roles(usuario.Rol.getNombre())
                .accountLocked(!(usuario.getStatus() == 1))
                .disabled(!(usuario.getStatus() == 1))
                .build();
    }
    
}
