# CustomRegisterLibrary
The CustomDialogs Library for Android provides a set of customizable dialog fragments designed to streamline common app interactions. This library includes:

**Registration DialogFragment:** A customizable registration dialog that allows developers to easily collect user details like name, email, and password with built-in validity checks.

**Rating App Dialog Fragment:** A dialog that prompts users to rate the app, with an option to redirect them to the Google Play Store for ratings above a certain rank.

**Profile Image Picker Fragment:** A view that enables users to select a profile image from their gallery or capture a new photo, with customizable dimensions.

## Setup
Step 1. Add it in your root build.gradle at the end of repositories:
```
allprojects {
    repositories {
        // other repositories
        maven { url 'https://jitpack.io' }
    }
}
```

Step2. Add the dependency:
```
dependencies {
  implementation(project(":CustomDialogs"))
}
```

## Usage
### Registration DialogFragment
#### Features:
**1) Customizable** Registration Options: Allows developers to add various registration fields such as Name, Last Name, Email, Password, Phone Number, City, and Address.

**2) Validation:** Built-in validation checks for each registration option, ensuring data integrity and accuracy (e.g., email format, password strength, etc.).

**3) Customizable UI:** Developers can customize the dialog's appearance, including the title, button text, and button color.

**4) Callback Mechanism:** Provides a callback interface (OnRegistrationCompleteListener) to notify when the registration process is successfully completed.

**5) Dark mode support:** The dialog automatically adapts its color scheme for dark mode (only by using the default color). 

#### How to use it:

**1. Add a Frame Layout to the Activity's XML Layout**:
```
<!-- res/layout/activity_main.xml -->
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <!-- Other UI elements -->

    <!-- FrameLayout to hold the RegistrationDialogFragment -->
    <FrameLayout
        android:id="@+id/frame_layout_container"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"/>

</RelativeLayout>
``` 

**2. Initialize RegistrationDialogBuilder:** Create an instance of "RegistrationDialogBuilder" in your activity or fragment.
```
RegistrationDialogBuilder builder = new RegistrationDialogBuilder(this, getSupportFragmentManager(), R.id.frame_layout_container);
```

**3. Add Registration Options:** Add desired registration options using "addXXXOption" methods.
```
builder.addNameOption(R.drawable.user, "Enter your name");
builder.addEmailOption(R.drawable.ic_mail, "Enter your email");
// Add more options as needed

```
**4. Customize the Dialog:** You can customize the dialog’s button text, title, and color:
```
builder.setButtonText("Sign Up");
builder.setDialogRegisterTitle("Sign up to create account");
builder.setDialogColor(Color.parseColor("#FF8A80"));
```

**5. Set Completion Listener:** Set a listener to handle registration completion
```
builder.setOnRegistrationCompleteListener(new OnRegistrationCompleteListener() {
    @Override
    public void onRegistrationComplete() {
        // Handle registration completion
    }
});
```
**6. Show Registration Fragment:** Display the registration fragment by calling show().
```
builder.showRegistrationDialog();
```

### Rating App DialogFragment

#### Features:

**1) Customizable Dialog:** The dialog's appearance can be customized with a color specified by the developer.

**2) User Rating System:** Users can rate the app using a star rating system, with a flexible threshold to trigger the app store redirection.

**3) Callbacks:**
- Receive feedback when a rating is submitted through the RatingAppDialogCallback.
- Trigger an action when redirecting to the app store via the RatingAppOnStoreDialogCallback.
  
**4) Dynamic Icons:** The dialog updates icons based on the user’s rating, providing instant visual feedback.

**5) Dark mode support:** The dialog automatically adapts its color scheme for dark mode (only by using the default color).

#### How to use it:

**1) Create an Instance of RatingAppDialogBuilder:** Initialize a RatingAppDialogBuilder with the necessary parameters, such as Context, FragmentManager, package name, and others.
```
String testPackageName = "your_package_name"; 
RatingAppDialogBuilder ratingAppDialogBuilder = new RatingAppDialogBuilder(
        this, //Context
        getSupportFragmentManager(), //FragmentManager
        testPackageName, //Your package name
        4,  // Stars limit to direct to Play Store, if you put -1 there will be no reference to the google store 
        Color.parseColor("#9B0000"),  // Custom dialog color, for default color put -1
        new RatingAppDialogCallback() { // Callback for Handle user rating submission
            @Override
            public void onRatingSubmitted(float rating) {
                // YOUR CODE
            }
        },
        new RatingAppOnStoreDialogCallback() { //Callback for Handle Google store reference
            @Override
            public void onStoreReference() {
                // YOUR CODE
            }
        }
);
```

**2) Show the Rating Dialog:**
```
ratingAppDialogBuilder.showRatingDialog();
```

### Profile Image Picker Fragment

#### Features: 

**1) Customizable Profile Image:** Allows developers to set a custom placeholder image for the profile picture.

**2) Image Source Options:** Users can select their profile image from the gallery or capture a new photo using the camera.

**3) Persistent Image Storage:**  The selected or captured image is saved locally, and the URI is stored in *SharedPreferences* for persistent use.

#### How to use it

 **1. Setup Permissions and Provider in AndroidManifest.xml:**
```
<!-- Camera and External Storage Permissions -->
<uses-feature
    android:name="android.hardware.camera"
    android:required="false" />
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />

<!-- Provider -->
<provider
    android:name="androidx.core.content.FileProvider"
    android:authorities="your.package.name.fileProvider"
    android:exported="false"
    android:grantUriPermissions="true">
    <meta-data
        android:name="android.support.FILE_PROVIDER_PATHS"
        android:resource="@xml/file_paths" />
</provider>
```

**2. Define File Paths in res/xml/file_paths.xml:**
```
<?xml version="1.0" encoding="utf-8"?>
<paths>
    <external-files-path name="images" path="Pictures/" />
</paths>
```

**3. Add a Container in Your Layout:**
Notice: For a better display the layout_width should be equal to the layout_height
```
FrameLayout
    android:id="@+id/frame_layout_container"
    android:layout_width="300dp"
    android:layout_height="300dp"
    android:layout_centerInParent="true"
    android:foregroundGravity="center"
    android:padding="25dp"
    android:layout_marginTop="16dp"/>
```

**4: Integrate ProfileImagePickerFragment in Your Activity:**
```
// Inside your activity or fragment
ProfileImagePickerFragmentBuilder builder = new ProfileImagePickerFragmentBuilder(
    this, //Context 
    getSupportFragmentManager(), //FragmentManager 
    R.id.frame_layout_container, //Container
    getPackageName(), //Package name
    -1 // Pass your initial custom image resource ID or -1 for use the default image
);

builder.showFragment();
```

## License
```
Copyright 2024 Lior Avraham

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

## Credits
CustomRegisterLibrary was created by Lior Avraham. 
