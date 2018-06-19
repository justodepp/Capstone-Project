package org.gratitude.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import org.gratitude.R;
import org.gratitude.data.model.projects.Project;
import org.gratitude.databinding.ProjectItemBinding;

import java.util.ArrayList;
import java.util.List;

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.ProjectHolder>{

    private Context mContext;
    private ArrayList<Project> mProject;

    private final ProjectClickListener mProjectClickListener;

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
            Glide.with(mContext)
                    .load(project.getImageLink())
                    .into(mBinding.includeHeader.projectImageview);

            mBinding.includeHeader.projectTitle.setText(project.getTitle());

            String text = String.format(mContext.getString(R.string.money_raised_text), project.getFunding(), project.getGoal());
            mBinding.includeRaised.moneyRaised.setText(text);
        }

        @Override
        public void onClick(View view) {
            int clickPosition = getAdapterPosition();
            mProjectClickListener.onClickProjectItem(mProject.get(clickPosition));
        }
    }
}
