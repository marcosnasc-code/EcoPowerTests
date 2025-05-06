package steps;

import com.google.gson.Gson;
import com.networknt.schema.ValidationMessage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.pt.*;
import model.*;
import org.junit.Assert;
import services.CadastroEmpresaService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CadastroEmpresaSteps {

    CadastroEmpresaService cadastroEmpresaService = new CadastroEmpresaService();


    @Dado("que eu tenha os seguintes dados da Empresa:")
    public void queEuTenhaOsSeguintesDadosDaEmpresa(List<Map<String, String>>rows) {
        for(Map<String, String> columns : rows){
            cadastroEmpresaService.setFieldsDelivery(columns.get("campo"), columns.get("valor"));
        }
    }

    @E("que a Empresa possua o seguinte endereço:")
    public void queAEmpresaPossuaOSeguinteEndereço(DataTable dataTable) {
        for(Map<String, String> linha: dataTable.asMaps()){
            EnderecoModel enderecoModel = new EnderecoModel();
            enderecoModel.setCep(linha.get("cep"));
            String regiaoStr = linha.get("regiao");
            if (regiaoStr != null){
                try{
                    Regiao regiao = Regiao.valueOf(regiaoStr.toUpperCase());
                    enderecoModel.setRegiao(regiao);
                }catch (IllegalArgumentException e){
                    System.out.println("Valor de região inválido: " + regiaoStr);
                }
            }
            String enderecoJson = new Gson().toJson(enderecoModel);
            cadastroEmpresaService.setFieldsDelivery("endereco", enderecoJson);
        }
    }

    @E("que a Empresa possua os seguintes dados empresariais:")
    public void queAEmpresaPossuaOsSeguintesDadosEmpresariais(DataTable dataTable) {
        Map<String, String> dados = new HashMap<>();
        for(Map<String, String> linha: dataTable.asMaps()){
            dados.put(linha.get("campo"), linha.get("valor"));
        }
        String jsonDadosEmpresa = new Gson().toJson(dados);
        cadastroEmpresaService.setFieldsDelivery("dadosEmpresa", jsonDadosEmpresa);
    }

    @Quando("eu enviar a requisição para o endpoint {string} de cadastro de Empresas")
    public void euEnviarARequisiçãoParaOEndpointDeCadastroDeEmpresas(String endPoint) {
        cadastroEmpresaService.createDelivery(endPoint);
    }

    @Entao("o status code da resposta da empresa deve ser {int}")
    public void oStatusCodeDaRespostaDeveSer(int statusCode) {
        Assert.assertEquals(statusCode, cadastroEmpresaService.response.statusCode());
    }

    @E("o corpo da resposta de erro da api empresas deve retornar a mensagem {string}")
    public void oCorpoDaRespostaDeErroDaApiDeveRetornarAMensagem(String message) {
        ErrorMessageModel errorMessageModel = cadastroEmpresaService.gson.fromJson(
                cadastroEmpresaService.response.jsonPath().prettify(), ErrorMessageModel.class
        );
        Assert.assertEquals(message, errorMessageModel.getMessage());
    }

    @Dado("que eu recupere o ID da empresa criada no contexto")
    public void queEuRecupereOIDDaEmpresaCriadaNoContexto() {
        cadastroEmpresaService.retrieveIdDelivery();
    }

    @Quando("eu enviar a requisição com o ID da empresa para o endpoint {string} de deleção de entrega")
    public void euEnviarARequisiçãoComOIDDaEmpresaParaOEndpointDeDeleçãoDeEntrega(String endPoint) {
        cadastroEmpresaService.deleteDelivery(endPoint);
    }


    @E("que o arquivo de contrato esperado da empresa é o {string}")
    public void queOArquivoDeContratoEsperadoDaEmpresaÉO(String contract) throws IOException {
        cadastroEmpresaService.setContract(contract);
    }

    @Então("a resposta da requisição deve estar em conformidade com o contrato selecionado da empresa")
    public void aRespostaDaRequisiçãoDeveEstarEmConformidadeComOContratoSelecionadoDaEmpresa() throws IOException {
        Set<ValidationMessage> validateResponse = cadastroEmpresaService.validateResponseAgainstSchema();
        Assert.assertTrue("O Contrato está inválido. Erros encontrados: " + validateResponse, validateResponse.isEmpty());
    }
}
