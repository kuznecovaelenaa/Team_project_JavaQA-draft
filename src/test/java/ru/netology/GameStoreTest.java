package ru.netology;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class GameStoreTest {

    GameStore store = new GameStore();
    Player player = new Player("Ivan");

    Game game = store.publishGame("Нетология Баттл Онлайн", "Аркады");
    Game game1 = store.publishGame("MK", "Fight");
    Game game2 = store.publishGame("NFS", "Race");
    Game game3 = store.publishGame("GTA", "Action");


    @Test
    public void shouldAddGame() {

        Game game = store.publishGame("Нетология Баттл Онлайн", "Аркады");

        assertTrue(store.containsGame(game));
    }

    @Test
    public void shouldAddGame1() {

        store.publishGame("Worms", "Strategy");

        assertFalse(store.containsGame(game));
    }

    @Test
    public void shouldAddGame2() {

        Game game = store.publishGame("Нетология Баттл Онлайн", "Аркады");
        Game game1 = store.publishGame("Worms", "Strategy");

        assertFalse(store.containsGame(game1));
        assertTrue(store.containsGame(game));
    }

    @Test
    public void playTime() {

        player.play(game1, 2);
        store.addPlayTime("Ivan", 1);

        int actual = X; // заменить переменную.
        int expected = 3;

        assertEquals(actual, expected);


    }


}