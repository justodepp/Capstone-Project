
package org.gratitude.data.model.countries;

import com.google.gson.annotations.Expose;

import java.util.List;

public class Countries {

    @Expose
    private List<Country> country;

    public List<Country> getCountry() {
        return country;
    }

    public static class Builder {

        private List<Country> country;

        public Countries.Builder withCountry(List<Country> country) {
            this.country = country;
            return this;
        }

        public Countries build() {
            Countries countries = new Countries();
            countries.country = country;
            return countries;
        }

    }

}
