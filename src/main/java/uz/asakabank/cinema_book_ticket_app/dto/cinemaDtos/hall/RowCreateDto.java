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
public class RowCreateDto implements Dto {
    @Column(nullable = false)
    private Long hallId;
    @Min(2)
    private Integer rowCount;
    @Min(2)
    private Integer seatPerRow ;
}
