package com.sks.securityFull.auth;

import java.util.Optional;

public interface ApplicationUserDao {

	Optional<ApplicationUser> selectApplicationUserByUserName(String username);
}
