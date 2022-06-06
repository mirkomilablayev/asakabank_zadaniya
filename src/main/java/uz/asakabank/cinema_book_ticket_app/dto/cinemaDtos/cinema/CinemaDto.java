package uz.asakabank.cinema_book_ticket_app.dto.cinemaDtos.cinema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.config.web.servlet.oauth2.client.OAuth2ClientSecurityMarker;
import uz.asakabank.cinema_book_ticket_app.dto.Dto;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CinemaDto implements Dto {
    private Long id;
    private String name;
    private List<ManagerDto> managers;
}
