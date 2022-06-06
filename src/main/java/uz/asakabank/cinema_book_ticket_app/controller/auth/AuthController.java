package uz.asakabank.cinema_book_ticket_app.controller.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.asakabank.cinema_book_ticket_app.entity.user.User;
import uz.asakabank.cinema_book_ticket_app.service.auth.AuthService;
import uz.asakabank.cinema_book_ticket_app.service.jwt.JwtProvider;
import uz.asakabank.cinema_book_ticket_app.dto.auth.LoginDto;
import uz.asakabank.cinema_book_ticket_app.dto.auth.RegisterDto;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @PostMapping("/register")
    public HttpEntity<?> register(@RequestBody RegisterDto registerDto){
        return authService.register(registerDto);
    }

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginDto loginDto){
        User user = null;
        try{
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
            user =(User) authenticate.getPrincipal();
        }catch (AuthenticationException e){
            return new ResponseEntity<>("", HttpStatus.BAD_GATEWAY);
        }
        String token = jwtProvider.generateToken(user.getUsername(), user.getUserRoleSet());
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

}
