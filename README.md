# FNote 📝

FNote is a simple and elegant note-taking application built with **Jetpack Compose** for the UI and **Firebase Firestore** for backend data storage. It allows users to create, view, edit, and delete notes in real-time, with data synchronized across all devices.

***

## ✨ Features

* **Modern UI:** A clean and intuitive user interface designed with Jetpack Compose.
* **Real-time Synchronization:** Notes are instantly synchronized with Firebase Firestore.
* **CRUD Operations:** Easily Create, Read, Update, and Delete notes.
* **Secure & Scalable:** Leverages Firebase Cloud Firestore for a robust and scalable backend.

***

## 🚀 Getting Started

To run this project, you'll need to set up a Firebase project and connect it to the app.

### Firebase Setup

1.  Create a new project in the [Firebase Console](https://console.firebase.google.com/).
2.  Add an Android app to your Firebase project with the package name **`com.example.fnote`**.
3.  Add the `google-services.json` file to your app's `app` folder.
***

### Run the App
* *Clone this repository.*

* *Open the project in Android Studio.*

* *Build and run the app on an emulator or physical device.*
***

 ### 🛠️ Built With
* Jetpack Compose - Android's modern toolkit for building native UI.

* Firebase Firestore - A flexible, scalable database for mobile, web, and server development.

* Kotlin - A modern programming language used for Android development.

# ✍️ Author
**PatilParas05 - Initial work**

***
#### Google Services File

Your `google-services.json` file should look like this:

#### The actual values for mobilesdk_app_id and current_key will be unique to your Firebase project.
```json
{
  "project_info": {
    "project_number": "921068894145",
    "project_id": "fnote-36b51",
    "storage_bucket": "fnote-36b51.firebasestorage.app"
  },
  "client": [
    {
      "client_info": {
        "mobilesdk_app_id": "your_mobilesdk_app_id",
        "android_client_info": {
          "package_name": "com.example.fnote"
        }
      },
      "oauth_client": [],
      "api_key": [
        {
          "current_key": "your_api_key"
        }
      ],
      "services": {
        "appinvite_service": {
          "other_platform_oauth_client": []
        }
      }
    }
  ],
  "configuration_version": "1"
}

