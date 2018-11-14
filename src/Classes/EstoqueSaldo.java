/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author guilh
 */
@Entity
@Table(name = "estoque_saldo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstoqueSaldo.findAll", query = "SELECT e FROM EstoqueSaldo e")
    , @NamedQuery(name = "EstoqueSaldo.findById", query = "SELECT e FROM EstoqueSaldo e WHERE e.id = :id")
    , @NamedQuery(name = "EstoqueSaldo.findBySaldoEstoque", query = "SELECT e FROM EstoqueSaldo e WHERE e.saldoEstoque = :saldoEstoque")})
public class EstoqueSaldo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "saldo_estoque")
    private Integer saldoEstoque;

    public EstoqueSaldo() {
    }

    public EstoqueSaldo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSaldoEstoque() {
        return saldoEstoque;
    }

    public void setSaldoEstoque(Integer saldoEstoque) {
        this.saldoEstoque = saldoEstoque;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstoqueSaldo)) {
            return false;
        }
        EstoqueSaldo other = (EstoqueSaldo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Classes.EstoqueSaldo[ id=" + id + " ]";
    }
    
}
