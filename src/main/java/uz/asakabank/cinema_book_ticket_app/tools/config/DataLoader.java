package uz.asakabank.cinema_book_ticket_app.tools.config;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.asakabank.cinema_book_ticket_app.entity.cinema.ContentType;
import uz.asakabank.cinema_book_ticket_app.entity.user.User;
import uz.asakabank.cinema_book_ticket_app.entity.user.UserRole;
import uz.asakabank.cinema_book_ticket_app.repository.cinema.ContentTypeRepo;
import uz.asakabank.cinema_book_ticket_app.repository.user.UserRepo;
import uz.asakabank.cinema_book_ticket_app.repository.user.RoleRepo;
import uz.asakabank.cinema_book_ticket_app.tools.exceptions.UserRoleNotFoundException;

import java.util.HashSet;
import java.util.List;


@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final ContentTypeRepo contentTypeRepo;
    private final PasswordEncoder passwordEncoder;

    @Value(value = "${spring.jpa.hibernate.ddl-auto}")
    private String ddl;

    @Override
    public void run(String... args) throws Exception {
        if (ddl.equalsIgnoreCase("create") || ddl.equalsIgnoreCase("create-drop")) {
//            User roles
            roleRepo.save(new UserRole("USER"));
            roleRepo.save(new UserRole("MANAGER"));
            roleRepo.save(new UserRole("ADMIN"));
            // this is super admin
            User admin = new User();
            admin.setFirstName("Mirkomil");
            admin.setLastName("Ablayev");
            admin.setUserRoleSet(new HashSet<>(List.of(roleRepo.findByNameAndIsActive("ADMIN",true).orElseThrow(() -> new UserRoleNotFoundException("ADMIN role not found")))));
            admin.setUsername("1");
            admin.setPassword(passwordEncoder.encode("1"));
            userRepo.save(admin);

//            types of content which performed in cinema
            contentTypeRepo.save(new ContentType("FILM"));
            contentTypeRepo.save(new ContentType("KONSERT"));
            contentTypeRepo.save(new ContentType("SPEKTAKL"));
            contentTypeRepo.save(new ContentType("SHOU"));


        }
    }
}
