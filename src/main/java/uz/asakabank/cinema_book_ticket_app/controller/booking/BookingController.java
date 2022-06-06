package uz.asakabank.cinema_book_ticket_app.controller.booking;

import org.springframework.http.HttpEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.asakabank.cinema_book_ticket_app.controller.AbstractController;
import uz.asakabank.cinema_book_ticket_app.dto.book.BookingListCreateDto;
import uz.asakabank.cinema_book_ticket_app.dto.book.BookingListUpdateDto;
import uz.asakabank.cinema_book_ticket_app.dto.book.buy.BuyBookedTicketDto;
import uz.asakabank.cinema_book_ticket_app.dto.book.buy.BuyTicketDto;
import uz.asakabank.cinema_book_ticket_app.dto.book.buy.TicketDtoForPdf;
import uz.asakabank.cinema_book_ticket_app.service.book.BookingService;
import uz.asakabank.cinema_book_ticket_app.service.CrudService;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/booking")
public class BookingController extends AbstractController<BookingService> implements CrudService<BookingListCreateDto, BookingListUpdateDto> {
    public BookingController(BookingService service) {
        super(service);
    }

    @PostMapping("/BookPlace")
    @Override
    public HttpEntity<?> create(@RequestBody BookingListCreateDto cd) {
        return service.create(cd);
    }


    @PostMapping("/buyBookedTicket")
    public void buyTicket(@RequestBody BuyBookedTicketDto buyBookedTicketDto,HttpServletResponse response){
        service.PdfTicketMaker(service.buyTicket(buyBookedTicketDto));
        service.downloadFileMethod(response);
    }

    @PostMapping("/buyTicket")
    public void buyTicket(@RequestBody BuyTicketDto buyTicketDto, HttpServletResponse response){
        service.PdfTicketMaker(service.buyTicket(buyTicketDto));
        service.downloadFileMethod(response);
    }


    @Override
    public HttpEntity<?> update(BookingListUpdateDto cd) {
        return null;
    }


    @Override
    public HttpEntity<?> get(Long id) {
        return null;
    }

    @Override
    public HttpEntity<?> deleteById(Long id) {
        return null;
    }
}
