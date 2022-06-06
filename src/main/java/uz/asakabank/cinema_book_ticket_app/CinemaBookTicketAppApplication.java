package uz.asakabank.cinema_book_ticket_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;


@Component
@EnableScheduling
@SpringBootApplication
public class CinemaBookTicketAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(CinemaBookTicketAppApplication.class, args);
    }

}
