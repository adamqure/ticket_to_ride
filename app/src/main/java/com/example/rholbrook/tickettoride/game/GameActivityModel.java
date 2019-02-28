package com.example.rholbrook.tickettoride.game;

import com.example.rholbrook.tickettoride.main.Authentication;
import com.example.rholbrook.tickettoride.serverconnection.ServerProxy;
import com.example.shared.model.Chat;
import com.example.shared.model.Game;
import com.example.shared.model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Set;

public class GameActivityModel extends Observable {
    private static GameActivityModel instance;
    private DrawerContract.ChatPresenter chatListener;
    private DrawerContract.GameHistoryPresenter historyListener;
    private String gameId;
    private Player opponentOne;
    private Player opponentTwo;
    private Player opponentThree;
    private Player opponentFour;
    private Game game;
    private List<Chat> chatMessages;

    public GameActivityModel() {
        chatMessages = new ArrayList<>();
    }

    public static GameActivityModel getInstance() {
        if (instance == null) {
            instance = new GameActivityModel();
        }
        return instance;
    }

    public void receivedChat(Chat chat) {
        chatMessages.add(chat);
        chatListener.updateChatList(chatMessages);
    }

    public void sendChat(String message) {
        Chat newChat = new Chat(Authentication.getInstance().getUsername(), message);
        ServerProxy.getInstance().sendChat(newChat, gameId);
    }

    public void selectFaceUpCard(int index) {

    }

    public void selectFaceDownCardDeck() {

    }

    public void drawTickets() {

    }

    public void sortPlayers() {
        Set<Player> players = game.getPlayerList();
    }

    public void setChatListener(DrawerContract.ChatPresenter chatListener) {
        this.chatListener = chatListener;
    }

    public void setHistoryListener(DrawerContract.GameHistoryPresenter historyListener) {
        this.historyListener = historyListener;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
