package org.gratitude.ui.adapter;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.gratitude.R;
import org.gratitude.data.model.report.Link;
import org.gratitude.databinding.SingleImageReportItemBinding;
import org.gratitude.utils.ImageHandler;

import java.util.ArrayList;
import java.util.List;

public class ImageReportProjectAdapter extends RecyclerView.Adapter<ImageReportProjectAdapter.ImageHolder>{

    private ArrayList<Link> mList;
    private Context mContext;

    public ImageReportProjectAdapter(Context context, List<Link> imageList){
        this.mContext = context;
        this.mList = new ArrayList<>(imageList);
    }

    @NonNull
    @Override
    public ImageReportProjectAdapter.ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SingleImageReportItemBinding mBinding = DataBindingUtil.inflate(inflater,
                R.layout.single_image_report_item, parent, false);

        return new ImageHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() :  0;
    }

    public class ImageHolder extends RecyclerView.ViewHolder {

        private final SingleImageReportItemBinding mBinding;

        ImageHolder(SingleImageReportItemBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        public void bind(int position){
            // TODO: associate image list
            ImageHandler.reportImageHandler(mContext, mBinding.singleImage, mList.get(position).getHref());
        }
    }
}
