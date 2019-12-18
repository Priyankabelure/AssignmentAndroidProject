package com.assignment.androidproject.view;

import android.os.Bundle;
import android.view.WindowManager;
import com.assignment.androidproject.base_model.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        if (savedInstanceState == null) {
            replaceFragment(ListFragment.newInstance(), false);
        }
    }

    @Override
    protected boolean shouldShowToolbar() {
        return true;
    }

}
