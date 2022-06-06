package uz.asakabank.cinema_book_ticket_app.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.asakabank.cinema_book_ticket_app.entity.user.User;
import uz.asakabank.cinema_book_ticket_app.entity.user.UserRole;
import uz.asakabank.cinema_book_ticket_app.repository.user.UserRepo;
import uz.asakabank.cinema_book_ticket_app.repository.user.RoleRepo;
import uz.asakabank.cinema_book_ticket_app.service.BaseService;
import uz.asakabank.cinema_book_ticket_app.dto.auth.RegisterDto;
import uz.asakabank.cinema_book_ticket_app.tools.exceptions.ResourceNotFoundException;
import uz.asakabank.cinema_book_ticket_app.tools.exceptions.UserRoleNotFoundException;
import uz.asakabank.cinema_book_ticket_app.tools.exceptions.UsernameAlreadyRegisterException;
import uz.asakabank.cinema_book_ticket_app.tools.mapper.UserMapper;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService , BaseService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final RoleRepo roleRepo;


    public HttpEntity<?> register(RegisterDto registerDto) {
        if (!userRepo.existsByUsernameAndIsDeleted(registerDto.getUsername(),false)) {
            User user = new User();
            user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
            String user_role = "USER";
            Set<UserRole> userRoles = new HashSet<>();
            UserRole userRole = roleRepo.findByNameAndIsActive("USER",true).orElseThrow(() -> new UserRoleNotFoundException(user_role + " not found"));
            userRoles.add(userRole);
            user.setBalance(registerDto.getBalance());
            user.setUsername(registerDto.getUsername());
            user.setFirstName(registerDto.getFirstName());
            user.setLastName(registerDto.getLastName());
            user.setUserRoleSet(userRoles);
            return ResponseEntity.status(HttpStatus.OK).body(userRepo.save(user));
        }
        throw new UsernameAlreadyRegisterException(registerDto.getUsername()+ " is already registered");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return   userRepo.findByUsernameAndIsDeleted(username,false).orElseThrow(() -> new ResourceNotFoundException(""));
    }
}
