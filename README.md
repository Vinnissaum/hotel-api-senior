<h1 align="center">Hotel API</h1>

## Schema
``` sql
CREATE TABLE guest (
    id UUID NOT NULL PRIMARY KEY,
    name VARCHAR NOT NULL,
    document VARCHAR(11),
    phone VARCHAR(20)
);

CREATE TABLE checkin (
    id UUID NOT NULL PRIMARY KEY,
    arrival_at TIMESTAMP NOT NULL,
    left_at TIMESTAMP,
    vehicle_additional_cost BOOLEAN,
    guest_id UUID,
    FOREIGN KEY (guest_id) REFERENCES guest (id) ON DELETE SET NULL
);
```

## Tecnologias utilizadas
- [Spring](https://spring.io/)
- [Hibernate](https://hibernate.org/)
- [Flyway](https://flywaydb.org/)
- [PostgreSQL](https://www.postgresql.org/)
- [QueryDSL](http://querydsl.com/)
- [Docker](https://www.docker.com/)
- [Docker-Compose](https://docs.docker.com/compose/)

## Endpoints
### Para facilitar, a coleção do [Insomnia](https://insomnia.rest/download) utilizada em desenvolvimento está em [arquivo](./insomnia/Insomnia_2023-09-15.json), basta importá-la.
- #### Lista os hóspedes
```http
GET /guests
```
- #### Busca hóspede por ID
```http
GET /guests/{id}
```
- #### Cria hóspede
```http
POST /guests
```
- #### Atualiza hóspede informando ID
```http
PUT /guests/{id}
```
- #### Deleta hóspede informando ID
```http
DELETE /guests/{id}
```
- #### Lista os Check-ins
```http
GET /checkIns
```
- #### Busca Check-in por ID
```http
GET /checkIns/{id}
```
- #### Filtra Check-in pelo nome, documento ou telefone do hóspede
```http
GET /checkIns/getCheckIn?guestName={nome}
GET /checkIns/getCheckIn?guestDocument={documento}
GET /checkIns/getCheckIn?guestPhone={telefone}
```
- #### Busca Check-ins que ainda estão ativos
```http
GET /checkIns/getActive
```
- #### Busca Check-ins que já foram feitos o checkout
```http
GET /checkIns/getInactive
```
- #### Realiza o Check-in informando o hóspede
```http
POST /checkIns/getInactive
```
- #### Realiza o Checkout informando ID do Check-in
```http
POST /checkout/{id}
```

## Requisitos para executar o projeto
- Ter JDK 11 e maven instalados
- Clonar o projeto
- Criar um database no Postgresql e configurar a conexão em [application.properties](./src/main/resources/application.properties)
- Realizar o build ou executar em alguma IDE.

## Executando alternativamente com Docker
```bash
$ docker compose up 
```
- A API estará disponível em `http://localhost:8081` e o banco de dados em `http://localhost:3333`.

