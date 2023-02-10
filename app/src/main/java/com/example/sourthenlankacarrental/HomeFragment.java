package com.example.sourthenlankacarrental;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sourthenlankacarrental.DRVInterface.LoadMore;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HomeFragment extends Fragment {

    //---newly added

    List<DynamicRVModel> item=new ArrayList();
    DynamicRVAdapter dynamicRVAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_home, container, false);


        //---newly aded
        item.add(new DynamicRVModel("Burger"));
        item.add(new DynamicRVModel("Burger"));
        item.add(new DynamicRVModel("Burger"));
        item.add(new DynamicRVModel("Burger"));
        item.add(new DynamicRVModel("Burger"));
        item.add(new DynamicRVModel("Burger"));
        item.add(new DynamicRVModel("Burger"));
        item.add(new DynamicRVModel("Burger"));
        item.add(new DynamicRVModel("Burger"));
        item.add(new DynamicRVModel("Burger"));
        item.add(new DynamicRVModel("Burger"));
        item.add(new DynamicRVModel("Burger"));
        item.add(new DynamicRVModel("Burger"));
        item.add(new DynamicRVModel("Burger"));

        RecyclerView drv=v.findViewById(R.id.rv_1);
        drv.setLayoutManager(new LinearLayoutManager(container.getContext()));
        dynamicRVAdapter=new DynamicRVAdapter(drv,(Activity) container.getContext() ,item);
        drv.setAdapter(dynamicRVAdapter);

        dynamicRVAdapter.setLoadMore(new LoadMore() {
            @Override
            public void onLoadmore() {
                if (item.size()<=10){
                    item.add(null);
                    dynamicRVAdapter.notifyItemInserted(item.size()-1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            item.remove(item.size()-1);
                            dynamicRVAdapter.notifyItemRemoved(item.size());

                            int index=item.size();
                            int end=index+10;
                            for (int i=index;i<end;i++){
                                String name= UUID.randomUUID().toString();
                                DynamicRVModel dynamicRVModel=new DynamicRVModel(name);
                                item.add(dynamicRVModel);

                            }
                            dynamicRVAdapter.notifyDataSetChanged();
                            dynamicRVAdapter.setLoded();
                        }
                    },4000);

                }
            }
        });

        return v;

    }

}