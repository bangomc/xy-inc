FROM java:8-jdk-alpine
LABEL author="Cristhiano Roberto (bangomc@gmail.com)"
RUN mkdir -p /usr/local/app
RUN mkdir -p /usr/local/newrelic/logs
ADD target/xy-inc-0.0.1-SNAPSHOT.jar /usr/local/app/xy-inc-0.0.1-SNAPSHOT.jar
ADD newrelic/newrelic.jar /usr/local/newrelic/newrelic.jar
ADD newrelic/newrelic.yml /usr/local/newrelic/newrelic.yml
WORKDIR /usr/local/app
EXPOSE 8080
ENTRYPOINT ["java", "-javaagent:/usr/local/newrelic/newrelic.jar","-jar", "xy-inc-0.0.1-SNAPSHOT.jar"]