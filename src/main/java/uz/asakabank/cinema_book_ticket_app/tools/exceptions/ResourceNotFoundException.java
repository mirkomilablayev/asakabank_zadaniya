package uz.asakabank.cinema_book_ticket_app.tools.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ResourceNotFoundException extends RuntimeException{
    private String msg;
}
