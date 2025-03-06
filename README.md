change password to make ready to run

$ mvn clean install
installs packages, makes ready to run

$ mvn spring-boot:run
boots up webserver, now ready to accept http requests

to run
$ curl -X POST      -H "Content-Type: application/json"      -d '{"name": "Charlie Brown"}'      http://localhost:8080/query