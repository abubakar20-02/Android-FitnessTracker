package com.example.cw3.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class users {
        @PrimaryKey
        @NonNull
        @ColumnInfo(name = "UserName")
        private String UserName;

        @ColumnInfo(name = "UserAge")
        private int UserAge;

        @ColumnInfo(name = "UserWeight")
        private double UserWeight;

        @ColumnInfo(name = "UserSelected")
        private boolean UserSelected;

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

        public boolean isUserSelected() {
                return UserSelected;
        }

        public void setUserSelected(boolean userSelected) {
                UserSelected = userSelected;
        }

}
