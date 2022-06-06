package uz.asakabank.cinema_book_ticket_app.dto.book.buy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TicketDtoForPdf {
    private String buyer;
    private String cinemaName;
    private String hallName;

    private LocalDateTime sessionTime;
    private String contentName;
    private String contentType;

    private LocalDateTime boughtAt;

    private Integer row;
    private Integer seat;

}
