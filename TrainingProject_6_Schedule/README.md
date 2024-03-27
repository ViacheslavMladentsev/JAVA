### График

- Пользовательские методы и классы запрещены для всех задач дня, за исключением пользовательских статических функций и процедур в главном файле класса решения.
- Задачи содержат пример работы приложения. Реализованное решение должно быть идентично указанному выходному примеру для текущих входных данных.

| **Разрешено использовать** |                                                        |
|----------------------------|--------------------------------------------------------|
| Input/Output               | System.out, System.err, Scanner(System.in)             |
| Типы                       | Примитивные типы, String, arrays                       |
| Операторы                  | Стандартные операции примитивных типов, условия, циклы |
| Выход из программы         | System::exit                                           |
| Методы                     | String::equals, String::toCharArray, String::length    |

Заказчик открывает школу в сентябре 2020 года. Итак, вам необходимо реализовать версию проекта MVP только на этот месяц.

Вам нужно иметь возможность создавать список учащихся и указывать время и дни недели для занятий. Занятия могут проводиться в любой день недели с 13:00 до 18:00. В один день можно проводить несколько занятий. Однако общее количество занятий в неделю не может превышать 10.

Максимальное количество студентов в расписании также равно 10. Максимальная длина имени студента - 10 (без пробелов).

Вы также должны предоставить возможность записывать посещаемость учащегося. Для этого рядом с именем каждого учащегося должны быть указаны время и дата занятий, а также статус посещаемости (HERE, NOT_HERE). Вам не нужно записывать посещаемость всех занятий за месяц.

Таким образом, жизненный цикл приложения выглядит следующим образом:
1. Создание списка учащихся
2. Заполнение расписания — каждое занятие (время, день недели) вводится в отдельной строке
3. Запись посещаемости
4. Отображение расписания в табличной форме со статусами посещаемости.

Каждый этап работы приложения разделен на "." (период). Гарантируется абсолютная корректность данных, за исключением последовательного упорядочения занятий при заполнении расписания.

Пример работы программы:
```
Artur
Ivan
.
1 TU
5 FR
.
Ivan 1 8 HERE
Ivan 5 11 NOT_HERE
Artur 5 11 HERE
.
           1:00 TU  1|5:00 FR  4|1:00 TU  8|5:00 FR 11|1:00 TU 15|5:00 FR 18|1:00 TU 22|5:00 FR 25|1:00 TU 29|
      Artur          |          |          |         1|          |          |          |          |          |
       Ivan          |          |         1|        -1|          |          |          |          |          |
```