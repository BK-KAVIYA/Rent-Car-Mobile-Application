package com.example.sourthenlankacarrental;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sourthenlankacarrental.Connection.DBConnection;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment{

    //---newly added
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    Connection connection;
    private DatabaseReference databaseReference;

    ArrayList<DynamicRVModel> item=new ArrayList();
    ArrayList<DynamicItemList> itemVehicle=new ArrayList();
    DynamicRVAdapter dynamicRVAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    //@Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        DBConnection dbConnection=new DBConnection();
        connection=dbConnection.getConnection();

        RecyclerView drv = v.findViewById(R.id.rv_1);
        drv.setLayoutManager(new LinearLayoutManager(container.getContext()));
        dynamicRVAdapter = new DynamicRVAdapter(itemVehicle);
        drv.setAdapter(dynamicRVAdapter);

        if (connection != null) {

            try {
                String query = "SELECT * FROM [slcrms].[dbo].[vehicle] WHERE is_deleted = 0 AND is_service_out = 0";
                PreparedStatement statement = null;
                statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();
                itemVehicle.clear();
                while (resultSet.next()){
                    Vehicle vehicle=new Vehicle();
                    vehicle.setId(resultSet.getInt(1));
                    vehicle.setTitle(resultSet.getString(2));
                    vehicle.setRating(5);
                    vehicle.setDescription("Test description");
                    vehicle.setImage(resultSet.getString(8));

                    itemVehicle.add(new DynamicItemList(vehicle.getId(),vehicle.getTitle(), vehicle.getDescription(), vehicle.getRating(), vehicle.getImage()));

                }
                dynamicRVAdapter.notifyDataSetChanged();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }



        }



//       connection.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                itemVehicle.clear();
//                for (DataSnapshot vehicelSnapshot : snapshot.getChildren()) {
//                    // Get the VehicleList object
//                    DynamicRVModel vehicleList = vehicelSnapshot.getValue(DynamicRVModel.class);
//                    Query query = databaseReference.child("vehicle").orderByChild("id").equalTo(vehicleList.getVehicele_id());
//                    query.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.exists()) {
//                                List<Vehicle> vehicles = new ArrayList<>();
//                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                                    Vehicle vehicle = snapshot.getValue(Vehicle.class);
//                                    vehicles.add(vehicle);
//                                }
//                                for (Vehicle vehicle : vehicles) {
//                                    itemVehicle.add(new DynamicItemList(vehicle.getId(),vehicle.getTitle(), vehicleList.getDescription(), vehicleList.getRating(), vehicle.getImage()));
//                                }
//                               dynamicRVAdapter.notifyDataSetChanged();
//                            }
//                            // Use the vehicle object here
//                            else {
//                                // Handle case when data doesn't exist
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//                            // Handle error cases
//                        }
//
//                    });
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                System.out.println("Error Occur-" + error.getMessage());
//            }
//        });

        return v;
    }



}


