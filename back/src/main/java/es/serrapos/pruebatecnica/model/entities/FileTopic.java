package es.serrapos.pruebatecnica.model.entities;

import lombok.Data;

@Data
public class FileTopic {
	
	private Long id;
	
	private String filename;
	
	private byte[] content;

}
