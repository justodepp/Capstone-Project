
package org.gratitude.data.model.response;

import com.google.gson.annotations.Expose;

import org.gratitude.data.model.organization.Organization;

public class OrganizationByBridgeId {

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

}
