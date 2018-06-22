package org.gratitude.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import org.gratitude.R;
import org.gratitude.data.model.organization.Organization;
import org.gratitude.databinding.OrganizationItemBinding;
import org.gratitude.utils.ImageHandler;

import java.util.ArrayList;
import java.util.List;

public class OrganizationsAdapter extends RecyclerView.Adapter<OrganizationsAdapter.OrganizationHolder>{

    private Context mContext;
    private ArrayList<Organization> mOrganization;

    // Allows to remember the last item shown on screen
    private int lastPosition = -1;

    public OrganizationsAdapter(Context context, List<Organization> orgList) {
        mContext = context;
        mOrganization = new ArrayList<>(orgList);
    }

    @NonNull
    @Override
    public OrganizationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        OrganizationItemBinding mBinding = DataBindingUtil.inflate(inflater,
                R.layout.organization_item, parent, false);

        return new OrganizationHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrganizationHolder holder, int position) {
        holder.bind(position);

        setAnimation(holder.itemView, position);
    }

    public void setOrganizationList(List<Organization> orgList){
        mOrganization.addAll(orgList);
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
    public String toString() {
        return super.toString();
    }

    @Override
    public int getItemCount() {
        return mOrganization != null ? mOrganization.size() :  0;
    }

    public Organization getItem(int position) {
        return mOrganization.get(position);
    }

    public class OrganizationHolder extends RecyclerView.ViewHolder{

        private final OrganizationItemBinding mBinding;

        private OrganizationHolder(OrganizationItemBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        public void bind(int position){
            Organization org = mOrganization.get(position);

            ImageHandler.orgImageHandler(mContext, mBinding.imageView, org);

            mBinding.nameText.setText(org.getName());
            mBinding.missionText.setText(org.getMission());

            String text = String.format(mContext.getString(R.string.project_active_text),
                    String.valueOf(org.getActiveProjects()));
            mBinding.numberText.setText(Html.fromHtml(text));
        }

    }
}
