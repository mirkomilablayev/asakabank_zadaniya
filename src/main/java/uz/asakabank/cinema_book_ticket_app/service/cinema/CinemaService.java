package uz.asakabank.cinema_book_ticket_app.service.cinema;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import uz.asakabank.cinema_book_ticket_app.dto.cinemaDtos.cinema.CinemaCreateDto;
import uz.asakabank.cinema_book_ticket_app.dto.cinemaDtos.cinema.CinemaDto;
import uz.asakabank.cinema_book_ticket_app.dto.cinemaDtos.cinema.CinemaUpdateDto;
import uz.asakabank.cinema_book_ticket_app.dto.cinemaDtos.cinema.ManagerDto;
import uz.asakabank.cinema_book_ticket_app.entity.cinema.Cinema;
import uz.asakabank.cinema_book_ticket_app.entity.user.User;
import uz.asakabank.cinema_book_ticket_app.entity.user.UserRole;
import uz.asakabank.cinema_book_ticket_app.repository.cinema.CinemaRepo;
import uz.asakabank.cinema_book_ticket_app.repository.user.RoleRepo;
import uz.asakabank.cinema_book_ticket_app.repository.user.UserRepo;
import uz.asakabank.cinema_book_ticket_app.service.AbstractService;
import uz.asakabank.cinema_book_ticket_app.service.BaseService;
import uz.asakabank.cinema_book_ticket_app.service.CrudService;
import uz.asakabank.cinema_book_ticket_app.tools.exceptions.ObjectNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CinemaService extends AbstractService<CinemaRepo> implements BaseService, CrudService<CinemaCreateDto, CinemaUpdateDto> {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    public CinemaService(CinemaRepo repository,
                         UserRepo userRepo,
                         RoleRepo roleRepo) {
        super(repository);
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    @Override
    public HttpEntity<?> create(CinemaCreateDto cd) {
        try{
            Cinema cinema = new Cinema();
            cinema.setCinema_name(cd.getCinema_name());
            cinema.setIsDeleted(false);
            cinema.setManagers(getCinemaManager(cd.getManagers()));
            return ResponseEntity.status(HttpStatus.OK).body(repository.save(cinema));
        }catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e);
        }
    }

    @Override
    public HttpEntity<?> update(CinemaUpdateDto cd) {
        Cinema cinema = repository.findByIdAndIsDeleted(cd.getId(), false).orElseThrow(ObjectNotFoundException::new);
        if (!cinema.getCinema_name().equals(cd.getName())){
            cinema.setCinema_name(cd.getName());
            repository.save(cinema);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully Updated");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Updated");
    }


    @Override
    public HttpEntity<?> get(Long id) {
        Cinema cinema = repository.findByIdAndIsDeleted(id, false).orElseThrow(ObjectNotFoundException::new);
        List<ManagerDto> managerDtos = new ArrayList<>();
        cinema.getManagers().forEach(user -> {
            ManagerDto managerDto = new ManagerDto();
            managerDto.setManagerId(user.getId());
            List<String> managerRoles = new ArrayList<>();
            user.getUserRoleSet().forEach(userRole -> {
                managerRoles.add(userRole.getName());
            });
            managerDto.setRoles(managerRoles);
            managerDto.setFullName(user.getFirstName()+" "+user.getLastName());
            managerDtos.add(managerDto);
        });
        return ResponseEntity.status(HttpStatus.OK).body(new CinemaDto(
            cinema.getId(),
            cinema.getCinema_name(),
            managerDtos
        ));
    }

    @Override
    public HttpEntity<?> deleteById(Long id) {
        Cinema cinema = repository.findByIdAndIsDeleted(id, false).orElseThrow(ObjectNotFoundException::new);
        if (!cinema.getIsDeleted()){
            cinema.setIsDeleted(true);
            Cinema save = repository.save(cinema);
            return ResponseEntity.status(HttpStatus.OK).body(save.getCinema_name()+" successfully deleted");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This Cinema not found");
    }

    private List<User> getCinemaManager(List<Long> managers) {
        List<User> res = new ArrayList<>();
        managers.forEach(aLong -> {
            User user = userRepo.findByIdAndIsDeleted(aLong, false)
                    .orElseThrow(ObjectNotFoundException::new);
            Set<UserRole> userRoleSet = user.getUserRoleSet();
            userRoleSet.add(roleRepo.findByNameAndIsActive("MANAGER",true)
                    .orElseThrow(ObjectNotFoundException::new));
            user.setUserRoleSet(userRoleSet);
            res.add(userRepo.save(user));
        });
        return res;
    }
}
