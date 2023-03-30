# Grab base image
from openjdk:8-jre-alpine

# Copy .jar from target folder and paste as 'app.jar'
copy target/*.jar app.jar

# CLI Commands to run application
ENTRYPOINT [ "java", "-jar", "app.jar" ]