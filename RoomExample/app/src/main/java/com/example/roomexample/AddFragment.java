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
public class AddFragment extends Fragment {

    // @TODO: Use autocomplete to get this method

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add, container, false);
        ((Button) v.findViewById(R.id.btnAddUser)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickAdd(view);
            }
        });
        return v;
    }

    public void onClickAdd(View view){
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

            User user = new User();
            user.setId(Integer.parseInt(id));
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);

            MainActivity.db.userDao().addUser(user);
            Toast t = Toast.makeText(getContext(), "User added", Toast.LENGTH_SHORT);
            t.show();
        }catch(Exception e){
            Log.d("ADD_FRAGMENT", e.getMessage());
        }

    }

}
