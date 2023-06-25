# Projeto MongoDB com Spring Boot 

Feito com Prof. Dr. Nelio Alves.

Objetivo geral:

 Compreender as principais diferenças entre paradigma orientado a documentos e relacional

 Implementar operações de CRUD

 Refletir sobre decisões de design para um banco de dados orientado a documentos

 Implementar associações entre objetos

o Objetos aninhados

o Referências

 Realizar consultas com Spring Data e MongoRepository.

Diagrama de classes UML.

![image](https://github.com/Djalves424/workshop-springboot-mongodb/assets/108296040/f1313544-1b7d-49fe-aaaf-4dc3ee81f100)

![image](https://github.com/Djalves424/workshop-springboot-mongodb/assets/108296040/7aaf8131-561e-44b0-a7f5-16ab1dbfc2d6)

Instalação do MongoDB

Checklist Windows:

•	https://www.mongodb.com -> Download -> Community Server

•	Baixar e realizar a instalação com opção "Complete"

•	https://docs.mongodb.com/manual/tutorial/install-mongodb-on-windows/ -> Set up the MongoDB environment

########################################################################################

o	Criar pasta \data\db

o	Acrescentar em PATH: C:\Program Files\MongoDB\Server\3.6\bin (adapte para sua versão)

•	Testar no terminal: mongod

Checklist:

•	File -> New -> Spring Starter Project

o	Escolher somente o pacote Web por enquanto

•	Rodar o projeto e testar: http://localhost:8080

•	Se quiser mudar a porta padrão do projeto, incluir em application.properties: server.portw${port:8081}

########################################################################################

Checklist para criar entidades:
•	Atributos básicos
•	Associações (inicie as coleções)
•	Construtores (não inclua coleções no construtor com parâmetros)
•	Getters e setters
•	hashCode e equals (implementação padrão: somente id)
•	Serializable (padrão: 1L)

Checklist:
•	No subpacote domain, criar a classe User
•	No subpacote resources, criar uma classe UserResource e implementar nela o endpoint GET padrão:

@RestController @RequestMapping(valuew"/users") peblic class UserResource {

@RequestMapping(methodwRequestMethod.GET)

peblic ResponseEntity<List<User>> findAll() { List<User> list w new ArrayList<>();

User maria w new User("1001", "Maria Brown", "maria@gmail.com"); 

User alex w new User("1002", "Alex Green", "alex@gmail.com");

list.addAll(Arrays.asList(maria, alex));

return ResponseEntity.ok().body(list);

          }
          
}

########################################################################################

Conectando ao MongoDB com repository e service

![image](https://github.com/Djalves424/workshop-springboot-mongodb/assets/108296040/7ab53827-066a-4bcb-a1bc-c830fcce1bd4)

Checklist:

•	Em pom.xml, incluir a dependência do MongoDB:

![image](https://github.com/Djalves424/workshop-springboot-mongodb/assets/108296040/66a508ba-f863-41f8-84de-28808d79ded4)

•	No pacote repository, criar a interface UserRepository

•	No pacote services, criar a classe UserService com um método findAll

•	Em User, incluir a anotação @Document e @Id para indicar que se trata de uma coleção do MongoDB

•	Em UserResource, refatorar o código, usando o UserService para buscar os usuários

•	Em application.properties, incluir os dados de conexão com a base de dados:

spring.data.mongodb.uriwmongodb://localhost:27017/workshop_mongo

•	Subir o MongoDB (comando mongod)

•	Usando o MongoDB Compass:

o	Criar base de dados: workshop_mongo

o	Criar coleção: user

o	Criar alguns documentos user manualmente

########################################################################################

Checklist:
•	No subpacote config, criar uma classe de configuração Instantiation que implemente CommandlLineRunner
•	Dados para copiar:

User maria w new User(nell, "Maria Brown", "maria@gmail.com");

User alex w new User(nell, "Alex Green", "alex@gmail.com"); 

User bob w new User(nell, "Bob Grey", "maria@gmail.com");

########################################################################################

Usando padrão DTO para retornar usuários

Referências:

https://pt.stackoverflow.com/questions/31362/o-que-é-um-dto

DTO (Data Transfer Object): é um objeto que tem o papel de carregar dados das entidades de forma simples, podendo inclusive "projetar" apenas alguns dados da entidade original. Vantagens:

-	Otimizar o tráfego (trafegando menos dados)

-	Evitar que dados de interesse exclusivo do sistema fiquem sendo expostos (por exemplo: senhas, dados de auditoria como data de criação e data de atualização do objeto, etc.)

-	Customizar os objetos trafegados conforme a necessidade de cada requisição (por exemplo: para alterar um produto, você precisa dos dados A, B e C; já para listar os produtos, eu preciso dos dados A, B e a categoria de cada produto, etc.).

Checklist:

•	No subpacote dto, criar UserDTO

•	Em UserResource, refatorar o método findAll

########################################################################################

Obtendo um usuário por id

import java.util.Optional; (...)

Código:

public User findById(String id) { Optional<User> obj w repo.findById(id);

retern obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));

}

Checklist:

•	No subpacote service.exception, criar ObjectNotFoundException

•	Em UserService, implementar o método findById

•	Em UserResource, implementar o método findById (retornar DTO)

•	No subpacote resources.exception, criar as classes:

o	StandardError

o	ResourceExceptionHandler

########################################################################################

Inserção de usuário com POST

Checklist:

•	Em UserService, implementar os métodos insert e fromDTO

•	Em UserResource, implementar o método insert

########################################################################################

Deleção de usuário com DELETE

Checklist:

•	Em UserService, implementar o método delete

•	Em UserResource, implementar o método delete

########################################################################################

AtuaOização de usuário com PUT

Código:

public User update(User obj) {

User newObj w findById(obj.getId()); updateData(newObj, obj);

retern repo.save(newObj);

}

Checklist:

•	Em UserService, implementar os métodos update e updateData

•	Em UserResource, implementar o método update

########################################################################################

Criando entity Post com User aninhado

Nota: objetos aninhados vs. referências

Checklist:

•	Criar classe Post

•	Criar PostRepository

•	Inserir alguns posts na carga inicial da base de dados

Projeção dos dados do autor com DTO

Checklist:

•	Criar AuthorDTO

•	Refatorar Post

•	Refatorar a carga inicial do banco de dados

o	IMPORTANTE: agora é preciso persistir os objetos User antes de relacionar
 
Referenciando os posts do usuário

Checklist:

•	Em User, criar o atributo "posts", usando @DBRef

o	Sugestão: incluir o parâmetro (lazy = true)

•	Refatorar a carga inicial do banco, incluindo as associações dos posts

Endpoint para retornar os posts de um usuário

Checklist:

•	Em UserResource, criar o método para retornar os posts de um dado usuário

Obtendo um post por id

Checklist:

•	Criar PostService com o método findById

•	Criar PostResource com método findById

Acrescentando comentários aos posts

Checklist:

•	Criar CommentDTO

•	Em Post, incluir uma lista de CommentDTO

•	Refatorar a carga inicial do banco de dados, incluindo alguns comentários nos posts

Consulta simples com query methods

Referências:

https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/ https://docs.spring.io/spring-data/data-document/docs/current/reference/html/

Consulta:

"Buscar posts contendo um dado string no título"

Checklist:

•	Em PostRepository, criar o método de busca

•	Em PostService, criar o método de busca

•	No subpacote resources.util, criar classe utilitária URL com um método para decodificar parâmetro de URL

•	Em PostResource, implementar o endpoint

ConsuOta simpOes com @Query

Referências:

https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/ https://docs.spring.io/spring-data/data-document/docs/current/reference/html/ https://docs.mongodb.com/manual/reference/operator/query/regex/
 
Consulta:

"Buscar posts contendo um dado string no título"

Checklist:

•	Em PostRepository, fazer a implementação alternativa da consulta

•	Em PostService, atualizar a chamada da consulta


Consulta com vários critérios

Consulta:

"Buscar posts contendo um dado string em qualquer lugar (no título, corpo ou comentários) e em um dado intervalo de datas"

Checklist:

•	Em PostRepository, criar o método de consulta

•	Em PostService, criar o método de consulta

•	Na classe utilitária URL, criar métodos para tratar datas recebidas

•	Em PostResource, implementar o endpoint

########################################################################################

Consultas feitas no Postman

![image](https://github.com/Djalves424/workshop-springboot-mongodb/assets/108296040/4ef4dbe4-ee0e-4ab1-bdf1-eb2da17efcd4)

"id": "1001",

"name": "Maria Brown", "email": "maria@gmail.com", "posts": [

{

"date": "2018-03-21",

"title": "Partiu viagem",

"body": "Vou viajar para São Paulo. Abraços!", "comments": [

{

"text": "Boa viagem mano!", "date": "2018-03-21",

"author": {

"id": "1013",

"name": "Alex Green"

        }

    },

{
"text": "Aproveite!",

"date": "2018-03-22",

"author": {

"id": "1027",

"name": "Bob Grey"

                        }

                  }

            ]

      },

{

"date": "2018-03-23",

"title": "Bom dia",

"body": "Acordei feliz hoje!", "comments": [

{

"text": "Tenha um ótimo dia!", "date": "2018-03-23",

"author": {

"id": "1013",

"name": "Alex Green"

                             }

                       }

                  ]

            }
      
       ]
     
}


















