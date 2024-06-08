package edu.lieineyes.logic;

import java.util.ArrayList;

/**
 * Класс ValidationField осуществляет проверку корректности игрового поля. Поле должно быть таким,
 * что бы у игрока была возможность пройти от точки старта, до финиша. Должен быть хотя бы один не заблокированный путь при старте игры.
 * checkedExistenceMap - статический метод для запуска, где инициализируются несколько вспомогательных переменных.
 * Метод возвращает true если карта корректна и false если карта не корректна.
 * copyDifferentTypeArray - метод делает копию основной карты для упрощения построения маршрута.
 * checkedSolution - метод рекурсивно в глубину проверяет возможность прохода игрока от старта до финиша.
 * <p>
 * В дополнении метод checkedCountEnemies проверяет корректность игрового поля на предмет размера,
 * и соотношения размещенных на нём объектов относительно общего размера. Метод может выбросить исключение RuntimeException.
 */

public class ValidationField {

    private static final String MESSAGE_ERROR_COUNT_ENEMIES = "Ошибка: количество объектов для размещения на игровом поле" +
            " относительно общего размера не должно превышать %.0f%%";

    private static final String MESSAGE_ERROR_SIZE_FIELD = "Ошибка: размер игрового поля не может быть" +
            "меньше чем 2х2, и больше чем100х100\n" +
            "параметр --size=<целое положительное число в диапазоне от 2 до 100 включительно>";


    private static final double RATIO_COUNT_ENEMIES_OF_ALL_COUNT_PLAY_FIELD = 0.3;

    private static final double MAX_COUNT_CELL = 10000;

    private static final double MIN_COUNT_CELL = 4;

    private static final int MARKING_EMPTY_CELL = 0;

    private static final int MARKING_OCCUPIED_CELL = 1;

    private static final int MARKING_VISITED_CELL = 2;


    private static int finishRow;

    private static int finishCol;

    private static int[][] maze;


    private ValidationField() {
    }


    public static boolean checkedExistenceMap(final PlayingFieldInformation playingFieldInformation) {
        finishRow = playingFieldInformation.getGoal().getCoordinatePositionEnemies().get(0).get(0);
        finishCol = playingFieldInformation.getGoal().getCoordinatePositionEnemies().get(0).get(1);

        maze = new int[playingFieldInformation.getSizeField()][playingFieldInformation.getSizeField()];

        copyDifferentTypeArray(playingFieldInformation);

        return checkedSolution(playingFieldInformation.getPlayer().getCoordinatePositionEnemies().get(0).get(0),
                playingFieldInformation.getPlayer().getCoordinatePositionEnemies().get(0).get(1));
    }

    private static void copyDifferentTypeArray(final PlayingFieldInformation field) {
        for (int i = 0; i < maze.length; ++i) {
            for (int j = 0; j < maze.length; ++j) {
                if (contains(field.getEmpty().getCoordinatePositionEnemies(), i, j) ||
                        contains(field.getPlayer().getCoordinatePositionEnemies(), i, j) ||
                        contains(field.getGoal().getCoordinatePositionEnemies(), i, j)) {
                    maze[i][j] = MARKING_EMPTY_CELL;

                } else {
                    maze[i][j] = MARKING_OCCUPIED_CELL;

                }
            }
        }
    }

    private static boolean contains(final ArrayList<ArrayList<Integer>> arrayCoordinate, final int x, final int y) {
        for (ArrayList<Integer> integers : arrayCoordinate) {
            if (integers.get(0) == x && integers.get(1) == y) {
                return true;
            }
        }
        return false;
    }


    private static boolean checkedSolution(final int currentRow, final int currentCol) {
        if (currentRow < 0 || currentRow > maze.length - 1 || currentCol < 0 || currentCol > maze.length - 1) {
            return false;
        }
        if (maze[currentRow][currentCol] != MARKING_EMPTY_CELL) {
            return false;
        }
        if (currentRow == finishRow && currentCol == finishCol) {
            return true;
        }

        maze[currentRow][currentCol] = MARKING_VISITED_CELL;

        return checkedSolution(currentRow - 1, currentCol) ||
                checkedSolution(currentRow, currentCol - 1) ||
                checkedSolution(currentRow + 1, currentCol) ||
                checkedSolution(currentRow, currentCol + 1);
    }


    public static void checkedCountEnemies(final int countCellField, final int countEnemies) throws RuntimeException {
        if (countCellField > MAX_COUNT_CELL || countCellField < MIN_COUNT_CELL) {
            throw new RuntimeException(MESSAGE_ERROR_SIZE_FIELD);
        }
        if (countEnemies > countCellField * RATIO_COUNT_ENEMIES_OF_ALL_COUNT_PLAY_FIELD) {
            throw new RuntimeException(String.format(MESSAGE_ERROR_COUNT_ENEMIES, (RATIO_COUNT_ENEMIES_OF_ALL_COUNT_PLAY_FIELD * 100)));
        }
    }

}