# language: pt
Funcionalidade: Cadastro de nova empresa
  Como novo usuário da API
  Quero cadastrar uma nova empresa fornecedora
  Para que o registro seja salvo corretamente no sistema

  Cenário: Cadastro bem-sucedido da Empresa com endereço e dados empresariais
    Dado que eu tenha os seguintes dados da Empresa:
      | campo | valor                  |
      | nome  | Energia Verde          |
      | email | energiverde@email.com |
      | senha | senha123              |

    E que a Empresa possua o seguinte endereço:
      | cep       | regiao   |
      | 98765432  | NORDESTE |

    E que a Empresa possua os seguintes dados empresariais:
      | campo | valor            |
      | cnpj  | 12345678000190   |

    Quando eu enviar a requisição para o endpoint "/auth/registro/empresa" de cadastro de Empresas
    Entao o status code da resposta da empresa deve ser 201

  Cenário: Cadastro de Empresa sem sucesso ao passar a região inválida
    Dado que eu tenha os seguintes dados da Empresa:
      | campo | valor                  |
      | nome  | Energia Verde          |
      | email | energiverdeinvalido321@email.com |
      | senha | senha123              |

    E que a Empresa possua o seguinte endereço:
      | cep       | regiao    |
      | 98765432  | SUESTE    |

    E que a Empresa possua os seguintes dados empresariais:
      | campo | valor            |
      | cnpj  | 12345678000190   |

    Quando eu enviar a requisição para o endpoint "/auth/registro/empresa" de cadastro de Empresas
    Então o status code da resposta da empresa deve ser 400
    E o corpo da resposta de erro da api empresas deve retornar a mensagem "Dados fornecidos estão em formato inválido"