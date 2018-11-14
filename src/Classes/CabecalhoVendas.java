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
@Table(name = "cabecalho_vendas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CabecalhoVendas.findAll", query = "SELECT c FROM CabecalhoVendas c")
    , @NamedQuery(name = "CabecalhoVendas.findByNumPedido", query = "SELECT c FROM CabecalhoVendas c WHERE c.numPedido = :numPedido")
    , @NamedQuery(name = "CabecalhoVendas.findByDatavenda", query = "SELECT c FROM CabecalhoVendas c WHERE c.datavenda = :datavenda")})
public class CabecalhoVendas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "num_pedido")
    private Integer numPedido;
    @Column(name = "datavenda")
    private String datavenda;

    public CabecalhoVendas() {
    }

    public CabecalhoVendas(Integer numPedido) {
        this.numPedido = numPedido;
    }

    public Integer getNumPedido() {
        return numPedido;
    }

    public void setNumPedido(Integer numPedido) {
        this.numPedido = numPedido;
    }

    public String getDatavenda() {
        return datavenda;
    }

    public void setDatavenda(String datavenda) {
        this.datavenda = datavenda;
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
        if (!(object instanceof CabecalhoVendas)) {
            return false;
        }
        CabecalhoVendas other = (CabecalhoVendas) object;
        if ((this.numPedido == null && other.numPedido != null) || (this.numPedido != null && !this.numPedido.equals(other.numPedido))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Classes.CabecalhoVendas[ numPedido=" + numPedido + " ]";
    }
    
}
