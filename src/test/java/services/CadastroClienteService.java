package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import io.cucumber.java.es.Dado;
import io.restassured.response.Response;
import model.CadastroClienteModel;
import model.DadosClienteModel;
import model.EnderecoModel;
import model.ImoveisModel;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Set;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.json.JSONTokener;

public class CadastroClienteService {

    final CadastroClienteModel cadastroClienteModel = new CadastroClienteModel();
    public final Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();
    public Response response;
    String baseUrl = "http://localhost:8080";
    String idDelivery;
    String schemasPath = "src/test/resources/schemas/";
    JSONObject jsonSchema;
    private final ObjectMapper mapper = new ObjectMapper();


    public void setFieldsDelivery(String field, String value) {
        switch (field) {
            case "nome" -> cadastroClienteModel.setNome(value);
            case "email" -> cadastroClienteModel.setEmail(value);
            case "senha" -> cadastroClienteModel.setSenha(value);

            case "endereco" -> {
                EnderecoModel endereco = gson.fromJson(value, EnderecoModel.class);
                cadastroClienteModel.setEndereco(endereco);
            }

            case "dadosCliente" -> {
                DadosClienteModel dadosCliente = gson.fromJson(value, DadosClienteModel.class);
                cadastroClienteModel.setDadosCliente(dadosCliente);
            }

            case  "imoveis" -> {
                ImoveisModel imoveis = gson.fromJson(value, ImoveisModel.class);
                DadosClienteModel dadosCliente = cadastroClienteModel.getDadosCliente();

                if(dadosCliente == null){
                    dadosCliente = new DadosClienteModel();
                    cadastroClienteModel.setDadosCliente(dadosCliente);
                }
                if (dadosCliente.getImoveis() == null){
                    dadosCliente.setImoveis(new ArrayList<>());
                }
                dadosCliente.getImoveis().add(imoveis);
            }
            default -> throw new IllegalStateException("Unexpected field: " + field);
        }
        //System.out.println(gson.toJson(cadastroClienteModel));
    }


    public void createDelivery(String endPoint){

        String url = baseUrl + endPoint;
        String bodyToSend = gson.toJson(cadastroClienteModel);
        response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(bodyToSend)
                .when()
                .post(url)
                .then()
                .extract()
                .response();
    }

    public void retrieveIdDelivery(){
        idDelivery = String.valueOf(gson.fromJson(response.jsonPath().prettify(), CadastroClienteModel.class).getEmail());
        System.out.println("ID de entrega (email): " + idDelivery);
    }

    public void deleteDelivery(String endPoint) {
        String url = String.format("%s%s/%s", baseUrl, endPoint, idDelivery);
        //simula token adm para exclusão. Precisa ser trocado sempre que for testado.
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJFY29Qb3dlciIsInN1YiI6ImFkbWluQGFkbWluLmNvbSIsImV4cCI6MTc0NjM5NTgyOH0.MGoiKDHLmGcOIabHZiGvIx7xsQL4X_8S-u7_24zRpz8";
        response = given()
                .header("Authorization", token)
                .accept(ContentType.JSON)
                .when()
                .delete(url)
                .then()
                .extract()
                .response();
    }

    private JSONObject loadJsonFromFile(String filePath) throws IOException {
        try (InputStream inputStream = Files.newInputStream(Paths.get(filePath))) {
            JSONTokener tokener = new JSONTokener(inputStream);
            return new JSONObject(tokener);
        }
    }

    public void setContract(String contract) throws IOException {
        switch (contract) {
            case "Cadastro bem-sucedido do cliente" -> jsonSchema = loadJsonFromFile(schemasPath + "cadastroBemSucedidoCliente.json");
            default -> throw new IllegalStateException("Unexpected contract" + contract);
        }
    }

    public Set<ValidationMessage> validateResponseAgainstSchema() throws IOException
    {
        JSONObject jsonResponse = new JSONObject(response.getBody().asString());
        JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
        JsonSchema schema = schemaFactory.getSchema(jsonSchema.toString());
        JsonNode jsonResponseNode = mapper.readTree(jsonResponse.toString());
        //System.out.println(jsonResponse);
        Set<ValidationMessage> schemaValidationErrors = schema.validate(jsonResponseNode);
        return schemaValidationErrors;
    }

}

