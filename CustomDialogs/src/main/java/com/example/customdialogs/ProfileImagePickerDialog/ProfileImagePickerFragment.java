package com.example.customdialogs.ProfileImagePickerDialog;

import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.customdialogs.R;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfileImagePickerFragment extends Fragment {

    private static final String PROFILE_IMAGE = "ProfileImage";
    private static final int CAMERA_PERMISSION_CODE = 100;

    private ShapeableImageView profile_image_picker_BTN;
    private boolean isPickImageFromGalleryRequested = false;
    private ActivityResultLauncher<Intent> resultLauncher;

    private Uri imageUri;
    private Context context;
    private String packageName;
    private int customUserImage;
    private SharedPreferences sharedPreferences;

    public ProfileImagePickerFragment(Context context, String packageName, int customUserImage) {
        this.context = context;
        this.packageName = packageName;
        this.customUserImage = customUserImage;
        sharedPreferences = context.getSharedPreferences(PROFILE_IMAGE, Context.MODE_PRIVATE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_image_picker, container, false);

        findViews(view);
        intViews();
        registerResult(context);
        setupButton();

        loadSavedImageUri(); // Load saved URI

        return view;
    }

    private void findViews(View view) {
        profile_image_picker_BTN = view.findViewById(R.id.profile_image_picker_BTN);
    }

    private void intViews() {
        if(customUserImage != -1)
            profile_image_picker_BTN.setImageResource(customUserImage);
    }

    private void setupButton() {
        profile_image_picker_BTN.setOnClickListener(v -> showImageSourceDialog());
    }

    private void showImageSourceDialog() {
        String[] options = {"Choose from Gallery", "Take Photo"};
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Select Image Source");
        builder.setItems(options, (dialog, which) -> {
            if (which == 0) {
                isPickImageFromGalleryRequested = true;
                pickImage();
            } else if (which == 1) {
                isPickImageFromGalleryRequested = false;
                captureImage();
            }
        });
        builder.show();
    }

    private void pickImage() {
        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        resultLauncher.launch(intent);
    }

    private void captureImage() {
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        } else {
            // Create a file for the full-resolution image
            imageUri = createImageFileUri();
            if (imageUri != null) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); // Pass the file URI
                resultLauncher.launch(intent);
            }
        }
    }

    private Uri createImageFileUri() {
        File storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File imageFile = new File(storageDir, "IMG_" + timeStamp + ".jpg");
        Uri uri = null;
        try {
            if (imageFile.exists() || imageFile.createNewFile()) {
                uri = FileProvider.getUriForFile(requireContext(),
                        packageName+".fileProvider", imageFile);
            }
        } catch (IOException e) {
            Log.e("ProfileImagePicker", "Error creating image file", e);
        }
        return uri;
    }

    private void registerResult(Context context) {
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == android.app.Activity.RESULT_OK) {
                        // if the result code indicates that the activity completed successfully
                        Intent data = result.getData();

                        if ((data != null && data.getAction() == null && imageUri == null) || isPickImageFromGalleryRequested) {
                            //the action of the intent is null (indicating it's not a special action like ACTION_IMAGE_CAPTURE)
                            // Image picked from gallery
                            imageUri = data.getData();
                            if (imageUri != null) {
                                profile_image_picker_BTN.setImageURI(imageUri);
                                saveImageUri(imageUri); // Save the URI
                            } else {
                                Toast.makeText(context, "Failed to load image from gallery", Toast.LENGTH_SHORT).show();
                            }

                        } else if (imageUri != null || !isPickImageFromGalleryRequested) {
                            // Image captured from camera (imageUri already contains the Uri)
                            profile_image_picker_BTN.setImageURI(imageUri);
                            saveImageUri(imageUri); // Save the URI
                        } else {
                            Toast.makeText(context, "No Image Selected/Captured", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "No Image Selected/Captured", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveImageUri(Uri imageUri) {
        Uri persistentUri = saveImageToInternalStorage(imageUri);
        if (persistentUri != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("imageUri", persistentUri.toString());
            editor.apply();
        }
    }

    private Uri saveImageToInternalStorage(Uri sourceUri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), sourceUri);
            File storageDir = requireContext().getFilesDir();
            String fileName = "profile_image.jpg";
            File imageFile = new File(storageDir, fileName);

            try (FileOutputStream out = new FileOutputStream(imageFile)) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            }

            // Save orientation information
            int orientation = getImageOrientation(sourceUri);
            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
            exif.setAttribute(ExifInterface.TAG_ORIENTATION, String.valueOf(orientation));
            exif.saveAttributes();
            return Uri.fromFile(imageFile);
        }
        catch (IOException e) {
            Log.e("ProfileImagePicker", "Error saving image", e);
            return null;
        }
    }

    private void loadSavedImageUri() {
        String imageUriString = sharedPreferences.getString("imageUri", null);
        if (imageUriString != null) {
            imageUri = Uri.parse(imageUriString);
            if (imageUri != null) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), imageUri);
                    int orientation = getImageOrientation(imageUri);
                    Bitmap rotatedBitmap = rotateBitmap(bitmap, orientation);
                    profile_image_picker_BTN.setImageBitmap(rotatedBitmap);
                } catch (IOException e) {
                    Log.e("ProfileImagePicker", "Error loading image", e);
                }
            } else {
                Log.e("ProfileImagePicker", "Loaded URI is null");
            }
        } else {
            Log.e("ProfileImagePicker", "No URI found in SharedPreferences");
        }
    }

    private Bitmap rotateBitmap(Bitmap bitmap, int orientation) {
        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.postRotate(90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.postRotate(180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.postRotate(270);
                break;
            default:
                return bitmap;
        }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
    private int getImageOrientation(Uri imageUri) {
        try {
            InputStream inputStream = requireContext().getContentResolver().openInputStream(imageUri);
            //EXIF = tag that contains a data about the orientation of the image
            ExifInterface exif = new ExifInterface(inputStream);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            inputStream.close();
            return orientation;
        } catch (IOException e) {
            Log.e("ProfileImagePicker", "Error getting image orientation", e);
            return ExifInterface.ORIENTATION_NORMAL;
        }
    }

}