(ns summernote
  (:require
   [reagent.core :as r]
   [clojure.string :as s]))

(defn editor [id text on-change-fn]
  (let [component-id (r/atom nil)
        this (r/atom nil)
        value (r/atom nil)
        change-fn
        #(do
           (reset! value %)
           (on-change-fn
            @component-id
            (if (s/blank? @value) nil @value))
           )]
    (r/create-class
     {:component-did-mount
      (fn [component]
        (reset! this (js/$ (r/dom-node component)))
        (.summernote @this #js
                     {:callbacks
                      #js {:onChange change-fn}
                      :fontNames #js ["Helvetica Neue"
                                      "Helvetica"
                                      "Arial"
                                      "sans-serif"
                                      "Courier"
                                      "Courier New"
                                      "Times New Roman"]
                      ;;["groupName" [list of buttons]]
                      ;; CAUTION - drop down css styles depend on "groupName"
                      :toolbar #js
                      [
                       ;;#js ["time-travel" #js ["undo"
                       ;;                        "redo"]]
                       #js ["format" #js ["fontname"
                                          "fontsize"]]
                       #js ["font-style" #js ["bold"
                                              "italic"
                                              "underline"
                                              "strikethrough"
                                              "color"]]
                       #js ["font-style-special" #js ["superscript"
                                                      "subscript"]]
                       #js ["style-reset" #js ["clear"]]
                       #js ["style" #js ["style"]]
                       #js ["para" #js ["ol"
                                        "ul"
                                        "paragraph"
                                        ]]
                       #js ["height" #js ["height"]]
                       #js ["insert" #js ["link"
                                          ;;"picture"
                                          ;;"video"
                                          "hr"]]
                       #js ["table" #js ["table"]]
                       #js ["misc" #js ["fullscreen"
                                        "codeview"
                                        ;;"help"
                                        ]]]})

        (if @value
          (.summernote @this "code" @value)))

      :component-did-update
      (fn []
        (if @value
          (.summernote @this "code" @value)))

      :component-will-unmount
      (fn []
        (println "destroy")
        (.summernote @this "destroy"))

      :display-name  (str "summernote-editor-" id)

      :reagent-render
      (fn [id text]
        (reset! component-id id)
        (reset! value text)
        [:div {:id (str "summernote-editor-" @component-id)}])})))
