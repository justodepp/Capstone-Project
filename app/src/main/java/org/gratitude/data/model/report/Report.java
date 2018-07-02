
package org.gratitude.data.model.report;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;

import org.gratitude.data.api.ApiHandler;
import org.gratitude.data.api.ApiInterfaces;
import org.gratitude.main.interfaces.ResponseInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class Report implements Parcelable {

    @Expose
    private Attributes attributes;
    @Expose
    private List<Author> authors;
    @Expose
    private List<Entry> entries;
    @Expose
    private String id;
    @Expose
    private List<Link> links;
    @Expose
    private String title;

    public Attributes getAttributes() {
        return attributes;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public String getId() {
        return id;
    }

    public List<Link> getLinks() {
        return links;
    }

    public String getTitle() {
        return title;
    }

    public static class Builder {

        private Attributes attributes;
        private List<Author> authors;
        private List<Entry> entries;
        private String id;
        private List<Link> links;
        private String title;

        public Report.Builder withAttributes(Attributes attributes) {
            this.attributes = attributes;
            return this;
        }

        public Report.Builder withAuthors(List<Author> authors) {
            this.authors = authors;
            return this;
        }

        public Report.Builder withEntries(List<Entry> entries) {
            this.entries = entries;
            return this;
        }

        public Report.Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Report.Builder withLinks(List<Link> links) {
            this.links = links;
            return this;
        }

        public Report.Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Report build() {
            Report report = new Report();
            report.attributes = attributes;
            report.authors = authors;
            report.entries = entries;
            report.id = id;
            report.links = links;
            report.title = title;
            return report;
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.attributes, flags);
        dest.writeList(this.authors);
        dest.writeList(this.entries);
        dest.writeString(this.id);
        dest.writeList(this.links);
        dest.writeString(this.title);
    }

    public Report() {
    }

    protected Report(Parcel in) {
        this.attributes = in.readParcelable(Attributes.class.getClassLoader());
        this.authors = new ArrayList<Author>();
        in.readList(this.authors, Author.class.getClassLoader());
        this.entries = new ArrayList<Entry>();
        in.readList(this.entries, Entry.class.getClassLoader());
        this.id = in.readString();
        this.links = new ArrayList<Link>();
        in.readList(this.links, Link.class.getClassLoader());
        this.title = in.readString();
    }

    public static final Parcelable.Creator<Report> CREATOR = new Parcelable.Creator<Report>() {
        @Override
        public Report createFromParcel(Parcel source) {
            return new Report(source);
        }

        @Override
        public Report[] newArray(int size) {
            return new Report[size];
        }
    };

    public static void getReport(Context context, Long projectId, final ResponseInterface<Report> responseInterface) {
        ApiInterfaces apiService = ApiHandler.getApiService(context, false);
        Call<Report> responseReport = apiService.getProjectReport(projectId);

        responseReport.enqueue(new Callback<Report>() {
            @Override
            public void onResponse(@NonNull Call<Report> call, @NonNull Response<Report> response) {
                Timber.d(response.toString());

                Report report = new Report.Builder()
                        .withAttributes(response.body().getAttributes())
                        .withAuthors(response.body().getAuthors())
                        .withEntries(response.body().getEntries())
                        .withId(response.body().getId())
                        .withLinks(response.body().getLinks())
                        .withTitle(response.body().getTitle())
                        .build();

                responseInterface.onResponseLoaded(report);
            }

            @Override
            public void onFailure(@NonNull Call<Report> call, @NonNull Throwable t) {
                Timber.e(t);
            }
        });
    }
}
