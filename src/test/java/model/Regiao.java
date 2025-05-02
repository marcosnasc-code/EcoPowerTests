package model;

import com.google.gson.annotations.SerializedName;

public enum Regiao {
    @SerializedName("NORTE")
    NORTE,
    @SerializedName("NORDESTE")
    NORDESTE,
    @SerializedName("CENTRO_OESTE")
    CENTRO_OESTE,
    @SerializedName("SUDESTE")
    SUDESTE,
    @SerializedName("SUL")
    SUL
}
