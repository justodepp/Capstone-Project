
package org.gratitude.data.model.response;

import com.google.gson.annotations.Expose;

import org.gratitude.data.model.organization.Organizations;

public class AllOrganizations {

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

}
