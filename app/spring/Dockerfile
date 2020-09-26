FROM adoptopenjdk/openjdk13:alpine-jre

ENV SERVER_PORT=80 \
    SPRING_PROFILES_ACTIVE=default \
    AWS_REGION=eu-west-1 \
    ENVIRONMENT=idv-local

COPY build/libs/verification-context-spring-app-*.jar /opt/app.jar

CMD java \
  -Djava.security.egd=file:/dev/./urandom \
  -Dserver.port=${SERVER_PORT} \
  -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} \
  -Daws.region=${AWS_REGION} \
  -Denvironment=${ENVIRONMENT} \
  -Daws.dynamo.db.endpoint.uri=${AWS_DYNAMO_DB_ENDPOINT_URI} \
  -Dredis.endpoint.uri=${REDIS_ENDPOINT_URI} \
  -jar /opt/app.jar