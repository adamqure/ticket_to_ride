package com.example.rholbrook.tickettoride.game;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.example.rholbrook.tickettoride.R;
import com.example.rholbrook.tickettoride.main.CreateGameDialogFragment;
import com.example.shared.model.Player;
import com.example.shared.model.Ticket;

import java.util.ArrayList;
import java.util.List;

public class SelectTicketsDialogFragment extends DialogFragment {
    private final int INITIALIZE_GAME_INDICATOR = 0;
    private final int ADDITIONAL_TICKETS_INDICATOR = 1;
    private final int INITIALIZE_MAX_RETURN_COUNT = 1;
    private final int ADDITIONAL_MAX_RETURN_COUNT = 2;
    private List<Ticket> keptCards;
    private List<Ticket> returnedCards;
    private List<Ticket> possibleCards;
    private int selectionTypeIndicator = 0;

    public interface SelectTicketsDialogInterface {
        public void onReturnPressed(DialogFragment dialogFragment, List<Ticket> keptCards, List<Ticket> returnedCards);
    }

    SelectTicketsDialogFragment.SelectTicketsDialogInterface mListener;

    public static SelectTicketsDialogFragment newInstance(List<Ticket> possibleCards, int selectionTypeIndicator) {
        SelectTicketsDialogFragment fragment = new SelectTicketsDialogFragment();
        Bundle params = new Bundle();
        fragment.setArguments(params);
        fragment.setPossibleCards(possibleCards);
        fragment.setTypeIndicator(selectionTypeIndicator);
        return fragment;
    }

    private void setTypeIndicator(int selectionTypeIndicator) {
        this.selectionTypeIndicator = selectionTypeIndicator;
    }

    public void setPossibleCards(List<Ticket> possibleCards) {
        this.possibleCards = possibleCards;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (SelectTicketsDialogInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement Dialog Interface");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_select_tickets, null);
        final ImageView ticketOne = dialogView.findViewById(R.id.ticket_one_image_view);
        final ImageView ticketTwo = dialogView.findViewById(R.id.ticket_two_image_view);
        final ImageView ticketThree = dialogView.findViewById(R.id.ticket_three_image_view);
        final Button returnTicketsButton = dialogView.findViewById(R.id.return_tickets_button);
        returnTicketsButton.setActivated(true);
        returnTicketsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onReturnPressed(SelectTicketsDialogFragment.this, keptCards, returnedCards);
            }
        });

        //To-Do: Set Ticket images

        ticketOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (returnedCards.contains(possibleCards.get(0))) {
                    returnedCards.remove(possibleCards.get(0));
                    keptCards.add(possibleCards.get(0));
                } else {
                    returnedCards.add(possibleCards.get(0));
                    keptCards.remove(possibleCards.get(0));
                }

                switch (selectionTypeIndicator) {
                    case INITIALIZE_GAME_INDICATOR:
                        if (returnedCards.size() > INITIALIZE_MAX_RETURN_COUNT) {
                            returnTicketsButton.setActivated(false);
                        } else {
                            returnTicketsButton.setActivated(true);
                        }
                        break;
                    case ADDITIONAL_TICKETS_INDICATOR:
                        if (returnedCards.size() > ADDITIONAL_MAX_RETURN_COUNT) {
                            returnTicketsButton.setActivated(false);
                        } else {
                            returnTicketsButton.setActivated(true);
                        }
                        break;
                }
            }
        });

        ticketTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (returnedCards.contains(possibleCards.get(1))) {
                    returnedCards.remove(possibleCards.get(1));
                    keptCards.add(possibleCards.get(1));
                } else {
                    returnedCards.add(possibleCards.get(1));
                    keptCards.remove(possibleCards.get(1));
                }

                switch (selectionTypeIndicator) {
                    case INITIALIZE_GAME_INDICATOR:
                        if (returnedCards.size() > INITIALIZE_MAX_RETURN_COUNT) {
                            returnTicketsButton.setActivated(false);
                        } else {
                            returnTicketsButton.setActivated(true);
                        }
                        break;
                    case ADDITIONAL_TICKETS_INDICATOR:
                        if (returnedCards.size() > ADDITIONAL_MAX_RETURN_COUNT) {
                            returnTicketsButton.setActivated(false);
                        } else {
                            returnTicketsButton.setActivated(true);
                        }
                        break;
                }
            }
        });

        ticketThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (returnedCards.contains(possibleCards.get(2))) {
                    returnedCards.remove(possibleCards.get(2));
                    keptCards.add(possibleCards.get(2));
                } else {
                    returnedCards.add(possibleCards.get(2));
                    keptCards.remove(possibleCards.get(2));
                }

                switch (selectionTypeIndicator) {
                    case INITIALIZE_GAME_INDICATOR:
                        if (returnedCards.size() > INITIALIZE_MAX_RETURN_COUNT) {
                            returnTicketsButton.setActivated(false);
                        } else {
                            returnTicketsButton.setActivated(true);
                        }
                        break;
                    case ADDITIONAL_TICKETS_INDICATOR:
                        if (returnedCards.size() > ADDITIONAL_MAX_RETURN_COUNT) {
                            returnTicketsButton.setActivated(false);
                        } else {
                            returnTicketsButton.setActivated(true);
                        }
                        break;
                }
            }
        });



        builder.setView(dialogView);
        return builder.create();
    }
}
