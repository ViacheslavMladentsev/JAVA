# можно запустить этот файл как скрипт с помощью команды sudo sh ./README.txt
# для запуска скрипта необходимо находится в src/ImagesToChar
# запускать из под суперпользователя

# удаляем директорию target
rm -rf target lib

# создаём директорию, куда будем складывать артефакты, в нашем случае скомпилированные файлы с расширением .class
mkdir target lib

# скачиваем библиотеки jcommander и
curl https://repo1.maven.org/maven2/com/beust/jcommander/1.82/jcommander-1.82.jar -o lib/jcommander-1.82.jar
curl https://repo1.maven.org/maven2/com/diogonunes/JColor/5.5.1/JColor-5.5.1.jar -o lib/JColor-5.5.1.jar

# извлекаем скаченные библиотеки в target/
jar xf lib/jcommander-1.82.jar
jar xf lib/JColor-5.5.1.jar
mv com target
rm -rf META-INF

# копируем директорию resources в целевую директорию target
cp -r src/resources target

# компилируем наши исходники в директорию созданную пунктом выше
javac -classpath lib/jcommander-1.82.jar:lib/JColor-5.5.1.jar -d target src/java/printer/*/*.java

# собираем jar (-cfm используемые флаги -> имя создаваемого архива -> путь до манифеста -> переходим в нужный каталог -> указываем что включить в архив)
jar -cfm ./target/images-to-chars-printer.jar src/manifest.txt -C target .

# запуск программы с указанием пути к jar архиву + необходимые аргументы
java -jar target/images-to-chars-printer.jar --white=RED --black=GREEN
