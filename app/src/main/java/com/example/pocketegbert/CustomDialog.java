package com.example.pocketegbert;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.room.Room;

public class CustomDialog extends DialogFragment {
    public Dialog onCreateDialog(@Nullable Bundle savedInstnaceState){

        //getting all the info used to delete the users data
        userDatabase userDB = Room.databaseBuilder(requireContext().getApplicationContext(), userDatabase.class, "userDatabase")
                .allowMainThreadQueries().fallbackToDestructiveMigration(true).build();

        SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        userGameData usersData = viewModel.getPlayersGameData();

        int currentSave = viewModel.getSaveSlot();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Deleting all data mean deleting all timelines and the user.");
        builder.setTitle("Would you like to delete your data?");
        //deletes all the users data
        builder.setPositiveButton("Yes delete ALL data", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                user currentUser = usersData.user;
                gameData gameSlot1 = usersData.usersGameData.get(0);
                gameData gameSlot2 = usersData.usersGameData.get(1);
                userDB.gameDataDAO().deleteGameData(gameSlot1);
                userDB.gameDataDAO().deleteGameData(gameSlot2);
                userDB.userDAO().deleteUser(currentUser);

                //toast confirming users data is gone
                Toast loginAgainPlease = new Toast(getActivity().getApplicationContext());
                loginAgainPlease.setText("Deleted All Data. Login again to start a new!");
                loginAgainPlease.setGravity(Gravity.CENTER,0,0);
                loginAgainPlease.setDuration(Toast.LENGTH_LONG);
                loginAgainPlease.show();

                //send them back to title
                NavHostFragment.findNavController(requireParentFragment())
                        .navigate(R.id.action_settingsScreen_to_titleScreen);
            }
        });

        //user declines deleting data
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //toast confirming no data is gone
                Toast noDataWasDeleted = new Toast(getActivity().getApplicationContext());
                noDataWasDeleted.setText("No data was deleted!");
                noDataWasDeleted.setGravity(Gravity.CENTER,0,0);
                noDataWasDeleted.setDuration(Toast.LENGTH_LONG);
                noDataWasDeleted.show();
            }
        });


        return builder.create();
    }
}
