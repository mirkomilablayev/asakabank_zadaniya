package uz.asakabank.cinema_book_ticket_app.entity.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.asakabank.cinema_book_ticket_app.entity.BaseEntity;
import uz.asakabank.cinema_book_ticket_app.entity.BaseEntityId;
import uz.asakabank.cinema_book_ticket_app.entity.cinema.Session;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BookingList extends BaseEntityId implements BaseEntity {
    @ManyToOne
    private Session session;
    private String lastName;
    private String secretWord;
    @Column(nullable = false)
    private LocalDateTime meetingTime;
}
