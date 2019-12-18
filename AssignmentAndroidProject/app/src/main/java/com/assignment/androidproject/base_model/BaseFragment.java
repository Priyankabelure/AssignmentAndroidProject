package com.assignment.androidproject.base_model;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.MenuRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * All fragments should extend from the BaseFragment.
 * It contains commonly required methods.
 */

public class BaseFragment extends Fragment implements ToolbarButtonsListener {

    private ToolbarButtonsListener mListener;
    private Context mContext;
    private boolean showSearchIcon = false;
    private SearchView searchView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).setCurrentFragmentShown(this);
        }
        if (((BaseActivity) getActivity()).isShowingToolbar()) {
            setupToolbar();
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = this;
        mContext = context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        ProgressDialogManager.dismissProgressDialog(mContext);
    }

    public void setupToolbar() {
        final BaseActivity activity = (BaseActivity) mContext;
        if (activity == null) {
            return;
        }

        if (searchView != null) {
            searchView.setVisibility(showSearchIcon ? View.VISIBLE : View.GONE);

            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
/*
                    if (getActivity() != null) {
                        Fragment fragment = ((BaseActivity) getActivity()).getCurrentShownFragment();
                        if (fragment instanceof BuddiesListFragment) {
                            ((BuddiesListFragment) BaseFragment.this).searchedClick("");
                        } else if (fragment instanceof BuddiesBlockedFragment) {
                            ((BuddiesBlockedFragment) BaseFragment.this).searchedClick("");
                        } else if (fragment instanceof PendingRequestFragment) {
                            ((PendingRequestFragment) BaseFragment.this).searchedClick("");
                        } else if (fragment instanceof CoachListFragment) {
                            ((CoachListFragment) BaseFragment.this).searchedClick("");
                        } else if (fragment instanceof PreloadedNutritionChartsFragment) {
                            ((PreloadedNutritionChartsFragment) BaseFragment.this).searchedClick("");
                        } else if (fragment instanceof SelectExercisesFragmentNew) {
                            ((SelectExercisesFragmentNew) BaseFragment.this).onSearchClose();
                        } else {
                            onSearchTextChanged("");
                        }
                    }*/
                    return false;
                }
            });

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                   /* if (getActivity() != null) {
                        Fragment fragment = ((BaseActivity) getActivity()).getCurrentShownFragment();
                        if (fragment instanceof BuddiesListFragment) {
                            ((BuddiesListFragment) BaseFragment.this).searchedClick(newText);
                        } else if (fragment instanceof BuddiesBlockedFragment) {
                            ((BuddiesBlockedFragment) BaseFragment.this).searchedClick(newText);
                        } else if (fragment instanceof PendingRequestFragment) {
                            ((PendingRequestFragment) BaseFragment.this).searchedClick(newText);
                        } else if (fragment instanceof CoachListFragment) {
                            ((CoachListFragment) BaseFragment.this).searchedClick(newText);
                        } else if (fragment instanceof PreloadedNutritionChartsFragment) {
                            ((PreloadedNutritionChartsFragment) BaseFragment.this).searchedClick(newText);
                        } else if (fragment instanceof SelectExercisesFragmentNew) {
                            ((SelectExercisesFragmentNew) BaseFragment.this).searchedClick(newText);
                        } else {
                            onSearchTextChanged(newText);
                        }
                    }*/
                    return false;
                }
            });

            searchView.setMaxWidth(Integer.MAX_VALUE);
        }
    }

    protected void onSearchTextChanged(String query) {
        // TODO: Override this for search
    }


    public SearchView getSearchView() {
        return searchView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("TESTING", "CURRENT FRAGMENT :" + getClass().getSimpleName());
        if (getMenuRes() != -1) {
            setHasOptionsMenu(true);
        }
    }

    public void setToolbarTitle(String title) {
        if (title != null) {
            ((BaseActivity) mContext).setToolbarTitle(title);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (getMenuRes() != -1) {
            inflater.inflate(getMenuRes(), menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mListener != null) {
            return mListener.onToolbarMenuItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @MenuRes
    protected int getMenuRes() {
        return -1;
    }

    protected boolean shouldShowLeftBtn() {
        return false;
    }

    protected int getLeftBtnResId() {
        return -1;
    }

    /**
     * Use this method when we need back press functionality on specific buttons
     * OTPResponseList: From sign in page, sign up will be shown. On going back using back button/tapping on
     * Login, the previous fragment in the backstack should be shown.
     * NOTE: This will work only if the fragment is added in backstack
     */
    protected void pressBackButton() {
        /*BaseActivity activity = (BaseActivity) getActivity();
        if (activity != null) {
            activity.onBackPressed();
            return;
        }*/
        FragmentManager mgr = getFragmentManager();
        if (mgr != null) {
            if (mgr.getBackStackEntryCount() > 0) {
                mgr.popBackStack();
            }
        }
    }

    /**
     * Should be overriden by child fragments to handle back press. If back press is handled at
     * fragment level, this method should return true.
     *
     * @return true if back press is handled by fragment
     */
    protected boolean handleOnBackPress() {
        return false;
    }

    public void replaceFragment(AppCompatActivity activity, BaseFragment fragment, boolean addToBackStack) {
        if (fragment != null && activity != null && activity instanceof BaseActivity) {
            ((BaseActivity) activity).setCurrentFragmentShown(this);
            ((BaseActivity) activity).replaceFragment(fragment, addToBackStack);
        }
    }

    public void showSearchIcon() {
        showSearchIcon = true;
    }

    @Override
    public boolean onToolbarMenuItemSelected(MenuItem item) {
        return false;
    }

    @Override
    public void onToolbarLeftButtonClicked(View view) {

    }




}
