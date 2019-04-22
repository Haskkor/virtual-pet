# APP

* Local
    * lein uberjar
    * java -jar target/uberjar/virtual-pet-0.1.0-SNAPSHOT-standalone.jar
* Docker
    * docker build -t jfarnault/virtual-pet .
    * docker run --rm -p 4000:4000 jfarnault/virtual-pet
    
# DOCKER

* List images: docker images
* Remove unused resources: docker system prune
* docker image xxx xxx
* Remove unused images: docker image prune
* Remove all unused images: docker image prune -a

# DOCKER COMPOSE

* docker-compose up
* docker-compose down

# MONGO LOCAL

* docker pull mongo
* docker run -d -p 27017-27019:27017-27019 --name mongodb mongo:4.0.4
* docker exec -it mongodb bash
* mongo
* show dbs
* db.people.save({ firstname: "Maria", lastname: "Raboy" })
