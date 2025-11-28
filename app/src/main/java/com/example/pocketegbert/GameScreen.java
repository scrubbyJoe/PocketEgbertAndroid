package com.example.pocketegbert;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameScreen extends Fragment {

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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GameScreen.
     */
    // TODO: Rename and change types and number of parameters
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
                egbertAnim.stop();
                egbert.setImageResource(R.drawable.johnlv1anim);
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
                }, annoyedDuration);
            }
            // update database points or whatever
        });

        // Click listener for the gushers
        // TODO make it rain gushers
        gushers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });

        // Click listener for the pogohammer
        pogohammer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });

        // Click listener for the bunny
        bunny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
        // TODO make custom function for playing the animations because this is ridiculous
    }
}