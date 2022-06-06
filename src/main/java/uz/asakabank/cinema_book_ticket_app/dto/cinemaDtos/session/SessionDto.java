package uz.asakabank.cinema_book_ticket_app.dto.cinemaDtos.session;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.asakabank.cinema_book_ticket_app.dto.Dto;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SessionDto implements Dto {
    private Long id;
    private String contentName;
    private String hallName;
    private Long hallId;
    private LocalDateTime sessionTime;
    private String contentType;
    private Boolean isActive;
}
