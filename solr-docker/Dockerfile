FROM solr:7.7.2
LABEL maintainer="jovanibrasil@gmail.com"

   
# Set database environment variables
ARG BLOG_MYSQL_URL 
ARG BLOG_MYSQL_USERNAME 
ARG BLOG_MYSQL_PASSWORD
ENV BLOG_MYSQL_URL=$BLOG_MYSQL_URL
ENV BLOG_MYSQL_USERNAME=$BLOG_MYSQL_USERNAME
ENV BLOG_MYSQL_PASSWORD=$BLOG_MYSQL_PASSWORD

RUN echo "SOLR_OPTS=\"\$SOLR_OPTS -Ddb.url=\$BLOG_MYSQL_URL -Ddb.user_name=\$BLOG_MYSQL_USERNAME -Ddb.password=\$BLOG_MYSQL_PASSWORD\"" > /opt/solr/bin/solr.in.sh

USER solr

RUN mkdir -p /opt/solr/temp
COPY ./config-files /opt/solr/temp
COPY ./mysql-connector/mysql-connector-java-8.0.15.jar /opt/solr/dist
# Create an core using pre-defined configurations
ENTRYPOINT [ "bash", "-c", "precreate-core blog /opt/solr/temp; exec solr -f" ]
