#!/bin/bash
# Grabs and kill a process from the pidlist that has the word fitlersserver

pid=`ps aux | grep fitlersserver | awk '{print $2}'`
kill -9 $pid
