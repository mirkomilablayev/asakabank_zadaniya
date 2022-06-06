package uz.asakabank.cinema_book_ticket_app.repository.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.asakabank.cinema_book_ticket_app.entity.book.BookingList;
import uz.asakabank.cinema_book_ticket_app.repository.BaseRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingListRepo extends JpaRepository<BookingList,Long>, BaseRepository {
    @Query(nativeQuery = true,value = "select bl.id from booking_list bl where bl.meeting_time < now()")
    List<Long> getDelayedBookingsId();
    List<BookingList> findAllByLastNameAndSecretWordAndMeetingTime(String lastName, String secretWord, LocalDateTime meetingTime);
}
