Sobre o Swagger ui

No Swagger ui é possível interagir com o backend sem o frontend. la tbm diz o que ele espera de cada endpoint e o que ele retorna. Para acessar basta colocar o backend para rodar (rodando esse arquivo: \backend\horte-api\src\main\java\com\horte\horte_api\HorteApiApplication.java) e entrar nesse link: http://localhost:8080/swagger-ui.html

-----------------------------------------------
Configurando o banco de dados:

Para que o backend ache o banco de dados ele deve estar rodando localmente no pc. Você deve então ir no arquivo: \backend\horte-api\src\main\resources\application.properties e trocar as seguintes informações para as do seu banco de dados logo no começo:

spring.datasource.url=jdbc:postgresql://localhost:5432/horteDB <-- Troque horteDB pelo nome do seu banco de dados
spring.datasource.username=postgres <-- esse é usuário padrão do postgre troque pelo seu
spring.datasource.password=postgres <-- essa é a senha padrão do postgre troque pela sua

-----------------------------------------------
Sobre o banco de dados:

Use o arquivo cricao.sql para criar as tabelas. As tabelas roles e usuario_roles são criadas quando o backend é executado pela primeira vez.

De preferência insira os dados pelo backend (tirando as plantas que são muitas e os guias de exemplo), mas se for usar os do drive use na seguinte ordem:

usuarios -> plantas -> guias -> posts -> comentários -> roles

Para atribuir roles a um usuário use:
INSERT INTO usuario_roles (usuario_id, role_id) VALUES (id_usuario, id_role);

Para fazer a associação entre guias e plantas use o backend.

Para criar novos usuários crie pelo backend, pois a senha armazena no banco de dados é criptografada por ele então se você criar um usuário diretamente no banco de dados não será possível logar nele (como é o caso do usuário de id 1).

-----------------------------------------------
Sobre autenticação:

Existem 3 tipos de usuário no código do enum no backend, mas só 2 são usados (user e admin) o 3º (moderação) optei por não usar para simplificar.

Todos os usuários são registrados com o role user, para adicionar um administrador tem que inserir diretamente no banco de dados na tabela usuario_roles passando o id de um usuario ja existente.

Lembrando que para acessar as funcionalidades é preciso estar logado. Quando você loga na resposta vem algumas informações entre elas um token. Forneça sempre o token nas requisições para elas funcionarem. Se estiver testando no Swagger ui após logar vá em autenticação no canto superior direito a página e coloque o token lá para logar.

-----------------------------------------------
Como passar o token de autenticação no frontend

Basta colocar ele no header

 const response = await fetch(`${API_URL}/plantas`, 
       {
         method: 'GET',
         headers: {
           'Authorization': `Bearer ${token}`, <-- AQUI (tem colocar bem assim com o Bearer antes)
       },
 });

Guarde a resposta do login porque ela também passa outras informações uteis que precisarão ser usadas em outros endpoints (como id e nome do usuário logado para pegar a imagem do usuário e colocar num post por exemplo)

-----------------------------------------------