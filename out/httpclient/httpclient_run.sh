#!/bin/bash

basepath=$(cd `dirname $0`; pwd)
JAVA_HOME=$basepath/jre
export PATH=$JAVA_HOME/bin:$PATH


export LD_LIBRARY_PATH=.:jre/bin:$LD_LIBRARY_PATH
cd $basepath
java -agentlib:NativeDecrypt=dec_httpclient.xml -jar httpclient_enc.jar $1
