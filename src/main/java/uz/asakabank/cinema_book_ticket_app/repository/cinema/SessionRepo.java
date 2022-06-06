package uz.asakabank.cinema_book_ticket_app.repository.cinema;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.w3c.dom.stylesheets.LinkStyle;
import uz.asakabank.cinema_book_ticket_app.entity.cinema.Session;
import uz.asakabank.cinema_book_ticket_app.repository.BaseRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SessionRepo extends JpaRepository<Session,Long>, BaseRepository {
    Optional<Session> findByIdAndIsActive(Long id, Boolean isActive);
    List<Session> findAllByIsActiveAndSessionDateAfter(Boolean isActive, LocalDateTime sessionDate);


    @Query(nativeQuery = true, value = "select s.* from sessions s\n" +
            "join halls h on h.id = s.hall_id\n" +
            "join cinema c on c.id = h.cinema_id\n" +
            "where s.is_active and c.id = ?1")
    List<Session> getSessions(Long cinemaId);
}
