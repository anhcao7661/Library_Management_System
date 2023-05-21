package com.anhcao.borrowingservice.command.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Date;

public class UpdateBookReturnCommand {
    @TargetAggregateIdentifier
    private String id;
    private String bookId;
    private String employee;
    private Date returnDate;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getBookId() {
        return bookId;
    }
    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
    public String getEmployee() {
        return employee;
    }
    public void setEmployee(String employee) {
        this.employee = employee;
    }
    public Date getReturnDate() {
        return returnDate;
    }
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
    public UpdateBookReturnCommand(String id, String bookId, String employee, Date returnDate) {
        super();
        this.id = id;
        this.bookId = bookId;
        this.employee = employee;
        this.returnDate = returnDate;
    }
}
