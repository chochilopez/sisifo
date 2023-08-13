package muni.eolida.sisifo.security.services;

import muni.eolida.sisifo.model.UsuarioModel;
import muni.eolida.sisifo.repository.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UsuarioDAO usuarioDTO;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UsuarioModel user = usuarioDTO.findByUsernameContainingIgnoreCaseAndBajaIsNullAndHabilitadaIsTrue(username)
        .orElseThrow(() -> new UsernameNotFoundException("No existe el usuario: " + username));

    return UserDetailsImpl.build(user);
  }

}
