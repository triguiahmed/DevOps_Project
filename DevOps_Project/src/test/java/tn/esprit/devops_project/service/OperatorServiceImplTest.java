package tn.esprit.devops_project.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.repositories.OperatorRepository;
import tn.esprit.devops_project.services.OperatorServiceImpl;

@ContextConfiguration(classes = {OperatorServiceImpl.class})
@ExtendWith(SpringExtension.class)
class OperatorServiceImplTest {
    @MockBean
    private OperatorRepository operatorRepository;

    @Autowired
    private OperatorServiceImpl operatorServiceImpl;

    final static String FAKE_PASSWORD="password" ;
    final static String FAKE_FNAME="fname" ;

    final static String FAKE_LNAME="lname" ;


    @Test
    public void testRetrieveAllOperators() {
        // Arrange
        List<Operator> mockOperators = Arrays.asList(
                new Operator(),
                new Operator()
        );
        when(operatorRepository.findAll()).thenReturn(mockOperators);

        // Act
        List<Operator> result = operatorServiceImpl.retrieveAllOperators();

        // Assert
        assertEquals(mockOperators, result);
        verify(operatorRepository).findAll();
    }

    @Test
    void testAddOperator() {
        Operator operator = new Operator();
        operator.setFname(FAKE_FNAME);
        operator.setIdOperateur(1L);
        operator.setInvoices(new HashSet<>());
        operator.setLname(FAKE_LNAME);
        operator.setPassword(FAKE_PASSWORD);
        when(operatorRepository.save(Mockito.<Operator>any())).thenReturn(operator);

        Operator operator2 = new Operator();
        operator2.setFname(FAKE_FNAME);
        operator2.setIdOperateur(1L);
        operator2.setInvoices(new HashSet<>());
        operator2.setLname(FAKE_LNAME);
        operator2.setPassword(FAKE_PASSWORD);
        assertSame(operator, operatorServiceImpl.addOperator(operator2));
        verify(operatorRepository).save(Mockito.<Operator>any());
    }


    @Test
    void testDeleteOperator() {
        doNothing().when(operatorRepository).deleteById(Mockito.<Long>any());
        operatorServiceImpl.deleteOperator(1L);
        verify(operatorRepository).deleteById(Mockito.<Long>any());
    }

    @Test
    void testUpdateOperator() {
        Operator operator = new Operator();
        operator.setFname(FAKE_FNAME);
        operator.setIdOperateur(1L);
        operator.setInvoices(new HashSet<>());
        operator.setLname(FAKE_LNAME);
        operator.setPassword(FAKE_PASSWORD);
        when(operatorRepository.save(Mockito.<Operator>any())).thenReturn(operator);

        Operator operator2 = new Operator();
        operator2.setFname(FAKE_FNAME);
        operator2.setIdOperateur(1L);
        operator2.setInvoices(new HashSet<>());
        operator2.setLname(FAKE_LNAME);
        operator2.setPassword(FAKE_PASSWORD);
        assertSame(operator, operatorServiceImpl.updateOperator(operator2));
        verify(operatorRepository).save(Mockito.<Operator>any());
    }

    @Test
    void testRetrieveOperator() {
        Operator operator = new Operator();
        operator.setFname(FAKE_FNAME);
        operator.setIdOperateur(1L);
        operator.setInvoices(new HashSet<>());
        operator.setLname(FAKE_LNAME);
        operator.setPassword(FAKE_PASSWORD);
        Optional<Operator> ofResult = Optional.of(operator);
        when(operatorRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertSame(operator, operatorServiceImpl.retrieveOperator(1L));
        verify(operatorRepository).findById(Mockito.<Long>any());
    }

}

