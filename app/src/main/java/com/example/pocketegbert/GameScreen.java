package com.example.pocketegbert;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameScreen extends Fragment {

    // ------------TOPHER CODE---------------

    TextView scoreText;
    TextView happyText;
    // ------------TOPHER CODE---------------

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    AnimationDrawable egbertAnim;
    AnimationDrawable gushersAnim;
    ImageView egbert;
    ImageView gushersSprites;
    ImageView gushers;
    ImageView pogohammer;
    ImageView bunny;

    // Length of the various animations in milliseconds
    int annoyedDuration = 500;
    int pogoDuration = 1950;
    int happyJumpDuration = 800;
    int bunnyDuration = 1400;

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

        // Get references to items
        //Setting up database

        // ------------TOPHER CODE---------------
        
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
        // ------------TOPHER CODE---------------

        egbert = view.findViewById(R.id.egbert);
        gushers = view.findViewById(R.id.gushers);
        gushersSprites = view.findViewById(R.id.gushersSprites);
        pogohammer = view.findViewById(R.id.pogohammer);
        bunny = view.findViewById(R.id.bunny);

        egbertAnim = (AnimationDrawable) egbert.getDrawable();
        egbertAnim.start();

        // Click listener for Egbert
        egbert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // update animation

                // PULL FROM DATABASE TO DETERMINE WHICH ANIMATION TO PLAY

                // ------------TOPHER CODE---------------
                gameData game = usersData.usersGameData.get(0);

                //increasing the score by 1 each time
                game.score +=1;
                scoreText.setText(String.valueOf(game.score));
                userDB.gameDataDAO().updateGameData(game);


                if(game.score % 1 == 0){
                    game.happiness-=100;
                    happyText.setText(String.valueOf(game.happiness));
                    userDB.gameDataDAO().updateGameData(game);
                }
                // ------------TOPHER CODE---------------

                if(game.happiness > 0)
                {
                    egbertAnim.stop();

                    // figure it out
                    if(game.happiness < 75 && game.happiness > 50)
                    {
                        egbert.setImageResource(R.drawable.johnlv1anim);
                        egbertAnim = (AnimationDrawable) egbert.getDrawable();
                        egbertAnim.start();
                    }
                    else if(game.happiness < 50 && game.happiness > 25)
                    {
                        egbert.setImageResource(R.drawable.johnlv2anim);
                        egbertAnim = (AnimationDrawable) egbert.getDrawable();
                        egbertAnim.start();
                    }
                    else if(game.happiness < 25 && game.happiness > 0)
                    {
                        egbert.setImageResource(R.drawable.johnlv3anim);
                        egbertAnim = (AnimationDrawable) egbert.getDrawable();
                        egbertAnim.start();
                    }

                    // Returns to idle animation
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            egbert.setImageResource(R.drawable.idleanim);
                            egbertAnim = (AnimationDrawable) egbert.getDrawable();
                            egbertAnim.start();
                        }
                    }, annoyedDuration);
                }
                else
                {
                    egbert.setImageResource(R.drawable.john40);
                    gushersSprites.setImageResource(R.drawable.brainbleedanim);
                    gushersAnim = (AnimationDrawable) gushersSprites.getDrawable();
                    gushersAnim.start();
                }

            }
            // update database points or whatever
        });

        // Click listener for the gushers
        // TODO make it rain gushers
        gushers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameData game = usersData.usersGameData.get(0);

                if(game.happiness > 0)
                {
                    egbertAnim.stop();
                    egbert.setImageResource(R.drawable.johnhappyjumpanim);
                    gushersSprites.setImageResource(R.drawable.gushersanim);
                    egbertAnim = (AnimationDrawable) egbert.getDrawable();
                    gushersAnim = (AnimationDrawable) gushersSprites.getDrawable();
                    egbertAnim.start();
                    gushersAnim.start();
                    // Returns to idle animation
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            egbert.setImageResource(R.drawable.idleanim);
                            egbertAnim = (AnimationDrawable) egbert.getDrawable();
                            egbertAnim.start();
                            gushersAnim.stop();
                            gushersSprites.setImageResource(R.drawable.gushersblank);
                        }
                    }, happyJumpDuration);
                }

            }
        });

        // Click listener for the pogohammer
        pogohammer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameData game = usersData.usersGameData.get(0);

                if(game.happiness > 0)
                {
                    egbertAnim.stop();
                    egbert.setImageResource(R.drawable.pogohammeranim);
                    egbertAnim = (AnimationDrawable) egbert.getDrawable();
                    egbertAnim.start();
                    // Returns to idle animation
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            egbert.setImageResource(R.drawable.idleanim);
                            egbertAnim = (AnimationDrawable) egbert.getDrawable();
                            egbertAnim.start();
                        }
                    }, pogoDuration);
                }
            }
        });

        // Click listener for the bunny
        bunny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameData game = usersData.usersGameData.get(0);

                if(game.happiness > 0)
                {
                    egbertAnim.stop();
                    egbert.setImageResource(R.drawable.bunnyanim);
                    egbertAnim = (AnimationDrawable) egbert.getDrawable();
                    egbertAnim.start();
                    // Returns to idle animation
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            egbert.setImageResource(R.drawable.idleanim);
                            egbertAnim = (AnimationDrawable) egbert.getDrawable();
                            egbertAnim.start();
                        }
                    }, bunnyDuration);
                }
            }
        });
    }

}