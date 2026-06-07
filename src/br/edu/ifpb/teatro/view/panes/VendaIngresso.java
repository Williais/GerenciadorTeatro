package br.edu.ifpb.teatro.view.panes;

import br.edu.ifpb.teatro.dao.CentralDeInformacoes;
import br.edu.ifpb.teatro.dao.Persistencia;
import br.edu.ifpb.teatro.enums.PessoaSexo;
import br.edu.ifpb.teatro.model.Ingresso;
import br.edu.ifpb.teatro.model.Pessoa;
import br.edu.ifpb.teatro.model.PropostaDeAluguel;
import br.edu.ifpb.teatro.security.ValidadorDesconto;
import br.edu.ifpb.teatro.security.ValidadorDocumento;
import br.edu.ifpb.teatro.util.GeradorDeContratos;
import br.edu.ifpb.teatro.util.GeradorDeIngressos;
import br.edu.ifpb.teatro.util.LimpadorCPF;
import br.edu.ifpb.teatro.util.Mensageiro;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import static br.edu.ifpb.teatro.view.TelaHome.BG_COLOR;
import static br.edu.ifpb.teatro.view.TelaHome.PANEL_COLOR;

public class VendaIngresso extends JPanel {

    private CentralDeInformacoes centralDeInformacoes;

    private JTextField txtNome;
    private JComboBox<PessoaSexo> cbSexo;
    private JTextField txtCPF;
    private JTextField txtEmail;
    private JTextField txtTelefone;
    private JTextField txtDataNascimento;
    private JLabel lblTotal;

    private JComboBox<String> cbEventos;
    private JComboBox<String> cbIngressos;
    private JComboBox<String> cbPagamento;

    private JTable tabelaIngressos;
    private DefaultTableModel modeloCarrinho;

    private JSpinner spnQtd;

    public VendaIngresso(CentralDeInformacoes centralDeInformacoes) {
        this.centralDeInformacoes = centralDeInformacoes;

        this.setLayout(new BorderLayout(20, 0));
        this.setBackground(BG_COLOR);
        this.setBorder(new EmptyBorder(25, 25, 25, 25));

        this.add(painelCarrinho(), BorderLayout.CENTER);
        this.add(painelComprador(), BorderLayout.WEST);
        carregarEventos();
        this.addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent e) {
                carregarEventos();
            }
        });
    }

    public JPanel painelCarrinho() {
        JPanel painelCarrinho = new JPanel(new BorderLayout(0, 15));
        painelCarrinho.setOpaque(false);

        JPanel painelSelecao = new JPanel();
        painelSelecao.setOpaque(false);
        painelSelecao.setLayout(new BoxLayout(painelSelecao, BoxLayout.Y_AXIS));

        JPanel painelEvento = new JPanel(new BorderLayout(0, 5));
        painelEvento.setOpaque(false);
        JLabel lblEvento = new JLabel("Selecione Evento");
        lblEvento.setForeground(Color.WHITE);
        lblEvento.setFont(new Font("SansSerif", Font.BOLD, 18));

        cbEventos  = new JComboBox<>(new String[]{"Selecione Evento"});
        painelEvento.add(lblEvento, BorderLayout.NORTH);
        painelEvento.add(cbEventos, BorderLayout.CENTER);

        JPanel painelIngresso = new JPanel(new GridLayout(1, 3, 15, 0));
        painelIngresso.setOpaque(false);
        painelIngresso.setBorder(new EmptyBorder(15, 0, 0, 0));

        JPanel painelTipo = new JPanel(new BorderLayout(0, 5));
        painelTipo.setOpaque(false);
        JLabel lblTipo = new JLabel("Tipos de Ingresso");
        lblTipo.setForeground(Color.WHITE);

        cbIngressos  = new JComboBox<>(new String[]{"Selecione Ingresso (Inteira ou Meia)"});
        painelTipo.add(lblTipo, BorderLayout.NORTH);
        painelTipo.add(cbIngressos, BorderLayout.CENTER);

        JPanel painelQtd = new JPanel(new BorderLayout(0, 5));
        painelQtd.setOpaque(false);
        JLabel lblQtd = new JLabel("Quantidade de Ingressos");
        lblQtd.setForeground(Color.WHITE);

        spnQtd = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));

        painelQtd.add(lblQtd, BorderLayout.NORTH);
        painelQtd.add(spnQtd, BorderLayout.CENTER);

        JButton btnIngresso = new JButton("Adicionar Ingresso");
        btnIngresso.setBackground(new Color(90, 90, 90));
        btnIngresso.setForeground(Color.WHITE);
        btnIngresso.setFocusPainted(false);
        btnIngresso.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int indexEvento = cbEventos.getSelectedIndex();
                int indexTipo = cbIngressos.getSelectedIndex();

                if (indexEvento == 0 || indexTipo == 0) {
                    JOptionPane.showMessageDialog(null, "Selecione o evento e o tipo de ingresso!");
                    return;
                }

                PropostaDeAluguel eventoSelecionado = centralDeInformacoes.getTodasAsPropostas().stream()
                        .filter(p -> p.getStatus().name().equals("CONTRATADO"))
                        .toList()
                        .get(indexEvento - 1);

                int qtd = (int) spnQtd.getValue();
                float precoBase = eventoSelecionado.getPrecoDoIngresso();

                if (cbIngressos.getSelectedItem().toString().equals("Meia")) {
                    precoBase = precoBase / 2;
                }

                float subTotal = precoBase * qtd;

                Object[] linha = {
                        eventoSelecionado.getNomeDaPeca() + " (" + cbIngressos.getSelectedItem() + ")",
                        qtd,
                        subTotal,
                        eventoSelecionado.getId(),
                        cbIngressos.getSelectedItem().toString()
                };
                modeloCarrinho.addRow(linha);
                atualizarTotalCarrinho();
            }
        });

        JPanel painelBotao = new JPanel(new BorderLayout());
        painelBotao.setOpaque(false);
        painelBotao.add(new JLabel(" "), BorderLayout.NORTH);
        painelBotao.add(btnIngresso, BorderLayout.CENTER);

        painelIngresso.add(painelTipo);
        painelIngresso.add(painelQtd);
        painelIngresso.add(painelBotao);

        painelSelecao.add(painelEvento);
        painelSelecao.add(painelIngresso);

        String[] coluna = {"Ingresso", "Quantidade", "Sub-Total", "ID_Evento", "Tipo_Ingresso"};
        modeloCarrinho = new DefaultTableModel(coluna, 0);
        tabelaIngressos = new JTable(modeloCarrinho);

        tabelaIngressos.getColumnModel().getColumn(3).setMinWidth(0);
        tabelaIngressos.getColumnModel().getColumn(3).setMaxWidth(0);
        tabelaIngressos.getColumnModel().getColumn(3).setWidth(0);
        tabelaIngressos.getColumnModel().getColumn(4).setMinWidth(0);
        tabelaIngressos.getColumnModel().getColumn(4).setMaxWidth(0);
        tabelaIngressos.getColumnModel().getColumn(4).setWidth(0);

        JScrollPane scrollPane = new JScrollPane(tabelaIngressos);
        scrollPane.getViewport().setBackground(PANEL_COLOR);

        painelCarrinho.add(painelSelecao, BorderLayout.NORTH);
        painelCarrinho.add(scrollPane, BorderLayout.CENTER);

        return painelCarrinho;
    }

    public JPanel painelComprador() {
        JPanel painel = new JPanel(new BorderLayout(0, 15));
        painel.setPreferredSize(new Dimension(350, 0));
        painel.setBackground(PANEL_COLOR);
        painel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel wrapperTopo = new JPanel(new BorderLayout());
        wrapperTopo.setOpaque(false);

        JLabel lblTitulo = new JLabel("Dados de Venda");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBorder(new EmptyBorder(0, 0, 15, 0));
        wrapperTopo.add(lblTitulo, BorderLayout.NORTH);

        JPanel formComprador = new JPanel();
        formComprador.setLayout(new BoxLayout(formComprador, BoxLayout.Y_AXIS));
        formComprador.setOpaque(false);

        txtCPF = adicionarCampoFormulario(formComprador, "CPF");
        txtCPF.addFocusListener(new OuvinteFocoCpf());

        txtNome = adicionarCampoFormulario(formComprador, "Nome");
        txtEmail = adicionarCampoFormulario(formComprador, "E-mail");
        txtDataNascimento = adicionarCampoFormulario(formComprador, "Data de Nascimento");
        txtTelefone = adicionarCampoFormulario(formComprador, "Telefone");

        JLabel lblSexo = new JLabel("Sexo");
        lblSexo.setForeground(Color.LIGHT_GRAY);
        lblSexo.setAlignmentX(Component.LEFT_ALIGNMENT);
        cbSexo = new JComboBox<>(PessoaSexo.values());
        cbSexo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        cbSexo.setAlignmentX(Component.LEFT_ALIGNMENT);
        formComprador.add(lblSexo);
        formComprador.add(Box.createRigidArea(new Dimension(0, 5)));
        formComprador.add(cbSexo);
        formComprador.add(Box.createRigidArea(new Dimension(0, 15)));

        JLabel lblPagamento = new JLabel("Forma de Pagamento");
        lblPagamento.setForeground(Color.LIGHT_GRAY);
        lblPagamento.setAlignmentX(Component.LEFT_ALIGNMENT);

        cbPagamento = new JComboBox<>(new String[]{"Pix - QR Code", "Cartão de Débito/Crédito", "Dinheiro"});
        cbPagamento.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        cbPagamento.setAlignmentX(Component.LEFT_ALIGNMENT);

        formComprador.add(lblPagamento);
        formComprador.add(Box.createRigidArea(new Dimension(0, 5)));
        formComprador.add(cbPagamento);
        formComprador.add(Box.createRigidArea(new Dimension(0, 15)));

        wrapperTopo.add(formComprador, BorderLayout.CENTER);
        painel.add(wrapperTopo, BorderLayout.NORTH);

        JPanel painelRodape = new JPanel();
        painelRodape.setLayout(new BoxLayout(painelRodape, BoxLayout.Y_AXIS));
        painelRodape.setOpaque(false);

        JPanel linhaTotal = new JPanel(new BorderLayout());
        linhaTotal.setOpaque(false);
        JLabel lblTextoTotal = new JLabel("Total a Pagar:");
        lblTextoTotal.setForeground(Color.GRAY);
        lblTotal = new JLabel("R$ 0,00");
        lblTotal.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTotal.setForeground(new Color(46, 204, 113));
        linhaTotal.add(lblTextoTotal, BorderLayout.WEST);
        linhaTotal.add(lblTotal, BorderLayout.EAST);

        JButton btnConfirmar = new JButton("Confirmar Venda e Gerar PDF");
        btnConfirmar.setBackground(new Color(46, 204, 113));
        btnConfirmar.setForeground(Color.WHITE);
        btnConfirmar.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnConfirmar.setFocusPainted(false);
        btnConfirmar.putClientProperty("JButton.buttonType", "borderless");
        btnConfirmar.putClientProperty("Component.arc", 10);
        btnConfirmar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnConfirmar.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnConfirmar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                if(modeloCarrinho.getRowCount() == 0){
                    JOptionPane.showMessageDialog(null, "carrinho está vazio");
                    return;
                }

                if (txtCPF.getText().trim().isEmpty() || txtNome.getText().trim().isEmpty()){
                    JOptionPane.showMessageDialog(null, "não pode campos vazios");
                    return;
                }

                String cpf = txtCPF.getText();
                Pessoa clienteFinal = null;
                try{

                    ValidadorDocumento.validarCPF(cpf);
                    Pessoa p = centralDeInformacoes.recuperarPessoaPorCPF(cpf);

                    if(p != null){
                        clienteFinal = p;

                    }else{
                        String nome = txtNome.getText();
                        String email = txtEmail.getText();
                        PessoaSexo sexo = (PessoaSexo) cbSexo.getSelectedItem();
                        String telefone = txtTelefone.getText();
                        String dataNasc = txtDataNascimento.getText();

                        clienteFinal = new Pessoa(nome, cpf, email, sexo, telefone, dataNasc);
                        centralDeInformacoes.getTodasAsPessoas().add(clienteFinal);
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

                float valorTotalCarrinho = 0.0f;
                for (int i = 0; i < modeloCarrinho.getRowCount(); i++) {
                    valorTotalCarrinho += (float) modeloCarrinho.getValueAt(i, 2);

                }

                float valorComDesconto = ValidadorDesconto.calcularDescontoAniversario(clienteFinal, valorTotalCarrinho);

                boolean teveDesconto = false;

                if (valorComDesconto < valorTotalCarrinho) {
                    JOptionPane.showMessageDialog(null, "Desconto de aniversario aplicado! O total foi de R$ " + valorTotalCarrinho + " para R$ " + valorComDesconto);
                    teveDesconto = true;
                }

                List<Ingresso> ingressosDestaVenda = new ArrayList<>();

                for (int i = 0; i < modeloCarrinho.getRowCount(); i++) {
                    int qtd = (int) modeloCarrinho.getValueAt(i, 1);
                    float subTotal = (float) modeloCarrinho.getValueAt(i, 2);
                    long idEvento = (long) modeloCarrinho.getValueAt(i, 3);
                    String tipoIngresso = (String) modeloCarrinho.getValueAt(i, 4);

                    PropostaDeAluguel evento = centralDeInformacoes.recuperarPropostaPorId(idEvento);
                    float valorUnitario = subTotal / qtd;

                    if (teveDesconto) {
                        valorUnitario = valorUnitario * 0.90f;
                    }

                    Ingresso novoIngresso = new Ingresso(clienteFinal, evento, qtd, subTotal, tipoIngresso);

                    ingressosDestaVenda.add(novoIngresso);
                    centralDeInformacoes.getIngresso().add(novoIngresso);
                }

                Persistencia persistencia = new Persistencia();
                persistencia.salvarCentral(centralDeInformacoes, "central.xml");

                float valorFinalCobrado = (valorComDesconto < valorTotalCarrinho) ? valorComDesconto : valorTotalCarrinho;

                GeradorDeIngressos.gerarPdfDaVenda(clienteFinal, ingressosDestaVenda, valorFinalCobrado);

                JOptionPane.showMessageDialog(null, "Venda Confirmada");

                int enviar = JOptionPane.showConfirmDialog(null, "Deseja que o ingresso seja enviado para seu e-mail?", "Enviar por email?", JOptionPane.YES_NO_CANCEL_OPTION);
                if (enviar == JOptionPane.YES_OPTION) {

                    JDialog dialogCarregando = new JDialog();
                    dialogCarregando.setTitle("Aguarde");
                    dialogCarregando.setModal(true);
                    dialogCarregando.setSize(250, 100);
                    dialogCarregando.setLocationRelativeTo(null);
                    dialogCarregando.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

                    JPanel painelAguarde = new JPanel(new BorderLayout());
                    painelAguarde.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
                    painelAguarde.add(new JLabel("Enviando ingressos por e-mail...", SwingConstants.CENTER), BorderLayout.CENTER);
                    dialogCarregando.add(painelAguarde);

                    SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

                        protected Void doInBackground() throws Exception {

                            Mensageiro.enviarIngresso(txtEmail.getText(), "Ingressos_" + LimpadorCPF.limparCPF(cpf) + ".pdf");
                            return null;
                        }

                        protected void done() {

                            dialogCarregando.dispose();
                            try {
                                get();
                                JOptionPane.showMessageDialog(null, "email enviado!");
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, "Erro ao enviar email: " + ex.getMessage());
                            }
                        }
                    };

                    worker.execute();
                    dialogCarregando.setVisible(true);
                }

                modeloCarrinho.setRowCount(0);
                atualizarTotalCarrinho();
                cbEventos.setSelectedIndex(0);
                cbIngressos.setSelectedIndex(0);
                spnQtd.setValue(1);

            }
        });

        JButton btnLimpar = new JButton("Limpar Carrinho");
        btnLimpar.setBackground(Color.GRAY);
        btnLimpar.setForeground(Color.WHITE);
        btnLimpar.setFocusPainted(false);
        btnLimpar.putClientProperty("JButton.buttonType", "borderless");
        btnLimpar.putClientProperty("Component.arc", 10);
        btnLimpar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnLimpar.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnLimpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modeloCarrinho.setRowCount(0);
                atualizarTotalCarrinho();
                cbEventos.setSelectedIndex(0);
                cbIngressos.setSelectedIndex(0);
                spnQtd.setValue(1);
            }
        });

        painelRodape.add(linhaTotal);
        painelRodape.add(Box.createRigidArea(new Dimension(0, 15)));
        painelRodape.add(btnConfirmar);
        painelRodape.add(Box.createRigidArea(new Dimension(0, 10)));
        painelRodape.add(btnLimpar);

        painel.add(painelRodape, BorderLayout.SOUTH);

        return painel;
    }


    private JTextField adicionarCampoFormulario(JPanel container, String nomeLabel) {
        JLabel label = new JLabel(nomeLabel);
        label.setForeground(Color.LIGHT_GRAY);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField textField = new JTextField();
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        textField.setAlignmentX(Component.LEFT_ALIGNMENT);

        container.add(label);
        container.add(Box.createRigidArea(new Dimension(0, 5)));
        container.add(textField);
        container.add(Box.createRigidArea(new Dimension(0, 15)));

        return textField;
    }

    private void carregarEventos() {
        cbEventos.removeAllItems();
        cbEventos.addItem("Selecione Evento");

        for (PropostaDeAluguel p : centralDeInformacoes.getTodasAsPropostas()) {
            if (p.getStatus().name().equals("CONTRATADO") ||p.getStatus().name().equals("CONTRATADO_COM_ALTERACAO")) {
                cbEventos.addItem(p.getNomeDaPeca() + " (" + p.getDataEvento() + ")");
            }
        }

        cbIngressos.removeAllItems();
        cbIngressos.addItem("Selecione o Tipo");
        cbIngressos.addItem("Inteira");
        cbIngressos.addItem("Meia");
    }

    private void atualizarTotalCarrinho() {
        float totalGeral = 0.0f;

        for (int i = 0; i < modeloCarrinho.getRowCount(); i++) {
            float subTotalLinha = (float) modeloCarrinho.getValueAt(i, 2);
            totalGeral += subTotalLinha;
        }

        lblTotal.setText(String.format("R$ %.2f", totalGeral));
    }

    private class OuvinteFocoCpf implements FocusListener {
        public void focusGained(FocusEvent e) {
        }

        public void focusLost(FocusEvent e) {
            String cpf = txtCPF.getText().trim();

            if (cpf.isEmpty()) {
                return;
            }

            Pessoa cliente = centralDeInformacoes.recuperarPessoaPorCPF(cpf);

            if (cliente != null) {
                txtNome.setText(cliente.getNome());
                txtEmail.setText(cliente.getEmail());
                txtDataNascimento.setText(cliente.getDataNascimento());
                if (txtTelefone != null) txtTelefone.setText(cliente.getTelefone());
                if (cbSexo != null) cbSexo.setSelectedItem(cliente.getSexo());

                txtNome.setEditable(false);
                txtEmail.setEditable(false);
                txtDataNascimento.setEditable(false);
                if (txtTelefone != null) txtTelefone.setEditable(false);
                if (cbSexo != null) cbSexo.setEnabled(false);
            } else {
                txtNome.setText("");
                txtEmail.setText("");
                txtDataNascimento.setText("");
                if (txtTelefone != null) txtTelefone.setText("");
                if (cbSexo != null) cbSexo.setSelectedIndex(0);

                txtNome.setEditable(true);
                txtEmail.setEditable(true);
                txtDataNascimento.setEditable(true);
                if (txtTelefone != null) txtTelefone.setEditable(true);
                if (cbSexo != null) cbSexo.setEnabled(true);
            }
        }
    }
}