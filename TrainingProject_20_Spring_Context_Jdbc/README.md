Реализовать модель User:
 - Identifier
 - Email

Объявить интерфейс CrudRepository<T> с объявленным методом:
 - Optional<T> findById(Long id)
 - List<T> findAll()
 - void save(T entity)
 - void update(T entity)
 - void delete(Long id)

Объявить интерфейс UsersRepository, который расширяет CrudRepository:
 - Optional<T> findByEmail(String email)

Создать две реализации UsersRepository:
 - UsersRepositoryJdbcImpl (использует стандартные механизмы операторов)
 - UsersRepositoryJdbcTemplateImpl (основан на JdbcTemaplte).
Оба класса должны принимать объект DataSource в качестве аргумента конструктора.

Иточник данных для подключения к базе должен быть описан в файле db.properties и
использовать тип HikariDataSource.

Механизмы настройки Spring должны быть настроены с помощью аннотаций и
описаны в конфигурационном классе AnnotationConfig помеченный @Configuration.
Внутри этого класса вам нужно описать компоненты для подключения к базе данных DataSource
с помощью аннотации @Bean. Данные о подключении должны быть расположены внутри файла db.properties.
Запрещено использовать context.xml.

Реализуйте интерфейс UsersService и наследованный от него класс UsersServiceImpl
с объявленной в нем зависимостью от UsersRepository. Вставка правильного компонента репозитория
должна быть реализована с использованием аннотации @Autowired (аналогично,
вам необходимо привязать источник данных внутри репозиториев). Коллизии при автоматическом привязывании
должны быть разрешены с помощью аннотации @Qualifier. Компоненты для UsersService и UsersRepository
должны определяться с помощью аннотации @Component.

В UsersServiceImpl реализован метод строковой регистрации (String email),
который регистрирует нового пользователя и сохраняет его данные в базе данных.
Этот метод возвращает временный пароль, назначенный пользователю системой
(эта информация также должна быть сохранена в базе данных).

Выполните интеграционный тест для UsersServiceImp, используя базу данных в памяти (H2 или HSQLDB).
Этот тест должен проверить, был ли возвращен временный пароль в методе регистрации.

Пример структуры программы:
 - Service
    - src
        - main
            - java
                - school21.spring.service
                    - config
                        - ApplicationConfig
                    - models
                        - User
                    - repositories
                        - CrudRepository
                        - UsersRepository
                        - UsersRepositoryJdbcImpl
                        - UsersRepositoryJdbcTemplateImpl
                    - services
                        - UsersService
                        - UsersServiceImpl
                    - application
                        - Main
            - resources
                - db.properties
        - test
            - java
                - school21.spring.service
                    - config
                        - TestApplicationConfig
                    - repositories
                        - EmbeddedDataSourceTest
                        - UserRepositoryJdbcImplTest
                        - UserRepositoryJdbcTemplateImplTest
                    - services
                        - UsersServiceImplTest
    - pom.xml
