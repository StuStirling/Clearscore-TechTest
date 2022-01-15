# Summary

My solution to the Clearscore Android developer technical test.

## New Frameworks

Some frameworks I had not used before:

* Mockk
* Hilt

## Known Issues

* I could not get Mockk working on Android 30 so instrumented tests only work on Android 29 and below.
* I used a mixture of mockito and mockk. This was due to issues on the ui tests. Ideally I would 
have replaced mockito completely because I think mockk has better coroutine handling

## Improvements

Some shortcuts were taken due to time contraints. Here is what I would have done with more time.

* Custom view testing would be in its own instrumented test
* Mapping around the error to the UI so the user could have more detailed error messaging.
* Flavour handling for different environments so the base url could change depending on build variant
* Hilt required my to include GSON as a transitive dependency because of the SingletonComponent. This
is a code smell and the data class should be the only one to know about it.
* Obviously the fact the UI tests do not working on all API versions.
* More investigation into Hilt scopes as does not seem segregated enough