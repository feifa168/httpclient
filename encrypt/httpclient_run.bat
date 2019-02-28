@echo off

set path=.;jre\bin;%path%

java -agentlib:libNativeDecrypt=dec_httpclient.xml -jar httpclient_enc.jar assetsscan.xml