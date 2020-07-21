[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)

# iPeople

Language: Kotlin

A master-detail application that loads users from [JSONPlaceholder API](https://jsonplaceholder.typicode.com/users).
<br/><br/>
<img src="https://github.com/ajcordenete/iPeople/blob/develop/screenshots/Screenshot_1595335880.png" width="250">&nbsp; 
<img src="https://github.com/ajcordenete/iPeople/blob/develop/screenshots/Screenshot_1595335893.png" width="250">&nbsp;

### Authentication
  - This app implements local authentication only.
  - [Room Library](https://developer.android.com/topic/libraries/architecture/room) is used as the persistence mechanism for creating new users and signing in into the app.
  
  ### Networking
   - Retrofit library is used for consuming REST calls from the JSON Placeholder API.
   
   ## Architecture
   
   ### MVVM + Repository

- **Ease of maintainability** - Since MVVM implements separation of concerns, it is easy to change, add or remove a feature with minimal effect to other parts of the app. As an example, changing remote data source to a different API can be as simple as changing the repository without changing any parts of the UI.

 - **Ease of testing** - Along with dependency injection, this allows independent testing of the views, viewModels, and repository.
 
 
 ### Modularization
 
  The app is divided into separate modules according to the layers from [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html).
   - **domain module** - contains models/entities that are being used within the app.
   - **local module** - contains classes that handle local storage such database, daos and shared preference.
   - **network module** - contains classes that handle networking such api services and remote data sources.
   - **data module** - contains repository classes and responsible for combining network and local data sources.
   - **app module** - contains the presentation layer (Activities, Fragments, ViewModels, UI logic)
   
   Other modules
   - **common** - contains the common core classes such as extension and widgets
   - **buildSrc** - contains the Config file where all libraries and their versions are declared.
 
 ### Project Folder Structure
  
  - **PBF or [Package By Feature](https://medium.com/mindorks/pbf-package-by-feature-no-more-pbl-package-by-layer-50b8a9d54ae8)**. This folder structure enables us to maintain the structure properly and also increases the readability of the code. PBF also reduces time to find the code for a specific feature.

## Design
 - Material Design principles
 - Minimalist design approach
 
 ## Other libraries used:
- RXJava - handles the stream/flow of data from one architecture layer to another
- Glide - image loading
- Dagger2 - dependency injection
- Gson - json parsing
- Google Maps
- Architecture Components
