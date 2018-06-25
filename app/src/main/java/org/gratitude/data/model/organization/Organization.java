
package org.gratitude.data.model.organization;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;

import org.gratitude.data.api.ApiHandler;
import org.gratitude.data.api.ApiInterfaces;
import org.gratitude.data.model.countries.Countries;
import org.gratitude.data.model.response.AllOrganizations;
import org.gratitude.data.model.response.OrganizationByBridgeId;
import org.gratitude.data.model.themes.Themes;
import org.gratitude.main.interfaces.ResponseInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class Organization implements Parcelable {

    @Expose
    private Long activeProjects;
    @Expose
    private String addressLine1;
    @Expose
    private String addressLine2;
    @Expose
    private Long bridgeId;
    @Expose
    private String city;
    @Expose
    private Countries countries;
    @Expose
    private String country;
    @Expose
    private String ein;
    @Expose
    private Long id;
    @Expose
    private String iso3166CountryCode;
    @Expose
    private String logoUrl;
    @Expose
    private String mission;
    @Expose
    private String name;
    @Expose
    private String postal;
    @Expose
    private String state;
    @Expose
    private Themes themes;
    @Expose
    private Long totalProjects;
    @Expose
    private String url;

    public Long getActiveProjects() {
        return activeProjects;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public Long getBridgeId() {
        return bridgeId;
    }

    public String getCity() {
        return city;
    }

    public Countries getCountries() {
        return countries;
    }

    public String getCountry() {
        return country;
    }

    public String getEin() {
        return ein;
    }

    public Long getId() {
        return id;
    }

    public String getIso3166CountryCode() {
        return iso3166CountryCode;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public String getMission() {
        return mission;
    }

    public String getName() {
        return name;
    }

    public String getPostal() {
        return postal;
    }

    public String getState() {
        return state;
    }

    public Themes getThemes() {
        return themes;
    }

    public Long getTotalProjects() {
        return totalProjects;
    }

    public String getUrl() {
        return url;
    }

    public static class Builder {

        private Long activeProjects;
        private String addressLine1;
        private String addressLine2;
        private Long bridgeId;
        private String city;
        private Countries countries;
        private String country;
        private String ein;
        private Long id;
        private String iso3166CountryCode;
        private String logoUrl;
        private String mission;
        private String name;
        private String postal;
        private String state;
        private Themes themes;
        private Long totalProjects;
        private String url;

        public Organization.Builder withActiveProjects(Long activeProjects) {
            this.activeProjects = activeProjects;
            return this;
        }

        public Organization.Builder withAddressLine1(String addressLine1) {
            this.addressLine1 = addressLine1;
            return this;
        }

        public Organization.Builder withAddressLine2(String addressLine2) {
            this.addressLine2 = addressLine2;
            return this;
        }

        public Organization.Builder withBridgeId(Long bridgeId) {
            this.bridgeId = bridgeId;
            return this;
        }

        public Organization.Builder withCity(String city) {
            this.city = city;
            return this;
        }

        public Organization.Builder withCountries(Countries countries) {
            this.countries = countries;
            return this;
        }

        public Organization.Builder withCountry(String country) {
            this.country = country;
            return this;
        }

        public Organization.Builder withEin(String ein) {
            this.ein = ein;
            return this;
        }

        public Organization.Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Organization.Builder withIso3166CountryCode(String iso3166CountryCode) {
            this.iso3166CountryCode = iso3166CountryCode;
            return this;
        }

        public Organization.Builder withLogoUrl(String logoUrl) {
            this.logoUrl = logoUrl;
            return this;
        }

        public Organization.Builder withMission(String mission) {
            this.mission = mission;
            return this;
        }

        public Organization.Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Organization.Builder withPostal(String postal) {
            this.postal = postal;
            return this;
        }

        public Organization.Builder withState(String state) {
            this.state = state;
            return this;
        }

        public Organization.Builder withThemes(Themes themes) {
            this.themes = themes;
            return this;
        }

        public Organization.Builder withTotalProjects(Long totalProjects) {
            this.totalProjects = totalProjects;
            return this;
        }

        public Organization.Builder withUrl(String url) {
            this.url = url;
            return this;
        }

        public Organization build() {
            Organization organization = new Organization();
            organization.activeProjects = activeProjects;
            organization.addressLine1 = addressLine1;
            organization.addressLine2 = addressLine2;
            organization.bridgeId = bridgeId;
            organization.city = city;
            organization.countries = countries;
            organization.country = country;
            organization.ein = ein;
            organization.id = id;
            organization.iso3166CountryCode = iso3166CountryCode;
            organization.logoUrl = logoUrl;
            organization.mission = mission;
            organization.name = name;
            organization.postal = postal;
            organization.state = state;
            organization.themes = themes;
            organization.totalProjects = totalProjects;
            organization.url = url;
            return organization;
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.activeProjects);
        dest.writeString(this.addressLine1);
        dest.writeString(this.addressLine2);
        dest.writeValue(this.bridgeId);
        dest.writeString(this.city);
        dest.writeParcelable(this.countries, flags);
        dest.writeString(this.country);
        dest.writeString(this.ein);
        dest.writeValue(this.id);
        dest.writeString(this.iso3166CountryCode);
        dest.writeString(this.logoUrl);
        dest.writeString(this.mission);
        dest.writeString(this.name);
        dest.writeString(this.postal);
        dest.writeString(this.state);
        dest.writeParcelable(this.themes, flags);
        dest.writeValue(this.totalProjects);
        dest.writeString(this.url);
    }

    public Organization() {
    }

    protected Organization(Parcel in) {
        this.activeProjects = (Long) in.readValue(Long.class.getClassLoader());
        this.addressLine1 = in.readString();
        this.addressLine2 = in.readString();
        this.bridgeId = (Long) in.readValue(Long.class.getClassLoader());
        this.city = in.readString();
        this.countries = in.readParcelable(Countries.class.getClassLoader());
        this.country = in.readString();
        this.ein = in.readString();
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.iso3166CountryCode = in.readString();
        this.logoUrl = in.readString();
        this.mission = in.readString();
        this.name = in.readString();
        this.postal = in.readString();
        this.state = in.readString();
        this.themes = in.readParcelable(Themes.class.getClassLoader());
        this.totalProjects = (Long) in.readValue(Long.class.getClassLoader());
        this.url = in.readString();
    }

    public static final Parcelable.Creator<Organization> CREATOR = new Parcelable.Creator<Organization>() {
        @Override
        public Organization createFromParcel(Parcel source) {
            return new Organization(source);
        }

        @Override
        public Organization[] newArray(int size) {
            return new Organization[size];
        }
    };

    public static void getOrganizations(Context context, Long nextOrgId, final ResponseInterface<Organizations> responseInterface) {
        ApiInterfaces apiService = ApiHandler.getApiService(context, false);
        Call<AllOrganizations> responseOrg = apiService.getOrganizations(nextOrgId);

        responseOrg.enqueue(new Callback<AllOrganizations>() {
            @Override
            public void onResponse(@NonNull Call<AllOrganizations> call, @NonNull Response<AllOrganizations> response) {
                Timber.d(response.toString());
                Organizations organizations = new AllOrganizations.Builder()
                        .withOrganizations(response.body().getOrganizations())
                        .build()
                        .getOrganizations();
                responseInterface.onResponseLoaded(organizations);
            }

            @Override
            public void onFailure(@NonNull Call<AllOrganizations> call, @NonNull Throwable t) {
                Timber.e(t);
            }
        });
    }

    public static void getOrganizationByBridgeId(Context context, String bridgeId, final ResponseInterface<Organization> responseInterface) {
        ApiInterfaces apiService = ApiHandler.getApiService(context, false);
        Call<OrganizationByBridgeId> responseOrg = apiService.getOrganizationByBridgeId(bridgeId);

        responseOrg.enqueue(new Callback<OrganizationByBridgeId>() {
            @Override
            public void onResponse(@NonNull Call<OrganizationByBridgeId> call, @NonNull Response<OrganizationByBridgeId> response) {
                Timber.d(response.toString());
                Organization organization = new OrganizationByBridgeId.Builder()
                        .withOrganization(response.body().getOrganization())
                        .build()
                        .getOrganization();
                responseInterface.onResponseLoaded(organization);
            }

            @Override
            public void onFailure(@NonNull Call<OrganizationByBridgeId> call, @NonNull Throwable t) {
                Timber.e(t);
            }
        });
    }
}
