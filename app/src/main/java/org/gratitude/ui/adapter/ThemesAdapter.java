package org.gratitude.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import org.gratitude.R;
import org.gratitude.data.model.themes.Theme;
import org.gratitude.databinding.ThemeItemBinding;

import java.util.ArrayList;
import java.util.List;

public class ThemesAdapter extends RecyclerView.Adapter<ThemesAdapter.ThemeHolder>{

    private Context mContext;
    private ArrayList<Theme> mTheme;

    // Allows to remember the last item shown on screen
    private int lastPosition = -1;

    public ThemesAdapter(Context context, List<Theme> themeList) {
        mContext = context;
        mTheme = new ArrayList<>(themeList);
    }

    @NonNull
    @Override
    public ThemeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ThemeItemBinding mBinding = DataBindingUtil.inflate(inflater,
                R.layout.theme_item, parent, false);

        return new ThemeHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ThemeHolder holder, int position) {
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
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.item_animation_from_right);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return mTheme != null ? mTheme.size() :  0;
    }

    public Theme getItem(int position) {
        return mTheme.get(position);
    }

    public class ThemeHolder extends RecyclerView.ViewHolder {

        private final ThemeItemBinding mBinding;

        private ThemeHolder(ThemeItemBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        private void bind(int position){
            mBinding.themeText.setText(mTheme.get(position).getName());
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
