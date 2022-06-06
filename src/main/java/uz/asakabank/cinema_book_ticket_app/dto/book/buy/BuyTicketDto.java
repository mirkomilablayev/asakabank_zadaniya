package uz.asakabank.cinema_book_ticket_app.dto.book.buy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.asakabank.cinema_book_ticket_app.dto.Dto;

import javax.persistence.Column;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BuyTicketDto implements Dto {
    @Column(nullable = false)
    private List<Long> seatId;
    @Column(nullable = false)
    private Long sessionId;
    private Long buyerId;
}
