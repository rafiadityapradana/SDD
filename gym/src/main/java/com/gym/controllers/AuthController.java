package com.gym.controllers;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.gym.configs.JwtTokenProvider;
import com.gym.models.request.*;
import com.gym.services.AuthService;

@RestController
@RequestMapping("/auth/v1")
@Validated
public class AuthController {
     @Autowired
     private AuthService authService;

     @Autowired
     JwtTokenProvider jwtTokenProvider;
     

     @PostMapping("/register")
     public ResponseEntity<Object> registerController(@Valid @RequestBody RegisterRequest registerRequest) throws Exception {
          String accessToken = jwtTokenProvider.generateToken("dfsdffs", 520000);
               System.out.println("accessToken "+accessToken);
          return authService.registerService(registerRequest);
     }
     @PostMapping("/verify-account")
     public ResponseEntity<Object> verifyAccountController(@Valid @RequestBody VerifyAccountRequest req ) throws Exception{
          return authService.verifyAccountService(req);
     }

     @PostMapping("/regenerate-otp")
     public ResponseEntity<Object> regenerateOtpController(@Valid @RequestBody RegenerateOtpRequest req ) throws Exception{
          return authService.regenerateOtpService(req);
     }
     @PostMapping("/login")
     public ResponseEntity<Object> loginController(@Valid @RequestBody LoginRequest loginRequest) throws Exception{
          return authService.loginService(loginRequest);
     }

     @PostMapping("/refresh-token")
     public ResponseEntity<Object> refreshTokenController(@Valid @RequestBody LoginRequest loginRequest) throws Exception{
          return authService.loginService(loginRequest);
     }
     @PostMapping("/request-reset-password")
     public ResponseEntity<Object> RequestResetPasswordController( @Valid @RequestBody RequestResetPassword resetPassword) throws Exception{
          return authService.RequestResetPasswordService(resetPassword);
     }
     @PostMapping("/reset-password")
     public ResponseEntity<Object> ResetPasswordController(@Valid @RequestBody ResetPassword req) throws Exception{
          return authService.ResetPasswordService(req);
     }
}
