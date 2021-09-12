#! /bin/bash

global=10

function foo(){
        local val=3
        echo "foo(){}"
        echo "global variable = $global"
        echo "local variable = $val"
        echo "arg1 = $1"
        echo "arg2 = $2"
}

foo arg1 arg2