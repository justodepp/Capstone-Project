package org.gratitude.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.DrawableCrossFadeTransition;

import org.gratitude.R;
import org.gratitude.data.model.image.Imagelink;
import org.gratitude.data.model.projects.Project;

import java.util.ArrayList;

public class ImageHandler {

    private final static String IMAGE_SMALL = "small";
    private final static String IMAGE_THUMBNAIL = "thumbnail";
    private final static String IMAGE_MEDIUM = "medium";
    private final static String IMAGE_LARGE = "large";
    private final static String IMAGE_EXTRALARGE = "extraLarge";
    private final static String IMAGE_ORIGINAL = "original";

    /**
     *
     * @param project parent object
     * @param imageType type of image size
     * @return url string if url contains @param imageType
     */
    private static String getUrlImageFromImageLink(Project project, String imageType){
        ArrayList<Imagelink> imagelinkArrayList = new ArrayList<>(project.getImage().getImagelink());
        for (int i = 0; i < imagelinkArrayList.size(); i++) {
            String imageSize = imagelinkArrayList.get(i).getSize();
            if(imageSize.equals(imageType)) {
                return imagelinkArrayList.get(i).getUrl();
            }
        }
        return "";
    }

    private static String getImageUrl(Project project, String imageType) {
        String returnUrl = getUrlImageFromImageLink(project, imageType);
        if(!returnUrl.equals("")) {
            return returnUrl;
        } else {
            return project.getImageLink();
        }
    }


    public static void imageHandler(Context context, ImageView imageView, Project project) {

        RequestOptions requestOptionsThumbnail =  new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter();

        RequestOptions requestOptions =  new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.gg_logo_color)
                .error(R.drawable.gg_logo_color)
                .centerCrop();

        Glide.with(context)
                .load(getImageUrl(project, IMAGE_LARGE))
                .apply(requestOptions)
                .preload();

        Glide.with(context)
                .load(getImageUrl(project, IMAGE_LARGE))
                .apply(requestOptions)
                .thumbnail(
                        Glide.with(context)
                                .load(getImageUrl(project, IMAGE_THUMBNAIL))
                                .apply(requestOptionsThumbnail)
                )
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        target.onResourceReady(resource, new DrawableCrossFadeTransition(600, isFirstResource));
                        return true;
                    }
                })
                .into(imageView).clearOnDetach();
    }
}
