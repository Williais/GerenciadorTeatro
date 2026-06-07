package br.edu.ifpb.teatro.view.panes;

import br.edu.ifpb.teatro.dao.CentralDeInformacoes;
import br.edu.ifpb.teatro.enums.StatusProposta;
import br.edu.ifpb.teatro.model.Ingresso;
import br.edu.ifpb.teatro.model.PropostaDeAluguel;
import br.edu.ifpb.teatro.security.ValidadorTurno;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static br.edu.ifpb.teatro.enums.StatusProposta.EM_CONTRATACAO;

public class DashboardPanel extends JPanel {

    private final Color BG_COLOR = new Color(30, 30, 30);
    private final Color PANEL_COLOR = new Color(45, 45, 45);
    private final Color TEXT_LIGHT = Color.WHITE;
    private final Color TEXT_MUTED = Color.GRAY;
    private final Color ACCENT_GREEN = new Color(46, 204, 113);

    private CentralDeInformacoes central;

    private JLabel lblValorCaixa;
    private JLabel lblProximaPecaNome;
    private JLabel lblProximaPecaSub;
    private JLabel lblOcupacaoValor;
    private JPanel listaAvisos;
    private DefaultTableModel modeloTabelaRecentes;
    private JTextField txtPesquisa;

    public DashboardPanel(CentralDeInformacoes central) {
        this.central = central;
        this.setLayout(new BorderLayout(20, 20));
        this.setBackground(BG_COLOR);
        this.setBorder(new EmptyBorder(25, 25, 25, 25));

        JPanel painelCards = new JPanel(new GridLayout(1, 3, 20, 0));
        painelCards.setOpaque(false);
        painelCards.setPreferredSize(new Dimension(0, 110));

        painelCards.add(CardProximaPeca());
        painelCards.add(CardCaixa());
        painelCards.add(CardOcupacao());

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

        txtPesquisa = new JTextField();
        txtPesquisa.putClientProperty("JTextField.placeholderText", "Pesquisar Proposta...");
        txtPesquisa.setPreferredSize(new Dimension(200, 30));
        txtPesquisa.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { atualizarDashboard(); }
            public void removeUpdate(DocumentEvent e) { atualizarDashboard(); }
            public void changedUpdate(DocumentEvent e) { atualizarDashboard(); }
        });

        tabelaHeader.add(lblTabela, BorderLayout.WEST);
        tabelaHeader.add(txtPesquisa, BorderLayout.EAST);

        modeloTabelaRecentes = new DefaultTableModel(new Object[][]{}, new String[]{"Locatário", "Peça", "Status", "Data"});
        JTable tabelaRecentes = new JTable(modeloTabelaRecentes);
        JScrollPane scrollTabela = new JScrollPane(tabelaRecentes);
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

        listaAvisos = new JPanel();
        listaAvisos.setLayout(new BoxLayout(listaAvisos, BoxLayout.Y_AXIS));
        listaAvisos.setOpaque(false);
        listaAvisos.add(ItemAviso("Proposta pendente", "João Silva aguarda aprovação"));
        listaAvisos.add(Box.createRigidArea(new Dimension(0, 10)));
        listaAvisos.add(ItemAviso("Aviso do Sistema", "O backup na nuvem foi atualizado"));

        painelAvisos.add(listaAvisos, BorderLayout.CENTER);

        painelCorpo.add(painelTabela, BorderLayout.CENTER);
        painelCorpo.add(painelAvisos, BorderLayout.EAST);

        this.add(painelCorpo, BorderLayout.CENTER);

        this.addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent e) {
                atualizarDashboard();
            }
        });
        atualizarDashboard();
    }

    private JPanel Card(String titulo, String valorPrincipal, String subtitulo, Color corDestaque) {
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

    private JPanel CardProximaPeca() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(PANEL_COLOR);
        card.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel lblTitulo = new JLabel("Próxima peça");
        lblTitulo.setForeground(TEXT_MUTED);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 12));

        lblProximaPecaNome = new JLabel("Nome da Peça");
        lblProximaPecaNome.setForeground(TEXT_LIGHT);
        lblProximaPecaNome.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblProximaPecaNome.setBorder(new EmptyBorder(10, 0, 10, 0));

        lblProximaPecaSub = new JLabel("Hoje - 19h00");
        lblProximaPecaSub.setForeground(TEXT_MUTED);
        lblProximaPecaSub.setFont(new Font("SansSerif", Font.PLAIN, 11));

        card.add(lblTitulo);
        card.add(lblProximaPecaNome);
        card.add(lblProximaPecaSub);
        return card;
    }

    private JPanel CardCaixa() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(PANEL_COLOR);
        card.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel lblTitulo = new JLabel("Caixa do Dia");
        lblTitulo.setForeground(TEXT_MUTED);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 12));

        lblValorCaixa = new JLabel("R$ 0,00");
        lblValorCaixa.setForeground(ACCENT_GREEN);
        lblValorCaixa.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblValorCaixa.setBorder(new EmptyBorder(10, 0, 10, 0));

        JLabel lblSub = new JLabel("Total em ingressos");
        lblSub.setForeground(TEXT_MUTED);
        lblSub.setFont(new Font("SansSerif", Font.PLAIN, 11));

        card.add(lblTitulo);
        card.add(lblValorCaixa);
        card.add(lblSub);
        return card;
    }

    private JPanel CardOcupacao() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(PANEL_COLOR);
        card.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel lblTitulo = new JLabel("Ocupação do Teatro");
        lblTitulo.setForeground(TEXT_MUTED);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 12));

        lblOcupacaoValor = new JLabel("Turno: Livre");
        lblOcupacaoValor.setForeground(TEXT_LIGHT);
        lblOcupacaoValor.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblOcupacaoValor.setBorder(new EmptyBorder(10, 0, 10, 0));

        JLabel lblSub = new JLabel("Status de aluguel");
        lblSub.setForeground(TEXT_MUTED);
        lblSub.setFont(new Font("SansSerif", Font.PLAIN, 11));

        card.add(lblTitulo);
        card.add(lblOcupacaoValor);
        card.add(lblSub);
        return card;
    }

    private JPanel ItemAviso(String titulo, String descricao) {
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

    public void atualizarDashboard(){
        float totalCaixa = 0.0f;

        for(Ingresso i : central.getIngresso()){
            totalCaixa += i.getQuantidade() * i.getValorIngresso();
        }
        lblValorCaixa.setText(String.format("R$ %.2f", totalCaixa));

        listaAvisos.removeAll();

        for (PropostaDeAluguel p :central.getTodasAsPropostas()){
            if ((p.getStatus() == EM_CONTRATACAO)){
                listaAvisos.add(ItemAviso("Aprovação Pendente", p.getNomeDaPeca()));
            }

        }
        listaAvisos.revalidate();
        listaAvisos.repaint();

        LocalDate hoje = LocalDate.now();
        PropostaDeAluguel proximaPeca = null;
        long menorDiferencaDeDias = Long.MAX_VALUE;

        for (PropostaDeAluguel p : central.getTodasAsPropostas()) {
            boolean ativo = p.getStatus() == StatusProposta.CONTRATADO || p.getStatus() == StatusProposta.CONTRATADO_COM_ALTERACAO;

            if (ativo && (p.getDataEvento().isEqual(hoje) || p.getDataEvento().isAfter(hoje))) {
                long diasAteOEvento = ChronoUnit.DAYS.between(hoje, p.getDataEvento());

                if (diasAteOEvento < menorDiferencaDeDias) {
                    menorDiferencaDeDias = diasAteOEvento;
                    proximaPeca = p;
                }
            }
        }

        if (proximaPeca != null){
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataParaUsar = null;

            if (proximaPeca.getDataEstendida() == null){
                dataParaUsar = proximaPeca.getDataEvento();
            }else {
                dataParaUsar = proximaPeca.getDataEstendida();
            }

            lblProximaPecaNome.setText(proximaPeca.getNomeDaPeca());

            lblProximaPecaSub.setText("Data: " + dataParaUsar.format(formato));

            String turno = ValidadorTurno.validarTurno(proximaPeca.getHoraInicioLocacao());
            lblOcupacaoValor.setText("Turno da " + turno);


        }else{
            lblProximaPecaNome.setText("Nenhuma peça");
            lblOcupacaoValor.setText("Turno: Livre");
            lblProximaPecaSub.setText("NEnhuma peça prevista");
        }


        modeloTabelaRecentes.setRowCount(0);
        String termoBusca = txtPesquisa.getText().toLowerCase();

        int contador = 0;
        for (int i = central.getTodasAsPropostas().size() - 1; i >= 0; i--) {
            if (contador >= 10) break;

            PropostaDeAluguel p = central.getTodasAsPropostas().get(i);

            boolean passaBusca = termoBusca.isEmpty() ||
                    p.getNomeDaPeca().toLowerCase().contains(termoBusca) ||
                    p.getLocatario().getNome().toLowerCase().contains(termoBusca);

            if (passaBusca) {
                Object[] linha = {
                        p.getLocatario().getNome(),
                        p.getNomeDaPeca(),
                        p.getStatus().name(),
                        p.getDataEvento().toString()
                };
                modeloTabelaRecentes.addRow(linha);
                contador++;
            }
        }
    }
}
