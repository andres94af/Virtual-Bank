package com.virtualbank.security;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.virtualbank.model.Usuario;
import com.virtualbank.service.IUsuarioService;

import javax.servlet.http.HttpSession;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	HttpSession session;

//	private BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> optionalUser = usuarioService.findByEmail(username);
		if(optionalUser.isPresent()) {
			session.setAttribute("idusuario", optionalUser.get().getId());
			Usuario usuario = optionalUser.get();
			return User.builder().username(usuario.getNombre()).password(usuario.getPassword()).roles(usuario.getRol()).build();
		}else {
			throw new UsernameNotFoundException("Usuario no encontrado");
		}
	}

}