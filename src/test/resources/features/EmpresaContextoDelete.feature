#language: pt
Funcionalidade: Delete de uma empresa
  Como novo usuário da API
  Quero deletar uma Empresa
  Para que o registro da empresa seja apagado corretamente no sistema
  Contexto: Cadastro bem sucedido da Empresa com sublistas
    Dado que eu tenha os seguintes dados da Empresa:
      | campo | valor                  |
      | nome  | Energia Verde          |
      | email | energiverdedelete@email.com |
      | senha | senha123              |

    E que a Empresa possua o seguinte endereço:
      | cep       | regiao   |
      | 98765432  | NORDESTE |

    E que a Empresa possua os seguintes dados empresariais:
      | campo | valor            |
      | cnpj  | 12345678000190   |

    Quando eu enviar a requisição para o endpoint "/auth/registro/empresa" de cadastro de Empresas
    Entao o status code da resposta da empresa deve ser 201

  Cenário: Deve ser possível deletar uma Empresa
    Dado que eu recupere o ID da empresa criada no contexto
    Quando eu enviar a requisição com o ID da empresa para o endpoint "/api/usuarios/deletar" de deleção de entrega
    Entao o status code da resposta da empresa deve ser 204