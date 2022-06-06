package uz.asakabank.cinema_book_ticket_app.dto.cinemaDtos.session;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.asakabank.cinema_book_ticket_app.dto.Dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SessionCreateDto implements Dto {
    private String contentName;
    private Long hallId;
    private Long contentTypeId;
    private LocalDateTime sessionDateTime;
}
