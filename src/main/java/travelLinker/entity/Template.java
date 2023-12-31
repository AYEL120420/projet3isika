package travelLinker.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Template {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nom;

	private String htmlCssContent;

	@Column(name = "background_color")
	private String backgroundColor;
	
	@Column (name = "picture")
	private String picture; 
	

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getHtmlCssContent() {
		return htmlCssContent;
	}

	public void setHtmlCssContent(String htmlCssContent) {
		this.htmlCssContent = htmlCssContent;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Template [id=");
		builder.append(id);
		builder.append(", nom=");
		builder.append(nom);
		builder.append(", htmlCssContent=");
		builder.append(htmlCssContent);
		builder.append(", backgroundColor=");
		builder.append(backgroundColor);
		builder.append("]");
		return builder.toString();
	}

}
