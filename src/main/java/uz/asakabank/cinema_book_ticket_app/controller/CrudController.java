package uz.asakabank.cinema_book_ticket_app.controller;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import uz.asakabank.cinema_book_ticket_app.dto.Dto;
import uz.asakabank.cinema_book_ticket_app.dto.UpDto;
import uz.asakabank.cinema_book_ticket_app.entity.BaseEntity;

public interface CrudController<
        DTO extends Dto,
        UpDto extends uz.asakabank.cinema_book_ticket_app.dto.UpDto> {

     HttpEntity<?> create(@RequestBody DTO cd);

     HttpEntity<?> update(@RequestBody UpDto cd);

     HttpEntity<?> get(@PathVariable Long id);

     HttpEntity<?> deleteById(@PathVariable Long id);
}
