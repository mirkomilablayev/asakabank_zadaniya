package uz.asakabank.cinema_book_ticket_app.dto.rowAndSeat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.asakabank.cinema_book_ticket_app.dto.Dto;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SeatPlace implements Dto {
    private Long seatId;
    private Integer seatNum;
    private Long halRowId;
    private Integer rowNum;
}
