# WorkHub - Sistema de Gestão de Coworking 🚀

O **WorkHub** é uma solução completa para a gestão de espaços de coworking, permitindo o controle de clientes, reservas de ambientes e contratação de serviços adicionais. [cite_start]Este projeto foi desenvolvido como trabalho final da disciplina de **Programação Orientada a Objetos (POO)**.

## 🎯 Objetivos do Projeto
[cite_start]O sistema consolida conceitos fundamentais de desenvolvimento em Java:
* [cite_start]**Encapsulamento e Polimorfismo**: Estruturação de entidades e especialização de espaços.
* [cite_start]**Abstração**: Uso de classes abstratas para tipos de espaços e interfaces para serviços adicionais.
* [cite_start]**Coleções Genéricas**: Manipulação eficiente de dados através de `HashMap` e `List`.
* [cite_start]**Persistência de Dados**: Salvamento automático do estado do sistema via serialização de objetos (`.dat`).
* [cite_start]**Tratamento de Exceções**: Implementação de exceções personalizadas para garantir a robustez do sistema.

## 🏗️ Estrutura de Pacotes
[cite_start]A arquitetura do projeto segue uma divisão clara por responsabilidades:



* [cite_start]`workhub.entidades`: Contém as classes de domínio como `Cliente`, `Reserva`, a classe abstrata `Espaco` e a interface `ServicoAdicional`.
* [cite_start]`workhub.controle`: Implementa as regras de negócio na classe `AdministradorSistema` e a persistência nos Repositórios.
* [cite_start]`workhub.fronteira`: Gerencia a interface textual e os menus interativos.
* [cite_start]`workhub.excecoes`: Concentra as exceções customizadas do domínio.

## 🛠️ Regras de Negócio Implementadas
[cite_start]Para garantir a integridade dos dados, o sistema segue diretrizes rígidas:
1. [cite_start]**CPF Único**: Impede cadastros duplicados no sistema.
2. [cite_start]**Sem Sobreposição**: Reservas para o mesmo espaço e data não podem colidir horários.
3. [cite_start]**Cálculo de Custos**: O valor total é calculado somando a duração da reserva (valor/hora) aos serviços adicionais.
4. [cite_start]**Vínculo de Serviços**: Serviços extras só podem ser associados a reservas ativas.
5. [cite_start]**Persistência Automática**: Todos os dados são salvos em arquivos binários ao realizar alterações.

## 📊 Relatórios Disponíveis
[cite_start]O sistema gera quatro tipos de relatórios para análise administrativa:
* [cite_start]**Reservas por Cliente**: Histórico detalhado filtrado por CPF.
* [cite_start]**Utilização de Espaços**: Contador de reservas e total de horas por ambiente.
* [cite_start]**Faturamento**: Receita gerada segmentada por dia, espaço ou cliente.
* [cite_start]**Serviços Adicionais**: Quantidade e valor arrecadado por tipo de serviço (Café, Locker, etc.).

## 💻 Como Rodar o Projeto
1. Clone este repositório.
2. Certifique-se de que o **JDK 17+** está configurado no seu ambiente.
3. [cite_start]Compile e execute a classe `Main.java`.
4. [cite_start]O sistema carregará automaticamente os arquivos `clientes.dat`, `espacos.dat` e `reservas.dat` (se existirem).

---
[cite_start]**Professor:** Raimundo Osvaldo  
[cite_start]**Instituição:** IFMA - Campus Monte Castelo
