package model;


import com.google.gson.annotations.Expose;
import lombok.Data;

import java.util.List;

@Data
public class DadosClienteModel {

    @Expose
    private String cpf;
    @Expose
    private List<ImoveisModel> imoveis;
}
