package tn.esprit.devops_project.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
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
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.ProductCategory;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.ProductRepository;
import tn.esprit.devops_project.repositories.StockRepository;
import tn.esprit.devops_project.services.ProductServiceImpl;

@ContextConfiguration(classes = {ProductServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ProductServiceImplTest {
    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductServiceImpl productServiceImpl;

    @MockBean
    private StockRepository stockRepository;


    @Test
    void testAddProduct() {
        Stock stock = new Stock();
        stock.setIdStock(1L);
        stock.setProducts(new HashSet<>());
        stock.setTitle("Dr");

        Product product = new Product();
        product.setCategory(ProductCategory.ELECTRONICS);
        product.setIdProduct(1L);
        product.setPrice(10.0f);
        product.setQuantity(1);
        product.setStock(stock);
        product.setTitle("Dr");
        when(productRepository.save(Mockito.<Product>any())).thenReturn(product);

        Stock stock2 = new Stock();
        stock2.setIdStock(1L);
        stock2.setProducts(new HashSet<>());
        stock2.setTitle("Dr");
        Optional<Stock> ofResult = Optional.of(stock2);
        when(stockRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Stock stock3 = new Stock();
        stock3.setIdStock(1L);
        stock3.setProducts(new HashSet<>());
        stock3.setTitle("Dr");

        Product product2 = new Product();
        product2.setCategory(ProductCategory.ELECTRONICS);
        product2.setIdProduct(1L);
        product2.setPrice(10.0f);
        product2.setQuantity(1);
        product2.setStock(stock3);
        product2.setTitle("Dr");
        assertSame(product, productServiceImpl.addProduct(product2, 1L));
        verify(productRepository).save(Mockito.<Product>any());
        verify(stockRepository).findById(Mockito.<Long>any());
        assertSame(stock2, product2.getStock());
    }


    @Test
    void testRetrieveProduct() {
        Stock stock = new Stock();
        stock.setIdStock(1L);
        stock.setProducts(new HashSet<>());
        stock.setTitle("Dr");

        Product product = new Product();
        product.setCategory(ProductCategory.ELECTRONICS);
        product.setIdProduct(1L);
        product.setPrice(10.0f);
        product.setQuantity(1);
        product.setStock(stock);
        product.setTitle("Dr");
        Optional<Product> ofResult = Optional.of(product);
        when(productRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertSame(product, productServiceImpl.retrieveProduct(1L));
        verify(productRepository).findById(Mockito.<Long>any());
    }

    @Test
    void testRetreiveAllProduct() {
        ArrayList<Product> productList = new ArrayList<>();
        when(productRepository.findAll()).thenReturn(productList);
        List<Product> actualRetreiveAllProductResult = productServiceImpl.retreiveAllProduct();
        assertSame(productList, actualRetreiveAllProductResult);
        assertTrue(actualRetreiveAllProductResult.isEmpty());
        verify(productRepository).findAll();
    }

    @Test
    void testRetrieveProductByCategory() {
        ArrayList<Product> productList = new ArrayList<>();
        when(productRepository.findByCategory(Mockito.<ProductCategory>any())).thenReturn(productList);
        List<Product> actualRetrieveProductByCategoryResult = productServiceImpl
                .retrieveProductByCategory(ProductCategory.ELECTRONICS);
        assertSame(productList, actualRetrieveProductByCategoryResult);
        assertTrue(actualRetrieveProductByCategoryResult.isEmpty());
        verify(productRepository).findByCategory(Mockito.<ProductCategory>any());
    }

    @Test
    void testDeleteProduct() {
        doNothing().when(productRepository).deleteById(Mockito.<Long>any());
        productServiceImpl.deleteProduct(1L);
        verify(productRepository).deleteById(Mockito.<Long>any());
    }

    @Test
    void testRetreiveProductStock() {
        ArrayList<Product> productList = new ArrayList<>();
        when(productRepository.findByStockIdStock(Mockito.<Long>any())).thenReturn(productList);
        List<Product> actualRetreiveProductStockResult = productServiceImpl.retreiveProductStock(1L);
        assertSame(productList, actualRetreiveProductStockResult);
        assertTrue(actualRetreiveProductStockResult.isEmpty());
        verify(productRepository).findByStockIdStock(Mockito.<Long>any());
    }


}

