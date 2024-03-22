package com.autoservice.security.business.service;

import com.autoservice.security.models.AuthenticationRequest;
import com.autoservice.security.models.AuthenticationResponse;
import com.autoservice.security.models.RegisterRequest;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest registerRequest);
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
}
