FROM openjdk:8
ADD target/ticket-market.jar ticket-market.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar","ticket-market.jar"]