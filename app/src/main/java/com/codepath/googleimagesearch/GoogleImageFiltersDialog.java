package com.codepath.googleimagesearch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by yahuijin on 9/11/15.
 */
public class GoogleImageFiltersDialog extends DialogFragment {

    public GoogleFilter googleFilter = new GoogleFilter();

    public GoogleImageFiltersDialog() {}

    public interface GoogleImageFiltersDialogListener {
        void onFinishDialog(GoogleFilter googleFilter);
    }

    public static GoogleImageFiltersDialog newInstance(String title) {
        GoogleImageFiltersDialog dialog = new GoogleImageFiltersDialog();

        Bundle args = new Bundle();
        args.putString("title", title);

        dialog.setArguments(args);

        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String title = getArguments().getString("title");

        View view = getActivity().getLayoutInflater().inflate(R.layout.image_filter, container);

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Register tap events to all the spinners
        Spinner sImageSizes = (Spinner)view.findViewById(R.id.s_image_size);
        ArrayAdapter<CharSequence> imageSizeAdapter = ArrayAdapter.createFromResource(
                getActivity().getBaseContext(),
                R.array.s_image_size,
                android.R.layout.simple_spinner_item);
        sImageSizes.setAdapter(imageSizeAdapter);
        sImageSizes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                googleFilter.selectedImageSizeIndex = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner sColorFilter = (Spinner)view.findViewById(R.id.s_color_filter);
        ArrayAdapter<CharSequence> colorAdapter = ArrayAdapter.createFromResource(
                getActivity().getBaseContext(),
                R.array.s_color_filter,
                android.R.layout.simple_spinner_item);
        sColorFilter.setAdapter(colorAdapter);
        sColorFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                googleFilter.selectedColorFilterIndex = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner sImageType = (Spinner)view.findViewById(R.id.s_image_type);
        ArrayAdapter<CharSequence> imageTypeAdapter = ArrayAdapter.createFromResource(
                getActivity().getBaseContext(),
                R.array.s_img_type,
                android.R.layout.simple_spinner_item);
        sImageType.setAdapter(imageTypeAdapter);
        sImageType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                googleFilter.selectedImageTypeIndex = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        EditText siteFilter = (EditText)view.findViewById(R.id.et_site_filter);
        googleFilter.siteFilter = siteFilter.getText().toString();

        // Done is pressed
        Button done = (Button)view.findViewById(R.id.btn_done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleImageFiltersDialogListener listener = (GoogleImageFiltersDialogListener) getActivity();
                listener.onFinishDialog(googleFilter);
                getDialog().dismiss();
            }
        });

        Button cancel = (Button)view.findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return view;
    }
}
