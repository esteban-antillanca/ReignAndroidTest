# Reign Android Development Test - Esteban Antillanca

## About the solution
According to the instructions, this is a simple Android app that shows a list of articles, with scroll to refresh, swipe to delete, WebView visualization and offline support features. It was developed in Kotlin with Android Studio, targeting API level 28 with support from 22 and above.

### Implementation details
* MVP was used. The scope and functionalities were simple enough to adapt the whole project in this pattern, without to worry about huge future escalation.
	* According to the Android Framework, Views were represented with Fragments.
	* Presenter was coded fully independent of the framework.
	* Model is also a plain Kotlin class, with access to Android Framework.
* Standard Material Design resources from the Android Framework were used.
* Due to models simplicity, data persists on local storage through Shared Preferences. Deleted Articles are kept in this way to filter even online responses from the API. The last response obtained from the server is also cached this way.
* The following libraries were used:
	* [Retrofit](https://github.com/square/retrofit) for API management
	* [SwipeRevealLayout](https://github.com/chthai64/SwipeRevealLayout) for adding swipe-to-reveal functionality to RecyclerView items. This functionality was used to add the Delete-Article feature.

## Authors

* **Esteban Antillanca**
