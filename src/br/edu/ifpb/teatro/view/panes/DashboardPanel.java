package br.edu.ifpb.teatro.view.panes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

import static br.edu.ifpb.teatro.view.TelaHome.BG_COLOR;

public class DashboardPanel extends JPanel {

    private final Color BG_COLOR = new Color(30, 30, 30);
    private final Color PANEL_COLOR = new Color(45, 45, 45);
    private final Color TEXT_LIGHT = Color.WHITE;
    private final Color TEXT_MUTED = Color.GRAY;
    private final Color ACCENT_GREEN = new Color(46, 204, 113);

    public DashboardPanel() {
        this.setLayout(new BorderLayout(20, 20));
        this.setBackground(BG_COLOR);
        this.setBorder(new EmptyBorder(25, 25, 25, 25));

        JPanel painelCards = new JPanel(new GridLayout(1, 3, 20, 0));
        painelCards.setOpaque(false);
        painelCards.setPreferredSize(new Dimension(0, 110));

        painelCards.add(criarCard("Próxima peça", "Nome da Peça", "Hoje - 19h00", TEXT_LIGHT));
        painelCards.add(criarCard("Caixa do Dia", "R$ 0,00", "Total em ingressos", ACCENT_GREEN));
        painelCards.add(criarCard("Ocupação do Teatro", "Turno: Livre", "Status de aluguel", TEXT_LIGHT));

        JPanel topoWrapper = new JPanel(new BorderLayout(0, 25));
        topoWrapper.setOpaque(false);
        topoWrapper.add(painelCards, BorderLayout.CENTER);

        this.add(topoWrapper, BorderLayout.NORTH);

        JPanel painelCorpo = new JPanel(new BorderLayout(20, 0));
        painelCorpo.setOpaque(false);

        JPanel painelTabela = new JPanel(new BorderLayout(0, 15));
        painelTabela.setBackground(PANEL_COLOR);
        painelTabela.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel tabelaHeader = new JPanel(new BorderLayout());
        tabelaHeader.setOpaque(false);
        JLabel lblTabela = new JLabel("Propostas de Aluguéis Recentes");
        lblTabela.setForeground(TEXT_LIGHT);
        lblTabela.setFont(new Font("SansSerif", Font.BOLD, 16));

        JTextField txtPesquisa = new JTextField("Pesquisar Proposta...");
        txtPesquisa.setPreferredSize(new Dimension(200, 30));

        tabelaHeader.add(lblTabela, BorderLayout.WEST);
        tabelaHeader.add(txtPesquisa, BorderLayout.EAST);

        JTable tabelaMock = new JTable(new Object[][]{}, new String[]{"Locatário", "Peça", "Status", "Ação"});
        JScrollPane scrollTabela = new JScrollPane(tabelaMock);
        scrollTabela.getViewport().setBackground(PANEL_COLOR);

        painelTabela.add(tabelaHeader, BorderLayout.NORTH);
        painelTabela.add(scrollTabela, BorderLayout.CENTER);

        JPanel painelAvisos = new JPanel(new BorderLayout(0, 15));
        painelAvisos.setBackground(PANEL_COLOR);
        painelAvisos.setPreferredSize(new Dimension(300, 0));
        painelAvisos.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel lblAvisos = new JLabel("Pendências");
        lblAvisos.setForeground(TEXT_LIGHT);
        lblAvisos.setFont(new Font("SansSerif", Font.BOLD, 16));
        painelAvisos.add(lblAvisos, BorderLayout.NORTH);

        JPanel listaAvisos = new JPanel();
        listaAvisos.setLayout(new BoxLayout(listaAvisos, BoxLayout.Y_AXIS));
        listaAvisos.setOpaque(false);
        listaAvisos.add(criarItemAviso("Proposta pendente", "João Silva aguarda aprovação"));
        listaAvisos.add(Box.createRigidArea(new Dimension(0, 10)));
        listaAvisos.add(criarItemAviso("Aviso do Sistema", "O backup na nuvem foi atualizado"));

        painelAvisos.add(listaAvisos, BorderLayout.CENTER);

        painelCorpo.add(painelTabela, BorderLayout.CENTER);
        painelCorpo.add(painelAvisos, BorderLayout.EAST);

        this.add(painelCorpo, BorderLayout.CENTER);
    }

    private JPanel criarCard(String titulo, String valorPrincipal, String subtitulo, Color corDestaque) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(PANEL_COLOR);
        card.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setForeground(TEXT_MUTED);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 12));

        JLabel lblValor = new JLabel(valorPrincipal);
        lblValor.setForeground(corDestaque);
        lblValor.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblValor.setBorder(new EmptyBorder(10, 0, 10, 0));

        JLabel lblSub = new JLabel(subtitulo);
        lblSub.setForeground(TEXT_MUTED);
        lblSub.setFont(new Font("SansSerif", Font.PLAIN, 11));

        card.add(lblTitulo);
        card.add(lblValor);
        card.add(lblSub);

        return card;
    }

    private JPanel criarItemAviso(String titulo, String descricao) {
        JPanel item = new JPanel(new BorderLayout());
        item.setOpaque(false);
        item.setBorder(BorderFactory.createMatteBorder(0, 4, 0, 0, ACCENT_GREEN));

        JPanel interno = new JPanel(new GridLayout(2, 1));
        interno.setOpaque(false);
        interno.setBorder(new EmptyBorder(0, 10, 0, 0));

        JLabel lblTit = new JLabel(titulo);
        lblTit.setForeground(TEXT_LIGHT);
        lblTit.setFont(new Font("SansSerif", Font.BOLD, 12));

        JLabel lblDesc = new JLabel(descricao);
        lblDesc.setForeground(TEXT_MUTED);
        lblDesc.setFont(new Font("SansSerif", Font.PLAIN, 11));

        interno.add(lblTit);
        interno.add(lblDesc);
        item.add(interno, BorderLayout.CENTER);
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        return item;
    }
}
