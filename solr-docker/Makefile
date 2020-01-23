stop:
	- docker stop solr-master
clean: stop
	- docker rm solr-master
build: clean
	docker build -t solr-master .
run: clean 
	docker run --name solr-master --network net -d -p 8983:8983 \
		-e "BLOG_MYSQL_URL=${BLOG_MYSQL_URL}" -e "BLOG_MYSQL_USERNAME=${BLOG_MYSQL_USERNAME}" \
	   	-e "BLOG_MYSQL_PASSWORD=${BLOG_MYSQL_PASSWORD}" -t solr-master
run-dev: clean
	docker run --name solr-master --network net -d -p 8983:8983 \
		-e "BLOG_MYSQL_URL=jdbc:mysql://mysql-database:3306/blogdb" -e "BLOG_MYSQL_USERNAME=root" \
	   	-e "BLOG_MYSQL_PASSWORD=root" -t solr-master
import-data: 
	curl http://127.0.0.1:8983/solr/blog/dataimport?command=full-import
start:
	docker start solr-master 
bash:
	docker container exec -i -t --user root solr-master bash
logs:
	docker logs solr-master

compose-up:
	docker-compose up -d
compose-down:
	docker-compose down -v
