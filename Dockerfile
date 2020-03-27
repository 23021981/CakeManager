FROM openjdk:8
ADD target/CakeManager-0.0.1-SNAPSHOT.jar CakeManager.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","CakeManager.jar"]