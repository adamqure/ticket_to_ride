package com.example.rholbrook.tickettoride.login;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.rholbrook.tickettoride.R;
import com.example.rholbrook.tickettoride.main.MainActivity;
import com.example.rholbrook.tickettoride.register.RegisterFragment;
import com.example.shared.model.Message;

public class LoginFragment extends Fragment implements LoginFragmentContract.View {
    private LoginFragmentContract.Presenter mPresenter;

    private EditText mUsernameField;
    private EditText mPasswordField;
    private Button mLoginButton;
    private TextView mRegister;

    public interface Listener {
        void successfulLogin();
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle params = new Bundle();

        fragment.setArguments(params);

        return fragment;
    }


    // onCreate: when the app begins, the code below is run
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new LoginFragmentPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mUsernameField = view.findViewById(R.id.login_username_edit_text);
        mPasswordField = view.findViewById(R.id.login_password_edit_text);
        mLoginButton = view.findViewById(R.id.login_button);
        mRegister = view.findViewById(R.id.register_button);
        mLoginButton.setEnabled(false);

        // onTextChanged listeners
        mUsernameField.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mUsernameField.getText().toString().length() == 0) {
                    mLoginButton.setEnabled(false);
                } else {
                    if (mPasswordField.getText().toString().length() == 0) {
                        mLoginButton.setEnabled(true);
                        mLoginButton.setTextColor(Color.BLACK);
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }

        });

        mPasswordField.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mPasswordField.getText().toString().length() == 0) {
                    mLoginButton.setEnabled(false);
                } else {
                    if (mUsernameField.getText().toString().length() == 0) {
                        mLoginButton.setEnabled(true);
                        mLoginButton.setTextColor(Color.BLACK);
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }

        });


        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = mPresenter.login(mUsernameField.getText().toString(), mPasswordField.getText().toString());
                checkStatus(message);
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // bring up Blaine's Fragment
                startRegisterFragment();

            }

        });
        return view;
    }

    private void checkStatus(Message message) {
        if (message.isSuccess()) {
            successfulLogin();
        }
        else {
            showToast(message.getMessage());
        }

    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void startRegisterFragment() {
         Fragment fragment = RegisterFragment.newInstance();
         getFragmentManager().beginTransaction().replace(R.id.authentication_fragment_container, fragment).commit();
    }

    @Override
    public void successfulLogin() {
        // Bring up main Activity
        getActivity().getSupportFragmentManager().beginTransaction().remove(LoginFragment.this).commit();
    }
}