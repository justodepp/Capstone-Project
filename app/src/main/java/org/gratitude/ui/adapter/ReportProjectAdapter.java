package org.gratitude.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import org.gratitude.R;
import org.gratitude.data.model.report.Entry;
import org.gratitude.databinding.ProjectItemReportBinding;
import org.gratitude.utils.Utility;

import java.util.ArrayList;
import java.util.List;

public class ReportProjectAdapter extends RecyclerView.Adapter<ReportProjectAdapter.ReportHolder>{

    private Context mContext;
    private ArrayList<Entry> mEntry;

    private ImageReportProjectAdapter mImageAdapter;

    // Allows to remember the last item shown on screen
    private int lastPosition = -1;

    public ReportProjectAdapter(Context context, List<Entry> entryList) {
        mContext = context;
        mEntry = new ArrayList<>(entryList);
    }

    @NonNull
    @Override
    public ReportProjectAdapter.ReportHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ProjectItemReportBinding mBinding = DataBindingUtil.inflate(inflater,
                R.layout.project_item_report, parent, false);

        return new ReportProjectAdapter.ReportHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportProjectAdapter.ReportHolder holder, int position) {
        holder.bind(position);

        setAnimation(holder.itemView, position);
    }

    /**
     * Here is the key method to apply the animation
     */
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.item_animation_from_bottom);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return mEntry != null ? mEntry.size() :  0;
    }

    public Entry getItem(int position) {
        return mEntry.get(position);
    }

    public class ReportHolder extends RecyclerView.ViewHolder{

        private final ProjectItemReportBinding mBinding;

        private ReportHolder(ProjectItemReportBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;

            mBinding.layoutImage.rvImage.setLayoutManager(new LinearLayoutManager(mContext,
                    LinearLayoutManager.HORIZONTAL,
                    false));
        }

        public void bind(int position){
            mBinding.reportTitle.setText(mEntry.get(position).getTitle());
            String text = Utility.reformatDate(mEntry.get(position).getPublished()) + " - " + mEntry.get(position).getStringListAuthor();
            mBinding.reportAuthor.setText(text);
            mBinding.reportContent.setText(Html.fromHtml(mEntry.get(position).getContent()));

            mBinding.layoutImage.rvImage.hasFixedSize();
            if(mEntry.get(position).getLinks() != null) {
                mImageAdapter = new ImageReportProjectAdapter(mContext, mEntry.get(position).getLinks());
                mBinding.layoutImage.rvImage.setAdapter(mImageAdapter);
            }
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
