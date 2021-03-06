package org.emaginalabs.example.springdata.controller;

import org.emaginalabs.example.springdata.response.SecurityRole;
import org.emaginalabs.example.springdata.response.UserDto;
import org.emaginalabs.example.springdata.security.util.SecurityContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Iterator;

@Controller
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Resource
	private SecurityContextUtil securityContextUtil;

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	@ResponseBody
	public UserDto getLoggedInUser() {
		LOGGER.debug("Getting logged in user.");
		UserDetails principal = securityContextUtil.getPrincipal();
		return createDTO(principal);
	}

	private UserDto createDTO(UserDetails principal) {
		UserDto dto = null;
		if (principal != null) {
			String username = principal.getUsername();
			SecurityRole role = getRole(principal.getAuthorities());

			dto = new UserDto(username, role);
		}

		LOGGER.debug("Created user dto: {}", dto);

		return dto;
	}

	private SecurityRole getRole(Collection<? extends GrantedAuthority> authorities) {
		LOGGER.debug("Getting role from authorities: {}", authorities);

		Iterator<? extends GrantedAuthority> authority = authorities.iterator();
		GrantedAuthority a = authority.next();

		return SecurityRole.valueOf(a.getAuthority());
	}
}

