FROM adoptopenjdk/openjdk15:alpine-jre

ARG VERSION

ENV SERVER_PORT=80 \
    SPRING_PROFILES_ACTIVE=default \
    ENVIRONMENT=idv-local \
    ASYNC_SIM_SWAP_THREAD_POOL_SIZE=50 \
    CONTEXT_EXPIRY_THREAD_POOL_SIZE=50 \
    EXTERNAL_DATA_TIMEOUT=2000 \
    EXTERNAL_PHONE_NUMBER_DELAY=1500 \
    EXTERNAL_EMAIL_ADDRESS_DELAY=1000 \
    RESPONSE_FILTERING_ENABLED=false \
    REQUEST_LOGGING_ENABLED=false \
    RESPONSE_LOGGING_ENABLED=false \
    POLICY_REFRESH_THREAD_POOL_SIZE=4 \
    POLICY_REFRESH_DELAY=3600000 \
    SERVER_SERVLET_CONTEXT_PATH=/

COPY build/libs/idv-context-spring-app-${VERSION}.jar /opt/app.jar

CMD java \
  -Djava.security.egd=file:/dev/./urandom \
  -Dserver.port=${SERVER_PORT} \
  -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} \
  -Denvironment=${ENVIRONMENT} \
  -Dredis.endpoint.uri=${REDIS_ENDPOINT_URI} \
  -Dspring.data.mongodb.uri=${MONGO_CONNECTION_STRING} \
  -Dasync.sim.swap.thread.pool.size=${ASYNC_SIM_SWAP_THREAD_POOL_SIZE} \
  -Dcontext.expiry.thread.pool.size=${CONTEXT_EXPIRY_THREAD_POOL_SIZE} \
  -Dexternal.data.timeout=${EXTERNAL_DATA_TIMEOUT} \
  -Dexternal.phone.number.delay=${EXTERNAL_PHONE_NUMBER_DELAY} \
  -Dexternal.email.address.delay=${EXTERNAL_EMAIL_ADDRESS_DELAY} \
  -Dresponse.filtering.enabled=${RESPONSE_FILTERING_ENABLED} \
  -Drequest.logging.enabled=${REQUEST_LOGGING_ENABLED} \
  -Dresponse.logging.enabled=${RESPONSE_LOGGING_ENABLED} \
  -Dpolicy.refresh.thread.pool.size=${POLICY_REFRESH_THREAD_POOL_SIZE} \
  -Dpolicy.refresh.delay=${POLICY_REFRESH_DELAY} \
  -Dserver.servlet.contextPath=${SERVER_SERVLET_CONTEXT_PATH} \
  -jar /opt/app.jar