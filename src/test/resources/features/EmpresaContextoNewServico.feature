#language: pt
Funcionalidade: Registrar um novo servico de uma empresa
  Como usuário empresa da API
  Quero cadastrar novo servico
  Para que o servico seja registrado corretamente a uma empresa
  Contexto: Cadastro bem sucedido do servico para uma empresa
    Dado que eu tenha os seguintes dados da Empresa:
      | campo | valor                  |
      | nome  | Energia Verde          |
      | email | energiverdeservico@email.com |
      | senha | senha123              |

    E que a Empresa possua o seguinte endereço:
      | cep       | regiao   |
      | 98765432  | NORDESTE |

    E que a Empresa possua os seguintes dados empresariais:
      | campo | valor            |
      | cnpj  | 12345678000190   |

    Quando eu enviar a requisição para o endpoint "/auth/registro/empresa" de cadastro de Empresas
    Entao o status code da resposta da empresa deve ser 201

  Cenário: Deve ser possível cadastrar um novo service na empresa
    Dado que eu recupere o ID da empresa criada no contexto
    E que o servico possua os seguintes dados:
      | nome           | precoBaseKwh  |
      | ENERGIA SOLAR  | 2.24          |
    Quando eu enviar a requisição com o ID da empresa para o endpoint "/api/empresa/" de cadastro de servico
    Entao o status code da resposta do servico deve ser 201