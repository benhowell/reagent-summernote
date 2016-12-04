(ns summernote
  (:require
   [reagent.core :as r]
   [clojure.string :as s]))

(defn editor [{:keys [id text on-change-fn]}]
  (let [component-id (r/atom id)
        this (r/atom nil)
        value (r/atom text)
        on-change (r/atom on-change-fn)
        change-fn
        #(do
           (reset! value %)
           (@on-change
            @component-id
            (if (s/blank? @value) nil @value)))]
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
        (.summernote @this "code" @value))

      :component-will-receive-props
      (fn [component next-props]
        (if-not (= (r/props component) (second next-props))
          (do
            (reset! component-id (:id (second next-props)))
            (reset! value (:text (second next-props)))
            (reset! on-change (:on-change-fn (second next-props))))))

      :component-did-update
      (fn [] (.summernote @this "code" @value))

      :component-will-unmount
      (fn [] (.summernote @this "destroy"))

      :display-name  (str "summernote-editor-" id)

      :reagent-render
      (fn [{:keys [id text]}]
        [:div {:id (str "summernote-editor-" @component-id)}])})))

   
                             
