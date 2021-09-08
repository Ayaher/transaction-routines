# Transactions Routines

An API with transactions routines.

## Directories and Layers

API requests are handled by controllers on the `controller` directory. If an endpoint requires an specific request body rather than a simple model, such request wil be modelled on the `controller/request` directory.

The service layer (`service` directory) contains the logic behind the controllers for validation and return codes. Database acess is stored on the repository layer (`repository` directory) and is called by the service layer.

For initial data loading, such as predefined `OperationType`s, check the `startup` direcory.

## Build and Running

```
$ gradle bootJar
$ docker-compose up
```

Services:

- API will be available on `http://localhost:8080/api`
- Database (Postgres) admin will be available on `http://localhost:8081`
- Swagger documentation will be available on `http://localhost:8082`

## Testing

Tests will be done against a H2 database defined in [application-integrationtest.properties](src/main/resources/application-integrationtest.properties).

```
$ gradle test
```
