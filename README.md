# Investment Aggregator

Um agregador de investimentos desenvolvido com Spring Boot e MySQL, fornecendo uma API REST para gerenciamento de usuários.

##  Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **MySQL 8.0**
- **Docker & Docker Compose**
- **Lombok**
- **Maven**

##  Pré-requisitos

- Docker Desktop instalado e rodando
- Java 17+ instalado
- IDE de sua preferência (IntelliJ IDEA, VS Code, etc.)
- Postman ou similar para testes de API

##  Como Executar o Projeto

### 1. Clonar o Repositório
```bash
git clone https://github.com/Eduardolimzz/investment-aggregator.git
cd investment-aggregator
```

### 2. Iniciar o Docker Desktop
Certifique-se que o Docker Desktop está aberto e rodando.

### 3. Subir o Banco de Dados
```bash
docker-compose up -d
```

Para verificar se os containers estão rodando:
```bash
docker-compose ps
```

### 4. Executar a Aplicação
Execute a classe `InvestmentaggregatorApplication` na sua IDE ou via command line:
```bash
./mvnw spring-boot:run
```

##  API Endpoints

Base URL: `http://localhost:8080/v1/users`

### Criar Usuário
```http
POST /v1/users
Content-Type: application/json

{
    "username": "eduardo_teste",
    "email": "eduardo@email.com",
    "password": "senha123"
}
```

### Listar Todos os Usuários
```http
GET /v1/users
```

### Buscar Usuário por ID
```http
GET /v1/users/{userId}
```

### Atualizar Usuário
```http
PUT /v1/users/{userId}
Content-Type: application/json

{
    "username": "novo_nome",
    "password": "nova_senha"
}
```

### Deletar Usuário
```http
DELETE /v1/users/{userId}
```
##  Testes Unitários

O projeto inclui uma suíte completa de testes unitários para a camada de serviço, garantindo a qualidade e confiabilidade do código.

### Cobertura de Testes

- **UserService:** 100% de cobertura de métodos e linhas
- **Todas as operações CRUD testadas**
- **Cenários de sucesso e erro contemplados**

### Estrutura dos Testes

```
src/test/java/investment/aggregator/investmentaggregator/
└── service/
    └── UserServiceTest.java         
```

### Funcionalidades Testadas

####  Create User (`createUser`)
- **Sucesso:** Criação de usuário com dados válidos
- **Erro:** Tratamento de exceções durante criação

####  Get User by ID (`getUserById`)  
- **Sucesso:** Busca usuário existente (Optional presente)
- **Vazio:** Busca usuário inexistente (Optional vazio)

####  List Users (`listUsers`)
- **Sucesso:** Retorna lista de todos os usuários

####  Delete User (`deleteById`)
- **Sucesso:** Deleta usuário existente
- **Proteção:** Não executa delete quando usuário não existe

####  Update User (`updateUserById`)
- **Sucesso:** Atualiza usuário existente com novos dados
- **Proteção:** Não executa update quando usuário não existe

### Tecnologias de Teste

- **JUnit 5:** Framework principal de testes
- **Mockito:** Mocking de dependências
- **MockitoExtension:** Integração com JUnit 5
- **ArgumentCaptor:** Captura e validação de argumentos

### Executar os Testes

#### Via IDE
1. Navegue até `src/test/java/`
2. Clique com botão direito em `UserServiceTest`
3. Selecione "Run Tests"

#### Via Command Line
```bash
# Executar todos os testes
./mvnw test

# Executar apenas testes do UserService
./mvnw test -Dtest=UserServiceTest

# Executar com relatório de cobertura
./mvnw test jacoco:report
```

### Padrão de Testes (AAA)

Todos os testes seguem o padrão **Arrange, Act, Assert**:

```java
@Test
void shouldCreateAUserWithSuccess() {
    //Arrange: Preparar dados de teste e mocks
    var input = new CreateUserDto("username", "email@test.com", "password");
    doReturn(user).when(userRepository).save(any());
    
    //Act: Executar o método a ser testado
    var output = userService.createUser(input);
    
    //Assert: Verificar os resultados
    assertNotNull(output);
    assertEquals(input.getUsername(), output.getUsername());
}
```

### Verificação de Cobertura

O projeto mantém **100% de cobertura** na camada de serviço:
- **Classes:** 100% (1/1)
- **Métodos:** 100% (5/5) 
- **Linhas:** 100% (14/23)

### Mocks e Validações

- **Repository:** Totalmente mocado para isolamento
- **ArgumentCaptor:** Validação de parâmetros passados
- **Verify:** Confirmação de chamadas aos métodos
- **Times:** Verificação da quantidade de execuções

##  Acessar o Banco de Dados

Para verificar os dados diretamente no MySQL:

### 1. Conectar ao Container MySQL
```bash
docker exec -it investmentaggregator-mysql-1 mysql -u springuser -p
```

**Senha:** `ThePassword`

### 2. Comandos MySQL Úteis
```sql
-- Mostrar databases
SHOW DATABASES;

-- Selecionar o database
USE db_example;

-- Mostrar tabelas
SHOW TABLES;

-- Ver dados dos usuários
SELECT * FROM tb_users;

-- Ver estrutura da tabela
DESCRIBE tb_users;
```

##  Estrutura do Projeto

```
src/main/java/investment/aggregator/investmentaggregator/
├── controller/
│   └── UserController.java       # Endpoints REST
├── dto/
│   ├── CreateUserDto.java       # DTO para criação
│   └── UpdateUserDto.java       # DTO para atualização
├── entity/
│   └── User.java                # Entidade JPA
├── repository/
│   └── UserRepository.java      # Interface de acesso a dados
├── service/
│   └── UserService.java         # Lógica de negócio
└── InvestmentaggregatorApplication.java
```

##  Exemplo de Teste no Postman

1. **Criar um usuário:**
   - Method: `POST`
   - URL: `http://localhost:8080/v1/users`
   - Body (JSON):
   ```json
   {
       "username": "teste",
       "email": "teste@email.com", 
       "password": "teste123456"
   }
   ```

2. **Listar usuários:**
   - Method: `GET`
   - URL: `http://localhost:8080/v1/users`

## 🛠️ Solução de Problemas

### Erro de Conexão com MySQL
- Verifique se o Docker está rodando
- Execute `docker-compose ps` para ver se os containers estão up
- Aguarde alguns segundos após `docker-compose up -d` para o MySQL inicializar

### Porta 8080 já em uso
```bash
# Verificar o que está usando a porta
netstat -ano | findstr :8080

# Ou alterar a porta no application.properties
server.port=8081
```

### Logs da Aplicação
Para ver logs detalhados, adicione no `application.properties`:
```properties
logging.level.investment.aggregator=DEBUG
```

##  Funcionalidades da Entidade User

- **ID único:** Gerado automaticamente via UUID
- **Timestamps automáticos:** Criação e atualização registradas automaticamente
- **Validação:** Campos obrigatórios validados
- **Mapeamento JPA:** Tabela `tb_users` no banco `db_example`

##  Contribuição

1. Faça um fork do projeto
2. Crie sua feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'feat: Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request
