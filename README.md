# RavenChallenge
<h1 align="center">Hi 👋, I'm Javier Papes </h1>

<p align="center">This is Raven News</p>

<h2 align="left">About this App</h2>
This app allows you to search the most popular New York Times news. You can filter by period of time (1 day, 7 days or 30 days) and also by popularity type (views, sent by email or shared).
The app was built using the public API: https://developer.nytimes.com/apis. ❗ <i> Note: This API only support listing of 20 news (without paging 😔) </i> 

All results are listed, and you can click on any new to see the details and a button to go to the browser.


<h2 align="left">About this project</h2>
This project is 100% Kotlin and it follows the MVI (Model-View-Intent) architectural pattern.<br>
<br>
Project structure:

This project has been designed with careful consideration of the proposed architecture, multimodule, where every feature is a separated module (same as Google recommend). For this reason only the common components are shared in different modules, for example: 

In core module:
- Network monitor: Used to monitor the user connectivity and this could be used cross feature-modules as needed.
- Common architecture bases: StateViewModel as base to share across different VMs, and ViewState to simplify ui states within them. StateViewModel provides a common functions used to implement MVI.
- Extensions: Since Kotlin has a lot of adventages, we are using the extensions functions to simplify the use of flows and make the code more readibility.

In common/network module:
- You can find a HeaderInterceptor where the API_KEY for the New York Times API is used to add it as query parameter in the api call. 
Here you can notice the API_KEY it's getted from BuildConfig,❗❗ but as keys are sensitive data and should not be exposed or 
pushed in github repositories ❗❗ the value it's setted in local.properties so 👉🏼 👉🏼 👉🏼 KEEP IN MIND you need to update your local.properties and add api.key="my_api_key_value" to run this project :) 

In module home:
- You can find all the related code for home feature, that includes listing and detail screens for news.
- In presentation: All the related to the user interface: UI models (it's a good practice use a different model for every layer of the app), Fragments and ViewModels. 
- In domain: The use cases (one for get news and another one to get the article detail), the models for network and database and the mapper to use with them.
- In DI package: All the providers to inject the dependencies using Hilt.
- In data: We have a separation for network and local data. For persistence, Room is used to save in local all the news. In that way the user could have a good experience, where if an error ocurrs the app continue
working with the data from the database. To implement a performant database (avoid persist every query, have duplicated entrys, etc.) you can find a cross reference DAO to mantain references of news shared/getted from
differentes querys. In the remote package, you can find the service to get the news from network with dynamic parameteres. Also, a Error extension file with error that the user can experienced (after some tests with Postman api), like:
429, 404, 401. These errors are captured and the news from database are presented to the user in these scenarios. In a real-case app all the custom exceptions can be logged using a reporter/tracking tool like Firebase crashlytics.


<h2 align="left">About the code</h2>
This project uses several Design Patterns, like: Builder, Adapter, Singleton and Observer.

📱 About UI:
- ConstraintLayouts 
- ViewStubs for better layout inflating

Future UI improvements:
- Different XML layouts for portrait and landscape modes.
- Guidelines to support different screen sizes
- 😁 Possibility to migrate to Jetpack Compose

❗ Error handling:
- Network errors: User continues using the app if there is saved data for the query.
- Empty response: No news result.
- Connectivity: Snackbar to indicate to the user that is navigating offline.

🤝🏻 Improvements:
Some potential future improvements for the app include:
- Adding more tests to improve the app reliability.
- Creating more detailed documentation.


<h2 align="left">How app works:</h2>

![nyt1](https://github.com/Papes96/RavenChallenge/assets/25874890/dc1eba2b-6865-42c6-8a95-007a92285884)




<h2 align="left">Thanks!</h2>
