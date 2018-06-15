
package org.gratitude.data.model.donation;

import com.google.gson.annotations.Expose;

public class DonationOption {

    @Expose
    private Long amount;
    @Expose
    private String description;

    public Long getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public static class Builder {

        private Long amount;
        private String description;

        public DonationOption.Builder withAmount(Long amount) {
            this.amount = amount;
            return this;
        }

        public DonationOption.Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public DonationOption build() {
            DonationOption donationOption = new DonationOption();
            donationOption.amount = amount;
            donationOption.description = description;
            return donationOption;
        }

    }

}
