package uz.asakabank.cinema_book_ticket_app.service.cinema;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import uz.asakabank.cinema_book_ticket_app.dto.cinemaDtos.session.SessionCreateDto;
import uz.asakabank.cinema_book_ticket_app.dto.cinemaDtos.session.SessionDto;
import uz.asakabank.cinema_book_ticket_app.dto.cinemaDtos.session.SessionUpdateDto;
import uz.asakabank.cinema_book_ticket_app.entity.cinema.HallBusyList;
import uz.asakabank.cinema_book_ticket_app.entity.cinema.Session;
import uz.asakabank.cinema_book_ticket_app.repository.cinema.*;
import uz.asakabank.cinema_book_ticket_app.service.AbstractService;
import uz.asakabank.cinema_book_ticket_app.service.BaseService;
import uz.asakabank.cinema_book_ticket_app.service.CrudService;
import uz.asakabank.cinema_book_ticket_app.tools.exceptions.ObjectNotFoundException;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class SessionService extends AbstractService<SessionRepo> implements BaseService, CrudService<SessionCreateDto, SessionUpdateDto> {
    private final ContentTypeRepo contentTypeRepo;
    private final HallRepo hallRepo;
    private final HallBusyListRepo hallBusyListRepo;

    public SessionService(SessionRepo repository,
                          ContentTypeRepo contentTypeRepo,
                          HallRepo hallRepo,
                          HallBusyListRepo hallBusyListRepo) {
        super(repository);
        this.contentTypeRepo = contentTypeRepo;
        this.hallRepo = hallRepo;
        this.hallBusyListRepo = hallBusyListRepo;
    }

    @Override
    public HttpEntity<?> create(SessionCreateDto cd) {

            Session session = new Session();
            session.setIsActive(true);
            session.setContentName(cd.getContentName());
            session.setContentType(contentTypeRepo.findById(cd.getContentTypeId()).orElseThrow(()->new ObjectNotFoundException("contenttype not found with this id = "+cd.getContentTypeId()+" at line"+new Throwable().getStackTrace()[0].getLineNumber()+" !!!!")));
            session.setSessionDate(cd.getSessionDateTime());
            if (isHallBusy(
                    cd.getHallId(),
                    cd.getSessionDateTime()
            )) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Hall is Busy in " + cd.getSessionDateTime());
            } else{
                session.setHall(hallRepo.findByIdAndIsActive(cd.getHallId(), true)
                        .orElseThrow(ObjectNotFoundException::new));
                Session save = repository.save(session);
                hallBusyListRepo.save(new HallBusyList(cd.getSessionDateTime(), hallRepo.findByIdAndIsActive(cd.getHallId(), true).orElseThrow(ObjectNotFoundException::new),save));
                return ResponseEntity.status(HttpStatus.OK).body(save);
            }
    }

    @Override
    public HttpEntity<?> update(SessionUpdateDto cd) {
        try {
            Optional<Session> sessionOptional = repository.findById(cd.getId());
            if (sessionOptional.isPresent()) {
                Session session = sessionOptional.get();
                if (!session.getSessionDate().equals(cd.getSessionDateTime())) {
                    session.setSessionDate(cd.getSessionDateTime());
                }
                if (!session.getHall().getId().equals(cd.getHallId())) {
                    session.setHall(hallRepo.findByIdAndIsActive(cd.getHallId(),true).orElseThrow(ObjectNotFoundException::new));
                }
                if (!session.getContentName().equals(cd.getContentName())) {
                    session.setContentName(cd.getContentName());
                }
                if (!session.getContentType().getId().equals(cd.getContentTypeId())) {
                    session.setContentType(contentTypeRepo.findById(cd.getContentTypeId()).orElseThrow(ObjectNotFoundException::new));
                }
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(repository.save(session));
            } else {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(cd.getContentName() + " NOT FOUND");
            }
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("ERROR: aT" + e.getStackTrace()[0].getLineNumber() + ": CAUSE " + e.getCause());
        }
    }

    private Boolean isHallBusy(Long hallId,LocalDateTime sessionDateTime) {
        return hallBusyListRepo
                .existsByBusyTimeAndHall(
                        sessionDateTime,
                        hallRepo.findByIdAndIsActive(hallId, true)
                                .orElseThrow(ObjectNotFoundException::new)
                );
    }

    @Override
    public HttpEntity<?> get(Long id) {
        Session session = repository.findByIdAndIsActive(id, true).orElseThrow(ObjectNotFoundException::new);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SessionDto(
                        session.getId(),
                        session.getContentName(),
                        session.getHall().getName(),
                        session.getHall().getId(),
                        session.getSessionDate(),
                        session.getContentType().getName(),
                        session.getIsActive()
                ));
    }

    @Override
    public HttpEntity<?> deleteById(Long id) {
        Optional<Session> sessionOptional = repository.findById(id);
        if (sessionOptional.isPresent()) {
            Session session = sessionOptional.get();
            if (session.getIsActive()) {
                session.setIsActive(false);
                Session save = repository.save(session);
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(save.getContentName() + " Successfully deleted");
            }
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("session not found " + id + " in system");
    }


    /**
     * this function works automatically in every 2 hours,
     * and it is tasks is to make sessions unActive which became old
     */
    @Scheduled(fixedRate = 1000 * 60 * 60 * 2)
    public void deleteOldSession() {
        repository.findAllByIsActiveAndSessionDateAfter(true, LocalDateTime.now()).forEach(session -> {
            if (session.getIsActive() &&
                    (session.getSessionDate().equals(LocalDateTime.now()) ||
                            session.getSessionDate().isAfter(LocalDateTime.now()))) {
                session.setIsActive(false);
                repository.save(session);
            }
        });
    }

    public HttpEntity<?> getSessions(Long cinemaId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(makeSessionSto(repository.getSessions(cinemaId)));
    }

    private List<SessionDto> makeSessionSto(List<Session> sessions) {
        List<SessionDto> sessionDtos = new ArrayList<>();
        sessions.forEach(session -> {
            SessionDto sessionDto = new SessionDto(
                    session.getId(),
                    session.getContentName(),
                    session.getHall().getName(),
                    session.getHall().getId(),
                    session.getSessionDate(),
                    session.getContentType().getName(),
                    session.getIsActive()
            );
            sessionDtos.add(sessionDto);
        });
        return sessionDtos;
    }
}
