package com.example.pocketegbert;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameScreen extends Fragment {

    TextView scoreText;
    TextView happyText;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    AnimationDrawable egbertIdle;
    ImageView egbert;

    public GameScreen() {
        // Required empty public constructor
    }

    public static GameScreen newInstance(String param1, String param2) {
        GameScreen fragment = new GameScreen();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_screen, container, false);

    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Setting up database
        userDatabase userDB = Room.databaseBuilder(requireContext().getApplicationContext(), userDatabase.class, "userDatabase")
                .allowMainThreadQueries().fallbackToDestructiveMigration(true).build();

       SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

       userGameData usersData = viewModel.getPlayersGameData();

        //getting the id for the scoreText Text view
        scoreText = getView().findViewById(R.id.scoreText);
        happyText = getView().findViewById(R.id.happyScore);

       if(usersData != null){
           user currentUser = usersData.user;
           gameData game = usersData.usersGameData.get(0);
           scoreText.setText(String.valueOf(game.score));
           happyText.setText(String.valueOf(game.happiness));
       }
       else{
           Toast loginPlease = new Toast(getActivity().getApplicationContext());
           loginPlease.setText("Please go and enter a username.");
           loginPlease.setGravity(Gravity.CENTER,0,0);
           loginPlease.setDuration(Toast.LENGTH_LONG);
           loginPlease.show();
           return;
       }

        egbert = view.findViewById(R.id.egbert);
        egbertIdle = (AnimationDrawable) egbert.getBackground();
        egbertIdle.start();


        egbert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameData game = usersData.usersGameData.get(0);

                //increasing the score by 1 each time
                game.score +=1;
                scoreText.setText(String.valueOf(game.score));
                userDB.gameDataDAO().updateGameData(game);


                if(game.score % 5 == 0){
                    game.happiness-=1;
                    happyText.setText(String.valueOf(game.happiness));
                    userDB.gameDataDAO().updateGameData(game);
                }
            }
        });



    }

}