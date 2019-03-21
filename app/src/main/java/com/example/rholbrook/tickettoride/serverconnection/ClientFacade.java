package com.example.rholbrook.tickettoride.serverconnection;

import android.util.Log;
import com.example.rholbrook.tickettoride.game.GameActivityModel;
import com.example.rholbrook.tickettoride.gamelobby.GameLobbyActivityModel;
import com.example.rholbrook.tickettoride.main.MainActivityModel;
import com.example.shared.commands.Command;
import com.example.shared.interfaces.IClientInGame;
import com.example.shared.interfaces.IClientNotInGame;
import com.example.shared.model.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ClientFacade implements IClientInGame, IClientNotInGame {
    private final String TAG = "ticket_to_ride";

    private static ClientFacade instance;
    private static Gson gson = new Gson();

    public ClientFacade() {}

    public static synchronized ClientFacade getInstance() {
        if (instance == null) {
            instance = new ClientFacade();
        }
        return instance;
    }

    //    MainActivity
    @Override
    public void updateGameList(ArrayList<Game> games) {
        String jsonValue = gson.toJson(games);
        Type typeName = new TypeToken<ArrayList<Game>>(){}.getType();
        ArrayList<Game> gameList = gson.fromJson(jsonValue, typeName);
        MainActivityModel.getInstance().newGameListRetrieved(gameList);
    }

    @Override
    public void joinGameComplete(String username, String gameId) {
        MainActivityModel.getInstance().joinedGame(gameId);
    }

    //    GameLobby
    @Override
    public void receivedChat(Chat chat, boolean gameStarted, String gameId) {
        Log.d(TAG, "Client Facade: in receivedChat");
        if (gameStarted) {
            GameActivityModel.getInstance().receivedChat(chat);
        } else {
            GameLobbyActivityModel.getInstance().receivedChat(chat);
        }
    }

    @Override
    public void playerJoinedGame(Set<Player> playerList, String gameId) {
        String jsonValue = gson.toJson(playerList);
        Type typeName = new TypeToken<Set<Player>>(){}.getType();
        Set<Player> players = gson.fromJson(jsonValue, typeName);
        GameLobbyActivityModel.getInstance().newPlayerJoined(players);
    }

    @Override
    public void gameStarted(String gameId) {
        GameLobbyActivityModel.getInstance().gameStarted();
    }


    //  History Drawer
    @Override
    public void receivedChatHistory(List<Chat> chatHistory, boolean gameStarted, String username, String gameId) {
        String jsonValue = gson.toJson(chatHistory);
        Type typeName = new TypeToken<List<Chat>>(){}.getType();
        List<Chat> chats = gson.fromJson(jsonValue, typeName);
        GameActivityModel.getInstance().receivedChatHistory(chats);
    }

    @Override
    public void receivedGameHistory(List<GameHistory> gameHistory, String username, String gameId) {
        String jsonValue = gson.toJson(gameHistory);
        Type typeName = new TypeToken<List<GameHistory>>(){}.getType();
        List<GameHistory> history = gson.fromJson(jsonValue, typeName);
        GameActivityModel.getInstance().receivedGameHistory(history);
    }

    //  Game Initialization
    @Override
    public void initializeGame(List<TrainCard> trainCardsFaceUp, List<TrainCard> trainCards, List<Ticket> tickets, List<Player> turnOrder, String username, String gameId) {
        //Fix Face Up Train Card List
        String typeValue = gson.toJson(trainCardsFaceUp);
        Type typeName = new TypeToken<List<TrainCard>>(){}.getType();
        List<TrainCard> trainCardsFaceUpList = gson.fromJson(typeValue, typeName);

        //Fix Train Cards List
        typeValue = gson.toJson(trainCards);
        typeName = new TypeToken<List<TrainCard>>(){}.getType();
        List<TrainCard> trainCardsList = gson.fromJson(typeValue, typeName);

        //Fix ticket List
        typeValue = gson.toJson(tickets);
        typeName = new TypeToken<List<Ticket>>(){}.getType();
        List<Ticket> ticketList = gson.fromJson(typeValue, typeName);

        //Fix Turn Order
        typeValue = gson.toJson(turnOrder);
        typeName = new TypeToken<List<Player>>(){}.getType();
        List<Player> newTurnOrder = gson.fromJson(typeValue, typeName);

        GameActivityModel.getInstance().initializeGame(trainCardsFaceUpList, trainCardsList, ticketList, newTurnOrder);
    }

    @Override
    public void initializeComplete(Player player, String gameId) {
        GameActivityModel.getInstance().updatePlayer(player);
    }

    //  Turn
    @Override
    public void startTurn(List<Route> availableRoutes, String username, String gameId) {
        String typeValue = gson.toJson(availableRoutes);
        Type typeName = new TypeToken<ArrayList<Route>>(){}.getType();
        List<Route> routesList = gson.fromJson(typeValue, typeName);
        GameActivityModel.getInstance().startTurn(routesList);
    }
    @Override
    public void turnStarted(Player player, String gameId) {
        GameActivityModel.getInstance().playerTurnStarted(player);
    }

    @Override
    public void turnEnded(Player player) {
        GameActivityModel.getInstance().playerTurnEnded(player);
    }


    //  Tickets
    @Override
    public void ticketsReceived(List<Ticket> tickets, String username, String gameId) {
        String typeValue = gson.toJson(tickets);
        Type typeName = new TypeToken<List<Ticket>>(){}.getType();
        List<Ticket> ticketsList = gson.fromJson(typeValue, typeName);
        GameActivityModel.getInstance().ticketDataReceived(ticketsList);
    }

    @Override
    public void ticketCompleted(Ticket ticket) {

    }

    //  Routes
    @Override
    public void routeClaimed(Player player, Route route) {
        System.out.println("Route claimed");
        GameActivityModel.getInstance().routeClaimed(player, route);
    }

    //  Cards
    @Override
    public void sendDeckCount(int ticketDeckCount, int trainDeckCount) {
        GameActivityModel.getInstance().setDeckCount(ticketDeckCount, trainDeckCount);
    }

    @Override
    public void updateFaceUpCards(List<TrainCard> newTrainCards) {
        String typeValue = gson.toJson(newTrainCards);
        Type typeName = new TypeToken<List<TrainCard>>(){}.getType();
        List<TrainCard> trainCards = gson.fromJson(typeValue, typeName);
        GameActivityModel.getInstance().updateFaceUpCards(trainCards);
    }

    @Override
    public void receiveFaceDownCard(TrainCard newCard, String username, String gameId) {
        GameActivityModel.getInstance().drewCard(newCard);
    }

    @Override
    public void getClaimableRoutes(List<Route> claimableRoutes, String username, String gameId) {
    }

    @Override
    public void gameEnding(String gameId) {
        GameActivityModel.getInstance().notifyLastTurn();
    }

    @Override
    public void gameEnded(String gameId) {
        GameActivityModel.getInstance().endGame();
    }
}
