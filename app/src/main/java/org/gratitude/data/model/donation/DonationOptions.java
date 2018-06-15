
package org.gratitude.data.model.donation;

import com.google.gson.annotations.Expose;

import java.util.List;

public class DonationOptions {

    @Expose
    private List<DonationOption> donationOption;

    public List<DonationOption> getDonationOption() {
        return donationOption;
    }

    public static class Builder {

        private List<DonationOption> donationOption;

        public DonationOptions.Builder withDonationOption(List<DonationOption> donationOption) {
            this.donationOption = donationOption;
            return this;
        }

        public DonationOptions build() {
            DonationOptions donationOptions = new DonationOptions();
            donationOptions.donationOption = donationOption;
            return donationOptions;
        }

    }

}
