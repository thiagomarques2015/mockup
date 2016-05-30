# Mockup
Biblioteca para otimizar a construção de aplicativos em Android. Mockup versão 1.3 

## 1. Adicione o repositório JitPack no seu arquivo build 

``` gradle
allprojects {
		repositories {
			maven { url "https://jitpack.io" }
		}
	}
```


## 2. Adicione a dependência ##

``` gradle
dependencies {
		compile 'com.github.thiagomarques2015:mockup:-SNAPSHOT'
}
```


### Iniciando Mockup ###


``` java

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicia o mockup
        Mockup mockup = Mockup.getInstance().setContext(this);
        // Configura o gerenciador de fabricas
        FactoryManager mockupFactoryManager = MyFactoryManager.getInstance();
        // Adiciona no mockup o gerenciador de fabricas
        mockup.mockupFactoryManager(mockupFactoryManager);

        // Restante do codigo
    }
}
```

A classe **MyFactoryManager** ainda não foi criada e você precisa criar em seu projeto estendendo de **FactoryManager**. Veja na próxima seção como.

### Seu próprio gerenciador de fábricas ###

O padrão de projeto Fábrica, é muito utilizado para centralizar a criação de objetos de um mesmo tipo, para garantir que a criação dos objetos sejam feita por apenas um método, simplificando sua lógica de criação e evitando a descentralização em seu código. 

[Exemplo de utilização do padrão Fábrica](http://www.tutorialspoint.com/design_pattern/factory_pattern.htm)

No seu projeto, essa Classe **MyFactoryManager** precisa ser criada para permitir que sua aplicação interaja com os recursos da biblioteca. Deve ficar dessa maneira:

``` java

public class MyFactoryManager extends FactoryManager{
    private static MyFactoryManager ourInstance = new MyFactoryManager();

    public static MyFactoryManager getInstance() {
        return ourInstance;
    }

    private MyFactoryManager() {
    }
}
```

Se tudo estiver certo até aqui, seu projeto está pronto para utilizar a biblioteca :)  

###  Utilizando requisições HTTP com métodos GET e POST ### 

Para criar um gerenciador de conexão basta criar uma classe que herde de **Request<T>** passando por parâmetro a classe de contexto. Então você sobrescreverá o método **config** que recebe por parâmetro uma operação desejada. Essa classe possui alguns métodos como:

### Métodos disponíveis ###

* add - adiciona um par-valor na requisição
* remove - remove um valor da requisição
* clear - limpa todos os parâmetros da requisição
* print - printa todos os valores da requisição no console 
* getError - Retorna um código referente ao erro ocorrido


### Códigos de erro ###

* NENHUM_ERROR = 0; // HttpException
* ERROR_CONEXAO = 1; // HttpException
* ERROR_INESPERADO = 2; // RunntimeException, NullpointerException
* ERROR_SINTAXE_JSON = 3; // JSONException

Todos os códigos de erros podem ser recuperados pelo método **getError**.

A sua classe de conexão deve ficar da seguinte maneira:

``` java

public class HttpManager extends Request<HttpManager> {

    // URL do servidor 
    private static final String URL =  "url do servidor";
    // Operacoes que serao executadas utilizando HTTP 
    public static final int PUBLISH_POST = 1; // Publicar um post

    // Cria o gerenciador de classe passando a URL do servidor
    public HttpManager() {
        super(URL); 
    }

    /**
    * @param op - operacao a ser exeucutada
    */
    @Override
    public HttpManager config(int op) {

        // Limpa os parâmetros anteriores adicionado na conexão
        clear();

        // Verifica qual operacao sera executada
        switch (op){
            case PUBLISH_POST : 
                /** 
                    Adiciona no cabecalho da requisao um parametro
                     chamado 'params' que sera enviado para o servidor 
                **/
                add("params", "valor");
                // ....
                break;
        }

        return this;
    }
}
```

Feito até aqui, sua aplicação está pronta para utilizar a requisição HTTP utilizando método POST ou GET. Exemplo de utilização:

## POST ##


``` java

// Configura o objeto da conexao para executar a operacao de publicar um post
HttpManager request = new HttpManager().request(HttpManager.PUBLISH_POST);
// Executa a configuracao definida em sua classe de conexao
request.config();
// Envia o pedido para o servidor do tipo POST e espera que um JSON seja o retorno
// Se nao retornar no formato JSON o retorno sera nulo
json = request.send(HttpManager.POST);
// Recupera o que retornou do servidor em forma de String (Response)
response = request.getResponse();
```


## GET ##


``` java

// Configura o objeto da conexao para executar a operacao de publicar um post
HttpManager request = new HttpManager().request(HttpManager.PUBLISH_POST);
// Executa a configuracao definida em sua classe de conexao
request.config();
// Envia o pedido para o servidor do tipo GET e espera que um JSON seja o retorno
// Se nao retornar no formato JSON o retorno sera nulo
json = request.send(HttpManager.GET);
// Recupera o que retornou do servidor em forma de String (Response)
response = request.getResponse();
```

### Adicionando/Removendo parâmetros ### 


``` java

// Configura o objeto da conexao para executar a operacao de publicar um post
HttpManager request = new HttpManager().request(HttpManager.PUBLISH_POST);
// Adicionando um parametro 
request.add("usuario", 1);

// Removendo um parametro
request.remove("usuario");

// Executa a configuracao definida em sua classe de conexao
request.config();

// restante do codigo 
// ...

```

### Utilizando imagens com o Glide ###

O Mockup foi inspirado pelo Glide para criar um framework que utilize uma sintaxe simples para resolver problemas rotineiros utilizando imagens no Android.

[Glide](https://github.com/bumptech/glide)

Abaixo você irá ver um exemplo de como utilizar o recurso de abrir, processar e trabalhar com imagens dentro do Android utilizando o Mockup e Glide.

### Alguns métodos ###

* placeHolder - Recebe por parâmetro uma imagem **Drawable** para ser substituída em caso de erro de download da imagem principal
* open - Recebe dois parâmetros como entrada a fábrica de criação de objetos glide e uma **URL, URI ou Drawable**
* build - Recebe como parâmetro o tipo da requisicao **BITMAP** (para abrir imagens) ou **BYTES** (ler os bytes da imagem) 
* resize - Recebe como parâmetro uma **Altura** e **Largura** que deseja redimensionar a imagem como saída
* cache - Recebe como parâmetro um **timestamp** (Long) referente a data que a imagem deverá ser salva em cache para reaproveitar-la em futuras ocasiões
* circle -  Transforma a imagem em um circulo como saída
* into - Recebe como parâmetro um **ImageView** referente a view que deseja visualizar a imagem

``` java

// Importando a fabrica de objetos do tipo Glide
ObjectPool factory = MyFactoryManager.getInstance().getObjectPoolFactory().create(Constantes.Pool.GLIDE_IMAGE);

// View da imagem
ImageView photo;

// Carregando uma imagem
GlideFacade.open(factory, url)
        .placeHolder(R.drawable.photo)
        .build(GlideRequest.BITMAP)
        .resize(250, 250)
        .cache(12312312)
        .circle()
        .into(photo);
```


### Criando uma fábrica de objetos (Factory Pattern) ###


  **context** - Contexto de sua aplicação

  **file** - Nome do arquivo de texto que contém os comandos do seu aplicativo (localizado na pasta app/assets)

  **mockupFactoryManager** - Instancia singleton do gerenciador de fábricas do seu aplicativo


``` java

public class MainCommandFactory extends Factory<MainCommand> {

    public MainCommandFactory(Context context, String file, FactoryManager mockupFactoryManager) {
        super(context, file, mockupFactoryManager);
    }
}
```

Onde **MainCommand** é sua classe de modelo para comandos que serão executados. Esta classe deve herdar de **Command** localizado no Mockup:


``` java

public abstract class MainCommand implements Command {
    @Override
    public void execute() {
        // Implementação de um comando principal
    }
}
```

### Utilizando o gerenciador de fábricas e configurando ele para manipular a fábrica principal de comandos ###


``` java

MyFactoryManager mockupFactoryManager = MyFactoryManager.getInstance();
mockupFactoryManager.main(new MainFactory(context, "commands", mockupFactoryManager));
```

### Criando seu próprio facade para acionar os comandos de sua aplicação ###


``` java

public class MyCommandFacade extends CommandFacade {
    private static MyCommandFacade ourInstance = new MyCommandFacade();

    public static MyCommandFacade getInstance() {
        return ourInstance;
    }

    private MyCommandFacade() {

    }

   public MainCommand getMainCommand(){
       return (MainCommand) getCommand();
   }
}
```
## Mockup 1.1 ##

### Criando o gerenciador de conexão ###

Para criar um gerenciador de conexão basta criar uma classe que herde de **Request<T>** passando por parâmetro a classe de contexto. Então você sobrescreverá o método **config** que recebe por parâmetro uma operação desejada. Essa classe possui alguns métodos como:

* add - adiciona um par-valor na requisição
* remove - remove um valor da requisição
* clear - limpa todos os parâmetros da requisição
* print - printa todos os valores da requisição no console 
* getError - Retorna um código referente ao erro ocorrido


### Códigos de erro ###

* NENHUM_ERROR = 0; // HttpException
* ERROR_CONEXAO = 1; // HttpException
* ERROR_INESPERADO = 2; // RunntimeException, NullpointerException
* ERROR_SINTAXE_JSON = 3; // JSONException

``` java

public class HttpManager extends Request<HttpManager> {

    private static final String URL =  "url do servidor";
    public static final int PUBLISH_POST = 1;

    public HttpManager() {
        super(URL);
    }

    @Override
    public HttpManager config(int i) {

        clear();

        switch (i){
            case PUBLISH_POST :
                add("params", "valor");
                // ....
                break;
        }

        return this;
    }
}
```

## Mockup 1.2 ##

### Gerenciador de tarefas ###

Para utilizar o gerenciador de tarefas, basta chamar o facade da seguinte forma:


``` java

AsyncTaskFacade.getInstance().create(PostTask.getInstance(), "post").delegate(this).execute();
```

A classe **PostTask** deve herdar de **AsyncTask** do pacote Mockup, por exemplo:


```java

public class PostTask extends AsyncTask {

        private static final PostTask instance = new PostTask();

        public static PostTask getInstance() {
            return instance;
        }

        private PostTask(){

        }

        @Override
        public void run() {
            super.run();

            // Processar alguma coisa pesada em background

            finish("Post finalizado", null); // Msg - Mensagem enviada para o callback , Object - Objeto enviado depois que a tarefa for finalizada
        }
};
```

### Callback para tarefas ###


```java

@Override
public void onTaskCompleted(String msg, Object object) {
        
}
```


### Releases ###

Mockup 1.3

* Atualizado o projeto, criado o compile no Jitpack

Mockup 1.2

* Adicionado um gerenciador de tarefas assíncronas 

Mockup 1.1

* Adicionado classes de requisição HTTP, com suporte a requisições POST e GET
* Corrigido bugs da versão anterior


Mockup 1.0

* Implementa navegação com tabs (material design - https://developer.android.com/intl/pt-br/design/material/index.html)
* Utiliza a lib Glide (https://github.com/bumptech/glide) para abrir imagens (com design pattern) 



### How do I get set up? ###

* Summary of set up
* Configuration
* Dependencies
* Database configuration
* How to run tests
* Deployment instructions

### Contribution guidelines ###

* Writing tests
* Code review
* Other guidelines

### Who do I talk to? ###

* Repo owner or admin
* Other community or team contact