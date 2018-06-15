
package org.gratitude.data.model.donation;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class DonationOptions implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.donationOption);
    }

    public DonationOptions() {
    }

    protected DonationOptions(Parcel in) {
        this.donationOption = new ArrayList<DonationOption>();
        in.readList(this.donationOption, DonationOption.class.getClassLoader());
    }

    public static final Parcelable.Creator<DonationOptions> CREATOR = new Parcelable.Creator<DonationOptions>() {
        @Override
        public DonationOptions createFromParcel(Parcel source) {
            return new DonationOptions(source);
        }

        @Override
        public DonationOptions[] newArray(int size) {
            return new DonationOptions[size];
        }
    };
}
