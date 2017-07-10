package sy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * AttributeNamelib entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "attributenamelib")
public class AttributeNamelib implements java.io.Serializable {

	// Fields

	private long id;
	private String attributeNameCn;
	private String attributeName;

	// Constructors

	/** default constructor */
	public AttributeNamelib() {
	}

	/** full constructor */
	public AttributeNamelib(String attributeNameCn, String attributeName) {
		this.attributeNameCn = attributeNameCn;
		this.attributeName = attributeName;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "attributeNameCn", length = 400)
	public String getAttributeNameCn() {
		return this.attributeNameCn;
	}

	public void setAttributeNameCn(String attributeNameCn) {
		this.attributeNameCn = attributeNameCn;
	}

	@Column(name = "attributeName", length = 50)
	public String getAttributeName() {
		return this.attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

}