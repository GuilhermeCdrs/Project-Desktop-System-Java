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
@Table(name = "itens_venda")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ItensVenda.findAll", query = "SELECT i FROM ItensVenda i")
    , @NamedQuery(name = "ItensVenda.findById", query = "SELECT i FROM ItensVenda i WHERE i.id = :id")
    , @NamedQuery(name = "ItensVenda.findByNumPedido", query = "SELECT i FROM ItensVenda i WHERE i.numPedido = :numPedido")
    , @NamedQuery(name = "ItensVenda.findByIdProd", query = "SELECT i FROM ItensVenda i WHERE i.idProd = :idProd")
    , @NamedQuery(name = "ItensVenda.findByCodProduto", query = "SELECT i FROM ItensVenda i WHERE i.codProduto = :codProduto")
    , @NamedQuery(name = "ItensVenda.findByNome", query = "SELECT i FROM ItensVenda i WHERE i.nome = :nome")
    , @NamedQuery(name = "ItensVenda.findByVlUnitario", query = "SELECT i FROM ItensVenda i WHERE i.vlUnitario = :vlUnitario")
    , @NamedQuery(name = "ItensVenda.findByQuant", query = "SELECT i FROM ItensVenda i WHERE i.quant = :quant")
    , @NamedQuery(name = "ItensVenda.findByVlTotal", query = "SELECT i FROM ItensVenda i WHERE i.vlTotal = :vlTotal")})
public class ItensVenda implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "numPedido")
    private Integer numPedido;
    @Basic(optional = false)
    @Column(name = "id_prod")
    private int idProd;
    @Column(name = "codProduto")
    private String codProduto;
    @Column(name = "nome")
    private String nome;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "vlUnitario")
    private Double vlUnitario;
    @Column(name = "quant")
    private Integer quant;
    @Column(name = "vlTotal")
    private Double vlTotal;

    public ItensVenda() {
    }

    public ItensVenda(Integer id) {
        this.id = id;
    }

    public ItensVenda(Integer id, int idProd) {
        this.id = id;
        this.idProd = idProd;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumPedido() {
        return numPedido;
    }

    public void setNumPedido(Integer numPedido) {
        this.numPedido = numPedido;
    }

    public int getIdProd() {
        return idProd;
    }

    public void setIdProd(int idProd) {
        this.idProd = idProd;
    }

    public String getCodProduto() {
        return codProduto;
    }

    public void setCodProduto(String codProduto) {
        this.codProduto = codProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getVlUnitario() {
        return vlUnitario;
    }

    public void setVlUnitario(Double vlUnitario) {
        this.vlUnitario = vlUnitario;
    }

    public Integer getQuant() {
        return quant;
    }

    public void setQuant(Integer quant) {
        this.quant = quant;
    }

    public Double getVlTotal() {
        return vlTotal;
    }

    public void setVlTotal(Double vlTotal) {
        this.vlTotal = vlTotal;
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
        if (!(object instanceof ItensVenda)) {
            return false;
        }
        ItensVenda other = (ItensVenda) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Classes.ItensVenda[ id=" + id + " ]";
    }
    
}
