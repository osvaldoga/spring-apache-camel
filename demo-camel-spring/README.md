## Docker

### PostgreSQL

``docker run --name some-postgres -e POSTGRES_PASSWORD=welcome1 -d -p 5432:5432 postgres``

``docker run -e POSTGRES_PASSWORD=welcome1 -d -p 5432:5432 postgres``

``docker exec -it 5ea3f9b95452c077a4f9534f3f7f179f89c73a808250c7a8738d66d634f4a23b bash``

``psql -U postgres``

``CREATE DATABASE camel;``