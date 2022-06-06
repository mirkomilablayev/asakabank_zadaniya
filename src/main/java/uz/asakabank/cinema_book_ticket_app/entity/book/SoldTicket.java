package uz.asakabank.cinema_book_ticket_app.entity.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.asakabank.cinema_book_ticket_app.entity.BaseEntity;
import uz.asakabank.cinema_book_ticket_app.entity.BaseEntityId;
import uz.asakabank.cinema_book_ticket_app.entity.cinema.Cinema;
import uz.asakabank.cinema_book_ticket_app.entity.cinema.Seat;
import uz.asakabank.cinema_book_ticket_app.entity.cinema.Session;
import uz.asakabank.cinema_book_ticket_app.entity.user.User;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SoldTicket extends BaseEntityId implements BaseEntity {
    @ManyToOne
    private Seat seat;
    @ManyToOne
    private User buyer;
    @ManyToOne
    private Session session;
}
