
# TODO

A simple TODO project created in order to practice tests with Spring Boot


## Run Locally

Clone the project

```bash
  git clone https://github.com/flaviobarbosa/todo
```

Go to the project directory

```bash
  cd todo
```

You can run it using Docker by simple executing the following command

``` 
  docker-compose up 
```



## API Reference

#### Swagger 
http://localhost:8080/swagger-ui/index.html

#### Get all TODOs

```http
  GET /todo
```

#### Get TODO by id

```http
  GET /todo/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of Todo to fetch |


#### Create TODO

```http
  POST /todo
```

```json
{
  "title": "Todo title",
  "description": "Todo description"
}

```

#### Update TODO

```http
  PUT /todo/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of Todo to fetch |

```json
{
  "title": "Todo title",
  "description": "Todo description"
}

```

#### Mark as done

```http
  PUT /todo/${id}/done
```

#### Delete

```http
  DELETE /todo/${id}
```
