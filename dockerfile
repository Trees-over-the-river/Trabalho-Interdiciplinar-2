FROM maven
ENV PORT=8080
EXPOSE $PORT
WORKDIR /usr/src/app
COPY pom.xml .
RUN mvn -B -e -C -T 1C org.apache.maven.plugins:maven-dependency-plugin:3.1.2:go-offline
COPY . .
RUN mvn -B -e -o -T 1C exec:java