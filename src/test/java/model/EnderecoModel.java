package model;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class EnderecoModel {

    @Expose
    private String cep;
    @Expose
    private Regiao regiao;
}
