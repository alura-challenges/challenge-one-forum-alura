# Challenge ONE | Back End | Alura Forum 

<p align="center" >
     <img width="200" heigth="200" src="https://user-images.githubusercontent.com/78982435/209698701-28dedb2e-855b-44b2-8872-afa45e3b35aa.png">
</p>

## Minhas boas-vindas ao projeto Fórum Alura com Java e Spring! 

#### 📃<u>*Visite a página do Challenge [Clicando aqui!](https://www.alura.com.br/challenges/oracle-one-back-end/aluraforum)*</u> 


### Tecnologias utilizadas:

- [IntellijIDEA](https://www.jetbrains.com/idea/)
- [MySql](https://www.mysql.com/)
- [Java](https://www.java.com/pt-BR/)
- [Spring Security](https://start.spring.io/)
- [Token JWT](https://jwt.io/)
- [Isomnia](https://insomnia.rest/download)

</br>

# Sobre o Projeto

<p>Neste projeto foi feita as implementações por etapas, onde a primeira etapa tinha como objetivos:</p>

<ul>
     <li>Desenvolvimento de API Rest;</li>
     <li>CRUD (Create, Read, Update, e Delete);</li>
     <li>Validações;</li>
     <li>Paginação e ordenação.</li>
</ul>

<p>A segunda etapa foquei em novos objetivos, a seguir:</p>
<ul>
     <li>Boas práticas na API;</li>
     <li>Tratamento de erros;</li>
     <li>Autenticação/Autorização;</li>
     <li>Tokens JWT.</li>
</ul>

</br>
</br>

# Funcionalidades
<p>Este projeto se refere-se a uma API de fórum, onde cada usuário através de login, pode postar um tópico no fórum e  responder tópicos. Os dados são armazenados em banco de dados.</p>

# Outras implementações
<p>Implementação de outras rotas do aplicativo:</p>
"/usuarios"
</br>
"/respostas
</br></br>



## Configuração de mensagem de erro Stacktrace

<p>Foi usado uma configuração no application.properties para que não seja enviado ao cliente mensagem de erro stacktrace. Você pode conferir a documentação <a href="https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html#appendix.application-properties.server">aqui</a>.</p>

<pre>
<code>server.error.include-stacktrace=never</code>
</pre>

## Tratamento de Erros

<p>O Bean Validation possui uma mensagem de erro para cada uma de suas anotações.</p>
<p>Essas mensagens de erro não foram definidas na aplicação, pois são mensagens de erro padrão do próprio Bean Validation. Entretanto, existe a possibilidade de personalizar tais mensagens.</p>

<p>Uma das maneiras de personalizar as mensagens de erro é adicionar o atributo message nas próprias anotações de validação:</p>

<pre>
     <code>
          public record DadosCadastroTopico(
          Long id,
               @NotBlank(message = "O título é obrigatório")
               String titulo,
               @NotBlank(message = "É obrigatório descrever uma mensagem")
               String mensagem,

               @Column(name = "data_criacao")
               LocalDateTime dataCriacao,

               StatusTopico status,

               Usuario autor,

               Curso curso ) {

               }
     </code>
</pre>

<p>Outra maneira é isolar as mensagens em um arquivo de propriedades, que deve possuir o nome ValidationMessages.properties e ser criado no diretório src/main/resources:</p>

<pre>
     <code>
     titulo.obrigatorio=O título é obrigatório.
     mensagem.obrigatoria=É obrigatório descrever uma mensagem.
     </code>
</pre>

<p>E, nas anotações, indicar a chave das propriedades pelo próprio atribuo "message", delimitando com os caracteres "{}":</p>

<pre>
     <code>
          public record DadosCadastroTopico(
               Long id,
               @NotBlank(message = "{titulo.obrigatorio}")
               String titulo,
               @NotBlank(message = "{mensagem.obrigatoria}")
               String mensagem,

               @Column(name = "data_criacao")
               LocalDateTime dataCriacao,

               StatusTopico status,

               Usuario autor,

               Curso curso ) {

               }
     </code>
</pre>

## Autenticação e Autorização

<p>O Spring contém um módulo específico para tratar de segurança, conhecido como <strong>Spring Security</strong>.</p>
<p>Um dos objetivos do Security é providenciar um serviço para customização de como será o controle de autenticação no projeto. Isto é, como os usuários efetuam login na aplicação.</p>
<p>O Spring Security possui, também, a autorização, sendo o controle de acesso para liberação da requisição na API ou para efetuar um controle de permissão.</p>
<p>Há, também, um mecanismo de proteção contra os principais ataques que ocorre em uma aplicação, como o CSRF (Cross Site Request Forgery) e o clickjacking.</p>

## JWT - JSON Web Tokens

<p>É usado no projeto o "JWT - JSON Web Tokens" como protocolo padrão para lidar com o gerenciamento dos tokens - geração e armazenamento de informações nos tokens.</p>
<p>Para mais informações, acesse <a href="https://jwt.io/">aqui</a>.</p>
<p>Existem diversas formas de se realizar o processo de autenticação e autorização em aplicações Web e APIs Rest. Você pode conferir <a href="https://www.alura.com.br/artigos/tipos-de-autenticacao">aqui</a>.</p>

## Sobre Filters

<p>Filter é um dos recursos que fazem parte da especificação de Servlets, a qual padroniza o tratamento de requisições e respostas em aplicações Web no Java. Ou seja, tal recurso não é específico do Spring, podendo assim ser utilizado em qualquer aplicação Java.</p>
<p>É um recurso muito útil para isolar códigos de infraestrutura da aplicação, como, por exemplo, segurança, logs e auditoria, para que tais códigos não sejam duplicados e misturados aos códigos relacionados às regras de negócio da aplicação.</p>
<p>Para criar um Filter, basta criar uma classe e implementar nela a interface Filter (pacote jakarta.servlet). Por exemplo:</p>

<pre>
     <code>
          @WebFilter(urlPatterns = "/api/**")
          public class LogFilter implements Filter {

          @Override
          public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
               System.out.println("Requisição recebida em: " + LocalDateTime.now());
               filterChain.doFilter(servletRequest, servletResponse);
          }

          }
     </code>
</pre>

<p>O método doFilter é chamado pelo servidor automaticamente, sempre que esse filter tiver que ser executado, e a chamada ao método filterChain.doFilter indica que os próximos filters, caso existam outros, podem ser executados. A anotação @WebFilter, adicionada na classe, indica ao servidor em quais requisições esse filter deve ser chamado, baseando-se na URL da requisição.</p>

<p>Neste projeto, foi utilizado outra maneira de implementar um filter, usando recursos do Spring.</p>

## Mudanças na versão 3.1

<p>A partir da versão 3.1 do Spring Boot algumas mudanças foram realizadas, em relação às configurações de segurança. Caso você esteja utilizando o Spring Boot nessa versão, ou em versões posteriores, o código neste projeto pode apresentar um aviso de deprecated, por conta de tais mudanças.</p>
<p>A partir dessa versão, o método securityFilterChain deve ser alterado para:</p>

<code>
     <pre>
     @Bean
     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
     return http.csrf(csrf -> csrf.disable())
               .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
               .authorizeHttpRequests(req -> {
                    req.requestMatchers(HttpMethod.POST, "/login").permitAll();
                    req.anyRequest().authenticated();
               })
               .addFilterBefore(new UsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
               .build();
     }
     </pre>
</code>

## Controle de acesso por url

<p>Na aplicação utilizada, não tem perfis de acessos distintos para os usuários. Entretanto, esse recurso é utilizado em algumas aplicações e podemos indicar ao Spring Security que determinadas URLs somente podem ser acessadas por usuários que possuem um perfil específico.</p>
<p>Por exemplo, suponha que em na aplicação tenha um perfil de acesso chamado de ADMIN, sendo que somente usuários com esse perfil possam excluir tópicos e cursos. Podemos indicar ao Spring Security tal configuração alterando o método securityFilterChain, na classe SecurityConfigurations, da seguinte maneira:</p>

<code>
     <pre>
          @Bean
          public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
          return http.csrf().disable()
               .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
               .and().authorizeHttpRequests()
               .requestMatchers(HttpMethod.POST, "/login").permitAll()
               .requestMatchers(HttpMethod.DELETE, "/topicos").hasRole("ADMIN")
               .requestMatchers(HttpMethod.DELETE, "/cursos").hasRole("ADMIN")
               .anyRequest().authenticated()
               .and().addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
               .build();
          }
     </pre>
</code>

<p>Observa-se que no código anterior foram adicionadas duas linhas, indicando ao Spring Security que as requisições do tipo DELETE para as URLs /topicos e /cursos somente podem ser executadas por usuários autenticados e cujo perfil de acesso seja ADMIN.</p>

## Controle de acesso por anotações

<p>Outra maneira de restringir o acesso a determinadas funcionalidades, com base no perfil dos usuários, é com a utilização de um recurso do Spring Security conhecido como Method Security, que funciona com a utilização de anotações em métodos:</p>


<code>
     <pre>
          @GetMapping("/{id}")
          @Secured("ROLE_ADMIN")
          public ResponseEntity detalhar(@PathVariable Long id) {
          var topico = repository.getReferenceById(id);
          return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
          }
     </pre>
</code>

<p>No exemplo de código anterior o método foi anotado com @Secured("ROLE_ADMIN"), para que apenas usuários com o perfil ADMIN possam disparar requisições para detalhar um topico. A anotação @Secured pode ser adicionada em métodos individuais ou mesmo na classe, que seria o equivalente a adicioná-la em todos os métodos.</p>
<p>Atenção! Por padrão esse recurso vem desabilitado no spring Security, sendo que para o utilizar deve adicionar a seguinte anotação na classe Securityconfigurations do projeto:</p>

<code>
     <pre>
          @EnableMethodSecurity(securedEnabled = true)
     </pre>
</code>

<p>Para mais detalhes sobre o recurso de method security na documentação do Spring Security, disponível <a href="https://docs.spring.io/spring-security/reference/servlet/authorization/method-security.html"> aqui </a>.</p>

# Colaborador:

<strong>Daniel Lincoln</strong>

[![img](https://camo.githubusercontent.com/c00f87aeebbec37f3ee0857cc4c20b21fefde8a96caf4744383ebfe44a47fe3f/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f2d4c696e6b6564496e2d2532333030373742353f7374796c653d666f722d7468652d6261646765266c6f676f3d6c696e6b6564696e266c6f676f436f6c6f723d7768697465)](https://www.linkedin.com/in/daniellincolndev/)

