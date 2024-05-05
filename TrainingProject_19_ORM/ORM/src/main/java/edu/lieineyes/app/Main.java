package edu.lieineyes.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.lieineyes.annotation.OrmManager;
import edu.lieineyes.models.Car;
import edu.lieineyes.models.User;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class Main {

//    private static final String PATH_DATA_SOURCE_CONFIG = "TrainingProject_19_ORM/ORM/src/main/resources/datasource.config";   // для запуска из idea
        private static final String PATH_DATA_SOURCE_CONFIG = "target/classes/datasource.config";                       // для запуска из терминала
    private static final String PACKAGE_NAME = "edu.school21.models";

    private static final int CODE_FORCED_TERMINATION_PROGRAM = -1;

    public static void main(String[] args) throws IllegalAccessException {

        HikariDataSource dataSource = new HikariDataSource(new HikariConfig(PATH_DATA_SOURCE_CONFIG));
        Reflections reflections = new Reflections(PACKAGE_NAME, new SubTypesScanner(false));
        Set<Class> allClasses = new HashSet<>(reflections.getSubTypesOf(Object.class));

        try {
            OrmManager ormManager = new OrmManager(dataSource, allClasses);

            System.out.println("\n================================================================================\n");

            User user1 = new User(null, "Иванов", 180);
            User user2 = new User("Антон", null, 193);
            User user3 = new User("Максим", "Максимов", null);
            System.out.println(user1);
            System.out.println(user2);
            System.out.println(user3);
            System.out.println();

            Car car1 = new Car(null, 270);
            Car car2 = new Car("Nissan", null);
            System.out.println(car1);
            System.out.println(car2);
            System.out.println();

            ormManager.save(user1);
            ormManager.save(user2);
            ormManager.save(user3);
            System.out.println(user1);
            System.out.println(user2);
            System.out.println(user3);
            System.out.println();

            ormManager.save(car1);
            ormManager.save(car2);
            System.out.println(car1);
            System.out.println(car2);
            System.out.println();

            System.out.println("\n================================================================================\n");

            User userById = ormManager.findById(2L, User.class);
            System.out.println(userById);
            Car carById = ormManager.findById(3L, Car.class);
            System.out.println(carById);

            System.out.println("\n================================================================================\n");

            user1.setFirstName("QWERTY");
            user1.setHeight(111);
            System.out.println(user1);
            ormManager.update(user1);

            car2.setSpeed(12);
            System.out.println(car2);
            ormManager.update(car2);

        } catch (IllegalArgumentException | SQLException | InstantiationException e) {
            completionProgram(e.getMessage());
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }


    private static void completionProgram(String e) {
        System.out.println(e);
        System.exit(CODE_FORCED_TERMINATION_PROGRAM);
    }
}