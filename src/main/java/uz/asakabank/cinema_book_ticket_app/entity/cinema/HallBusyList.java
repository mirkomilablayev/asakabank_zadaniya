package uz.asakabank.cinema_book_ticket_app.entity.cinema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.asakabank.cinema_book_ticket_app.entity.BaseEntity;
import uz.asakabank.cinema_book_ticket_app.entity.BaseEntityId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.websocket.OnError;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "hall_busy_lists")
public class HallBusyList extends BaseEntityId implements BaseEntity { @Column(nullable = false)
    private LocalDateTime busyTime;
    @ManyToOne
    private Hall hall;
    @OneToOne
    private Session session;
}
