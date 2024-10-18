# fixr-consumer-api
Spring boot app for consumer-api using postgres (postgis and postgis_topology) and BDD with cucumber. 

# Build state
A github action workflow is in place for CI/CD.

![BUILD](https://github.com/carlospatinos/fixr-consumer-api/actions/workflows/gradle-build.yml/badge.svg)

# Requirements 
- Java 17 
- Gradle (alternatively you can use the wrapper here gradlew)
- Docker 
- Docker compose 
- PostgreSQl

## Postgres Spatial Data config
Spatial data is required to enable geo positioning calculations. As we are using postgres, we need to install and enable PostGIS extension.  
https://postgis.net/documentation/getting_started/install_macos/

Then enable that functionality on the DBMS.

```sql
CREATE EXTENSION IF NOT EXISTS postgis;
CREATE EXTENSION IF NOT EXISTS postgis_topology;

SET search_path = fixr_app, "$user", public; 

CREATE TABLE IF NOT EXISTS geofences
(
  id UUID PRIMARY KEY NOT NULL DEFAULT gen_random_uuid(),
  name TEXT NOT NULL,

  geometry_polygon GEOMETRY(POLYGON, 4326) NOT NULL,
  geometry_point GEOMETRY(POINT, 4326) NOT NULL
);

CREATE INDEX geofences_geometry_polygon_idx ON geofences USING gist(geometry_polygon);
```

Init connection in GCP
https://console.developers.google.com/apis/api/sqladmin.googleapis.com/overview?project=chefmate-438018
https://cloud.google.com/sql/docs/postgres/connect-instance-local-computer
https://medium.com/google-cloud/connecting-dbeaver-to-cloud-sql-3f672ece836b

# Run

There is 2 profiles, the test one use H2 in memory DB, the default one uses postgreSQL. You will need to have postgreSQL installed and running. Configure application.properties pointing to the DB and then execute with application as follows: 

## Default profile

```sh
./gradlew build
./gradlew bootRun
```

## Test profile

```sh
./gradlew build
./gradlew bootRun --args='--spring.profiles.active=test'
```

# Test Case 
![Geo Spatial Use Case](https://github.com/carlospatinos/fixr-consumer-api/blob/main/geojson/athloneGeoData.png?raw=true)

H2 (h2gis) is being used to simulate postgris extension. In memory DB is being used to speed test up and remove postgresql dependency.

http://localhost:8080/api/consumer/h2

# Tools

Useful tools.

End of life postgres jan 2025
https://customer.elephantsql.com/instance/create

## Drawer

To play with GeoSpatial data this is quite useful. 

https://geojson.io/#map=2/0/20
https://spatialreference.org/
https://www.cockroachlabs.com/docs/stable/srid-4326

# Documentation
http://localhost:8080/api/consumer/swagger-ui/index.html
http://localhost:8080/api/consumer/api-docs
actuator url
https://cloud.google.com/run/docs/quickstarts/build-and-deploy/deploy-java-service
https://dev.to/rushi-patel/deploy-react-app-to-google-cloud-run-with-github-actions-cicd-a-complete-guide-52pf

# TODOs
- [ ] Consider using[h2gis](https://github.com/orbisgis/h2gis) for local in memory testing