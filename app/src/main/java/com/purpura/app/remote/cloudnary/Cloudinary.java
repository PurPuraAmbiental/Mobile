package com.purpura.app.remote.cloudnary;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;

import java.util.HashMap;
import java.util.Map;

public class Cloudinary {

    private final String cloud_name = "dughz83oa";
    private final String project = "Purpura";
    private boolean initialized = false;

    public void initCloudinary(Context context) {
        try {
            if (!initialized) {
                Map<String, String> config = new HashMap<>();
                config.put("cloud_name", cloud_name);
                MediaManager.init(context.getApplicationContext(), config);
                initialized = true;
            } else {
            }
        } catch (Exception e) {
        }
    }

    public void uploadImage(Context context, Uri imageUri, ImageUploadCallback callback) {
        if (imageUri == null) {
            if (callback != null) callback.onUploadFailure("URI nula");
            return;
        }

        try {
            MediaManager.get().upload(imageUri)
                    .option("folder", project)
                    .unsigned(project)
                    .callback(new UploadCallback() {
                        @Override
                        public void onStart(String requestId) {
                            Log.d("UPLOAD", "Upload iniciado");
                        }

                        @Override
                        public void onProgress(String requestId, long bytes, long totalBytes) {
                            Log.d("UPLOAD", "Enviando imagem...");
                        }

                        @Override
                        public void onSuccess(String requestId, Map resultData) {
                            Log.d("UPLOAD", "Upload concluído com sucesso");
                            String url = (String) resultData.get("secure_url");

                            if (callback != null) callback.onUploadSuccess(url);
                        }

                        @Override
                        public void onError(String requestId, ErrorInfo error) {
                            Log.e("UPLOAD", "Erro: " + error.getDescription());
                            if (callback != null) callback.onUploadFailure(error.getDescription());
                        }

                        @Override
                        public void onReschedule(String requestId, ErrorInfo error) {
                            if (callback != null) callback.onUploadFailure("Reagendado: " + error.getDescription());
                        }
                    })
                    .dispatch(context);
        } catch (Exception e) {
            Log.e("Cloudinary", "Erro no upload: " + e.getMessage());
            if (callback != null) callback.onUploadFailure("Erro no upload: " + e.getMessage());
        }
    }

    public interface ImageUploadCallback {
        void onUploadSuccess(String imageUrl);
        void onUploadFailure(String error);
    }
}
