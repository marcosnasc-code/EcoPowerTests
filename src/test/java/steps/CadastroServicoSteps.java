package steps;

import com.google.gson.Gson;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.junit.Assert;
import services.CadastroEmpresaService;
import services.CadastroServicoService;

import java.util.HashMap;
import java.util.Map;

public class CadastroServicoSteps {

    CadastroServicoService cadastroServicoService = new CadastroServicoService();
    CadastroEmpresaService cadastroEmpresaService = new CadastroEmpresaService();

    @E("que o servico possua os seguintes dados:")
    public void queOServicoPossuaOsSeguintesDados(DataTable dataTable) {
        for(Map<String, String> linha : dataTable.asMaps()){
            for (Map.Entry<String, String> entry: linha.entrySet()){
                cadastroServicoService.setFieldsService(entry.getKey(), entry.getValue());
            }
        }
    }

    @Quando("eu enviar a requisição com o ID da empresa para o endpoint {string} de cadastro de servico")
    public void euEnviarARequisiçãoComOIDDaEmpresaParaOEndpointDeCadastroDeServico(String endPoint) {
        cadastroServicoService.createService(endPoint);
    }

    @Entao("o status code da resposta do servico deve ser {int}")
    public void oStatusCodeDaRespostaDeveSer(int statusCode) {
        Assert.assertEquals(statusCode, cadastroServicoService.response.statusCode());
    }
}
