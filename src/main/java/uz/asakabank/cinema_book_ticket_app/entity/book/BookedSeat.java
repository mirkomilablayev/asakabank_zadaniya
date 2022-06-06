package uz.asakabank.cinema_book_ticket_app.entity.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.asakabank.cinema_book_ticket_app.entity.BaseEntity;
import uz.asakabank.cinema_book_ticket_app.entity.BaseEntityId;
import uz.asakabank.cinema_book_ticket_app.entity.cinema.Seat;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BookedSeat extends BaseEntityId implements BaseEntity {
    @ManyToOne
    private BookingList bookingList;
    @ManyToOne
    private Seat seat;
}
