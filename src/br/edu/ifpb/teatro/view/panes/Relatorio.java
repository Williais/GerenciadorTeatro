package br.edu.ifpb.teatro.view.panes;

import br.edu.ifpb.teatro.dao.CentralDeInformacoes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import static br.edu.ifpb.teatro.view.TelaHome.*;

public class Relatorio extends JPanel {
    private CentralDeInformacoes central;

    private JComboBox<String> cbTipoRelatorio;
    private JTextField txtDataInicio;
    private JTextField txtDataFim;
    private JComboBox<String> cbFiltroEvento;
    private JButton btnGerarPDF;
    private JButton btnAcaoSecundaria;
    private JLabel lblValorReceita;
    private JLabel lblValorIngressos;
    private JLabel lblValorPecas;
    private JTable tabelaRelatorio;
    private DefaultTableModel modeloTabela;

    public Relatorio(CentralDeInformacoes central) {
        this.central = central;
        this.setLayout(new BorderLayout(20, 0));
        this.setBackground(BG_COLOR);
        this.setBorder(new EmptyBorder(25, 25, 25, 25));

        this.add(PainelParametros(), BorderLayout.WEST);
        this.add(PainelDashboard(), BorderLayout.CENTER);
    }

    private JPanel PainelParametros() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setPreferredSize(new Dimension(300, 0));
        painel.setBackground(PANEL_COLOR);
        painel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Parametros");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBorder(new EmptyBorder(0, 0, 20, 0));
        painel.add(lblTitulo, BorderLayout.NORTH);

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setOpaque(false);

        cbTipoRelatorio = adicionarComboBox(form, "Tipo de relatorio", new String[]{"Fechamento Financeiro", "Lista de Presença", "Ocupação"});

        form.add(Box.createRigidArea(new Dimension(0, 30)));

        JLabel lblPeriodo = new JLabel("Periodo de analise");
        lblPeriodo.setForeground(Color.LIGHT_GRAY);
        lblPeriodo.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(lblPeriodo);
        form.add(Box.createRigidArea(new Dimension(0, 5)));

        JPanel painelDatas = new JPanel(new GridLayout(1, 2, 10, 0));
        painelDatas.setOpaque(false);
        painelDatas.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        painelDatas.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtDataInicio = new JTextField("01/01/2025");
        txtDataFim = new JTextField("31/01/2025");
        painelDatas.add(txtDataInicio);
        painelDatas.add(txtDataFim);
        form.add(painelDatas);
        form.add(Box.createRigidArea(new Dimension(0, 20)));

        cbFiltroEvento = adicionarComboBox(form, "Filtrar por evento", new String[]{"Todos os Eventos"});

        painel.add(form, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new BoxLayout(painelBotoes, BoxLayout.Y_AXIS));
        painelBotoes.setOpaque(false);

        btnGerarPDF = new JButton("Gerar Relatorio em PDF");
        btnGerarPDF.setBackground(new Color(150, 0, 0));
        btnGerarPDF.setForeground(Color.WHITE);
        btnGerarPDF.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnGerarPDF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btnGerarPDF.setFocusPainted(false);

        btnAcaoSecundaria = new JButton("Atualizar Dashboard");
        btnAcaoSecundaria.setBackground(new Color(85, 139, 47));
        btnAcaoSecundaria.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btnAcaoSecundaria.setFocusPainted(false);

        painelBotoes.add(btnGerarPDF);
        painelBotoes.add(Box.createRigidArea(new Dimension(0, 15)));
        painelBotoes.add(btnAcaoSecundaria);

        painel.add(painelBotoes, BorderLayout.SOUTH);

        return painel;
    }

    private JPanel PainelDashboard() {
        JPanel painel = new JPanel(new BorderLayout(0, 20));
        painel.setOpaque(false);

        JPanel painelCards = new JPanel(new GridLayout(1, 3, 15, 0));
        painelCards.setOpaque(false);
        painelCards.setPreferredSize(new Dimension(0, 100));

        lblValorReceita = CardIndicador(painelCards, "Receita Bruta", "R$ 0,00", new Color(46, 204, 113));
        lblValorIngressos = CardIndicador(painelCards, "Ingressos Emitidos", "0", new Color(53, 132, 228));
        lblValorPecas = CardIndicador(painelCards, "Eventos Realizados", "0 Peças", Color.WHITE);

        painel.add(painelCards, BorderLayout.NORTH);

        JPanel painelTabela = new JPanel(new BorderLayout(0, 15));
        painelTabela.setBackground(PANEL_COLOR);
        painelTabela.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel lblTituloTab = new JLabel("Detalhamento");
        lblTituloTab.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTituloTab.setForeground(Color.WHITE);
        painelTabela.add(lblTituloTab, BorderLayout.NORTH);

        String[] colunas = {"Data/Evento", "Ingressos", "Receita Bilheteria", "Status"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabelaRelatorio = new JTable(modeloTabela);
        JScrollPane scroll = new JScrollPane(tabelaRelatorio);
        scroll.getViewport().setBackground(PANEL_COLOR);

        painelTabela.add(scroll, BorderLayout.CENTER);

        painel.add(painelTabela, BorderLayout.CENTER);

        return painel;
    }

    private JComboBox<String> adicionarComboBox(JPanel container, String nomeLabel, String[] opcoes) {
        JLabel label = new JLabel(nomeLabel);
        label.setForeground(Color.LIGHT_GRAY);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        JComboBox<String> comboBox = new JComboBox<>(opcoes);
        comboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        comboBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        container.add(label);
        container.add(Box.createRigidArea(new Dimension(0, 5)));
        container.add(comboBox);
        container.add(Box.createRigidArea(new Dimension(0, 15)));

        return comboBox;
    }

    private JLabel CardIndicador(JPanel container, String titulo, String valorInicial, Color corValor) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(PANEL_COLOR);
        card.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setForeground(Color.GRAY);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblValor = new JLabel(valorInicial);
        lblValor.setForeground(corValor);
        lblValor.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblValor.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(lblTitulo);
        card.add(Box.createVerticalGlue());
        card.add(lblValor);

        container.add(card);

        return lblValor;
    }
}