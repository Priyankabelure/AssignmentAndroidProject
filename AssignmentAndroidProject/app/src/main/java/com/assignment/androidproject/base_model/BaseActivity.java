package com.assignment.androidproject.base_model;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.assignment.androidproject.R;



/**
 * Base Activity for the application containing commonly required methods.
 */

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();

    private BaseFragment mCurrentFragmentShown;
    private Toolbar mToolbar;
    protected TextView toolbarTitle;
    private LinearLayout backBtnLayout;
    private FrameLayout rootContainer;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("TESTING", "CURRENT ACTIVITY : " + getClass().getSimpleName());
        setContentView(getLayoutResId());
        mToolbar = findViewById(R.id.toolbar);
        toolbarTitle = findViewById(R.id.toolbar_title);
        backBtnLayout = findViewById(R.id.back_button_layout);

        rootContainer = findViewById(R.id.root_container);
        if (backBtnLayout != null) {
            backBtnLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }


        setupToolbar();

    }

    @Override
    protected void onPause() {
        super.onPause();
        ProgressDialogManager.dismissProgressDialog(this);
    }

    public void setCurrentFragmentShown(BaseFragment fragment) {
        mCurrentFragmentShown = fragment;
    }

    /**
     * Specifies the activity layout should that should be used to add/replace fragments. This layout
     * should include toolbar if required.
     * Returns activity base layout by default. If activity uses its own layout, override this method
     * and return the layout id.
     *
     * @return layout resource id
     */
    protected int getLayoutResId() {
        return R.layout.activity_base;
    }

    /**
     * Specifies if the base animation (fade in/fade out) should be used for activity transitions.
     * Returns true by default. If activity will define its own animation, override this method and return false.
     *
     * @return whether base animation should be used
     */
    protected boolean shouldUseBaseAnimation() {
        return true;
    }

    /**
     * This id is used to place fragments. If an activity extending from {@link BaseActivity} uses its
     * own layout, it should specify the root container id here.
     *
     * @return id of the container to be used
     */
    protected int getRootContainerId() {
        return R.id.root_container;
    }

    /**
     * Callback received when hardware back button is pressed
     * If back button press is handled by fragment, super.onBackPressed() will not be called
     */
    @Override
    public void onBackPressed() {
        if (isBackPressHandledByFragment()) {
            Log.d(TAG, "Back button press handled by fragment " +
                    mCurrentFragmentShown.getClass().getSimpleName());
            return;
        }
        super.onBackPressed();
    }

    public boolean isBackPressHandledByFragment() {
        return mCurrentFragmentShown != null && mCurrentFragmentShown.handleOnBackPress();
    }

    //Can add extra parameter to show enter/exit animation if required
    public void replaceFragment(BaseFragment fragment, boolean addTobackStack) {
        if (fragment == null) {
            return;
        }
        if ((mCurrentFragmentShown != null) && (fragment.getClass() == mCurrentFragmentShown.getClass())) {
            return;
        }
        if (isFinishing() || isChangingConfigurations() || isDestroyed()) {
            return;
        }
        mCurrentFragmentShown = fragment;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim);
        fragmentTransaction.replace(getRootContainerId(), fragment);
        if (addTobackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    //Can add extra parameter to show enter/exit animation if required
    public void addFragment(BaseFragment fragment, boolean addTobackStack) {
        if (fragment == null) {
            return;
        }
        if ((mCurrentFragmentShown != null) && (fragment.getClass() == mCurrentFragmentShown.getClass())) {
            return;
        }
        if (isFinishing() || isChangingConfigurations() || isDestroyed()) {
            return;
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(getRootContainerId(), fragment);
        if (addTobackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void setupToolbar() {
        // If the activity uses its own layout having no toolbar, visbility shouldn't be handled
        if (mToolbar != null) {

            mToolbar.setVisibility(shouldShowToolbar() ? View.VISIBLE : View.GONE);
            setSupportActionBar(mToolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
        }
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public boolean isShowingToolbar() {
        return (mToolbar != null);
    }

    protected boolean shouldShowToolbar() {
        return true;
    }

    protected BaseFragment getCurrentShownFragment() {
        return mCurrentFragmentShown;
    }

    public void setAppBarLayoutElevation(boolean isShow) {
        if (mToolbar != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (isShow) {
                mToolbar.setElevation(10);
            } else {
                mToolbar.setElevation(0);
            }
        }
    }

    public void setToolbarTitle(String title) {
        if(toolbarTitle != null) {
            toolbarTitle.setVisibility(View.VISIBLE);
            toolbarTitle.setText(title);
        }
    }

    public void showToolbar(boolean shouldShowToolbar) {
        if (mToolbar != null) {
            if (shouldShowToolbar) {
                mToolbar.setVisibility(View.VISIBLE);
            } else {
                mToolbar.setVisibility(View.GONE);
            }
        }
    }

    public void setToolbarTitle(int title) {
        toolbarTitle.setVisibility(View.VISIBLE);
        toolbarTitle.setText(title);
    }

    public void setBackBtnVisible(boolean isVisible) {
        if(backBtnLayout != null) {
            if (isVisible) {
                backBtnLayout.setVisibility(View.VISIBLE);
            } else {
                backBtnLayout.setVisibility(View.GONE);
            }
        }
    }
}
