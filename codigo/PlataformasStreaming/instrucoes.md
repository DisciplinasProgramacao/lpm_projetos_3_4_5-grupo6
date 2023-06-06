# Instruções

## Arquivos

Os arquivos utilizados para leitura/escrita estão no diretório `assets` na raiz do projeto

## Configuração de Testes no VS Code

Se utilizar o VS Code, crie um arquivo `settings.json` no mesmo diretório do Projeto Java dessa forma

```txt
PlataformasStreaming
|__src
|__assets
|__.vscode
   |__settings.json
```

Depois, cole a seguinte configuração no arquivo `settings.json`

```json
{
  "java.test.config": {
    "workingDirectory": "${workspaceFolder}"
  }
}
```

Isso deve ser feito para não ocorrerem erros de caminhos quando forem executados testes.
