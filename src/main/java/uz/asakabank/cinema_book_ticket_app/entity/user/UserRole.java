package uz.asakabank.cinema_book_ticket_app.entity.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import uz.asakabank.cinema_book_ticket_app.entity.BaseEntity;
import uz.asakabank.cinema_book_ticket_app.entity.BaseEntityId;

import javax.persistence.Entity;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "user_roles")
public class UserRole extends BaseEntityId implements GrantedAuthority, BaseEntity {
    private String name;
    private Boolean isActive = true;
    @Override
    public String getAuthority() {
        return name;
    }

    public UserRole(String name){
        this.name = name;
    }
}
