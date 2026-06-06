package br.edu.ifpb.teatro.view.panes;

import br.edu.ifpb.teatro.dao.CentralDeInformacoes;
import br.edu.ifpb.teatro.dao.Persistencia;
import br.edu.ifpb.teatro.enums.StatusProposta;
import br.edu.ifpb.teatro.exception.HorarioForaDoTurnoPermitidoException;
import br.edu.ifpb.teatro.exception.HorarioIndisponivelException;
import br.edu.ifpb.teatro.model.Ingresso;
import br.edu.ifpb.teatro.model.PropostaDeAluguel;
import br.edu.ifpb.teatro.security.ValidadorTurno;
import br.edu.ifpb.teatro.util.CalculadoraPreco;
import br.edu.ifpb.teatro.util.GeradorDeContratos;
import br.edu.ifpb.teatro.util.Mensageiro;
import br.edu.ifpb.teatro.view.modals.ModalNovaProposta;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static br.edu.ifpb.teatro.view.TelaHome.*;

public class Aluguel extends JPanel {
    private CentralDeInformacoes central;

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JTextField txtBuscar;
    private JComboBox<String> cbFiltroMes;
    private JComboBox<String> cbFiltroStatus;
    private List<PropostaDeAluguel> propostasExibidas = new ArrayList<>();
    private PropostaDeAluguel propostaSelecionada = null;
    private JTextField txtDetalheArtista;
    private JTextField txtDetalhePeca;
    private JTextField txtDetalheData;
    private JTextField txtDetalheTurno;
    private JComboBox<String> cbDetalheStatus;
    private JButton btnSalvarAlteracoes;
    private JButton btnVerContrato;
    private JButton btnEstenderContrato;
    private JButton btnEncerrarContrato;

    public Aluguel(CentralDeInformacoes central) {
        this.central = central;
        this.setLayout(new BorderLayout(20, 0));
        this.setBackground(BG_COLOR);
        this.setBorder(new EmptyBorder(25, 25, 25, 25));

        this.add(PainelEsquerdo(), BorderLayout.CENTER);
        this.add(PainelDireito(), BorderLayout.EAST);

        configurarEventosTabela();
        configurarFiltros();
        atualizarTabela();
    }

    private void configurarFiltros() {
        cbFiltroMes.addActionListener(e -> atualizarTabela());
        cbFiltroStatus.addActionListener(e -> atualizarTabela());

        txtBuscar.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { atualizarTabela(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { atualizarTabela(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { atualizarTabela(); }
        });
    }

    private JPanel PainelEsquerdo() {
        JPanel painel = new JPanel(new BorderLayout(0, 15));
        painel.setOpaque(false);

        // Barra de Filtros (Topo)
        JPanel painelFiltros = new JPanel(new GridLayout(1, 3, 10, 0));
        painelFiltros.setOpaque(false);
        painelFiltros.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        txtBuscar = new JTextField();
        txtBuscar.putClientProperty("JTextField.placeholderText", "Buscar artista ou peça...");

        cbFiltroMes = new JComboBox<>(new String[]{"Todos os Meses", "Jan/2026", "Fev/2026", "Mar/2026", "Abr/2026", "Mai/2026", "Jun/2026"});
        cbFiltroStatus = new JComboBox<>(new String[]{"Todos os Status", "Em Contratação", "Contratado", "Encerrado"});

        painelFiltros.add(txtBuscar);
        painelFiltros.add(cbFiltroMes);
        painelFiltros.add(cbFiltroStatus);

        String[] colunas = {"Data", "Turno", "Evento", "Artista", "Status"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabela = new JTable(modeloTabela);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.getViewport().setBackground(PANEL_COLOR);

        painel.add(painelFiltros, BorderLayout.NORTH);
        painel.add(scroll, BorderLayout.CENTER);

        return painel;
    }

    private JPanel PainelDireito() {
        JPanel painel = new JPanel(new BorderLayout(0, 20));
        painel.setPreferredSize(new Dimension(350, 0));
        painel.setBackground(PANEL_COLOR);
        painel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.setOpaque(false);
        JLabel lblTitulo = new JLabel("Detalhes do evento");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);

        JButton btnNovo = new JButton("+ Novo");
        btnNovo.setBackground(new Color(53, 132, 228));
        btnNovo.setForeground(Color.WHITE);
        btnNovo.setFocusPainted(false);

        btnNovo.addActionListener(e -> {
            Window janelaPai = SwingUtilities.getWindowAncestor(this);
            ModalNovaProposta modal = new ModalNovaProposta(janelaPai, central);
            modal.setVisible(true);
            atualizarTabela();
        });

        painelTopo.add(lblTitulo, BorderLayout.WEST);
        painelTopo.add(btnNovo, BorderLayout.EAST);

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setOpaque(false);

        txtDetalheArtista = adicionarCampoLeitura(form, "Artista responsável");
        txtDetalhePeca = adicionarCampoLeitura(form, "Nome da peça");

        JPanel painelDuplo = new JPanel(new GridLayout(1, 2, 10, 0));
        painelDuplo.setOpaque(false);
        painelDuplo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        JPanel pnlData = new JPanel(new BorderLayout());
        pnlData.setOpaque(false);
        pnlData.add(new JLabel("Data do evento"), BorderLayout.NORTH);
        txtDetalheData = new JTextField();
        txtDetalheData.setEditable(false);
        pnlData.add(txtDetalheData, BorderLayout.CENTER);

        JPanel pnlTurno = new JPanel(new BorderLayout());
        pnlTurno.setOpaque(false);
        pnlTurno.add(new JLabel("Turno"), BorderLayout.NORTH);
        txtDetalheTurno = new JTextField();
        txtDetalheTurno.setEditable(false);
        pnlTurno.add(txtDetalheTurno, BorderLayout.CENTER);

        painelDuplo.add(pnlData);
        painelDuplo.add(pnlTurno);

        form.add(painelDuplo);
        form.add(Box.createRigidArea(new Dimension(0, 15)));

        JLabel lblStatus = new JLabel("Status da Contratação");
        lblStatus.setForeground(Color.LIGHT_GRAY);
        cbDetalheStatus = new JComboBox<>(new String[]{"EM_CONTRATACAO", "CONTRATADO", "ENCERRADO"});
        cbDetalheStatus.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        cbDetalheStatus.setEnabled(false);

        form.add(lblStatus);
        form.add(cbDetalheStatus);

        painel.add(painelTopo, BorderLayout.NORTH);
        painel.add(form, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new GridLayout(4, 1, 0, 10));
        painelBotoes.setOpaque(false);

        btnVerContrato = new JButton("Ver Contrato");
        btnVerContrato.setBackground(Color.GRAY);
        btnVerContrato.setFont(new Font("Poppins", Font.BOLD, 11));
        btnVerContrato.setForeground(Color.WHITE);
        btnVerContrato.setEnabled(false);

        btnSalvarAlteracoes = new JButton("Salvar Alterações");
        btnSalvarAlteracoes.setBackground(new Color(53, 132, 228));
        btnSalvarAlteracoes.setFont(new Font("Poppins", Font.BOLD, 11));
        btnSalvarAlteracoes.setForeground(Color.WHITE);
        btnSalvarAlteracoes.setEnabled(false);

        btnEstenderContrato = new JButton("Estender Contrato");
        btnEstenderContrato.setBackground(new Color(46, 204, 113));
        btnEstenderContrato.setFont(new Font("Poppins", Font.BOLD, 11));
        btnEstenderContrato.setForeground(Color.WHITE);
        btnEstenderContrato.setEnabled(false);

        btnEncerrarContrato = new JButton("Encerrar Contrato");
        btnEncerrarContrato.setFont(new Font("Poppins", Font.BOLD, 11));
        btnEncerrarContrato.setBackground(Color.RED);
        btnEncerrarContrato.setForeground(Color.WHITE);
        btnEncerrarContrato.setEnabled(false);

        btnSalvarAlteracoes.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (propostaSelecionada != null) {

                    String novoStatusStr = cbDetalheStatus.getSelectedItem().toString();

                    StatusProposta novoStatus = StatusProposta.valueOf(novoStatusStr);

                    propostaSelecionada.setStatus(novoStatus);

                    Persistencia persistencia = new Persistencia();
                    persistencia.salvarCentral(central, "central.xml");

                    JOptionPane.showMessageDialog(null, "Status atualizado com sucesso!");
                    atualizarTabela();
                }
            }
        });

        btnVerContrato.addActionListener(e -> {
            if (propostaSelecionada != null){
                GeradorDeContratos.obterContrato(propostaSelecionada.getId(), central);
            }

            int resposta = JOptionPane.showConfirmDialog(null, "Você deseja enviar para o email do artista?", "Confirmação", JOptionPane.YES_NO_OPTION);

            if (resposta == JOptionPane.YES_OPTION){
                try{

                Mensageiro.enviarContrato(propostaSelecionada.getLocatario().getEmail(), "contrato.pdf");
                } catch (Exception ex) { // precisa alterar essa exceção aq pedro
                    throw new RuntimeException(ex);
                }
            }
        });

        btnEstenderContrato.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (propostaSelecionada == null) {
                    return;
                }
                String entradaData = JOptionPane.showInputDialog(null, "Digite a nova data adicional (DD/MM/AAAA):");

                if (entradaData == null || entradaData.isEmpty()){
                    return;
                }


                DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate dataConvertida =  LocalDate.parse(entradaData, formato);

                LocalTime horaI = propostaSelecionada.getHoraInicioLocacao();
                LocalTime horaF = propostaSelecionada.getHoraFimLocacao();


                try{
                    central.validarHorarioLocacao(dataConvertida, horaI, horaF);
                } catch (HorarioForaDoTurnoPermitidoException | HorarioIndisponivelException ex) {
                    JOptionPane.showMessageDialog(null, "Não foi possível estender: " + ex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }


                LocalDateTime inicioVerdadeiro = LocalDateTime.of(dataConvertida, horaI);
                LocalDateTime finalVerdadeiro = LocalDateTime.of(dataConvertida, horaF);
                LocalDateTime horaAtual = inicioVerdadeiro;
                float valorExtra = 0.0f;

                while (horaAtual.isBefore(finalVerdadeiro)){
                    float valorDaCalculadora = CalculadoraPreco.recuperarPrecoVigente(central.getTodasAsRegras(), horaAtual);
                    valorExtra += valorDaCalculadora;
                    horaAtual = horaAtual.plusHours(1);
                }

                propostaSelecionada.setValorTotalDoAluguel(propostaSelecionada.getValorTotalDoAluguel() + valorExtra);
                propostaSelecionada.setStatus(StatusProposta.CONTRATADO_COM_ALTERACAO);
                propostaSelecionada.setDataEstendida(dataConvertida);

                Persistencia p = new Persistencia();
                p.salvarCentral(central, "central.xml");

                JOptionPane.showMessageDialog(null, "Proposta Atualizada");


                try{
                    GeradorDeContratos.obterContrato(propostaSelecionada.getId(), central);

                    JDialog dialogCarregando = new JDialog();
                    dialogCarregando.setTitle("Aguarde");
                    dialogCarregando.setModal(true);
                    dialogCarregando.setSize(250, 100);
                    dialogCarregando.setLocationRelativeTo(null);
                    dialogCarregando.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

                    JPanel painelAguarde = new JPanel(new BorderLayout());
                    painelAguarde.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
                    painelAguarde.add(new JLabel("Enviando Nova Proposta por email...", SwingConstants.CENTER), BorderLayout.CENTER);
                    dialogCarregando.add(painelAguarde);

                    SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

                        protected Void doInBackground() throws Exception {

                            Mensageiro.enviarIngresso(propostaSelecionada.getLocatario().getEmail(), "contrato.pdf");
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
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Não foi possível enviar: " + ex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
                }

                atualizarTabela();
            }
        });

        btnEncerrarContrato.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (propostaSelecionada == null) {
                    return;
                }

                LocalDate hoje = LocalDate.now();

                List<Ingresso> ingressosVendidos = central.gerarListaDeIngressos(propostaSelecionada.getId());

                LocalDate ultimaDataDaPeca = propostaSelecionada.getDataEvento();
                if (propostaSelecionada.getDataEstendida() != null) {
                    if (propostaSelecionada.getDataEstendida().isAfter(ultimaDataDaPeca)) {
                        ultimaDataDaPeca = propostaSelecionada.getDataEstendida();
                    }
                }

                boolean eventoVaiAcontecer = ultimaDataDaPeca.isAfter(hoje) || ultimaDataDaPeca.isEqual(hoje);
                boolean temIngressoVendido = ingressosVendidos != null && !ingressosVendidos.isEmpty();

                if (eventoVaiAcontecer && temIngressoVendido) {
                    JOptionPane.showMessageDialog(null,
                            "existem ingressos vendidos para datas deste evento. O contrato nao pode ser encerrado.",
                            "Cancelamento Bloqueado",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                float receitaBilheteria = 0.0f;

                for (Ingresso ingresso : ingressosVendidos){
                    receitaBilheteria += ingresso.getQuantidade() * ingresso.getValorIngresso();
                }

                float aluguel = propostaSelecionada.getValorTotalDoAluguel();

                float saldoFinal = receitaBilheteria - aluguel;


                String mensagem = String.format(
                        "Resumo do Encerramento:\n\nArrecadação Bilheteria: R$ %.2f\n" +
                                "Custo do Aluguel: R$ %.2f\nSaldo Líquido: R$ %.2f\n\n" +
                                "Deseja confirmar o encerramento definitivo?",
                        receitaBilheteria,
                        propostaSelecionada.getValorTotalDoAluguel(),
                        saldoFinal
                );

                int resposta = JOptionPane.showConfirmDialog(null, mensagem, "Confirmar Encerramento", JOptionPane.YES_NO_OPTION);

                if (resposta == JOptionPane.YES_OPTION) {
                    propostaSelecionada.setStatus(StatusProposta.ENCERRADO);

                    Persistencia p = new Persistencia();
                    p.salvarCentral(central, "central.xml");

                    JOptionPane.showMessageDialog(null, "Contrato Encerrao");
                    atualizarTabela();
                }

            }
        });

        painelBotoes.add(btnVerContrato);
        painelBotoes.add(btnSalvarAlteracoes);
        painelBotoes.add(btnEstenderContrato);
        painelBotoes.add(btnEncerrarContrato);

        painel.add(painelBotoes, BorderLayout.SOUTH);

        return painel;
    }

    public void atualizarTabela() {
        modeloTabela.setRowCount(0);
        propostasExibidas.clear();
        String termoBusca = txtBuscar.getText().toLowerCase();
        int mesSelecionado = cbFiltroMes.getSelectedIndex();
        String statusSelecionado = cbFiltroStatus.getSelectedItem().toString();

        for (PropostaDeAluguel p : central.getTodasAsPropostas()) {
            boolean passaBusca = termoBusca.isEmpty() ||
                    p.getNomeDaPeca().toLowerCase().contains(termoBusca) ||
                    p.getLocatario().getNome().toLowerCase().contains(termoBusca);

            boolean passaMes = (mesSelecionado == 0) || (p.getDataEvento().getMonthValue() == mesSelecionado);

            boolean passaStatus = true;
            if (!statusSelecionado.equals("Todos os Status")) {
                String statusDaProposta = p.getStatus().name();
                if (statusSelecionado.equals("Em Contratação") && !statusDaProposta.equals("EM_CONTRATACAO")) passaStatus = false;
                if (statusSelecionado.equals("Contratado") && !statusDaProposta.equals("CONTRATADO")) passaStatus = false;
                if (statusSelecionado.equals("Encerrado") && !statusDaProposta.equals("ENCERRADO")) passaStatus = false;
            }

            if (passaBusca && passaMes && passaStatus) {
                propostasExibidas.add(p);

                String turno = ValidadorTurno.validarTurno(p.getHoraInicioLocacao());

                String data = p.getDataEvento().toString();
                if (p.getDataEstendida() != null) {
                    data += " (+ " + p.getDataEstendida().toString() + ")";
                }

                Object[] linha = {data, turno, p.getNomeDaPeca(), p.getLocatario().getNome(), p.getStatus().name()};
                modeloTabela.addRow(linha);
            }
        }
    }

    private void configurarEventosTabela() {
        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabela.getSelectedRow() != -1) {
                int linhaSelecionada = tabela.getSelectedRow();
                propostaSelecionada = propostasExibidas.get(linhaSelecionada);
                preencherDetalhes(propostaSelecionada);
            }
        });
    }

    private void preencherDetalhes(PropostaDeAluguel p) {
        boolean ativo = p.getStatus() == StatusProposta.CONTRATADO_COM_ALTERACAO || p.getStatus() == StatusProposta.CONTRATADO;
        txtDetalheArtista.setText(p.getLocatario().getNome());
        txtDetalhePeca.setText(p.getNomeDaPeca());

        if (p.getDataEstendida() != null) {
            txtDetalheData.setText(p.getDataEvento().toString() + " e " + p.getDataEstendida().toString());
        } else {
            txtDetalheData.setText(p.getDataEvento().toString());
        }

        String turno = ValidadorTurno.validarTurno(p.getHoraInicioLocacao());

        txtDetalheTurno.setText(turno);

        cbDetalheStatus.setSelectedItem(p.getStatus().name());

        cbDetalheStatus.setEnabled(true);
        btnSalvarAlteracoes.setEnabled(true);
        btnVerContrato.setEnabled(ativo);
        btnEstenderContrato.setEnabled(ativo);
        btnEncerrarContrato.setEnabled(ativo);
    }

    private JTextField adicionarCampoLeitura(JPanel container, String titulo) {
        JLabel lbl = new JLabel(titulo);
        lbl.setForeground(Color.LIGHT_GRAY);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField txt = new JTextField();
        txt.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        txt.setAlignmentX(Component.LEFT_ALIGNMENT);
        txt.setEditable(false);

        container.add(lbl);
        container.add(txt);
        container.add(Box.createRigidArea(new Dimension(0, 10)));
        return txt;
    }
}