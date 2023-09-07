## Package
Package application with Eureka client dependency

```
mvn -f pom-cloud.xml clean package
```

## Run
Put jar package with registry discovery dependencies with
application-properties together and run jar

```
copy <repo>/cicd/application-properties <repo>/target
cd <repo>/target
java -jar movieapi-<tag>.jar
```