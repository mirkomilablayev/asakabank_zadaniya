package uz.asakabank.cinema_book_ticket_app.tools.mapper;

import org.mapstruct.Mapper;
import uz.asakabank.cinema_book_ticket_app.entity.user.User;
import uz.asakabank.cinema_book_ticket_app.dto.auth.RegisterDto;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(RegisterDto registerDto);
}
