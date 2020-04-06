FROM java:8-jdk-alpine
LABEL author="Cristhiano Roberto (bangomc@gmail.com)"
COPY target/xy-inc-0.0.1-SNAPSHOT.jar /home
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/home/xy-inc-0.0.1-SNAPSHOT.jar"]