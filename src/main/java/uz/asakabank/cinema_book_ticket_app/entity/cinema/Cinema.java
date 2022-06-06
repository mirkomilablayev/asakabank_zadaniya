package uz.asakabank.cinema_book_ticket_app.entity.cinema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.asakabank.cinema_book_ticket_app.entity.BaseEntity;
import uz.asakabank.cinema_book_ticket_app.entity.BaseEntityId;
import uz.asakabank.cinema_book_ticket_app.entity.user.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cinema extends BaseEntityId implements BaseEntity {
    @Column(nullable = false)
    private String cinema_name;
    private Boolean isDeleted = false;
    @Column(nullable = false)
    @ManyToMany
    private List<User> managers;
}
