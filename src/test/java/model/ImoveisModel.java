package model;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class ImoveisModel {

    @Expose
    private String idImovel;
    private TipoImoveis tipoImoveis;
    private EnderecoModel endereco;
}
