package uz.asakabank.cinema_book_ticket_app.dto.cinemaDtos.cinema;

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
public class ManagerDto implements Dto {
    private Long managerId;
    private String fullName;
    private List<String> roles;
}
