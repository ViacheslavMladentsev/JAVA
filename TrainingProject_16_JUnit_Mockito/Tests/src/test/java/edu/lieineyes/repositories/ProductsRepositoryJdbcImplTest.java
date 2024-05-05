package edu.lieineyes.repositories;

import edu.lieineyes.models.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;

class ProductsRepositoryJdbcImplTest {

    private static final List<Product> EXPECTED_FIND_ALL_PRODUCTS_EQUALS = Arrays.asList(
            new Product(1L, "milk", 130),
            new Product(2L, "bread", 40),
            new Product(3L, "egg", 165),
            new Product(4L, "meat", 545),
            new Product(5L, "mushrooms", 240));

    private static final List<Product> EXPECTED_FIND_ALL_PRODUCTS_NOT_EQUALS = Arrays.asList(
            new Product(1L, "milk", 130),
            new Product(2L, "bread", 40),
            new Product(3L, "qwe", 165),
            new Product(4L, "meat", 545),
            new Product(5L, "mushrooms", 240));

    private static final List<Product> EXPECTED_FIND_ALL_PRODUCTS_DIFFERENT_LENGTH = Arrays.asList(
            new Product(1L, "milk", 130),
            new Product(2L, "bread", 40));

    private static final Product PRODUCT_BY_ID_EQUAL = new Product(3L, "egg", 165);
    private static final Product PRODUCT_BY_ID_NOT_EQUAL = new Product(3L, "qwe", 165);

    private static final Product PRODUCT_UPDATE = new Product(3L, "AAAAAAAAAAAA", 165);

    private static final Product PRODUCT_SAVE = new Product(6L, "Bear", 114);

    private static final Long PRODUCT_DELETE = 2L;

    private static final List<Product> EXPECTED_ALL_PRODUCTS_AFTER_DELETE = Arrays.asList(
            new Product(1L, "milk", 130),
            new Product(3L, "egg", 165),
            new Product(4L, "meat", 545),
            new Product(5L, "mushrooms", 240));


    private DataSource dataSource;
    private ProductsRepositoryJdbcImpl productsRepositories;

    @BeforeEach
    void init() {
        dataSource = new EmbeddedDatabaseBuilder()
                .setName("HyperSQL")
                .setType(HSQL)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .addScripts("schema.sql", "data.sql")
                .build();
        productsRepositories = new ProductsRepositoryJdbcImpl(dataSource);
    }

    @AfterEach
    void shootDownDB() {
        if (dataSource != null) {
            ((EmbeddedDatabase) dataSource).shutdown();
        }
    }

    @Test
    void findAllEquals() {
        assertEquals(EXPECTED_FIND_ALL_PRODUCTS_EQUALS, productsRepositories.findAll());
    }

    @Test
    void findAllNotEquals() {
        assertNotEquals(EXPECTED_FIND_ALL_PRODUCTS_NOT_EQUALS, productsRepositories.findAll());
    }

    @Test
    void findAllDifferentLength() {
        assertNotEquals(EXPECTED_FIND_ALL_PRODUCTS_DIFFERENT_LENGTH, productsRepositories.findAll());
    }


    @Test
    void findByIdEqual() {
        Optional<Product> tempProduct = productsRepositories.findById(3L);
        tempProduct.ifPresent(product -> assertEquals(PRODUCT_BY_ID_EQUAL, product));
    }

    @Test
    void findByIdNotEqual() {
        Optional<Product> tempProduct = productsRepositories.findById(3L);
        tempProduct.ifPresent(product -> assertNotEquals(PRODUCT_BY_ID_NOT_EQUAL, product));
    }

    @Test
    void findByIdEmpty() {
        assertEquals(Optional.empty(), productsRepositories.findById(7L));
    }


    @Test
    void update() {
        productsRepositories.update(PRODUCT_UPDATE);
        Optional<Product> tempProduct = productsRepositories.findById(3L);
        tempProduct.ifPresent(product -> assertEquals(PRODUCT_UPDATE, product));
    }


    @Test
    void save() {
        productsRepositories.save(PRODUCT_SAVE);
        Optional<Product> tempProduct = productsRepositories.findById(6L);
        tempProduct.ifPresent(product -> assertEquals(PRODUCT_SAVE, product));
    }


    @Test
    void delete() {
        productsRepositories.delete(PRODUCT_DELETE);
        assertEquals(EXPECTED_ALL_PRODUCTS_AFTER_DELETE, productsRepositories.findAll());
    }

}
