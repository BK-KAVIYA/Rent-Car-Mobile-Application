package com.example.sourthenlankacarrental;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sourthenlankacarrental.user.UserHelperClass;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SettingFragment extends Fragment {


    private String email=null;
    private DatabaseReference databaseReference;
    private TextInputEditText fullname,uemail,uname,idNumber,mobile,password;
    private Button profileButton;

    FirebaseUser user;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // super.onCreate(savedInstanceState);
        // Inflate the layout for this fragment
//        setContentView(R.layout.activity_gaurantor_details);
//        getSupportActionBar().hide();
        View view = inflater.inflate(R.layout.activity_user_profile, container, false);
        fullname=view.findViewById(R.id.full_name_profile);
        uemail=view.findViewById(R.id.email_profile);
        uname=view.findViewById(R.id.user_name_profile);
        idNumber=view.findViewById(R.id.id_number_profile);
        mobile=view.findViewById(R.id.phone_number_profile);
        password=view.findViewById(R.id.password_profile);
        profileButton=view.findViewById(R.id.usr_profile_btn);

        // Get another reference to the root node of the database
        databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = databaseReference.child("users");

        // Add a ValueEventListener to retrieve data from the "users" node in the database
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            email = user.getEmail();
        }



        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = fullname.getText().toString();
                String userName = uname.getText().toString();
                String userEmail = uemail.getText().toString();
                String phoneNumber = mobile.getText().toString();
                String idNumberValue = idNumber.getText().toString();
                String passwordValue = password.getText().toString();
                String cPasswordValue = password.getText().toString();

//                Map<String, Object> updates = new HashMap<>();
//                updates.put("regName", fullName);
//                updates.put("userName", userName);
//                updates.put("email", userEmail);
//                updates.put("phoneNumber", phoneNumber);
//                updates.put("idNumber", idNumberValue);
//                updates.put("password", passwordValue);
//                updates.put("cPassword", cPasswordValue);



                // Assuming you have already initialized the Firebase app and the user is authenticated
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                String userID = currentUser.getUid(); // Get the user ID of the current user

                // Assuming you have a Firebase reference to a location called "users"
                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users").child(userID);

                // Define the updates object
                Map<String, Object> updates = new HashMap<>();
                updates.put("regName",userName);
                updates.put("userName", fullName);
                updates.put("email", userEmail);
                updates.put("phoneNumber", phoneNumber);
                updates.put("idNumber", idNumberValue);
                updates.put("password", passwordValue);
                updates.put("cPassword", passwordValue);

                // Perform the update
                usersRef.updateChildren(updates)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                System.out.println("Update successful!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                System.out.println("Update failed: " + e.getMessage());
                            }
                        });

                System.out.println("Called the button");
            }
        });

                    // Get another reference to the root node of the database
                    databaseReference = FirebaseDatabase.getInstance().getReference();



                    // Create a query to retrieve the corresponding Vehicle object from the "users" node
                    Query query = databaseReference.child("users").orderByChild("email").equalTo(email);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {

                                UserHelperClass userHelperClass=new UserHelperClass();
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    UserHelperClass usc = snapshot.getValue(UserHelperClass.class);
                                    fullname.setText(usc.getRegName());
                                    uemail.setText(usc.getEmail());
                                    uname.setText(usc.getUserName());
                                    idNumber.setText(usc.getIdNumber());
                                    mobile.setText(usc.getPhonenNumber());
                                    password.setText(usc.getPassword());


                                    System.out.println("User Details" + usc.toString());
                                }


                               // dynamicRVAdapter.notifyDataSetChanged();
                            } else {
                                // Handle case when data doesn't exist
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle the error
                        }
                    });
            return view;
        }


}

