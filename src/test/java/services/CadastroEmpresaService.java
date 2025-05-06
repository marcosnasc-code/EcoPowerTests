package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.CadastroEmpresaModel;
import model.DadosEmpresaModel;
import model.EnderecoModel;
import model.ServicoCadastroModel;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static io.restassured.RestAssured.given;

public class CadastroEmpresaService {

    final CadastroEmpresaModel cadastroEmpresaModel = new CadastroEmpresaModel();
    public final Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();
    public Response response;
    String baseUrl = "http://localhost:8080";
    String idDelivery;
    String schemasPath = "src/test/resources/schemas/";
    JSONObject jsonSchema;
    private final ObjectMapper mapper = new ObjectMapper();


    public void setFieldsDelivery(String field, String value){
        switch (field){
            case "nome" -> cadastroEmpresaModel.setNome(value);
            case "email" -> cadastroEmpresaModel.setEmail(value);
            case "senha" -> cadastroEmpresaModel.setSenha(value);

            case "endereco" -> {
                EnderecoModel endereco = gson.fromJson(value, EnderecoModel.class);
                cadastroEmpresaModel.setEndereco(endereco);
            }
            case "dadosEmpresa" -> {
                DadosEmpresaModel dadosEmpresa = gson.fromJson(value, DadosEmpresaModel.class);
                cadastroEmpresaModel.setDadosEmpresa(dadosEmpresa);
            }
            default -> throw new IllegalStateException("Unexpected Field: " + field);
        }
        System.out.println(gson.toJson(cadastroEmpresaModel));
    }

    public void createDelivery(String endPoint){
        String url = baseUrl + endPoint;
        String bodyToSend = gson.toJson(cadastroEmpresaModel);
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
        idDelivery = String.valueOf(gson.fromJson(response.jsonPath().prettify(), CadastroEmpresaModel.class).getEmail());
        System.out.println("ID de entrega (email): " + idDelivery);
    }

    public void deleteDelivery(String endPoint){
        String url = String.format("%s%s/%s", baseUrl, endPoint, idDelivery);
        //necessário token válido
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJFY29Qb3dlciIsInN1YiI6ImFkbWluQGFkbWluLmNvbSIsImV4cCI6MTc0NjUxNDc3Mn0.jhPYgTkLRpk2dNG01uv2Tylg7GOaudqBBTNnSA2rEno";
        response = given().header("Authorization", token)
                .accept(ContentType.JSON)
                .when()
                .delete(url)
                .then()
                .extract()
                .response();
    }

    private JSONObject loadJsonFromFile(String filePath) throws IOException {
        try(InputStream inputStream = Files.newInputStream(Paths.get(filePath))){
            JSONTokener tokener = new JSONTokener(inputStream);
            return new JSONObject(tokener);
        }
    }

    public void setContract(String contract) throws IOException {
        switch (contract) {
            case "Cadastro bem-sucedido da empresa" -> jsonSchema = loadJsonFromFile(schemasPath + "cadastroBemSucedidoEmpresa.json");
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
