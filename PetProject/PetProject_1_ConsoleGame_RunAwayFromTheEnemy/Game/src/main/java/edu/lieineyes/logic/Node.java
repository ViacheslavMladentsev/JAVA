package edu.lieineyes.logic;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Класс Node хранит в себе полную информацию об объектах одного типа, которые будут размещены на игровом поле.
 * symbol - символ, который будет отображаться в занятой клетке игрового поля.
 * backgroundColor - цвет ячейке, которую будет занимать объект этого типа.
 * coordinatePositionEnemies - массив объектов с координатами игрового поля, где будут размещены объекты одного типа.
 * <p>
 * maxCapacity - максимальное количество объектов (размер выделенной памяти), который будут размещены на игровом поле.
 * size - текущее количество объектов одного типа записанного в этот узел (Node).
 * <p>
 * Класс может выбросить исключение RuntimeException.
 */

public class Node {

    private static final String MESSAGE_ERROR_COLOR_PROPERTIES = "\nОшибка:" +
            "некорректно заданный цвет в файле с настройками.\n";

    private static final String MESSAGE_ERROR_ADD_ELEMENTS_NODE = "\nОшибка:" +
            "добавление координат для нового enemies в существующую Node невозможно.\n" +
            "Нет места для нового enemies, Node заполнена";

    private static final int CODE_FOR_FORCED_TERMINATION_PROGRAM = -1;

    private static final int COUNT_COORDINATE_AXES_FOR_ENEMIES = 2;

    private final int maxCount;

    private int size;

    private final String symbol;

    private Color backgroundColor;

    private ArrayList<ArrayList<Integer>> coordinatePositionEnemies;


    public Node(final int maxCapacity, final String symbol, final String backgroundColor) {
        this.size = 0;
        this.maxCount = maxCapacity;
        this.symbol = String.valueOf(symbol);

        createListCoordinate();
        initializationColorByName(backgroundColor);
    }

    private void createListCoordinate() {
        this.coordinatePositionEnemies = new ArrayList<>(maxCount);
        for (int i = 0; i < maxCount; ++i) {
            this.coordinatePositionEnemies.add(new ArrayList<>(COUNT_COORDINATE_AXES_FOR_ENEMIES));
        }
    }


    public Color getBackgroundColor() {
        return backgroundColor;
    }


    public String getSymbol() {
        return this.symbol;
    }

    public ArrayList<ArrayList<Integer>> getCoordinatePositionEnemies() {
        return this.coordinatePositionEnemies;
    }


    public void addCoordinateForEnemies(final int row, final int col) throws RuntimeException {
        if (this.size == this.maxCount) {
            throw new RuntimeException(MESSAGE_ERROR_ADD_ELEMENTS_NODE);
        }
        this.coordinatePositionEnemies.get(this.size).add(row);
        this.coordinatePositionEnemies.get(this.size).add(col);
        ++this.size;
    }

    private void initializationColorByName(final String nameColor) {
        try {
            Field field = Class.forName("java.awt.Color").getField(nameColor);
            this.backgroundColor = (Color) field.get(null);
        } catch (Exception e) {
            System.out.println(e + MESSAGE_ERROR_COLOR_PROPERTIES);
            System.exit(CODE_FOR_FORCED_TERMINATION_PROGRAM);
        }
    }

}