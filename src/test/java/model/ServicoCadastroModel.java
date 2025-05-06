package model;


import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class ServicoCadastroModel {

    @Expose
    private String nome;
    @Expose
    private double precoBaseKwh;

}
