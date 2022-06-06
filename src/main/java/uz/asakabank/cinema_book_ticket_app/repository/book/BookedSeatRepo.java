package uz.asakabank.cinema_book_ticket_app.repository.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.asakabank.cinema_book_ticket_app.entity.book.BookedSeat;
import uz.asakabank.cinema_book_ticket_app.entity.book.BookingList;
import uz.asakabank.cinema_book_ticket_app.repository.BaseRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Transactional
public interface BookedSeatRepo extends JpaRepository<BookedSeat,Long>, BaseRepository {


    Boolean existsByBookingList_Session_SessionDateAndSeat_Id(LocalDateTime bookingList_session_sessionDate, Long seat_id);

    @Modifying
    void deleteAllByBookingList(BookingList bookingList);
    List<BookedSeat> findAllByBookingList(BookingList bookingList);
}
