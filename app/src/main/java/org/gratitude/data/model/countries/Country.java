
package org.gratitude.data.model.countries;

import com.google.gson.annotations.Expose;

public class Country {

    @Expose
    private String iso3166CountryCode;
    @Expose
    private String name;

    public String getIso3166CountryCode() {
        return iso3166CountryCode;
    }

    public String getName() {
        return name;
    }

    public static class Builder {

        private String iso3166CountryCode;
        private String name;

        public Country.Builder withIso3166CountryCode(String iso3166CountryCode) {
            this.iso3166CountryCode = iso3166CountryCode;
            return this;
        }

        public Country.Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Country build() {
            Country country = new Country();
            country.iso3166CountryCode = iso3166CountryCode;
            country.name = name;
            return country;
        }

    }

}
