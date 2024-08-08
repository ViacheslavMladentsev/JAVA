CXX=g++
CXXFLAGS=-Wall -Werror -Wextra -std=c++11
FLAGS_DYNAMIC_LIB = -fPIC
PATH_INCLUDE=/usr/lib/jvm/java-11-openjdk-amd64/include
PATH_LINUX=/usr/lib/jvm/java-11-openjdk-amd64/include/linux
WASH=rm -rf src/main/resources/lib/calculatorcpp/*.o src/main/resources/lib/nativecpp/*.o


install: unistall libcalculator.so
	./mvnw javafx:jlink
	cp -r target/app $(HOME)/Desktop/SmartCalc_v_3_0
	cp $(HOME)/libcalculator.so $(HOME)/Desktop/SmartCalc_v_3_0/lib/libcalculator.so
	rm -rf $(HOME)/libcalculator.so
	./mvnw clean

run:
	$(HOME)/Desktop/SmartCalc_v_3_0/bin/app

unistall: clean
	rm -rf $(HOME)/Desktop/SmartCalc_v_3_0

tests: libcalculator_test.so
	./mvnw test

libcalculator_test.so: unistall all_o
	mkdir $(HOME)/Desktop/SmartCalc_v_3_0/
	mkdir $(HOME)/Desktop/SmartCalc_v_3_0/lib/
	$(CC) -shared src/main/resources/lib/nativecpp/*.o src/main/resources/lib/calculatorcpp/*.o -o $(HOME)/Desktop/SmartCalc_v_3_0/lib/libcalculator.so

libcalculator.so: clean all_o
	$(CC) -shared src/main/resources/lib/nativecpp/*.o src/main/resources/lib/calculatorcpp/*.o -o $(HOME)/libcalculator.so
	$(WASH)

all_o: com_lieineyes_calculator_model_ModelCalculator.o model_calculator.o modify_input.o reverse_polish_notation.o valid_user_input.o

com_lieineyes_calculator_model_ModelCalculator.o: src/main/resources/lib/nativecpp/com_lieineyes_calculator_model_ModelCalculator.cc
	$(CC) $(FLAGS_DYNAMIC_LIB) -c -I $(PATH_INCLUDE) -I $(PATH_LINUX) $^ -o src/main/resources/lib/nativecpp/com_lieineyes_calculator_model_ModelCalculator.o

model_calculator.o: src/main/resources/lib/calculatorcpp/model_calculator.cc
	$(CC) $(FLAGS_DYNAMIC_LIB) -c $^ -o src/main/resources/lib/calculatorcpp/model_calculator.o

modify_input.o: src/main/resources/lib/calculatorcpp/modify_input.cc
	$(CC) $(FLAGS_DYNAMIC_LIB) -c $^ -o src/main/resources/lib/calculatorcpp/modify_input.o

reverse_polish_notation.o: src/main/resources/lib/calculatorcpp/reverse_polish_notation.cc
	$(CC) $(FLAGS_DYNAMIC_LIB) -c $^ -o src/main/resources/lib/calculatorcpp/reverse_polish_notation.o

valid_user_input.o: src/main/resources/lib/calculatorcpp/valid_user_input.cc
	$(CC) $(FLAGS_DYNAMIC_LIB) -c $^ -o src/main/resources/lib/calculatorcpp/valid_user_input.o

clean:
	./mvnw clean
	$(WASH)

#calculator.h: src/main/java/com/lieineyes/calculator/model/ModelCalculator.java
#	javac -h src/main/resources/lib/nativecpp /home/lieineyes/school21/in_work/APJ2_SmartCalc_v3.0_Desktop_Java-1/src/SmartCalc_v3/src/main/java/com/lieineyes/calculator/model/ModelCalculator.java
