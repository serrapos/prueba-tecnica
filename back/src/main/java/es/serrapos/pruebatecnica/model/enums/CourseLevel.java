package es.serrapos.pruebatecnica.model.enums;

public enum CourseLevel {
	BASICO("Básico"), INTERMEDIO("Intermedio"), AVANZADO("Avanzado");
	
	private final String value;
	
    private CourseLevel(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
	
}
