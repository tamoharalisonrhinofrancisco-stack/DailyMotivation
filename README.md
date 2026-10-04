# Daily Motivation

Daily Motivation is a private, offline, device-bound Android application designed to provide daily motivational content without internet access, accounts, ads, analytics, or cloud services.

## What it does
- Shows a motivational quote for the current date
- Keeps the quote stable for the given day
- Allows a new random thought from the local library
- Lets the user copy the current message
- Shows a clean About screen with security status
- Runs entirely offline

## Device binding model
This version uses Android Keystore and a non-exportable RSA key stored in the device keystore. The first launch creates a device-bound key and saves a signed challenge response. On future launches, the app verifies that the same key still exists and can sign/verify the challenge. If the key cannot be verified, the app blocks access and shows the unauthorized screen.

Important limitation: Android Keystore helps protect the private key, but no offline APK is impossible to modify. A determined attacker could reverse-engineer the app. This is strong practical device binding for a private offline edition, not an unbreakable DRM system.

## Local encrypted storage
The app uses AndroidX Security EncryptedSharedPreferences with a Keystore-backed master key to store the app's minimal authorization metadata and preference values. No cryptographic private key is stored as plain data.

## Build instructions
1. Install Android Studio
2. Open Android Studio and select "Open an existing project"
3. Select this project directory
4. Let Gradle sync
5. Choose a connected device or emulator
6. Run the app

## Install on device
1. Build the APK from Android Studio
2. Connect your Android device by USB
3. Enable Developer Options and USB debugging
4. Select Run and choose the device
5. Install the APK

## Test plan
### TEST A – First installation
- Install the APK on Device A
- Launch the app
- It should authorize on first launch
- Main screen should appear

### TEST B – Restart
- Close the app
- Open it again
- It should remain authorized

### TEST C – Phone restart
- Restart the Android device
- Open the app again
- It should remain authorized if the Keystore key is still present

### TEST D – Offline test
- Disable Wi-Fi and mobile data
- Launch the app
- It should still work normally without internet

### TEST E – APK copy
- Copy the APK to Device B
- Install it there
- It should display the unauthorized device screen

### TEST F – Data loss conditions
- If app data is cleared: the app will likely lose stored authorization metadata and will require reactivation or show unauthorized behavior depending on the Keystore state.
- If the app is uninstalled: the key and secure state are removed by the system, so reinstallation requires a fresh authorization.
- If the device is factory-reset: the Android Keystore is reset and the app becomes unauthorized.
- If the Keystore becomes unavailable: the app fails securely and blocks access.

## Limitations of offline device binding
- A determined attacker may decompile the app
- A modified APK could attempt to bypass logic
- Keystore protection does not make an APK impossible to reverse-engineer
- This is a practical offline device-bound solution, not a perfect DRM system

## Future commercial direction
A commercial version could evolve into a stronger licensing model using:
- secured activation codes
- signed licenses
- server-side validation
- Play Integrity API
- license revocation
- encrypted application state
- signed updates

This would still keep the app offline-first where possible, but a service-backed license would provide stronger anti-tamper controls.

## Developer
TAMOHA Ralison Rhino Françisco

## Package name
com.rhino.dailymotivation
