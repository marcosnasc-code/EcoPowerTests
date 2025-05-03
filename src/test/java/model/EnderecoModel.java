package model;

import com.google.gson.annotations.Expose;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.Data;

@Data
public class EnderecoModel {

    @Expose
    private String cep;
    @Expose
    private Regiao regiao;
}
