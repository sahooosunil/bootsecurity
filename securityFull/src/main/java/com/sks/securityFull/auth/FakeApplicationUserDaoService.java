package com.sks.securityFull.auth;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import com.sks.securityFull.security.ApplicationUserRole;

@Repository("fake")
public class FakeApplicationUserDaoService implements ApplicationUserDao {

	private PasswordEncoder passwordEncoder;

	@Autowired
	public FakeApplicationUserDaoService(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Optional<ApplicationUser> selectApplicationUserByUserName(String username) {
		Optional<ApplicationUser> findFirst = getApplicationUser().stream().filter(applicationUser ->username.equalsIgnoreCase(applicationUser.getUsername())).findFirst();
		return findFirst;
	}

	private List<ApplicationUser> getApplicationUser() {
		List<ApplicationUser> applicationUser = Lists.newArrayList(
				new ApplicationUser(
						passwordEncoder.encode("password"),
						"annasmith",
						ApplicationUserRole.STUDENT.getGrantedAuthorities(),
						true, true, true, true
				), 
				new ApplicationUser(
						passwordEncoder.encode("password"),
						"linda", 
						ApplicationUserRole.ADMIN.getGrantedAuthorities(),
						true, true, true, true
				), 
				new ApplicationUser(
						passwordEncoder.encode("password"),
						"tom", 
						ApplicationUserRole.ADMINTRAINEE.getGrantedAuthorities(),
						true, true, true, true
				)
		);	
		
		return applicationUser;
	}

}
