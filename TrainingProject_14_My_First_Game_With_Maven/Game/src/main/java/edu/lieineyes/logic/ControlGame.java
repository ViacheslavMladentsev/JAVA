package edu.lieineyes.logic;

import java.util.ArrayList;

public class ControlGame {

    private static final String MESSAGE_PLAYER_HAS_REACHED_EDGE_FIELD = "В данном направлении ход невозможен. Вы достигли края игрового поля. Попробуйте ещё...";

    private static final String MESSAGE_CELL_IS_OCCUPIED = "В данном направлении ход невозможен. Клетка занята. Попробуйте еще...";


    private ControlGame() {
    }


    public static boolean moveRight(final PlayingFieldInformation playingFieldInformation) {
        return checkingAbilityMakeMove(playingFieldInformation,
                playingFieldInformation.getPlayer().getCoordinatePositionEnemies().get(0).get(0),
                playingFieldInformation.getPlayer().getCoordinatePositionEnemies().get(0).get(1) + 1);
    }

    public static boolean moveLeft(final PlayingFieldInformation playingFieldInformation) {
        return checkingAbilityMakeMove(playingFieldInformation,
                playingFieldInformation.getPlayer().getCoordinatePositionEnemies().get(0).get(0),
                playingFieldInformation.getPlayer().getCoordinatePositionEnemies().get(0).get(1) - 1);
    }

    public static boolean moveUp(final PlayingFieldInformation playingFieldInformation) {
        return checkingAbilityMakeMove(playingFieldInformation,
                playingFieldInformation.getPlayer().getCoordinatePositionEnemies().get(0).get(0) - 1,
                playingFieldInformation.getPlayer().getCoordinatePositionEnemies().get(0).get(1));
    }

    public static boolean moveDown(final PlayingFieldInformation playingFieldInformation) {
        return checkingAbilityMakeMove(playingFieldInformation,
                playingFieldInformation.getPlayer().getCoordinatePositionEnemies().get(0).get(0) + 1,
                playingFieldInformation.getPlayer().getCoordinatePositionEnemies().get(0).get(1));
    }


    private static boolean checkingAbilityMakeMove(final PlayingFieldInformation playingFieldInformation, final int row, final int col) {
        int playerRow = playingFieldInformation.getPlayer().getCoordinatePositionEnemies().get(0).get(0);
        int playerCol = playingFieldInformation.getPlayer().getCoordinatePositionEnemies().get(0).get(1);
        if (row >= playingFieldInformation.getSizeField() || row < 0 || col >= playingFieldInformation.getSizeField() || col < 0) {
            System.out.println(MESSAGE_PLAYER_HAS_REACHED_EDGE_FIELD);
            return false;
        } else {
            if (contains(playingFieldInformation.getEnemies().getCoordinatePositionEnemies(), row, col) ||
                    contains(playingFieldInformation.getWall().getCoordinatePositionEnemies(), row, col)) {
                System.out.println(MESSAGE_CELL_IS_OCCUPIED);
                return false;
            } else {
                for (int i = 0; i < playingFieldInformation.getEmpty().getCoordinatePositionEnemies().size(); ++i) {
                    if (playingFieldInformation.getEmpty().getCoordinatePositionEnemies().get(i).get(0) == row &&
                            playingFieldInformation.getEmpty().getCoordinatePositionEnemies().get(i).get(1) == col) {
                        playingFieldInformation.getEmpty().getCoordinatePositionEnemies().get(i).set(0, playerRow);
                        playingFieldInformation.getEmpty().getCoordinatePositionEnemies().get(i).set(1, playerCol);
                    }
                }
                playingFieldInformation.getPlayer().getCoordinatePositionEnemies().get(0).set(0, row);
                playingFieldInformation.getPlayer().getCoordinatePositionEnemies().get(0).set(1, col);
            }
        }
        return true;
    }


    private static boolean contains(final ArrayList<ArrayList<Integer>> arrayCoordinate, final int row, final int col) {
        for (ArrayList<Integer> integers : arrayCoordinate) {
            if (integers.get(0) == row && integers.get(1) == col) {
                return true;
            }
        }
        return false;
    }

}