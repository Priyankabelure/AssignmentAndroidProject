package com.assignment.androidproject.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.net.Uri;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.assignment.androidproject.R;
import com.assignment.androidproject.model.SingleResponseModel;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    private RecyclerAdapterListener listener;

    private List<SingleResponseModel> singleResponseModels;
    Context context;
    int finalHeight=0, finalWidth=0;
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

        SingleResponseModel singleResponseModelsInfo = singleResponseModels.get(position);
        holder.txt_title.setText(singleResponseModelsInfo.getTitle());
        if(singleResponseModelsInfo.getDescription()==null)
        {
            holder.txt_description.setVisibility(View.GONE);
        }
        holder.txt_description.setText(singleResponseModelsInfo.getDescription());
        String imageUrl = singleResponseModelsInfo.getImageHref();





        if((singleResponseModelsInfo.getDescription()!=null)  ) {

            final ViewTreeObserver vto = holder.profile_icon.getViewTreeObserver();
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {

                    holder.profile_icon.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    finalHeight = holder.profile_icon.getMeasuredHeight();
                    finalWidth = holder.profile_icon.getMeasuredWidth();
                    /**
                     * Get the text
                     */
                    String plainText = singleResponseModelsInfo.getDescription();
                    Spanned htmlText = Html.fromHtml(plainText);
                    SpannableString mSpannableString = new SpannableString(htmlText);

                    int allTextStart = 0;
                    int allTextEnd = htmlText.length() - 1;

                    /**
                     * Calculate the lines number = image height.
                     * You can improve it... it is just an example
                     */
                    int lines;
                    Rect bounds = new Rect();
                    holder.txt_description.getPaint().getTextBounds(plainText.substring(0, 10), 0, 1, bounds);

                    //float textLineHeight = mTextView.getPaint().getTextSize();
                    float fontSpacing = holder.txt_description.getPaint().getFontSpacing();
                    lines = (int) (finalHeight / (fontSpacing));

                    /**
                     * Build the layout with LeadingMarginSpan2
                     */
                    MyLeadingMarginSpan2 span = new MyLeadingMarginSpan2(lines, finalWidth + 10);
                    mSpannableString.setSpan(span, allTextStart, allTextEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                    holder.txt_description.setText(mSpannableString);
                }
            });

        }
        Glide.with(context)
                .load(imageUrl)
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
