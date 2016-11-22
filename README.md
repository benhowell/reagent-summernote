# reagent-summernote
Basic reagent wrapper recipe for [summernote](http://summernote.org/).

## Usage

Add the css files (adjusting paths to suit) to the head of your file
```cljs
[:head
 ;; other head stuff ...
 (for [x ["/bootstrap/css/bootstrap/3.3.6/css/bootstrap.min.css"  
          "/summernote/css/summernote.css"
          ;; other css files ... ]]
   (include-css x)))]
```

Add the js files (adjusting paths to suit) to the body of your file (note: `root-container` is where out reagent app mounts)
```cljs
[:body
 [:div {:id "root-container" :style "height: inherit"}]
 (for [x ["/jquery/js/jquery-2.2.2.min.js"
          "/bootstrap/js/bootstrap_3.3.6.min.js"
          "/summernote/js/summernote.min.js"
          ;; other js libs ... ]]
   (include-js x))]
```

reagent-summernote takes the following parameters:
 * id (string): the unique id of the component
 * text (string | html): the initial text displayed
 * on-change-fn (function): the function to call when editor text changes

In your reagent app, use summernote like so:
```cljs
(ns your.component
  (:require
   [summernote :as summernote]
   ;; other requires ... ))
   
   [:div
    [summernote/editor
    "my-summernote-component-id"
    "welcome to reagent-summernote!"
    #(println (str "id " %1 " text changed: " %2])]]
   
   
```
