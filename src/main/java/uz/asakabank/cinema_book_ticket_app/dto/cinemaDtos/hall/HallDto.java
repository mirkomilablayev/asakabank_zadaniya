package uz.asakabank.cinema_book_ticket_app.dto.cinemaDtos.hall;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.asakabank.cinema_book_ticket_app.dto.Dto;

import javax.persistence.Column;
import javax.validation.constraints.Min;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HallDto implements Dto {
    private Long id;
    private String hallName;
    private Long cinemaId;
    private String cinemaName;
    private Long rowCount;
    private Long seatCountPerRow;
}
