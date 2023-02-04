package com.example.sourthenlankacarrental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    Button callSingup,login_btn;
    ImageView image;
    TextView logoText,sloganText;
    ProgressBar progressBar;
    TextInputLayout email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        //Hooks
        callSingup= findViewById(R.id.singup_screen);
        image= findViewById(R.id.LogiImage);
        logoText= findViewById(R.id.logo_name);
        sloganText= findViewById(R.id.slogo_name);
        login_btn= findViewById(R.id.login_btn);
        email=findViewById(R.id.username);
        password=findViewById(R.id.password);
        progressBar=findViewById(R.id.progress_bar);

        callSingup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,SingUp.class);

                Pair[] pairs=new Pair[7];

                pairs[0]=new Pair<View,String>(image,"logo_image");
                pairs[1]=new Pair<View,String>(logoText,"logo_text");
                pairs[2]=new Pair<View,String>(sloganText,"logo_desc");
                pairs[3]=new Pair<View,String>(email,"user_tran");
                pairs[4]=new Pair<View,String>(password,"password_tran");
                pairs[5]=new Pair<View,String>(login_btn,"login_tran");
                pairs[6]=new Pair<View,String>(callSingup,"button_tran");

                ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(Login.this,pairs);
                startActivity(intent,options.toBundle());

            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email=email.getEditText().getText().toString();
                String Password=password.getEditText().getText().toString();

                boolean isValidated=validateData(Email,Password);
                if (!isValidated){
                    return;
                }else {
                    loginAccountInFirebase(Email,Password);
                }
            }


        });

    }
    void loginAccountInFirebase(String email,String upassword){
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        changeInProgress(true);
        firebaseAuth.signInWithEmailAndPassword(email,upassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                changeInProgress(false);
                if(task.isSuccessful()){
                    //task successfull
                    if (firebaseAuth.getCurrentUser().isEmailVerified()){
                        //go to main activity
                        startActivity(new Intent(Login.this,Dashboard.class));
                        finish();
                    }else{
                        Utility.showToast(Login.this,"Email not verified, Please verify your Email");
                    }
                }else{
                    //task failure
                    Utility.showToast(Login.this,task.getException().getLocalizedMessage());
                }
            }
        });
    }

    void changeInProgress(boolean inProgress){
        if (inProgress){
            progressBar.setVisibility(View.VISIBLE);
            login_btn.setVisibility(View.GONE);
        }else {
            progressBar.setVisibility(View.GONE);
            login_btn.setVisibility(View.VISIBLE);
        }
    }
    boolean validateData(String testEmail,String TPassword){
        //validate user enter data
        if(!Patterns.EMAIL_ADDRESS.matcher(testEmail).matches()){
            email.setError("Email is Invalid");
            return false;
        }
        if (TPassword.length()<6){
            email.setError("");
            password.setError("Password Length Invaild");
            return false;
        }
        return true;

    }

}
