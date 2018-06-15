
package org.gratitude.data.model.projects;

import com.google.gson.annotations.Expose;

import org.gratitude.data.model.donation.DonationOptions;
import org.gratitude.data.model.image.Image;
import org.gratitude.data.model.organization.Organization;
import org.gratitude.data.model.video.Videos;

@SuppressWarnings("unused")
public class Project {

    @Expose
    private Boolean active;
    @Expose
    private String activities;
    @Expose
    private String additionalDocumentation;
    @Expose
    private String approvedDate;
    @Expose
    private String contactAddress;
    @Expose
    private String contactAddress2;
    @Expose
    private String contactCity;
    @Expose
    private String contactCountry;
    @Expose
    private String contactName;
    @Expose
    private String contactPostal;
    @Expose
    private String contactState;
    @Expose
    private String contactTitle;
    @Expose
    private String contactUrl;
    @Expose
    private String country;
    @Expose
    private DonationOptions donationOptions;
    @Expose
    private Double funding;
    @Expose
    private Long goal;
    @Expose
    private Long id;
    @Expose
    private Image image;
    @Expose
    private Long imageGallerySize;
    @Expose
    private String imageLink;
    @Expose
    private String iso3166CountryCode;
    @Expose
    private Double latitude;
    @Expose
    private String longTermImpact;
    @Expose
    private Double longitude;
    @Expose
    private String need;
    @Expose
    private Long numberOfDonations;
    @Expose
    private Organization organization;
    @Expose
    private String progressReportLink;
    @Expose
    private String projectLink;
    @Expose
    private String region;
    @Expose
    private Double remaining;
    @Expose
    private String status;
    @Expose
    private String summary;
    @Expose
    private String themeName;
    @Expose
    private String title;
    @Expose
    private String type;
    @Expose
    private Videos videos;

    public Boolean getActive() {
        return active;
    }

    public String getActivities() {
        return activities;
    }

    public String getAdditionalDocumentation() {
        return additionalDocumentation;
    }

    public String getApprovedDate() {
        return approvedDate;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public String getContactAddress2() {
        return contactAddress2;
    }

    public String getContactCity() {
        return contactCity;
    }

    public String getContactCountry() {
        return contactCountry;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactPostal() {
        return contactPostal;
    }

    public String getContactState() {
        return contactState;
    }

    public String getContactTitle() {
        return contactTitle;
    }

    public String getContactUrl() {
        return contactUrl;
    }

    public String getCountry() {
        return country;
    }

    public DonationOptions getDonationOptions() {
        return donationOptions;
    }

    public Double getFunding() {
        return funding;
    }

    public Long getGoal() {
        return goal;
    }

    public Long getId() {
        return id;
    }

    public Image getImage() {
        return image;
    }

    public Long getImageGallerySize() {
        return imageGallerySize;
    }

    public String getImageLink() {
        return imageLink;
    }

    public String getIso3166CountryCode() {
        return iso3166CountryCode;
    }

    public Double getLatitude() {
        return latitude;
    }

    public String getLongTermImpact() {
        return longTermImpact;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getNeed() {
        return need;
    }

    public Long getNumberOfDonations() {
        return numberOfDonations;
    }

    public Organization getOrganization() {
        return organization;
    }

    public String getProgressReportLink() {
        return progressReportLink;
    }

    public String getProjectLink() {
        return projectLink;
    }

    public String getRegion() {
        return region;
    }

    public Double getRemaining() {
        return remaining;
    }

    public String getStatus() {
        return status;
    }

    public String getSummary() {
        return summary;
    }

    public String getThemeName() {
        return themeName;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public Videos getVideos() {
        return videos;
    }

    public static class Builder {

        private Boolean active;
        private String activities;
        private String additionalDocumentation;
        private String approvedDate;
        private String contactAddress;
        private String contactAddress2;
        private String contactCity;
        private String contactCountry;
        private String contactName;
        private String contactPostal;
        private String contactState;
        private String contactTitle;
        private String contactUrl;
        private String country;
        private DonationOptions donationOptions;
        private Double funding;
        private Long goal;
        private Long id;
        private Image image;
        private Long imageGallerySize;
        private String imageLink;
        private String iso3166CountryCode;
        private Double latitude;
        private String longTermImpact;
        private Double longitude;
        private String need;
        private Long numberOfDonations;
        private Organization organization;
        private String progressReportLink;
        private String projectLink;
        private String region;
        private Double remaining;
        private String status;
        private String summary;
        private String themeName;
        private String title;
        private String type;
        private Videos videos;

        public Project.Builder withActive(Boolean active) {
            this.active = active;
            return this;
        }

        public Project.Builder withActivities(String activities) {
            this.activities = activities;
            return this;
        }

        public Project.Builder withAdditionalDocumentation(String additionalDocumentation) {
            this.additionalDocumentation = additionalDocumentation;
            return this;
        }

        public Project.Builder withApprovedDate(String approvedDate) {
            this.approvedDate = approvedDate;
            return this;
        }

        public Project.Builder withContactAddress(String contactAddress) {
            this.contactAddress = contactAddress;
            return this;
        }

        public Project.Builder withContactAddress2(String contactAddress2) {
            this.contactAddress2 = contactAddress2;
            return this;
        }

        public Project.Builder withContactCity(String contactCity) {
            this.contactCity = contactCity;
            return this;
        }

        public Project.Builder withContactCountry(String contactCountry) {
            this.contactCountry = contactCountry;
            return this;
        }

        public Project.Builder withContactName(String contactName) {
            this.contactName = contactName;
            return this;
        }

        public Project.Builder withContactPostal(String contactPostal) {
            this.contactPostal = contactPostal;
            return this;
        }

        public Project.Builder withContactState(String contactState) {
            this.contactState = contactState;
            return this;
        }

        public Project.Builder withContactTitle(String contactTitle) {
            this.contactTitle = contactTitle;
            return this;
        }

        public Project.Builder withContactUrl(String contactUrl) {
            this.contactUrl = contactUrl;
            return this;
        }

        public Project.Builder withCountry(String country) {
            this.country = country;
            return this;
        }

        public Project.Builder withDonationOptions(DonationOptions donationOptions) {
            this.donationOptions = donationOptions;
            return this;
        }

        public Project.Builder withFunding(Double funding) {
            this.funding = funding;
            return this;
        }

        public Project.Builder withGoal(Long goal) {
            this.goal = goal;
            return this;
        }

        public Project.Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Project.Builder withImage(Image image) {
            this.image = image;
            return this;
        }

        public Project.Builder withImageGallerySize(Long imageGallerySize) {
            this.imageGallerySize = imageGallerySize;
            return this;
        }

        public Project.Builder withImageLink(String imageLink) {
            this.imageLink = imageLink;
            return this;
        }

        public Project.Builder withIso3166CountryCode(String iso3166CountryCode) {
            this.iso3166CountryCode = iso3166CountryCode;
            return this;
        }

        public Project.Builder withLatitude(Double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Project.Builder withLongTermImpact(String longTermImpact) {
            this.longTermImpact = longTermImpact;
            return this;
        }

        public Project.Builder withLongitude(Double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Project.Builder withNeed(String need) {
            this.need = need;
            return this;
        }

        public Project.Builder withNumberOfDonations(Long numberOfDonations) {
            this.numberOfDonations = numberOfDonations;
            return this;
        }

        public Project.Builder withOrganization(Organization organization) {
            this.organization = organization;
            return this;
        }

        public Project.Builder withProgressReportLink(String progressReportLink) {
            this.progressReportLink = progressReportLink;
            return this;
        }

        public Project.Builder withProjectLink(String projectLink) {
            this.projectLink = projectLink;
            return this;
        }

        public Project.Builder withRegion(String region) {
            this.region = region;
            return this;
        }

        public Project.Builder withRemaining(Double remaining) {
            this.remaining = remaining;
            return this;
        }

        public Project.Builder withStatus(String status) {
            this.status = status;
            return this;
        }

        public Project.Builder withSummary(String summary) {
            this.summary = summary;
            return this;
        }

        public Project.Builder withThemeName(String themeName) {
            this.themeName = themeName;
            return this;
        }

        public Project.Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Project.Builder withType(String type) {
            this.type = type;
            return this;
        }

        public Project.Builder withVideos(Videos videos) {
            this.videos = videos;
            return this;
        }

        public Project build() {
            Project project = new Project();
            project.active = active;
            project.activities = activities;
            project.additionalDocumentation = additionalDocumentation;
            project.approvedDate = approvedDate;
            project.contactAddress = contactAddress;
            project.contactAddress2 = contactAddress2;
            project.contactCity = contactCity;
            project.contactCountry = contactCountry;
            project.contactName = contactName;
            project.contactPostal = contactPostal;
            project.contactState = contactState;
            project.contactTitle = contactTitle;
            project.contactUrl = contactUrl;
            project.country = country;
            project.donationOptions = donationOptions;
            project.funding = funding;
            project.goal = goal;
            project.id = id;
            project.image = image;
            project.imageGallerySize = imageGallerySize;
            project.imageLink = imageLink;
            project.iso3166CountryCode = iso3166CountryCode;
            project.latitude = latitude;
            project.longTermImpact = longTermImpact;
            project.longitude = longitude;
            project.need = need;
            project.numberOfDonations = numberOfDonations;
            project.organization = organization;
            project.progressReportLink = progressReportLink;
            project.projectLink = projectLink;
            project.region = region;
            project.remaining = remaining;
            project.status = status;
            project.summary = summary;
            project.themeName = themeName;
            project.title = title;
            project.type = type;
            project.videos = videos;
            return project;
        }

    }

}
