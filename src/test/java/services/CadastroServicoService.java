package services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.CadastroEmpresaModel;
import model.ServicoCadastroModel;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class CadastroServicoService {


    final ServicoCadastroModel servicoCadastroModel = new ServicoCadastroModel();
    final CadastroEmpresaService cadastroEmpresaService = new CadastroEmpresaService();
    public final Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();
    public Response response;
    String baseUrl = "http://localhost:8080";
    String idDelivery = cadastroEmpresaService.idDelivery;

    public void setFieldsService(String fields, String value){
        switch (fields){
            case "nome" -> servicoCadastroModel.setNome(value);
            case "precoBaseKwh" -> servicoCadastroModel.setPrecoBaseKwh(Double.parseDouble(value));
            default -> throw new IllegalStateException("Unexpected Field: " + fields);
        }
        System.out.println(servicoCadastroModel);
    }

    public void createService(String endPoint){
        String url = "http://localhost:8080/api/empresa/energiverdeservico@email.com/servicos";
        System.out.println(url);
        String bodyToSend = gson.toJson(servicoCadastroModel);
        //necessário token válido
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJFY29Qb3dlciIsInN1YiI6ImFkbWluQGFkbWluLmNvbSIsImV4cCI6MTc0NjUxNDc3Mn0.jhPYgTkLRpk2dNG01uv2Tylg7GOaudqBBTNnSA2rEno";
        System.out.println(bodyToSend);
        response = given()
                .header("Authorization", token)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(bodyToSend)
                .when()
                .post(url)
                .then()
                .extract()
                .response();
    }

}
