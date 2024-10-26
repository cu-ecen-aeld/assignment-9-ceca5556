#!/bin/sh
# module startup and shutdown
# Author: Cedric Camacho

case "$1" in
  start)
    echo "loading misc-module"
    # scull_load
    module_load faulty
    modprobe hello
    ;;
  stop)
    echo "unloading misc-module"
    # scull_unload
    module_unload faulty
    rmmod hello
    ;;
  *)
    echo "Usage: $0 {start|stop}"
  exit 1
esac

exit 0