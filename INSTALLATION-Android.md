# Cordova: Tango Targeting Plugin

Smart engagement with your customers.

For more information please see [our website][1].

## Installation (Android)

### Add the SDK Key
Tango Targeting SDK auto-initializez on Android. However, it needs to read your Tango SDK Key from AndroidManifest.xml file. In your `config.xml` add the SDK Key to the android platform in the following manner:

```xml
<platform name="android">
  <!-- other configurations above -->
  <config-file parent="./application" target="AndroidManifest.xml">
    <meta-data android:name="tango_api_key" android:value="your-tango-sdk-key" />
  </config-file>
  <!-- other configurations below -->
</platform>
```

### Enable Tango logs

If you want to see Tango logs add a `tango_debug_mode` `meta-data` set to `true` to your android platform.

```xml
<meta-data android:name="tango_debug_mode" android:value="true" />
```

### Enable Push Notifications

Tango Targeting uses FCM for push notifications. 
