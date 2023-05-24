# Documento de Testes

## Testes Da Classe Serie

| TESTE | IMPLEMENTADO |  DESCRIÇÃO
|:--|:--:|--|
| registrarNovoPontoDeAudiencia() | ✅ | Verifica se ponto de audiência é registrado corretamente. |
| serieDeveConterIdValido() | ❌ | Verifica se a Série está sendo criada com um número de identificação válido, ou seja, não retorna excessão ao ser criada.
| serieDeveConterNomeValido() | ❌ | Verifica se a Série está sendo criada com um nome válido, ou seja, não retorna excessão ao ser criada.|

## Teste Da Classe PlataformaStreaming

| TESTE | IMPLEMENTADO |  DESCRIÇÃO
|:--|:--:|--|
| clienteDeveConseguirLogarComSucesso() | ✅ | Valida se usuário consegue logar no sistema utilizando as credenciais corretas. |
| soDeveSerPossivelLogarSeEstiverCadastrado() | ✅ | Certifica que usuário não deve poder logar no sistema caso não esteja cadastrado. |
| deveSerPossivelAdicionarSeriesAPlataforma() | ✅ | Valida se a classe adiciona objetos do tipo Serie corretamente. |
| naoDeveSerPossivelAdicionarUmaSerieDuplicada() | ✅ | Certifica que o sistema não deve adicionar uma série caso ela já tenha sido cadastrada antes. |
| deveSerPossivelAdicionarUmNovoCliente() | ✅ | Certifica que o sistema não retorna erros ao tentar adicionar um novo cliente no sistema passando todos os parâmetros corretamente. |
| deveSerPossivelFazerLogOff() | ✅ | Certifica que sistema efetua o logoff do usuário corretamente. |
| deveSerPossivelEncontrarUmaSeriePeloNome() | ✅ | Certifica que o sistema é capaz de encontrar uma Série passando o nome dela como parâmetro de pesquisa. |


> OBS.: No estado atual de desenvolvimento, não é preciso incluir testes adicionais na classe PlataformaStreaming.

## Teste Da Classe Cliente

| TESTE | IMPLEMENTADO |  DESCRIÇÃO
|:--|:--:|--|
| clienteDeveTerNomeLoginESenha() | ✅ | Certifica que o sistema retorna uma excessão ao tentar criar um Cliente sem todos os parâmetros necessários. |
| clienteDeveTerInformacoesDeTamanhoMaiorQue0() | ✅ | Certifica que o Cliente não deve ser criado com atributos inválidos. |
| naoDeveSerPossivelAddSerieSeElaJaEstaNaLista() | ✅ | Cerifica que o usuário não pode adicionar uma Série que já existe em sua Lista de séries. |
| adicionarSerieNaListaParaVer() | ✅ | Certifica que o sistema é capaz de adicionar corretamente uma série na lista de séries do Cliente |

> OBS.: No estado atual de desenvolvimento, não é preciso incluir testes adicionais na classe Cliente.