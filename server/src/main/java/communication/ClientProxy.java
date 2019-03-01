package communication;

import com.example.shared.commands.Command;
import com.example.shared.interfaces.IClientInGame;
import com.example.shared.interfaces.IClientNotInGame;
import com.example.shared.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClientProxy implements IClientInGame, IClientNotInGame {
    private String gameId;
    private SocketServer ss;

    public ClientProxy(){
        gameId = null;
        ss = SocketServer.getInstance();
    }

    public ClientProxy(String gameId){
        this.gameId = gameId;
        ss = SocketServer.getInstance();
    }

    @Override
    public void updateGameList(ArrayList<Game> games) {
        String methodName = "updateGameList";
        String[] typeNames = {ArrayList.class.getName()};
        Object[] inputVals = {games};

        ss.broadcastToManagement(new Command(methodName, typeNames, inputVals));
    }

    /**
     * Send the command for just one user to update their game list
     * @param games List of games
     * @param username User to send cmd to
     */
    public void updateGameList(ArrayList<Game> games, String username){
        String methodName = "updateGameList";
        String[] typeNames = {ArrayList.class.getName()};
        Object[] inputVals = {games};

        ss.sendToUser(new Command(methodName, typeNames, inputVals), username);
    }

    @Override
    public void playerJoinedGame(Set<Player> playerList, String gameId) {
        String methodName = "playerJoinedGame";
        String[] typeNames = {Set.class.getName(), String.class.getName()};
        Object[] inputVals = {playerList, gameId};

        ss.broadcastToGame(new Command(methodName, typeNames, inputVals), gameId);
    }

    @Override
    public void joinGameComplete(String username, String gameId) {
        String methodName = "joinGameComplete";
        String[] typeNames = {String.class.getName(), String.class.getName()};
        Object[] inputVals = {username, gameId};

        ss.sendToUser(new Command(methodName, typeNames, inputVals), username);
    }

    @Override
    public void gameStarted(String gameId) {
        String methodName = "gameStarted";
        String[] typeNames = {String.class.getName()};
        Object[] inputVals = {gameId};

        ss.broadcastToGame(new Command(methodName, typeNames, inputVals), gameId);
    }

    @Override
    public void receivedChat(Chat chat, boolean gameStarted, String gameId) {
        String methodName = "receivedChat";
        String[] typeNames = {Chat.class.getName(), boolean.class.getName(), String.class.getName()};
        Object[] inputVals = {chat, gameStarted, gameId};

        ss.broadcastToGame(new Command(methodName, typeNames, inputVals), gameId);
    }

    @Override
    public void receivedChatHistory(List<Chat> chatHistory, boolean gameStarted, String username) {
        String methodName = "receivedChatHistory";
        String[] typeNames = {List.class.getName(), boolean.class.getName(), String.class.getName()};
        Object[] inputVals = {chatHistory, gameStarted, username};

        ss.sendToUser(new Command(methodName, typeNames, inputVals), username);
    }

    @Override
    public void receivedHistoryObject(GameHistory history) {

    }

    @Override
    public void receivedGameHistory(List<GameHistory> gameHistory) {

    }

    @Override
    public void initializeGame(List<TrainCard> trainCards, List<Ticket> tickets, List<Player> turnOrder, String username) {

    }

    @Override
    public void ticketsReceived(List<Ticket> tickets) {

    }

    @Override
    public void startTurn(List<Route> availableRoutes) {

    }

    @Override
    public void ticketCompleted(Ticket ticket) {

    }

    @Override
    public void routeClaimed(Player player, Route route) {

    }

    @Override
    public void cardDrawn(List<TrainCard> faceUpCards) {

    }

    @Override
    public void turnEnded(Player player) {

    }
}
