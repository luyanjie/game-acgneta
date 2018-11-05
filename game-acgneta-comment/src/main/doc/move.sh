#!/usr/bin/sh
# 发布后移动文件，目录说明，new为新版本的文件目录，back为回收目录

APP_NAME=acgneta-comments

SERVICE_DIR=/data/www/$APP_NAME
SERVICE_BACK_DIR=$SERVICE_DIR/back
SERVICE_NEW_DIR=$SERVICE_DIR/new
SERVICE_NAME=game-acgneta-comment-1.0
JAR_NAME=$SERVICE_NAME\.jar

# 判断文件是否存在
if [ -e $SERVICE_NEW_DIR/$JAR_NAME ]
then
        # 获取时间
        currentTime=$(date +%Y%m%d%H%M%S)
        #echo $currentTime
        #echo $SERVICE_BACK_DIR/$JAR_NAME
        mv $SERVICE_DIR/$JAR_NAME $SERVICE_BACK_DIR/$JAR_NAME\.$currentTime

        cp -a $SERVICE_NEW_DIR/$JAR_NAME $SERVICE_DIR/$JAR_NAME
else
        echo 'file not exist'
fi
