package steps;

import com.google.gson.Gson;
import com.networknt.schema.ValidationMessage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import model.*;
import org.junit.Assert;
import services.CadastroClienteService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CadastroClienteSteps {

    CadastroClienteService cadastroClienteService = new CadastroClienteService();


    @Dado("que eu tenha os seguintes dados do Cliente:")
    public void queEuTenhaOsSeguintesDadosDoCliente(List<Map<String, String>> rows) {
        for (Map<String, String> columns : rows){
            cadastroClienteService.setFieldsDelivery(columns.get("campo"), columns.get("valor"));
        }
    }

    @E("que o Cliente possua o seguinte endereço:")
    public void queOClientePossuaOSeguinteEndereço(DataTable dataTable) {
        for (Map<String, String> linha: dataTable.asMaps()) {
            EnderecoModel endereco = new EnderecoModel();
            endereco.setCep(linha.get("cep"));
            String regiaoStr = linha.get("regiao");
            if (regiaoStr != null) {
                try {
                    Regiao regiao = Regiao.valueOf(regiaoStr.toUpperCase());
                    endereco.setRegiao(regiao);
                } catch (IllegalArgumentException e) {
                    System.out.println("Valor de regiao inválido: " + regiaoStr);
                }
            }
            String enderecoJson = new Gson().toJson(endereco);
            cadastroClienteService.setFieldsDelivery("endereco", enderecoJson);
        }
    }

    @E("que o Cliente possua os seguintes dados complementares:")
    public void queOClientePossuaOsSeguintesDadosComplementares(DataTable dataTable) {
        Map<String, String> dados = new HashMap<>();
        for (Map<String, String> linha : dataTable.asMaps()) {
            dados.put(linha.get("campo"), linha.get("valor"));
        }
        String jsonDadosCliente = new Gson().toJson(dados);
        cadastroClienteService.setFieldsDelivery("dadosCliente", jsonDadosCliente);
    }

    @E("que o Cliente possua os seguintes imóveis:")
    public void queOClientePossuaOsSeguintesImóveis(DataTable dataTable) {
        for (Map<String, String> linha: dataTable.asMaps()){
            EnderecoModel endereco = new EnderecoModel();
            endereco.setCep(linha.get("cep"));
            String regiaoStr = linha.get("regiao");
            if (regiaoStr != null) {
                try {
                    Regiao regiao = Regiao.valueOf(regiaoStr.toUpperCase());
                    endereco.setRegiao(regiao);
                } catch (IllegalArgumentException e) {
                    System.out.println("Valor de regiao inválido: " + regiaoStr);
                }
            }

            ImoveisModel imovel = new ImoveisModel();
            imovel.setIdImovel(linha.get("idImovel"));
            imovel.setEndereco(endereco);
            String imovelStr = linha.get("tipoImoveis");
            if(imovelStr != null){
                try{
                    TipoImoveis tipoImoveis = TipoImoveis.valueOf(imovelStr.toUpperCase());
                    imovel.setTipoImoveis(tipoImoveis);
                } catch (IllegalArgumentException e){
                    System.out.println("Tipo imovel Inválido: " + imovelStr);
                }
            }
            String json = new Gson().toJson(imovel);
            cadastroClienteService.setFieldsDelivery("imoveis", json);
        }
    }

    @Quando("eu enviar a requisição para o endpoint {string} de cadastro de Clientes")
    public void euEnviarARequisiçãoParaOEndpointDeCadastroDeClientes(String endPoint) {
        cadastroClienteService.createDelivery(endPoint);
    }

    @Então("o status code da resposta deve ser {int}")
    public void oStatusCodeDaRespostaDeveSer(int statusCode) {
        Assert.assertEquals(statusCode, cadastroClienteService.response.statusCode());
    }

    @E("o corpo da resposta de erro da api deve retornar a mensagem {string};")
    public void oCorpoDaRespostaDeErroDaApiDeveRetornarAMensagem(String message) {
        ErrorMessageModel errorMessageModel = cadastroClienteService.gson.fromJson(
                cadastroClienteService.response.jsonPath().prettify(), ErrorMessageModel.class);
        Assert.assertEquals(message, errorMessageModel.getMessage());
    }

    @Dado("que eu recupere o ID da entrega criada no contexto")
    public void queEuRecupereOIDDaEntregaCriadaNoContexto() {
        cadastroClienteService.retrieveIdDelivery();
    }
    @Quando("eu enviar a requisição com o ID para o endpoint {string} de deleção de entrega")
    public void euEnviarARequisiçãoComOIDParaOEndpointDeDeleçãoDeEntrega(String endPoint) {
        cadastroClienteService.deleteDelivery(endPoint);
    }

    @E("que o arquivo de contrato esperado é o {string}")
    public void queOArquivoDeContratoEsperadoÉO(String contract) throws IOException {
        cadastroClienteService.setContract(contract);
    }

    @Então("a resposta da requisição deve estar em conformidade com o contrato selecionado")
    public void aRespostaDaRequisiçãoDeveEstarEmConformidadeComOContratoSelecionado() throws IOException {
        Set<ValidationMessage> validateResponse = cadastroClienteService.validateResponseAgainstSchema();
        Assert.assertTrue("O Contrato está inválido. Erros encontrados: " + validateResponse, validateResponse.isEmpty());
    }
}
