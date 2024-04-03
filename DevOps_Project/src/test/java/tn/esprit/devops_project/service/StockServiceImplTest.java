package tn.esprit.devops_project.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.StockRepository;
import tn.esprit.devops_project.services.StockServiceImpl;

@ContextConfiguration(classes = {StockServiceImpl.class})
@ExtendWith(SpringExtension.class)
class StockServiceImplTest {
    @MockBean
    private StockRepository stockRepository;

    @Autowired
    private StockServiceImpl stockServiceImpl;


    @Test
    void testAddStock() {
        Stock stock = new Stock();
        stock.setIdStock(1L);
        stock.setProducts(new HashSet<>());
        stock.setTitle("Dr");
        when(stockRepository.save(Mockito.<Stock>any())).thenReturn(stock);

        Stock stock2 = new Stock();
        stock2.setIdStock(1L);
        stock2.setProducts(new HashSet<>());
        stock2.setTitle("Dr");
        assertSame(stock, stockServiceImpl.addStock(stock2));
        verify(stockRepository).save(Mockito.<Stock>any());
    }

    @Test
    void testRetrieveStock() {
        Stock stock = new Stock();
        stock.setIdStock(1L);
        stock.setProducts(new HashSet<>());
        stock.setTitle("Dr");
        Optional<Stock> ofResult = Optional.of(stock);
        when(stockRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertSame(stock, stockServiceImpl.retrieveStock(1L));
        verify(stockRepository).findById(Mockito.<Long>any());
    }

    @Test
    void testRetrieveAllStock() {
        ArrayList<Stock> stockList = new ArrayList<>();
        when(stockRepository.findAll()).thenReturn(stockList);
        List<Stock> actualRetrieveAllStockResult = stockServiceImpl.retrieveAllStock();
        assertSame(stockList, actualRetrieveAllStockResult);
        assertTrue(actualRetrieveAllStockResult.isEmpty());
        verify(stockRepository).findAll();
    }

}

