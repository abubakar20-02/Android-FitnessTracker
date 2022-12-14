package com.example.cw3.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.cw3.R;
import com.example.cw3.entities.Course;
import com.example.cw3.entities.UserProfileEntities;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

//Adapter for recycler view to display all course data
public class RecycleAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<RecycleAdapter.ViewHolder> {

    //Declare objects and list
    private final LayoutInflater inflater;
    private ItemClickListener clickListener;
    private List<UserProfileEntities> data;
    //Format for doubles
    private static final DecimalFormat df = new DecimalFormat("0.00");
    //Format for doubles
//    private static final DecimalFormat df = new DecimalFormat("0.00");

    //Constructor for initialising data list and layout inflater
    public RecycleAdapter(Context context) {
        this.data = new ArrayList<>();
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.userlist, parent, false);
        return new ViewHolder(view);
    }

    //Called by RecyclerView to display the data at the specified position.
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    //Get the amount of elements in the data list
    @Override
    public int getItemCount() {
        return data.size();
    }

//    //Get the ID of a selected item in the list
//    @Override
//    public long getItemId(int position) {
//        return data.get(position).getCourseID();
//    }

    //If the data is not empty then clear it and add the newly given list, otherwise set data to the new data
    //Notify dataset has been changed
    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<UserProfileEntities> newData) {
        if(data != null) {
            data.clear();
            data.addAll(newData);
            notifyDataSetChanged();
        } else {
            data = newData;
        }
    }

    //Get the ID of a selected item in the list
    public String getUserName(int position) {
        return data.get(position).getUserName();
    }

    public int getUserAge(int position) {
        return data.get(position).getUserAge();
    }

    public double getUserWeight(int position){return data.get(position).getUserWeight();}

    public boolean getUserSelected(int position){return data.get(position).isUserSelected();}

    //Describes an item view and metadata about its place within the RecyclerView
    public class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder implements View.OnClickListener {

        //Declare view items
        TextView UserName;
        TextView UserAge;
        TextView UserWeight;

        //Constructor assign ids to view items
        ViewHolder(View itemView) {
            super(itemView);
            UserName = itemView.findViewById(R.id.UserNameInList);
            UserAge = itemView.findViewById(R.id.UserAgeInList);
            UserWeight = itemView.findViewById(R.id.UserWeightInList);
            itemView.setOnClickListener(this);
        }

        //Bind view items to the associated data values
        @SuppressLint("SetTextI18n")
        void bind(UserProfileEntities userProfileEntities) {
            if(userProfileEntities != null) {
                UserName.setText(userProfileEntities.getUserName());
                UserAge.setText(Integer.toString(userProfileEntities.getUserAge()));
                UserWeight.setText((df.format(userProfileEntities.getUserWeight())));
            }
        }

        //When an item is clicked get position
        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                try {
                    clickListener.onItemClick(view, getAdapterPosition());
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //Set click listener
    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    //Declare click listener interface
    public interface ItemClickListener {
        void onItemClick(View view, int position) throws ExecutionException, InterruptedException;
    }
}
