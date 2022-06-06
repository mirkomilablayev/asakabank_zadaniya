package uz.asakabank.cinema_book_ticket_app.repository.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.asakabank.cinema_book_ticket_app.entity.book.SoldTicket;
import uz.asakabank.cinema_book_ticket_app.repository.BaseRepository;

import java.time.LocalDateTime;

public interface SoldTicketRepo extends JpaRepository<SoldTicket,Long>, BaseRepository {
    Boolean existsBySeat_IdAndSession_IsActiveAndSession_SessionDate(Long seat_id, Boolean session_isActive, LocalDateTime session_sessionDate);
}
