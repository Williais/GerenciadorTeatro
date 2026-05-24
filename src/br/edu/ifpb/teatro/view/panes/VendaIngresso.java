package br.edu.ifpb.teatro.view.panes;

import br.edu.ifpb.teatro.dao.CentralDeInformacoes;
import br.edu.ifpb.teatro.enums.PessoaSexo;
import br.edu.ifpb.teatro.model.Pessoa;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

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

    private JComboBox<String> cbEventos;
    private JComboBox<String> cbIngressos;
    private JComboBox<String> cbPagamento;

    private JTable tabelaIngressos;

    private JSpinner spnQtd;

    public VendaIngresso(CentralDeInformacoes centralDeInformacoes) {

        this.centralDeInformacoes = centralDeInformacoes;

        this.setLayout(new BorderLayout(20, 0));
        this.setBackground(BG_COLOR);
        this.setBorder(new EmptyBorder(25, 25, 25, 25));

        this.add(painelCarrinho(), BorderLayout.CENTER);
        this.add(painelComprador(), BorderLayout.WEST);

    }

    public JPanel painelCarrinho() {
        JPanel painelCarrinho = new JPanel(new BorderLayout(0, 15));
        painelCarrinho.setOpaque(false);

        JPanel painelSelecao = new JPanel();
        painelSelecao.setOpaque(false);
        painelSelecao.setLayout(new BoxLayout(painelSelecao, BoxLayout.Y_AXIS));

        JPanel painelEvento = new JPanel(new BorderLayout());
        painelEvento.setOpaque(false);
        JLabel lblEvento = new JLabel("Selecione Evento");
        lblEvento.setForeground(Color.WHITE);
        lblEvento.setFont(new Font("SansSerif", Font.BOLD, 18));

        cbEventos  = new JComboBox<>(new String[]{"Selecione Evento"});
        painelEvento.add(lblEvento, BorderLayout.NORTH);
        painelEvento.add(cbEventos, BorderLayout.CENTER);

        JPanel painelIngresso = new JPanel(new GridLayout(1, 3, 15, 0));
        painelIngresso.setOpaque(false);
        painelIngresso.setBorder(new EmptyBorder(10,0,0,0));

        JPanel painelTipo = new JPanel(new BorderLayout());
        painelTipo.setOpaque(false);
        JLabel lblTipo = new JLabel("Tipos de Ingresso");
        lblTipo.setForeground(Color.WHITE);

        cbIngressos  = new JComboBox<>(new String[]{"Selecione Ingresso (Inteira ou Meia)"});
        painelIngresso.add(lblTipo, BorderLayout.NORTH);
        painelIngresso.add(cbIngressos, BorderLayout.CENTER);

        JPanel painelQtd = new JPanel(new BorderLayout());
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

        JPanel painelBotao = new JPanel(new BorderLayout());
        painelBotao.setOpaque(false);
        painelBotao.add(new JLabel(" "), BorderLayout.NORTH);
        painelBotao.add(btnIngresso, BorderLayout.CENTER);

        painelIngresso.add(painelTipo);
        painelIngresso.add(painelQtd);
        painelIngresso.add(painelBotao);

        painelSelecao.add(painelEvento);
        painelSelecao.add(painelIngresso);

        String[] coluna = {"Ingresso", "Quantidade", "Sub-Total"};
        Object[][] dados = {};

        tabelaIngressos = new JTable(dados, coluna);

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

        JLabel lblTitulo = new JLabel("Dados de Venda");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTitulo.setForeground(Color.WHITE);
        painel.add(lblTitulo, BorderLayout.NORTH);

        JPanel formComprador = new JPanel();
        formComprador.setLayout(new BoxLayout(formComprador, BoxLayout.Y_AXIS));
        formComprador.setOpaque(false);

        txtCPF = adicionarCampoFormulario(formComprador, "CPF");
        txtNome = adicionarCampoFormulario(formComprador, "Nome");
        txtEmail = adicionarCampoFormulario(formComprador, "E-mail");
        txtDataNascimento = adicionarCampoFormulario(formComprador, "Data de Nascimento");

        JLabel lblPagamento = new JLabel("Forma de Pagamento");
        lblPagamento.setForeground(Color.LIGHT_GRAY);
        cbPagamento = new JComboBox<>(new String[]{"Pix - QR Code", "Cartão de Débito/Crédito", "Dinheiro"});
        cbPagamento.setMaximumSize(new Dimension(Short.MAX_VALUE, 30));
        formComprador.add(lblPagamento);
        formComprador.add(Box.createRigidArea(new Dimension(0, 5)));
        formComprador.add(cbPagamento);

        painel.add(formComprador, BorderLayout.CENTER);

        // AINDA FALTA O TOTAL E BOTÃO PARA COMPRA

        // GERAR O INGRESSO
    }




}
