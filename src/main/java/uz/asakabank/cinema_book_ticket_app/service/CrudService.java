package uz.asakabank.cinema_book_ticket_app.service;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PathVariable;
import uz.asakabank.cinema_book_ticket_app.dto.Dto;
import uz.asakabank.cinema_book_ticket_app.dto.UpDto;
import uz.asakabank.cinema_book_ticket_app.entity.BaseEntity;

public interface CrudService<
        DTO extends Dto,
        UpDto extends uz.asakabank.cinema_book_ticket_app.dto.UpDto> {

    HttpEntity<?> create(DTO cd);

    HttpEntity<?> update(UpDto cd);

    HttpEntity<?> get(@PathVariable Long id);

    HttpEntity<?> deleteById(@PathVariable Long id);
}
