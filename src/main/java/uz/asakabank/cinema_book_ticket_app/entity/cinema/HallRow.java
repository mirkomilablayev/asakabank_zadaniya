package uz.asakabank.cinema_book_ticket_app.entity.cinema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.asakabank.cinema_book_ticket_app.entity.BaseEntity;
import uz.asakabank.cinema_book_ticket_app.entity.BaseEntityId;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "hall_rows")
public class HallRow extends BaseEntityId implements BaseEntity {
    private Integer rowNum;
    @ManyToOne
    private Hall hall;
}
