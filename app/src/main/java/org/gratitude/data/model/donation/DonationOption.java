
package org.gratitude.data.model.donation;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

public class DonationOption implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.amount);
        dest.writeString(this.description);
    }

    public DonationOption() {
    }

    protected DonationOption(Parcel in) {
        this.amount = (Long) in.readValue(Long.class.getClassLoader());
        this.description = in.readString();
    }

    public static final Parcelable.Creator<DonationOption> CREATOR = new Parcelable.Creator<DonationOption>() {
        @Override
        public DonationOption createFromParcel(Parcel source) {
            return new DonationOption(source);
        }

        @Override
        public DonationOption[] newArray(int size) {
            return new DonationOption[size];
        }
    };
}
