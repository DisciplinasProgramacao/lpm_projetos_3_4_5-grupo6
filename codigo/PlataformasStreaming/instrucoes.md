# Instruções

## Arquivos

Os arquivos utilizados para leitura/escrita estão no diretório `assets` na raiz do projeto `PlataformasStreaming`

## VS Code

### Workspace

Para a abrir o projeto corretamente, clicar em `Arquivos` > `Abrir pasta` e selecionar a pasta `PlataformasStreaming`

> Isso simplifica as configurações do projeto no VS Code, e impede por exemplo instalar a biblioteca de testes "fora" do projeto ou configurar o projeto a nível "global" e não de Workspace

### Configuração de testes

Crie um arquivo `settings.json` na raiz do projeto java `PlataformasStreaming`

```txt
PlataformasStreaming
|__assets
|__bin
|__src
|__.vscode
   |__settings.json
```

Depois, cole essa configuração no arquivo `settings.json`

```json
{
  "java.test.config": {
    "workingDirectory": "${workspaceFolder}"
  }
}
```

> Isso deve ser feito para não ocorrerem erros de caminhos quando forem executados testes e/ou leitura/escrita de arquivos
