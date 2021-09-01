FROM openjdk:12

EXPOSE 8080

# Timezone
ENV TZ=America/Belem
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

## Add the wait script to the image
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.7.3/wait /wait
RUN chmod +x /wait

RUN mkdir /app
COPY ./build/libs/*.jar /app/spring-boot-application.jar

CMD /wait && java -jar /app/spring-boot-application.jar
