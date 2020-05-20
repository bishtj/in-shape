VERSION ?=1.0-SNAPSHOT
JAR ?=shape-service-${VERSION}.jar
BIN_PATH ?= shape-service/target
JAVA_CMD=`which java`
CURL_CMD=`which curl`
MAVEN_CMD=`which mvn`
URL=localhost:8080
NAME ?= 'new-name'
BOTTOM_LEFT_X ?= 0
BOTTOM_LEFT_Y ?= 0
WIDTH ?= 5

build: build-clean build-package

build-clean:
	${MAVEN_CMD} clean

build-package:
	${MAVEN_CMD} package

run:
	${JAVA_CMD} -jar ${BIN_PATH}/${JAR}


add-shape:
	${CURL_CMD} -H "Content-Type: application/json" -XPOST ${URL}/api/v1/shape/square/create  -d'         \
    {                                                                                                     \
      "type" : "square",                                                                                  \
      "name" : "'${NAME}'",                                                                               \
      "bottomLeftPointX" : '${BOTTOM_LEFT_X}',                                                            \
      "bottomLeftPointY" : '${BOTTOM_LEFT_Y}',                                                            \
      "width" : '${WIDTH}'                                                                                \
    }'

get-shapes:
	${CURL_CMD} -H "Content-Type: application/json" -XGET ${URL}/api/v1/shape/square
