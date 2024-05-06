# projetoAT3-N1PCD

## Sistema de Simulação de Reserva e Gerenciamento de Quartos de Hotel

## Objetivo

Este projeto simula um sistema de reserva e gerenciamento de quartos de hotel utilizando threads para representar diversas entidades envolvidas no processo. Ele visa criar uma representação realista das operações de um hotel, incluindo check-in de hóspedes, alocação de quartos, limpeza por camareiras, interações com recepcionistas e o gerenciamento de cenários como espera de hóspedes, acomodação de grupos e coordenação de limpeza de quartos.



## Implementação

O sistema é implementado usando Java 17 e consiste nas seguintes classes:

--------------------------------------------------------------------------------------------------------------------------------------------------
**Classe Room**
* Representa um quarto de hotel com atributos como número, status de ocupação, capacidade, disponibilidade de chave e status de limpeza.

    * **Variáveis da Classe:**
        * **`number` (final int):** Armazena o número do quarto (valor final, não pode ser alterado após a criação do objeto).
        * **`occupied` (boolean):** Indica se o quarto está ocupado (true) ou desocupado (false).
        * **`capacity` (final int):** Capacidade máxima de hóspedes no quarto (valor final, não pode ser alterado após a criação do objeto).
        * **`guestsCount` (int):** Número de hóspedes atualmente ocupando o quarto.
        * **`keyAvailable` (boolean):** Indica se a chave do quarto está disponível na recepção (true) ou com algum hóspede (false).
        * **`cleaningInProgress` (boolean):** Indica se a limpeza do quarto está em andamento (true) ou finalizada (false).

    * **Métodos da Classe:**    
        * **`Room(int number)` (Constructor):** Inicializa o objeto quarto com seu número, definindo valores padrão para as demais variáveis.
        * **`checkIn(int numOfGuests) throws InterruptedException` (synchronized):**  Simula o check-in de hóspedes no quarto.
        * **`checkOut(int numOfGuests)` (synchronized):** Simula o check-out de hóspedes do quarto.    
        * **`leaveKeyAtReception()` (synchronized):** Simula o hóspede deixando a chave na recepção.
        * **`cleanRoom() throws InterruptedException` (synchronized):** Simula a limpeza do quarto pela camareira.
        * **`isOccupied()`:** Consulta o estado de ocupação do quarto.
        * **`getNumber()`:** Retorna o número do quarto.
        * **`getCapacity()`:** Retorna a capacidade máxima de hóspedes do quarto.
        * **`isKeyAvailable()`:** Consulta se a chave do quarto está
      
--------------------------------------------------------------------------------------------------------------------------------------------------
**Classe Guest**
* Representa um hóspede que chega ao hotel, procura um quarto, faz check-in e, potencialmente, check-out. Cada hóspede é simulado por uma thread separada.

    * **Variáveis da Classe:**    
        * **`name` (String):** Armazena o nome do hóspede.
        * **`rooms` (List<Room>):** Lista de referência para os quartos do hotel.
        * **`attempts` (int):** Contador de tentativas para alugar um quarto.
    
    * **Métodos da Classe:**
        * **`Guest(String name, List<Room> rooms)` (Constructor):** Inicializa o objeto hóspede com seu nome e a lista de quartos do hotel.
        * * **`run()` (Override):** Método principal do thread do hóspede. É executado repetidamente enquanto a simulação estiver rodando.
        * * **`findAvailableRoom()`:** Procura um quarto disponível na lista de quartos.


--------------------------------------------------------------------------------------------------------------------------------------------------
**Classe Maid**
* Representa uma camareira responsável pela limpeza dos quartos que foram desocupados. Cada camareira é simulada por uma thread separada.

    * **Variáveis da Classe:**
        * **`name` (final String):** Armazena o nome da camareira.
        * **`rooms` (List<Room>):** Lista de referência para os quartos do hotel.
     
    * **Métodos da Classe:**
        * **`Maid(String name, List<Room> rooms)` (Constructor):** Inicializa o objeto camareira com seu nome e a lista de quartos do hotel.
        * **`run()` (Override):** Método principal do thread da camareira. É executado repetidamente enquanto a simulação estiver rodando.
 

--------------------------------------------------------------------------------------------------------------------------------------------------
**Classe Recepcionist**
* Representa um recepcionista que lida com o check-in de hóspedes, alocação de quartos e gerenciamento de chaves. Cada recepcionista é simulado por uma thread separada.

    * **Variáveis da Classe:**
        * **`name` (String):** Armazena o nome do recepcionista.
        * **`rooms` (List<Room>):** Lista de referência para os quartos do hotel.
     
    * **Métodos da Classe:**
        * **`Receptionist(String name, List<Room> rooms)` (Constructor):** Inicializa o objeto recepcionista com seu nome e a lista de quartos do hotel.
        * **`run()` (Override):** Método principal do thread do recepcionista. É executado repetidamente enquanto a simulação estiver rodando.

--------------------------------------------------------------------------------------------------------------------------------------------------
**Classe Hotel**
* Representa o hotel em si, gerenciando a lista de quartos, recepcionistas, camareiras e hóspedes.

   * **Variáveis da Classe:**
        * **`rooms`:** Lista de quartos do hotel.
        * **`receptionists`:** Lista de recepcionistas do hotel.
        * **`maids`:** Lista de camareiras do hotel.
        * **`guests`:** Lista de hóspedes do hotel.


    * **Métodos da Classe:**
        * `startSimulation()`: Inicia a simulação do sistema, executando as threads de recepcionistas, camareiras e hóspedes.

--------------------------------------------------------------------------------------------------------------------------------------------------
**Classe HotelSimulation**
* Classe principal do projeto, responsável por criar as instâncias de `Hotel`, `Receptionist`, `Maid` e `Guest`, e iniciar a simulação.

    * **Métodos da Classe:**
        * `main(String[] args)`: Ponto de entrada da aplicação. Cria as instâncias de `Hotel`, `Receptionist`, `Maid` e `Guest`, configura o número de threads e inicia a simulação.
---------------------------------------------------------------------

## Funcionalidades Principais

* **Check-in de Hóspedes e Alocação de Quartos:**
    * Os hóspedes chegam ao hotel e interagem com os recepcionistas.
    * Os recepcionistas verificam os quartos disponíveis e alocam quartos aos hóspedes com base na capacidade e disponibilidade.
    * Os hóspedes fazem check-in nos quartos designados.

* **Ocupação do Quarto e Gerenciamento de Chave:**
    * Os quartos têm capacidade máxima para 4 hóspedes.
    * Cada quarto possui uma única chave que é gerenciada pelo recepcionista.
    * Os hóspedes deixam suas chaves na recepção quando saem do quarto ou fazem check-out.

* **Limpeza por Camareira e Disponibilidade do Quarto:**
    * As camareiras monitoram continuamente a lista de quartos procurando por quartos que foram desocupados e estão prontos para limpeza.
    * As camareiras entram em quartos que estão desocupados e têm suas chaves disponíveis na recepção.
    * Os quartos são marcados como indisponíveis durante a limpeza.
    * Uma vez finalizada a limpeza, os quartos ficam disponíveis para novos hóspedes.

* **Fila de Espera de Hóspedes:**
    * Uma fila de espera de hóspedes é implementada para lidar com situações em que os quartos não estão disponíveis na chegada do hóspede.
    * Os hóspedes são adicionados à fila e notificados quando um quarto adequado se torna disponível.

* **Acomodação em Grupo:**
    * Grupos de hóspedes com mais de 4 membros são divididos em vários quartos com base na disponibilidade.
    * Cada membro do grupo é designado a um quarto, garantindo que o grupo seja acomodado junto.

* **Tratamento de Erros e Recuperação:**
    * Mecanismos robustos de tratamento de erros são implementados para lidar com exceções potenciais durante a execução das threads.
    * Cenários como erros de acesso ao quarto, interrupções inesperadas de thread e tempos limite de espera do hóspede são abordados.
---------------------------------------------------------------------

**Configuração e Execução do Projeto**

1. Compile os arquivos fonte Java:
    * `Guest.java`
    * `Hotel.java`
    * `Room.java`
    * `Maid.java`
    * `Receptionist.java`
    * `HotelSimulation.java`

2. Execute a simulação usando o comando:
    * `java HotelSimulation`
  
