cd backend && chmod 777 ./gradlew && ./gradlew bootJar && cd ..
docker-compose pull

COMPOSE_DOCKER_CLI_BUILD=1 DOCKER_BUILDKIT=1 docker-compose up --build -d

# remove unused docker images
unused=$(docker images -f "dangling=true" -q)
if [ ${#result} != 0 ]
then
    docker rmi $unused -f

fi
exit 0

