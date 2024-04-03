package tn.esprit.devops_project.service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.devops_project.entities.Invoice;
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.entities.Supplier;
import tn.esprit.devops_project.entities.SupplierCategory;
import tn.esprit.devops_project.repositories.InvoiceDetailRepository;
import tn.esprit.devops_project.repositories.InvoiceRepository;
import tn.esprit.devops_project.repositories.OperatorRepository;
import tn.esprit.devops_project.repositories.SupplierRepository;
import tn.esprit.devops_project.services.InvoiceServiceImpl;

@ContextConfiguration(classes = {InvoiceServiceImpl.class})
@ExtendWith(SpringExtension.class)
public class InvoiceServiceTest {
    @MockBean
    private InvoiceDetailRepository invoiceDetailRepository;

    @MockBean
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceServiceImpl invoiceServiceImpl;

    @MockBean
    private OperatorRepository operatorRepository;

    @MockBean
    private SupplierRepository supplierRepository;

    @Test
    void testRetrieveAllInvoices() {
        ArrayList<Invoice> invoiceList = new ArrayList<>();
        when(invoiceRepository.findAll()).thenReturn(invoiceList);
        List<Invoice> actualRetrieveAllInvoicesResult = invoiceServiceImpl.retrieveAllInvoices();
        assertSame(invoiceList, actualRetrieveAllInvoicesResult);
        assertTrue(actualRetrieveAllInvoicesResult.isEmpty());
        verify(invoiceRepository).findAll();
    }


    @Test
    void testCancelInvoice() {
        Supplier supplier = new Supplier();
        supplier.setCode("Code");
        supplier.setIdSupplier(1L);
        supplier.setInvoices(new HashSet<>());
        supplier.setLabel("Label");
        supplier.setSupplierCategory(SupplierCategory.ORDINAIRE);

        Invoice invoice = new Invoice();
        invoice.setAmountDiscount(10.0f);
        invoice.setAmountInvoice(10.0f);
        invoice.setArchived(true);
        invoice.setDateCreationInvoice(
                Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        invoice.setDateLastModificationInvoice(
                Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        invoice.setIdInvoice(1L);
        invoice.setInvoiceDetails(new HashSet<>());
        invoice.setSupplier(supplier);
        Optional<Invoice> ofResult = Optional.of(invoice);

        Supplier supplier2 = new Supplier();
        supplier2.setCode("Code");
        supplier2.setIdSupplier(1L);
        supplier2.setInvoices(new HashSet<>());
        supplier2.setLabel("Label");
        supplier2.setSupplierCategory(SupplierCategory.ORDINAIRE);

        Invoice invoice2 = new Invoice();
        invoice2.setAmountDiscount(10.0f);
        invoice2.setAmountInvoice(10.0f);
        invoice2.setArchived(true);
        invoice2.setDateCreationInvoice(
                Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        invoice2.setDateLastModificationInvoice(
                Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        invoice2.setIdInvoice(1L);
        invoice2.setInvoiceDetails(new HashSet<>());
        invoice2.setSupplier(supplier2);
        when(invoiceRepository.save(Mockito.<Invoice>any())).thenReturn(invoice2);
        doNothing().when(invoiceRepository).updateInvoice(Mockito.<Long>any());
        when(invoiceRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        invoiceServiceImpl.cancelInvoice(1L);
        verify(invoiceRepository).save(Mockito.<Invoice>any());
        verify(invoiceRepository).findById(Mockito.<Long>any());
        verify(invoiceRepository).updateInvoice(Mockito.<Long>any());
    }



    @Test
    void testRetrieveInvoice() {
        Supplier supplier = new Supplier();
        supplier.setCode("Code");
        supplier.setIdSupplier(1L);
        supplier.setInvoices(new HashSet<>());
        supplier.setLabel("Label");
        supplier.setSupplierCategory(SupplierCategory.ORDINAIRE);

        Invoice invoice = new Invoice();
        invoice.setAmountDiscount(10.0f);
        invoice.setAmountInvoice(10.0f);
        invoice.setArchived(true);
        invoice.setDateCreationInvoice(
                Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        invoice.setDateLastModificationInvoice(
                Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        invoice.setIdInvoice(1L);
        invoice.setInvoiceDetails(new HashSet<>());
        invoice.setSupplier(supplier);
        Optional<Invoice> ofResult = Optional.of(invoice);
        when(invoiceRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertSame(invoice, invoiceServiceImpl.retrieveInvoice(1L));
        verify(invoiceRepository).findById(Mockito.<Long>any());
    }



    @Test
    public void testGetInvoicesBySupplier_NotFound() {
        // Arrange
        Long idSupplier = 2L;
        when(supplierRepository.findById(idSupplier)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(NullPointerException.class, () -> {
            invoiceServiceImpl.getInvoicesBySupplier(idSupplier);
        });

        assertEquals("Supplier not found", exception.getMessage(), "The exception message should match the expected one");
    }

    @Test
    void testAssignOperatorToInvoice() {
        Supplier supplier = new Supplier();
        supplier.setCode("Code");
        supplier.setIdSupplier(1L);
        supplier.setInvoices(new HashSet<>());
        supplier.setLabel("Label");
        supplier.setSupplierCategory(SupplierCategory.ORDINAIRE);

        Invoice invoice = new Invoice();
        invoice.setAmountDiscount(10.0f);
        invoice.setAmountInvoice(10.0f);
        invoice.setArchived(true);
        invoice
                .setDateCreationInvoice(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        invoice.setDateLastModificationInvoice(
                Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        invoice.setIdInvoice(1L);
        invoice.setInvoiceDetails(new HashSet<>());
        invoice.setSupplier(supplier);
        Optional<Invoice> ofResult = Optional.of(invoice);
        when(invoiceRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Operator operator = new Operator();
        operator.setFname("Fname");
        operator.setIdOperateur(1L);
        operator.setInvoices(new HashSet<>());
        operator.setLname("Lname");
        operator.setPassword("iloveyou");
        Optional<Operator> ofResult2 = Optional.of(operator);

        Operator operator2 = new Operator();
        operator2.setFname("Fname");
        operator2.setIdOperateur(1L);
        operator2.setInvoices(new HashSet<>());
        operator2.setLname("Lname");
        operator2.setPassword("iloveyou");
        when(operatorRepository.save(Mockito.<Operator>any())).thenReturn(operator2);
        when(operatorRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        invoiceServiceImpl.assignOperatorToInvoice(1L, 1L);
        verify(invoiceRepository).findById(Mockito.<Long>any());
        verify(operatorRepository).save(Mockito.<Operator>any());
        verify(operatorRepository).findById(Mockito.<Long>any());
    }

    @Test
    void testGetTotalAmountInvoiceBetweenDates() {
        when(invoiceRepository.getTotalAmountInvoiceBetweenDates(Mockito.<Date>any(), Mockito.<Date>any()))
                .thenReturn(10.0f);
        Date startDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        assertEquals(10.0f, invoiceServiceImpl.getTotalAmountInvoiceBetweenDates(startDate,
                Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())));
        verify(invoiceRepository).getTotalAmountInvoiceBetweenDates(Mockito.<Date>any(), Mockito.<Date>any());
    }


}
