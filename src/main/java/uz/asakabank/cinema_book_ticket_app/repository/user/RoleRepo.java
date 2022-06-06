package uz.asakabank.cinema_book_ticket_app.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.asakabank.cinema_book_ticket_app.entity.user.UserRole;
import uz.asakabank.cinema_book_ticket_app.repository.BaseRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<UserRole,Long>, BaseRepository {
Optional<UserRole> findByNameAndIsActive(String name, Boolean isActive);
}
