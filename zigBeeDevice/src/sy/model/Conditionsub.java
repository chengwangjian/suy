package sy.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="conditionsub")

public class Conditionsub implements java.io.Serializable {
  private Integer id;
  private String leftBracket;
  private String field;
  private String operator;
  private String content;
  private String rightBracket;
  private String relation;
  private Integer mid;
  
  @Id @GeneratedValue(strategy=IDENTITY)
  
  @Column(name="id", unique=true, nullable=false)

 public Integer getId() {
	return id;
}
 public void setId(Integer id) {
	this.id = id;
}

 @Column(name="leftBracket", length=50)
 public String getLeftBracket() {
	return leftBracket;
}
 public void setLeftBracket(String leftBracket) {
 	this.leftBracket = leftBracket;
}
 @Column(name="field", length=50)
 public String getField() {
	return field;
}
 public void setField(String field) {
	this.field = field;
}
 @Column(name="operator", length=50)
public String getOperator() {
	return operator;
}
public void setOperator(String operator) {
	this.operator = operator;
}
@Column(name="content", length=50)
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
public String getRightBracket() {
	return rightBracket;
}
@Column(name="rightBracket", length=50)
public void setRightBracket(String rightBracket) {
	this.rightBracket = rightBracket;
}
@Column(name="relation", length=50)
public String getRelation() {
	return relation;
}
public void setRelation(String relation) {
	this.relation = relation;
}
@Column(name="mid", length=50)
public Integer getMid() {
	return mid;
}
public void setMid(Integer mid) {
	this.mid = mid;
}
	
	
}
