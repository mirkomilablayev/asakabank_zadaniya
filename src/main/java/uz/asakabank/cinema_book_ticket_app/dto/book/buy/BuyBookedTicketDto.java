package uz.asakabank.cinema_book_ticket_app.dto.book.buy;

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
public class BuyBookedTicketDto implements Dto {
    private String contentName;
    private LocalDateTime meetingTime;
    private LocalDateTime seansTime;
    private Integer ticketCount;
    private String lastName;
    private String secretWord;
}
