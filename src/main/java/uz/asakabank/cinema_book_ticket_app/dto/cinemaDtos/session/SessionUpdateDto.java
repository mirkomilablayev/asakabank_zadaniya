package uz.asakabank.cinema_book_ticket_app.dto.cinemaDtos.session;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.asakabank.cinema_book_ticket_app.dto.UpDto;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SessionUpdateDto implements UpDto {
    private Long id;
    private String contentName;
    private Long hallId;
    private Long contentTypeId;
    private LocalDateTime sessionDateTime;
}
