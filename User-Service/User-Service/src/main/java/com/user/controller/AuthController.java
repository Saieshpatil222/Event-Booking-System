package com.user.controller;

import com.user.dto.JwtRequest;
import com.user.dto.JwtResponse;
import com.user.dto.UserDto;
import com.user.security.JwtHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest) {

        this.doAuthenticate(jwtRequest.getEmailId(), jwtRequest.getPassword());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtRequest.getEmailId());

        String token = jwtHelper.generateToken(userDetails);
        JwtResponse jwtResponse = JwtResponse
                .builder()
                .jwtToken(token)
                .userDto(modelMapper.map(userDetails, UserDto.class))

                .build();

        return ResponseEntity.status(HttpStatus.OK).body(jwtResponse);

    }

    private void doAuthenticate(String emailId, String password) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(emailId, password);
        try{
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (BadCredentialsException e) {

        }
    }


//    @PostMapping("/generate-token")
//    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
//
//        logger.info("Username {} ,  Password {}", request.getEmail(), request.getPassword());
//
//        this.doAuthenticate(request.getEmail(), request.getPassword());
//
//        User user = (User) userDetailsService.loadUserByUsername(request.getEmail());
//
//        ///.. generate token...
//        String token = jwtHelper.generateToken(user);
//        //send karna hai response
//
//        // Refresh Token
//
//        RefreshTokenDto refreshToken = refreshTokenService.createRefreshToken(user.getEmail());
//
//
//        JwtResponse jwtResponse = JwtResponse
//                .builder()
//                .token(token)
//                .user(modelMapper.map(user, UserDto.class))
//                .refreshToken(refreshToken)
//                .build();
//
//
//        return ResponseEntity.ok(jwtResponse);
//
//
//    }


}
