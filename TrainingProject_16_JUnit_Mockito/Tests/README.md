Создайте класс NumberWorker и реализуйте методы
- boolean isPrime(int number);
- public int digitsSum(int number);

Создайте тестовый класс NumberWorkerTest. Методы класса NumberWorkerTest должны проверять корректность работы методов NumberWorker для различных входных данных:
1. Метод isPrimeForPrimes для проверки isPrime с использованием простых чисел (не менее трех)
2. Метод isPrimeForNotPrimes для проверки isPrime с использованием составных чисел (не менее трех)
3. Метод isPrime forincorrectnumbers для проверки isPrime с использованием неправильных чисел (как минимум трех)
4. метод проверки цифровой суммы с использованием набора из как минимум 10 чисел

- Тестовый класс Numberworker должен содержать как минимум 4 метода для тестирования функциональности NumberWorker
- Использование @ParameterizedTest и @ValueSource обязательно для методов 1-3.
- Использование @ParameterizedTest и @CsvFileSource обязательно для метода 4.
- Для метода 4 вам необходимо подготовить файл data.csv, в котором вы должны указать не менее 10 чисел и их правильную сумму цифр. Пример содержимого файла:
  1234, 10


Подключите к проекту зависимости spring-jdbc и hsqldb. Подготовьте файлы schema.sql и data.sql, в которых вы будете описывать структуру таблиц продуктов и тестовые данные (не менее пяти).
Структура таблицы продуктов:
- id
- name
- price

Создайте встроенный тестовый класс EmbeddedDataSourceTest. В этом классе реализуйте метод init(), помеченный аннотацией @beforeEach. В этом классе реализована функциональность для создания источника данных с использованием EmbeddedDataBaseBuilder (класс в библиотеке spring-jdbc). Реализуйте простой тестовый метод для проверки возвращаемого значения метода getConnection(), созданного DataSource (это значение не должно быть null).


Реализуйте ProductsRepository/Products Repository Jdbc в сочетании с интерфейсом/классом с помощью следующих методов:
- List<Product> findAll();
- Optional<Product> findById(Long id);
- void update(Product product);
- void save(Product product);
- void delete(Long id);

Вы должны реализовать тестовый класс ProductsRepositoryJdbcImpl, содержащий методы проверки функциональности репозитория с использованием базы данных в памяти, описанный ранее. В этом классе вы должны заранее подготовить объекты модели, которые будут использоваться для сравнения во всех тестах.

**Примечания**:
1. Каждый тест должен быть изолирован от поведения других тестов. Таким образом, база данных должна находиться в исходном состоянии перед запуском каждого теста.
2. Методы тестирования могут вызывать другие методы, не относящиеся к текущему тесту. Например, при тестировании метода update() может быть вызван метод findById() для проверки достоверности обновления объекта в базе данных.


Реализуйте уровень бизнес-логики, представленный классом UserServiceImpl. Этот класс содержит логику аутентификации пользователя. Он также зависит от интерфейса UsersRepository (не нужно реализовывать этот интерфейс).
Методы UsersRepository interface:
- User findByLogin(String login);
- void update(User user);

Предполагается, что метод findByLogin возвращает объект User, найденный при входе в систему, или генерирует исключение EntityNotFoundException, если пользователь с указанным именем входа не найден. Метод Update генерирует аналогичное исключение при обновлении пользователя, которого нет в базе данных.

Поля класса User:
- Id
- Login
- Password
- Authentication (статус аутентификации true/false)

класс UsersServiceImpl вызывает эти методы внутри функции аутентификации:
- boolean authenticate(String login, String password)

Этот метод:

Проверяет, прошел ли пользователь аутентификацию в системе, используя этот логин. Если аутентификация была выполнена, должно быть вызвано исключение AlreadyAuthenticatedException .
Пользователь с этим логином извлекается из хранилища UsersRepository.
Если полученный пароль пользователя совпадает с указанным паролем, метод устанавливает статус успешной аутентификации для пользователя, обновляет его информацию в базе данных и возвращает true. Если пароли не совпадают, метод возвращает false.

Поскольку ваша цель - проверить корректность работы метода authenticate независимо от компонента UsersRepository , вам следует использовать макет объекта и заглушки методов findByLogin и update (см. Библиотеку Mockito).

Метод аутентификации должен быть проверен в трех случаях:

Правильный логин / пароль (проверьте вызов метода обновления с помощью инструкции verify библиотеки Mockito)
Неверный вход в систему
Неверный пароль

Структура проекта:

- Tests
  - src
    - main
      - java
        - edu.school21
          - exceptions
            - AlreadyAuthenticatedException
          - numbers
            - NumberWorker
          - models
            - Product
            - User
          - services
            - UsersServiceImpl
          - repositories
            - ProductsRepository
            - ProductsRepositoryJdbcImpl
            - UsersRepository
      - resources
    - test
      - java
        - edu.school21
          - services
            - UsersServiceImplTest
          - numbers
            - NumberWorkerTest
          - repositories
            - EmbeddedDataSourceTest
            - ProductsRepositoryJdbcImplTest
      - resources
          -	data.csv
          -	schema.sql
          -	data.sql
  - pom.xml
