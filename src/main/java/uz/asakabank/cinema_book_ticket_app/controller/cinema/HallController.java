package uz.asakabank.cinema_book_ticket_app.controller.cinema;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.asakabank.cinema_book_ticket_app.controller.AbstractController;
import uz.asakabank.cinema_book_ticket_app.controller.CrudController;
import uz.asakabank.cinema_book_ticket_app.dto.book.BookingListCreateDto;
import uz.asakabank.cinema_book_ticket_app.dto.cinemaDtos.hall.HallCreateDto;
import uz.asakabank.cinema_book_ticket_app.dto.cinemaDtos.hall.HallUpdateDto;
import uz.asakabank.cinema_book_ticket_app.dto.cinemaDtos.hall.RowCreateDto;
import uz.asakabank.cinema_book_ticket_app.service.cinema.HallService;

@RestController
@RequestMapping("/api/hall")
public class HallController extends AbstractController<HallService> implements CrudController<HallCreateDto, HallUpdateDto> {

    public HallController(HallService service) {
        super(service);
    }


    @PostMapping("/createHall")
    @Override
    public HttpEntity<?> create(HallCreateDto cd) {
        return service.create(cd);
    }

    @Override
    public HttpEntity<?> update(HallUpdateDto cd) {
        return null;
    }

    @GetMapping("/getHall/{id}")
    @Override
    public HttpEntity<?> get(Long id) {
        return service.get(id);
    }


    @GetMapping("/deleteHall")
    @Override
    public HttpEntity<?> deleteById(Long id) {
        return service.deleteById(id);
    }


    @PostMapping("/createRowsAndSeats")
    public HttpEntity<?> createSeatsForHall(@RequestBody RowCreateDto rowCreateDto){
        return service.createRowAndSeats(rowCreateDto);
    }

    @GetMapping("/getSeats/{hallId}")
    public HttpEntity<?> getFreeSeats(@PathVariable Long hallId,
                                      @RequestParam(defaultValue = "1") Long page,
                                      @RequestParam(defaultValue = "20") Long size){
        return service.getSeats(hallId,size,page);
    }

}
