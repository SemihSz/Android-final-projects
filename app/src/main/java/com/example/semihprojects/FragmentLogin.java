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

public class FragmentLogin extends Fragment {
    View view;
    private EditText email,password;
    private Button sign_in, sign_up;
    private FirebaseAuth mAuth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_fragment, container, false);
        email = view.findViewById(R.id.editText);
        password = view.findViewById(R.id.editText2);
        sign_in = view.findViewById(R.id.sign_in);
        mAuth = FirebaseAuth.getInstance();
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if(!TextUtils.isEmpty(email.getText().toString()) && !TextUtils.isEmpty(password.getText().toString())){
                    mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            Toast.makeText(view.getContext(), "Welcome "+ email.getText().toString(), Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getActivity().getApplication(), MainActivity.class));
                        }
                    });
                }
                else{
                    Toast.makeText(view.getContext(), "You have Fill every field", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }
}
