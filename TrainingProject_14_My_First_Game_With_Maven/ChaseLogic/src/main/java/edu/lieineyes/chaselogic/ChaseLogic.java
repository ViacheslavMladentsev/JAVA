package edu.lieineyes.chaselogic;

import java.util.ArrayList;


public class ChaseLogic {

    private static final int MARKING_EMPTY_CELL = -1;

    private static final int MARKING_WALL_OR_ENEMIES_CELL = -3;

    private static final int MARKING_START_ENEMY_CELL = -4;

    private static final int STEP_FOR_CORRECTION = 1;

    private static final int[] COORDINATE_NEXT_MOVE = new int[2];

    private static final int[] DEFAULT_START_ENEMY = new int[2];


    private static ArrayList<ArrayList<Integer>> field;

    private static int minValueStepPlayerCell;

    private static int finishRow;

    private static int finishCol;

    static boolean isExit = true;


    private ChaseLogic() {
    }

    public static int[] getNewCoordinate(final ArrayList<ArrayList<Integer>> field,
                                         final ArrayList<Integer> startPosition,
                                         final ArrayList<Integer> finishPosition) {
        ChaseLogic.field = field;
        finishRow = finishPosition.get(0);
        finishCol = finishPosition.get(1);
        minValueStepPlayerCell = field.size() * field.size();
        DEFAULT_START_ENEMY[0] = startPosition.get(0);
        DEFAULT_START_ENEMY[1] = startPosition.get(1);
        COORDINATE_NEXT_MOVE[0] = finishPosition.get(0);
        COORDINATE_NEXT_MOVE[1] = finishPosition.get(1);

        findingShortestPath(startPosition.get(0), startPosition.get(1), 0);
        coordinatesNextMove();

        return isExit ? COORDINATE_NEXT_MOVE : DEFAULT_START_ENEMY;
    }

    private static void findingShortestPath(final int currentRow, final int currentCol, int numberStep) {
        if (currentRow < 0 || currentRow > field.size() - 1 ||
                currentCol < 0 || currentCol > field.size() - 1) {
            return;
        }
        if (field.get(currentRow).get(currentCol) == MARKING_WALL_OR_ENEMIES_CELL) {
            return;
        }
        if (currentRow == finishRow && currentCol == finishCol) {
            if (minValueStepPlayerCell > numberStep) {
                minValueStepPlayerCell = numberStep;
                field.get(currentRow).set(currentCol, numberStep);
            }
            return;
        }
        if (field.get(currentRow).get(currentCol) == MARKING_EMPTY_CELL ||
                field.get(currentRow).get(currentCol) == MARKING_START_ENEMY_CELL ||
                field.get(currentRow).get(currentCol) > numberStep) {
            field.get(currentRow).set(currentCol, numberStep);
        } else {
            return;
        }
        findingShortestPath(currentRow - STEP_FOR_CORRECTION, currentCol, numberStep + STEP_FOR_CORRECTION);
        findingShortestPath(currentRow, currentCol - STEP_FOR_CORRECTION, numberStep + STEP_FOR_CORRECTION);
        findingShortestPath(currentRow + STEP_FOR_CORRECTION, currentCol, numberStep + STEP_FOR_CORRECTION);
        findingShortestPath(currentRow, currentCol + STEP_FOR_CORRECTION, numberStep + STEP_FOR_CORRECTION);
    }

    private static void coordinatesNextMove() {
        int currentValue = field.get(COORDINATE_NEXT_MOVE[0]).get(COORDINATE_NEXT_MOVE[1]);
        isExit = true;
        while (currentValue != 1 && isExit) {
            if (COORDINATE_NEXT_MOVE[1] - STEP_FOR_CORRECTION >= 0) {
                if (field.get(COORDINATE_NEXT_MOVE[0]).get(COORDINATE_NEXT_MOVE[1] - STEP_FOR_CORRECTION) == currentValue - 1) {
                    COORDINATE_NEXT_MOVE[1] = COORDINATE_NEXT_MOVE[1] - STEP_FOR_CORRECTION;
                    currentValue = field.get(COORDINATE_NEXT_MOVE[0]).get(COORDINATE_NEXT_MOVE[1]);
                    continue;
                }
            }
            if (COORDINATE_NEXT_MOVE[0] - STEP_FOR_CORRECTION >= 0) {
                if (field.get(COORDINATE_NEXT_MOVE[0] - STEP_FOR_CORRECTION).get(COORDINATE_NEXT_MOVE[1]) == currentValue - 1) {
                    COORDINATE_NEXT_MOVE[0] = COORDINATE_NEXT_MOVE[0] - STEP_FOR_CORRECTION;
                    currentValue = field.get(COORDINATE_NEXT_MOVE[0]).get(COORDINATE_NEXT_MOVE[1]);
                    continue;
                }
            }
            if (COORDINATE_NEXT_MOVE[1] + STEP_FOR_CORRECTION < field.size()) {
                if (field.get(COORDINATE_NEXT_MOVE[0]).get(COORDINATE_NEXT_MOVE[1] + STEP_FOR_CORRECTION) == currentValue - 1) {
                    COORDINATE_NEXT_MOVE[1] = COORDINATE_NEXT_MOVE[1] + STEP_FOR_CORRECTION;
                    currentValue = field.get(COORDINATE_NEXT_MOVE[0]).get(COORDINATE_NEXT_MOVE[1]);
                    continue;
                }
            }
            if (COORDINATE_NEXT_MOVE[0] + STEP_FOR_CORRECTION < field.size()) {
                if (field.get(COORDINATE_NEXT_MOVE[0] + STEP_FOR_CORRECTION).get(COORDINATE_NEXT_MOVE[1]) == currentValue - 1) {
                    COORDINATE_NEXT_MOVE[0] = COORDINATE_NEXT_MOVE[0] + STEP_FOR_CORRECTION;
                    currentValue = field.get(COORDINATE_NEXT_MOVE[0]).get(COORDINATE_NEXT_MOVE[1]);
                    continue;
                }
            }
            isExit = false;
        }
    }

}