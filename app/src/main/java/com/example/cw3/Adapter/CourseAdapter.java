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
import com.example.cw3.Tools;
import com.example.cw3.entities.Course;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

// https://github.com/mdf/comp3018/blob/main/CatRecycler/app/src/main/java/uk/ac/nott/mrl/myapplication/CatRecyclerViewAdapter.java
public class CourseAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private ItemClickListener clickListener;
    private List<Course> data;

    private Tools tools = new Tools();

    public CourseAdapter(Context context) {
        this.data = new ArrayList<>();
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @NonNull
    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_course_card, parent, false);
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
    public void setData(List<Course> newData) {
        if(data != null) {
            data.clear();
            data.addAll(newData);
            notifyDataSetChanged();
        } else {
            data = newData;
        }
    }

    public int getCourseID(int position) {
        return data.get(position).getCourseID();
    }

    public class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder implements View.OnClickListener {

        TextView CourseID;
        TextView Date;
        TextView Distance;
        TextView Time;
        TextView Speed;
        RatingBar RatingBar;


        ViewHolder(View itemView) {
            super(itemView);
            CourseID = itemView.findViewById(R.id.CourseID);
            Date = itemView.findViewById(R.id.dateCard);
            Distance = itemView.findViewById(R.id.distanceCard);
            Time = itemView.findViewById(R.id.totalTimeCard);
            Speed = itemView.findViewById(R.id.averageSpeedCard);
            RatingBar = itemView.findViewById(R.id.ratingBar2);
            itemView.setOnClickListener(this);
        }

        @SuppressLint("SetTextI18n")
        void bind(Course course) {
            if(course != null) {
                CourseID.setText("Course " + course.getCourseID());
                Date.setText(course.getDate());
                Distance.setText(tools.formatDistance(course.getDistance()));
                Time.setText(tools.formatTime(course.getTime()));
                Speed.setText(tools.formatAverageSpeed(course.getAverageSpeed()));
                RatingBar.setRating(course.getRating());
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
