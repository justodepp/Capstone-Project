
package org.gratitude.data.model.organization;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class Organizations implements Parcelable {

    @Expose
    private Boolean hasNext;
    @Expose
    private Long nextOrgId;
    @Expose
    private List<Organization> organization;

    public Boolean getHasNext() {
        return hasNext;
    }

    public Long getNextOrgId() {
        return nextOrgId;
    }

    public List<Organization> getOrganization() {
        return organization;
    }

    public static class Builder {

        private Boolean hasNext;
        private Long nextOrgId;
        private List<Organization> organization;

        public Organizations.Builder withHasNext(Boolean hasNext) {
            this.hasNext = hasNext;
            return this;
        }

        public Organizations.Builder withNextOrgId(Long nextOrgId) {
            this.nextOrgId = nextOrgId;
            return this;
        }

        public Organizations.Builder withOrganization(List<Organization> organization) {
            this.organization = organization;
            return this;
        }

        public Organizations build() {
            Organizations organizations = new Organizations();
            organizations.hasNext = hasNext;
            organizations.nextOrgId = nextOrgId;
            organizations.organization = organization;
            return organizations;
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.hasNext);
        dest.writeValue(this.nextOrgId);
        dest.writeList(this.organization);
    }

    public Organizations() {
    }

    protected Organizations(Parcel in) {
        this.hasNext = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.nextOrgId = (Long) in.readValue(Long.class.getClassLoader());
        this.organization = new ArrayList<Organization>();
        in.readList(this.organization, Organization.class.getClassLoader());
    }

    public static final Parcelable.Creator<Organizations> CREATOR = new Parcelable.Creator<Organizations>() {
        @Override
        public Organizations createFromParcel(Parcel source) {
            return new Organizations(source);
        }

        @Override
        public Organizations[] newArray(int size) {
            return new Organizations[size];
        }
    };
}
