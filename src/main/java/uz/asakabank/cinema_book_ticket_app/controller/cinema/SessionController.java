package uz.asakabank.cinema_book_ticket_app.controller.cinema;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.asakabank.cinema_book_ticket_app.controller.AbstractController;
import uz.asakabank.cinema_book_ticket_app.controller.CrudController;
import uz.asakabank.cinema_book_ticket_app.dto.cinemaDtos.session.SessionCreateDto;
import uz.asakabank.cinema_book_ticket_app.dto.cinemaDtos.session.SessionUpdateDto;
import uz.asakabank.cinema_book_ticket_app.service.cinema.SessionService;
import uz.asakabank.cinema_book_ticket_app.tools.config.anotation.CheckRole;

@RestController
@RequestMapping("/api/session")
public class SessionController extends AbstractController<SessionService> implements CrudController<SessionCreateDto, SessionUpdateDto> {
    public SessionController(SessionService service) {
        super(service);
    }


    @CheckRole("MANAGER")
    @PostMapping("/createSession")
    @Override
    public HttpEntity<?> create(SessionCreateDto cd) {
        return service.create(cd);
    }

    @CheckRole("MANAGER")
    @PatchMapping("/updateSession")
    @Override
    public HttpEntity<?> update(SessionUpdateDto cd) {
        return service.update(cd);
    }


    @GetMapping("/getById/{id}")
    @Override
    public HttpEntity<?> get(Long id) {
        return service.get(id);
    }

    @CheckRole("MANAGER")
    @DeleteMapping("/delete/{id}")
    @Override
    public HttpEntity<?> deleteById(Long id) {
        return service.deleteById(id);
    }

    @GetMapping("/getAll/{cinemaId}")
    public HttpEntity<?> getSessions(@PathVariable Long cinemaId){
        return service.getSessions(cinemaId);
    }
}
