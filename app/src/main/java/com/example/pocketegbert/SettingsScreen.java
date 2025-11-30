package com.example.pocketegbert;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsScreen extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // Spinners
    Spinner bgSpinner;
    Spinner diffSpinner;

    Button deleteData;

    public SettingsScreen() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsScreen.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsScreen newInstance(String param1, String param2) {
        SettingsScreen fragment = new SettingsScreen();
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
        return inflater.inflate(R.layout.fragment_settings_screen, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        userDatabase userDB = Room.databaseBuilder(requireContext().getApplicationContext(), userDatabase.class, "userDatabase")
                .allowMainThreadQueries().fallbackToDestructiveMigration(true).build();

        SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        userGameData usersData = viewModel.getPlayersGameData();

        int currentSave = viewModel.getSaveSlot();

        if(usersData != null){
            user currentUser = usersData.user;
            gameData game = usersData.usersGameData.get(currentSave);
        }
        else{
            Toast loginPlease = new Toast(getActivity().getApplicationContext());
            loginPlease.setText("Please go and enter a username.");
            loginPlease.setGravity(Gravity.CENTER,0,0);
            loginPlease.setDuration(Toast.LENGTH_LONG);
            loginPlease.show();
            return;
        }

        bgSpinner = view.findViewById(R.id.bgSpinner);
        diffSpinner = view.findViewById(R.id.difficultySpinner);
        deleteData = view.findViewById(R.id.deleteButton);

        bgSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gameData game = usersData.usersGameData.get(currentSave);
                String selectedItem = parent.getItemAtPosition(position).toString();
                if(selectedItem.equals("The White Void"))
                    game.background = 1;
                else if(selectedItem.equals("Skaia"))
                    game.background = 2;
                else
                    game.background = 3;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        diffSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gameData game = usersData.usersGameData.get(currentSave);
                String selectedItem = parent.getItemAtPosition(position).toString();
                if(selectedItem.equals("Easy"))
                    game.difficulty = 1;
                else if(selectedItem.equals("Normal"))
                    game.difficulty = 2;
                else if(selectedItem.equals("Hard"))
                    game.difficulty = 3;
                else
                    game.difficulty = 4;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user currentUser = usersData.user;
                gameData gameSlot1 = usersData.usersGameData.get(0);
                gameData gameSlot2 = usersData.usersGameData.get(1);
                userDB.gameDataDAO().deleteGameData(gameSlot1);
                userDB.gameDataDAO().deleteGameData(gameSlot2);
                userDB.userDAO().deleteUser(currentUser);
                Toast loginAgainPlease = new Toast(getActivity().getApplicationContext());
                loginAgainPlease.setText("Deleted All Data. Login again to start a new!");
                loginAgainPlease.setGravity(Gravity.CENTER,0,0);
                loginAgainPlease.setDuration(Toast.LENGTH_LONG);
                loginAgainPlease.show();
            }
        });
    }
}