#! /bin/sh

java -cp ./bin:./lib/jdom.jar:./lib/jme-jmePhysics.jar: -Djava.library.path=./lib/native game/main/StartGame -Xms512M -Xmx1024M -Xmn256M
