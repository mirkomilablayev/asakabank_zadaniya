package uz.asakabank.cinema_book_ticket_app.repository.cinema;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.asakabank.cinema_book_ticket_app.entity.cinema.Cinema;
import uz.asakabank.cinema_book_ticket_app.repository.BaseRepository;

import java.util.Optional;

public interface CinemaRepo extends JpaRepository<Cinema,Long> , BaseRepository {
        Optional<Cinema> findById(Long id);
        Optional<Cinema> findByIdAndIsDeleted(Long id, Boolean isDeleted);
}
