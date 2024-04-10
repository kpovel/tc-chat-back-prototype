run:
	mvn clean package && java -jar target/chat-0.0.1-SNAPSHOT.jar > target/log.txt &

stop:
	lsof -t -i:8080 | xargs kill

