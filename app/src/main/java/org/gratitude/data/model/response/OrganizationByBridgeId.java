
package org.gratitude.data.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import org.gratitude.data.model.organization.Organization;

public class OrganizationByBridgeId implements Parcelable {

    @Expose
    private Organization organization;

    public Organization getOrganization() {
        return organization;
    }

    public static class Builder {

        private Organization organization;

        public OrganizationByBridgeId.Builder withOrganization(Organization organization) {
            this.organization = organization;
            return this;
        }

        public OrganizationByBridgeId build() {
            OrganizationByBridgeId organizationByBridgeId = new OrganizationByBridgeId();
            organizationByBridgeId.organization = organization;
            return organizationByBridgeId;
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.organization, flags);
    }

    public OrganizationByBridgeId() {
    }

    protected OrganizationByBridgeId(Parcel in) {
        this.organization = in.readParcelable(Organization.class.getClassLoader());
    }

    public static final Parcelable.Creator<OrganizationByBridgeId> CREATOR = new Parcelable.Creator<OrganizationByBridgeId>() {
        @Override
        public OrganizationByBridgeId createFromParcel(Parcel source) {
            return new OrganizationByBridgeId(source);
        }

        @Override
        public OrganizationByBridgeId[] newArray(int size) {
            return new OrganizationByBridgeId[size];
        }
    };
}
