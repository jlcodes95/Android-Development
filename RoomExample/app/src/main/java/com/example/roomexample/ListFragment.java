package com.example.roomexample;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {


    private List<User> users;
    // @TODO: Use autocomplete to get this method
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        final ListView lv = v.findViewById(R.id.lvUsers);
        users = MainActivity.db.userDao().getUsers();

        final UserArrayAdapter adapter = new UserArrayAdapter(getContext(), R.layout.user_layout, users);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                final User user = (User) lv.getItemAtPosition(position);
                AlertDialog.Builder confirmation = new AlertDialog.Builder(getContext());
                confirmation.setTitle("WARNING!");
                confirmation.setMessage("Are you sure you want to delete" + user.getFirstName() + " " + user.getLastName() + "?");
                confirmation.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("YES", "Clicked");
                        MainActivity.db.userDao().deleteUser(user);
                        users = MainActivity.db.userDao().getUsers();
                        ((BaseAdapter) lv.getAdapter()).notifyDataSetChanged();
//                        lv.setVisibility(View.VISIBLE);
                    }
                });
                confirmation.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast t = Toast.makeText(getContext(), "CANCELLED", Toast.LENGTH_SHORT);
                        t.show();
                    }
                });
                confirmation.show();
            }
        });

        return v;
    }

}
