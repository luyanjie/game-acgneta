#!/usr/bin/sh

## java env
## vim /etc/profile 查看环境变量中java的配置地址
export JAVA_HOME=/usr/lib/java/jdk1.8.0_171
export JRE_HOME=$JAVA_HOME/jre


## service name
APP_NAME=acgneta-comments
SERVICE_DIR=/data/www/$APP_NAME
SERVICE_NAME=game-acgneta-comment-1.0
JAR_NAME=$SERVICE_NAME\.jar
PID=$SERVICE_NAME\.pid
cd $SERVICE_DIR

function start() {
    nohup $JRE_HOME/bin/java -jar -Xms1024m -Xmx1024m -Dspring.profiles.active=dev -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=10086 $JAR_NAME > $SERVICE_DIR/nohup.out 2>&1 &
    echo $! > $SERVICE_DIR/$PID
    echo "=== start $SERVICE_NAME"
}

function stop() {
    kill `cat $SERVICE_DIR/$PID`
    rm -rf $SERVICE_DIR/$PID
    echo "=== stop $SERVICE_NAME"
    sleep 5
    ##
    ## edu-service-aa.jar
    ## edu-service-aa-bb.jar
    P_ID=`ps -ef | grep -w "$SERVICE_NAME" | grep -v "grep" | awk '{print $2}'`
    if [ "$P_ID" == "" ]; then
        echo "=== $SERVICE_NAME process not exists or stop success"
    else
        echo "=== $SERVICE_NAME process pid is:$P_ID"
        echo "=== begin kill $SERVICE_NAME process, pid is:$P_ID"
        kill -9 $P_ID
    fi
}

function restart() {
    stop
    sleep 2
    start
}

case "$1" in
    start)
        echo "****************"
        start
        echo "****************"
        ;;
    stop)
        echo "****************"
        stop
        echo "****************"
        ;;
    restart)
        echo "****************"
        restart
        echo "****************"
        ;;
    *)
        echo "****************"
        echo "no command"
        echo "****************"
        ;;

esac
exit 0