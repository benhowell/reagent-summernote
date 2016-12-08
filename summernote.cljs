(ns summernote
  (:require
   [reagent.core :as r]
   [clojure.string :as s]))

(defn summernote-editor [{:keys [id text on-change-fn]}]
  (let [id (atom id)
        this (r/atom nil)
        value (r/atom text)
        reset? (atom false)]
    (r/create-class
     {:component-did-mount
      (fn [component]
        (reset! this (js/$ (r/dom-node component)))
        (.summernote @this #js
                     {:callbacks
                      #js {:onChange
                           #(if-not @reset?
                              (on-change-fn (.summernote @this "code"))
                              (reset! reset? false))}
                      :disableDragAndDrop true
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
                                        ]]]}))


      :component-will-receive-props
      (fn [component next-props]
        (if-not (= (second next-props) (r/props component))
          (do
            (reset! reset? true)
            (reset! id (:id (second next-props)))
            (reset! value (:text (second next-props)))
            (.summernote @this "code" @value))))


      :component-will-unmount
      (fn []
        (println "destroy")
        (.summernote @this "destroy"))

      :display-name  (str "summernote-editor")

      :reagent-render
      (fn []
        [:div {:id (str "summernote-editor-" id)
               :dangerouslySetInnerHTML {:__html @value}}])})))
