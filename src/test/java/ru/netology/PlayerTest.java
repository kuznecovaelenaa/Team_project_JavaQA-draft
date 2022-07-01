package ru.netology;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PlayerTest {
    GameStore store = new GameStore();
    Game game1 = store.publishGame("Нетология Баттл Онлайн", "Аркады");
    Game game2 = store.publishGame("Крестики", "Головоломки");
    Game game3 = store.publishGame("Марио", "Экшен");
    Game game4 = store.publishGame("Кроссворд", "Головоломки");
    Game game5 = store.publishGame("Энгри Бедс", "Аркады");
    Game game6 = store.publishGame("Стрелялки", "Экшен");
    Game game7 = store.publishGame("Морской бой", "Головоломки");
    Player player1 = new Player("Petya");


    @Test /*игра не добавляется игроку*/
    public void shouldSumGenreIfOneGameAndInstallGame() {
        player1.installGame(game1);
        player1.play(game1, 3);

        int expected = 3;
        int actual = player1.sumGenre(game1.getGenre());
        assertEquals(expected, actual);
    }

    @Test /*игра не установлена у игрока, но время посчиталось, а должно выходить RuntimeException*/
    public void shouldSumGenreIfOneGame2AndNotInstallGame() {
//        player1.installGame(game1);
        player1.play(game1, 3);

        int expected = 3;
        int actual = player1.sumGenre(game1.getGenre());
        assertEquals(expected, actual);
    }

    @Test /*несколько игр не добавляются игроку*/
    public void shouldSumGenreIfManyGameAndInstallGame() {
        player1.installGame(game1);
        player1.installGame(game2);
        player1.installGame(game3);
        player1.installGame(game4);
        player1.installGame(game5);
        player1.installGame(game6);
        player1.installGame(game7);
        player1.play(game1, 3);
        player1.play(game2, 2);
        player1.play(game3, 0);
        player1.play(game4, 3);
        player1.play(game5, 5);
        player1.play(game6, 1);
        player1.play(game7, 2);

        int expected = 8;
        int actual = player1.sumGenre(game1.getGenre());
        assertEquals(expected, actual);
    }

    @Test /*игры не установлена у игрока, но время посчиталось, а должно выходить RuntimeException*/
    public void shouldSumGenreIfManyGame2AndNotInstallGame() {
        player1.play(game1, 3);
        player1.play(game5, 5);

        int expected = 8;
        int actual = player1.sumGenre(game1.getGenre());
        assertEquals(expected, actual);
    }

    @Test /*не суммируется время, если игрок играет в одну игру несколько раз с регистрацией*/
    public void shouldSumGenreIfOneGameManyTimes() {
        player1.installGame(game1);
        player1.play(game1, 3);
        player1.play(game1, 2);

        int expected = 5;
        int actual = player1.sumGenre(game1.getGenre());
        assertEquals(expected, actual);
    }

    @Test /*не суммируется время, если игрок играет в одну игру несколько раз без регистрации*/
    public void shouldSumGenreIfOneGameManyTimesAndNotInstallGame() {
        player1.play(game1, 3);
        player1.play(game1, 2);

        int expected = 5;
        int actual = player1.sumGenre(game1.getGenre());
        assertEquals(expected, actual);
    }

    @Test /*при использовании невалидного значения, должен выходить 0*/
    public void shouldSumGenreIfNotValid() {
        player1.installGame(game1);
        player1.play(game1, -3);

        int expected = 0;
        int actual = player1.sumGenre(game1.getGenre());
        assertEquals(expected, actual);
    }

    @Test /*при использовании невалидного значения, должен выходить 0*/
    public void shouldSumGenreIfNotValidAndNotInstallGame() {
        player1.play(game1, -3);

        int expected = 0;
        int actual = player1.sumGenre(game1.getGenre());
        assertEquals(expected, actual);
    }

    @Test /*если игрок играет 0 часов*/
    public void shouldSumGenreIfNull() {
        player1.installGame(game1);
        player1.play(game1, 0);

        int expected = 0;
        int actual = player1.sumGenre(game1.getGenre());
        assertEquals(expected, actual);
    }

    @Test /*если игрок играет 0 часов без регистрации*/
    public void shouldSumGenreIfNullAndNotInstallGame() {
        player1.play(game1, 0);

        int expected = 0;
        int actual = player1.sumGenre(game1.getGenre());
        assertEquals(expected, actual);
    }

    @Test /*при любых условиях выходит null, а должно выходить название игры*/
    public void shouldGetGameGenreWithMoreTime() {
        player1.installGame(game1);
        player1.installGame(game5);
        player1.play(game1, 3);
        player1.play(game5, 5);

        String expected = "Энгри Бедс";
        String actual = String.valueOf(player1.mostPlayerByGenre("Аркады"));
        assertEquals(expected, actual);
    }

    @Test /*при любых условиях выходит null, а должно выходить название игры*/
    public void shouldGetGameGenreWithMoreTimeAndNotInstallGame() {
        player1.play(game1, 3);
        player1.play(game5, 5);

        String expected = "Энгри Бедс";
        String actual = String.valueOf(player1.mostPlayerByGenre("Аркады"));
        assertEquals(expected, actual);
    }

    @Test /*здесь верно, но при любых условиях выходит null*/
    public void shouldGetGameGenreWithMoreTimeNotValidAndNotInstallGame() {
        player1.play(game1, 3);
        player1.play(game5, 5);

        String expected = "null";
        String actual = String.valueOf(player1.mostPlayerByGenre("Головоломки"));
        assertEquals(expected, actual);
    }


//    @Test /*этот тест был изначально*/
//    public void shouldSumGenreIfOneGame() {
//        GameStore store = new GameStore();
//        Game game = store.publishGame("Нетология Баттл Онлайн", "Аркады");
//
//        Player player = new Player("Petya");
//        player.installGame(game);
//        player.play(game, 3);
//
//        int expected = 3;
//        int actual = player.sumGenre(game.getGenre());
//        assertEquals(expected, actual);
//    }

    // другие ваши тесты
}


