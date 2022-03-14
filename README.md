# co2-monitor-api
Demo service for monitoring CO2 sensor data.

### usage
There are 2 options to run the service:
* run `Co2TrackerApiApplication.main` in an IDE
* run `mvn spring-boot:run` in a terminal

### db access
While the service is running, the in-memory H2 database can be accessed via http://localhost:8080/h2-console/ with the default credentials.

### REST API documentation
While the service is running, swagger-ui is accessible via http://localhost:8080/swagger-ui/index.html

### curl examples
1. add CO2 reading: `curl -X POST localhost:8080/co2readings/cities/barcelona/districts/garcia -H 'Content-Type: application/json' -H 'Authorization: Bearer eyJhbGciOiJFUzI1NiIsInR5cCI6IkpXVCJ9.eyJjaXR5SWQiOiJiYXJjZWxvbmEiLCJkaXN0cmljdElkIjoiZ2FyY2lhIn0.SRuaoG0T2FdIIiZIfg88GVWGYCJV89YtLBK24XfI330YjX6XeL-eWCtd33uaDnQJfp0_skOgMQFS7MdZmHbCEA' -d 123`
2. fetch CO2 readings: `curl localhost:8080/co2readings/cities/barcelona/districts/garcia -H 'Authorization: Bearer eyJhbGciOiJFUzI1NiIsInR5cCI6IkpXVCJ9.eyJjaXR5SWQiOiJiYXJjZWxvbmEifQ.QjHdgpqQeM0wVG0pHjEqkLH4jiQoSJpnUhqDk5vt5xx3PASJ9mJI8Qt69hVwlKpAG7kMeZ_4yddtfXSDCr-_iA'`

### auth
The services authorizes requests using the JWT token in the Authorization header. Tokens were generated on https://jwt.io/ using a manually generated ES256 key pair. Keys are stored in the `src/resources/jwt` folder. To generate new keys:
1. create key `openssl ecparam -genkey -name prime256v1 -noout -out key`
2. convert to pkcs8 `openssl pkcs8 -topk8 -inform PEM -outform PEM -in key -out key.pem -nocrypt`
3. create public key `openssl ec -in key -pubout -out key.pub`
