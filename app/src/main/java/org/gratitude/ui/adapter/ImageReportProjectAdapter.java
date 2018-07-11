package org.gratitude.ui.adapter;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.gratitude.R;
import org.gratitude.data.model.report.Link;
import org.gratitude.databinding.SingleImageReportItemBinding;
import org.gratitude.utils.GlideApp;
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

    public Link getItem(int position) {
        return mList.get(position);
    }

    public class ImageHolder extends RecyclerView.ViewHolder {

        private final SingleImageReportItemBinding mBinding;

        ImageHolder(SingleImageReportItemBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        public void bind(final int position){
            // TODO: associate image list
            ImageHandler.reportImageHandler(mContext, mBinding.singleImage, mList.get(position).getHref());

            mBinding.singleImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showZoomImage(getItem(position).getHref());
                }
            });
        }

        private void showZoomImage(String url) {

            WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;

            final Dialog builder = new Dialog(mContext, R.style.DialogTheme);
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    //nothing;
                }
            });

            ImageView imageView = new ImageView(mContext);

            builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

            GlideApp.with(mContext)
                    .load(url)
                    .dontTransform()
                    .override(width - ((width/100)*30))
                    .into(imageView);
            builder.show();
        }
    }
}
