package com.example.travelmantics;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DealAdapter extends RecyclerView.Adapter<DealAdapter.DealViewHolder>
{
    ArrayList<TravelDeal> mdeals;
    private FirebaseDatabase myFirebaseDb;
    private DatabaseReference myDbRef;
    private ChildEventListener myChildListener;
    private ImageView imageDeal;

    public DealAdapter()
    {
       // FirebaseUtil.openFirebaseRef("traveldeals"); //check*
        myFirebaseDb = FirebaseUtil.myFirebasedb;
        myDbRef = FirebaseUtil.myDbRef;
        this.mdeals = FirebaseUtil.myDeals;
        myChildListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
                TravelDeal mtdeal = dataSnapshot.getValue(TravelDeal.class);
                Log.d("Deal: ",mtdeal.getTitle());
                mtdeal.setId(dataSnapshot.getKey());
                mdeals.add(mtdeal);
                notifyItemInserted(mdeals.size()-1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        myDbRef.addChildEventListener(myChildListener);
    }
    @NonNull
    @Override
    public DealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).
                inflate(R.layout.rows, parent,false);
        return new DealViewHolder(itemView);
       // return null;
    }

    @Override
    public void onBindViewHolder(@NonNull DealViewHolder holder, int position)
    {
        TravelDeal deal = mdeals.get(position);
        holder.bind(deal);

    }

    @Override
    public int getItemCount()
    {
        return mdeals.size();
       // return 0;
    }

    public class DealViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //TextView tvTitle;
        TextView textTitle;
        TextView textDescription;
        TextView textPrice;

        public DealViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle =itemView.findViewById(R.id.textTitle);
            textDescription = itemView.findViewById(R.id.textDescription);
            textPrice = itemView.findViewById(R.id.textPrice);
            imageDeal = itemView.findViewById(R.id.imageDeal);
            itemView.setOnClickListener(this);
        }
        public void bind(TravelDeal mdeal)
        {
            textTitle.setText(mdeal.getTitle());
            textDescription.setText(mdeal.getDescription());
            textPrice.setText(mdeal.getPrice());
            showImage(mdeal.getImageUrl());
        }
        public void onClick(View view)
        {
            int position = getAdapterPosition();
            Log.d("Click", String.valueOf(position));
            TravelDeal selectedDeal = mdeals.get(position);
            Intent intent = new Intent(view.getContext(), AdminsActivity.class);
            intent.putExtra("Deal", selectedDeal);
            view.getContext().startActivity(intent);
        }
        private void showImage(String url) {
    /*        if (url != null && url.isEmpty()==false) {

                Picasso.with(imageDeal.getContext())
                        .load(url)
                        .resize(160, 160)
                        .centerCrop()
                        .into(imageDeal);*/
                if (url != null && !url.isEmpty()) {
                Picasso.get()
                        .load(url)
                        .resize(160, 160)
                        .centerCrop()
                        .into(imageDeal);
            }
        }

    }

}
