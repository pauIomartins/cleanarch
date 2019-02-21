# Clean Architecture Lab

Lab para estudo da idéia de Clean Arch do Uncle Bob.

> The center of your application is not the database. 
> Nor is it one or more of the frameworks you may be using. 
> The center of your application is the use cases of your application 
> **Unclebob**

## O que é Clean Architecture?

Na _minha opinião_ Clean Architecture é uma forma de pensar, estruturar e organizar uma aplicação de software aplicando fortemente boas práticas de Programação Orientada a Objetos. Práticas como: Escrever código com **Responsabilidade Única**, classes e funções que sejam **Coesas**, possuam **Baixo Nível de Acoplamento** com suas dependencias, aplicam o princípio de **Inversão de Dependência**, naturalmente levam a um desenho arquitetural mais limpo.

## Problemas comuns onde Clean Arch pode ajudar
* Decisões de infraestrutura tomadas no início do projeto (banco de dados, frameworks, ferramentas de messageria);
* Desenhos baseados em stack tecnológico e não em necessidades de negócio;
* Dificuldade de mudança de alguma tecnologia;
* Desrespeito a Pirâmide de Testes (exemplo: mais testes de integração que unitários)    
  
## Prós:
* Foco nos casos de Uso de Negócio e não em componentes de infraestrutura e/ou frameworks;
* Casos de Uso implementados em um único lugar;
* Facilidade de Manutenção e de aplicar mudanças;
* Total separação de responsabilidades (causa uma impressão de que cada coisa em seu devido lugar);
* Abordagem de teste mais efetiva (respeitando a pirâmede de testes);
* Independente de Framework, Banco de Dados e componentes de Infraestrutura em Geral; 

## Contras:
* Mais código escrito e uma aparente repetição de código;
* Pode levar mais tempo para desenvolver inicialmente (maior custo de desenvolvimento);
* Curva de aprendizagem e de adaptação; 
* Pode provocar discussões filosóficas interminavéis (se está quebrando o padrão, até onde abrir exceções para ser mais produtivo, etc)


## Fontes
* Artigo Uncle Bob - https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.
* Implementação Java - https://github.com/mattia-battiston/clean-architecture-example
* Implementação Java -https://github.com/lievendoclo/cleanarch
* Palestra Uncle Bob - https://www.youtube.com/watch?v=Nsjsiz2A9mg
* Palestra lievendoclo - https://www.youtube.com/watch?v=O6tdJO4aB7c&t=350s
