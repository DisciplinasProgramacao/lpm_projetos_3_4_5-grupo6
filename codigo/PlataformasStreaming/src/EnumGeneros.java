public enum EnumGeneros {
    ACAO,
    ANIME,
    AVENTURA,
    COMEDIA,
    DOCUMENTARIO,
    DRAMA,
    POLICIAL,
    ROMANCE,
    SUSPENSE;

    public static boolean verificarGenero(String genero) {
        for (EnumGeneros valor : EnumGeneros.values()) {
            if (valor.name().equalsIgnoreCase(genero)) {
                return true;
            }
        }
        return false;
    }
}
