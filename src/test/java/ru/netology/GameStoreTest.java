package ru.netology;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import javax.xml.namespace.QName;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class GameStoreTest {

    GameStore store = new GameStore();
    Player player = new Player("Ivan");
    Player player1 = new Player("Fedor");
    Player player2 = new Player("Anna");

    Game game = store.publishGame("Нетология Баттл Онлайн", "Аркады");
    Game game1 = store.publishGame("MK", "Fight");
    Game game2 = store.publishGame("NFS", "Race");
    Game game3 = store.publishGame("GTA", "Action");

    @Test
/** Проверка добавления игры
 *
 * тест проходит
 */
    public void shouldAddGame() {

        Game game = store.publishGame("Нетология Баттл Онлайн", "Аркады");

        assertTrue(store.containsGame(game));
    }

    @Test
    /** проверка, что НЕ добавленная игра в каталог не отображается (Игра существует в Сторе, но не добавлена в каталог)
     *
     * тест проходит
     */
    public void shouldAddGame1() {

        Game game123 = new Game("Worms", "Аркада", new GameStore());

        assertFalse(store.containsGame(game123));
    }

    @Test
    public void shouldAddGame2() {
        /** 1 и 2 тест в одном условии
         *
         * тест проходит
         */

        Game game = store.publishGame("Нетология Баттл Онлайн", "Аркады");
        Game game123 = new Game("Worms", "Аркада", new GameStore());

        assertFalse(store.containsGame(game123));
        assertTrue(store.containsGame(game));
    }


    @Test
    public void playTime() {
/** проверка добавления времени игры к игроку, при первоначальном запуске игры
 *
 * тест не проходит. getSumPlayedTime должен считать общее время, но не считает - а возвращает 0)
 */

        player.play(game1, 0);
        store.addPlayTime("Ivan", 1);

        int actual = store.getSumPlayedTime();
        int expected = 1;

        assertEquals(actual, expected);

    }

    @Test
    public void playTime1() {
/** проверка добавления времени игры к 2 игрокам, при первоначальном запуске игры
 *
 * тест не проходит. getSumPlayedTime должен считать общее время, но не считает - а возвращает 0)
 */

        player.play(game2, 0);
        player1.play(game2, 0);
        store.addPlayTime("Ivan", 1);
        store.addPlayTime("Fedor", 1);

        int actual = store.getSumPlayedTime();
        int expected = 2;

        assertEquals(actual, expected);

    }

    @Test
    public void playTime2() {

        /** проверка добавления времени игры к игроку, если игрок уже играл 1 час
         *
         * тест не проходит. getSumPlayedTime должен считать общее время, но не считает - а возвращает 0)
         */

        player2.play(game3, 1);
        store.addPlayTime("Anna", 3);

        assertEquals(store.getSumPlayedTime(), 4);

    }

    @Test
    public void playTime3() {

        /** проверка добавления времени игры к 2 игрокам, если игроки уже играли 1 час
         *
         * тест не проходит. getSumPlayedTime должен считать общее время, но не считает - а возвращает 0)
         */

        player2.play(game3, 1);
        player1.play(game3, 1);
        store.addPlayTime("Anna", 3);
        store.addPlayTime("Fedor", 2);

        assertEquals(store.getSumPlayedTime(), 7);

    }

    @Test
    public void playTime4() {

        /** проверка добавления времени игры к 2 игрокам, если игроки уже играли 1 час + установка игры
         *
         * тест не проходит. getSumPlayedTime должен считать общее время, но не считает - а возвращает 0)
         */

        player2.installGame(game3);
        player1.installGame(game3);
        player2.play(game3, 1);
        player1.play(game3, 1);
        store.addPlayTime("Anna", 3);
        store.addPlayTime("Fedor", 2);

        assertEquals(store.getSumPlayedTime(), 7);

    }

    @Test
    public void playTime5() {

        /** проверка добавления времени игры к игроку, если игроки уже играли -1 час (невалидное значение)
         *
         * тест не проходит. addPlayTime не должен принимать отрицательные значения времени игры)
         */

        player1.installGame(game3);
        player1.play(game3, 0);
        store.addPlayTime("Fedor", -1);

        assertNull(store.getSumPlayedTime(), "null");

    }

    @Test
    public void playTime6() {
/** проверка добавления времени игры к несуществующему игроку
 *
 * тест не проходит. getSumPlayedTime должен выдать исключение, сообщить что Игрок "..." не зарегистрован
 * или просто остановить исполнение теста (ранний выход)
 */

        store.addPlayTime("Garry", 20);

        assertNull(store.getSumPlayedTime(), "null");

    }

    @Test
    public void bestPlayer() {
        /** проверка поиска игроков по времени игры
         * проверка 1: нет игроков - возврать налл
         *
         * тест проходит
         */
        store.getMostPlayer();

        assertNull(store.getMostPlayer(), "null");
    }

    @Test
    public void bestPlayer1() {
        /** проверка поиска игроков по времени игры
         * проверка 1: 1 игрок - возврат игрока
         *
         * тест проходит
         */
        store.addPlayTime("Ivan", 10);
        store.getMostPlayer();

        assertEquals(store.getMostPlayer(), "Ivan");
    }

    @Test
    public void bestPlayer2() {
        /** проверка поиска игроков по времени игры, если играл всего 1 час
         * проверка 1: 1 игрок - возврат игрока
         *
         * тест НЕ проходит, так как в методе установлен знак > 1
         */
        store.addPlayTime("Ivan", 1);
        store.getMostPlayer();

        assertEquals(store.getMostPlayer(), "Ivan");
    }

    @Test
    public void bestPlayer3() {
        /** проверка поиска игроков по времени игры
         * проверка 2: 2 игрока - возврат лучшего
         *
         * тест проходит
         */
        store.addPlayTime("Ivan", 2);
        store.addPlayTime("Anna", 4);

        String expected = "Anna";
        String actual = store.getMostPlayer();

        assertEquals(actual, expected);
    }

    @Test
    public void bestPlayer4() {
        /** проверка поиска игроков по времени игры
         *  3 игрока - возврат лучшего
         *
         * тест проходит
         */
        store.addPlayTime("Ivan", 4);
        store.addPlayTime("Fedor", 10);
        store.addPlayTime("Anna", 9);

        String expected = "Fedor";
        String actual = store.getMostPlayer();

        assertEquals(actual, expected);
    }
    @Test
    public void bestPlayer5() {
        /** проверка поиска игроков по времени игры
         *2 игрока, уже игравших - возврат лучшего
         *
         * тест  НЕ проходит, так как значения не суммуриются. Принимаются только первые значения (игрок.плей)
         */
        player2.installGame(game3);
        player1.installGame(game3);
        player2.play(game3, 2);
        player1.play(game3, 3);
        store.addPlayTime("Anna", 5);
        store.addPlayTime("Fedor", 2);

        String expected = "Anna";
        String actual = store.getMostPlayer();

        assertEquals(actual, expected);
    }

    @Test
    public void bestPlayer6() {
        /** проверка поиска игроков по времени игры
         * 3 игрока - одниковое время
         *
         * тест не проходит ОР - возврат всех трех игроков или ранний выход
         */

        store.addPlayTime("Ivan", 10);
        store.addPlayTime("Fedor", 10);
        store.addPlayTime("Anna", 10);


        String actual = store.getMostPlayer();
        String expected = "Ivan, " + "Fedor, " + "Anna";

        assertEquals(actual, expected);
    }

    @Test
    public void sumPlayedTime() {
        /** проверка суммирования времени игры
         *
         * тест не проходит. в методе нет сложения
         */

        store.addPlayTime("Ivan", 10);
        store.addPlayTime("Fedor", 10);
        store.addPlayTime("Anna", 10);

        int expected = 30;
        int actual = store.getSumPlayedTime();

        assertEquals(actual, expected);
    }

    @Test
    public void sumPlayedTime1() {
        /** проверка суммирования времени игры c невалидными значениями
         *
         * тест не проходит - должен сообщить об отрицательном времени игры - ранний выход с налл
         */

        store.addPlayTime("Ivan", -10);
        store.addPlayTime("Fedor", -20);
        store.addPlayTime("Anna", 10);

       assertNull(store.getSumPlayedTime(), "null");
    }

    @Test
    public void sumPlayedTime2() {
        /** проверка добавления времени игры к 2 игрокам, если игроки уже играли 1 час + установка игры + суммирование
         * игрового времени
         *
         * тест не проходит. getSumPlayedTime должен считать общее время, но не считает - а возвращает 0)
         */

        player2.installGame(game3);
        player1.installGame(game3);
        player2.play(game3, 2);
        player1.play(game3, 4);
        store.addPlayTime("Anna", 3);
        store.addPlayTime("Fedor", 2);

        assertEquals(store.getSumPlayedTime(), 11);
    }

}
