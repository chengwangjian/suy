package sy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * IRBranddictionary entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="irbranddictionary")

public class IRBranddictionary  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private String irtype;
     private String brandCn;
     private String brandEn;
     private String nameIndex;//英文索引
     private String unitCode;//中文索引
     private String buse;//ir品牌库常用字段 常用1，不常用0


    // Constructors

    /** default constructor */
    public IRBranddictionary() {
    }

    
    /** full constructor */
    public IRBranddictionary(String irtype, String brandCn, String brandEn, String nameIndex, String unitCode, String buse) {
        this.irtype = irtype;
        this.brandCn = brandCn;
        this.brandEn = brandEn;
        this.nameIndex = nameIndex;
        this.unitCode = unitCode;
        this.buse = buse;
    }

   
    // Property accessors
    @Id @GeneratedValue
    
    @Column(name="id", unique=true, nullable=false)

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    @Column(name="IRType", length=20)

    public String getIrtype() {
        return this.irtype;
    }
    
    public void setIrtype(String irtype) {
        this.irtype = irtype;
    }
    
    @Column(name="BrandCN", length=50)

    public String getBrandCn() {
        return this.brandCn;
    }
    
    public void setBrandCn(String brandCn) {
        this.brandCn = brandCn;
    }
    
    @Column(name="BrandEN", length=50)

    public String getBrandEn() {
        return this.brandEn;
    }
    
    public void setBrandEn(String brandEn) {
        this.brandEn = brandEn;
    }
    
    @Column(name="NameIndex", length=20)

    public String getNameIndex() {
        return this.nameIndex;
    }
    
    public void setNameIndex(String nameIndex) {
        this.nameIndex = nameIndex;
    }
    
    @Column(name="UnitCode", length=20)

    public String getUnitCode() {
        return this.unitCode;
    }
    
    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }
    
    @Column(name="bUse", length=4)

    public String getBuse() {
        return this.buse;
    }
    
    public void setBuse(String buse) {
        this.buse = buse;
    }
   

}