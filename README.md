
# Peanut Labs Reward Center - Publisher Marmalde SDK (Beta)

Peanut Labs connects your users with thousands of paid online surveys from big brands and market researchers. This SDK allows you to integrate our Reward Center within your Marmalade application for iOS and Android. 

This Marmalade extension supports iPhone and Android.
This is the first Beta release, if you run into any issues please use Github issues to report.

#The Reward Center

The Reward Center lists surveys and offers best suited for each of your members. It is highly customizable and positively engaging.

You get paid whenever your members complete a listing, and get to reward them back in the virtual currency of your choice.

All of this and much more is configured and monitored through our Publisher Dashboard. To learn more and get access to our full set of tools, get in touch with us at publisher.integration@peanutlabs.com

#Integration

Check out <a href="http://peanut-labs.github.io/publisher-doc/" target="_blank">our integration guide</a> for step by step instructions on getting up and running with our Reward Center within your iOS application.

#Marmalade Usage
* Clone this github repository
* Include the subproject "peanutlabs" in your project's MKB file, for example:
```
subprojects
{
    iwutil
    /path/to/this/repo/peanutlabs
}
```

* Include the extension's header file in your source code
* Call the method "openRewardsCenter" with the generated user id as a parameter. Example: ```openRewardsCenter("END_USER_ID-APPID-HASH")```


#Compiling Against iOS 9

You will need to add the following to your applications plist file in order for the system to work with iOS 9:
```
 <key>NSAppTransportSecurity</key>
    <dict>
        <key>NSAllowsArbitraryLoads</key>
        <true/>
    </dict>
```
#Android

* Run peanutlabs_android.mkb and then peanutlabs_android_java.mkb to build android extensions

You will need to add the following activity to your Manifest.xml file in order to run android application with rewards center.

```
<activity
	android:name="com.peanutlabs.plsdk.RewardsCenterActivity"
	android:configChanges="keyboard|keyboardHidden|screenSize|orientation"
	android:label="@string/title_activity_rewards_center" >
</activity>
```