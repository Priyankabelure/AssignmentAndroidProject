package com.assignment.androidproject.base_model;

import android.view.MenuItem;
import android.view.View;

public interface ToolbarButtonsListener {
    boolean onToolbarMenuItemSelected(MenuItem item);

    void onToolbarLeftButtonClicked(View view);
}
