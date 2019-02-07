package com.example.rholbrook.tickettoride.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.example.rholbrook.tickettoride.R;
import com.example.rholbrook.tickettoride.gamelobby.GameLobbyActivity;
import com.example.rholbrook.tickettoride.login.LoginFragment;
import com.example.rholbrook.tickettoride.register.RegisterFragment;
import com.example.rholbrook.tickettoride.serverconnection.ServerProxy;
import com.example.shared.model.Game;
import com.example.shared.model.Player;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        MainActivityContract.View,
        View.OnClickListener,
        CreateGameDialogFragment.CreateGameDialogInterface,
        JoinGameDialogFragment.JoinGameDialogInterface {

    private MainActivityContract.Presenter mPresenter;

    private Button createGameButton;
    private Button joinGameButton;
    private RecyclerView gameListRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private FrameLayout fragmentContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Authentication authentication = Authentication.getInstance();
        authentication.setUsername("username");
        fragmentContainer = findViewById(R.id.authentication_fragment_container);
        createGameButton = findViewById(R.id.create_game_button);
        createGameButton.setId(MainActivityModel.CREATE_GAME_BUTTON);
        createGameButton.setOnClickListener(this);
        joinGameButton = findViewById(R.id.join_game_button);
        joinGameButton.setId(MainActivityModel.JOIN_GAME_BUTTON);
        joinGameButton.setOnClickListener(this);
        gameListRecyclerView = findViewById(R.id.game_list_recycler_view);
        gameListRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        gameListRecyclerView.setLayoutManager(mLayoutManager);
        joinGameButton.setEnabled(false);

        mPresenter = new MainActivityPresenter(this);
        mPresenter.init();

//        Fragment firstFragment = LoginFragment.newInstance();
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.authentication_fragment_container, firstFragment).commit();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateGameList(List<Game> games) {
        this.gameListRecyclerView.setAdapter(new GameListAdapter(games, this));
    }

    @Override
    public void createGame() {
        CreateGameDialogFragment dialog = new CreateGameDialogFragment();
        dialog.show(getSupportFragmentManager(), "CreateGameDialogFragment");
    }

    @Override
    public void joinGame() {
        JoinGameDialogFragment dialog = new JoinGameDialogFragment();
        ArrayList<CharSequence> availableColors = mPresenter.getAvailableColors();
        Bundle args = new Bundle();
        args.putCharSequenceArrayList("availableColors", availableColors);
        dialog.show(getSupportFragmentManager(), "JoinGameDialogFragment");
    }

    @Override
    public void selectGame(Game game) {
        joinGameButton.setEnabled(true);
        mPresenter.setSelectedGame(game);
    }

    @Override
    public void startGameLobbyFragment() {
        Intent intent = new Intent(this, GameLobbyActivity.class);
        intent.putExtra("gameId", mPresenter.getSelectedGame().getGameId());
        intent.putExtra("hostUsername", mPresenter.getSelectedGame().getHost().getUsername());
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        mPresenter.onClick(view.getId());
    }


    @Override
    public void onJoinPressed(DialogFragment dialog) {
        mPresenter.joinGame();
        dialog.dismiss();
    }

    @Override
    public void onCreatePressed(DialogFragment dialog, String gameName, int maxPlayers, Player.PlayerColor selectedColor) {
        mPresenter.createGame(new Player(Authentication.getInstance().getUsername(), true, selectedColor), maxPlayers, gameName);
    }

    @Override
    public void onCancelPressed(DialogFragment dialog) {
        dialog.dismiss();
    }
}
