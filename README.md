# InstantFlix
Application that List Movies and Tv Shows

### Config Project
#### 1. create a file with the name: secrets.properties in the root with the following properties:
BASE_URL="https://api.themoviedb.org/3/" <br />
API_KEY="YOUR_API_KEY" (if you don't have one, request one at https://developers.themoviedb.org/3/getting-started/introduction)<br /><br />

Context: For security you must not expose urls, api keys etc. <br />


### App Install

The APK is in the folder delivery in the root of the project in the branch Master with the name: instantflix.apk downolad it.

when install the apk to a physical device make sure that previously configure your device to accept unknow providers or insecure application.
Or when the dialog shows of app insecure tap install anyway.

## Architecture
The architecture of this project follows the concepts of an architecture and implementing the principles of SOLID and using the MVVV design pattern.:<br />

##### Layers
<ul>
<li>core.data</li>
<li>core.di</li>
<li>core.exception</li>
<li>core.usecase</li>
<li>core.utils</li>
<li>presentation
</ul>

## Caching
For cache management, ROOM was used as Data Source

## Pagination
A pagination system is used using Paging 3

## UI
Jetpack compose was used for the user interface design.

## Stack used

<ul>
<li>Jetpack compose</li>
<li>Dagger Hilt</li>
<li>Paging 3</li>
<li>Retrofit</li>
<li>ROOM</li>
<li>presentation</li>
<li>Kotlin Coroutines</li>
<li>Kotlin Flows</li>
</ul>
