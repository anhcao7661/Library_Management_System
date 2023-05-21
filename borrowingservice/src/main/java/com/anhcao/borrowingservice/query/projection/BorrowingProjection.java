package com.anhcao.borrowingservice.query.projection;

import com.anhcao.borrowingservice.command.data.BorrowRepository;
import com.anhcao.borrowingservice.command.data.Borrowing;
import com.anhcao.borrowingservice.query.model.BorrowingResponseModel;
import com.anhcao.borrowingservice.query.queries.GetAllBorrowing;
import com.anhcao.borrowingservice.query.queries.GetListBorrowingByEmployeeQuery;
import com.anhcao.commonservice.model.BookResponseCommonModel;
import com.anhcao.commonservice.model.EmployeeResponseCommonModel;
import com.anhcao.commonservice.query.GetDetailsBookQuery;
import com.anhcao.commonservice.query.GetDetailsEmployeeQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BorrowingProjection {
    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private QueryGateway queryGateway;

    @QueryHandler
    public List<BorrowingResponseModel> handle(GetListBorrowingByEmployeeQuery query){
        List<BorrowingResponseModel> list  = new ArrayList<>();
        List<Borrowing> listEntity = borrowRepository.findByEmployeeIdAndReturnDateIsNull(query.getEmployeeId());
        return getBorrowingResponseModels(list, listEntity);
    }

    private List<BorrowingResponseModel> getBorrowingResponseModels(List<BorrowingResponseModel> list, List<Borrowing> listEntity) {
        listEntity.forEach(s ->{
            BorrowingResponseModel model = new BorrowingResponseModel();
            BeanUtils.copyProperties(s, model);
            model.setNameBook(queryGateway.query(new GetDetailsBookQuery(model.getBookId()), ResponseTypes.instanceOf(BookResponseCommonModel.class)).join().getName());
            EmployeeResponseCommonModel employee = queryGateway.query(new GetDetailsEmployeeQuery(model.getEmployeeId()), ResponseTypes.instanceOf(EmployeeResponseCommonModel.class)).join();
            model.setNameEmployee(employee.getFirstName()+" "+employee.getLastName());

            list.add(model);
        });
        return list;
    }

    @QueryHandler
    public List<BorrowingResponseModel> handle(GetAllBorrowing query){
        List<BorrowingResponseModel> list  = new ArrayList<>();
        List<Borrowing> listEntity = borrowRepository.findAll();
        return getBorrowingResponseModels(list, listEntity);
    }
}
