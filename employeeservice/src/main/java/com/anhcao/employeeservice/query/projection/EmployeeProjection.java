package com.anhcao.employeeservice.query.projection;

import com.anhcao.commonservice.model.EmployeeResponseCommonModel;
import com.anhcao.commonservice.query.GetDetailsEmployeeQuery;
import com.anhcao.employeeservice.command.data.Employee;
import com.anhcao.employeeservice.command.data.EmployeeRepository;
import com.anhcao.employeeservice.query.model.EmployeeResponseModel;
import com.anhcao.employeeservice.query.queries.GetAllEmployeeQuery;
import com.anhcao.employeeservice.query.queries.GetEmployeesQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeProjection {
    @Autowired
    private EmployeeRepository employeeRepository;

    @QueryHandler
    public EmployeeResponseModel handle(GetEmployeesQuery getEmployeesQuery) {
        EmployeeResponseModel model = new EmployeeResponseModel();
        Employee employee = employeeRepository.getReferenceById(getEmployeesQuery.getEmployeeId());
        BeanUtils.copyProperties(employee, model);

        return model;
    }

    @QueryHandler
    public List<EmployeeResponseModel> handle(GetAllEmployeeQuery getAllEmployeeQuery){
        List<EmployeeResponseModel> listModel = new ArrayList<>();
        List<Employee> listEntity = employeeRepository.findAll();
        listEntity.forEach(s -> {
            EmployeeResponseModel model = new EmployeeResponseModel();
            BeanUtils.copyProperties(s, model);
            listModel.add(model);
        });
        return listModel;
    }

    public EmployeeResponseCommonModel handle(GetDetailsEmployeeQuery getDetailsEmployeeQuery) {
        EmployeeResponseCommonModel model = new EmployeeResponseCommonModel();
        Employee employee = employeeRepository.getById(getDetailsEmployeeQuery.getEmployeeId());
        BeanUtils.copyProperties(employee, model);

        return model;
    }

}
