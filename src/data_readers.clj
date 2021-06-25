;Tagged literals
;When clojure starts, it searches for data_readers.clj at the root classpath
;This file must contain a map of symbols and functions that will be called
{ref/dot-string      reference.c1-reader/dot-string
 ref/log             reference.c1-reader/log
 ref/local-date-time reference.c1-reader/local-date-time
 ;Rebinding tagged literals
 ;inst           reference.c1-reader/local-date-time
 }