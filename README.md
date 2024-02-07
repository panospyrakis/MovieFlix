# MovieFlix App
## Overview
The application is a movie catalog where users can check the movies of the week, search
for movies and view details about them. It is developed by consuming the Api service of [The Movie DB](https://developer.themoviedb.org/docs).

##  Setup
In order to be able to build the project and the service to properly work you need to create a file named apikey.properties inside the root folder of the project "~/MovieFlix". In there you should write the following:

    API_KEY="ENTER_YOUR_API_KEY_HERE"
You should replace the `ENTER_YOUR_API_KEY_HERE` placeholder with your Api key. In my case the api key from TMDB did not work and had to use an bearer token. If you user a bearer token the apikey.properties file should look like this:

    API_KEY="Bearer ENTER_YOUR_API_KEY_HERE"
Again you should replace the `ENTER_YOUR_API_KEY_HERE` placeholder with your Bearer token.

## Architecture
I used MVVM architecture for this project with a single activity and two fragments. Each fragment has each own's view model. For the handling of data sources I created repositories. The logic behind the loading of the data of the list for the infinite scrolling is that we load the data from the network and insert them into the Room db.
Using observables I collect the data to the fragment (after some manipulations in repository and view model) and apply the changes to the ui accordingly.
Thus we achieve the single source of truth which for this case is the Room db.

For the details screen a more simple login by loading the data(after some manipulations in repository and view model) directly from the api service. 
In both cases the movies repository is the single source of truth.
For dependency injection I used Hilt and for the ui handling view binding and for observables I used Kotlin's Flows. Also used coroutines for the threading of the app.
Also I tried to break down as much as possible my packages in to features. In this way it will be much easier in the future to convert into modular architecture.


## Next Steps

 1. Testing: both unit and integration.
 2. Refactor project and apply modular architecture.
 3. Refactor project and add compose
 4. Polish ui and apply common styles throughout the project.
