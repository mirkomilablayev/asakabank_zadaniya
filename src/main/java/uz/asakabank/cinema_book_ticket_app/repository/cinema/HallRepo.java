package uz.asakabank.cinema_book_ticket_app.repository.cinema;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.asakabank.cinema_book_ticket_app.entity.cinema.Hall;
import uz.asakabank.cinema_book_ticket_app.repository.BaseRepository;

import java.util.Optional;

public interface HallRepo extends JpaRepository<Hall,Long>, BaseRepository {
Optional<Hall> findByIdAndIsActive(Long id, Boolean isActive);
}
