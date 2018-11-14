package Janela;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Classes.CabecalhoVendas;
import Classes.Clientes;
import Classes.ConnectionFactory;
import Classes.EstoqueSaldo;
import Classes.Fornecedores;
import Classes.ItensVenda;
import Classes.Produtos;
import Classes.Vendas;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author guilh
 */
public final class ProgMain extends javax.swing.JFrame {

    // Chamando a conexão
    EntityManager em;

    class hora implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            Calendar now = Calendar.getInstance();
            txtHoraSistema.setText(String.format("%1$tH:%1$tM:%1$tS", now));
        }
    }

    private void setColor(JPanel pane) {
        pane.setBackground(new Color(33, 40, 51));
    }

    private void resetColor(JPanel[] p, JPanel[] y) {
        for (int i = 0; i < p.length; i++) {
            p[i].setBackground(new Color(26, 32, 40));
        }
        for (int j = 0; j < y.length; j++) {
            y[j].setBackground(new Color(26, 32, 40));
        }
    }

    public void produtos1() {
        DefaultTableModel modeloP1 = (DefaultTableModel) tblProdutos1.getModel();
        modeloP1.setRowCount(0);
        // Populando a Tabela Produtos
        List p1 = em.createNamedQuery("Produtos.findAll", Produtos.class).getResultList();
        // (Find = encontrar)
        for (int i = 0; i < p1.size(); i++) {
            modeloP1.addRow(new Object[]{
                ((Produtos) p1.get(i)).getCodInterno(),
                ((Produtos) p1.get(i)).getCodEan1(),
                ((Produtos) p1.get(i)).getDescricaoCompleta(),
                ((Produtos) p1.get(i)).getPrecoVenda()
            });
        }
    }

    public void produtos2() {
        DefaultTableModel modeloP2 = (DefaultTableModel) tblProdutos2.getModel();
        modeloP2.setRowCount(0);
        List<EstoqueSaldo> p1 = em.createNamedQuery("EstoqueSaldo.findAll", EstoqueSaldo.class).getResultList();
        List<Produtos> p2 = em.createNamedQuery("Produtos.findAll", Produtos.class).getResultList();

//            List p2 = em.createQuery("SELECT * FROM busca").getResultList();

        for (int i = 0; i < p2.size(); i++) {
            modeloP2.addRow(new Object[]{
                ((Produtos) p2.get(i)).getCodInterno(),
                ((Produtos) p2.get(i)).getCpfCgcFornecedor(),
                ((Produtos) p2.get(i)).getCodEan1(),
                ((Produtos) p2.get(i)).getDescricaoCompleta(),
                ((Produtos) p2.get(i)).getPrecoVenda(),
                ((EstoqueSaldo) p1.get(i)).getSaldoEstoque(),
                ((Produtos) p2.get(i)).getDataUltAlt(), 
                ((Produtos) p2.get(i)).getAtivo()
            });
        }
    }

    public void clientes() {
        DefaultTableModel modeloC2 = (DefaultTableModel) tblClientes1.getModel();
        DefaultTableModel modeloC3 = (DefaultTableModel) tblClientesExcluidos.getModel();
        modeloC2.setRowCount(0);
        modeloC3.setRowCount(0);

        // Populando a Tabela Clientes
        List cliente = em.createNativeQuery("SELECT * FROM clientes WHERE ativo = true", Clientes.class).getResultList();
        for (int i = 0; i < cliente.size(); i++) {
            modeloC2.addRow(new Object[]{
                ((Clientes) cliente.get(i)).getId(),
                ((Clientes) cliente.get(i)).getNome(),
                ((Clientes) cliente.get(i)).getCpf()
            });
        }// Populando a Tabela Clientes Excluidos
        List excluidos = em.createNativeQuery("SELECT * FROM clientes WHERE ativo = false", Clientes.class).getResultList();
        for (int i = 0; i < excluidos.size(); i++) {
            modeloC3.addRow(new Object[]{
                ((Clientes) excluidos.get(i)).getId(),
                ((Clientes) excluidos.get(i)).getNome(),
                ((Clientes) excluidos.get(i)).getCpf()
            });
        }
    }

    public void fornecedores() {
        DefaultTableModel modeloF = (DefaultTableModel) tblFornecedores.getModel();
        modeloF.setRowCount(0);
        // Populando a Tabela Fornecedores
        List fornecedor = em.createNamedQuery("Fornecedores.findAll", Fornecedores.class).getResultList();
        for (int i = 0; i < fornecedor.size(); i++) {
            modeloF.addRow(new Object[]{
                ((Fornecedores) fornecedor.get(i)).getId(),
                ((Fornecedores) fornecedor.get(i)).getCnpj(),
                ((Fornecedores) fornecedor.get(i)).getNomeRazao()
            });
        }
    }

    public void estoque() {
        DefaultTableModel modeloE = (DefaultTableModel) tblEstoque.getModel();
        modeloE.setRowCount(0);
        // Populando a Tabela Fornecedores
        List p = em.createNamedQuery("Produtos.findAll", Produtos.class).getResultList();
        List e = em.createNamedQuery("EstoqueSaldo.findAll", EstoqueSaldo.class).getResultList();
        for (int i = 0; i < e.size(); i++) {
            modeloE.addRow(new Object[]{
                ((EstoqueSaldo) e.get(i)).getId(),
                ((Produtos) p.get(i)).getDescricaoCompleta(),
                ((EstoqueSaldo) e.get(i)).getSaldoEstoque()
            });
        }
    }

    public void vendas() {
        DefaultTableModel modeloV1 = (DefaultTableModel) tblVendas.getModel();
        modeloV1.setRowCount(0);

        // Populando a Tabela Vendas
        List v = em.createNamedQuery("Vendas.findAll", Vendas.class).getResultList();
        for (int i = 0; i < v.size(); i++) {
            modeloV1.addRow(new Object[]{
                ((Vendas) v.get(i)).getNumPedido(),
                ((Vendas) v.get(i)).getCpfCliente(),
                ((Vendas) v.get(i)).getNome(),
                ((Vendas) v.get(i)).getQuant(),
                ((Vendas) v.get(i)).getDtVenda(),
                ((Vendas) v.get(i)).getValor()
            });
        }
    }

    /**
     * Creates new form Main
     */
    public ProgMain() {

        em = new ConnectionFactory().getConnection();
        // Iniciando interface
        initComponents();
        // Populando as tabela produtos
        produtos1();
        produtos2();
        // Populando a tabela Estoque
        estoque();
        // Populando a tabela Clientes
        clientes();
        // Populando a tabela Forncedores
        fornecedores();
        // Populando a tabela Vendas
        vendas();

        setLocationRelativeTo(null);
        txtTotalCompra.setText("0");
        txtHoraSistema.setBackground(new Color(0, 0, 0, 30));
        btnFinalizarCompra.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel19 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        p6 = new javax.swing.JPanel();
        painel = new javax.swing.JTabbedPane();
        novacompra = new javax.swing.JPanel();
        btnFinalizarCompra = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblProdutos1 = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtCpf = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        btnEditarClienteVenda = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblItens = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txtPesquisaProduto = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        txtQuantidade = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        txtTotalCompra = new javax.swing.JTextField();
        jSeparator11 = new javax.swing.JSeparator();
        jSeparator12 = new javax.swing.JSeparator();
        jPanel14 = new javax.swing.JPanel();
        txtHoraSistema = new javax.swing.JTextField();
        clientes = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblClientesExcluidos = new javax.swing.JTable();
        jLabel37 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        txtNomeCliente = new javax.swing.JTextField();
        btnExcluirCliente1 = new javax.swing.JButton();
        btnAdicionarCliente1 = new javax.swing.JButton();
        txtCpfCliente = new javax.swing.JFormattedTextField();
        btnAtualizarCliente = new javax.swing.JButton();
        jScrollPane11 = new javax.swing.JScrollPane();
        tblClientes1 = new javax.swing.JTable();
        jSeparator13 = new javax.swing.JSeparator();
        jLabel23 = new javax.swing.JLabel();
        jSeparator14 = new javax.swing.JSeparator();
        btnAtivarCliente = new javax.swing.JButton();
        produtos = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblProdutos2 = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        txtCodBarra = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        txtCpfFornecedor = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        txtDescricao = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        txtPreco = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        txtData = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        txtQuant = new javax.swing.JTextField();
        btnAdd1 = new javax.swing.JButton();
        btnAtualizarProduto1 = new javax.swing.JButton();
        btnExcluirProduto1 = new javax.swing.JButton();
        btnAtivar = new javax.swing.JButton();
        fornecedores = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblFornecedores = new javax.swing.JTable();
        jLabel46 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        txtNomeFornecedor = new javax.swing.JTextField();
        btnAdicionarFornecedor = new javax.swing.JButton();
        btnAtualizarFornecedor = new javax.swing.JButton();
        txtCnpjFornecedor = new javax.swing.JTextField();
        btnExcluirFornecedor = new javax.swing.JButton();
        vendas = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblVendas = new javax.swing.JTable();
        jScrollPane12 = new javax.swing.JScrollPane();
        tblProdutosCompras = new javax.swing.JTable();
        btnAtualizar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblProdutoDetalhado = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        jSeparator10 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblEstoque = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        txtNomeProdutoEstoque = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        txtPrecoProdutoEstoque = new javax.swing.JTextField();
        txtUltimaAlteracaoEstoque = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        txtQuantProdutoEstoque = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        btnAtualizarFornecedor1 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        p1 = new javax.swing.JPanel();
        y1 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        p2 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        y2 = new javax.swing.JPanel();
        p3 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        y3 = new javax.swing.JPanel();
        p4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        y4 = new javax.swing.JPanel();
        p5 = new javax.swing.JPanel();
        y5 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        p7 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        y7 = new javax.swing.JPanel();
        p8 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        y6 = new javax.swing.JPanel();
        jSeparator8 = new javax.swing.JSeparator();

        jLabel19.setText("jLabel19");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(26, 32, 40));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Arial", 0, 55)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));

        p6.setBackground(new java.awt.Color(44, 62, 80));
        p6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        painel.setBackground(new java.awt.Color(26, 32, 40));
        painel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 51, 51), 1, true));
        painel.setForeground(new java.awt.Color(204, 204, 204));
        painel.setToolTipText("");
        painel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        painel.setFocusTraversalPolicyProvider(true);
        painel.setFocusable(false);
        painel.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        painel.setName(""); // NOI18N
        painel.setOpaque(true);
        painel.setVerifyInputWhenFocusTarget(false);

        novacompra.setBackground(new java.awt.Color(26, 32, 40));
        novacompra.setToolTipText("");
        novacompra.setFocusable(false);

        btnFinalizarCompra.setBackground(new java.awt.Color(0, 204, 102));
        btnFinalizarCompra.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        btnFinalizarCompra.setText("Finalizar");
        btnFinalizarCompra.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btnFinalizarCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinalizarCompraActionPerformed(evt);
            }
        });

        btnExcluir.setBackground(new java.awt.Color(255, 0, 0));
        btnExcluir.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnExcluir.setForeground(new java.awt.Color(255, 255, 255));
        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Trash Can_30px.png"))); // NOI18N
        btnExcluir.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Produto:");

        jScrollPane2.setBorder(null);
        jScrollPane2.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        tblProdutos1.setAutoCreateRowSorter(true);
        tblProdutos1.setBackground(new java.awt.Color(33, 40, 51));
        tblProdutos1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        tblProdutos1.setForeground(new java.awt.Color(204, 204, 204));
        tblProdutos1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Cod_Barra", "Descrição", "Preço"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProdutos1.setAlignmentX(1.0F);
        tblProdutos1.setAlignmentY(1.0F);
        tblProdutos1.setFocusable(false);
        tblProdutos1.setGridColor(new java.awt.Color(0, 0, 0));
        tblProdutos1.setInheritsPopupMenu(true);
        tblProdutos1.setRowHeight(25);
        tblProdutos1.setRowMargin(5);
        tblProdutos1.setSelectionBackground(new java.awt.Color(46, 56, 71));
        tblProdutos1.setSelectionForeground(new java.awt.Color(204, 204, 204));
        tblProdutos1.getTableHeader().setReorderingAllowed(false);
        tblProdutos1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tblProdutos1FocusLost(evt);
            }
        });
        tblProdutos1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProdutos1MouseClicked(evt);
            }
        });
        tblProdutos1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblProdutos1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblProdutos1KeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tblProdutos1);
        if (tblProdutos1.getColumnModel().getColumnCount() > 0) {
            tblProdutos1.getColumnModel().getColumn(0).setPreferredWidth(50);
            tblProdutos1.getColumnModel().getColumn(1).setPreferredWidth(140);
            tblProdutos1.getColumnModel().getColumn(2).setPreferredWidth(500);
            tblProdutos1.getColumnModel().getColumn(3).setPreferredWidth(60);
        }

        jLabel13.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Qtd:");

        jLabel10.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Produtos");

        jPanel9.setBackground(new java.awt.Color(26, 32, 40));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)), "Cliente", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Cpf:");

        txtCpf.setBackground(new java.awt.Color(33, 40, 51));
        txtCpf.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));
        txtCpf.setForeground(new java.awt.Color(204, 204, 204));
        try {
            txtCpf.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###-###-###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCpf.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCpf.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        txtCpf.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtCpf.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCpfMouseClicked(evt);
            }
        });
        txtCpf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCpfActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Nome:");

        txtNome.setBackground(new java.awt.Color(33, 40, 51));
        txtNome.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtNome.setForeground(new java.awt.Color(204, 204, 204));
        txtNome.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNome.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));
        txtNome.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        txtNome.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomeFocusLost(evt);
            }
        });
        txtNome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtNomeMouseClicked(evt);
            }
        });
        txtNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNomeKeyPressed(evt);
            }
        });

        btnEditarClienteVenda.setBackground(new java.awt.Color(107, 107, 107));
        btnEditarClienteVenda.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnEditarClienteVenda.setText("Alterar");
        btnEditarClienteVenda.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btnEditarClienteVenda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditarClienteVendaMouseClicked(evt);
            }
        });
        btnEditarClienteVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarClienteVendaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnEditarClienteVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnEditarClienteVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jScrollPane7.setBorder(null);

        tblItens.setBackground(new java.awt.Color(33, 40, 51));
        tblItens.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        tblItens.setForeground(new java.awt.Color(204, 204, 204));
        tblItens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Cod Barras", "Nome", "Quantidade", "Valor unitário", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblItens.setFocusable(false);
        tblItens.setGridColor(new java.awt.Color(0, 0, 0));
        tblItens.setRowHeight(25);
        tblItens.setRowMargin(5);
        tblItens.setSelectionBackground(new java.awt.Color(46, 56, 71));
        tblItens.setSelectionForeground(new java.awt.Color(204, 204, 204));
        tblItens.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblItensKeyPressed(evt);
            }
        });
        jScrollPane7.setViewportView(tblItens);
        if (tblItens.getColumnModel().getColumnCount() > 0) {
            tblItens.getColumnModel().getColumn(0).setPreferredWidth(10);
            tblItens.getColumnModel().getColumn(1).setPreferredWidth(30);
            tblItens.getColumnModel().getColumn(2).setPreferredWidth(300);
            tblItens.getColumnModel().getColumn(3).setPreferredWidth(5);
            tblItens.getColumnModel().getColumn(4).setPreferredWidth(40);
            tblItens.getColumnModel().getColumn(5).setPreferredWidth(50);
        }

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Meu Carrinho");

        txtPesquisaProduto.setBackground(new java.awt.Color(33, 40, 51));
        txtPesquisaProduto.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtPesquisaProduto.setForeground(new java.awt.Color(204, 204, 204));
        txtPesquisaProduto.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));
        txtPesquisaProduto.addHierarchyListener(new java.awt.event.HierarchyListener() {
            public void hierarchyChanged(java.awt.event.HierarchyEvent evt) {
                txtPesquisaProdutoHierarchyChanged(evt);
            }
        });
        txtPesquisaProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPesquisaProdutoActionPerformed(evt);
            }
        });
        txtPesquisaProduto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisaProdutoKeyReleased(evt);
            }
        });

        btnAdd.setBackground(new java.awt.Color(30, 153, 71));
        btnAdd.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnAdd.setText("Adicionar");
        btnAdd.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        txtQuantidade.setBackground(new java.awt.Color(33, 40, 51));
        txtQuantidade.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtQuantidade.setForeground(new java.awt.Color(204, 204, 204));
        txtQuantidade.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtQuantidade.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));
        txtQuantidade.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtQuantidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQuantidadeActionPerformed(evt);
            }
        });

        jLabel36.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setText("Total:");

        txtTotalCompra.setBackground(new java.awt.Color(33, 40, 51));
        txtTotalCompra.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        txtTotalCompra.setForeground(new java.awt.Color(255, 0, 0));
        txtTotalCompra.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotalCompra.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));
        txtTotalCompra.setFocusable(false);

        jPanel14.setBackground(new java.awt.Color(26, 32, 40));
        jPanel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

        txtHoraSistema.setBackground(new java.awt.Color(26, 32, 40));
        txtHoraSistema.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        txtHoraSistema.setForeground(new java.awt.Color(153, 153, 153));
        txtHoraSistema.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtHoraSistema.setText("00:00:00");
        txtHoraSistema.setBorder(null);
        txtHoraSistema.setDisabledTextColor(new java.awt.Color(44, 62, 80));
        txtHoraSistema.setFocusable(false);
        txtHoraSistema.setSelectedTextColor(new java.awt.Color(51, 51, 51));
        txtHoraSistema.setSelectionColor(new java.awt.Color(44, 62, 80));
        txtHoraSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHoraSistemaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtHoraSistema, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(txtHoraSistema, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout novacompraLayout = new javax.swing.GroupLayout(novacompra);
        novacompra.setLayout(novacompraLayout);
        novacompraLayout.setHorizontalGroup(
            novacompraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(novacompraLayout.createSequentialGroup()
                .addGroup(novacompraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(novacompraLayout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addGroup(novacompraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(novacompraLayout.createSequentialGroup()
                                .addGap(117, 117, 117)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPesquisaProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(novacompraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(novacompraLayout.createSequentialGroup()
                                    .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3)
                                    .addGap(33, 33, 33)
                                    .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1008, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, novacompraLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(324, 324, 324)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 266, Short.MAX_VALUE)
                        .addComponent(jLabel36)
                        .addGap(18, 18, 18)
                        .addComponent(txtTotalCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(57, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, novacompraLayout.createSequentialGroup()
                .addGroup(novacompraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(novacompraLayout.createSequentialGroup()
                        .addGap(161, 161, 161)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(novacompraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnExcluir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnFinalizarCompra, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(novacompraLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 937, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(95, 95, 95))
        );
        novacompraLayout.setVerticalGroup(
            novacompraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, novacompraLayout.createSequentialGroup()
                .addGroup(novacompraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(novacompraLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(novacompraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTotalCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel36)
                            .addComponent(jLabel10)))
                    .addGroup(novacompraLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(novacompraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(novacompraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPesquisaProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel13)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(novacompraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(novacompraLayout.createSequentialGroup()
                        .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnFinalizarCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48))
        );

        painel.addTab("Vendas", novacompra);

        clientes.setBackground(new java.awt.Color(26, 32, 40));

        jScrollPane8.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane8.setBorder(null);
        jScrollPane8.setAlignmentX(1.0F);
        jScrollPane8.setAlignmentY(1.0F);
        jScrollPane8.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        tblClientesExcluidos.setAutoCreateRowSorter(true);
        tblClientesExcluidos.setBackground(new java.awt.Color(33, 40, 51));
        tblClientesExcluidos.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        tblClientesExcluidos.setForeground(new java.awt.Color(204, 204, 204));
        tblClientesExcluidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nome", "Cpf"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblClientesExcluidos.setFocusable(false);
        tblClientesExcluidos.setGridColor(new java.awt.Color(0, 0, 0));
        tblClientesExcluidos.setRowHeight(25);
        tblClientesExcluidos.setSelectionBackground(new java.awt.Color(46, 56, 71));
        tblClientesExcluidos.setSelectionForeground(new java.awt.Color(204, 204, 204));
        tblClientesExcluidos.getTableHeader().setResizingAllowed(false);
        tblClientesExcluidos.getTableHeader().setReorderingAllowed(false);
        tblClientesExcluidos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesExcluidosMouseClicked(evt);
            }
        });
        tblClientesExcluidos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblClientesExcluidosKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblClientesExcluidosKeyReleased(evt);
            }
        });
        jScrollPane8.setViewportView(tblClientesExcluidos);
        if (tblClientesExcluidos.getColumnModel().getColumnCount() > 0) {
            tblClientesExcluidos.getColumnModel().getColumn(0).setResizable(false);
            tblClientesExcluidos.getColumnModel().getColumn(1).setResizable(false);
            tblClientesExcluidos.getColumnModel().getColumn(2).setResizable(false);
        }

        jLabel37.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("Clientes ");

        jPanel10.setBackground(new java.awt.Color(26, 32, 40));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)), "Cliente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel10.setForeground(new java.awt.Color(255, 255, 255));
        jPanel10.setToolTipText("");
        jPanel10.setDoubleBuffered(false);
        jPanel10.setInheritsPopupMenu(true);

        jLabel38.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("Nome");
        jLabel38.setToolTipText("");

        jLabel39.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setText("CPF");
        jLabel39.setToolTipText("");

        txtNomeCliente.setBackground(new java.awt.Color(33, 40, 51));
        txtNomeCliente.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtNomeCliente.setForeground(new java.awt.Color(204, 204, 204));
        txtNomeCliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNomeCliente.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));

        btnExcluirCliente1.setBackground(new java.awt.Color(141, 28, 41));
        btnExcluirCliente1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnExcluirCliente1.setText("Excluir");
        btnExcluirCliente1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btnExcluirCliente1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirCliente1ActionPerformed(evt);
            }
        });

        btnAdicionarCliente1.setBackground(new java.awt.Color(30, 153, 71));
        btnAdicionarCliente1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnAdicionarCliente1.setText("Adicionar");
        btnAdicionarCliente1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btnAdicionarCliente1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarCliente1ActionPerformed(evt);
            }
        });

        txtCpfCliente.setBackground(new java.awt.Color(33, 40, 51));
        txtCpfCliente.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));
        txtCpfCliente.setForeground(new java.awt.Color(204, 204, 204));
        try {
            txtCpfCliente.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###-###-###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCpfCliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCpfCliente.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtCpfCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCpfClienteActionPerformed(evt);
            }
        });

        btnAtualizarCliente.setBackground(new java.awt.Color(102, 102, 102));
        btnAtualizarCliente.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnAtualizarCliente.setText("Atualizar");
        btnAtualizarCliente.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btnAtualizarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jLabel39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCpfCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAdicionarCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(btnAtualizarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(btnExcluirCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNomeCliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel38)
                        .addComponent(jLabel39)
                        .addComponent(txtCpfCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnExcluirCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdicionarCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAtualizarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jScrollPane11.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane11.setBorder(null);
        jScrollPane11.setAlignmentX(1.0F);
        jScrollPane11.setAlignmentY(1.0F);
        jScrollPane11.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        tblClientes1.setAutoCreateRowSorter(true);
        tblClientes1.setBackground(new java.awt.Color(33, 40, 51));
        tblClientes1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        tblClientes1.setForeground(new java.awt.Color(204, 204, 204));
        tblClientes1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nome", "Cpf"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblClientes1.setFocusable(false);
        tblClientes1.setGridColor(new java.awt.Color(0, 0, 0));
        tblClientes1.setRowHeight(25);
        tblClientes1.setSelectionBackground(new java.awt.Color(46, 56, 71));
        tblClientes1.setSelectionForeground(new java.awt.Color(204, 204, 204));
        tblClientes1.getTableHeader().setResizingAllowed(false);
        tblClientes1.getTableHeader().setReorderingAllowed(false);
        tblClientes1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientes1MouseClicked(evt);
            }
        });
        tblClientes1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblClientes1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblClientes1KeyReleased(evt);
            }
        });
        jScrollPane11.setViewportView(tblClientes1);
        if (tblClientes1.getColumnModel().getColumnCount() > 0) {
            tblClientes1.getColumnModel().getColumn(0).setResizable(false);
            tblClientes1.getColumnModel().getColumn(1).setResizable(false);
            tblClientes1.getColumnModel().getColumn(2).setResizable(false);
        }

        jLabel23.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Clientes Excluidos");

        btnAtivarCliente.setBackground(new java.awt.Color(102, 102, 102));
        btnAtivarCliente.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnAtivarCliente.setText("Restaurar");
        btnAtivarCliente.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btnAtivarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtivarClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout clientesLayout = new javax.swing.GroupLayout(clientes);
        clientes.setLayout(clientesLayout);
        clientesLayout.setHorizontalGroup(
            clientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(clientesLayout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addGroup(clientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, clientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(clientesLayout.createSequentialGroup()
                            .addGap(429, 429, 429)
                            .addComponent(jLabel37))
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 921, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(clientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAtivarCliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 921, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, clientesLayout.createSequentialGroup()
                                .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel23)
                                .addGap(18, 18, 18)
                                .addComponent(jSeparator14, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, clientesLayout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(179, 179, 179)))
                .addContainerGap(120, Short.MAX_VALUE))
        );
        clientesLayout.setVerticalGroup(
            clientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(clientesLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel37)
                .addGap(28, 28, 28)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(clientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel23)
                    .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator14, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAtivarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        painel.addTab("Clientes", clientes);

        produtos.setBackground(new java.awt.Color(26, 32, 40));

        jScrollPane4.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane4.setBorder(null);
        jScrollPane4.setEnabled(false);
        jScrollPane4.setFocusTraversalPolicyProvider(true);
        jScrollPane4.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        tblProdutos2.setAutoCreateRowSorter(true);
        tblProdutos2.setBackground(new java.awt.Color(33, 40, 51));
        tblProdutos2.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        tblProdutos2.setForeground(new java.awt.Color(204, 204, 204));
        tblProdutos2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "CPF Fornecedor", "Cod_Barra", "Descrição_Completa", "Preço", "Quant", "Data_Ult_Alt", "Ativo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProdutos2.setFocusable(false);
        tblProdutos2.setGridColor(new java.awt.Color(0, 0, 0));
        tblProdutos2.setRowHeight(25);
        tblProdutos2.setRowMargin(5);
        tblProdutos2.setSelectionBackground(new java.awt.Color(46, 56, 71));
        tblProdutos2.setSelectionForeground(new java.awt.Color(204, 204, 204));
        tblProdutos2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProdutos2MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblProdutos2MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblProdutos2MouseReleased(evt);
            }
        });
        tblProdutos2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblProdutos2KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblProdutos2KeyReleased(evt);
            }
        });
        jScrollPane4.setViewportView(tblProdutos2);
        if (tblProdutos2.getColumnModel().getColumnCount() > 0) {
            tblProdutos2.getColumnModel().getColumn(0).setPreferredWidth(5);
            tblProdutos2.getColumnModel().getColumn(1).setPreferredWidth(85);
            tblProdutos2.getColumnModel().getColumn(2).setPreferredWidth(80);
            tblProdutos2.getColumnModel().getColumn(3).setPreferredWidth(280);
            tblProdutos2.getColumnModel().getColumn(4).setPreferredWidth(5);
            tblProdutos2.getColumnModel().getColumn(6).setPreferredWidth(55);
            tblProdutos2.getColumnModel().getColumn(7).setPreferredWidth(10);
        }

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Produtos");
        jLabel12.setToolTipText("");

        jPanel12.setBackground(new java.awt.Color(26, 32, 40));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true), "Produto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel12.setToolTipText("");
        jPanel12.setDoubleBuffered(false);
        jPanel12.setInheritsPopupMenu(true);

        txtCodBarra.setBackground(new java.awt.Color(33, 40, 51));
        txtCodBarra.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtCodBarra.setForeground(new java.awt.Color(204, 204, 204));
        txtCodBarra.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodBarra.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));
        txtCodBarra.setDisabledTextColor(new java.awt.Color(51, 51, 51));

        jLabel40.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setText("Fornecedor");

        jLabel41.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setText("Cod Barra");

        txtCpfFornecedor.setBackground(new java.awt.Color(33, 40, 51));
        txtCpfFornecedor.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtCpfFornecedor.setForeground(new java.awt.Color(204, 204, 204));
        txtCpfFornecedor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCpfFornecedor.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));
        txtCpfFornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCpfFornecedorActionPerformed(evt);
            }
        });

        jLabel42.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setText("Descrição");

        txtDescricao.setBackground(new java.awt.Color(33, 40, 51));
        txtDescricao.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtDescricao.setForeground(new java.awt.Color(204, 204, 204));
        txtDescricao.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDescricao.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));
        txtDescricao.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        txtDescricao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescricaoActionPerformed(evt);
            }
        });
        txtDescricao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDescricaoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDescricaoKeyReleased(evt);
            }
        });

        jLabel43.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setText("Valor");

        txtPreco.setBackground(new java.awt.Color(33, 40, 51));
        txtPreco.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtPreco.setForeground(new java.awt.Color(204, 204, 204));
        txtPreco.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPreco.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));
        txtPreco.setName(""); // NOI18N

        jLabel44.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 255, 255));
        jLabel44.setText("Data ");

        txtData.setBackground(new java.awt.Color(33, 40, 51));
        txtData.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtData.setForeground(new java.awt.Color(204, 204, 204));
        txtData.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtData.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));
        txtData.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        txtData.setEnabled(false);

        jLabel45.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 255, 255));
        jLabel45.setText("Quant");

        txtQuant.setBackground(new java.awt.Color(33, 40, 51));
        txtQuant.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtQuant.setForeground(new java.awt.Color(204, 204, 204));
        txtQuant.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtQuant.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));
        txtQuant.setName(""); // NOI18N

        btnAdd1.setBackground(new java.awt.Color(30, 153, 71));
        btnAdd1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnAdd1.setText("Adicionar");
        btnAdd1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btnAdd1.setPreferredSize(new java.awt.Dimension(121, 41));
        btnAdd1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdd1ActionPerformed(evt);
            }
        });

        btnAtualizarProduto1.setBackground(new java.awt.Color(102, 102, 102));
        btnAtualizarProduto1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnAtualizarProduto1.setText("Atualizar");
        btnAtualizarProduto1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btnAtualizarProduto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarProduto1ActionPerformed(evt);
            }
        });

        btnExcluirProduto1.setBackground(new java.awt.Color(141, 28, 41));
        btnExcluirProduto1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnExcluirProduto1.setText("Excluir");
        btnExcluirProduto1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btnExcluirProduto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirProduto1ActionPerformed(evt);
            }
        });

        btnAtivar.setBackground(new java.awt.Color(182, 94, 67));
        btnAtivar.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnAtivar.setText("Ativar");
        btnAtivar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btnAtivar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtivarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel42)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 587, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(jLabel43)
                .addGap(12, 12, 12)
                .addComponent(txtPreco, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel40)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCpfFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel45)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtQuant, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel41)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCodBarra)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel44)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAdd1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(btnAtualizarProduto1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(btnExcluirProduto1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(btnAtivar, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(175, 175, 175))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43)
                    .addComponent(txtPreco, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel45)
                        .addComponent(txtQuant, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCpfFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel40)
                        .addComponent(jLabel41))
                    .addComponent(txtCodBarra, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel44)
                        .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAtualizarProduto1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExcluirProduto1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAtivar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout produtosLayout = new javax.swing.GroupLayout(produtos);
        produtos.setLayout(produtosLayout);
        produtosLayout.setHorizontalGroup(
            produtosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, produtosLayout.createSequentialGroup()
                .addContainerGap(62, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1012, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69))
            .addGroup(produtosLayout.createSequentialGroup()
                .addGroup(produtosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(produtosLayout.createSequentialGroup()
                        .addGap(526, 526, 526)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(produtosLayout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        produtosLayout.setVerticalGroup(
            produtosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(produtosLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel12)
                .addGap(26, 26, 26)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(80, Short.MAX_VALUE))
        );

        painel.addTab("Produtos ", produtos);

        fornecedores.setBackground(new java.awt.Color(26, 32, 40));

        jScrollPane10.setBorder(null);
        jScrollPane10.setAutoscrolls(true);
        jScrollPane10.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        tblFornecedores.setAutoCreateRowSorter(true);
        tblFornecedores.setBackground(new java.awt.Color(33, 40, 51));
        tblFornecedores.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        tblFornecedores.setForeground(new java.awt.Color(204, 204, 204));
        tblFornecedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Cnpj", "Nome"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblFornecedores.setFocusable(false);
        tblFornecedores.setGridColor(new java.awt.Color(0, 0, 0));
        tblFornecedores.setRowHeight(25);
        tblFornecedores.setRowMargin(5);
        tblFornecedores.setSelectionBackground(new java.awt.Color(46, 56, 71));
        tblFornecedores.setSelectionForeground(new java.awt.Color(204, 204, 204));
        tblFornecedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblFornecedoresMouseReleased(evt);
            }
        });
        tblFornecedores.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblFornecedoresKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblFornecedoresKeyReleased(evt);
            }
        });
        jScrollPane10.setViewportView(tblFornecedores);
        if (tblFornecedores.getColumnModel().getColumnCount() > 0) {
            tblFornecedores.getColumnModel().getColumn(2).setPreferredWidth(400);
        }

        jLabel46.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(255, 255, 255));
        jLabel46.setText("Fornecedores");

        jPanel11.setBackground(new java.awt.Color(26, 32, 40));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)), "Fornecedores", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 20), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel11.setToolTipText("");
        jPanel11.setDoubleBuffered(false);
        jPanel11.setInheritsPopupMenu(true);

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(255, 255, 255));
        jLabel47.setText("Nome");

        jLabel48.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(255, 255, 255));
        jLabel48.setText("Cnpj");

        txtNomeFornecedor.setBackground(new java.awt.Color(33, 40, 51));
        txtNomeFornecedor.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtNomeFornecedor.setForeground(new java.awt.Color(204, 204, 204));
        txtNomeFornecedor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNomeFornecedor.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));

        btnAdicionarFornecedor.setBackground(new java.awt.Color(30, 153, 71));
        btnAdicionarFornecedor.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        btnAdicionarFornecedor.setText("Adicionar");
        btnAdicionarFornecedor.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btnAdicionarFornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarFornecedorActionPerformed(evt);
            }
        });

        btnAtualizarFornecedor.setBackground(new java.awt.Color(102, 102, 102));
        btnAtualizarFornecedor.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        btnAtualizarFornecedor.setText("Atualizar");
        btnAtualizarFornecedor.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btnAtualizarFornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarFornecedorActionPerformed(evt);
            }
        });

        txtCnpjFornecedor.setBackground(new java.awt.Color(33, 40, 51));
        txtCnpjFornecedor.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtCnpjFornecedor.setForeground(new java.awt.Color(204, 204, 204));
        txtCnpjFornecedor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCnpjFornecedor.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));
        txtCnpjFornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCnpjFornecedorActionPerformed(evt);
            }
        });

        btnExcluirFornecedor.setBackground(new java.awt.Color(141, 28, 41));
        btnExcluirFornecedor.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        btnExcluirFornecedor.setText("Excluir");
        btnExcluirFornecedor.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btnExcluirFornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirFornecedorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel47)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtNomeFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addComponent(jLabel48)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCnpjFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAdicionarFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(btnAtualizarFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(btnExcluirFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(201, 201, 201))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNomeFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel47)
                    .addComponent(txtCnpjFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel48))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdicionarFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAtualizarFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExcluirFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout fornecedoresLayout = new javax.swing.GroupLayout(fornecedores);
        fornecedores.setLayout(fornecedoresLayout);
        fornecedoresLayout.setHorizontalGroup(
            fornecedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fornecedoresLayout.createSequentialGroup()
                .addGroup(fornecedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(fornecedoresLayout.createSequentialGroup()
                        .addGap(503, 503, 503)
                        .addComponent(jLabel46))
                    .addGroup(fornecedoresLayout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 982, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(fornecedoresLayout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(86, Short.MAX_VALUE))
        );
        fornecedoresLayout.setVerticalGroup(
            fornecedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fornecedoresLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel46)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(98, Short.MAX_VALUE))
        );

        painel.addTab("Fornecedores ", fornecedores);

        vendas.setBackground(new java.awt.Color(26, 32, 40));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Itens Vendas");

        jScrollPane9.setBorder(null);

        tblVendas.setAutoCreateRowSorter(true);
        tblVendas.setBackground(new java.awt.Color(33, 40, 51));
        tblVendas.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        tblVendas.setForeground(new java.awt.Color(204, 204, 204));
        tblVendas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod Venda", "Cpf", "Cliente", "Quantidade", "Data Venda", "Valor Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblVendas.setFocusable(false);
        tblVendas.setGridColor(new java.awt.Color(0, 0, 0));
        tblVendas.setRowHeight(25);
        tblVendas.setRowMargin(5);
        tblVendas.setSelectionBackground(new java.awt.Color(46, 56, 71));
        tblVendas.setSelectionForeground(new java.awt.Color(204, 204, 204));
        tblVendas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblVendasMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(tblVendas);

        jScrollPane12.setBorder(null);

        tblProdutosCompras.setAutoCreateRowSorter(true);
        tblProdutosCompras.setBackground(new java.awt.Color(33, 40, 51));
        tblProdutosCompras.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        tblProdutosCompras.setForeground(new java.awt.Color(204, 204, 204));
        tblProdutosCompras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Produto", "Preço/unidade", "Quantidade", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProdutosCompras.setFocusable(false);
        tblProdutosCompras.setGridColor(new java.awt.Color(0, 0, 0));
        tblProdutosCompras.setIntercellSpacing(new java.awt.Dimension(1, 5));
        tblProdutosCompras.setRowHeight(25);
        tblProdutosCompras.setSelectionBackground(new java.awt.Color(46, 56, 71));
        tblProdutosCompras.setSelectionForeground(new java.awt.Color(204, 204, 204));
        tblProdutosCompras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProdutosComprasMouseClicked(evt);
            }
        });
        jScrollPane12.setViewportView(tblProdutosCompras);
        if (tblProdutosCompras.getColumnModel().getColumnCount() > 0) {
            tblProdutosCompras.getColumnModel().getColumn(1).setPreferredWidth(400);
            tblProdutosCompras.getColumnModel().getColumn(2).setPreferredWidth(30);
            tblProdutosCompras.getColumnModel().getColumn(3).setPreferredWidth(30);
            tblProdutosCompras.getColumnModel().getColumn(4).setPreferredWidth(30);
        }

        btnAtualizar.setBackground(new java.awt.Color(102, 102, 102));
        btnAtualizar.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnAtualizar.setForeground(new java.awt.Color(255, 255, 255));
        btnAtualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Synchronize_30px.png"))); // NOI18N
        btnAtualizar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btnAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Vendas");

        jScrollPane6.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane6.setBorder(null);
        jScrollPane6.setEnabled(false);
        jScrollPane6.setFocusTraversalPolicyProvider(true);
        jScrollPane6.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        tblProdutoDetalhado.setAutoCreateRowSorter(true);
        tblProdutoDetalhado.setBackground(new java.awt.Color(33, 40, 51));
        tblProdutoDetalhado.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        tblProdutoDetalhado.setForeground(new java.awt.Color(204, 204, 204));
        tblProdutoDetalhado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "CPF Fornecedor", "Cod_Barra", "Descrição_Completa", "Preço", "Estoque", "Data_Ult_Alt"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProdutoDetalhado.setFocusable(false);
        tblProdutoDetalhado.setGridColor(new java.awt.Color(0, 0, 0));
        tblProdutoDetalhado.setRowHeight(25);
        tblProdutoDetalhado.setRowMargin(5);
        tblProdutoDetalhado.setSelectionBackground(new java.awt.Color(46, 56, 71));
        tblProdutoDetalhado.setSelectionForeground(new java.awt.Color(204, 204, 204));
        tblProdutoDetalhado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblProdutoDetalhadoMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblProdutoDetalhadoMouseReleased(evt);
            }
        });
        tblProdutoDetalhado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblProdutoDetalhadoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblProdutoDetalhadoKeyReleased(evt);
            }
        });
        jScrollPane6.setViewportView(tblProdutoDetalhado);
        if (tblProdutoDetalhado.getColumnModel().getColumnCount() > 0) {
            tblProdutoDetalhado.getColumnModel().getColumn(0).setPreferredWidth(5);
            tblProdutoDetalhado.getColumnModel().getColumn(1).setPreferredWidth(85);
            tblProdutoDetalhado.getColumnModel().getColumn(1).setHeaderValue("CPF Fornecedor");
            tblProdutoDetalhado.getColumnModel().getColumn(2).setPreferredWidth(80);
            tblProdutoDetalhado.getColumnModel().getColumn(3).setPreferredWidth(280);
            tblProdutoDetalhado.getColumnModel().getColumn(4).setPreferredWidth(5);
            tblProdutoDetalhado.getColumnModel().getColumn(4).setHeaderValue("Preço");
            tblProdutoDetalhado.getColumnModel().getColumn(5).setPreferredWidth(8);
            tblProdutoDetalhado.getColumnModel().getColumn(6).setPreferredWidth(45);
        }

        jLabel18.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Detalhes (Produto)");

        javax.swing.GroupLayout vendasLayout = new javax.swing.GroupLayout(vendas);
        vendas.setLayout(vendasLayout);
        vendasLayout.setHorizontalGroup(
            vendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vendasLayout.createSequentialGroup()
                .addContainerGap(47, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 1056, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
            .addGroup(vendasLayout.createSequentialGroup()
                .addGroup(vendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(vendasLayout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addGroup(vendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(vendasLayout.createSequentialGroup()
                                .addGap(420, 420, 420)
                                .addComponent(jLabel4)
                                .addGap(366, 366, 366)
                                .addComponent(btnAtualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, vendasLayout.createSequentialGroup()
                                .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel18)
                                .addGap(18, 18, 18)
                                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 967, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 967, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(vendasLayout.createSequentialGroup()
                        .addGap(528, 528, 528)
                        .addComponent(jLabel7)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        vendasLayout.setVerticalGroup(
            vendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vendasLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addGroup(vendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(vendasLayout.createSequentialGroup()
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel4))
                    .addComponent(btnAtualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addGroup(vendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(vendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vendasLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(101, Short.MAX_VALUE))
        );

        painel.addTab("Vendas", vendas);

        jPanel2.setBackground(new java.awt.Color(26, 32, 40));

        jScrollPane5.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane5.setBorder(null);
        jScrollPane5.setEnabled(false);
        jScrollPane5.setFocusTraversalPolicyProvider(true);
        jScrollPane5.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        tblEstoque.setAutoCreateRowSorter(true);
        tblEstoque.setBackground(new java.awt.Color(33, 40, 51));
        tblEstoque.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        tblEstoque.setForeground(new java.awt.Color(204, 204, 204));
        tblEstoque.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Produto", "Quantidade"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblEstoque.setFocusable(false);
        tblEstoque.setGridColor(new java.awt.Color(0, 0, 0));
        tblEstoque.setRowHeight(25);
        tblEstoque.setRowMargin(5);
        tblEstoque.setSelectionBackground(new java.awt.Color(46, 56, 71));
        tblEstoque.setSelectionForeground(new java.awt.Color(204, 204, 204));
        tblEstoque.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEstoqueMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblEstoqueMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblEstoqueMouseReleased(evt);
            }
        });
        tblEstoque.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblEstoqueKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblEstoqueKeyReleased(evt);
            }
        });
        jScrollPane5.setViewportView(tblEstoque);
        if (tblEstoque.getColumnModel().getColumnCount() > 0) {
            tblEstoque.getColumnModel().getColumn(0).setResizable(false);
            tblEstoque.getColumnModel().getColumn(0).setPreferredWidth(100);
            tblEstoque.getColumnModel().getColumn(1).setResizable(false);
            tblEstoque.getColumnModel().getColumn(1).setPreferredWidth(600);
            tblEstoque.getColumnModel().getColumn(2).setResizable(false);
            tblEstoque.getColumnModel().getColumn(2).setPreferredWidth(100);
        }

        jLabel9.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Estoque");

        txtNomeProdutoEstoque.setBackground(new java.awt.Color(26, 32, 40));
        txtNomeProdutoEstoque.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtNomeProdutoEstoque.setForeground(new java.awt.Color(204, 204, 204));
        txtNomeProdutoEstoque.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNomeProdutoEstoque.setBorder(null);
        txtNomeProdutoEstoque.setCaretColor(new java.awt.Color(26, 32, 40));
        txtNomeProdutoEstoque.setDisabledTextColor(new java.awt.Color(26, 32, 40));
        txtNomeProdutoEstoque.setEnabled(false);
        txtNomeProdutoEstoque.setSelectedTextColor(new java.awt.Color(26, 32, 40));

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(255, 255, 255));
        jLabel49.setText("Nome");

        jLabel50.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(255, 255, 255));
        jLabel50.setText("Preço");

        txtPrecoProdutoEstoque.setBackground(new java.awt.Color(26, 32, 40));
        txtPrecoProdutoEstoque.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtPrecoProdutoEstoque.setForeground(new java.awt.Color(204, 204, 204));
        txtPrecoProdutoEstoque.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPrecoProdutoEstoque.setBorder(null);
        txtPrecoProdutoEstoque.setCaretColor(new java.awt.Color(26, 32, 40));
        txtPrecoProdutoEstoque.setDisabledTextColor(new java.awt.Color(26, 32, 40));
        txtPrecoProdutoEstoque.setEnabled(false);
        txtPrecoProdutoEstoque.setOpaque(false);

        txtUltimaAlteracaoEstoque.setBackground(new java.awt.Color(26, 32, 40));
        txtUltimaAlteracaoEstoque.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtUltimaAlteracaoEstoque.setForeground(new java.awt.Color(204, 204, 204));
        txtUltimaAlteracaoEstoque.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUltimaAlteracaoEstoque.setBorder(null);
        txtUltimaAlteracaoEstoque.setCaretColor(new java.awt.Color(26, 32, 40));
        txtUltimaAlteracaoEstoque.setDisabledTextColor(new java.awt.Color(26, 32, 40));
        txtUltimaAlteracaoEstoque.setEnabled(false);

        jLabel51.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(255, 255, 255));
        jLabel51.setText("Ultima Alteração");

        txtQuantProdutoEstoque.setBackground(new java.awt.Color(33, 40, 51));
        txtQuantProdutoEstoque.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtQuantProdutoEstoque.setForeground(new java.awt.Color(204, 204, 204));
        txtQuantProdutoEstoque.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtQuantProdutoEstoque.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));

        jLabel52.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(255, 255, 255));
        jLabel52.setText("Quantidade");

        btnAtualizarFornecedor1.setBackground(new java.awt.Color(102, 102, 102));
        btnAtualizarFornecedor1.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        btnAtualizarFornecedor1.setText("Atualizar");
        btnAtualizarFornecedor1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btnAtualizarFornecedor1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarFornecedor1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(528, 528, 528)
                        .addComponent(jLabel9))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(123, 123, 123)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 881, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel52)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtQuantProdutoEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel49)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNomeProdutoEstoque)
                                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 554, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel50)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jSeparator2)
                                    .addComponent(txtPrecoProdutoEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(81, 81, 81)
                                .addComponent(jLabel51)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jSeparator3)
                                    .addComponent(txtUltimaAlteracaoEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(139, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnAtualizarFornecedor1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(197, 197, 197))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel49)
                    .addComponent(txtNomeProdutoEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPrecoProdutoEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel50)
                    .addComponent(txtUltimaAlteracaoEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel51))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtQuantProdutoEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel52))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAtualizarFornecedor1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(137, Short.MAX_VALUE))
        );

        painel.addTab("Estoque", jPanel2);

        javax.swing.GroupLayout p6Layout = new javax.swing.GroupLayout(p6);
        p6.setLayout(p6Layout);
        p6Layout.setHorizontalGroup(
            p6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p6Layout.createSequentialGroup()
                .addComponent(painel, javax.swing.GroupLayout.PREFERRED_SIZE, 1147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        p6Layout.setVerticalGroup(
            p6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painel, javax.swing.GroupLayout.PREFERRED_SIZE, 955, Short.MAX_VALUE)
        );

        jLabel14.setBackground(new java.awt.Color(0, 102, 204));
        jLabel14.setFont(new java.awt.Font("Dialog", 1, 60)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 102, 204));
        jLabel14.setText("Next");

        jLabel15.setBackground(new java.awt.Color(33, 40, 51));
        jLabel15.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 102, 204));
        jLabel15.setText(".com");

        p1.setBackground(new java.awt.Color(26, 32, 40));
        p1.setPreferredSize(new java.awt.Dimension(288, 110));
        p1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                p1MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                p1MousePressed(evt);
            }
        });

        y1.setBackground(new java.awt.Color(26, 32, 40));
        y1.setPreferredSize(new java.awt.Dimension(3, 0));

        javax.swing.GroupLayout y1Layout = new javax.swing.GroupLayout(y1);
        y1.setLayout(y1Layout);
        y1Layout.setHorizontalGroup(
            y1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );
        y1Layout.setVerticalGroup(
            y1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/User_100px_1.png"))); // NOI18N

        javax.swing.GroupLayout p1Layout = new javax.swing.GroupLayout(p1);
        p1.setLayout(p1Layout);
        p1Layout.setHorizontalGroup(
            p1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, p1Layout.createSequentialGroup()
                .addComponent(y1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel21)
                .addGap(99, 99, 99))
        );
        p1Layout.setVerticalGroup(
            p1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p1Layout.createSequentialGroup()
                .addGroup(p1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, p1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(y1, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE))
                .addContainerGap())
        );

        p2.setBackground(new java.awt.Color(26, 32, 40));
        p2.setPreferredSize(new java.awt.Dimension(288, 110));
        p2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                p2MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                p2MousePressed(evt);
            }
        });

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Open Box_100px_1.png"))); // NOI18N

        y2.setBackground(new java.awt.Color(26, 32, 40));
        y2.setPreferredSize(new java.awt.Dimension(3, 0));

        javax.swing.GroupLayout y2Layout = new javax.swing.GroupLayout(y2);
        y2.setLayout(y2Layout);
        y2Layout.setHorizontalGroup(
            y2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );
        y2Layout.setVerticalGroup(
            y2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout p2Layout = new javax.swing.GroupLayout(p2);
        p2.setLayout(p2Layout);
        p2Layout.setHorizontalGroup(
            p2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, p2Layout.createSequentialGroup()
                .addComponent(y2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel20)
                .addGap(98, 98, 98))
        );
        p2Layout.setVerticalGroup(
            p2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(y2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
        );

        p3.setBackground(new java.awt.Color(26, 32, 40));
        p3.setPreferredSize(new java.awt.Dimension(288, 110));
        p3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                p3MouseClicked(evt);
            }
        });

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Amazon_100px.png"))); // NOI18N

        y3.setBackground(new java.awt.Color(26, 32, 40));
        y3.setPreferredSize(new java.awt.Dimension(3, 0));

        javax.swing.GroupLayout y3Layout = new javax.swing.GroupLayout(y3);
        y3.setLayout(y3Layout);
        y3Layout.setHorizontalGroup(
            y3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );
        y3Layout.setVerticalGroup(
            y3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout p3Layout = new javax.swing.GroupLayout(p3);
        p3.setLayout(p3Layout);
        p3Layout.setHorizontalGroup(
            p3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, p3Layout.createSequentialGroup()
                .addComponent(y3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel17)
                .addGap(99, 99, 99))
        );
        p3Layout.setVerticalGroup(
            p3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(y3, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
        );

        p4.setBackground(new java.awt.Color(26, 32, 40));
        p4.setPreferredSize(new java.awt.Dimension(288, 110));
        p4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                p4MouseClicked(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Buy_100px.png"))); // NOI18N

        y4.setBackground(new java.awt.Color(26, 32, 40));
        y4.setPreferredSize(new java.awt.Dimension(3, 0));

        javax.swing.GroupLayout y4Layout = new javax.swing.GroupLayout(y4);
        y4.setLayout(y4Layout);
        y4Layout.setHorizontalGroup(
            y4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );
        y4Layout.setVerticalGroup(
            y4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout p4Layout = new javax.swing.GroupLayout(p4);
        p4.setLayout(p4Layout);
        p4Layout.setHorizontalGroup(
            p4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p4Layout.createSequentialGroup()
                .addComponent(y4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(101, 101, 101)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        p4Layout.setVerticalGroup(
            p4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(y4, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
        );

        p5.setBackground(new java.awt.Color(26, 32, 40));
        p5.setPreferredSize(new java.awt.Dimension(288, 110));
        p5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                p5MouseClicked(evt);
            }
        });

        y5.setBackground(new java.awt.Color(26, 32, 40));
        y5.setPreferredSize(new java.awt.Dimension(3, 0));

        javax.swing.GroupLayout y5Layout = new javax.swing.GroupLayout(y5);
        y5.setLayout(y5Layout);
        y5Layout.setHorizontalGroup(
            y5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );
        y5Layout.setVerticalGroup(
            y5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Money Bag_100px_1.png"))); // NOI18N

        javax.swing.GroupLayout p5Layout = new javax.swing.GroupLayout(p5);
        p5.setLayout(p5Layout);
        p5Layout.setHorizontalGroup(
            p5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, p5Layout.createSequentialGroup()
                .addComponent(y5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 104, Short.MAX_VALUE)
                .addComponent(jLabel24)
                .addGap(93, 93, 93))
        );
        p5Layout.setVerticalGroup(
            p5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(y5, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
            .addGroup(p5Layout.createSequentialGroup()
                .addComponent(jLabel24)
                .addGap(0, 10, Short.MAX_VALUE))
        );

        p7.setBackground(new java.awt.Color(26, 32, 40));
        p7.setPreferredSize(new java.awt.Dimension(288, 110));
        p7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                p7MouseClicked(evt);
            }
        });

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Logout Rounded Left_100px.png"))); // NOI18N

        y7.setBackground(new java.awt.Color(26, 32, 40));
        y7.setPreferredSize(new java.awt.Dimension(3, 0));

        javax.swing.GroupLayout y7Layout = new javax.swing.GroupLayout(y7);
        y7.setLayout(y7Layout);
        y7Layout.setHorizontalGroup(
            y7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );
        y7Layout.setVerticalGroup(
            y7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout p7Layout = new javax.swing.GroupLayout(p7);
        p7.setLayout(p7Layout);
        p7Layout.setHorizontalGroup(
            p7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, p7Layout.createSequentialGroup()
                .addComponent(y7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(101, 101, 101)
                .addComponent(jLabel22)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        p7Layout.setVerticalGroup(
            p7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(y7, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
            .addGroup(p7Layout.createSequentialGroup()
                .addGap(0, 10, Short.MAX_VALUE)
                .addComponent(jLabel22))
        );

        p8.setBackground(new java.awt.Color(26, 32, 40));
        p8.setPreferredSize(new java.awt.Dimension(288, 110));
        p8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                p8MouseClicked(evt);
            }
        });

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Truck_100px_1.png"))); // NOI18N

        y6.setBackground(new java.awt.Color(26, 32, 40));
        y6.setPreferredSize(new java.awt.Dimension(3, 0));

        javax.swing.GroupLayout y6Layout = new javax.swing.GroupLayout(y6);
        y6.setLayout(y6Layout);
        y6Layout.setHorizontalGroup(
            y6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );
        y6Layout.setVerticalGroup(
            y6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout p8Layout = new javax.swing.GroupLayout(p8);
        p8.setLayout(p8Layout);
        p8Layout.setHorizontalGroup(
            p8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p8Layout.createSequentialGroup()
                .addComponent(y6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(100, 100, 100))
        );
        p8Layout.setVerticalGroup(
            p8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(y6, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
        );

        jSeparator8.setBackground(new java.awt.Color(75, 79, 81));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(p2, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                    .addComponent(p3, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                    .addComponent(p4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                    .addComponent(p7, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                    .addComponent(p1, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                    .addComponent(p8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel15)
                                        .addGap(38, 38, 38))
                                    .addComponent(jLabel2)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(p5, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 8, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(p6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(p1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(p2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(p3, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(p4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(p8, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(p5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(p7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(p6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        Timer timer = new Timer(1000, new hora());
        timer.start();

    }//GEN-LAST:event_formWindowOpened

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel1MouseClicked

    private void tblProdutoDetalhadoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblProdutoDetalhadoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tblProdutoDetalhadoKeyReleased

    private void tblProdutoDetalhadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblProdutoDetalhadoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblProdutoDetalhadoKeyPressed

    private void tblProdutoDetalhadoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProdutoDetalhadoMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tblProdutoDetalhadoMouseReleased

    private void tblProdutoDetalhadoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProdutoDetalhadoMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblProdutoDetalhadoMousePressed

    private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarActionPerformed
        // TODO add your handling code here:
        DefaultTableModel modeloI = (DefaultTableModel) tblProdutosCompras.getModel();
        DefaultTableModel modeloPD = (DefaultTableModel) tblProdutoDetalhado.getModel();
        modeloI.setRowCount(0);
        modeloPD.setRowCount(0);
        vendas();
    }//GEN-LAST:event_btnAtualizarActionPerformed

    private void tblProdutosComprasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProdutosComprasMouseClicked
        // TODO add your handling code here:
        DefaultTableModel modeloP3 = (DefaultTableModel) this.tblProdutoDetalhado.getModel();
        int id = Integer.parseInt(tblProdutosCompras.getValueAt(tblProdutosCompras.getSelectedRow(), 0).toString());
        em.getTransaction().begin();
        Produtos p = em.find(Produtos.class, id);
        EstoqueSaldo e = em.find(EstoqueSaldo.class, id);

        modeloP3.setRowCount(0);
        modeloP3.addRow(new Object[]{
            p.getCodInterno(),
            p.getCpfCgcFornecedor(),
            p.getCodEan1(),
            p.getDescricaoCompleta(),
            p.getPrecoVenda(),
            e.getSaldoEstoque(),
            p.getDataUltAlt()
        });
        em.getTransaction().commit();
    }//GEN-LAST:event_tblProdutosComprasMouseClicked

    private void tblVendasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVendasMouseClicked
        DefaultTableModel modeloPC = (DefaultTableModel) this.tblProdutosCompras.getModel();
        DefaultTableModel modeloV = (DefaultTableModel) tblVendas.getModel();
        DefaultTableModel modeloP3 = (DefaultTableModel) tblProdutoDetalhado.getModel();

        modeloP3.setRowCount(0);
        modeloPC.setRowCount(0);

        int id = Integer.parseInt(tblVendas.getValueAt(tblVendas.getSelectedRow(), 0).toString());

        List itensVenda = em.createNativeQuery("SELECT * FROM itens_venda WHERE numPedido = '" + id + "'", ItensVenda.class).getResultList();

        for (int i = 0; i < itensVenda.size(); i++) {
            modeloPC.addRow(new Object[]{
                ((ItensVenda) itensVenda.get(i)).getIdProd(),
                ((ItensVenda) itensVenda.get(i)).getNome(),
                ((ItensVenda) itensVenda.get(i)).getVlUnitario(),
                ((ItensVenda) itensVenda.get(i)).getQuant(),
                ((ItensVenda) itensVenda.get(i)).getVlTotal()
            });
        }
    }//GEN-LAST:event_tblVendasMouseClicked

    private void btnExcluirFornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirFornecedorActionPerformed
        // Checando campos vazios
        if (tblFornecedores.getSelectedRow() > -1) {

            if (!txtNomeFornecedor.getText().isEmpty() && !txtCnpjFornecedor.getText().isEmpty()) {
                DefaultTableModel modeloF = (DefaultTableModel) this.tblFornecedores.getModel();
                // Pegando o id da linha selecionada
                int id = Integer.parseInt(tblFornecedores.getValueAt(tblFornecedores.getSelectedRow(), 0).toString());

                // Deletando do Banco de Dados
                em.getTransaction().begin();
                Fornecedores f = em.find(Fornecedores.class, id);
                em.remove(f);
                em.getTransaction().commit();
                // Dados Atualizados
                fornecedores();
            } else {
                JOptionPane.showMessageDialog(null, "Campos Vazios");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecione uma linha!");
        }
    }//GEN-LAST:event_btnExcluirFornecedorActionPerformed

    private void txtCnpjFornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCnpjFornecedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCnpjFornecedorActionPerformed

    private void btnAtualizarFornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarFornecedorActionPerformed
        if (!txtNomeFornecedor.getText().equals("") && !txtCnpjFornecedor.getText().equals("")) {
            DefaultTableModel modeloF = (DefaultTableModel) this.tblFornecedores.getModel();
            // Pegando o id da linha selecionada
            String id = tblFornecedores.getValueAt(tblFornecedores.getSelectedRow(), 0).toString();

            // Atualizando no Banco de Dados
            em.getTransaction().begin();
            Fornecedores f = em.find(Fornecedores.class, Integer.parseInt(id));
            f.setCnpj(txtCnpjFornecedor.getText());
            f.setNomeRazao(txtNomeFornecedor.getText());
            em.getTransaction().commit();

            // Dados Atualizados
            fornecedores();

        } else {
            JOptionPane.showMessageDialog(null, "Selecione uma linha");
        }
    }//GEN-LAST:event_btnAtualizarFornecedorActionPerformed

    private void btnAdicionarFornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarFornecedorActionPerformed
        // Checando campos vazios
        if (!txtNomeFornecedor.getText().isEmpty() && !txtCnpjFornecedor.getText().isEmpty()) {
            DefaultTableModel modeloF = (DefaultTableModel) this.tblFornecedores.getModel();

            String nomeFornecedor = txtNomeFornecedor.getText();
            String CnpjFornecedor = txtCnpjFornecedor.getText();
            // Instanciando Class e setando os campos para Inserçao
            Fornecedores f = new Fornecedores();
            f.setCnpj(CnpjFornecedor);
            f.setNomeRazao(nomeFornecedor);

            em.getTransaction().begin();
            em.persist(f);
            em.flush();
            em.getTransaction().commit();
            // Dados Atualizados
            fornecedores();
            // Setando os campos para null
            txtNomeFornecedor.setText(null);
            txtCnpjFornecedor.setText(null);
        }
    }//GEN-LAST:event_btnAdicionarFornecedorActionPerformed

    private void tblFornecedoresKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblFornecedoresKeyReleased
        // Setando os campos txt de acordo com a linha selecionada na tabela
        DefaultTableModel modeloF = (DefaultTableModel) this.tblFornecedores.getModel();
        int linha = tblFornecedores.getSelectedRow();

        this.txtCnpjFornecedor.setText(tblFornecedores.getValueAt(linha, 1).toString());
        this.txtNomeFornecedor.setText(tblFornecedores.getValueAt(linha, 2).toString());
    }//GEN-LAST:event_tblFornecedoresKeyReleased

    private void tblFornecedoresKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblFornecedoresKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblFornecedoresKeyPressed

    private void tblFornecedoresMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblFornecedoresMouseReleased
        // Setando os campos txt de acordo com a linha selecionada na tabela
        DefaultTableModel modeloF = (DefaultTableModel) this.tblFornecedores.getModel();
        int linha = tblFornecedores.getSelectedRow();

        this.txtCnpjFornecedor.setText(tblFornecedores.getValueAt(linha, 1).toString());
        this.txtNomeFornecedor.setText(tblFornecedores.getValueAt(linha, 2).toString());
    }//GEN-LAST:event_tblFornecedoresMouseReleased

    private void btnExcluirProduto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirProduto1ActionPerformed
        DefaultTableModel modeloP1 = (DefaultTableModel) this.tblProdutos1.getModel();
        DefaultTableModel modeloP2 = (DefaultTableModel) this.tblProdutos2.getModel();

        if (tblProdutos2.getSelectedRow() != -1) {

            em.getTransaction().begin();
            Produtos p = em.find(Produtos.class, tblProdutos2.getValueAt(tblProdutos2.getSelectedRow(), 0));
            // Removendo das tabelas Produtos e Estoque Saldo
            p.setAtivo(false);
            em.getTransaction().commit();

            // Setando os campos Text (Null)
            txtDescricao.setText(null);
            txtCpfFornecedor.setText(null);
            txtQuant.setText(null);
            txtCodBarra.setText(null);
            txtPreco.setText(null);
            txtData.setText(null);

            // Atualizado as tabelas após remover
            produtos1();
            produtos2();
        } else {
            JOptionPane.showMessageDialog(null, "Selecione uma linha!");
        }
    }//GEN-LAST:event_btnExcluirProduto1ActionPerformed

    private void btnAtualizarProduto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarProduto1ActionPerformed
        if (!txtCpfFornecedor.getText().isEmpty() && !txtCodBarra.getText().isEmpty() && !txtDescricao.getText().isEmpty() && !txtPreco.getText().isEmpty()) {
            DefaultTableModel modeloP1 = (DefaultTableModel) tblProdutos1.getModel();
            DefaultTableModel modeloP2 = (DefaultTableModel) tblProdutos2.getModel();
            // Pegando a data atual
            Date dt = new Date();

            // get id da Row selecionada
            int id = Integer.parseInt(tblProdutos2.getValueAt(tblProdutos2.getSelectedRow(), 0).toString());
            // Atualizando no Banco de Dados(Dados do Produtos)
            em.getTransaction().begin();
            Produtos p = em.find(Produtos.class, id);
            p.setCpfCgcFornecedor(txtCpfFornecedor.getText());
            p.setDescricaoCompleta(txtDescricao.getText());
            p.setPrecoVenda(Double.parseDouble(txtPreco.getText()));
            p.setDataUltAlt(formatar.format(dt));
            // Setando a quantidade na tabela estoque
            EstoqueSaldo e = em.find(EstoqueSaldo.class, id);
            em.getTransaction().commit();

            // Dados Atualizados
            produtos1();
            produtos2();

            // Setando os campos Text (Null)
            txtDescricao.setText(null);
            txtCpfFornecedor.setText(null);
            txtQuant.setText(null);
            txtCodBarra.setText(null);
            txtPreco.setText(null);
            txtData.setText(null);
        } else {
            JOptionPane.showMessageDialog(null, "Selecione uma linha");
        }
    }//GEN-LAST:event_btnAtualizarProduto1ActionPerformed

    private void btnAdd1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdd1ActionPerformed
        // Checando campos vazios
        DefaultTableModel modeloP1 = (DefaultTableModel) this.tblProdutos1.getModel();
        DefaultTableModel modeloP2 = (DefaultTableModel) this.tblProdutos2.getModel();

        Date dt = new Date();
        em.getTransaction().begin();

        int linha = tblProdutos2.getSelectedRow();

        if (!txtCpfFornecedor.getText().equals(tblProdutos2.getValueAt(linha, 1)) || !txtCodBarra.getText().equals(tblProdutos2.getValueAt(linha, 2)) || !txtDescricao.getText().equals(tblProdutos2.getValueAt(linha, 3)) || !txtPreco.getText().equals(tblProdutos2.getValueAt(linha, 4))) {

            if (!txtCodBarra.getText().isEmpty() && !txtDescricao.getText().isEmpty()) {
                Produtos p = new Produtos();

                p.setCpfCgcFornecedor(txtCpfFornecedor.getText());
                p.setCodEan1(txtCodBarra.getText());
                p.setDescricaoCompleta(txtDescricao.getText());
                p.setPrecoVenda(Double.parseDouble(txtPreco.getText()));
                p.setDataUltAlt(formatar.format(dt));
                p.setAtivo(false);
                em.persist(p);
                em.getTransaction().commit();

                // Setando os campos Text (Null)
                txtDescricao.setText(null);
                txtCpfFornecedor.setText(null);
                txtQuant.setText(null);
                txtCodBarra.setText(null);
                txtPreco.setText(null);
                txtData.setText(null);

                //Atualizando as tabelas
                produtos1();
                produtos2();
            } else {
                JOptionPane.showMessageDialog(null, "Campo(s) Vazios");
            }

        } else if (tblProdutos2.getValueAt(linha, 7).equals("false") && txtCpfFornecedor.getText().equals(tblProdutos2.getValueAt(linha, 1)) && txtCodBarra.getText().equals(tblProdutos2.getValueAt(linha, 2)) && txtDescricao.getText().equals(tblProdutos2.getValueAt(linha, 3)) && txtPreco.getText().equals(tblProdutos2.getValueAt(linha, 4))) {

        }
    }//GEN-LAST:event_btnAdd1ActionPerformed

    private void txtDescricaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescricaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescricaoActionPerformed

    private void txtCpfFornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCpfFornecedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCpfFornecedorActionPerformed

    private void tblProdutos2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblProdutos2KeyReleased
        // Setando os campos txt de acordo com a linha selecionada na tabela
        int linha = tblProdutos2.getSelectedRow();

        txtCpfFornecedor.setText(tblProdutos2.getValueAt(linha, 1).toString());
        txtCodBarra.setText(tblProdutos2.getValueAt(linha, 2).toString());
        txtDescricao.setText(tblProdutos2.getValueAt(linha, 3).toString());
        txtPreco.setText(tblProdutos2.getValueAt(linha, 4).toString());
        txtQuant.setText(tblProdutos2.getValueAt(linha, 5).toString());
        txtData.setText(tblProdutos2.getValueAt(linha, 6).toString());
    }//GEN-LAST:event_tblProdutos2KeyReleased

    private void tblProdutos2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblProdutos2KeyPressed

    }//GEN-LAST:event_tblProdutos2KeyPressed

    private void tblProdutos2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProdutos2MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tblProdutos2MouseReleased

    private void tblProdutos2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProdutos2MousePressed
        if (tblProdutos2.getValueAt(tblProdutos2.getSelectedRow(), 7).equals("false")) {
            btnAtivar.setEnabled(true);
            int linha = tblProdutos2.getSelectedRow();
            txtCpfFornecedor.setText(tblProdutos2.getValueAt(linha, 1).toString());
            txtCodBarra.setText(tblProdutos2.getValueAt(linha, 2).toString());
            txtDescricao.setText(tblProdutos2.getValueAt(linha, 3).toString());
            txtPreco.setText(tblProdutos2.getValueAt(linha, 4).toString());
            txtQuant.setText(tblProdutos2.getValueAt(linha, 5).toString());
            txtData.setText(tblProdutos2.getValueAt(linha, 6).toString());
            btnAdd1.setEnabled(false);
            btnExcluirProduto1.setEnabled(false);
            btnAtualizarProduto1.setEnabled(false);
            System.out.println("Aqui");
            txtCodBarra.setEditable(false);
            add(txtCodBarra);
        } else if (tblProdutos2.getValueAt(tblProdutos2.getSelectedRow(), 7).equals("true")) {
            btnAtivar.setEnabled(false);
            int linha = tblProdutos2.getSelectedRow();
            txtCpfFornecedor.setText(tblProdutos2.getValueAt(linha, 1).toString());
            txtCodBarra.setText(tblProdutos2.getValueAt(linha, 2).toString());
            txtDescricao.setText(tblProdutos2.getValueAt(linha, 3).toString());
            txtPreco.setText(tblProdutos2.getValueAt(linha, 4).toString());
            txtQuant.setText(tblProdutos2.getValueAt(linha, 5).toString());
            txtData.setText(tblProdutos2.getValueAt(linha, 6).toString());
            System.out.println("Entrou true");
            btnAdd1.setEnabled(true);
            btnExcluirProduto1.setEnabled(true);
            btnAtualizarProduto1.setEnabled(true);
            txtCodBarra.setEnabled(false);
            add(txtCodBarra);
        }
    }//GEN-LAST:event_tblProdutos2MousePressed

    private void btnAtualizarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarClienteActionPerformed
        if (!txtNomeCliente.getText().isEmpty() && !txtCpfCliente.getText().isEmpty()) {
            DefaultTableModel modeloC = (DefaultTableModel) tblClientesExcluidos.getModel();
            // Pegando o id da linha selecionada
            String id = tblClientesExcluidos.getValueAt(tblClientesExcluidos.getSelectedRow(), 0).toString();
            // Atualizando no Banco de Dados
            em.getTransaction().begin();
            Clientes c = em.find(Clientes.class, Integer.parseInt(id));
            c.setCpf(txtCpfCliente.getText());
            c.setNome(txtNomeCliente.getText());
            em.getTransaction().commit();
            // Dados Atualizados
            modeloC.setRowCount(0);
            clientes();
            // Setando os campos Text (Null)
            txtCpfFornecedor.setText(null);
            txtNomeFornecedor.setText(null);
        } else {
            JOptionPane.showMessageDialog(null, "Selecione uma linha");
        }
    }//GEN-LAST:event_btnAtualizarClienteActionPerformed

    private void txtCpfClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCpfClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCpfClienteActionPerformed

    private void btnAdicionarCliente1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarCliente1ActionPerformed
        // Checando campos vazios
        if (!txtNomeCliente.getText().isEmpty() && !txtCpfCliente.getText().isEmpty()) {
            // Setando os campos
            Clientes c = new Clientes();
            c.setCpf(txtCpfCliente.getText());
            c.setNome(txtNomeCliente.getText());
            // Inserindo no Banco de Dados
            em.getTransaction().begin();
            em.persist(c);
            em.flush();
            em.getTransaction().commit();
            // Dados Atualizados
            clientes();
            // Setando os campos para null
            txtNomeCliente.setText(null);
            txtCpfCliente.setText(null);
        } else {
            JOptionPane.showMessageDialog(null, "Campos Vazios!");
        }
    }//GEN-LAST:event_btnAdicionarCliente1ActionPerformed

    private void btnExcluirCliente1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirCliente1ActionPerformed
        if (tblClientes1.getSelectedRow() > -1) {
            DefaultTableModel modeloC1 = (DefaultTableModel) this.tblClientesExcluidos.getModel();
            DefaultTableModel modeloC2 = (DefaultTableModel) this.tblClientes1.getModel();
            // Pegando o id da linha selecionada
            int id = Integer.parseInt(tblClientes1.getValueAt(tblClientes1.getSelectedRow(), 0).toString());

            em.getTransaction().begin();
            Clientes c = em.find(Clientes.class, id);
            c.setAtivo(false);
            em.getTransaction().commit();

            // Atualizando Dados das Tabelas
            modeloC1.setRowCount(0);
            modeloC2.setRowCount(0);
            clientes();

        } else {
            JOptionPane.showMessageDialog(null, "Selecione uma linha");
        }
    }//GEN-LAST:event_btnExcluirCliente1ActionPerformed

    private void tblClientesExcluidosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblClientesExcluidosKeyReleased
        txtNomeCliente.setText(tblClientesExcluidos.getValueAt(tblClientesExcluidos.getSelectedRow(), 1).toString());
        txtCpfCliente.setText(tblClientesExcluidos.getValueAt(tblClientesExcluidos.getSelectedRow(), 2).toString());
    }//GEN-LAST:event_tblClientesExcluidosKeyReleased

    private void tblClientesExcluidosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblClientesExcluidosKeyPressed
        txtNomeCliente.setText(tblClientesExcluidos.getValueAt(tblClientesExcluidos.getSelectedRow(), 1).toString());
        txtCpfCliente.setText(tblClientesExcluidos.getValueAt(tblClientesExcluidos.getSelectedRow(), 2).toString());
    }//GEN-LAST:event_tblClientesExcluidosKeyPressed

    private void tblClientesExcluidosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesExcluidosMouseClicked
        btnAtualizarCliente.setEnabled(false);
        btnExcluirCliente1.setEnabled(false);
        btnAdicionarCliente1.setEnabled(false);
        btnAtivarCliente.setEnabled(true);

        txtNomeCliente.setEditable(false);
        txtCpfCliente.setEditable(false);

        txtNomeCliente.setText("");
        txtCpfCliente.setText("");
    }//GEN-LAST:event_tblClientesExcluidosMouseClicked

    private void txtHoraSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHoraSistemaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHoraSistemaActionPerformed

    private void p1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_p1MouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_p1MouseClicked

    private void p1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_p1MousePressed
        // TODO add your handling code here:
        setColor(p1);
        y1.setBackground(new Color(255, 255, 255));
        resetColor(new JPanel[]{p2, p3, p4, p5, p6, p8}, new JPanel[]{y2, y3, y4, y5, y6, y7 });
        painel.setSelectedIndex(1);
    }//GEN-LAST:event_p1MousePressed

    private void p7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_p7MouseClicked
        // TODO add your handling code here:
        setColor(p7);
        y7.setBackground(new Color(255, 255, 255));
        resetColor(new JPanel[]{p1, p2, p3, p4, p5, p8}, new JPanel[]{y1, y2, y3, y4, y5, y6});
        dispose();
    }//GEN-LAST:event_p7MouseClicked

    private void p2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_p2MouseClicked
        // TODO add your handling code here:
        setColor(p2);
        y2.setBackground(new Color(255, 255, 255));
        resetColor(new JPanel[]{p1, p3, p4, p5, p6, p8}, new JPanel[]{y1, y3, y4, y5, y6, y7});
        painel.setSelectedIndex(2);
    }//GEN-LAST:event_p2MouseClicked

    private void p3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_p3MouseClicked
        // TODO add your handling code here:
        setColor(p3);
        y3.setBackground(new Color(255, 255, 255));
        resetColor(new JPanel[]{p1, p2, p4, p5, p6, p8}, new JPanel[]{y1, y2, y4, y5, y6, y7});
        painel.setSelectedIndex(3);
    }//GEN-LAST:event_p3MouseClicked

    private void p4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_p4MouseClicked
        // TODO add your handling code here:
        setColor(p4);
        y4.setBackground(new Color(255, 255, 255));
        resetColor(new JPanel[]{p1, p2, p3, p5, p6, p8}, new JPanel[]{y1, y2, y3, y5, y6, y7});
        painel.setSelectedIndex(0);
    }//GEN-LAST:event_p4MouseClicked

    private void p5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_p5MouseClicked
        // TODO add your handling code here:
        setColor(p5);
        y5.setBackground(new Color(255, 255, 255));
        resetColor(new JPanel[]{p1, p2, p3, p4, p6, p8}, new JPanel[]{y1, y2, y3, y4, y6, y7});
        painel.setSelectedIndex(4);
    }//GEN-LAST:event_p5MouseClicked

    private void p2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_p2MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_p2MousePressed

    private void tblClientes1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientes1MouseClicked
        // TODO add your handling code here:
        btnAdicionarCliente1.setEnabled(true);
        btnAtualizarCliente.setEnabled(true);
        btnExcluirCliente1.setEnabled(true);
        btnAtivarCliente.setEnabled(false);

        txtNomeCliente.setText(tblClientes1.getValueAt(tblClientes1.getSelectedRow(), 1).toString());
        txtCpfCliente.setText((String) tblClientes1.getValueAt(tblClientes1.getSelectedRow(), 2));
    }//GEN-LAST:event_tblClientes1MouseClicked

    private void tblClientes1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblClientes1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblClientes1KeyPressed

    private void tblClientes1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblClientes1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tblClientes1KeyReleased

    private void btnAtivarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtivarClienteActionPerformed
        // TODO add your handling code here:
        DefaultTableModel modeloC = (DefaultTableModel) this.tblClientes1.getModel();
        DefaultTableModel modeloCE = (DefaultTableModel) this.tblClientesExcluidos.getModel();
        if(tblClientesExcluidos.getRowCount() > -1){
            txtNomeCliente.setText("");
            txtCpfCliente.setText("");

            int id = Integer.parseInt(tblClientesExcluidos.getValueAt(tblClientesExcluidos.getSelectedRow(), 0).toString());
            Clientes c = em.find(Clientes.class, id);

            em.getTransaction().begin();
            c.setAtivo(true);
            em.getTransaction().commit();

            modeloC.setRowCount(0);
            modeloCE.setRowCount(0);
            clientes();
            
            if(tblClientesExcluidos.getRowCount() == 0){
                btnAtivarCliente.setEnabled(false);
            }
        }
        


    }//GEN-LAST:event_btnAtivarClienteActionPerformed

    private void tblProdutos2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProdutos2MouseClicked
        int linha = tblProdutos2.getSelectedRow();
        String x = tblProdutos2.getValueAt(tblProdutos2.getSelectedRow(), 7).toString();

        if (x.equalsIgnoreCase("false")) {
            btnAtivar.setEnabled(true);
            txtCpfFornecedor.setText(tblProdutos2.getValueAt(linha, 1).toString());
            txtCodBarra.setText(tblProdutos2.getValueAt(linha, 2).toString());
            txtDescricao.setText(tblProdutos2.getValueAt(linha, 3).toString());
            txtPreco.setText(tblProdutos2.getValueAt(linha, 4).toString());
            txtQuant.setText(tblProdutos2.getValueAt(linha, 5).toString());
            txtData.setText(tblProdutos2.getValueAt(linha, 6).toString());
            btnAdd1.setEnabled(false);
            btnExcluirProduto1.setEnabled(false);
            btnAtualizarProduto1.setEnabled(false);
            txtCodBarra.setEditable(false);
        }
        if (x.equalsIgnoreCase("true")) {
            btnAtivar.setEnabled(false);
            txtCpfFornecedor.setText(tblProdutos2.getValueAt(linha, 1).toString());
            txtCodBarra.setText(tblProdutos2.getValueAt(linha, 2).toString());
            txtDescricao.setText(tblProdutos2.getValueAt(linha, 3).toString());
            txtPreco.setText(tblProdutos2.getValueAt(linha, 4).toString());
            txtQuant.setText(tblProdutos2.getValueAt(linha, 5).toString());
            txtData.setText(tblProdutos2.getValueAt(linha, 6).toString());
            btnAdd1.setEnabled(true);
            btnExcluirProduto1.setEnabled(true);
            btnAtualizarProduto1.setEnabled(true);
            txtCodBarra.setEnabled(false);
        }
    }//GEN-LAST:event_tblProdutos2MouseClicked

    private void txtDescricaoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescricaoKeyReleased
        // TODO add your handling code here:
        
        String x = tblProdutos2.getValueAt(tblProdutos2.getSelectedRow(),3).toString().trim();

        if (txtDescricao.getText().trim().equalsIgnoreCase(x)){
            txtCodBarra.setEnabled(false);
            btnAdd1.setEnabled(false);
            
        } else if(!txtDescricao.getText().trim().equalsIgnoreCase(x)){
            txtCodBarra.setEnabled(true);
        }
        

        
    }//GEN-LAST:event_txtDescricaoKeyReleased

    private void txtDescricaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescricaoKeyPressed
        // TODO add your handling code here:
//        txtCodBarra.setEnabled(true);
    }//GEN-LAST:event_txtDescricaoKeyPressed

    private void btnAtivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtivarActionPerformed
        // TODO add your handling code here:]

        String ativo = tblProdutos2.getValueAt(tblProdutos2.getSelectedRow(),7).toString();
        
        if (ativo.equalsIgnoreCase("false")) {
            int id = Integer.parseInt(tblProdutos2.getValueAt(tblProdutos2.getSelectedRow(), 0).toString());
            // Atualizando no Banco de Dados(Dados do Produtos)
            em.getTransaction().begin();
            Produtos p = em.find(Produtos.class, id);
            p.setAtivo(true);
            em.getTransaction().commit();
            produtos2();
        } else if (!ativo.equalsIgnoreCase("false")) {
            int id = Integer.parseInt(tblProdutos2.getValueAt(tblProdutos2.getSelectedRow(), 0).toString());

            // Atualizando no Banco de Dados(Dados do Produtos)
            em.getTransaction().begin();
            Produtos p = em.find(Produtos.class, id);
            p.setAtivo(false);
            em.getTransaction().commit();
            produtos2();
        }
     

    }//GEN-LAST:event_btnAtivarActionPerformed

    private void tblEstoqueMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEstoqueMouseClicked
        // TODO add your handling code here:

        DefaultTableModel modeloE = (DefaultTableModel) tblEstoque.getModel();

        int id = Integer.parseInt(tblEstoque.getValueAt(tblEstoque.getSelectedRow(), 0).toString());

        Produtos p = em.find(Produtos.class, id);
        txtPrecoProdutoEstoque.setText("" + p.getPrecoVenda());
        txtUltimaAlteracaoEstoque.setText(p.getDataUltAlt());
        txtNomeProdutoEstoque.setText(p.getDescricaoCompleta());
        txtQuantProdutoEstoque.setText(tblEstoque.getValueAt(tblEstoque.getSelectedRow(), 2).toString());

    }//GEN-LAST:event_tblEstoqueMouseClicked

    private void tblEstoqueMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEstoqueMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblEstoqueMousePressed

    private void tblEstoqueMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEstoqueMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tblEstoqueMouseReleased

    private void tblEstoqueKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblEstoqueKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblEstoqueKeyPressed

    private void tblEstoqueKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblEstoqueKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tblEstoqueKeyReleased

    private void btnAtualizarFornecedor1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarFornecedor1ActionPerformed
        // TODO add your handling code here:

        int id = Integer.parseInt(tblEstoque.getValueAt(tblEstoque.getSelectedRow(), 0).toString());

        try {
            // Tentando converter o valor para int, ser der error nao é um inteiro 
            int x = Integer.parseInt(txtQuantProdutoEstoque.getText());
            EstoqueSaldo e = em.find(EstoqueSaldo.class, id);
            em.getTransaction().begin();
            e.setSaldoEstoque(x);
            em.getTransaction().commit();
            estoque();
            txtQuantProdutoEstoque.setText("");
            txtPrecoProdutoEstoque.setText("");
            txtUltimaAlteracaoEstoque.setText("");
            txtNomeProdutoEstoque.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Digite somente Números!");
        }

    }//GEN-LAST:event_btnAtualizarFornecedor1ActionPerformed

    private void p8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_p8MouseClicked
        // TODO add your handling code here:
        setColor(p8);
        y6.setBackground(new Color(255, 255, 255));
        resetColor(new JPanel[]{p1, p2, p3, p4, p6, p5, p7}, new JPanel[]{y1, y2, y3, y4,y5 ,y7});
        painel.setSelectedIndex(5);
        
    }//GEN-LAST:event_p8MouseClicked

    private void txtQuantidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQuantidadeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQuantidadeActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        DefaultTableModel modeloI = (DefaultTableModel) this.tblItens.getModel();
        DefaultTableModel modeloP1 = (DefaultTableModel) this.tblProdutos1.getModel();
        if (tblProdutos1.getSelectedRow() > -1) {

            try {
                int quant = Integer.parseInt(txtQuantidade.getText());
                // Checando se a tabela itens está vazia
                if (tblProdutos1.getSelectedRow() != -1) {
                    if (quant > 0) { // Checando se quantidade é maior que zero
                        df.applyPattern("#,##0.00");

                        // Dados do produto
                        double preco = Double.parseDouble(tblProdutos1.getValueAt(tblProdutos1.getSelectedRow(), 3).toString());

                        double total;
                        total = quant * preco;

                        modeloI.addRow(new Object[]{tblProdutos1.getValueAt(tblProdutos1.getSelectedRow(), 0), tblProdutos1.getValueAt(tblProdutos1.getSelectedRow(), 1).toString(),
                            tblProdutos1.getValueAt(tblProdutos1.getSelectedRow(), 2).toString(),
                            quant, preco, df.format(total)});

                    this.txtPesquisaProduto.setText(null);
                    this.txtQuantidade.setText("");
                } else if (quant < 0) {
                    JOptionPane.showMessageDialog(null, "Quantidade negativa não é válida!");
                } else {
                    JOptionPane.showMessageDialog(null, "Quantidade '0' não válida!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selecione uma Linha!");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "A quantidade não pode ser simbolos ou letras!");
        }

        double valorTotal = 0.0;
        double preco;
        for (int i = 0; i < tblItens.getRowCount(); i++) {
            tblItens.setRowSelectionInterval(i, i);
            double v = Double.parseDouble(tblItens.getValueAt(tblItens.getSelectedRow(), 4).toString());
            int q = Integer.parseInt(tblItens.getValueAt(tblItens.getSelectedRow(), 3).toString());
            valorTotal += q * v;
        }

        tblItens.setRowSelectionAllowed(true);

        txtTotalCompra.setText("" + df.format(valorTotal));
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um produto!");
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void txtPesquisaProdutoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaProdutoKeyReleased
        // TODO add your handling code here:

        DefaultTableModel modeloP1 = (DefaultTableModel) this.tblProdutos1.getModel();
        modeloP1.setRowCount(0);

        String produto = txtPesquisaProduto.getText();

        List p = em.createNativeQuery("SELECT * FROM produtos WHERE descricao_completa LIKE '" + produto + "%'", Produtos.class).getResultList();
        for (int i = 0; i < p.size(); i++) {
            modeloP1.addRow(new Object[]{
                ((Produtos) p.get(i)).getCodInterno(),
                ((Produtos) p.get(i)).getCodEan1(),
                ((Produtos) p.get(i)).getDescricaoCompleta(),
                ((Produtos) p.get(i)).getPrecoVenda()
            });
        }
    }//GEN-LAST:event_txtPesquisaProdutoKeyReleased

    private void txtPesquisaProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesquisaProdutoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPesquisaProdutoActionPerformed

    private void txtPesquisaProdutoHierarchyChanged(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_txtPesquisaProdutoHierarchyChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPesquisaProdutoHierarchyChanged

    private void tblItensKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblItensKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblItensKeyPressed

    private void btnEditarClienteVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarClienteVendaActionPerformed
        txtCpf.setText("");
        txtNome.setText("");
        txtCpf.setEnabled(true);
        txtNome.setEnabled(true);
        btnFinalizarCompra.setEnabled(false);
    }//GEN-LAST:event_btnEditarClienteVendaActionPerformed

    private void btnEditarClienteVendaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarClienteVendaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditarClienteVendaMouseClicked

    private void txtNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeKeyPressed

    private void txtNomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNomeMouseClicked
        DefaultTableModel modeloC = (DefaultTableModel) tblClientes1.getModel();

        int x = 0;
        boolean achou = false;

        if (txtCpf.getText().equals("   -   -   -  ")) {
            JOptionPane.showMessageDialog(null, "Informe primeiro seu CPF! ");
        } else {
            while (x <= modeloC.getRowCount() - 1) {
                if (txtCpf.getText().equals(tblClientes1.getValueAt(x, 2))) {
                    txtNome.setText(tblClientes1.getValueAt(x, 1).toString());
                    txtNome.setEnabled(false);
                    txtCpf.setEnabled(false);
                    btnFinalizarCompra.setEnabled(true);
                    achou = true;
                    break;
                } else {
                    x++;
                }
            }

            if (achou == false) {
                JOptionPane.showMessageDialog(null, "Cliente nao Cadastrado");
                txtCpf.setText(null);
                txtNome.setText("");
            }
        }
    }//GEN-LAST:event_txtNomeMouseClicked

    private void txtNomeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomeFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeFocusLost

    private void txtNomeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomeFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeFocusGained

    private void txtCpfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCpfActionPerformed
        // TODO add your handling code here:
        DefaultTableModel modeloC = (DefaultTableModel) tblClientesExcluidos.getModel();

        int x = 0;
        boolean achou = false;

        if (txtCpf.getText().equals("   -   -   -  ")) {
            JOptionPane.showMessageDialog(null, "Informe primeiro seu CPF! ");
        } else {
            while (x <= modeloC.getRowCount() - 1) {
                if (txtCpf.getText().equals(tblClientesExcluidos.getValueAt(x, 2))) {
                    txtNome.setText(tblClientesExcluidos.getValueAt(x, 1).toString());
                    txtNome.setEnabled(false);
                    txtCpf.setEnabled(false);
                    btnFinalizarCompra.setEnabled(true);
                    achou = true;
                    break;
                } else {
                    x++;
                }
            }

            if (achou == false) {
                JOptionPane.showMessageDialog(null, "Cliente nao Cadastrado");
                txtCpf.setText(null);
                txtNome.setText("");
            }
        }
    }//GEN-LAST:event_txtCpfActionPerformed

    private void txtCpfMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCpfMouseClicked
        // TODO add your handling code here:
        txtCpf.setCaretPosition(0);
    }//GEN-LAST:event_txtCpfMouseClicked

    private void tblProdutos1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblProdutos1KeyReleased
        // TODO add your handling code here:
        String desc = tblProdutos1.getValueAt(tblProdutos1.getSelectedRow(), 2).toString();

        txtPesquisaProduto.setText(desc);
        txtQuantidade.setText("1");
    }//GEN-LAST:event_tblProdutos1KeyReleased

    private void tblProdutos1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblProdutos1KeyPressed
        String desc = tblProdutos1.getValueAt(tblProdutos1.getSelectedRow(), 2).toString();
        txtPesquisaProduto.setText(desc);
        txtQuantidade.setText("1");
    }//GEN-LAST:event_tblProdutos1KeyPressed

    private void tblProdutos1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProdutos1MouseClicked
        txtPesquisaProduto.setText(tblProdutos1.getValueAt(tblProdutos1.getSelectedRow(), 2).toString());
        txtQuantidade.setText("1");
        txtQuantidade.requestFocus();
    }//GEN-LAST:event_tblProdutos1MouseClicked

    private void tblProdutos1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tblProdutos1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_tblProdutos1FocusLost

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        DefaultTableModel modeloI = (DefaultTableModel) this.tblItens.getModel();

        if (tblItens.getSelectedRow() != -1) {
            modeloI.removeRow(tblItens.getSelectedRow());
            double valorTotal = 0.0;
            double preco;
            for (int i = 0; i < tblItens.getRowCount(); i++) {
                tblItens.setRowSelectionInterval(i, i);
                double v = Double.parseDouble(tblItens.getValueAt(tblItens.getSelectedRow(), 4).toString());
                int q = Integer.parseInt(tblItens.getValueAt(tblItens.getSelectedRow(), 3).toString());
                valorTotal += q * v;
            }
            txtTotalCompra.setText("" + df.format(valorTotal));
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um produto para Remover!");
        }
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnFinalizarCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinalizarCompraActionPerformed
        DefaultTableModel modeloI = (DefaultTableModel) this.tblItens.getModel();
        DefaultTableModel modeloP = (DefaultTableModel) this.tblProdutos2.getModel();
        DefaultTableModel modeloP1 = (DefaultTableModel) this.tblProdutos1.getModel();
        DefaultTableModel modeloC1 = (DefaultTableModel) this.tblClientes1.getModel();

        df.applyPattern("#,##0.00");
        boolean cadastro = false;
        // Verificando se o cliente está cadastrado
        for (int i = 0; i < tblClientes1.getRowCount(); i++) {
            String cpf = tblClientes1.getValueAt(i, 2).toString();
            if (txtCpf.getText().equals(cpf)) {
                cadastro = true;
            }
        }
        if (cadastro == true) {
            Date dtVenda = new Date();
            if (!txtNome.getText().isEmpty() && !txtCpf.getText().isEmpty()) {
                if (tblItens.getRowCount() != 0) {
                    // Cabecalho vendas
                    em.getTransaction().begin();
                    CabecalhoVendas c = new CabecalhoVendas();
                    c.setDatavenda(formatar.format(dtVenda));
                    em.persist(c);
                    em.flush();

                    // Depois de Inserido, pega 'NumPedido' do Objeto adicionado
                    String numPedido = c.getNumPedido().toString();

                    // (Quant total de Itens) (Valor Total dos Produtos)
                    int quantTotal = 0;
                    double valorTotal = 0;

                    // Inserido itens da venda na tabela (Itens_Venda)
                    for (int i = 0; i < tblItens.getRowCount(); i++) {
                        // Setando a linha Selecionada para (i)
                        tblItens.setRowSelectionInterval(i, i);
                        int linha = tblItens.getSelectedRow();

                        // Instanciando um Objeto para receber os parametros
                        ItensVenda itens = new ItensVenda();
                        itens.setNumPedido(Integer.parseInt(numPedido));
                        itens.setIdProd(Integer.parseInt(tblItens.getValueAt(linha, 0).toString()));
                        itens.setCodProduto(tblItens.getValueAt(linha, 1).toString());
                        itens.setNome(tblItens.getValueAt(tblItens.getSelectedRow(), 2).toString());
                        itens.setQuant(Integer.parseInt(tblItens.getValueAt(linha, 3).toString()));
                        itens.setVlUnitario(Double.parseDouble(tblItens.getValueAt(linha, 4).toString()));
                        // Calculando o valor total (Preço * Quantidade)
                        int quant = Integer.parseInt(tblItens.getValueAt(linha, 3).toString());
                        double preco = Double.parseDouble(tblItens.getValueAt(linha, 4).toString());
                        double totalItem = preco * quant;

                        // Calculando o total de itens e o valor da compra
                        quantTotal += quant;
                        valorTotal += totalItem;
                        
                        itens.setVlTotal(totalItem);
                        em.persist(itens);
                        // Atualizando a quantidade no estoque
                        int id = Integer.parseInt(tblItens.getValueAt(tblItens.getSelectedRow(), 0).toString());
                        EstoqueSaldo e = em.find(EstoqueSaldo.class, id);
                        int quantAtual = Integer.parseInt(e.getSaldoEstoque().toString());
                        e.setSaldoEstoque(quantAtual - quant);
                    }

                    // Inserindo a compra na tabela Vendas
                    Vendas v = new Vendas();
                    v.setNumPedido(Integer.parseInt(numPedido));
                    v.setCpfCliente(txtCpf.getText());
                    v.setNome(txtNome.getText());
                    v.setQuant(quantTotal);
                    v.setDtVenda(formatar.format(dtVenda));
                    v.setValor(valorTotal);
                    em.persist(v);
                    // Finalizando a transaçao com o banco
                    em.getTransaction().commit();

                    // Set campos for null
                    txtCpf.setText(null);
                    txtCpf.setEnabled(true);
                    txtNome.setText(null);
                    txtNome.setEnabled(true);
                    txtTotalCompra.setText("0");

                    modeloI.setRowCount(0);
                    modeloP.setRowCount(0);
                    produtos2();
                } else {
                    JOptionPane.showMessageDialog(null, "Nenhum produto escolhido!\nImpossível finalizar Compra");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Os campos 'Cliente' estão Vazios!\nImpossível finalizar Compra");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Cliente não Cadastrado!");
        }
    }//GEN-LAST:event_btnFinalizarCompraActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ProgMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProgMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProgMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProgMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ProgMain().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAdd1;
    private javax.swing.JButton btnAdicionarCliente1;
    private javax.swing.JButton btnAdicionarFornecedor;
    private javax.swing.JButton btnAtivar;
    private javax.swing.JButton btnAtivarCliente;
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnAtualizarCliente;
    private javax.swing.JButton btnAtualizarFornecedor;
    private javax.swing.JButton btnAtualizarFornecedor1;
    private javax.swing.JButton btnAtualizarProduto1;
    private javax.swing.JButton btnEditarClienteVenda;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnExcluirCliente1;
    private javax.swing.JButton btnExcluirFornecedor;
    private javax.swing.JButton btnExcluirProduto1;
    private javax.swing.JButton btnFinalizarCompra;
    private javax.swing.JPanel clientes;
    private javax.swing.JPanel fornecedores;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JPanel novacompra;
    private javax.swing.JPanel p1;
    private javax.swing.JPanel p2;
    private javax.swing.JPanel p3;
    private javax.swing.JPanel p4;
    private javax.swing.JPanel p5;
    private javax.swing.JPanel p6;
    private javax.swing.JPanel p7;
    private javax.swing.JPanel p8;
    private javax.swing.JTabbedPane painel;
    private javax.swing.JPanel produtos;
    private javax.swing.JTable tblClientes1;
    private javax.swing.JTable tblClientesExcluidos;
    private javax.swing.JTable tblEstoque;
    private javax.swing.JTable tblFornecedores;
    private javax.swing.JTable tblItens;
    private javax.swing.JTable tblProdutoDetalhado;
    private javax.swing.JTable tblProdutos1;
    private javax.swing.JTable tblProdutos2;
    private javax.swing.JTable tblProdutosCompras;
    private javax.swing.JTable tblVendas;
    private javax.swing.JTextField txtCnpjFornecedor;
    private javax.swing.JTextField txtCodBarra;
    private javax.swing.JFormattedTextField txtCpf;
    private javax.swing.JFormattedTextField txtCpfCliente;
    private javax.swing.JTextField txtCpfFornecedor;
    private javax.swing.JTextField txtData;
    private javax.swing.JTextField txtDescricao;
    private javax.swing.JTextField txtHoraSistema;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtNomeCliente;
    private javax.swing.JTextField txtNomeFornecedor;
    private javax.swing.JTextField txtNomeProdutoEstoque;
    private javax.swing.JTextField txtPesquisaProduto;
    private javax.swing.JTextField txtPreco;
    private javax.swing.JTextField txtPrecoProdutoEstoque;
    private javax.swing.JTextField txtQuant;
    private javax.swing.JTextField txtQuantProdutoEstoque;
    private javax.swing.JTextField txtQuantidade;
    private javax.swing.JTextField txtTotalCompra;
    private javax.swing.JTextField txtUltimaAlteracaoEstoque;
    private javax.swing.JPanel vendas;
    private javax.swing.JPanel y1;
    private javax.swing.JPanel y2;
    private javax.swing.JPanel y3;
    private javax.swing.JPanel y4;
    private javax.swing.JPanel y5;
    private javax.swing.JPanel y6;
    private javax.swing.JPanel y7;
    // End of variables declaration//GEN-END:variables
    static Date data = new Date();
    static SimpleDateFormat formatar = new SimpleDateFormat("yyyy-MM-dd");
    static DecimalFormat df = new DecimalFormat();
}
