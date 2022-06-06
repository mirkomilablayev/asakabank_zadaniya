package uz.asakabank.cinema_book_ticket_app.dto.cinemaDtos.cinema;

import lombok.*;
import uz.asakabank.cinema_book_ticket_app.dto.Dto;

import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CinemaCreateDto implements Dto {
    private String cinema_name;
    private List<Long> managers;
}
