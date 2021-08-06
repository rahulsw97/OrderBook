package com.projectscaffe.orderbook;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HelperAdapter extends RecyclerView.Adapter {


    List<Customers> getCustomersData;

    public HelperAdapter(List<Customers> getCustomersData) {
        this.getCustomersData = getCustomersData;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        ViewHolderClass viewHolderClass = new ViewHolderClass(view);



        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ViewHolderClass viewHolderClass = (ViewHolderClass) holder;

        Customers customers = getCustomersData.get(position);
        viewHolderClass.nm.setText(customers.getName());
        viewHolderClass.addr.setText(customers.getAddress());
        viewHolderClass.dt.setText(customers.getDate());
        viewHolderClass.tm.setText(customers.getTime());
        viewHolderClass.pht.setText(customers.getPhoto());
        viewHolderClass.vdo.setText(customers.getVideo());
        viewHolderClass.prew.setText(customers.getPre_wedding());
        viewHolderClass.cine.setText(customers.getCinematic());
        viewHolderClass.cand.setText(customers.getCandid());


    }

    @Override
    public int getItemCount() {
        return getCustomersData.size();
    }


    public class ViewHolderClass extends RecyclerView.ViewHolder{

        TextView nm,addr,dt,tm,pht,vdo,prew,cine,cand;

        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);

            nm = itemView.findViewById(R.id.display_name);
            addr = itemView.findViewById(R.id.display_address);
            dt = itemView.findViewById(R.id.display_Date);
            tm = itemView.findViewById(R.id.display_Time);
            pht = itemView.findViewById(R.id.diaplay_photo);
            vdo = itemView.findViewById(R.id.display_video);
            prew = itemView.findViewById(R.id.display_preweddingo);
            cine = itemView.findViewById(R.id.display_cinematic);
            cand =itemView.findViewById(R.id.display_candid);



        }
    }


}
