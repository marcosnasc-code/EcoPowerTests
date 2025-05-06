# language: pt
Funcionalidade: Validar um contrato ao realizar um cadastro bem sucedido de entrega
  Como novo usuário da API
  Quero cadastrar uma nova empresa fornecedora
  Para validar se o contrato esta conforme o esperado
  Cenário: Validar cadastro bem sucedido de empresa com sublistas
    Dado que eu tenha os seguintes dados da Empresa:
      | campo | valor                  |
      | nome  | Energia Verde          |
      | email | energiverdecontrato@email.com |
      | senha | senha123              |

    E que a Empresa possua o seguinte endereço:
      | cep       | regiao   |
      | 98765432  | NORDESTE |

    E que a Empresa possua os seguintes dados empresariais:
      | campo | valor            |
      | cnpj  | 12345678000190   |

    Quando eu enviar a requisição para o endpoint "/auth/registro/empresa" de cadastro de Empresas
    Entao o status code da resposta da empresa deve ser 201
    E que o arquivo de contrato esperado da empresa é o "Cadastro bem-sucedido da empresa"
    Então a resposta da requisição deve estar em conformidade com o contrato selecionado da empresa