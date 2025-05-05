#language: pt
Funcionalidade: Cadastro de novo cliente
  Como novo usuário da API
  Quero deletar um cliente
  Para que o registro seja apagado corretamente no sistema
  Contexto: Cadastro bem sucedido do Cliente com sublistas
    Dado que eu tenha os seguintes dados do Cliente:
      | campo          | valor        |
      | nome           | Arthur       |
      | email          | art5@email.com|
      | senha          | senha123     |

    E que o Cliente possua o seguinte endereço:
      | cep       | regiao  |
      | 12345678  | SUDESTE |

    E que o Cliente possua os seguintes dados complementares:
      | campo | valor       |
      | cpf   | 12345678900 |

    E que o Cliente possua os seguintes imóveis:
      | idImovel | tipoImoveis | cep      | regiao  |
      | 1        | RESIDENCIAL | 12345678 | SUDESTE |

    Quando eu enviar a requisição para o endpoint "/auth/registro/cliente" de cadastro de Clientes
    Então o status code da resposta deve ser 201

  Cenário: Deve ser possível deletar um Cliente
    Dado que eu recupere o ID da entrega criada no contexto
    Quando eu enviar a requisição com o ID para o endpoint "/api/usuarios/deletar" de deleção de entrega
    Então o status code da resposta deve ser 204