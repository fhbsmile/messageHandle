#!/bin/bash

JAVA_HOME="/usr/java71_64"
 
APP_Name="messageHandle-1.0.jar"
App_Home=".."
 

 

JAVA_OPTS="-ms512m -mx512m -Xmn256m -Djava.awt.headless=true -XX:MaxPermSize=128m -jar"
 

psid=0
 
checkpid() {
   javaps=`ps -ef | grep java | grep $APP_Name`
 
   if [ -n "$javaps" ]; then
      psid=`echo $javaps | awk '{print $2}'`
   else
      psid=0
   fi
}
 

start() {
   checkpid
 
   if [ $psid -ne 0 ]; then
      echo "================================"
      echo "warn: $APP_MAINCLASS already started! (pid=$psid)"
      echo "================================"
   else
      echo -n "Starting $APP_MAINCLASS ..."
      nohup $JAVA_HOME/bin/java $JAVA_OPTS $App_Home/$APP_Name >/dev/null 2>&1 &
      checkpid
      if [ $psid -ne 0 ]; then
         echo "(pid=$psid) [OK]"
      else
         echo "[Failed]"
      fi
   fi
}
 

stop() {
   checkpid
 
   if [ $psid -ne 0 ]; then
      echo -n "Stopping $APP_Name ...(pid=$psid) "
      kill -9 $psid
      if [ $? -eq 0 ]; then
         echo "[OK]"
      else
         echo "[Failed]"
      fi
 
      checkpid
      if [ $psid -ne 0 ]; then
         stop
      fi
   else
      echo "================================"
      echo "warn: $APP_Name is not running"
      echo "================================"
   fi
}
 

status() {
   checkpid
 
   if [ $psid -ne 0 ];  then
      echo "$APP_Name is running! (pid=$psid)"
   else
      echo "$APP_Name is not running"
   fi
}

info() {
   echo "System Information:"
   echo "****************************"
   echo `head -n 1 /etc/issue`
   echo `uname -a`
   echo
   echo "JAVA_HOME=$JAVA_HOME"
   echo `$JAVA_HOME/bin/java -version`
   echo
   echo "APP_MAIN=$App_Home/$APP_Name"
   echo "****************************"
}
 

case "$1" in
   'start')
      start
      ;;
   'stop')
     stop
     ;;
   'restart')
     stop
     start
     ;;
   'status')
     status
     ;;
   'info')
     info
     ;;
  *)
     echo "Usage: $0 {start|stop|restart|status|info}"
     exit 1
esac
exit 0
