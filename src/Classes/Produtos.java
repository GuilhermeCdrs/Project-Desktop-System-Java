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
@Table(name = "produtos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Produtos.findAll", query = "SELECT p FROM Produtos p")
    , @NamedQuery(name = "Produtos.findByCodInterno", query = "SELECT p FROM Produtos p WHERE p.codInterno = :codInterno")
    , @NamedQuery(name = "Produtos.findByCodEan1", query = "SELECT p FROM Produtos p WHERE p.codEan1 = :codEan1")
    , @NamedQuery(name = "Produtos.findByDescricaoCompleta", query = "SELECT p FROM Produtos p WHERE p.descricaoCompleta = :descricaoCompleta")
    , @NamedQuery(name = "Produtos.findByCpfCgcFornecedor", query = "SELECT p FROM Produtos p WHERE p.cpfCgcFornecedor = :cpfCgcFornecedor")
    , @NamedQuery(name = "Produtos.findByPrecoVenda", query = "SELECT p FROM Produtos p WHERE p.precoVenda = :precoVenda")
    , @NamedQuery(name = "Produtos.findByDataUltAlt", query = "SELECT p FROM Produtos p WHERE p.dataUltAlt = :dataUltAlt")
    , @NamedQuery(name = "Produtos.findByAtivo", query = "SELECT p FROM Produtos p WHERE p.ativo = :ativo")})
public class Produtos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_interno")
    private Integer codInterno;
    @Column(name = "cod_ean1")
    private String codEan1;
    @Column(name = "descricao_completa")
    private String descricaoCompleta;
    @Column(name = "cpf_cgc_fornecedor")
    private String cpfCgcFornecedor;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "preco_venda")
    private Double precoVenda;
    @Column(name = "data_ult_alt")
    private String dataUltAlt;
    @Column(name = "ativo")
    private Boolean ativo;

    public Produtos() {
    }

    public Produtos(Integer codInterno) {
        this.codInterno = codInterno;
    }

    public Integer getCodInterno() {
        return codInterno;
    }

    public void setCodInterno(Integer codInterno) {
        this.codInterno = codInterno;
    }

    public String getCodEan1() {
        return codEan1;
    }

    public void setCodEan1(String codEan1) {
        this.codEan1 = codEan1;
    }

    public String getDescricaoCompleta() {
        return descricaoCompleta;
    }

    public void setDescricaoCompleta(String descricaoCompleta) {
        this.descricaoCompleta = descricaoCompleta;
    }

    public String getCpfCgcFornecedor() {
        return cpfCgcFornecedor;
    }

    public void setCpfCgcFornecedor(String cpfCgcFornecedor) {
        this.cpfCgcFornecedor = cpfCgcFornecedor;
    }

    public Double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(Double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public String getDataUltAlt() {
        return dataUltAlt;
    }

    public void setDataUltAlt(String dataUltAlt) {
        this.dataUltAlt = dataUltAlt;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codInterno != null ? codInterno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Produtos)) {
            return false;
        }
        Produtos other = (Produtos) object;
        if ((this.codInterno == null && other.codInterno != null) || (this.codInterno != null && !this.codInterno.equals(other.codInterno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Classes.Produtos[ codInterno=" + codInterno + " ]";
    }
    
}
