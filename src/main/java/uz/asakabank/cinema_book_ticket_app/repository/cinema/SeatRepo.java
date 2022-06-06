package uz.asakabank.cinema_book_ticket_app.repository.cinema;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.asakabank.cinema_book_ticket_app.entity.cinema.HallRow;
import uz.asakabank.cinema_book_ticket_app.entity.cinema.Seat;
import uz.asakabank.cinema_book_ticket_app.repository.BaseRepository;

import java.util.List;

public interface SeatRepo extends JpaRepository<Seat,Long>, BaseRepository {
    Long countAllByHallRowAndIsOutOfWork(HallRow hallRow, Boolean isOutOfWork);

    @Query(nativeQuery = true, value = "select count(*) from seats\n" +
            " join hall_rows hr on seats.hall_row_id = hr.id\n" +
            " join halls h on hr.hall_id = h.id\n" +
            "    where h.id = ?1 and not is_out_of_work")
    Long countSeat(Long row_id);


    @Query(nativeQuery = true,value = "select s.*\n" +
            "from seats s\n" +
            "         join hall_rows hr on hr.id = s.hall_row_id\n" +
            "where not s.is_out_of_work and hr.hall_id = ?1 limit ?2 offset (?3 - 1) * ?2")
    List<Seat> getSeatsPageable(Long hallId, Long size, Long page);
}
