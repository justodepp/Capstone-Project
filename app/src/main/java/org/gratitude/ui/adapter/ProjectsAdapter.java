package org.gratitude.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import org.gratitude.R;
import org.gratitude.data.model.projects.Project;
import org.gratitude.databinding.ProjectItemBinding;
import org.gratitude.utils.ImageHandler;

import java.util.ArrayList;
import java.util.List;

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.ProjectHolder>{

    private Context mContext;
    private ArrayList<Project> mProject;

    private final ProjectClickListener mProjectClickListener;

    // Allows to remember the last item shown on screen
    private int lastPosition = -1;

    public interface ProjectClickListener {
        void onClickProjectItem(Project project);
    }

    public ProjectsAdapter(Context context, List<Project> projectList, ProjectClickListener projectClickListener) {
        mContext = context;
        mProject = new ArrayList<>(projectList);
        mProjectClickListener = projectClickListener;
    }

    @NonNull
    @Override
    public ProjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ProjectItemBinding mBinding = DataBindingUtil.inflate(inflater,
                R.layout.project_item, parent, false);

        return new ProjectHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectHolder holder, int position) {
        holder.bind(position);

        setAnimation(holder.itemView, position);
    }

    public void setProjectList(List<Project> projectList){
        mProject.addAll(projectList);
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
        return mProject != null ? mProject.size() :  0;
    }

    public class ProjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final ProjectItemBinding mBinding;

        private ProjectHolder(ProjectItemBinding binding) {
            super(binding.getRoot());
            binding.getRoot().setOnClickListener(this);
            this.mBinding = binding;

            mBinding.projectCard.setOnClickListener(this);
        }

        public void bind(int position){
            Project project = mProject.get(position);

            mBinding.includeHeader.projectTitle.setText(project.getTitle());

            String text = String.format(mContext.getString(R.string.money_raised_text),
                    String.valueOf(project.getFunding()),
                    String.valueOf(project.getGoal()));
            mBinding.includeRaised.moneyRaised.setText(Html.fromHtml(text));

            double result = ((project.getFunding() / project.getGoal()) * 100);
            int progress;
            if(result > 0 && result < 1) {
                progress = 1;
            } else {
                progress = (int) result;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mBinding.includeRaised.moneyProgressBar.setProgress(progress, true);
            } else {
                mBinding.includeRaised.moneyProgressBar.setProgress(progress);
            }

            ImageHandler.imageHandler(mContext, mBinding.includeHeader.projectImageview, project);
        }

        @Override
        public String toString() {
            return super.toString();
        }

        @Override
        public void onClick(View view) {
            int clickPosition = getAdapterPosition();
            mProjectClickListener.onClickProjectItem(mProject.get(clickPosition));
        }
    }
}
