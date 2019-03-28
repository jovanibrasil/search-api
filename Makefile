stop:
	- docker stop search-api
clean: stop
	- docker rm search-api
build: clean
	mvn clean package
	docker build -t search-api .
run: clean
	docker run -d -p 4567:4567 --name=search-api search-api
start: stop
	docker start search-api
bash:
	docker container exec -i -t --user root search-api bash