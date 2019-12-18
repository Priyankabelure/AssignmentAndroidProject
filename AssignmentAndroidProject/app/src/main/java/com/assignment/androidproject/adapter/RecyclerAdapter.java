package com.assignment.androidproject.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.assignment.androidproject.R;
import com.assignment.androidproject.model.SingleResponseModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    private RecyclerAdapterListener listener;

    private List<SingleResponseModel> singleResponseModels;
    Context context;
    /*
     * Constructor
     * */
    public RecyclerAdapter(Context context,List<SingleResponseModel> singleResponseModels, RecyclerAdapterListener listener) {
        this.singleResponseModels = singleResponseModels;
        this.listener = listener;
        this.context=context;
    }




    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



        View view = LayoutInflater.from(context).inflate(R.layout.item_row, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        final SingleResponseModel singleResponseModelsInfo = singleResponseModels.get(position);
        holder.txt_title.setText(singleResponseModelsInfo.getTitle());
        holder.txt_description.setText(singleResponseModelsInfo.getDescription());
        String imageUrl = singleResponseModelsInfo.getImageHref();
        Log.e("imageUrl", imageUrl);
        Picasso.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(holder.profile_icon);

    }

    @Override
    public int getItemCount() {
        return singleResponseModels.size();
    }

    public void refreshRecyclerView(List<SingleResponseModel> newList) {

        if (this.singleResponseModels != null) {
            this.singleResponseModels.clear();
            this.singleResponseModels.addAll(newList);
        } else {
            this.singleResponseModels = newList;
        }

        notifyDataSetChanged();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_title)
        TextView txt_title;
        @BindView(R.id.txt_description)
        TextView txt_description;
        @BindView(R.id.profile_icon)
        ImageView profile_icon;

        /*
         * Constructor
         * */
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            /*
             * ButterKnife
             * */
            ButterKnife.bind(this, itemView);
        }
    }

    public interface RecyclerAdapterListener {

        void onClick(SingleResponseModel singleMovie);

    }
}
