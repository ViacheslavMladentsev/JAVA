Необходимо реализовать класс HtmlProcessor (производный от AbstractProcessor), который обрабатывает классы со специальными аннотациями @HtmlForm и @Htmlnput и генерирует код HTML-формы внутри целевой папки /classes после выполнения команды mvn clean compile.
Аннотации @HtmlForm и @htmllinput должны быть доступны только во время компиляции.
Использовать специальные настройки maven-compiler-plugin и зависимость автосервиса от com.google.auto.service.

Обработанные аннотации должены быть использованы в качестве основы для создания файла "*.html".
Пример содержимого:
```
<form action = "/users" method = "post">
	<input type = "text" name = "first_name" placeholder = "Enter First Name">
	<input type = "text" name = "last_name" placeholder = "Enter Last Name">
	<input type = "password" name = "password" placeholder = "Enter Password">
	<input type = "submit" value = "Send">
</form>
```
