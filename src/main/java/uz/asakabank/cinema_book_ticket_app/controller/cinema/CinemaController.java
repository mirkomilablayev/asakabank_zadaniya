package uz.asakabank.cinema_book_ticket_app.controller.cinema;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.asakabank.cinema_book_ticket_app.controller.AbstractController;
import uz.asakabank.cinema_book_ticket_app.controller.CrudController;
import uz.asakabank.cinema_book_ticket_app.dto.cinemaDtos.cinema.CinemaCreateDto;
import uz.asakabank.cinema_book_ticket_app.dto.cinemaDtos.cinema.CinemaUpdateDto;
import uz.asakabank.cinema_book_ticket_app.service.cinema.CinemaService;
import uz.asakabank.cinema_book_ticket_app.tools.config.anotation.CheckRole;

@RestController
@RequestMapping("/api/cinema")
public class CinemaController extends AbstractController<CinemaService> implements CrudController<CinemaCreateDto, CinemaUpdateDto> {
    public CinemaController(CinemaService service) {
        super(service);
    }

//    @CheckRole("MANAGER")
    @PostMapping("/createCinema")
    @Override
    public HttpEntity<?> create(CinemaCreateDto cd) {
        return service.create(cd);
    }

//    @CheckRole("MANAGER")
    @PatchMapping("/update/{id}")
    @Override
    public HttpEntity<?> update(CinemaUpdateDto cd) {
        return service.update(cd);
    }


    @GetMapping("/get/{id}")
    @Override
    public HttpEntity<?> get(Long id) {
        return service.get(id);
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public HttpEntity<?> deleteById(Long id) {
        return service.deleteById(id);
    }
}
