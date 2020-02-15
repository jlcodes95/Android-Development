package com.example.roomexample;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateFragment extends Fragment {

    // @TODO: Use autocomplete to get this method
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_update, container, false);
        ((Button) v.findViewById(R.id.btnUpdateUser)).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickUpdate(view);
            }
        });

        return v;
    }

    public void onClickUpdate(View view){
        EditText etId = getView().findViewById(R.id.etId);
        EditText etFirstName = getView().findViewById(R.id.etFirstName);
        EditText etLastName = getView().findViewById(R.id.etLastName);
        EditText etEmail = getView().findViewById(R.id.etEmail);

        String id = etId.getText().toString();
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String email = etEmail.getText().toString();

        try{
            if (id.equals("")){
                throw new Exception("please enter ID");
            }else if (firstName.equals("")){
                throw new Exception("please enter firstName");
            }else if (lastName.equals("")){
                throw new Exception("please enter lastName");
            }else if (email.equals("")){
                throw new Exception("please enter email");
            }

            MainActivity.db.userDao().updateUser(Integer.parseInt(id), firstName, lastName, email);

            Toast t = Toast.makeText(getContext(), "User updated", Toast.LENGTH_SHORT);
            t.show();
        }catch(Exception e){
            Log.d("UPDATE_FRAGMENT", e.getMessage());
        }
    }
}
