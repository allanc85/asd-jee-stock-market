# asd-jee-stock-market

### 1. Subir os servidores do MongoDB e RabbitMQ

```
cd docker
docker-compose up -d
```

### 2. Configurar email de envio no arquivo de "application.properties"

```
# >>> YOUR EMAIL INFO GOES HERE

mail.from=mayconfsousa@gmail.com
mail.password=***
```

### 3. Executar o projeto no *eclipse*

### 4. Links:

* [Swagger Docs] http://localhost:8080/swagger-ui.html
* [RabbitMQ] http://localhost:15672

### 5. Lógica de negócio

```
1. Adicionar novas "persons" :: http://localhost:8080/api/v1/persons
2. Adicionar novas "companies" :: http://localhost:8080/api/v1/companies
3. Enviar "stocks" para o mercado :: http://localhost:8080/api/v1/stocks/issue
4. Enviar solicitação de compra de "stocks" :: http://localhost:8080/api/v1/stocks/purchase
5. Comprador e vendedor recebem emails confirmando o processamento da transação
```
