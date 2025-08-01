package com.protectora.backend.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.protectora.backend.services.UsuarioService;

/*
Permite que Spring Security sepa cómo extraer el usuario de base de datos
para realizar la autenticación
*/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final UsuarioService userService;

    public UserDetailsServiceImpl(UsuarioService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String nombre) throws UsernameNotFoundException {
        log.debug("loadUserByUsername {}", nombre);
        var usuario = this.userService.findByNombre(nombre)
                .orElseThrow(() -> new UsernameNotFoundException(nombre + " no encontrado"));
        return new UsuarioDetails(usuario);
    }

}
