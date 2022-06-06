package uz.asakabank.cinema_book_ticket_app.entity.cinema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.asakabank.cinema_book_ticket_app.entity.BaseEntity;
import uz.asakabank.cinema_book_ticket_app.entity.BaseEntityId;

import javax.persistence.Entity;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "content_type")
public class ContentType extends BaseEntityId implements BaseEntity {
    private String name;
}
