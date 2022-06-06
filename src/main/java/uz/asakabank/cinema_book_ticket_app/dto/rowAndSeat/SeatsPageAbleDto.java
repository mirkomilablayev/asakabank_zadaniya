package uz.asakabank.cinema_book_ticket_app.dto.rowAndSeat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.asakabank.cinema_book_ticket_app.dto.Dto;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SeatsPageAbleDto implements Dto {
    private Long hallId;
    private String hallName;
    private List<SeatPlace> seatPlaces;
}
