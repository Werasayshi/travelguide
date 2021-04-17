package com.example.travelguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Rejestracja extends AppCompatActivity {
    EditText mEmail, mHaslo;
    Button mRejestracjaBtn;
    Button mLoginBtn;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejestracja);

        mEmail = findViewById(R.id.Email);
        mHaslo = findViewById(R.id.Hasło);
        mRejestracjaBtn = findViewById(R.id.button);
        mLoginBtn = findViewById(R.id.login);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() !=null) {
            System.out.println("Ok1");
            startActivity(new Intent(getApplicationContext(), Main.class));
            finish();
        }



        mRejestracjaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String hasło = mHaslo.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email jest wymagany");
                    return;
                }

                if(TextUtils.isEmpty(hasło)){
                    mHaslo.setError("Hasło jest wymagane");
                    return;
                }
                if(hasło.length() < 8) {
                    mHaslo.setError("Hasło musi mieć minimum 8 znaków");
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(email,hasło).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(Rejestracja.this, "Użytkownik zarejestrowany", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Main.class));
                        } else {
                            Toast.makeText(Rejestracja.this, "Wystąpił błąd " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Logowanie.class));
                finish();
            }
        });


    }



}
