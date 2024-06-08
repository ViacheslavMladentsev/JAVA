package edu.lieineyes.logic;

import com.beust.jcommander.JCommander;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static edu.lieineyes.chaselogic.ChaseLogic.getNewCoordinate;


public class StartGame {

    private static final String MESSAGE_GIVE_UP = "Слабак! Ты просто сдался!";

    private static final String MESSAGE_LOSS_GAME = "УВЫ, НО ВАС ПОЙМАЛИ!!!!";

    private static final String MESSAGE_WIN_GAME = "КОНГРАТЮЛАТИОН!!!! ВЫ ПОБЕДИЛИ!!!! ВЫ ТАКОЙ МОЛОДЕЦ!!!! =))))";

    private static final String MESSAGE_YOUR_MOVE = "Ваш ход, хорошенько подумайте!!!";

    private static final String MESSAGE_YOU_ARE_BLOCKED = "Вы заблокированы, поэтому пропускаете ход!";

    private static final String MESSAGE_CONFIRM_OPPONENT_MOVE = "Подтвердите ход противника %S или сдавайтесь!!!";
    private static final String SYMBOL_MOVE_RIGHT = "D";
    private static final String SYMBOL_MOVE_LEFT = "A";
    private static final String SYMBOL_MOVE_UP = "W";
    private static final String SYMBOL_MOVE_DOWN = "S";
    private static final String SYMBOL_SURRENDER = "9";
    private static final String SYMBOL_CONFIRMATION_OPPONENT_MOVE = "0";

    private static final String MESSAGE_FOR_INCORRECT_SYMBOL_BY_ALL_MODES = "допустимые символы для продолжения игры:\n" +
            "D - сделать ход вправо\n" +
            "A - сделать ход влево\n" +
            "W - сделать ход наверх\n" +
            "S - сделать ход вниз\n" +
            "9 - сдаться\n";

    private static final String MESSAGE_FOR_INCORRECT_SYMBOL_BY_MODE_DEV = "допустимые символы для продолжения игры в режиме разработчика:\n" +
            "0 - подтвердить следующий ход для противника\n" +
            "9 - сдаться\n";

    private static final String USER_MODE_PRODUCTION = "production";

    private static final String USER_MODE_DEV = "dev";

    private static final String TEMPLATE_FOR_CLEAR_TERMINAL = "\033[H\033[2J";


    private static final String MESSAGE_ENEMY_THINKS_ONE = "Враг думает.";

    private static final String MESSAGE_ENEMY_THINKS_TWO = "Враг думает..";

    private static final String MESSAGE_ENEMY_THINKS_THREE = "Враг думает...";

    private static final int CODE_FOR_FORCED_TERMINATION_PROGRAM = -1;


    private static final int MARKING_EMPTY_CELL = -1;

    private static final int MARKING_PLAYER_CELL = -2;

    private static final int MARKING_WALL_OR_ENEMIES_CELL = -3;


    private static final Properties PROPERTIES = new Properties();

    private static final InputArgs ARGUMENTS = new InputArgs();


    private static PlayingFieldInformation playingFieldInformation;

    private static ArrayList<ArrayList<Integer>> fieldForChaseLogic = null;


    private static final int[] oldPositionEnemies = new int[2];


    public static void run(final String[] args) {
        try {
            JCommander.newBuilder().addObject(ARGUMENTS).build().parse(args);
            ValidationPropertiesFile.validationProperties(ARGUMENTS, PROPERTIES);
            ValidationField.checkedCountEnemies(ARGUMENTS.getSize() * ARGUMENTS.getSize(),
                    ARGUMENTS.getWallsCount() + ARGUMENTS.getEnemiesCount());
            playingFieldInformation = new PlayingFieldInformation(ARGUMENTS, PROPERTIES);
            while (!ValidationField.checkedExistenceMap(playingFieldInformation)) {
                playingFieldInformation = new PlayingFieldInformation(ARGUMENTS, PROPERTIES);
            }
        } catch (FileNotFoundException | RuntimeException e) {
            forcedTerminationProgram(String.valueOf(e));
        }

        playingFieldInformation.printColorField(); // первая отрисовка
        System.out.println(MESSAGE_YOUR_MOVE);
        startGame();
    }


    public static void startGame() {
        Scanner sc = new Scanner(System.in);
        String line;
        createArrayForChaseLogic();
        while (true) {
            line = sc.nextLine();
            clearTerminal();
            switch (line) {
                case SYMBOL_MOVE_RIGHT:
                    moveRight();
                    break;
                case SYMBOL_MOVE_LEFT:
                    moveLeft();
                    break;
                case SYMBOL_MOVE_UP:
                    moveUp();
                    break;
                case SYMBOL_MOVE_DOWN:
                    moveDown();
                    break;
                case SYMBOL_SURRENDER:
                    sc.close();
                    forcedExitGame();
                default:
                    playingFieldInformation.printColorField();
                    System.out.println(MESSAGE_FOR_INCORRECT_SYMBOL_BY_ALL_MODES);
                    break;
            }
        }
    }


    private static void moveRight() {
        if (checkingAroundPlayer()) {
            if (ControlGame.moveRight(playingFieldInformation)) {
                checkedWinGame();
                stepEnemy();
            } else {
                playingFieldInformation.printColorField();
            }
        } else {
            System.out.println(MESSAGE_YOU_ARE_BLOCKED);
            if (ARGUMENTS.getProfile().equals(USER_MODE_PRODUCTION)) {
                printSystemMessageWaitEnemyMove();
                runChaseLogicModeProduction();
            } else if (ARGUMENTS.getProfile().equals(USER_MODE_DEV)) {
                runChaseLogicModeDevelop();
            }
        }
    }

    private static void moveLeft() {
        if (checkingAroundPlayer()) {
            if (ControlGame.moveLeft(playingFieldInformation)) {
                checkedWinGame();
                stepEnemy();
            } else {
                playingFieldInformation.printColorField();
            }
        } else {
            System.out.println(MESSAGE_YOU_ARE_BLOCKED);
            stepEnemy();
        }
    }

    private static void moveUp() {
        if (checkingAroundPlayer()) {
            if (ControlGame.moveUp(playingFieldInformation)) {
                checkedWinGame();
                stepEnemy();
            } else {
                playingFieldInformation.printColorField();
            }
        } else {
            System.out.println(MESSAGE_YOU_ARE_BLOCKED);
            stepEnemy();
        }
    }

    private static void moveDown() {
        if (checkingAroundPlayer()) {
            if (ControlGame.moveDown(playingFieldInformation)) {
                checkedWinGame();
                stepEnemy();
            } else {
                playingFieldInformation.printColorField();
            }
        } else {
            System.out.println(MESSAGE_YOU_ARE_BLOCKED);
            stepEnemy();
        }
    }


    private static boolean checkingAroundPlayer() {
        int startX = playingFieldInformation.getPlayer().getCoordinatePositionEnemies().get(0).get(0);
        int startY = playingFieldInformation.getPlayer().getCoordinatePositionEnemies().get(0).get(1);
        if (startX - 1 >= 0) {
            if (contains(playingFieldInformation.getEmpty().getCoordinatePositionEnemies(), startX - 1, startY) ||
                    contains(playingFieldInformation.getGoal().getCoordinatePositionEnemies(), startX - 1, startY)) {
                return true;
            }
        }
        if (startY - 1 >= 0) {
            if (contains(playingFieldInformation.getEmpty().getCoordinatePositionEnemies(), startX, startY - 1) ||
                    contains(playingFieldInformation.getGoal().getCoordinatePositionEnemies(), startX, startY - 1)) {
                return true;
            }
        }
        if (startX + 1 <= playingFieldInformation.getSizeField()) {
            if (contains(playingFieldInformation.getEmpty().getCoordinatePositionEnemies(), startX + 1, startY) ||
                    contains(playingFieldInformation.getGoal().getCoordinatePositionEnemies(), startX + 1, startY)) {
                return true;
            }
        }
        if (startY + 1 <= playingFieldInformation.getSizeField()) {
            return contains(playingFieldInformation.getEmpty().getCoordinatePositionEnemies(), startX, startY + 1) ||
                    contains(playingFieldInformation.getGoal().getCoordinatePositionEnemies(), startX, startY + 1);
        }
        return false;
    }

    private static void stepEnemy() {
        if (ARGUMENTS.getProfile().equals(USER_MODE_PRODUCTION)) {
            printSystemMessageWaitEnemyMove();
            runChaseLogicModeProduction();
        } else if (ARGUMENTS.getProfile().equals(USER_MODE_DEV)) {
            playingFieldInformation.printColorField();
            runChaseLogicModeDevelop();
        }
    }

    private static void getNewCoordinateEnemies(final int indexEnemy) {
        convertArrayForChaseLogic(playingFieldInformation.getEnemies().getCoordinatePositionEnemies().get(indexEnemy));

        oldPositionEnemies[0] = playingFieldInformation.getEnemies().getCoordinatePositionEnemies().get(indexEnemy).get(0);
        oldPositionEnemies[1] = playingFieldInformation.getEnemies().getCoordinatePositionEnemies().get(indexEnemy).get(1);

        int[] newPositionEnemies = getNewCoordinate(fieldForChaseLogic,
                playingFieldInformation.getEnemies().getCoordinatePositionEnemies().get(indexEnemy),
                playingFieldInformation.getPlayer().getCoordinatePositionEnemies().get(0));

        for (int k = 0; k < playingFieldInformation.getEmpty().getCoordinatePositionEnemies().size(); ++k) {
            if (playingFieldInformation.getEmpty().getCoordinatePositionEnemies().get(k).get(0) == newPositionEnemies[0] &&
                    playingFieldInformation.getEmpty().getCoordinatePositionEnemies().get(k).get(1) == newPositionEnemies[1]) {
                playingFieldInformation.getEmpty().getCoordinatePositionEnemies().get(k).set(0, oldPositionEnemies[0]);
                playingFieldInformation.getEmpty().getCoordinatePositionEnemies().get(k).set(1, oldPositionEnemies[1]);
                break;
            }
        }

        playingFieldInformation.getEnemies().getCoordinatePositionEnemies().get(indexEnemy).set(0, newPositionEnemies[0]);
        playingFieldInformation.getEnemies().getCoordinatePositionEnemies().get(indexEnemy).set(1, newPositionEnemies[1]);
        checkedLossGame();
    }

    private static void runChaseLogicModeProduction() {
        for (int i = 0; i < playingFieldInformation.getEnemies().getCoordinatePositionEnemies().size(); ++i) {
            getNewCoordinateEnemies(i);
        }
        playingFieldInformation.printColorField();
        System.out.println(MESSAGE_YOUR_MOVE);
    }

    private static void runChaseLogicModeDevelop() {

        Scanner sc = new Scanner(System.in);
        String line;
        for (int i = 0; i < playingFieldInformation.getEnemies().getCoordinatePositionEnemies().size(); ) {
            System.out.printf(MESSAGE_CONFIRM_OPPONENT_MOVE, (i + 1));
            line = sc.nextLine();
            clearTerminal();
            switch (line) {
                case SYMBOL_CONFIRMATION_OPPONENT_MOVE:
                    getNewCoordinateEnemies(i);
                    playingFieldInformation.printColorField();
                    ++i;
                    break;
                case SYMBOL_SURRENDER:
                    sc.close();
                    forcedExitGame();
                default:
                    playingFieldInformation.printColorField();
                    System.out.println(MESSAGE_FOR_INCORRECT_SYMBOL_BY_MODE_DEV);
                    break;
            }
        }
        System.out.println(MESSAGE_YOUR_MOVE);
    }


    private static void printSystemMessageWaitEnemyMove() {
        for (int i = 0; i < 3; ++i) {
            playingFieldInformation.printColorField();
            if (i == 0) {
                System.out.println(MESSAGE_ENEMY_THINKS_ONE);
            } else if (i == 1) {
                System.out.println(MESSAGE_ENEMY_THINKS_TWO);
            } else {
                System.out.println(MESSAGE_ENEMY_THINKS_THREE);
            }
            try {
                TimeUnit.MILLISECONDS.sleep(350);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            clearTerminal();
        }
    }


    private static void checkedWinGame() {
        int playerRow = playingFieldInformation.getPlayer().getCoordinatePositionEnemies().get(0).get(0);
        int playerCol = playingFieldInformation.getPlayer().getCoordinatePositionEnemies().get(0).get(1);
        int goalRow = playingFieldInformation.getGoal().getCoordinatePositionEnemies().get(0).get(0);
        int goalCol = playingFieldInformation.getGoal().getCoordinatePositionEnemies().get(0).get(1);
        if (playerRow == goalRow && playerCol == goalCol) {
            playingFieldInformation.printColorField();
            System.out.println(MESSAGE_WIN_GAME);
            System.exit(0);
        }
    }

    private static void checkedLossGame() {
        int playerX = playingFieldInformation.getPlayer().getCoordinatePositionEnemies().get(0).get(0);
        int playerY = playingFieldInformation.getPlayer().getCoordinatePositionEnemies().get(0).get(1);
        for (int i = 0; i < playingFieldInformation.getEnemies().getCoordinatePositionEnemies().size(); ++i) {
            int enemyX = playingFieldInformation.getEnemies().getCoordinatePositionEnemies().get(i).get(0);
            int enemyY = playingFieldInformation.getEnemies().getCoordinatePositionEnemies().get(i).get(1);
            if (playerX == enemyX && playerY == enemyY) {
                playingFieldInformation.getPlayer().getCoordinatePositionEnemies().get(0).set(0, -1);
                playingFieldInformation.getPlayer().getCoordinatePositionEnemies().get(0).set(1, -1);
                playingFieldInformation.printColorField();
                System.out.println(MESSAGE_LOSS_GAME);
                System.exit(0);
            }
        }
    }


    private static void createArrayForChaseLogic() {
        fieldForChaseLogic = new ArrayList<>();
        for (int i = 0; i < playingFieldInformation.getSizeField(); ++i) {
            fieldForChaseLogic.add(new ArrayList<>());
            for (int j = 0; j < playingFieldInformation.getSizeField(); ++j) {
                fieldForChaseLogic.get(i).add(0);
            }
        }
    }

    private static void convertArrayForChaseLogic(final ArrayList<Integer> startPositionEnemies) {
        for (int i = 0; i < playingFieldInformation.getSizeField(); ++i) {
            for (int j = 0; j < playingFieldInformation.getSizeField(); ++j) {
                if (contains(playingFieldInformation.getEmpty().getCoordinatePositionEnemies(), i, j)) {
                    fieldForChaseLogic.get(i).set(j, MARKING_EMPTY_CELL);
                } else if (contains(playingFieldInformation.getPlayer().getCoordinatePositionEnemies(), i, j)) {
                    fieldForChaseLogic.get(i).set(j, MARKING_PLAYER_CELL);
                } else {
                    fieldForChaseLogic.get(i).add(j, MARKING_WALL_OR_ENEMIES_CELL);
                }
            }
        }
        fieldForChaseLogic.get(startPositionEnemies.get(0)).set(startPositionEnemies.get(1), -4);
    }

    private static boolean contains(final ArrayList<ArrayList<Integer>> arrayCoordinate, final int row, final int col) {
        for (ArrayList<Integer> integers : arrayCoordinate) {
            if (integers.get(0) == row && integers.get(1) == col) {
                return true;
            }
        }
        return false;
    }


    private static void clearTerminal() {
        System.out.print(TEMPLATE_FOR_CLEAR_TERMINAL);
        System.out.flush();
    }

    private static void forcedTerminationProgram(final String messageError) {
        System.out.println(messageError);
        System.exit(CODE_FOR_FORCED_TERMINATION_PROGRAM);
    }

    private static void forcedExitGame() {
        System.out.println(MESSAGE_GIVE_UP);
        System.exit(0);
    }

}