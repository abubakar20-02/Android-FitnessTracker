package com.example.cw3.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.cw3.R;
import com.example.cw3.entities.users;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

//https://github.com/mdf/comp3018/blob/main/CatRecycler/app/src/main/java/uk/ac/nott/mrl/myapplication/CatRecyclerViewAdapter.java
public class UserRecycleAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<UserRecycleAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private ItemClickListener clickListener;
    private List<users> data;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public UserRecycleAdapter(Context context) {
        this.data = new ArrayList<>();
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.userlist_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<users> newData) {
        if(data != null) {
            data.clear();
            data.addAll(newData);
            notifyDataSetChanged();
        } else {
            data = newData;
        }
    }

    // bad practice but short on time.
    public String getUserName(int position) {
        return data.get(position).getUserName();
    }

    public int getUserAge(int position) {
        return data.get(position).getUserAge();
    }

    public double getUserWeight(int position){return data.get(position).getUserWeight();}

    public boolean getUserSelected(int position){return data.get(position).isUserSelected();}

    public class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder implements View.OnClickListener {

        TextView UserName;
        TextView UserAge;
        TextView UserWeight;

        ViewHolder(View itemView) {
            super(itemView);
            UserName = itemView.findViewById(R.id.UserNameInList);
            UserAge = itemView.findViewById(R.id.UserAgeInList);
            UserWeight = itemView.findViewById(R.id.UserWeightInList);
            itemView.setOnClickListener(this);
        }

        @SuppressLint("SetTextI18n")
        void bind(users userProfileEntities) {
            if(userProfileEntities != null) {
                UserName.setText(userProfileEntities.getUserName());
                UserAge.setText(Integer.toString(userProfileEntities.getUserAge()));
                UserWeight.setText((df.format(userProfileEntities.getUserWeight())));
            }
        }

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

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position) throws ExecutionException, InterruptedException;
    }
}
