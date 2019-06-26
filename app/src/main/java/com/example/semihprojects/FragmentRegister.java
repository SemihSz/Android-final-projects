package com.example.semihprojects;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class FragmentRegister extends Fragment {
    View view;
    EditText username, email, password;
    Button register_account;
    FirebaseAuth mAuth;
    DatabaseReference database;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.register_fragment,container,false);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        mAuth = FirebaseAuth.getInstance();
        username = view.findViewById(R.id.username);
        email = view.findViewById(R.id.emailRegister);
        password = view.findViewById(R.id.passwordRegister);
        register_account = view.findViewById(R.id.registerButtonAc);

        register_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String txt_username = username.getText().toString();
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();

                if(TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                    Toast.makeText(view.getContext(), "You have to fields every information", Toast.LENGTH_LONG).show();
                }
                else if(txt_password.length() < 6){
                    Toast.makeText(view.getContext(), "Password is shorter", Toast.LENGTH_LONG).show();
                }
                else {

                    register(txt_username,txt_email,txt_password);
                }

            }
        });

        return view;
    }
    private void register(final String username, final String email, final String password){
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    final String userId = firebaseUser.getUid();
                    database = FirebaseDatabase.getInstance().getReference("Users").child(userId);
                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("id",userId);
                    hashMap.put("username", username);
                    hashMap.put("email",email);
                    database.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Intent intent = new Intent(getActivity().getApplication(), MainActivity.class);
                                intent.putExtra("userdid", userId);
                                startActivity(intent);
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(view.getContext(), "You can't register with this email or password",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}
