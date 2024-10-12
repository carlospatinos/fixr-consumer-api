# fixr-consumer-api
Spring boot app for consumer-api


# Postgres Spatial Data config
Spatial data is required to enable geo positioning calculations. As we are using postgres, we need to install and enable PostGIS extension.  
https://postgis.net/documentation/getting_started/install_macos/

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

# url
http://localhost:8080/api/locations/near?latitude=10&longitude=10&distance=10