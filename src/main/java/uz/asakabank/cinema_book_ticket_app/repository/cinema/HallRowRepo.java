package uz.asakabank.cinema_book_ticket_app.repository.cinema;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.asakabank.cinema_book_ticket_app.entity.cinema.Hall;
import uz.asakabank.cinema_book_ticket_app.entity.cinema.HallRow;
import uz.asakabank.cinema_book_ticket_app.repository.BaseRepository;

public interface HallRowRepo extends JpaRepository<HallRow,Long>, BaseRepository {
    Long countAllByHall(Hall hall);

}
