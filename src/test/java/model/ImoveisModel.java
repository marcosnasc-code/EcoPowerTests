package model;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class ImoveisModel {

    @Expose
    private String idImovel;
    @Expose
    private TipoImoveis tipoImoveis;
    @Expose
    private EnderecoModel endereco;
}
