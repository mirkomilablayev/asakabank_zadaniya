package uz.asakabank.cinema_book_ticket_app.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.asakabank.cinema_book_ticket_app.entity.user.User;
import uz.asakabank.cinema_book_ticket_app.repository.BaseRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long>, BaseRepository {
    Optional<User> findByUsernameAndIsDeleted(String username, Boolean isDeleted);
    Boolean existsByUsernameAndIsDeleted(String username, Boolean isDeleted);
    Optional<User> findByIdAndIsDeleted(Long id, Boolean isDeleted);
}
