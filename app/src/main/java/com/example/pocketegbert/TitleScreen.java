package com.example.pocketegbert;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TitleScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TitleScreen extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TitleScreen() {
        // Required empty public constructor
    }

    public static TitleScreen newInstance(String param1, String param2) {
        TitleScreen fragment = new TitleScreen();
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
        View v = inflater.inflate(R.layout.fragment_title_screen, container, false);

        //Setting up database
        userDatabase userDB = Room.databaseBuilder(requireContext().getApplicationContext(), userDatabase.class, "userDatabase")
                .allowMainThreadQueries().fallbackToDestructiveMigration(true).build();

        TextInputEditText usernameInput = v.findViewById(R.id.usernameInput);
        Button loginButton = v.findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputedUser = usernameInput.getText().toString();

                List<user> players = userDB.userDAO().getAllUsers();

                Boolean userFound = false;

                //finding player in the database
                if(players != null){
                    //going through all the users in the database to see if the name is there
                    for(user player : players) {
                        //if the name is found transfer all the data
                        if(player.getUsername().equalsIgnoreCase(inputedUser)){
                            Log.i("Player was found", player.getUsername());
                            userFound = true;
                            break;
                        }
                    }
                }
                //user was not found so new user is added to the database
                if(!userFound){
                    Log.i("Play Data", "User was not found making new user");
                    user newUser = new user(inputedUser);
                    userDB.userDAO().addUser(newUser);
                    Log.i("New User", "User was added");
                }
            }
        });

        return v;
    }
}