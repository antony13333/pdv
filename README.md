README — API PDV

Este repositório contém uma API REST desenvolvida em Spring Boot, criada como parte do meu aprendizado em Java. O projeto foi construído utilizando recursos do Java 8, com foco em manter o código moderno, enxuto e funcional.

A API está conectada a um banco de dados relacional PostgreSQL, e ambos (API e banco) estão com deploy na plataforma Railway.

Tecnologias e Ferramentas Utilizadas:
	•	Java 8
	•	Spring Boot
	•	JPA / Hibernate para gerenciamento do banco de dados
	•	PostgreSQL
	•	Railway para deploy da aplicação e banco
	•	Records, interfaces e boas práticas de organização em pacotes para manter o projeto limpo e modular

Estrutura e Funcionamento:
	•	O sistema foi desenvolvido para gerenciar comandas de um restaurante, permitindo operações como:
	•	Criação de comandas
	•	Adição de produtos com quantidade
	•	Cálculo automático do valor total
	•	Atualização e fechamento de comandas
	•	Gerenciamento de pedidos
	•	Armazenamento de notas
	•	Histórico de aberturas e fechamentos de caixa
	•	Utiliza várias tabelas intermediárias e relações de diversos tipos para manter uma lógica organizada no banco de dados, evitando repetições de dados.
	•	A lógica de negócio garante que os produtos reais não sejam duplicados ou excluídos ao manipular uma comanda.

Consultas Complexas e Repositórios Customizados

Para atender às necessidades de consultas mais elaboradas no banco de dados, foram utilizadas interfaces de repositórios definidas com Spring Data JPA. Além dos métodos padrão, utilizei também consultas personalizadas por meio de JPQL e queries nativas, facilitando o acesso e a manipulação dos dados de forma clara e organizada.

Integração com o Front-End

Esta API está integrada ao meu outro repositório, o pdv front, onde desenvolvi o front-end da aplicação. Para mais detalhes sobre a interface e as interações, consulte o README do repositório pdv front.