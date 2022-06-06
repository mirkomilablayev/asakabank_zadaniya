package uz.asakabank.cinema_book_ticket_app.entity.cinema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.asakabank.cinema_book_ticket_app.entity.BaseEntity;
import uz.asakabank.cinema_book_ticket_app.entity.BaseEntityId;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sessions")
public class Session extends BaseEntityId implements BaseEntity {
    @Column(nullable = false)
    private String contentName;
    @ManyToOne
    private Hall hall;
    @Column(nullable = false)
    private LocalDateTime sessionDate;
    @ManyToOne
    private ContentType contentType;
    private Boolean isActive = true;
}
