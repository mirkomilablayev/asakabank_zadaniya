package uz.asakabank.cinema_book_ticket_app.tools.config.anotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckRole {
 String value();
}
