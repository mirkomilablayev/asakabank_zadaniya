package uz.asakabank.cinema_book_ticket_app.repository.cinema;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.asakabank.cinema_book_ticket_app.entity.cinema.ContentType;
import uz.asakabank.cinema_book_ticket_app.repository.BaseRepository;

import java.util.Optional;

public interface ContentTypeRepo extends JpaRepository<ContentType,Long> , BaseRepository {
    Optional<ContentType> findByName(String name);
}
