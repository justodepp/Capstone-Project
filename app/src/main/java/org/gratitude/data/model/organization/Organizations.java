
package org.gratitude.data.model.organization;

import com.google.gson.annotations.Expose;

import java.util.List;

public class Organizations {

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

}
