package com.anhcao.bookservice.query.projection;

import com.anhcao.bookservice.command.data.Book;
import com.anhcao.bookservice.command.data.BookRepository;
import com.anhcao.bookservice.query.model.BookResponseModel;
import com.anhcao.bookservice.query.queries.GetAllBooksQuery;
import com.anhcao.bookservice.query.queries.GetBookQuery;
import com.anhcao.commonservice.model.BookResponseCommonModel;
import com.anhcao.commonservice.query.GetDetailsBookQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookProjection {

    @Autowired
    private BookRepository bookRepository;

    @QueryHandler
    public BookResponseModel handle(GetBookQuery getBookQuery) {
        BookResponseModel bookResponseModel = new BookResponseModel();
        Book book = bookRepository.getReferenceById(getBookQuery.getBookId());
        BeanUtils.copyProperties(book, bookResponseModel);
        return bookResponseModel;
    }

    @QueryHandler
    List<BookResponseModel> handle(GetAllBooksQuery getAllBooksQuery) {
        List<Book> listEntity = bookRepository.findAll();
        List<BookResponseModel> listBook = new ArrayList<>();
        listEntity.forEach(s -> {
            BookResponseModel model = new BookResponseModel();
            BeanUtils.copyProperties(s, model);
            listBook.add(model);
        });
        return listBook;
    }

    @QueryHandler
    public BookResponseCommonModel handle(GetDetailsBookQuery getDetailsBookQuery) {
        BookResponseCommonModel model = new BookResponseCommonModel();
        Book book = bookRepository.getById(getDetailsBookQuery.getBookId());
        BeanUtils.copyProperties(book, model);

        return model;
    }
}
