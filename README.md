# WorkHub - Sistema de Gestão de Coworking

O **WorkHub** é uma solução completa para a gestão de espaços de coworking, permitindo o controle de clientes, reservas de ambientes e contratação de serviços adicionais. Este projeto foi desenvolvido como trabalho final da disciplina de **Programação Orientada a Objetos (POO)**.

## 🎯 Objetivos do Projeto
O sistema consolida conceitos fundamentais de desenvolvimento em Java:
* **Encapsulamento e Polimorfismo**: Estruturação de entidades e especialização de espaços.
* **Abstração**: Uso de classes abstratas para tipos de espaços e interfaces para serviços adicionais.
* **Coleções Genéricas**: Manipulação eficiente de dados através de `HashMap` e `List`.
* **Persistência de Dados**: Salvamento automático do estado do sistema via serialização de objetos (`.dat`).
* **Tratamento de Exceções**: Implementação de exceções personalizadas para garantir a robustez do sistema.

## 🏗️ Estrutura de Pacotes
A arquitetura do projeto segue uma divisão clara por responsabilidades:



* `workhub.entidades`: Contém as classes de domínio como `Cliente`, `Reserva`, a classe abstrata `Espaco` e a interface `ServicoAdicional`.
* `workhub.controle`: Implementa as regras de negócio na classe `AdministradorSistema` e a persistência nos Repositórios.
* `workhub.fronteira`: Gerencia a interface textual e os menus interativos.
* `workhub.excecoes`: Concentra as exceções customizadas do domínio.

## 🛠️ Regras de Negócio Implementadas
Para garantir a integridade dos dados, o sistema segue diretrizes rígidas:
1. **CPF Único**: Impede cadastros duplicados no sistema.
2. **Sem Sobreposição**: Reservas para o mesmo espaço e data não podem colidir horários.
3. **Cálculo de Custos**: O valor total é calculado somando a duração da reserva (valor/hora) aos serviços adicionais.
4. **Vínculo de Serviços**: Serviços extras só podem ser associados a reservas ativas.
5. **Persistência Automática**: Todos os dados são salvos em arquivos binários ao realizar alterações.

## 📊 Relatórios Disponíveis
O sistema gera quatro tipos de relatórios para análise administrativa:
* **Reservas por Cliente**: Histórico detalhado filtrado por CPF.
* **Utilização de Espaços**: Contador de reservas e total de horas por ambiente.
* **Faturamento**: Receita gerada segmentada por dia, espaço ou cliente.
* **Serviços Adicionais**: Quantidade e valor arrecadado por tipo de serviço (Café, Locker, etc.).

## 💻 Como Rodar o Projeto
1. Clone este repositório.
2. Certifique-se de que o **JDK 17+** está configurado no seu ambiente.
3. Compile e execute a classe `Main.java`.
4. O sistema carregará automaticamente os arquivos `clientes.dat`, `espacos.dat` e `reservas.dat` (se existirem).

---
**Professor:** Raimundo Osvaldo  
**Instituição:** IFMA - Campus Monte Castelo
