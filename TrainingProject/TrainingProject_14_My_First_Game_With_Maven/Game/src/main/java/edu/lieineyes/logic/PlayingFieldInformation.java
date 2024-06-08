package edu.lieineyes.logic;

import com.diogonunes.jcolor.Attribute;

import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.BACK_COLOR;
import static com.diogonunes.jcolor.Attribute.BLACK_TEXT;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс PlayingFieldInformation позволяет сгенерировать "игровое поле". Фактически записать координаты по каждому типу
 * объектов, размещаемых на поле и их характеристики (символ для отрисовки, цвет фона и т.д.).
 * <p>
 * Конструктор принимает 2 параметра - это набор аргументов переданных при запуске игры и все настройки из
 * файла .properties. В конструкторе сразу происходят все запуски логики для "генерации" игрового поля.
 * <p>
 * initializationEmptySymbol - инициализируем вспомогательную переменную emptySymbol в зависимости от настроек
 * в файле .properties.
 * <p>
 * initializationNodes - метод делает первичную инициализацию заявленных нод(объектов для размещения на игровом поле).
 * <p>
 * randomUniqueNumberFromRange - метод создаёт случайный набор номеров ячеек от 1 до максимального размера поля и
 * записывает в список positionAllEnemies.
 * <p>
 * fillNodesEnemies - метод забирает из списка positionAllEnemies номера ячеек и раскидывает по разным типам объектом:
 * Индекс 0 - записывает в positionCellPlayer (ячейка положения на карте игрока).
 * Индекс 1 - записывает в positionCellGoal (ячейка положения на карте финиша).
 * Индекс от 2 до 2+количество препятствий записывает в массив positionCellWall (ячейки положения препятствий на карте).
 * Оставшееся записывает в массив positionCeilEnemies (ячейки положения врагов на карте).
 * <p>
 * placementPlayerAndGoalInPlayingField - метод, который проходит по номерам всех ячеек, и в зависимости от их
 * типа добавляет координаты в соответствующие ноды.
 * <p>
 * printColorField - метод для отрисовки карты. В зависимости от типа объекта вызывается printElementFieldWithColor,
 * который забирает нужный цвет клетки и символ для рисования и выводит на печать.
 */

public class PlayingFieldInformation {

    private static final String TEMPLATE_PLAYER_CHAR = "player.char";

    private static final String TEMPLATE_GOAL_CHAR = "goal.char";

    private static final String TEMPLATE_ENEMY_CHAR = "enemy.char";

    private static final String TEMPLATE_WALL_CHAR = "wall.char";

    private static final String TEMPLATE_EMPTY_CHAR = "empty.char";

    private static final String TEMPLATE_PLAYER_COLOR = "player.color";

    private static final String TEMPLATE_GOAL_COLOR = "goal.color";

    private static final String TEMPLATE_ENEMY_COLOR = "enemy.color";

    private static final String TEMPLATE_WALL_COLOR = "wall.color";

    private static final String TEMPLATE_EMPTY_COLOR = "empty.color";

    private static final String DEFAULT_EMPTY_CHAR = " ";

    private static final int MAX_COUNT_PLAYERS = 1;

    private static final int MAX_COUNT_FINISH = 1;

    private static final int START_RANGE_FOR_GENERATE_RANDOM_NUMBER = 1;


    private final InputArgs arguments;

    private final Properties properties;


    private final int countCells;

    private final int sizeField;

    private String emptySymbol;


    private List<Integer> positionAllEnemies;

    private final ArrayList<Integer> positionCellEnemies = new ArrayList<>();
    private final ArrayList<Integer> positionCellWall = new ArrayList<>();

    private int positionCeilPlayer;

    private int positionCeilGoal;


    private Node player;

    private Node goal;

    private Node enemies;

    private Node wall;

    private Node empty;


    public PlayingFieldInformation(final InputArgs arguments, final Properties properties) throws RuntimeException {
        this.arguments = arguments;
        this.properties = properties;
        this.countCells = this.arguments.getSize() * this.arguments.getSize();
        this.sizeField = this.arguments.getSize();

        initializationEmptySymbol();
        initializationNodes();

        randomUniqueNumberFromRange();

        fillNodesEnemies();
        placementPlayerAndGoalInPlayingField();
    }


    public Node getPlayer() {
        return this.player;
    }

    public Node getGoal() {
        return this.goal;
    }

    public Node getEnemies() {
        return this.enemies;
    }

    public Node getWall() {
        return this.wall;
    }

    public Node getEmpty() {
        return this.empty;
    }

    public int getSizeField() {
        return this.sizeField;
    }


    private void initializationEmptySymbol() {
        if (this.properties.getProperty(TEMPLATE_EMPTY_CHAR).isEmpty()) {
            this.emptySymbol = DEFAULT_EMPTY_CHAR;
        } else {
            this.emptySymbol = this.properties.getProperty(TEMPLATE_EMPTY_CHAR);
        }
    }


    private void initializationNodes() {
        this.player = new Node(MAX_COUNT_PLAYERS, properties.getProperty(TEMPLATE_PLAYER_CHAR),
                properties.getProperty(TEMPLATE_PLAYER_COLOR));
        this.goal = new Node(MAX_COUNT_FINISH, properties.getProperty(TEMPLATE_GOAL_CHAR),
                properties.getProperty(TEMPLATE_GOAL_COLOR));
        this.enemies = new Node(arguments.getEnemiesCount(), properties.getProperty(TEMPLATE_ENEMY_CHAR),
                properties.getProperty(TEMPLATE_ENEMY_COLOR));
        this.wall = new Node(arguments.getWallsCount(), properties.getProperty(TEMPLATE_WALL_CHAR),
                properties.getProperty(TEMPLATE_WALL_COLOR));
        this.empty = new Node(this.countCells - this.arguments.getWallsCount() - this.arguments.getEnemiesCount() - 2,
                this.emptySymbol,
                properties.getProperty(TEMPLATE_EMPTY_COLOR));
    }


    private void randomUniqueNumberFromRange() {
        positionAllEnemies = new Random().ints(START_RANGE_FOR_GENERATE_RANDOM_NUMBER, this.countCells)
                .distinct()
                .limit(this.arguments.getWallsCount() + this.arguments.getEnemiesCount() + 2)
                .boxed()
                .collect(Collectors.toList());
    }


    private void fillNodesEnemies() {
        for (int i = 0; i < this.positionAllEnemies.size(); ++i) {
            if (i == 0) {
                this.positionCeilPlayer = this.positionAllEnemies.get(i);
            } else if (i == 1) {
                this.positionCeilGoal = this.positionAllEnemies.get(i);
            } else if (i <= 1 + arguments.getWallsCount()) {
                this.positionCellWall.add(this.positionAllEnemies.get(i));
            } else if (i > arguments.getWallsCount()) {
                this.positionCellEnemies.add(this.positionAllEnemies.get(i));
            }
        }
        Collections.sort(this.positionCellWall);
        Collections.sort(this.positionCellEnemies);
    }

    private void placementPlayerAndGoalInPlayingField() {
        int countCell = 1;
        for (int i = 0; i < this.sizeField; ++i) {
            for (int j = 0; j < this.sizeField; ++j) {
                if (this.positionCeilPlayer == countCell) {
                    this.player.addCoordinateForEnemies(i, j);
                } else if (this.positionCeilGoal == countCell) {
                    this.goal.addCoordinateForEnemies(i, j);
                } else if (this.positionCellWall.contains(countCell)) {
                    this.wall.addCoordinateForEnemies(i, j);
                } else if (this.positionCellEnemies.contains(countCell)) {
                    this.enemies.addCoordinateForEnemies(i, j);
                } else {
                    this.empty.addCoordinateForEnemies(i, j);
                }
                ++countCell;
            }
        }
    }


    private boolean contains(final ArrayList<ArrayList<Integer>> arrayCoordinate, final int row, final int col) {
        for (ArrayList<Integer> integers : arrayCoordinate) {
            if (integers.get(0) == row && integers.get(1) == col) {
                return true;
            }
        }
        return false;
    }


    public void printColorField() {
        for (int i = 0; i < this.arguments.getSize(); ++i) {
            for (int j = 0; j < this.arguments.getSize(); ++j) {
                if (contains(this.player.getCoordinatePositionEnemies(), i, j)) {
                    printElementFieldWithColor(this.player);
                } else if (contains(this.goal.getCoordinatePositionEnemies(), i, j)) {
                    printElementFieldWithColor(this.goal);
                } else if (contains(this.wall.getCoordinatePositionEnemies(), i, j)) {
                    printElementFieldWithColor(this.wall);
                } else if (contains(this.enemies.getCoordinatePositionEnemies(), i, j)) {
                    printElementFieldWithColor(this.enemies);
                } else {
                    printElementFieldWithColor(this.empty);
                }
            }
            System.out.println();
        }
    }

    private void printElementFieldWithColor(final Node element) {
        Attribute bkgColor = BACK_COLOR(element.getBackgroundColor().getRed(),
                element.getBackgroundColor().getGreen(),
                element.getBackgroundColor().getBlue());
        System.out.print(colorize(element.getSymbol(), BLACK_TEXT(), bkgColor));
    }

}