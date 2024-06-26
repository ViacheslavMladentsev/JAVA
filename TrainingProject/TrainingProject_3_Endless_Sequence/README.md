### Бесконечная последовательность

- Пользовательские методы и классы запрещены для всех задач дня, за исключением пользовательских статических функций и процедур в главном файле класса решения.
- Задачи содержат пример работы приложения. Реализованное решение должно быть идентично указанному выходному примеру для текущих входных данных.

| **Разрешено использовать** |                                                        |
|----------------------------|--------------------------------------------------------|
| Input/Output               | System.out, System.err, Scanner(System.in)             |
| Типы                       | Примитивные типы                                       |
| Операторы                  | Стандартные операции примитивных типов, условия, циклы |
| Выход из программы         | System::exit                                           |

Нужно реализовать программу, которая будет подсчитывать количество элементов для заданного набора чисел, сумма цифр которых является простым числом.
Для простоты давайте предположим, что эта потенциально бесконечная последовательность запросов все еще ограничена, и последним элементом последовательности является число 999.

Эта задача гарантирует, что входные данные абсолютно корректны.

Пример работы программы:

```
$ java Main
-> 65
-> 123
-> 7563
-> 999
   Count of simple number – 1
```
