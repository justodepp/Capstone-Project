package org.gratitude.data.db;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

public class ProjectPojo implements Parcelable {

    @Expose
    private String activities;
    @Expose
    private Double funding;
    @Expose
    private Long goal;
    @Expose
    private Long id;
    @Expose
    private String imageLink;
    @Expose
    private String longTermImpact;
    @Expose
    private String need;
    @Expose
    private String progressReportLink;
    @Expose
    private String projectLink;
    @Expose
    private String summary;
    @Expose
    private String title;
    @Expose
    private Long prjId;
    @Expose
    private String size;
    @Expose
    private String url;

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getActivities() {
        return activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }

    public Double getFunding() {
        return funding;
    }

    public void setFunding(Double funding) {
        this.funding = funding;
    }

    public Long getGoal() {
        return goal;
    }

    public void setGoal(Long goal) {
        this.goal = goal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLongTermImpact() {
        return longTermImpact;
    }

    public void setLongTermImpact(String longTermImpact) {
        this.longTermImpact = longTermImpact;
    }

    public String getNeed() {
        return need;
    }

    public void setNeed(String need) {
        this.need = need;
    }

    public String getProgressReportLink() {
        return progressReportLink;
    }

    public void setProgressReportLink(String progressReportLink) {
        this.progressReportLink = progressReportLink;
    }

    public String getProjectLink() {
        return projectLink;
    }

    public void setProjectLink(String projectLink) {
        this.projectLink = projectLink;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPrjId() {
        return prjId;
    }

    public void setPrjId(Long prjId) {
        this.prjId = prjId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ProjectPojo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.activities);
        dest.writeValue(this.funding);
        dest.writeValue(this.goal);
        dest.writeValue(this.id);
        dest.writeString(this.imageLink);
        dest.writeString(this.longTermImpact);
        dest.writeString(this.need);
        dest.writeString(this.progressReportLink);
        dest.writeString(this.projectLink);
        dest.writeString(this.summary);
        dest.writeString(this.title);
        dest.writeValue(this.prjId);
        dest.writeString(this.size);
        dest.writeString(this.url);
    }

    protected ProjectPojo(Parcel in) {
        this.activities = in.readString();
        this.funding = (Double) in.readValue(Double.class.getClassLoader());
        this.goal = (Long) in.readValue(Long.class.getClassLoader());
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.imageLink = in.readString();
        this.longTermImpact = in.readString();
        this.need = in.readString();
        this.progressReportLink = in.readString();
        this.projectLink = in.readString();
        this.summary = in.readString();
        this.title = in.readString();
        this.prjId = (Long) in.readValue(Long.class.getClassLoader());
        this.size = in.readString();
        this.url = in.readString();
    }

    public static final Creator<ProjectPojo> CREATOR = new Creator<ProjectPojo>() {
        @Override
        public ProjectPojo createFromParcel(Parcel source) {
            return new ProjectPojo(source);
        }

        @Override
        public ProjectPojo[] newArray(int size) {
            return new ProjectPojo[size];
        }
    };
}
