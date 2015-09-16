package com.codepath.googleimagesearch;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.codepath.googleimagesearch.models.GoogleImage;
import com.etsy.android.grid.StaggeredGridView;
import com.etsy.android.grid.util.DynamicHeightImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by yahuijin on 9/10/15.
 */
public class GoogleImageAdapter extends ArrayAdapter<GoogleImage> {

    SparseArray<Double> heightRatios = new SparseArray<>();

    static class ViewHolder {
        DynamicHeightImageView ivPhoto;
    }

    public GoogleImageAdapter(Context context, List<GoogleImage> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GoogleImage googleImage = getItem(position);
        ViewHolder vh;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.image_row, parent, false);

            vh = new ViewHolder();
            vh.ivPhoto = (DynamicHeightImageView)convertView.findViewById(R.id.iv_photo);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder)convertView.getTag();
        }

        double imageRatio = heightRatios.get(position, 0.0);
        if (imageRatio == 0) {
            imageRatio = (double)Integer.parseInt(googleImage.height) / Integer.parseInt(googleImage.width);
            heightRatios.append(position, imageRatio);
        }

        // Compute the new width and height based on how wide our columns are
        StaggeredGridView gv = (StaggeredGridView)parent;
        int width = gv.getColumnWidth();
        double height = imageRatio * width;

        // We need to set the photos so they do not resize on recycle
        vh.ivPhoto.getLayoutParams().height = (int)height;
        vh.ivPhoto.getLayoutParams().width = width;

        Picasso.with(getContext())
                .load(googleImage.unescapedUrl)
                .placeholder(R.color.light_gray)
                .resize(width, (int)height)
                .into(vh.ivPhoto);

        return convertView;
    }
}
