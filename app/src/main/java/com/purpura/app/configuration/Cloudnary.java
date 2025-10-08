package com.purpura.app.configuration;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;

import java.util.HashMap;
import java.util.Map;

public class Cloudnary {
    private final String cloud_name = "dughz83oa";
    private final String uploadProjeto = "Purpura";


    private ActivityResultLauncher<String[]> requestPermission;
    private ActivityResultLauncher<Intent> requestGallery;

    public Cloudnary(Context context) {
        initCloudinary(context);
    }

    private void initCloudinary(Context context) {
        if (MediaManager.get() == null) {
            Map<String, Object> config = new HashMap<>();
            config.put("cloud_name", cloud_name);
            MediaManager.init(context, config);
        }
    }
}
