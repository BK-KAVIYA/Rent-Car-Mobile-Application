package com.example.sourthenlankacarrental;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sourthenlankacarrental.BookingDetails.MyBookingFragment;
import com.example.sourthenlankacarrental.Connection.DBConnection;
import com.example.sourthenlankacarrental.user.UserHelperClass;
import com.example.sourthenlankacarrental.user.UserSingleton;
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
import com.google.protobuf.StringValue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SettingFragment extends Fragment {


    private String email=null;

    private Connection connection;
    private TextInputEditText fullname,uemail,uname,idNumber,mobile,password;
    private Button profileButton;
    RelativeLayout myRelativeLayout;

    TextView numOfBooking,paymentAmount,fullName,Email;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_user_profile, container, false);
        fullname=view.findViewById(R.id.full_name_profile);
        uemail=view.findViewById(R.id.email_profile);
        uname=view.findViewById(R.id.user_name_profile);
        idNumber=view.findViewById(R.id.id_number_profile);
        mobile=view.findViewById(R.id.phone_number_profile);
        password=view.findViewById(R.id.password_profile);
        profileButton=view.findViewById(R.id.usr_profile_btn);
        myRelativeLayout = view.findViewById(R.id.mybooking);
        numOfBooking=view.findViewById(R.id.booking_label);
        paymentAmount=view.findViewById(R.id.payment_label);
        fullName=view.findViewById(R.id.fullname_field);
        Email=view.findViewById(R.id.profile_email);


        DBConnection dbConnection=new DBConnection();
        connection=dbConnection.getConnection();

        email= UserSingleton.getInstance().getUserEmail();

        myRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = requireContext(); // or getContext() if you're inside a Fragment
                Intent intent = new Intent(context, MyBookingFragment.class);
                startActivity(intent);
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fullName = fullname.getText().toString();
                String address = uname.getText().toString();
                String userEmail = uemail.getText().toString();
                String phoneNumber = mobile.getText().toString();
                String idNumberValue = idNumber.getText().toString();
                String passwordValue = password.getText().toString();
                String cPasswordValue = password.getText().toString();

                PreparedStatement statement = null;
                try {
                    String query = "UPDATE [slcrms].[dbo].[user] SET name = ?,nic = ?,phone = ?,address = ?,password = ? WHERE email = ?";
                    statement = connection.prepareStatement(query);
                    statement.setString(1, fullName);
                    statement.setString(2, idNumberValue);
                    statement.setString(3, phoneNumber);
                    statement.setString(4, address);
                    statement.setString(5, passwordValue);
                    statement.setString(6, email);

                    int rowsAffected = statement.executeUpdate();
                    if (rowsAffected > 0) {
                        Context context = getContext();
                        Toast.makeText(context, "Update Successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Context context = getContext();
                        Toast.makeText(context, "Update Failed!", Toast.LENGTH_SHORT).show();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        });



                    try {
                        String query = "SELECT * FROM [slcrms].[dbo].[user] WHERE email = ?";
                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.setString(1, email);

                        ResultSet resultSet = statement.executeQuery();

                        if(resultSet.next()){
                            UserHelperClass userHelperClass=new UserHelperClass();
                            userHelperClass.setRegName(resultSet.getString(2));
                            userHelperClass.setEmail(resultSet.getString(4));
                            userHelperClass.setUserName(resultSet.getString(6));
                            userHelperClass.setIdNumber(resultSet.getString(3));
                            userHelperClass.setPhonenNumber(resultSet.getString(5));
                            userHelperClass.setPassword(resultSet.getString(9));


                            fullName.setText(userHelperClass.getRegName());
                            Email.setText(userHelperClass.getEmail());

                            fullname.setText(userHelperClass.getRegName());
                            uemail.setText(userHelperClass.getEmail());
                            uname.setText(userHelperClass.getUserName());
                            idNumber.setText(userHelperClass.getIdNumber());
                            mobile.setText(userHelperClass.getPhonenNumber());
                            password.setText(userHelperClass.getPassword());
                        }


                        if (connection != null) {


                            String query1 = "select count(id) AS row_count  FROM [slcrms].[dbo].[booking] WHERE customer_email=?";
                            PreparedStatement statement1 = connection.prepareStatement(query1);
                            statement1.setString(1, email);

                            ResultSet resultSet1 = statement1.executeQuery();
                            int rowCount=0;
                            if (resultSet1.next()) {
                                rowCount=resultSet1.getInt("row_count");
                            }
                            System.out.println("Rows-----------------"+rowCount);
                            numOfBooking.setText(String.valueOf(rowCount));
                        }

                        if(connection!=null){
                            String query2="select sum(amount) AS amount  FROM [slcrms].[dbo].[transaction] WHERE booking_id IN (select id  FROM [slcrms].[dbo].[booking] WHERE customer_email=?)";
                            PreparedStatement statement2 = connection.prepareStatement(query2);
                            statement2.setString(1, email);

                            ResultSet resultSet2 = statement2.executeQuery();
                            int amount=0;
                            if (resultSet2.next()) {
                                amount=resultSet2.getInt("amount");
                            }
                            paymentAmount.setText("Rs: "+String.valueOf(amount)+".00/=");
                        }


                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

            return view;
        }


}

