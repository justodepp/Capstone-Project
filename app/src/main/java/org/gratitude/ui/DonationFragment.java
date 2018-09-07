package org.gratitude.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.android.billingclient.api.SkuDetails;

import org.gratitude.R;
import org.gratitude.main.MainActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DonationFragment extends DialogFragment {
    public static final String TAG = "DonationFragment";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
            .setTitle(R.string.donate_title)
            .setMessage(R.string.donate_desc)
            .setNeutralButton(android.R.string.cancel, null);

        final MainActivity activity = (MainActivity) getActivity();

        List<SkuDetails> skus = new ArrayList<>(activity.getSkus());
        Collections.sort(skus, new Comparator<SkuDetails>() {
            @Override
            public int compare(SkuDetails o1, SkuDetails o2) {
                return Long.compare(o1.getPriceAmountMicros(), o2.getPriceAmountMicros());
            }
        });

        for (int i = 0; i < skus.size() && i < 2; i++) {
            final SkuDetails sku = skus.get(i);

            String price = sku.getPrice();
            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    activity.buy(sku);
                }
            };

            if (i == 0) {
                // Cheapest IAP
                builder.setNegativeButton(price, listener);
            } else {
                // More expensive IAP
                builder.setPositiveButton(price, listener);
            }
        }

        return builder.create();
    }
}
