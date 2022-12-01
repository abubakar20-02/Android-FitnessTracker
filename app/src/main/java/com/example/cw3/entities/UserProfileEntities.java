package com.example.cw3.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "course_table")
public class UserProfileEntities {
    //Declare table name

        //Declare table elements
        @ColumnInfo(name = "UserName")
        private String UserName;

        @ColumnInfo(name = "UserAge")
        private int UserAge;

        @ColumnInfo(name = "UserWeight")
        private double UserWeight;

        public String getUserName() {
                return UserName;
        }

        public void setUserName(String userName) {
                UserName = userName;
        }

        public int getUserAge() {
                return UserAge;
        }

        public void setUserAge(int userAge) {
                UserAge = userAge;
        }

        public double getUserWeight() {
                return UserWeight;
        }

        public void setUserWeight(double userWeight) {
                UserWeight = userWeight;
        }
}
