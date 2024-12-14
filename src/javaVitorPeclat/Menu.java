package javaVitorPeclat;

public class Menu extends javax.swing.JFrame {

    public Menu() {
        setLocationRelativeTo(null);
        initComponents();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        produtos = new javax.swing.JMenu();
        sobre = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel1.setText("MERCADINHO DO SEU JORGE");

        produtos.setText("Produtos");
        produtos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                produtosMouseClicked(evt);
            }
        });
        produtos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                produtosActionPerformed(evt);
            }
        });
        jMenuBar1.add(produtos);

        sobre.setText("Sobre");
        sobre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sobreMouseClicked(evt);
            }
        });
        jMenuBar1.add(sobre);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1)
                .addContainerGap(23, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel1)
                .addContainerGap(97, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void produtosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_produtosMouseClicked
        Produtos produto = new Produtos();
        produto.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_produtosMouseClicked

    private void produtosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_produtosActionPerformed
        
    }//GEN-LAST:event_produtosActionPerformed

    private void sobreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sobreMouseClicked
        Sobre sobre  = new Sobre();
        sobre.setVisible(true);
    }//GEN-LAST:event_sobreMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu produtos;
    private javax.swing.JMenu sobre;
    // End of variables declaration//GEN-END:variables
}
