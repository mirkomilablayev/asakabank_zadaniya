package uz.asakabank.cinema_book_ticket_app.service.book;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import uz.asakabank.cinema_book_ticket_app.dto.book.BookingListCreateDto;
import uz.asakabank.cinema_book_ticket_app.dto.book.BookingListUpdateDto;
import uz.asakabank.cinema_book_ticket_app.dto.book.buy.BuyBookedTicketDto;
import uz.asakabank.cinema_book_ticket_app.dto.book.buy.BuyTicketDto;
import uz.asakabank.cinema_book_ticket_app.dto.book.buy.TicketDtoForPdf;
import uz.asakabank.cinema_book_ticket_app.entity.book.BookedSeat;
import uz.asakabank.cinema_book_ticket_app.entity.book.BookingList;
import uz.asakabank.cinema_book_ticket_app.entity.book.SoldTicket;
import uz.asakabank.cinema_book_ticket_app.entity.cinema.Seat;
import uz.asakabank.cinema_book_ticket_app.entity.cinema.Session;
import uz.asakabank.cinema_book_ticket_app.repository.book.BookedSeatRepo;
import uz.asakabank.cinema_book_ticket_app.repository.book.BookingListRepo;
import uz.asakabank.cinema_book_ticket_app.repository.book.SoldTicketRepo;
import uz.asakabank.cinema_book_ticket_app.repository.cinema.HallBusyListRepo;
import uz.asakabank.cinema_book_ticket_app.repository.cinema.SeatRepo;
import uz.asakabank.cinema_book_ticket_app.repository.cinema.SessionRepo;
import uz.asakabank.cinema_book_ticket_app.repository.user.UserRepo;
import uz.asakabank.cinema_book_ticket_app.service.AbstractService;
import uz.asakabank.cinema_book_ticket_app.service.BaseService;
import uz.asakabank.cinema_book_ticket_app.service.CrudService;
import uz.asakabank.cinema_book_ticket_app.tools.exceptions.ObjectNotFoundException;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService extends AbstractService<BookingListRepo> implements BaseService, CrudService<BookingListCreateDto, BookingListUpdateDto> {
    private final SessionRepo sessionRepo;
    private final HallBusyListRepo hallBusyListRepo;
    private final BookedSeatRepo bookedSeatRepo;
    private final SeatRepo seatRepo;
    private final SoldTicketRepo soldTicketRepo;
    private final UserRepo userRepo;

    public BookingService(BookingListRepo repository,
                          SessionRepo sessionRepo,
                          HallBusyListRepo hallBusyListRepo,
                          BookedSeatRepo bookedSeatRepo,
                          SeatRepo seatRepo,
                          SoldTicketRepo soldTicketRepo,
                          UserRepo userRepo) {
        super(repository);
        this.sessionRepo = sessionRepo;
        this.hallBusyListRepo = hallBusyListRepo;
        this.bookedSeatRepo = bookedSeatRepo;
        this.seatRepo = seatRepo;
        this.soldTicketRepo = soldTicketRepo;
        this.userRepo = userRepo;
    }

    @Override
    public HttpEntity<?> create(BookingListCreateDto cd) {
        BookingList bookingList = new BookingList();
        Session session = sessionRepo
                .findByIdAndIsActive(cd.getSessionId(), true)
                .orElseThrow(ObjectNotFoundException::new);
        bookingList.setSession(session);
        bookingList.setLastName(cd.getLastName());
        bookingList.setMeetingTime(cd.getMeetingTime());
        bookingList.setSecretWord(cd.getSecretWord());
        bookingList.setMeetingTime(cd.getMeetingTime());

        // if meeting time is after session time
        // the booking process will close
        if (session.getSessionDate().isBefore(cd.getMeetingTime())) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(
                            makerSeatsBusy(
                                    cd.getSeatsId(),
                                    repository.save(bookingList), session.getSessionDate()
                            )
                    );
        }
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("Saqlanmadi");
    }


    private List<BookedSeat> makerSeatsBusy(List<Long> seatsId, BookingList bookingList, LocalDateTime sessionDate) {
        List<BookedSeat> bookedSeats = new ArrayList<>();
        for (Long seatId : seatsId) {
            if (!bookedSeatRepo.existsByBookingList_Session_SessionDateAndSeat_Id(sessionDate,seatId) &&
                    !soldTicketRepo.existsBySeat_IdAndSession_IsActiveAndSession_SessionDate(seatId,true, sessionDate)) {
                BookedSeat bookedSeat = new BookedSeat(bookingList, seatRepo.findById(seatId).orElseThrow(ObjectNotFoundException::new));
                BookedSeat save = bookedSeatRepo.save(bookedSeat);
                bookedSeats.add(save);
            }
        }
        return bookedSeats;
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

    public List<TicketDtoForPdf> buyTicket(BuyBookedTicketDto item) {
        List<BookingList> lists = repository.findAllByLastNameAndSecretWordAndMeetingTime(
                item.getLastName(),
                item.getSecretWord(),
                item.getMeetingTime()
        );

        List<TicketDtoForPdf> ticketDtoForPdfs = new ArrayList<>();
        for (BookingList list : lists) {
            List<BookedSeat> bookedSeats = bookedSeatRepo.findAllByBookingList(list);
            for (BookedSeat bookedSeat : bookedSeats) {
                TicketDtoForPdf ticketDtoForPdf = buyProcess(bookedSeat.getBookingList().getSession(), bookedSeat.getSeat());
                if (ticketDtoForPdf != null)
                    ticketDtoForPdfs.add(ticketDtoForPdf);
            }
        }
        return ticketDtoForPdfs;
    }

    public List<TicketDtoForPdf> buyTicket(BuyTicketDto buyTicketDto) {
        Session session = sessionRepo.findByIdAndIsActive(buyTicketDto.getSessionId(), true).orElseThrow(ObjectNotFoundException::new);
        List<TicketDtoForPdf> ticketDtoForPdfs = new ArrayList<>();
        if (session.getSessionDate().isBefore(LocalDateTime.now())) {
            for (Long aLong : buyTicketDto.getSeatId()) {
                TicketDtoForPdf ticketDtoForPdf = buyProcess(buyTicketDto, session, aLong);
                if (ticketDtoForPdf != null)
                    ticketDtoForPdfs.add(ticketDtoForPdf);
            }
        }
        return ticketDtoForPdfs;
    }

    private TicketDtoForPdf buyProcess(BuyTicketDto buyTicketDto, Session session, Long seatId) {
        if (!bookedSeatRepo.existsByBookingList_Session_SessionDateAndSeat_Id(session.getSessionDate(),seatId)) {
            SoldTicket soldTicket = new SoldTicket();
            soldTicket.setSession(session);
            soldTicket.setBuyer(buyTicketDto.getBuyerId() == null ? null :
                    userRepo.findByIdAndIsDeleted(buyTicketDto.getBuyerId(), false)
                            .orElseThrow(ObjectNotFoundException::new));
            soldTicket.setSeat(
                    seatRepo.findById(seatId)
                            .orElseThrow(ObjectNotFoundException::new)
            );
            SoldTicket save = soldTicketRepo.save(soldTicket);
            return makeTicketForPdfDto(save);
        } else
            return null;
    }


    private TicketDtoForPdf buyProcess(Session session, Seat seat) {
            SoldTicket soldTicket = new SoldTicket();
            soldTicket.setSession(session);
            soldTicket.setSeat(
                    seat
            );
            SoldTicket save = soldTicketRepo.save(soldTicket);
            return makeTicketForPdfDto(save);
    }


    private TicketDtoForPdf makeTicketForPdfDto(SoldTicket soldTicket) {
        TicketDtoForPdf item = new TicketDtoForPdf();
        item.setBuyer(soldTicket.getBuyer() == null ?
                "" : soldTicket.getBuyer().getFirstName() + " " + soldTicket.getBuyer().getLastName());

        item.setSeat(soldTicket.getSeat().getSeatNumber());
        item.setRow(soldTicket.getSeat().getHallRow().getRowNum());
        item.setBoughtAt(LocalDateTime.now());
        item.setCinemaName(soldTicket.getSession().getHall().getCinema().getCinema_name());
        item.setHallName(soldTicket.getSession().getHall().getName());
        item.setContentName(soldTicket.getSession().getContentName());
        item.setContentType(soldTicket.getSession().getContentType().getName());
        item.setSessionTime(soldTicket.getSession().getSessionDate());
        return item;
    }

    @Scheduled(fixedRate = 1000 * 60 * 60 * 2)
    public void deleteLateBookings() {
        List<Long> delayedBookingsId = repository.getDelayedBookingsId();
        for (Long aLong : delayedBookingsId) {
            bookedSeatRepo.deleteAllByBookingList(
                    repository.findById(aLong)
                            .orElseThrow(ObjectNotFoundException::new)
            );
            repository.deleteById(aLong);
        }
    }

    public void downloadFileMethod(HttpServletResponse response) {
        File file = new File("src/main/resources/items/Ticket.pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
        response.setContentType("application/pdf");
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            FileCopyUtils.copy(fileInputStream, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void PdfTicketMaker(List<TicketDtoForPdf> buyTicket) {
        try {
            PdfWriter writer = new PdfWriter("src/main/resources/items/Ticket.pdf");
            PdfDocument pdfDocument = new PdfDocument(writer);
            pdfDocument.addNewPage();

            Document document = new Document(pdfDocument);
            for (TicketDtoForPdf item : buyTicket) {
                document.add(new Paragraph("Kinoteatr nomi: " + item.getCinemaName() + ", Zall nomi:" + item.getHallName()));
                document.add(new Paragraph("Seans nomi: " + item.getContentName() + ", Content turi: " + item.getContentType()));
                document.add(new Paragraph("Boshlanish vaqti: " + item.getSessionTime().getDayOfMonth() + "" +
                        "-" + item.getSessionTime().getMonth() +
                        " " + item.getSessionTime().getYear() + "-yil, Vaqti - " + item.getSessionTime().getHour() + ":" + item.getSessionTime().getMinute()));

                document.add(new Paragraph(""));
                document.add(new Paragraph("O'rindiq manzili: " + item.getRow() + "-qator" + item.getSeat() + "-joy"));
                document.add(new Paragraph("Sotib Olingan Vaqti vaqti: " + item.getBoughtAt().getDayOfMonth() + "" +
                        "-" + item.getBoughtAt().getMonth() +
                        " " + item.getBoughtAt().getYear() + "-yil, Vaqti - " + item.getBoughtAt().getHour() + ":" + item.getBoughtAt().getMinute()));
                document.add(new Paragraph("----------------------------------------------------------------------"));
            }


            document.close();
            System.out.println("PDF Created");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
