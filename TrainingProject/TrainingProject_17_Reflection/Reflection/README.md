Реализуйте проект Maven, который взаимодействует с классами вашего приложения в RunTime.
Создайте как минимум два класса, каждый из которых будет иметь:

- private поля (поддерживаемые типы: String, Integer, Double, Boolean, Long)
- public методы
- defult конструктор
- параметризированный конструктор
- переопределённый toString()
- getters и setters реализовывать не нужно! 
- созданные классы должны быть расположены в отдельном пакете

Программа должна уметь обрабатывать ваши классы:
- выводить все названия классов
- по имени класса, выводить информацию о нём (поля и методы)
- даёт возможность в ходе выполнения программы создавать экземпляр класса
- даёт возможность менять состояние созданного экземпляра класса
- вызывать методы созданного экземпляра класса
- если метод содержит более одного параметра, вам необходимо задать значения для каждого из них
- если метод имеет тип void, строка с информацией о возвращаемом значении не отображается
- в сеансе программы возможно взаимодействие только с одним классом

Пример работы программы:
```
Classes:
	- Car
	- User
---------------------
Enter class name:
Car
---------------------
fields:
	String model
	int speed
methods:
	void printHello
	String powerIncrease
	String toString
---------------------
Let’s create an object.
model:
Buggati
speed:
414
Object created: Car(model=Buggati, speed=414)
---------------------
Enter name of the field for changing:
speed
Enter int value:
417
Object updated: Car(model=Buggati, speed=417)
---------------------
Enter name of the method for call:
powerIncrease
Enter int value:
23
Enter Integer value:
54
Enter String value:
123
Method returned:
512.9112354
```
