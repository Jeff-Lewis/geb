package ru.prbb.middleoffice.services;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Сервис информация о пользователях
 * 
 * @author RBr
 */
@Service
public class ArmUserDetailsService implements UserDetailsService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private static final SimpleGrantedAuthority ROLE_USER = new SimpleGrantedAuthority("ROLE_USER");
	private static final SimpleGrantedAuthority ROLE_ADMIN = new SimpleGrantedAuthority("ROLE_ADMIN");

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("loadUserByUsername({})", username);

		if ("mo".equals(username)) {
			User ud = new User(username, "mo", Arrays.asList(ROLE_USER, ROLE_ADMIN));
			return ud;
		}

		if ("admin1".equals(username)) {
			User ud = new User(username, "admin1", Arrays.asList(ROLE_USER));
			return ud;
		}

		UserDetails ud = null;
		return ud;
	}

}
