package edu.school21.tanks.server;

import edu.school21.tanks.models.GameResult;
import edu.school21.tanks.repositories.GameResultRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component("server")
@DependsOn("gameResultRepository")
public class Server {
    private ServerSocket serverSocket;
    private final AtomicInteger shotsHit;
    private final AtomicInteger shotsShot;
    private final AtomicInteger playersConnected;
    private final List<PlayerConnection> playerList;
    private final GameResultRepository gameResultRepository;
    public Server(@Qualifier("gameResultRepository")GameResultRepository gameResultRepository) {
        this.gameResultRepository = gameResultRepository;
        playerList = new LinkedList<>();
        playersConnected = new AtomicInteger();
        shotsShot = new AtomicInteger();
        shotsHit = new AtomicInteger();
    }
    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        playersConnected.set(0);
        shotsHit.set(0);
        shotsShot.set(0);
        while(playersConnected.intValue() < 2) {
            try {
                Socket socket = serverSocket.accept();
                PlayerConnection connection = new PlayerConnection(socket, playersConnected);
                playerList.add(connection);
                playersConnected.incrementAndGet();
                notifyPlayers();
            } catch (IOException e) {
                System.out.println("Server shutdown");
                System.exit(1);
            }
        }
        beginGame();
    }
    private void notifyPlayers() {
        for (PlayerConnection connection : playerList) {
            try {
                connection.sendMessageToClient(playersConnected.toString());
            } catch (IOException e) {}
        }
    }

    private void beginGame() {
        playerList.get(0).setSecondPlayer(playerList.get(1));
        playerList.get(1).setSecondPlayer(playerList.get(0));
        Thread firstThread = new Thread(() -> listenToMoves(playerList.get(0)));
        Thread secondThread = new Thread(() -> listenToMoves(playerList.get(1)));
        firstThread.start();
        secondThread.start();
        try {
            firstThread.join();
            secondThread.join();
            gameResultRepository.save(new GameResult(shotsHit.intValue() / 2, shotsShot.intValue()));
        } catch (InterruptedException e) {}

    }

    private void listenToMoves(PlayerConnection player) {
        String msg;
        while(true) {
            msg = player.getMessageFromClient();
            if (msg == null || msg.equals("ds") || msg.equals("null")) {
                for (PlayerConnection connection : playerList) {
                    connection.disconnect();
                    return;
                }
            } else if (msg.equals("hit")) {
                synchronized(shotsHit) {
                    shotsHit.incrementAndGet();
                }
            } else if (msg.equals("over")) {
                try {
                    player.sendMessageToClient(msg);
                } catch (IOException e) {}
                return;
            } else {
                if (msg.equals("shoot")) {
                    synchronized (shotsShot) {
                        shotsShot.incrementAndGet();
                    }
                }
                try {
                    player.getSecondPlayer().sendMessageToClient(msg);
                } catch (IOException e) {
                }
            }
        }
    }

}
