**Recommended types:** Java Collections API, Java IO, Files, Paths, etc.

 Необходимо реализовать приложение, эмулирующее командную строку Unix-подобных систем.

Программа должна принимать в качестве аргумента абсолютный путь к папке, с которой мы начинаем работать, и поддерживать следующие команды:

`mv` WHAT WHERE – позволяет перенести или переименовать файл, если WHERE содержит имя файла без пути.

`ls` – отображает текущее содержимое папки (имена файлов и вложенных папок и их размеры в КБ).

`cd` - изменяет текущий каталог

Давайте предположим, что на диске C:/ (или в корневом каталоге, в зависимости от операционной системы) есть основная папка со следующей иерархией:
- ОСНОВНОЙ
    + папка1
        * image.jpg
        * animation.gif
+ папка2
    * text.txt
    * Main.java

Пример работы программы для ГЛАВНОГО каталога:
```
$ java Main --current-folder=/home/TrainingProject_10_File_Commander/src
/home/TrainingProject_10_File_Commander/src
ls
ChangeDirectory.java 0.66015625 kb
examples 0.12639141082763672 kb
FileManager.java 2.482421875 kb
ListDirectoryContents.java 1.0771484375 kb
Main.java 0.11328125 kb
MoveDirectoryOrFile.java 1.5439453125 kb
cd examples
/home/TrainingProject_10_File_Commander/src/examples
ls
folder1 2.021484375 kb
folder2 127.4033203125 kb
cd folder1
/home/TrainingProject_10_File_Commander/src/examples/folder1
ls
123.txt 0.0 kb
school21.png 2.021484375 kb
mv 123.txt ../folder2
ls
school21.png 2.021484375 kb
cd ../folder2
/home/TrainingProject_10_File_Commander/src/examples/folder2
ls
123.txt
signatures.txt 0.203125 kb
snoopy-santa-claus-bells-usagif.gif 127.2001953125 kb
exit
```
