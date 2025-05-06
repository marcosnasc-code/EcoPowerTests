#language: pt
  Funcionalidade: Cadastro de novo cliente
    Como novo usuário da API
    Quero cadastrar um novo cliente
    Para que o registro seja salvo corretamente no sistema
    Cenário: Cadastro bem sucedido do Cliente com sublistas
      Dado que eu tenha os seguintes dados do Cliente:
      | campo          | valor        |
      | nome           | Arthur       |
      | email          | art2@email.com|
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

    Cenário: Cadastro de Cliente sem sucesso ao passar o campo Endereco invalido
      Dado que eu tenha os seguintes dados do Cliente:
        | campo          | valor        |
        | nome           | Arthur       |
        | email          | art@email.com|
        | senha          | senha123     |

      E que o Cliente possua o seguinte endereço:
        | cep       | regiao  |
        | 12345678  | BIZARRO |

      E que o Cliente possua os seguintes dados complementares:
        | campo | valor       |
        | cpf   | 12345678900 |

      E que o Cliente possua os seguintes imóveis:
        | idImovel | tipoImoveis | cep      | regiao  |
        | 1        | RESIDENCIAL | 12345678 | SUDESTE |
      Quando eu enviar a requisição para o endpoint "/auth/registro/cliente" de cadastro de Clientes
      Então o status code da resposta deve ser 400
      E o corpo da resposta de erro da api deve retornar a mensagem "Dados fornecidos estão em formato inválido";