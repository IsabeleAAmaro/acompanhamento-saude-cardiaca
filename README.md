# Acompanhamento de Saúde Cardíaca

Aplicação front-end desenvolvida com Ionic e Angular para o projeto de acompanhamento de saúde cardíaca.

## Requisitos

Antes de iniciar, é necessário ter instalado:

- Node.js [1][2]
- Ionic CLI [1]

## Instalação

```bash
npm install -g @ionic/cli
```

Depois, dentro da pasta do projeto, instale as dependências:

```bash
npm install
```

## Como executar o projeto

Na raiz do projeto, rode:

```bash
ionic serve
```

Esse comando inicia o servidor de desenvolvimento local da aplicação Ionic.[3]

## Passo a passo completo

```bash
cd saude-cardiaca-app
npm install
ionic serve
```

Se a máquina ainda não tiver o Ionic CLI instalado:

```bash
npm install -g @ionic/cli
cd saude-cardiaca-app
npm install
ionic serve
```

## Estrutura básica de execução

| Etapa | Comando | Finalidade |
|---|---|---|
| Entrar na pasta do projeto | `cd saude-cardiaca-app` | Acessar a raiz da aplicação |
| Instalar dependências | `npm install` | Baixar os pacotes do projeto |
| Rodar em desenvolvimento | `ionic serve` | Subir o front-end localmente [3] |

## Observações

- Caso o terminal não reconheça o comando `ionic`, é necessário instalar o Ionic CLI globalmente.[1]
- O Ionic Angular utiliza o Angular CLI por trás do comando `ionic serve` em projetos Angular.[3]
- Se houver atualização nas dependências do projeto, é recomendado executar `npm install` novamente.[1][2]
