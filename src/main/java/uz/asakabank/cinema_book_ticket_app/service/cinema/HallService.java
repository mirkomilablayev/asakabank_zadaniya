package uz.asakabank.cinema_book_ticket_app.service.cinema;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.asakabank.cinema_book_ticket_app.dto.cinemaDtos.hall.HallCreateDto;
import uz.asakabank.cinema_book_ticket_app.dto.cinemaDtos.hall.HallDto;
import uz.asakabank.cinema_book_ticket_app.dto.cinemaDtos.hall.HallUpdateDto;
import uz.asakabank.cinema_book_ticket_app.dto.cinemaDtos.hall.RowCreateDto;
import uz.asakabank.cinema_book_ticket_app.dto.rowAndSeat.SeatPlace;
import uz.asakabank.cinema_book_ticket_app.dto.rowAndSeat.SeatsPageAbleDto;
import uz.asakabank.cinema_book_ticket_app.entity.cinema.Hall;
import uz.asakabank.cinema_book_ticket_app.entity.cinema.HallRow;
import uz.asakabank.cinema_book_ticket_app.entity.cinema.Seat;
import uz.asakabank.cinema_book_ticket_app.repository.cinema.*;
import uz.asakabank.cinema_book_ticket_app.service.AbstractService;
import uz.asakabank.cinema_book_ticket_app.service.BaseService;
import uz.asakabank.cinema_book_ticket_app.service.CrudService;
import uz.asakabank.cinema_book_ticket_app.tools.exceptions.ObjectNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HallService extends AbstractService<HallRepo> implements BaseService, CrudService<HallCreateDto, HallUpdateDto> {

    private final CinemaRepo cinemaRepo;
    private final HallRowRepo hallRowRepo;
    private final SeatRepo seatRepo;


    public HallService(HallRepo repository,
                       CinemaRepo cinemaRepo,
                       HallRowRepo hallRowRepo,
                       SeatRepo seatRepo) {
        super(repository);
        this.cinemaRepo = cinemaRepo;
        this.hallRowRepo = hallRowRepo;
        this.seatRepo = seatRepo;
    }

    @Override
    public HttpEntity<?> create(HallCreateDto cd) {
        try {
            Hall hall = new Hall();
            hall.setCinema(cinemaRepo.findByIdAndIsDeleted(cd.getCinemaId(), false)
                    .orElseThrow(ObjectNotFoundException::new));
            hall.setIsActive(true);
            hall.setName(cd.getHallName());
            Hall save = repository.save(hall);
            createRowAndSeats(hall, cd.getRowCount(), cd.getSeatCountPerRow());
            return ResponseEntity.status(HttpStatus.OK).body(save);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e);
        }
    }

    @Override
    public HttpEntity<?> update(HallUpdateDto cd) {
        return null;
    }

    private void createRowAndSeats(Hall hall, Integer rowCount, Integer seatCountPerRow) {
        for (int i = 1; i <= rowCount; i++) {
            HallRow hallRow = new HallRow();
            hallRow.setHall(hall);
            hallRow.setRowNum(i);
            HallRow newHallRow = hallRowRepo.save(hallRow);
            for (int j = 1; j <= seatCountPerRow; j++) {
                Seat seat = new Seat();
                seat.setSeatNumber(i);
                seat.setHallRow(newHallRow);
                seat.setIsOutOfWork(false);
                seatRepo.save(seat);
            }
        }
    }


    @Override
    public HttpEntity<?> get(Long id) {
        Hall hall = repository.findByIdAndIsActive(id, true).orElseThrow(ObjectNotFoundException::new);
        return ResponseEntity.status(HttpStatus.OK).body(new HallDto(
                hall.getId(),
                hall.getName(),
                hall.getCinema().getId(),
                hall.getCinema().getCinema_name(),
                hallRowRepo.countAllByHall(hall),
                seatRepo.countSeat(hall.getId())
        ));
    }

    @Override
    public HttpEntity<?> deleteById(Long id) {
        Hall hall = repository.findByIdAndIsActive(id, true).orElseThrow(ObjectNotFoundException::new);
        if (hall.getIsActive()) {
            hall.setIsActive(false);
            repository.save(hall);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully Deleted");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND");
        }
    }

    public HttpEntity<?> createRowAndSeats(RowCreateDto rowCreateDto) {
        Hall hall = repository.findByIdAndIsActive(rowCreateDto.getHallId(), true).orElseThrow(ObjectNotFoundException::new);
        if (hallRowRepo.countAllByHall(hall).equals(0L)) {
            for (int i = 1; i <= rowCreateDto.getRowCount(); i++) {
                HallRow hallRow = new HallRow();
                hallRow.setHall(hall);
                hallRow.setRowNum(i);
                HallRow newHallRow = hallRowRepo.save(hallRow);
                for (int j = 1; j <= rowCreateDto.getSeatPerRow(); j++) {
                    Seat seat = new Seat();
                    seat.setSeatNumber(j);
                    seat.setHallRow(newHallRow);
                    seat.setIsOutOfWork(false);
                    seatRepo.save(seat);
                }
            }
            return ResponseEntity.status(HttpStatus.OK).body("Success");
        } else
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ushu Zal uchun allaqachon O'rindiqlar tayorlangan");
    }

    public HttpEntity<?> getSeats(Long hallId, Long size, Long page) {
        Hall hall = repository.findByIdAndIsActive(hallId, true).orElseThrow(ObjectNotFoundException::new);
        List<SeatPlace> seatPlaces = new ArrayList<>();
        seatRepo.getSeatsPageable(hallId,size,page).forEach(seat -> {
            SeatPlace seatPlace = new SeatPlace();
            seatPlace.setSeatId(seat.getId());
            seatPlace.setSeatNum(seat.getSeatNumber());
            seatPlace.setHalRowId(seat.getHallRow().getId());
            seatPlace.setRowNum(seat.getHallRow().getRowNum());
            seatPlaces.add(seatPlace);
        });
        return ResponseEntity.status(HttpStatus.OK).body(new SeatsPageAbleDto(
                hall.getId(),
                hall.getName(),
                seatPlaces
        ));
    }

    public HttpEntity<?> checkSeat(Long seatId) {

        return null;
    }
}
