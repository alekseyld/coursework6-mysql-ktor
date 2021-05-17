FROM openjdk:8-jdk
EXPOSE 8080:8080
RUN mkdir /app
COPY ./build/install/ugntu-database-coursework/ /app/
WORKDIR /app/bin
CMD ["./ugntu-database-coursework"]