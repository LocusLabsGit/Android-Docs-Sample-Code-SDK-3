# Android-Docs-Sample-Code-SDK-3

This is a sample application showing how to retrieve the locusmaps-android-sdk from the Package Registry.

## Custom Actions

Note the configuration in app/src/main/assets/locuslabs/llplaces/customLLCustomActions.json

## Map Packs

There are Map Packs in app/src/main/assets/locuslabs/android-A1DGMEMRRWEMBP-2020-09-10T16:55:14.tar.xz

The Map Packs are loaded in MyApplication.onCreate() by calling LLMapPackFinder.installMapPack().

## Permissions

Note the permissions in AndroidManifest.xml

## Dependencies

The LocusLabs customer should receive a Deploy Token from LocusLabs Customer Support and modify their Android project as follows:

### Enter Deploy Tokens

1. Open [gradle.properties file](https://developer.android.com/studio/build#properties-files)
1. Add a line `gitLabDeployToken=<REPLACE_THIS_WITH_YOUR_DEPLOY_TOKEN>`
1. Replace the value `REPLACE_THIS_WITH_YOUR_DEPLOY_TOKEN` with your Deploy Token
1. Add the line `MAPBOX_DOWNLOADS_TOKEN=sk.eyJ1IjoibG9jdXNsYWJzIiwiYSI6ImNrZ2I5OTJ3ZDBleTMyc29qZmY0MDJpeXQifQ.waQrABZiZd_GfJzWNuugQA`

### Point Gradle at the Package Registries

1. Open the [top-level build.gradle file](https://developer.android.com/studio/build#top-level)
1. Find the section `allprojects`
1. In `allprojects`, find the section `repositories`
1. In `repositories` add the two `maven` sections exactly like the ones below:

```
allprojects {
    repositories {
        google()
        jcenter()
        maven {
            url 'https://api.mapbox.com/downloads/v2/releases/maven'
            authentication {
                basic(BasicAuthentication)
            }
            credentials {
                // Do not change the username below.
                // This should always be `mapbox` (not your username).
                username = 'mapbox'
                // Use the secret token you stored in gradle.properties as the password
                password = project.properties['MAPBOX_DOWNLOADS_TOKEN'] ?: ""
            }
        }
        maven {
            url "https://gitlab.com/api/v4/groups/locuslabs/-/packages/maven"
            name "GitLab"
            credentials(HttpHeaderCredentials) {
                name = 'Deploy-Token'
                value = gitLabDeployToken // gitLabDeployToken is a variable defined in gradle.properties, each LocusLabs customer receives their own
            }
            authentication {
                header(HttpHeaderAuthentication)
            }
        }
    }
}
```

### Import the LocusMaps Android SDK

1. Open the [module-level build.gradle file](https://developer.android.com/studio/build#module-level)
1. Find the section `dependencies`
1. Add the dependency below

```
    implementation 'com.locuslabs:locuslabs-android-sdk:3.0.6'
```

## MyApplication class

Note that we've introduced the MyApplication class and referenced in AndroidManifest.xml as:

`android:name=".MyApplication"`

This is necessary in order for the following configurations to get picked up by the LocusMaps Android SDK:

```
        LLConfiguration.singleton.applicationContext = applicationContext
        LLConfiguration.singleton.accountID = ACCOUNT_ID_LOCUSLABS_DEMO
```

## Account ID

The LocusLabs Account ID is set in MyConstants.kt as `ACCOUNT_ID_LOCUSLABS_DEMO`