# Deadline

Modify this file to satisfy a submission requirement related to the project
deadline. Please keep this file organized using Markdown. If you click on
this file in your GitHub repository website, then you will see that the
Markdown is transformed into nice-looking HTML.

## Part 1.1: App Description

> Please provide a friendly description of your app, including
> the primary functions available to users of the app. Be sure to
> describe exactly what APIs you are using and how they are connected
> in a meaningful way.

> **Also, include the GitHub `https` URL to your repository.**

```
https://github.com/vitran75/cs1302-api-app
```

Our app is designed to help you find the perfect makeup products tailored to the current weather conditions in your area.
We understand that weather can affect your makeup choices,
so we've combined information from two powerful APIs to provide personalized recommendations just for you.

1. Location Detection: we use IP-API to automatically detect your location based on your IP address.
2. Location Key Conversion: we use AccuWeather City Search API to convert the city name from ip-api to the city's location key.
3. Weather Information: Once we have your location key, we retrieve current weather information using the AccuWeather Current Conditions API.
4. Makeup Recommendations: Armed with the weather information, we then query the MakeUpAPI to fetch a selection of makeup products, including blush, eyeliner, foundation, lipstick, and mascara. We carefully curate these recommendations based on your current weather conditions.

## Part 1.2: APIs

> For each RESTful JSON API that your app uses (at least two are required),
> include an example URL for a typical request made by your app. If you
> need to include additional notes (e.g., regarding API keys or rate
> limits), then you can do that below the URL/URI. Placeholders for this
> information are provided below. If your app uses more than two RESTful
> JSON APIs, then include them with similar formatting.

### API 1

```
http://ip-api.com/json/
```

> If there is no query after the /json/, the current IP address will be used.
> {query} can be a single IPv4/IPv6 address or a domain name.

### API 2

```
http://dataservice.accuweather.com/locations/v1/cities/search?apikey=G13LhdykGXvFiwcy9AVAsP42fb88lANy&q=Atlanta
```

> API-Key required, current key: G13LhdykGXvFiwcy9AVAsP42fb88lANy
> Rate-limit: 50 requests per day.

### API 3

```
http://dataservice.accuweather.com/currentconditions/v1/348181.json?apikey=G13LhdykGXvFiwcy9AVAsP42fb88lANy&details=true
```
> API-Key required, current key: G13LhdykGXvFiwcy9AVAsP42fb88lANy
> Rate-limit: 50 requests per day.
> City Search and Current Weather Condition utilize the same API-Key and thus have the same rate-limit.

### API 4

```
https://makeup-api.herokuapp.com/api/v1/products.json?product_type=blush
```

## Part 2: New

> What is something new and/or exciting that you learned from working
> on this project?

It's very exciting to know what API responses, how we can handle and connect these responses.
Creating method to filter the responses, learn how to use more JavaFX components and utilize it.

## Part 3: Retrospect

> If you could start the project over from scratch, what do
> you think might do differently and why?

I'll make the project more robust, manage OOP more strictly. The current codes look so messy, since the project use 4 APIs,
when we pass the responses from the previous API to the next one, it can easily cause InterruptedException if
you have network problems and the previous HTTP request hasn't been responded yet.
I'll do something with the rate-limit: make a rate-limit counter, make user wait an amount of time after giving request
or create more account to obtain more API-key so i can write a method to swap API-key when one exceed day limit.
Do something with the X Server maybe, or utilize more java built-in classes to optimize the code.