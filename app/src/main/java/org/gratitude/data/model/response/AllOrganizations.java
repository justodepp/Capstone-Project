
package org.gratitude.data.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import org.gratitude.data.model.organization.Organizations;

public class AllOrganizations implements Parcelable {

    @Expose
    private Organizations organizations;

    public Organizations getOrganizations() {
        return organizations;
    }

    public static class Builder {

        private Organizations organizations;

        public AllOrganizations.Builder withOrganizations(Organizations organizations) {
            this.organizations = organizations;
            return this;
        }

        public AllOrganizations build() {
            AllOrganizations allOrganization = new AllOrganizations();
            allOrganization.organizations = organizations;
            return allOrganization;
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.organizations, flags);
    }

    public AllOrganizations() {
    }

    protected AllOrganizations(Parcel in) {
        this.organizations = in.readParcelable(Organizations.class.getClassLoader());
    }

    public static final Parcelable.Creator<AllOrganizations> CREATOR = new Parcelable.Creator<AllOrganizations>() {
        @Override
        public AllOrganizations createFromParcel(Parcel source) {
            return new AllOrganizations(source);
        }

        @Override
        public AllOrganizations[] newArray(int size) {
            return new AllOrganizations[size];
        }
    };
}
