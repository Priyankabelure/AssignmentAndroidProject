package com.assignment.androidproject.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.assignment.androidproject.R;
import com.assignment.androidproject.adapter.RecyclerAdapter;
import com.assignment.androidproject.base_model.BaseFragment;
import com.assignment.androidproject.model.SingleResponseModel;
import com.assignment.androidproject.viewModel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, RecyclerAdapter.RecyclerAdapterListener {

    @BindView(R.id.swipe_refresh_activity_main)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recycler_activity_main)
    RecyclerView recyclerView;

    /*
            * ViewModel
     * */
    private MainViewModel viewModel;
    /*
            * List for Adapter
     * */
    private List<SingleResponseModel> singleResponseModels;

    /*
            * Adapter for RecyclerView
     * */
    private RecyclerAdapter recyclerAdapter;
    private Context mContext;


    static ListFragment newInstance() {
        ListFragment listFragment = new ListFragment();
        return listFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(@NonNull
                                         LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.list_fragment, container, false);

        /*
         * Bind ButterKnife
         * */

        ButterKnife.bind(this,view);
        return  view;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        /*
         * Request for data from ViewModel
         * */



           /*
         * Request for data from ViewModel
                * */
        viewModel.fetchData();

        /*
         * Observe the LiveData
                * */
        viewModel.getApiObserver().observe((AppCompatActivity)mContext, new Observer<String>() {
            @Override

            public void onChanged(String message) {
                //actionbar.setTitle(message);
                setToolbarTitle(message);
            }
        });

        /*
         * Observe for MovieList
                * */
        viewModel.getMovieListObserver().observe((AppCompatActivity)mContext, new Observer<List<SingleResponseModel>>() {
            @Override
            public void onChanged(List<SingleResponseModel> responseList) {
                if (responseList != null) {

                    if (responseList.size() > 0) {
                        recyclerAdapter.refreshRecyclerView(responseList);
                        swipeRefreshLayout.setRefreshing(false);
                    } else {
                        // TODO Show Empty List Error on UI
                    }
                }
            }
        });
        /*
         * SwipeRefreshLayout
                * */
       swipeRefreshLayout.setOnRefreshListener(this);

        /*
         * Init List
         * */
        singleResponseModels = new ArrayList<>();

        /*
         * Init RecyclerAdapter
         * */

        recyclerAdapter = new RecyclerAdapter(mContext,singleResponseModels, this);

        /*
         * Init RecyclerView
         * */
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(recyclerAdapter);
        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void onRefresh() {

        /*
         * Request for data from ViewModel
                * */
        viewModel.fetchData();

    }

    @Override
    public void onClick(SingleResponseModel singleResponseModel) {

    }
}

