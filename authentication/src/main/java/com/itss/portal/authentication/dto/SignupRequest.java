package com.itss.portal.authentication.dto;
import com.itss.portal.authentication.validation.ValidPassword;

import jakarta.validation.constraints.*;  // add this import

public class SignupRequest {
	@NotBlank @Size(min = 4, max = 32)
    private String username;

    @NotBlank @Email
    private String email;

    @ValidPassword
    private String password;

    public String getUsername() { return username; }
    public void setUsername(String u) { username = u; }

    public String getEmail() { return email; }
    public void setEmail(String e) { email = e; }

    public String getPassword() { return password; }
    public void setPassword(String p) { password = p; }
}
