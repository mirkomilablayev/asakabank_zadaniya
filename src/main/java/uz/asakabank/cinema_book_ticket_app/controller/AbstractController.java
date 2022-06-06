package uz.asakabank.cinema_book_ticket_app.controller;

import uz.asakabank.cinema_book_ticket_app.service.BaseService;

public abstract class AbstractController <S extends BaseService>{
public final S service;
public AbstractController(S service){this.service = service;}
}
