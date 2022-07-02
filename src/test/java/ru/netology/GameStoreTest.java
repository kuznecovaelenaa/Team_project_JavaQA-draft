package ru.netology;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import javax.xml.namespace.QName;
import java.nio.charset.StandardCharsets;

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
    /** проверка, что НЕ добавленная игра не отображается
     *
     * тест проходит
     */
    public void shouldAddGame1() {

        store.publishGame("Worms", "Strategy");

        assertFalse(store.containsGame(game));
    }

    @Test
    public void shouldAddGame2() {
        /** 1 и 2 тест в одном условии
         *
         * тест проходит
         */

        Game game = store.publishGame("Нетология Баттл Онлайн", "Аркады");
        Game game1 = store.publishGame("Worms", "Strategy");

        assertFalse(store.containsGame(game1));
        assertTrue(store.containsGame(game));
    }

    @Test
    public void playTime() {
/** проверка добавления времени игры к игроку
 *
 * тест не проходит. getSumPlayedTime должен считать общее время, но не считает - а возвращает 0)
 */

        player.play(game1, 2);
        store.addPlayTime("Ivan", 1);

        int actual = store.getSumPlayedTime();
        int expected = 3;

        assertEquals(actual, expected);

    }

    @Test
    public void playTime1() {

        /** проверка добавления времени игры к игроку
         *
         * тест не проходит. getSumPlayedTime должен считать общее время, но не считает - а возвращает 0)
         */

        store.addPlayTime("Ivan", 1);
        store.addPlayTime("Ivan", 3);

        assertEquals(store.getSumPlayedTime(), 4);

    }

    @Test
    public void bestPlayer() {
        /** проверка поиска игроков по времени игры
         * проверка 1: нет игроков - возврать налл
         *
         * тест проходит
         */

        store.getMostPlayer();

        Spring expected = null;
        String actual = store.getMostPlayer();
        assertEquals(actual, expected);

    }

    @Test
    public void bestPlayer1() {
        /** проверка поиска игроков по времени игры
         * проверка 2: 2 игрока - возврат лучшего
         *
         * тест проходит
         */
        store.addPlayTime("Ivan", 1);
        store.addPlayTime("Anna", 3);

        String expected = "Anna";
        String actual = store.getMostPlayer();

        assertEquals(actual, expected);
    }

    @Test
    public void bestPlayer3() {
        /** проверка поиска игроков по времени игры
         * проверка 2: 2 игрока, уже игравших - возврат лучшего
         *
         * тест  НЕ проходит, так как значения не суммуриются
         */
        player.play(game, 4);
        player2.play(game,3);

        store.addPlayTime("Ivan", 1);
        store.addPlayTime("Anna", 3);

        String expected = "Anna";
        String actual = store.getMostPlayer();

        assertEquals(actual, expected);
    }
    @Test
    public void bestPlayer2() {
        /** проверка поиска игроков по времени игры
         * проверка 2: 3 игрока - возврат лучшего
         *
         * тест не проходит. в методе нет сложения, когда один игрок играет несколько раз
         */

        store.addPlayTime("Ivan", 10);
        store.addPlayTime("Fedor", 10);
        store.addPlayTime("Anna", 10);
        store.addPlayTime("Ivan", 2);

        String actual = store.getMostPlayer();
        String expected = "Ivan";

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

}
