FROM anapsix/alpine-java
LABEL maintainer="jovanibrasil@gmail.com"

WORKDIR /code

COPY ./target/search-api-jar-with-dependencies.jar /search-api-jar-with-dependencies.jar

EXPOSE 4567
CMD ["java", "-jar", "/search-api-jar-with-dependencies.jar"]