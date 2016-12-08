# reagent-summernote
Basic [reagent](http://reagent-project.github.io/) recipe for [summernote](http://summernote.org/). A single instance could be used for multiple components using the `id` parameter.

This is by no means a complete implementation. See the [summernote API](http://summernote.org/deep-dive/) for additional options and callback functions. This recipe provides a clear enough template to riff on.

## Usage

Add the css files (adjusting paths to suit) to the head of your file
```cljs
[:head
 ;; other head stuff ...
 (for [x ["/bootstrap/css/bootstrap.min.css"  
          "/summernote/css/summernote.css"]] (include-css x)))]
```

Add the js files (adjusting paths to suit) to the body of your file (note: `root-container` is where out reagent app mounts)
```cljs
[:body
 [:div {:id "root-container" :style "height: inherit"}]
 (for [x ["/jquery/js/jquery-2.2.2.min.js"
          "/bootstrap/js/bootstrap_3.3.6.min.js"
          "/summernote/js/summernote.min.js"]](include-js x))]
```

reagent-summernote takes the following parameters:
 * id (string): a unique id to associate with the text in your summernote component
 * text (string | html): the initial text displayed
 * on-change-fn (function): the function to call when editor text changes

In your reagent app, use summernote like so:
```cljs
(ns your.component.or.view
  (:require
   [summernote :as summernote]))
   
   [:div
    [summernote/editor
    {:id "my-summernote-component-id"
     :text "welcome to reagent-summernote!"
     :on-change-fn #(println (str text changed: " %))}]]
```


