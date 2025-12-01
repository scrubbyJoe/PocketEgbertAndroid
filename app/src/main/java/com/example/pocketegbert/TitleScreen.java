package com.example.pocketegbert;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;


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

        //Variables
        TextInputEditText usernameInput = v.findViewById(R.id.usernameInput);
        Button loginButton = v.findViewById(R.id.loginButton);
        Spinner saveSlotSelector = v.findViewById(R.id.saveSlotSpinner);

        //Setting up database
        userDatabase userDB = Room.databaseBuilder(requireContext().getApplicationContext(), userDatabase.class, "userDatabase")
                .allowMainThreadQueries().fallbackToDestructiveMigration(true).build();


        //When the login button is pressed
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //making a view model to share the users data with all the fragments
                SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

                //the name the user inputted
                String inputtedUser = usernameInput.getText().toString();

                //making a new userGameData to get all the game data of a user
                userGameData usersData = userDB.userGameDataDAO().getGameData(inputtedUser);

                //The save slot (the timeline) the user has selected
                int saveSlot = saveSlotSelector.getSelectedItemPosition();

                //if the user presses the button without inputting a name
                if(inputtedUser.isEmpty()){
                    //display a toast saying no user name is inputted
                    Toast noUsernameInputed = new Toast(getActivity().getApplicationContext());
                    noUsernameInputed.setText("No username was inputted.");
                    noUsernameInputed.setGravity(Gravity.CENTER,0,0);
                    noUsernameInputed.setDuration(Toast.LENGTH_LONG);
                    noUsernameInputed.show();
                    return;
                }

                //if the inputted username already exists in the database retrieve the game data
                if(usersData != null){
                    //another check to make sure the user can get data
                    if (usersData.usersGameData != null && !usersData.usersGameData.isEmpty()) {
                        //give the user the data for their chosen save slot
                        gameData game = usersData.usersGameData.get(saveSlot);
                        Log.i("GameData", "Save Slot: " + saveSlot);
                        Log.i("GameData", "Score: " + usersData.user.getUsername());
                        Log.i("GameData", "Score: " + game.score);
                        Log.i("GameData", "Happiness: " + game.happiness);
                        //Share the users data with the other fragments
                        viewModel.setPlayersGameData(usersData);
                        viewModel.setSaveSlot(saveSlot);
                        Toast userLoggedIn = new Toast(getActivity().getApplicationContext());
                        userLoggedIn.setText("Welcome back " + usersData.user.getUsername() + "!");
                        userLoggedIn.setGravity(Gravity.CENTER,0,0);
                        userLoggedIn.setDuration(Toast.LENGTH_LONG);
                        userLoggedIn.show();
                        }
                    else {
                        //If there is an error getting the user data display this error
                        Log.i("ERROR", "nothing found");
                    }
                }

                //user was not found so new user is added to the database
                else{
                    Log.i("Play Data", "User was not found making new user");
                    //making new user and gameData objects to store the new data

                    //making a new user
                    user newUser = new user(inputtedUser);
                    userDB.userDAO().addUser(newUser);

                    user insertedUser= userDB.userDAO().getUsername(inputtedUser);

                    //making two game slots for both save slots
                    gameData newGameSlot1 = new gameData(0,100,insertedUser.id,1,1);
                    gameData newGameSlot2 = new gameData(0,100,insertedUser.id,1,1);

                    //adding the new user and data to the database
                    userDB.gameDataDAO().addGameData(newGameSlot1);
                    userDB.gameDataDAO().addGameData(newGameSlot2);

                    usersData= userDB.userGameDataDAO().getGameData(inputtedUser);

                    //set the players game data in the view model
                    viewModel.setPlayersGameData(usersData);

                    //Display a toast to confirm that the new user has been made
                    Toast newUserWasMade = new Toast(getActivity().getApplicationContext());
                    newUserWasMade.setText("New user has been made!");
                    newUserWasMade.setGravity(Gravity.CENTER,0,0);
                    newUserWasMade.setDuration(Toast.LENGTH_LONG);
                    newUserWasMade.show();

                    Log.i("New User", "User was added");
                    
                }


            }
        });

        return v;
    }
}