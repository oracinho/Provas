# Provas
Este projeto consiste na consutrção de um servidor para realizar a tradução de um número informado em dígitos em um número por extenso.

##
Execução
Para facilitar o teste da função, é disponibilizado o artefato ProvaCerti/dist/ProvaCerti-0.0.1-SNAPSHOT-jar-with-dependencies.jar. É possível executar esse arquivo através do prompt de comando informando como parâmetro o número da porta para subir o servidor.
Ex: java -jar ProvaCerti-0.0.1-SNAPSHOT-jar-with-dependencies.jar 3000

Se estiver tudo correto, o servidor exibe a mensagem "Servidor OK e aguardando conexões". É possível então solicitar as traduções com o seguinte endereço "localhost:<porta_informada>/[-99999;99999]".
Ex: localhost:3000/100

##
Utilização
Para se trabalhar neste projeto, é necessário ter instalado:
-Java 1.8
-Maven 4.0

O pom.xml do projeto já está configurado para compilar e gerar os pacotes, de modo que basta executa-lo após uma atualização. No entanto, ele ainda não dispara o teste automatizado, pois o plugin maven-assembly utiliza uma versão antiga do surefire, que não executa testes construídos com o JUnit 5.
