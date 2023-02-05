package com.example.cw3.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.cw3.Adapter.CourseAdapter;
import com.example.cw3.databinding.ActivityStatisticBinding;
import com.example.cw3.viewmodels.StatisticsVM;

import java.util.Objects;

public class StatisticActivity extends AppCompatActivity {
    private CourseAdapter courseAdapter;
    private ActivityStatisticBinding activityStatisticBinding;
    private StatisticsVM statisticsVM;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        statisticsVM = new ViewModelProvider(this).get(StatisticsVM.class);
        activityStatisticBinding = ActivityStatisticBinding.inflate(LayoutInflater.from(this));
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(activityStatisticBinding.getRoot());
        activityStatisticBinding.setViewmodel(statisticsVM);

        //https://stackoverflow.com/questions/13377361/how-to-create-a-drop-down-list

        String[] items = new String[]{"Date","Distance", "Time", "Average Speed", "Rating"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        activityStatisticBinding.spinner.setAdapter(adapter);

        activityStatisticBinding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                statisticsVM.setAllCourses(activityStatisticBinding.spinner.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        courseAdapter = new CourseAdapter(this);

        //Observe for an update on every ordered list
        statisticsVM.getCoursesByDate().observe(this, courses -> statisticsVM.setByDate(courses, activityStatisticBinding.spinner.getSelectedItem().toString()));
        statisticsVM.getCoursesByTime().observe(this, courses -> statisticsVM.setByTime(courses, activityStatisticBinding.spinner.getSelectedItem().toString()));
        statisticsVM.getCoursesByDistance().observe(this, courses -> statisticsVM.setByDistance(courses, activityStatisticBinding.spinner.getSelectedItem().toString()));
        statisticsVM.getCoursesByAverageSpeed().observe(this, courses -> statisticsVM.setByAverageSpeed(courses, activityStatisticBinding.spinner.getSelectedItem().toString()));
        statisticsVM.getCoursesByRating().observe(this, courses -> statisticsVM.setByRating(courses, activityStatisticBinding.spinner.getSelectedItem().toString()));

        statisticsVM.getAllCourses().observe(this, courses -> courseAdapter.setData(courses));
        activityStatisticBinding.recyclerView.setAdapter(courseAdapter);
        activityStatisticBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        courseAdapter.setClickListener((view, position) -> {

            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                // course ID is sent to the course view so it can access the database content
                Intent intent = new Intent(this, CourseView.class);
                intent.putExtra("CourseID", (courseAdapter.getCourseID(position)));
                startActivity(intent);
            }
            else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
            }
        });
    }

    @Override
    protected void onDestroy() {
        statisticsVM.getAllCourses().removeObservers(this);
        statisticsVM.getCoursesByDate().removeObservers(this);
        statisticsVM.getCoursesByAverageSpeed().removeObservers(this);
        statisticsVM.getCoursesByDistance().removeObservers(this);
        statisticsVM.getCoursesByTime().removeObservers(this);
        statisticsVM.getCoursesByRating().removeObservers(this);
        super.onDestroy();
    }
}