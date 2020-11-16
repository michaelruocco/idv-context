FROM adoptopenjdk/openjdk13:alpine-jre

ENV SERVER_PORT=80 \
    SPRING_PROFILES_ACTIVE=default \
    ENVIRONMENT=idv-local \
    EXTERNAL_FIND_IDENTITY_THREAD_POOL_SIZE=100 \
    SIM_SWAP_EXECUTOR_THREAD_POOL_SIZE=100 \
    ASYNC_SIM_SWAP_THREAD_POOL_SIZE=50 \
    CONTEXT_EXPIRY_THREAD_POOL_SIZE=50 \
    RESPONSE_FILTERING_ENABLED=false \
    REQUEST_LOGGING_ENABLED=false \
    RESPONSE_LOGGING_ENABLED=false

COPY build/libs/verification-context-spring-app-*.jar /opt/app.jar

CMD java \
  -Djava.security.egd=file:/dev/./urandom \
  -Dserver.port=${SERVER_PORT} \
  -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} \
  -Denvironment=${ENVIRONMENT} \
  -Dredis.endpoint.uri=${REDIS_ENDPOINT_URI} \
  -Dspring.data.mongodb.uri=${MONGO_CONNECTION_STRING} \
  -Dexternal.find.identity.thread.pool.size=${EXTERNAL_FIND_IDENTITY_THREAD_POOL_SIZE} \
  -Dsim.swap.executor.thread.pool.size=${SIM_SWAP_EXECUTOR_THREAD_POOL_SIZE} \
  -Dasync.sim.swap.thread.pool.size=${ASYNC_SIM_SWAP_THREAD_POOL_SIZE} \
  -Dcontext.expiry.thread.pool.size=${CONTEXT_EXPIRY_THREAD_POOL_SIZE} \
  -Dresponse.filtering.enabled=${RESPONSE_FILTERING_ENABLED} \
  -Drequest.logging.enabled=${REQUEST_LOGGING_ENABLED} \
  -Dresponse.logging.enabled=${RESPONSE_LOGGING_ENABLED} \
  -jar /opt/app.jar