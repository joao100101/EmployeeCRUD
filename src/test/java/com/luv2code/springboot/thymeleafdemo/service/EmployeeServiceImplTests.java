package com.luv2code.springboot.thymeleafdemo.service;

import com.luv2code.springboot.thymeleafdemo.dao.EmployeeRepository;
import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import com.luv2code.springboot.thymeleafdemo.exceptions.EmployeeNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class EmployeeServiceImplTests {
    public static final Integer ID = 1;
    public static final String EMPLOYEE_NOT_FOUND_EXCEPTION_MESSAGE = "Did not find employee id - " + ID;
    public static final String FIRST_NAME = "John";
    public static final String LAST_NAME = "Arboco";
    public static final String EMAIL = "john@luv2code.com";
    @Mock
    private EmployeeRepository repository;
    @InjectMocks
    private EmployeeServiceImpl service;
    @Mock
    private ModelMapper mapper;
    private Employee employee;
    private Optional<Employee> optionalEmployee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startEmployee();
    }

    @Test
    void findByID() {
        when(repository.findById(Mockito.anyInt())).thenReturn(optionalEmployee);

        Employee response = service.findById(ID);
        assertNotNull(response);
        assertEquals(Employee.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(FIRST_NAME, response.getFirstName());
        assertEquals(LAST_NAME, response.getLastName());
        assertEquals(EMAIL, response.getEmail());

    }

    @Test
    void findByIDExceptionEmployeeNotFound() {
        when(repository.findById(Mockito.anyInt())).thenThrow(new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND_EXCEPTION_MESSAGE));

        try {
            service.findById(ID);
        } catch (Exception ex) {
            assertEquals(EmployeeNotFoundException.class, ex.getClass());
            assertEquals(EMPLOYEE_NOT_FOUND_EXCEPTION_MESSAGE, ex.getMessage());
        }
    }

    @Test
    void findAll(){
        when(repository.findAll()).thenReturn(List.of(employee));

        List<Employee> response = service.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(Employee.class, response.get(0).getClass());
        assertEquals(ID, response.get(0).getId());
        assertEquals(FIRST_NAME, response.get(0).getFirstName());
        assertEquals(LAST_NAME, response.get(0).getLastName());
        assertEquals(EMAIL, response.get(0).getEmail());
    }

    private void startEmployee() {

        employee = new Employee(ID, FIRST_NAME, LAST_NAME, EMAIL);
        optionalEmployee = Optional.of(new Employee(ID, FIRST_NAME, LAST_NAME, EMAIL));
    }

}
