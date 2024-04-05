package tn.esprit.devops_project.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.devops_project.entities.Supplier;
import tn.esprit.devops_project.entities.SupplierCategory;
import tn.esprit.devops_project.repositories.SupplierRepository;
import tn.esprit.devops_project.services.SupplierServiceImpl;

@ContextConfiguration(classes = {SupplierServiceImpl.class})
@ExtendWith(SpringExtension.class)
class SupplierServiceImplTest {
    @MockBean
    private SupplierRepository supplierRepository;

    @Autowired
    private SupplierServiceImpl supplierServiceImpl;

    @Test
    void testRetrieveAllSuppliers() {
        ArrayList<Supplier> supplierList = new ArrayList<>();
        when(supplierRepository.findAll()).thenReturn(supplierList);
        List<Supplier> actualRetrieveAllSuppliersResult = supplierServiceImpl.retrieveAllSuppliers();
        assertSame(supplierList, actualRetrieveAllSuppliersResult);
        assertTrue(actualRetrieveAllSuppliersResult.isEmpty());
        verify(supplierRepository).findAll();
    }


    @Test
    void testAddSupplier() {
        Supplier supplier = new Supplier();
        supplier.setCode("Code");
        supplier.setIdSupplier(1L);
        supplier.setInvoices(new HashSet<>());
        supplier.setLabel("Label");
        supplier.setSupplierCategory(SupplierCategory.ORDINAIRE);
        when(supplierRepository.save(Mockito.<Supplier>any())).thenReturn(supplier);
        Supplier supplier2 = new Supplier();
        supplier2.setCode("Code");
        supplier2.setIdSupplier(1L);
        supplier2.setInvoices(new HashSet<>());
        supplier2.setLabel("Label");
        supplier2.setSupplierCategory(SupplierCategory.ORDINAIRE);
        assertSame(supplier, supplierServiceImpl.addSupplier(supplier2));
        verify(supplierRepository).save(Mockito.<Supplier>any());
    }


    @Test
    void testUpdateSupplier() {
        Supplier supplier = new Supplier();
        supplier.setCode("Code");
        supplier.setIdSupplier(1L);
        supplier.setInvoices(new HashSet<>());
        supplier.setLabel("Label");
        supplier.setSupplierCategory(SupplierCategory.ORDINAIRE);
        when(supplierRepository.save(Mockito.<Supplier>any())).thenReturn(supplier);
        Supplier supplier2 = new Supplier();
        supplier2.setCode("Code");
        supplier2.setIdSupplier(1L);
        supplier2.setInvoices(new HashSet<>());
        supplier2.setLabel("Label");
        supplier2.setSupplierCategory(SupplierCategory.ORDINAIRE);
        assertSame(supplier, supplierServiceImpl.updateSupplier(supplier2));
        verify(supplierRepository).save(Mockito.<Supplier>any());
    }


    @Test
    void testDeleteSupplier() {
        doNothing().when(supplierRepository).deleteById(Mockito.<Long>any());
        supplierServiceImpl.deleteSupplier(1L);
        verify(supplierRepository).deleteById(Mockito.<Long>any());
    }

    @Test
    void testRetrieveSupplier() {
        Supplier supplier = new Supplier();
        supplier.setCode("Code");
        supplier.setIdSupplier(1L);
        supplier.setInvoices(new HashSet<>());
        supplier.setLabel("Label");
        supplier.setSupplierCategory(SupplierCategory.ORDINAIRE);
        Optional<Supplier> ofResult = Optional.of(supplier);
        when(supplierRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertSame(supplier, supplierServiceImpl.retrieveSupplier(1L));
        verify(supplierRepository).findById(Mockito.<Long>any());
    }


}
