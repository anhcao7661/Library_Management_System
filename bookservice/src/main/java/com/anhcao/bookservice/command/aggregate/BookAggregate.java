package com.anhcao.bookservice.command.aggregate;

import com.anhcao.bookservice.command.command.CreateBookCommand;
import com.anhcao.bookservice.command.command.DeleteBookCommand;
import com.anhcao.bookservice.command.command.UpdateBookCommand;
import com.anhcao.bookservice.command.event.BookCreatedEvent;
import com.anhcao.bookservice.command.event.BookDeleteEvent;
import com.anhcao.bookservice.command.event.BookUpdateEvent;
import com.anhcao.commonservice.command.RollBackStatusBookCommand;
import com.anhcao.commonservice.command.UpdateStatusBookCommand;
import com.anhcao.commonservice.event.BookRollBackStatusEvent;
import com.anhcao.commonservice.event.BookUpdateStatusEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class BookAggregate {

    @AggregateIdentifier
    private String bookId;
    private String name;
    private String author;
    private Boolean isReady;

    public BookAggregate(){};

    @CommandHandler
    public BookAggregate(CreateBookCommand createBookCommand){
        BookCreatedEvent bookCreatedEvent
                = new BookCreatedEvent();
        BeanUtils.copyProperties(createBookCommand, bookCreatedEvent);
        AggregateLifecycle.apply(bookCreatedEvent);
    }

    @CommandHandler
    public void handle(UpdateBookCommand updateBookCommand){
        BookUpdateEvent bookUpdateEvent
                = new BookUpdateEvent();
        BeanUtils.copyProperties(updateBookCommand, bookUpdateEvent);
        AggregateLifecycle.apply(bookUpdateEvent);
    }

    @CommandHandler
    public void handle(DeleteBookCommand deleteBookCommand){
        BookDeleteEvent bookDeleteEvent
                = new BookDeleteEvent();
        BeanUtils.copyProperties(deleteBookCommand, bookDeleteEvent);
        AggregateLifecycle.apply(bookDeleteEvent);
    }

    @CommandHandler
    public void handle(RollBackStatusBookCommand command) {
        BookRollBackStatusEvent event = new BookRollBackStatusEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(UpdateStatusBookCommand command) {
        BookUpdateStatusEvent event = new BookUpdateStatusEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(BookUpdateStatusEvent event) {
        this.bookId = event.getBookId();
        this.isReady = event.getIsReady();
    }

    @EventSourcingHandler
    public void on(BookCreatedEvent event) {
        this.bookId = event.getBookId();;
        this.author = event.getAuthor();
        this.name = event.getName();
        this.isReady = event.getIsReady();
    }

    @EventSourcingHandler
    public void on(BookUpdateEvent event) {
        this.bookId = event.getBookId();;
        this.author = event.getAuthor();
        this.name = event.getName();
        this.isReady = event.getIsReady();
    }

    @EventSourcingHandler
    public void on(BookDeleteEvent event) {
        this.bookId = event.getBookId();;
    }

    @EventSourcingHandler
    public void on(BookRollBackStatusEvent event) {
        this.bookId = event.getBookId();
        this.isReady = event.getIsReady();
    }

}
