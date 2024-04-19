package edu.school21.repositories;

import edu.school21.exception.NotSavedSubEntityException;
import edu.school21.models.Product;
import lombok.AllArgsConstructor;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@AllArgsConstructor
public class ProductsRepositoryJdbcImpl implements ProductsRepository {

    private static final String FIND_ALL_PRODUCT_BY_ID = "SELECT * FROM product;";

    private static final String SEARCH_PRODUCT_BY_ID = "SELECT * FROM product WHERE id = ?;";

    private static final String UPDATE_PRODUCT = "UPDATE product SET name = ?, price = ?" +
            "                                     WHERE id = ?;";

    private static final String SAVE_PRODUCT = "INSERT INTO product VALUES (?, ?, ?);";

    private static final String DELETE_PRODUCT = "DELETE FROM product WHERE id = ?;";


    private static final String PRODUCT_ID = "id";
    private static final String PRODUCT_NAME = "name";
    private static final String PRODUCT_PRICE = "price";

    private final DataSource dataSource;


    @Override
    public List<Product> findAll() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_PRODUCT_BY_ID)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                List<Product> arrayProduct = new ArrayList<>();
                while (resultSet.next()) {
                    arrayProduct.add(new Product(resultSet.getLong(PRODUCT_ID),
                            resultSet.getString(PRODUCT_NAME),
                            resultSet.getInt(PRODUCT_PRICE)));
                }
                return arrayProduct;

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return Collections.emptyList();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }


    @Override
    public Optional<Product> findById(Long id) {
        Optional<Product> result = Optional.empty();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SEARCH_PRODUCT_BY_ID)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Product product = new Product(resultSet.getLong(PRODUCT_ID),
                            resultSet.getString(PRODUCT_NAME),
                            resultSet.getInt(PRODUCT_PRICE));

                    result = Optional.of(product);
                }
                return result;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return result;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return result;
        }
    }

    @Override
    public void update(Product product) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PRODUCT)) {

            initializationStatementForUpdate(statement, product);
            statement.execute();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void initializationStatementForUpdate(PreparedStatement statement, Product product) throws SQLException {
        statement.setString(1, product.getName());
        statement.setInt(2, product.getPrice());
        statement.setLong(3, product.getId());
    }


    @Override
    public void save(Product product) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_PRODUCT, Statement.RETURN_GENERATED_KEYS)) {

            initializationStatementForSave(statement, product);
            statement.execute();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    product.setId(resultSet.getLong(PRODUCT_ID));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        } catch (NotSavedSubEntityException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void initializationStatementForSave(PreparedStatement statement, Product product) throws SQLException {
        statement.setString(1, product.getName());
        statement.setInt(2, product.getPrice());
        statement.setLong(3, product.getId());
    }


    @Override
    public void delete(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PRODUCT)) {

            statement.setLong(1, id);
            statement.execute();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
