stop:
	- docker stop solr-master
clean: stop
	- docker rm solr-master
build: clean
	docker build --build-arg BLOG_MYSQL_URL --build-arg BLOG_MYSQL_USERNAME --build-arg BLOG_MYSQL_PASSWORD -t solr-master .

run: clean 
	docker run --name solr-master --network net -d -p 8983:8983 -t solr-master
	# Import data
	#curl http://localhost:8983/solr/blog/dataimport?command=full-import
start:
	docker start solr-master 
bash:
	docker container exec -i -t --user root solr-master bash
logs:
	docker logs solr-master
