package sy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Operatelib entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="operatelib")

public class Operatelib  implements java.io.Serializable {


    // Fields    

     private long id;
     private String opname;
     private String optype;
     private String operateName;
     private String operateNameCn;


    // Constructors

    /** default constructor */
    public Operatelib() {
    }

    
    /** full constructor */
    public Operatelib(String opname, String optype, String operateName, String operateNameCn) {
        this.opname = opname;
        this.optype = optype;
        this.operateName = operateName;
        this.operateNameCn = operateNameCn;
    }

   
    // Property accessors
    @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id", unique=true, nullable=false)

    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    @Column(name="opname", length=200)

    public String getOpname() {
        return this.opname;
    }
    
    public void setOpname(String opname) {
        this.opname = opname;
    }
    
    @Column(name="optype", length=20)

    public String getOptype() {
        return this.optype;
    }
    
    public void setOptype(String optype) {
        this.optype = optype;
    }
    
    @Column(name="operateName", length=400)

    public String getOperateName() {
        return this.operateName;
    }
    
    public void setOperateName(String operateName) {
        this.operateName = operateName;
    }
    
    @Column(name="operateNameCn", length=400)

    public String getOperateNameCn() {
        return this.operateNameCn;
    }
    
    public void setOperateNameCn(String operateNameCn) {
        this.operateNameCn = operateNameCn;
    }
   








}