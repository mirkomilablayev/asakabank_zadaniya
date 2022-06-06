package uz.asakabank.cinema_book_ticket_app.repository.cinema;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.asakabank.cinema_book_ticket_app.entity.cinema.Hall;
import uz.asakabank.cinema_book_ticket_app.entity.cinema.HallBusyList;
import uz.asakabank.cinema_book_ticket_app.repository.BaseRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface HallBusyListRepo extends JpaRepository<HallBusyList,Long>, BaseRepository {
Boolean existsByIdAndBusyTime(Long id, LocalDateTime busyTime);
boolean existsByBusyTimeAndHall(LocalDateTime busyTime, Hall hall);
}
