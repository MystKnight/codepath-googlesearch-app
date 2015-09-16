package com.codepath.googleimagesearch;

/**
 * Created by yahuijin on 9/11/15.
 */
public class Utils {

    public static String getImageSize(int selectedImageSizeIndex) {
        String imageSize;
        switch (selectedImageSizeIndex) {
            case 0:
                imageSize = "";
                break;
            case 1:
                imageSize = "icon";
                break;
            case 2:
                imageSize = "medium";
                break;
            case 3:
                imageSize = "xxlarge";
                break;
            case 4:
                imageSize = "huge";
                break;
            default:
                throw new IllegalArgumentException("Invalid image size index: " + selectedImageSizeIndex);
        }

        return imageSize;
    }

    public static String getColorFilter(int selectedColorFilterIndex) {
        String colorFilter;
        switch (selectedColorFilterIndex) {
            case 0:
                colorFilter = "";
                break;
            case 1:
                colorFilter = "black";
                break;
            case 2:
                colorFilter = "blue";
                break;
            case 3:
                colorFilter = "brown";
                break;
            case 4:
                colorFilter = "gray";
                break;
            case 5:
                colorFilter = "green";
                break;
            case 6:
                colorFilter = "orange";
                break;
            case 7:
                colorFilter = "pink";
                break;
            case 8:
                colorFilter = "purple";
                break;
            case 9:
                colorFilter = "red";
                break;
            case 10:
                colorFilter = "teal";
                break;
            case 11:
                colorFilter = "white";
                break;
            case 12:
                colorFilter = "yellow";
                break;
            default:
                throw new IllegalArgumentException("Invalid color filter index: " + selectedColorFilterIndex);
        }

        return colorFilter;
    }

    public static String getImageType(int selectedImageTypeIndex) {
        String imageType;
        switch (selectedImageTypeIndex) {
            case 0:
                imageType = "";
                break;
            case 1:
                imageType = "face";
                break;
            case 2:
                imageType = "photo";
                break;
            case 3:
                imageType = "clipart";
                break;
            case 5:
                imageType = "lineart";
                break;
            default:
                throw new IllegalArgumentException("Invalid image type index: " + selectedImageTypeIndex);
        }

        return imageType;
    }
}
