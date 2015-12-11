(ns hiccup.core
  "Library for rendering a tree of vectors into a string of HTML.
  Pre-compiles where possible for performance."
  (:use hiccup.compiler
        hiccup.util))

(defmacro html
  "Render Clojure data structures to a string of HTML."
  [options & content]
  (if (map? options)
    (let [mode (:mode options :xhtml)
          partial? (:partial? options)
          escaping (:escaping options :manual)]
      (binding [*html-mode* mode
                *escaping* escaping]
        (if partial?
          `(binding [*html-mode* ~mode
                     *escaping* ~escaping]
             (safe-str ~(apply compile-html content)))
          `(binding [*html-mode* ~mode
                     *escaping* ~escaping]
             ~(apply compile-html content)))))
    (apply compile-html options content)))

(def ^{:doc "Alias for hiccup.util/escape-html"}
  h escape-html)
