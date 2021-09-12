#! /bin/bash

case $1 in #swich()
    start) # case()
            echo "Straring"
            ;;
    stop) 
            echo "Stoppring"
            ;;
    *)
        echo "I don not know .."
        exit 1
        ;; #break;
esac 