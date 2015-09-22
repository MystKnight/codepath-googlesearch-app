package com.codepath.googleimagesearch;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yahuijin on 9/13/15.
 */
public class GoogleFilter implements Serializable {

    private int selectedImageSizeIndex;
    private int selectedColorFilterIndex;
    private int selectedImageTypeIndex;
    private String siteFilter;

    public GoogleFilter() {
        this.setDefaults();
    }

    public void setDefaults() {
        this.selectedColorFilterIndex = 0;
        this.selectedImageSizeIndex = 0;
        this.selectedImageTypeIndex = 0;
        this.siteFilter = "";
    }

    public Map<String, String> buildFilters() {
        Map filters = new HashMap<>();

        String imageSize = Utils.getImageSize(this.selectedImageSizeIndex);
        String imageType = Utils.getImageType(this.selectedImageTypeIndex);
        String colorFilter = Utils.getColorFilter(this.selectedColorFilterIndex);
        String siteFilter = this.siteFilter;

        if (!imageSize.equals("")) {
            filters.put("imgsz", imageSize);
        }

        if (!colorFilter.equals("")) {
            filters.put("imgcolor", colorFilter);
        }

        if (!imageType.equals("")) {
            filters.put("imgtype", imageType);
        }

        if (!siteFilter.equals("")) {
            filters.put("as_sitesearch", siteFilter);
        }

        return filters;
    }

    public int getSelectedImageSizeIndex() {
        return selectedImageSizeIndex;
    }

    public void setSelectedImageSizeIndex(int selectedImageSizeIndex) {
        this.selectedImageSizeIndex = selectedImageSizeIndex;
    }

    public int getSelectedColorFilterIndex() {
        return selectedColorFilterIndex;
    }

    public void setSelectedColorFilterIndex(int selectedColorFilterIndex) {
        this.selectedColorFilterIndex = selectedColorFilterIndex;
    }

    public int getSelectedImageTypeIndex() {
        return selectedImageTypeIndex;
    }

    public void setSelectedImageTypeIndex(int selectedImageTypeIndex) {
        this.selectedImageTypeIndex = selectedImageTypeIndex;
    }

    public String getSiteFilter() {
        return siteFilter;
    }

    public void setSiteFilter(String siteFilter) {
        this.siteFilter = siteFilter;
    }
}
