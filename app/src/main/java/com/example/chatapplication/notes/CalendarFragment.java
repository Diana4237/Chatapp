package com.example.chatapplication.notes;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.chatapplication.R;

import java.util.Calendar;

public class CalendarFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    private String loginUser;
    private String password;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Используйте текущую дату как значение по умолчанию для календаря
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        Bundle args = getArguments();
        if (args != null) {
            loginUser = args.getString("login");
            password=args.getString("password");
            // Используем loginUser по необходимости
        }
        // Создайте новый экземпляр DatePickerDialog и верните его
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    // Этот метод вызывается при выборе даты в календаре
    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Отправьте выбранную дату в PlanDayActivity
        Intent intent = new Intent(getActivity(), PlanDayActivity2.class);
        intent.putExtra("login",loginUser);
        intent.putExtra("password",password);
        intent.putExtra("selectedYear", year);
        intent.putExtra("selectedMonth", month);
        intent.putExtra("selectedDay", day);
        startActivity(intent);
    }
}