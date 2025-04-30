package steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

public class CadastroClienteSteps {
    @Dado("que eu tenha os seguintes dados do Cliente:")
    public void queEuTenhaOsSeguintesDadosDoCliente() {
    }

    @E("que o Cliente possua o seguinte endereço:")
    public void queOClientePossuaOSeguinteEndereço() {
    }

    @E("que o Cliente possua os seguintes dados complementares:")
    public void queOClientePossuaOsSeguintesDadosComplementares() {
    }

    @E("que o Cliente possua os seguintes imóveis:")
    public void queOClientePossuaOsSeguintesImóveis() {
    }

    @Quando("eu enviar a requisição para o endpoint {string} de cadastro de Clientes")
    public void euEnviarARequisiçãoParaOEndpointDeCadastroDeClientes(String endPoint) {
    }

    @Então("o status code da resposta deve ser {int}")
    public void oStatusCodeDaRespostaDeveSer(int statusCode) {
    }
}
