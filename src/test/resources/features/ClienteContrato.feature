#language: pt
Funcionalidade: Validar um contrato ao realizar um cadastro bem sucedido de entrega
  Como novo usuario da API
  Quero cadastrar um novo cliente
  Para validar se o contrato esta conforme o esperado
  Cenário: Validar cadastro bem sucedido do Cliente com sublistas
    Dado que eu tenha os seguintes dados do Cliente:
      | campo          | valor        |
      | nome           | Arthur3       |
      | email          | art3@email.com|
      | senha          | senha123     |

    E que o Cliente possua o seguinte endereço:
      | cep       | regiao  |
      | 12345678  | SUL |

    E que o Cliente possua os seguintes dados complementares:
      | campo | valor       |
      | cpf   | 12345678900 |

    E que o Cliente possua os seguintes imóveis:
      | idImovel | tipoImoveis | cep      | regiao  |
      | 1        | RESIDENCIAL | 12345678 | SUL |

    Quando eu enviar a requisição para o endpoint "/auth/registro/cliente" de cadastro de Clientes
    Então o status code da resposta deve ser 201
    E que o arquivo de contrato esperado é o "Cadastro bem-sucedido do cliente"
    Então a resposta da requisição deve estar em conformidade com o contrato selecionado