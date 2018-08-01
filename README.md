# Backend as a Service

## Cenário
<p>
Um desenvolvedor mobile está desenvolvendo uma app que precisa de um backend simples, mas ele não tem conhecimento para desenvolver esse backend.<br>
O que queremos fazer é criar uma tela simples para que o desenvolvedor mobile possa previamente cadastrar os modelos do seu Domínio.<br>
Para cada modelo cadastrado a API deverá expor os serviços básicos de CRUD via padrão RESTful.
</p>

## Arquitetura
* Java 8
* Spring Boot
* H2
* Thymeleaf
* Bootstrap

## Pré requisito
* Java 8
* Git
* Maven

## Execução do backend

No "shell/prompt de comando"

Clone o projeto:
```sh
 git clone https://github.com/bangomc/xy-inc.git
```

Entre no local do projeto:
```sh
 cd xy-inc
```

Compile/empacote o projeto:
```sh
 mvn clean install
```

Execute o projeto:
```sh
 java -jar target/xy-inc-0.0.1-SNAPSHOT.jar
```

## Inclusão de um modelo de Domínio
<p>
O desenvolvedor mobile deve acessar o endereço http://localhost:8080 para incluir uma tabela com seus campos e respectivos tipos de dados.<br>
Não é preciso se preocupar com o campo ID pois a API irá incluí-lo automaticamente.
</p>

## API RESTful
Após a inclusão do modelo de Domínio, o desenvolvedor mobile terá acesso ao CRUD via padrão RESTful da seguinte forma:

GET /xxx - Lista todos os elementos do Modelo xxx<br>
GET /xxx/{id} - Busca um registro do modelo xxx por id<br>
POST /xxx - Cria um novo registro do modelo xxx<br>
PUT /xxx/{id} - Edita um registro do modelo xxx<br>
DELETE /xxx/{id} - Deleta um registo do modelo xxx<br>

Onde xxx é o nome do modelo de Domínio criado no passo anterior.
