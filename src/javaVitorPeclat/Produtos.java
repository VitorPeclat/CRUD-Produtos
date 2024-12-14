package javaVitorPeclat;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import static java.lang.System.console;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class Produtos extends javax.swing.JFrame {
    FileInputStream fis;
    int tam;
    Connection conexao;
    PreparedStatement pst; 
    ResultSet rs; 
    
    String codigo;

    public Produtos() {
        initComponents();
        pesquisa.setText("");
        listar();
        pesquisa.setText("Pesquisar...");
    }

    public void limpar() {
        cod.setText("");
        nome.setText("");
        data.setDate(null);
        status.setSelectedIndex(0);
        qtdEstoque.setText("");
        desc.setText("");
        estoqueMaximo.setText("");
        estoqueMinimo.setText("");
        prcCompra.setText("");
        prcVenda.setText("");
        fatorLucro.setText("");
        ncm.setText("");
        codigoBarras.setText("");
        labelFoto.setIcon(null);
        listar();
    }

    public void cadastrar() {
        conexao = Conexao.obterConexao();
        try {
            String sql = "insert into produto (cod, status, nome, descricao, qtd_estoque, estoque_minimo, estoque_maximo, preco_compra, preco_venda, bar_code, ncm, fator, data_cadastro, imagem) "
                    + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pst = conexao.prepareStatement(sql);

            pst.setString(1, cod.getText()); 
            pst.setString(3, nome.getText()); 
            pst.setString(4, desc.getText());  
            pst.setInt(5, Integer.parseInt(qtdEstoque.getText())); 
            pst.setInt(6, Integer.parseInt(estoqueMinimo.getText())); 
            pst.setInt(7, Integer.parseInt(estoqueMaximo.getText())); 
            pst.setDouble(8, Double.parseDouble(prcCompra.getText()));  
            pst.setDouble(9, Double.parseDouble(prcVenda.getText()));  
            pst.setInt(10, Integer.parseInt(codigoBarras.getText()));
            pst.setString(11, ncm.getText()); 
            String fator = fatorLucro.getText();
            pst.setDouble(12, Double.parseDouble(fator.replace(" %", "")));
            pst.setDate(13, new java.sql.Date(data.getDate().getTime()));

            int flag = status.getSelectedIndex();
            char stat = 'I';
            if (flag == 1){
                stat = 'I';
            } else if (flag == 2){
                stat = 'A';
            }
            pst.setString(2,String.valueOf(stat));
            pst.setBlob(14, fis, tam);
            pst.execute();
            pst.close();

            JOptionPane.showMessageDialog(null, "Cadastrado com sucesso!");
            fis = null;
            limpar();
            pesquisa.setText("");
           
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar: " + e.getMessage());
        }
    }
    
    public void atualizar() {
        conexao = Conexao.obterConexao();
        try {
            if (fis == null) {
                String sql = "UPDATE produto SET cod = ?, status = ?, nome = ?, descricao = ?, qtd_estoque = ?, estoque_minimo = ?, estoque_maximo = ?, preco_compra = ?, preco_venda = ?, bar_code = ?, ncm = ?, fator = ?, data_cadastro = ? WHERE cod = ?";
                pst = conexao.prepareStatement(sql);

                pst.setString(1, cod.getText());  
                pst.setString(3, nome.getText()); 
                pst.setString(4, desc.getText());  
                pst.setInt(5, Integer.parseInt(qtdEstoque.getText()));
                pst.setInt(6, Integer.parseInt(estoqueMinimo.getText()));
                pst.setInt(7, Integer.parseInt(estoqueMaximo.getText()));
                pst.setDouble(8, Double.parseDouble(prcCompra.getText()));
                pst.setDouble(9, Double.parseDouble(prcVenda.getText()));
                pst.setInt(10, Integer.parseInt(codigoBarras.getText()));
                pst.setString(11, ncm.getText());

                String fator = fatorLucro.getText();
                pst.setDouble(12, Double.parseDouble(fator.replace(" %", "")));  

                pst.setDate(13, new java.sql.Date(data.getDate().getTime()));
                pst.setString(14, codigo);
                
                int flag = status.getSelectedIndex();
                char stat = 'I';
                if (flag == 1) {
                    stat = 'I';
                } else if (flag == 2) {
                    stat = 'A';
                }
                pst.setString(2, String.valueOf(stat));
                
                pst.execute();
                pst.close();
            } else {
                String sql = "UPDATE produto SET cod = ?, status = ?, nome = ?, descricao = ?, qtd_estoque = ?, estoque_minimo = ?, estoque_maximo = ?, preco_compra = ?, preco_venda = ?, bar_code = ?, ncm = ?, fator = ?, data_cadastro = ?, imagem = ? WHERE cod = ?";
                pst = conexao.prepareStatement(sql);

                pst.setString(1, cod.getText());
                pst.setString(3, nome.getText());
                pst.setString(4, desc.getText());
                pst.setInt(5, Integer.parseInt(qtdEstoque.getText()));
                pst.setInt(6, Integer.parseInt(estoqueMinimo.getText()));
                pst.setInt(7, Integer.parseInt(estoqueMaximo.getText()));
                pst.setDouble(8, Double.parseDouble(prcCompra.getText()));
                pst.setDouble(9, Double.parseDouble(prcVenda.getText()));
                pst.setInt(10, Integer.parseInt(codigoBarras.getText()));
                pst.setString(11, ncm.getText());

                String fator = fatorLucro.getText();
                pst.setDouble(12, Double.parseDouble(fator.replace(" %", "")));  

                pst.setDate(13, new java.sql.Date(data.getDate().getTime()));
                pst.setString(14, cod.getText());
                pst.setBlob(14, fis, tam);

                int flag = status.getSelectedIndex();
                char stat = 'I';
                if (flag == 1) {
                    stat = 'I';
                } else if (flag == 2) {
                    stat = 'A';
                }
                pst.setString(2, String.valueOf(stat));

                pst.setString(15, codigo);

                pst.execute();
                pst.close();
            }
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
            
            limpar();
            pesquisa.setText("");
            listar();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + e.getMessage());
        }
        fis = null;
    }
    
     public void listar() {
        conexao = Conexao.obterConexao();
        DefaultTableModel model = (DefaultTableModel) tabela.getModel();
        model.setNumRows(0);

        try {
            String sql = "SELECT * FROM produto WHERE nome LIKE ?";
            pst = conexao.prepareStatement(sql);
            pst.setString(1, pesquisa.getText() + "%");
            rs = pst.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("cod"),
                    rs.getString("status"),
                    rs.getString("nome"),
                    rs.getString("qtd_estoque"),
                    rs.getString("estoque_minimo"),
                    rs.getString("estoque_maximo"),
                    rs.getString("preco_compra"),
                    rs.getString("preco_venda"),
                    rs.getString("fator")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        pesquisa.setText("");
    }
    
    public void delete(){
        conexao = Conexao.obterConexao();
        try{
            String sql = "DELETE FROM produto WHERE cod = ?";
            pst = conexao.prepareStatement(sql);
            pst.setString(1, cod.getText());
            pst.execute();
            pst.close();
            JOptionPane.showMessageDialog(null, "Excluido com sucesso!");
            limpar();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao excluir");
        }
        listar();
    }
   
    public void adicionarFoto(){
        JFileChooser foto = new JFileChooser();
        foto.setDialogTitle("Selecionar arquivo");
        foto.setFileFilter(new FileNameExtensionFilter("Arquivo de Imagem(.PNG,.JPG,*.JPEG)", "png", "jgp", "jpeg"));
        int resultado = foto.showOpenDialog(this);

        if(resultado == JFileChooser.APPROVE_OPTION){
            try{
                fis = new FileInputStream(foto.getSelectedFile());
                tam = (int) foto.getSelectedFile().length();
                Image ft = ImageIO.read(foto.getSelectedFile()).getScaledInstance(labelFoto.getWidth(), labelFoto.getHeight(), Image.SCALE_SMOOTH);
                labelFoto.setIcon(new ImageIcon(ft));
                labelFoto.updateUI();
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
    }
    
    public void selecionar () {
        cod.setText(tabela.getValueAt(tabela.getSelectedRow(), 0).toString());
        codigo = cod.getText();
        conexao = Conexao.obterConexao();
        labelFoto.setIcon(null);
        
        try {
            String sql = "SELECT * FROM produto WHERE cod = ?";
            pst = conexao.prepareStatement(sql);
            pst.setString(1, cod.getText());
            rs = pst.executeQuery();
            
            if(rs.next()){
                rs.getString(3);
                if ("I".equals(rs.getString(3).trim())) {
                    status.setSelectedIndex(1);
                } else if ("A".equals(rs.getString(3).trim())) {
                    status.setSelectedIndex(2);
                }
                cod.setText(rs.getString(2));
                nome.setText(rs.getString(4));
                desc.setText(rs.getString(5));
                qtdEstoque.setText(rs.getString(6));
                estoqueMinimo.setText(rs.getString(7));
                estoqueMaximo.setText(rs.getString(8));
                prcCompra.setText(rs.getString(9));
                prcVenda.setText(rs.getString(10));
                codigoBarras.setText(rs.getString(11));
                ncm.setText(rs.getString(12));
                fatorLucro.setText(rs.getString(13));
                data.setDate(rs.getTimestamp(14));
                
                Blob blob  = (Blob) rs.getBlob(15);
                byte[] img = blob.getBytes(1, (int) blob.length());
                BufferedImage imagem = null;
                try{
                    imagem  = ImageIO.read(new ByteArrayInputStream(img));
                }
                catch (Exception e){
                }
                ImageIcon icone = new ImageIcon(imagem);
                Icon foto = new ImageIcon(icone.getImage().getScaledInstance(labelFoto.getWidth(), labelFoto.getHeight(), Image.SCALE_SMOOTH));
                labelFoto.setIcon(foto);
            }
            else{
                JOptionPane.showMessageDialog(null, "Produto nÃ£o cadastrado!");
            }
            pst.close();
        } 
        catch (Exception e) {
        
        }
    }
    
    public void pesquisar() {
        conexao = Conexao.obterConexao();
        DefaultTableModel model = (DefaultTableModel) tabela.getModel();
        model.setNumRows(0);
        try {
            String sql = "SELECT cod, status, nome, qtd_estoque, estoque_minimo, estoque_maximo, preco_compra, preco_venda, fator FROM produto WHERE nome LIKE ?";
            pst = conexao.prepareStatement(sql);
            pst.setString(1, pesquisa.getText() + "%"); 
            rs = pst.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("cod"),
                    rs.getString("status"),
                    rs.getString("nome"),
                    rs.getInt("qtd_estoque"),
                    rs.getInt("estoque_minimo"),
                    rs.getInt("estoque_maximo"),
                    rs.getFloat("preco_compra"),
                    rs.getFloat("preco_venda"),
                    rs.getDouble("fator")
                });
            }
            pst.close();
        } 
        catch (Exception e) {
        }
        pesquisa.setText("Pesquisar...");
    }
    
    public void imprimir(){
        Document document = new Document();
        try{
            PdfWriter.getInstance(document, new FileOutputStream("produtos.pdf"));
            document.open();
            LocalDateTime dataHora = LocalDateTime.now();  // Data e hora atuais
            DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss"); 
            document.add(new Paragraph(formatador.format(dataHora)));
            document.add(new Paragraph("Listagem de produtos:"));
            document.add(new Paragraph(" "));
            PdfPTable tabela = new PdfPTable(9);
            PdfPCell col1 = new PdfPCell(new Paragraph("Código"));
            tabela.addCell(col1);
            PdfPCell col2 = new PdfPCell(new Paragraph("Status"));
            tabela.addCell(col2);
            PdfPCell col3 = new PdfPCell(new Paragraph("Nome"));
            tabela.addCell(col3);
            PdfPCell col4 = new PdfPCell(new Paragraph("Qtd Estoque"));
            tabela.addCell(col4);
            PdfPCell col5 = new PdfPCell(new Paragraph("Qtd Min"));
            tabela.addCell(col5);
            PdfPCell col6 = new PdfPCell(new Paragraph("Qtd Max"));
            tabela.addCell(col6);
            PdfPCell col7 = new PdfPCell(new Paragraph("Preço Compra"));
            tabela.addCell(col7);
            PdfPCell col8 = new PdfPCell(new Paragraph("Preço Venda"));
            tabela.addCell(col8);
            PdfPCell col9 = new PdfPCell(new Paragraph("Fator (%)"));
            tabela.addCell(col9);
            String readLista = "SELECT * FROM produto ORDER BY data_cadastro";
            try{
                conexao = Conexao.obterConexao();
                pst = conexao.prepareStatement(readLista);
                rs = pst.executeQuery();
                while(rs.next()){
                    tabela.addCell(rs.getString(2));
                    tabela.addCell(rs.getString(3));
                    tabela.addCell(rs.getString(4));
                    tabela.addCell(rs.getString(6));
                    tabela.addCell(rs.getString(7));
                    tabela.addCell(rs.getString(8));
                    tabela.addCell(rs.getString(9));
                    tabela.addCell(rs.getString(10));
                    tabela.addCell(rs.getString(13));
                }
            }
            catch(Exception ex){
                System.out.println(ex);
            }
            document.add(tabela);
        }
        catch(Exception e){
            System.out.println(e);
        }
        finally{
            document.close();
        }
        try{
            Desktop.getDesktop().open(new File("produtos.pdf"));
        }
        catch(Exception e2){
            System.out.println(e2);
        }
    }
    
    public double calcularLucro() {
        double venda, compra, lucro;
        try {
            venda = Double.parseDouble(prcCompra.getText());
            compra = Double.parseDouble(prcVenda.getText());
            lucro = ((venda / compra) * 100) - 100;
            lucro = Math.round(lucro * 100.0) / 100.0;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Erro: Valores de preço inválidos.");
            lucro = 0;
        }
        return lucro;

    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        nome = new javax.swing.JTextField();
        qtdEstoque = new javax.swing.JTextField();
        cod = new javax.swing.JTextField();
        status = new javax.swing.JComboBox<>();
        desc = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        estoqueMinimo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        data = new com.toedter.calendar.JDateChooser();
        estoqueMaximo = new javax.swing.JTextField();
        cadastrar = new javax.swing.JButton();
        alterar = new javax.swing.JButton();
        apagar = new javax.swing.JButton();
        limpar = new javax.swing.JButton();
        imprimir = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        prcCompra = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        prcVenda = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        fatorLucro = new javax.swing.JTextField();
        ncm = new javax.swing.JTextField();
        codigoBarras = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        pesquisa = new javax.swing.JTextField();
        listar = new javax.swing.JButton();
        sair = new javax.swing.JButton();
        labelFoto = new javax.swing.JLabel();
        voltar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "codigo", "status", "nome", "Estoque", "Qtd.Mínima", "Qtd.Máxima", "Compra", "Venda", "Fator (%)"
            }
        ));
        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabela);

        jLabel1.setText("Código");

        jLabel2.setText("Status");

        jLabel3.setText("Data de Cadastro");

        jLabel4.setText("Nome");

        jLabel5.setText("Quantidade em Estoque");

        cod.setToolTipText("");

        status.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "Inativo", "Ativo" }));
        status.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusActionPerformed(evt);
            }
        });

        desc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                descActionPerformed(evt);
            }
        });

        jLabel6.setText("Descrição");

        jLabel7.setText("Estoque Mínimo");

        jLabel8.setText("Estoque Máximo");

        cadastrar.setText("Novo");
        cadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cadastrarActionPerformed(evt);
            }
        });

        alterar.setText("Alterar");
        alterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alterarActionPerformed(evt);
            }
        });

        apagar.setText("Apagar");
        apagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                apagarActionPerformed(evt);
            }
        });

        limpar.setText("Limpar");
        limpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limparActionPerformed(evt);
            }
        });

        imprimir.setText("Imprimir");
        imprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imprimirActionPerformed(evt);
            }
        });

        jLabel9.setText("Preço de Compra");

        prcCompra.setToolTipText("");
        prcCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prcCompraActionPerformed(evt);
            }
        });

        jLabel10.setText("Preço de Venda");

        prcVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prcVendaActionPerformed(evt);
            }
        });

        jLabel11.setText("Fator Lucro");

        jLabel12.setText("NCM");

        fatorLucro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fatorLucroActionPerformed(evt);
            }
        });

        jLabel13.setText("Código de Barras ETIN / ETAN");

        pesquisa.setText("Pesquisar...");
        pesquisa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pesquisaMouseClicked(evt);
            }
        });
        pesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pesquisaActionPerformed(evt);
            }
        });

        listar.setText("Listar");
        listar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listarActionPerformed(evt);
            }
        });

        sair.setText("Sair");
        sair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sairActionPerformed(evt);
            }
        });

        labelFoto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        labelFoto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelFotoMouseClicked(evt);
            }
        });

        voltar.setText("Voltar");
        voltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                voltarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cod, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(data, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel4)
                                            .addGap(313, 313, 313)
                                            .addComponent(jLabel5))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(nome, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(24, 24, 24)
                                            .addComponent(qtdEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(jLabel7)
                                                    .addComponent(estoqueMinimo, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
                                                .addComponent(jLabel9)
                                                .addComponent(jLabel11)
                                                .addComponent(fatorLucro, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGap(46, 46, 46)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jLabel12)
                                                .addComponent(jLabel10)
                                                .addComponent(prcVenda, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                                                .addComponent(ncm))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(labelFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(49, 49, 49)
                                            .addComponent(listar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(voltar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(sair, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(prcCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(46, 46, 46)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel8)
                                                .addComponent(estoqueMaximo, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGap(259, 259, 259))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addComponent(jLabel1)
                                            .addGap(163, 163, 163)
                                            .addComponent(jLabel2)
                                            .addGap(113, 113, 113)
                                            .addComponent(jLabel3)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(cadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(alterar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(desc, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel6))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(imprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(limpar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(apagar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(codigoBarras, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(34, 34, 34)
                                    .addComponent(pesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(data, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(qtdEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(desc, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(estoqueMaximo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel10)
                                        .addGap(7, 7, 7)
                                        .addComponent(prcVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(ncm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(estoqueMinimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(prcCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(fatorLucro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(labelFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(codigoBarras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(alterar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(listar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(apagar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(limpar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(imprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(voltar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sair, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void statusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusActionPerformed

    }//GEN-LAST:event_statusActionPerformed

    private void descActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_descActionPerformed

    }//GEN-LAST:event_descActionPerformed

    private void cadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cadastrarActionPerformed
        cadastrar();
    }//GEN-LAST:event_cadastrarActionPerformed

    private void alterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alterarActionPerformed
        atualizar();
    }//GEN-LAST:event_alterarActionPerformed

    private void apagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_apagarActionPerformed
        delete();
    }//GEN-LAST:event_apagarActionPerformed

    private void limparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limparActionPerformed
        limpar();
    }//GEN-LAST:event_limparActionPerformed

    private void imprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imprimirActionPerformed
        imprimir();
    }//GEN-LAST:event_imprimirActionPerformed

    private void prcCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prcCompraActionPerformed

    }//GEN-LAST:event_prcCompraActionPerformed

    private void fatorLucroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fatorLucroActionPerformed
        calcularLucro();
    }//GEN-LAST:event_fatorLucroActionPerformed

    private void pesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pesquisaActionPerformed
        listar();
    }//GEN-LAST:event_pesquisaActionPerformed

    private void listarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listarActionPerformed
        listar();
    }//GEN-LAST:event_listarActionPerformed

    private void pesquisaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pesquisaMouseClicked
        pesquisa.setText("");
    }//GEN-LAST:event_pesquisaMouseClicked

    private void sairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sairActionPerformed
        if (JOptionPane.showConfirmDialog(null, "Deseja realmente SAIR?", "warning", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
            this.dispose();
        }
        else{
        }
    }//GEN-LAST:event_sairActionPerformed

    private void labelFotoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelFotoMouseClicked
        adicionarFoto();
    }//GEN-LAST:event_labelFotoMouseClicked

    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked
        selecionar();
    }//GEN-LAST:event_tabelaMouseClicked

    private void prcVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prcVendaActionPerformed
        calcularLucro();
    }//GEN-LAST:event_prcVendaActionPerformed

    private void voltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_voltarActionPerformed
        this.dispose();
        new Menu().setVisible(true);
    }//GEN-LAST:event_voltarActionPerformed

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
            java.util.logging.Logger.getLogger(Produtos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Produtos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Produtos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Produtos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Produtos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton alterar;
    private javax.swing.JButton apagar;
    private javax.swing.JButton cadastrar;
    private javax.swing.JTextField cod;
    private javax.swing.JTextField codigoBarras;
    private com.toedter.calendar.JDateChooser data;
    private javax.swing.JTextField desc;
    private javax.swing.JTextField estoqueMaximo;
    private javax.swing.JTextField estoqueMinimo;
    private javax.swing.JTextField fatorLucro;
    private javax.swing.JButton imprimir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelFoto;
    private javax.swing.JButton limpar;
    private javax.swing.JButton listar;
    private javax.swing.JTextField ncm;
    private javax.swing.JTextField nome;
    private javax.swing.JTextField pesquisa;
    private javax.swing.JTextField prcCompra;
    private javax.swing.JTextField prcVenda;
    private javax.swing.JTextField qtdEstoque;
    private javax.swing.JButton sair;
    private javax.swing.JComboBox<String> status;
    private javax.swing.JTable tabela;
    private javax.swing.JButton voltar;
    // End of variables declaration//GEN-END:variables
}
