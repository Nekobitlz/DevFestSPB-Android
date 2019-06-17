# DevFestSPB-Android

#### Warning! Site and API are no longer available
 
 
[LATEST VERSION APK FILE (can work offline)](https://github.com/Nekobitlz/DevFestSPB-Android/blob/master/apks/DevFestSPB-1.0.apk)

The application was executed as part of training at the Android Academy and is a mobile version of the site: https://devfest-spb.com/.


## Technology stack 
The source code of the app was written in *Java*. The *MVP* was used as an architectural pattern. The *Room* was used to store data. With *OkHttp* and *Retrofit* been realized interaction with the network. Pictures from the network were uploaded using *Picasso*. For asynchronous operation with the database used *AsyncTask*. Regular data updates in the background are implemented using the service with *AlarmManager*.


## Screenshots
<img src="/screenshots/mainMenu.jpg" width="360" height="640"> <img src="/screenshots/speakerInfo1.jpg" width="360" height="640"> <img src="/screenshots/lectureInfo1.jpg" width="360" height="640"> <img src="/screenshots/speakerInfo2.jpg" width="360" height="640"> <img src="/screenshots/lectureInfo2.jpg" width="360" height="640">


## Setup for Developers
1. Make sure you have downloaded the latest version of [Android Studio](https://developer.android.com/sdk/index.html). It works on Linux, Windows and Mac. Download the correct version for your OS.
2. Go to [the project repo](https://github.com/Nekobitlz/DevFestSPB-Android) and fork it by clicking "Fork" 
3. If you are working on Windows, download [Git Bash for Windows](https://git-for-windows.github.io/) to get a full Unix bash with Git functionality
4. Clone the repo to your desktop `git clone https://github.com/YOUR_USERNAME/DevFestSPB-Android.git`
5. Open the project with Android Studio 
6. Build a 'app' application which is inside the base directory.
