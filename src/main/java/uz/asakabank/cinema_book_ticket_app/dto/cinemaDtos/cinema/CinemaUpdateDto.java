package uz.asakabank.cinema_book_ticket_app.dto.cinemaDtos.cinema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.asakabank.cinema_book_ticket_app.dto.UpDto;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CinemaUpdateDto implements UpDto {
    private Long id;
    private String name;
}
