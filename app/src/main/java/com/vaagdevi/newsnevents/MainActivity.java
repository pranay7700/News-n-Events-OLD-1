package com.vaagdevi.newsnevents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    EditText Emailid;
    EditText Passid;

    Button login;
    Button facebook;
    Button google;
    ImageButton register;

    TextView forgotpassid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Emailid=(EditText)findViewById(R.id.ETemailId);
        Passid=(EditText)findViewById(R.id.ETpassId);

        login=(Button)findViewById(R.id.BTNlogin);
        facebook=(Button)findViewById(R.id.BTNfb);
        google=(Button)findViewById(R.id.BTNgoogle);
        register=(ImageButton)findViewById(R.id.IMGBTNregister);

        forgotpassid=(TextView)findViewById(R.id.TVForgot);


        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailid=Emailid.getText().toString();
                String passid=Passid.getText().toString();


                if (emailid.isEmpty()&&passid.isEmpty())
                {
                    Toast.makeText(MainActivity.this,"Fields Empty!",Toast.LENGTH_SHORT).show();
                }

                else if (emailid.isEmpty())
                {
                    Emailid.setError("Provide Your Email");
                    Emailid.requestFocus();
                }
                else if(passid.isEmpty())
                {
                    Passid.setError("Enter Your Password");
                    Passid.requestFocus();
                }

                else if (!(emailid.isEmpty()&&passid.isEmpty()))
                {

                    mAuth.signInWithEmailAndPassword(emailid,passid)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!(task.isSuccessful())) {
                                        // Sign in success, update UI with the signed-in user's information


                                        Toast.makeText(MainActivity.this,"Logined Successfully",Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(MainActivity.this,Dashboard.class));
                                        finish();



                                    } else {
                                        // If sign in fails, display a message to the user.

                                        Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                                    }


                                }
                            });

                }
                else
                {
                    Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT).show();
                }



            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this,Registration.class));

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();


    }

}
