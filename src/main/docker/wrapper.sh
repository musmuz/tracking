#!/bin/bash
#while ! exec 6<>/dev/tcp/moitrack_db/27017; do
#    echo "Trying to connect to MONGODB at ${SPRING_DATA_MONGODB_URI}..."
#    sleep 10
#done

java -Djava.security.egd=file:/dev/./urandom -jar app.jar
