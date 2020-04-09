# SIMPLE TEST OF TOKOIN

### Candidate Information ###
* Name:     Duy, Pham Hoang
* Male:     Male
* Phone:    0904.341.348
* Skype:    https://lnkd.in/g3i2jgK

### Technologies ###
**Primary Languages:** Kotlin

**MVVM Architecture Pattern:** MVVM stands for Model, View, ViewModel.

* Model: This holds the data of the application. It cannot directly talk to the View. Generally, it’s recommended to expose the data to the ViewModel through Observables.

* View: It represents the UI of the application devoid of any Application Logic. It observes the ViewModel.

* ViewModel: It acts as a link between the Model and the View. It’s responsible for transforming the data from the Model. It provides data streams to the View. It also uses hooks or callbacks to update the View. It’ll ask for the data from the Model.

**Android CI/CD**
![Demo Android CI/CD on github](https://i.imgur.com/fEPtaP4.png)

* Continuous integration(CI) is a coding philosophy and set of practices that drive development teams to implement small changes and check in code to version control repositories frequently. Because most modern applications require developing code in different platforms and tools, the team needs a mechanism to integrate and validate its changes.

* Continuous Delivery(CD) picks up where continuous integration ends. CD automates the delivery of applications to selected infrastructure environments. Most teams work with multiple environments other than the production, such as development and testing environments, and CD ensures there is an automated way to push code changes to them.

* CI/CD tools help store the environment-specific parameters that must be packaged with each delivery. CI/CD automation then performs any necessary service calls to web servers, databases, and other services that may need to be restarted or follow other procedures when applications are deployed.


**Koin-Injection:** A pragmatic lightweight dependency injection framework for Kotlin developers. Written in pure Kotlin using functional resolution only: no proxy, no code generation, no reflection!

**Moshi:** Moshi uses Okio to optimize a few things that Gson doesn’t. When reading field names, Moshi doesn’t have to allocate strings or do hash lookups. Moshi scans the input as a sequence of UTF-8 bytes, converting to Java chars lazily. For example, it never needs to convert integer literals to chars. The benefits of these optimizations are particularly pronounced if you’re already using Okio streams. Users of Retrofit and OkHttp in particular benefit from Moshi.

**Jetpack Component and other:** Room, Navigation Component, material design, Stream, sdp, ssp, coroutine,...

### Datasource
* API Source: https://newsapi.org/
> Thank you about your API.

