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
@Table(name = "vendas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vendas.findAll", query = "SELECT v FROM Vendas v")
    , @NamedQuery(name = "Vendas.findByNumPedido", query = "SELECT v FROM Vendas v WHERE v.numPedido = :numPedido")
    , @NamedQuery(name = "Vendas.findByCpfCliente", query = "SELECT v FROM Vendas v WHERE v.cpfCliente = :cpfCliente")
    , @NamedQuery(name = "Vendas.findByNome", query = "SELECT v FROM Vendas v WHERE v.nome = :nome")
    , @NamedQuery(name = "Vendas.findByQuant", query = "SELECT v FROM Vendas v WHERE v.quant = :quant")
    , @NamedQuery(name = "Vendas.findByDtVenda", query = "SELECT v FROM Vendas v WHERE v.dtVenda = :dtVenda")
    , @NamedQuery(name = "Vendas.findByValor", query = "SELECT v FROM Vendas v WHERE v.valor = :valor")})
public class Vendas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "numPedido")
    private Integer numPedido;
    @Column(name = "cpf_cliente")
    private String cpfCliente;
    @Column(name = "nome")
    private String nome;
    @Column(name = "quant")
    private Integer quant;
    @Column(name = "dt_venda")
    private String dtVenda;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Double valor;

    public Vendas() {
    }

    public Vendas(Integer numPedido) {
        this.numPedido = numPedido;
    }

    public Integer getNumPedido() {
        return numPedido;
    }

    public void setNumPedido(Integer numPedido) {
        this.numPedido = numPedido;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getQuant() {
        return quant;
    }

    public void setQuant(Integer quant) {
        this.quant = quant;
    }

    public String getDtVenda() {
        return dtVenda;
    }

    public void setDtVenda(String dtVenda) {
        this.dtVenda = dtVenda;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numPedido != null ? numPedido.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vendas)) {
            return false;
        }
        Vendas other = (Vendas) object;
        if ((this.numPedido == null && other.numPedido != null) || (this.numPedido != null && !this.numPedido.equals(other.numPedido))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Classes.Vendas[ numPedido=" + numPedido + " ]";
    }
    
}
