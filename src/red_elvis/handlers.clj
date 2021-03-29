(ns red-elvis.handlers
  (:require
   [ring.util.response :refer [response]]
   [hiccup.core :refer [html]]
   [hiccup.page :refer [html5 include-js include-css]]
   [hiccup.element :refer [link-to]]))

(defn front-page
  [request]
  (response "Predator Free NZ"))


(defn predator-trap-media-object
  [trap-details]
  [:article {:class "media"}
   [:figure {:class "media-left"}
    [:p {:class "image is-64x64"}
     [:img {:src "public/images/weasel.jpg"}]]]
   [:div {:class "media-content"}
    [:div {:class "content"}
     [:h3 {:class "subtitle"}
      (str (:trap-type trap-details) " : " (:trap-kills trap-details) " 💀 ")]]

    [:div {:class "field is-grouped"}
     [:div {:class "control"}
      [:div {:class "tags has-addons"}
       [:span {:class "tag"} "Location"]
       [:span {:class "tag is-success is-light"} (:trap-location trap-details)]]]

     [:div {:class "tags has-addons"}
      [:span {:class "tag"} "Last Checked"]
      [:span {:class "tag is-success is-light"} (:trap-last-checked trap-details)]]]]

   [:div {:class "media-right"}
    (link-to {:class "button is-primary"} "/updatetrap" "Update Trap")
    ]])

(defn traps-overview-page
  "Overview of each trap used by the current customer.

  Using Bulma media object style
  https://bulma.io/documentation/layout/media-object/

  Request hash-map is not currently used"

  [request]
  (response
    (html5
      {:lang "en"}
      [:head
       [:meta {:charset "utf-8"}]
       [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge,chrome=1"}]
       [:meta
        {:name "viewport"
         :content "width=device-width, initial-scale=1.0, maximum-scale=1.0"}]
       [:title "Predator Free"]
       (include-css "https://cdn.jsdelivr.net/npm/bulma@0.9.0/css/bulma.min.css")]
      [:body
       [:section {:class "hero is-info"}
        [:div {:class "hero-body"}
         [:div {:class "container"}
          [:h1 {:class "title"} "Predator Free NZ"]
          [:p {:class "subtitle"}
           "Making your traps immutable"]]]]

       [:section {:class "section"}
        (predator-trap-media-object {:trap-type  "Ferret"      :trap-location    "123456789"
                                    :trap-kills "14"           :trap-last-checked "25-12-2019"})

        (predator-trap-media-object {:trap-type  "Stoat"      :trap-location    "123454321"
                                    :trap-kills "2"           :trap-last-checked "01-02-2020"})

        (predator-trap-media-object {:trap-type  "Rat"        :trap-location    "123454321"
                                    :trap-kills "200"         :trap-last-checked "02-04-2020"})

        (predator-trap-media-object {:trap-type  "Possum"      :trap-location    "98r9e8r79wr87e9232"
                                    :trap-kills "02"          :trap-last-checked "01-02-2020"})

        ]
       [:div {:class "media-right"}
        (link-to {:class "button is-info"} "/addtrap" "Add new Trap")]
       ])))
