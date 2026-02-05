# WorkHub - Sistema de Gestão de Coworking 🚀

O **WorkHub** é uma solução completa para a gestão de espaços de coworking, permitindo o controle de clientes, reservas de ambientes e contratação de serviços adicionais. [cite_start]Este projeto foi desenvolvido como trabalho final da disciplina de **Programação Orientada a Objetos (POO)**[cite: 20, 22].

## 🎯 Objetivos do Projeto
[cite_start]O sistema consolida conceitos fundamentais de desenvolvimento em Java[cite: 26]:
* [cite_start]**Encapsulamento e Polimorfismo**: Estruturação de entidades e especialização de espaços[cite: 9, 82].
* [cite_start]**Abstração**: Uso de classes abstratas para tipos de espaços e interfaces para serviços adicionais[cite: 79, 81, 86].
* [cite_start]**Coleções Genéricas**: Manipulação eficiente de dados através de `HashMap` e `List`[cite: 10, 98, 176].
* [cite_start]**Persistência de Dados**: Salvamento automático do estado do sistema via serialização de objetos (`.dat`)[cite: 11, 70, 102].
* [cite_start]**Tratamento de Exceções**: Implementação de exceções personalizadas para garantir a robustez do sistema[cite: 12, 71, 103].

## 🏗️ Estrutura de Pacotes
[cite_start]A arquitetura do projeto segue uma divisão clara por responsabilidades[cite: 115, 116]:



* [cite_start]`workhub.entidades`: Contém as classes de domínio como `Cliente`, `Reserva`, a classe abstrata `Espaco` e a interface `ServicoAdicional`[cite: 118, 122, 128].
* [cite_start]`workhub.controle`: Implementa as regras de negócio na classe `AdministradorSistema` e a persistência nos Repositórios[cite: 90, 93, 133].
* [cite_start]`workhub.fronteira`: Gerencia a interface textual e os menus interativos[cite: 104, 139].
* [cite_start]`workhub.excecoes`: Concentra as exceções customizadas do domínio[cite: 148, 237].

## 🛠️ Regras de Negócio Implementadas
[cite_start]Para garantir a integridade dos dados, o sistema segue diretrizes rígidas[cite: 62]:
1. [cite_start]**CPF Único**: Impede cadastros duplicados no sistema[cite: 63].
2. [cite_start]**Sem Sobreposição**: Reservas para o mesmo espaço e data não podem colidir horários[cite: 64, 68].
3. [cite_start]**Cálculo de Custos**: O valor total é calculado somando a duração da reserva (valor/hora) aos serviços adicionais[cite: 65, 85].
4. [cite_start]**Vínculo de Serviços**: Serviços extras só podem ser associados a reservas ativas[cite: 66].
5. [cite_start]**Persistência Automática**: Todos os dados são salvos em arquivos binários ao realizar alterações[cite: 70, 102, 217].

## 📊 Relatórios Disponíveis
[cite_start]O sistema gera quatro tipos de relatórios para análise administrativa[cite: 15, 226]:
* [cite_start]**Reservas por Cliente**: Histórico detalhado filtrado por CPF[cite: 228, 229].
* [cite_start]**Utilização de Espaços**: Contador de reservas e total de horas por ambiente[cite: 230, 231].
* [cite_start]**Faturamento**: Receita gerada segmentada por dia, espaço ou cliente[cite: 232, 233].
* [cite_start]**Serviços Adicionais**: Quantidade e valor arrecadado por tipo de serviço (Café, Locker, etc.)[cite: 234, 235].

## 💻 Como Rodar o Projeto
1. Clone este repositório.
2. Certifique-se de que o **JDK 17+** está configurado no seu ambiente.
3. [cite_start]Compile e execute a classe `Main.java`[cite: 156, 159].
4. [cite_start]O sistema carregará automaticamente os arquivos `clientes.dat`, `espacos.dat` e `reservas.dat` (se existirem)[cite: 222, 223, 224, 225].

---
[cite_start]**Professor:** Raimundo Osvaldo [cite: 5]  
[cite_start]**Instituição:** IFMA - Campus Monte Castelo [cite: 4]
