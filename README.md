# CUBA Navigation & Routing sample

This project is intended for demonstration of basic concepts of URL Routing
feature.

The `Booking` screen contains the following examples:

1. How to reflect some state to URL
2. How to handle URL params changing

## Basic usage

#### Reflecting state to URL

To reflect a state the `UrlRouting` component should be used. Its instance can be
obtained from `AppUI`.

`UrlRouting` has two main methods:

- `pushState(Screen screen)` - changes URL according the given screen and creates 
new entry in browser history
- `replaceState(Screen screen)` - changes URL according the given screen, but 
does not create new entry in browser history

Example:
```java
Map<String, String> params = ParamsMap.of(
        "param1", "value1",
        "param2", "value2");

// this equals to current screen instance
AppUI.getCurrent().getUrlRouting()
                .replaceState(this, params);
```

#### Handling URL params changing

Params changing can be handled with subscription for `UrlParamsChangedEvent` in
screen controller:

```java
@Subscribe
protected void onUrlParamsChanged(UrlParamsChangedEvent event) {
    Map<String, String> params = event.getParams();
    
    // do something
}
```

It enables to preload some data or change screen controls state before screen
is shown (for example, when a screen is navigated with some params).


## Screenshot

![Booking](./img/booking.png)

## Issues
Please use https://www.cuba-platform.com/discuss for discussion, support, and
reporting problems corresponding to this sample.
