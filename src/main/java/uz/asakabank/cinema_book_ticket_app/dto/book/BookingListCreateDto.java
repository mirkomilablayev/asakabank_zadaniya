package uz.asakabank.cinema_book_ticket_app.dto.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.asakabank.cinema_book_ticket_app.dto.Dto;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookingListCreateDto implements Dto {
    @Min(1)
    private Long sessionId;
    @Column(nullable = false)
    private String secretWord;
    @Column(nullable = false)
    private String lastName;
    private LocalDateTime meetingTime;
    private List<Long> seatsId;
}
