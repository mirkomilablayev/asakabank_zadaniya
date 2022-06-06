package uz.asakabank.cinema_book_ticket_app.service;

import uz.asakabank.cinema_book_ticket_app.repository.BaseRepository;

public abstract class AbstractService<R extends BaseRepository> implements BaseService{
    public final R repository;

    public AbstractService(R repository) {
        this.repository = repository;
    }
}




