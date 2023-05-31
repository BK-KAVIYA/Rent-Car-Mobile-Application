package com.example.sourthenlankacarrental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.sourthenlankacarrental.user.UserHelperClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SingUp extends AppCompatActivity {

    //variable
    TextInputLayout regName,userName,email,phonenNumber,idNumber,Password,cPassword;
    Button regBtn,regToLoginbtn;
    ProgressBar progressBar;
    FirebaseDatabase rootNode;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sing_up);
        getSupportActionBar().hide();


        //Hooks to all xml element in activity sing up
        regName=findViewById(R.id.name);
        userName=findViewById(R.id.uname);
        email=findViewById(R.id.email);
        phonenNumber=findViewById(R.id.phonenumber);
        idNumber=findViewById(R.id.idnumber);
        Password=findViewById(R.id.password);
        cPassword=findViewById(R.id.confirmpassword);
        regBtn=findViewById(R.id.reg_btn);
        regToLoginbtn=findViewById(R.id.reg_login_btn);
        progressBar=findViewById(R.id.progress_bar);

        //save data in firebase on button click
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //get all the values
                String Email=email.getEditText().getText().toString();
                String Pwd=Password.getEditText().getText().toString();
                String cPwd=cPassword.getEditText().getText().toString();

                boolean isValidated=validateData(Email,Pwd,cPwd);
                if (!isValidated){
                    return;
                }else {
                    createAccountFirebase(Email,Pwd);
                }


            }
        });


    }
    void createAccountFirebase(String email,String password){
        changeInProgress(true);
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SingUp.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                changeInProgress(false);
                if(task.isSuccessful()){
                    //create account is done
                    String name=regName.getEditText().getText().toString();
                    String uName=userName.getEditText().getText().toString();
                    String phoneNumber=phonenNumber.getEditText().getText().toString();
                    String IDNumber=idNumber.getEditText().getText().toString();
                    String cPwd=cPassword.getEditText().getText().toString();

                    rootNode=FirebaseDatabase.getInstance();
                    reference=rootNode.getReference("users");

                    UserHelperClass helperClass=new UserHelperClass(name,uName,email,phoneNumber,IDNumber,password,cPwd);
                    reference.child(IDNumber).setValue(helperClass);

                    Utility.showToast(SingUp.this,"Succefully create account, Check email to verify");
                    firebaseAuth.getCurrentUser().sendEmailVerification();
                    firebaseAuth.signOut();
                    finish();
                }else {
                    //failure
                    Utility.showToast(SingUp.this,task.getException().getLocalizedMessage());

                }
            }
        });
    }

    void changeInProgress(boolean inProgress){
        if (inProgress){
            progressBar.setVisibility(View.VISIBLE);
            regBtn.setVisibility(View.GONE);
        }else {
            progressBar.setVisibility(View.GONE);
            regBtn.setVisibility(View.VISIBLE);
        }
    }
    boolean validateData(String testEmail,String TPassword,String TcPassword){
        //validate user enter data
        if(!Patterns.EMAIL_ADDRESS.matcher(testEmail).matches()){
            email.setError("Email is Invalid");
            return false;
        }
        if (TPassword.length()<6){
            email.setError("");
            Password.setError("Password Length Invaild");
            return false;
        }
        if (!TPassword.equals(TcPassword)){
            Password.setError("");
            cPassword.setError("Password not matched");
            return false;
        }
        return true;

    }
}
