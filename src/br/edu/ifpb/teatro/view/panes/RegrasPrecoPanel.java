package br.edu.ifpb.teatro.view.panes;

import br.edu.ifpb.teatro.dao.CentralDeInformacoes;
import br.edu.ifpb.teatro.model.RegraDePreco;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.time.DayOfWeek;
import java.time.LocalTime;
import br.edu.ifpb.teatro.dao.Persistencia;

import static br.edu.ifpb.teatro.view.TelaHome.*;

public class RegrasPrecoPanel extends JPanel {
    private CentralDeInformacoes central;

    private JTextField txtNomeRegra;
    private JTextField txtValorBase;
    private JComboBox<String> cbDiaSemana;
    private JComboBox<String> cbTurno;
    private JComboBox<String> cbMes;
    private JTextField txtHoraInicio;
    private JTextField txtHoraFim;

    private JTable tabelaRegras;
    private DefaultTableModel modelo;

    public RegrasPrecoPanel(CentralDeInformacoes central) {
        this.central = central;
        this.setLayout(new BorderLayout(20, 0));
        this.setBackground(BG_COLOR);
        this.setBorder(new EmptyBorder(25, 25, 25, 25));

        this.add(criarPainelFormulario(), BorderLayout.WEST);
        this.add(criarPainelTabela(), BorderLayout.CENTER);
    }

    private JPanel criarPainelFormulario() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setPreferredSize(new Dimension(350, 0));
        painel.setBackground(PANEL_COLOR);
        painel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel wrapperTopo = new JPanel(new BorderLayout());
        wrapperTopo.setOpaque(false);

        JLabel lblTitulo = new JLabel("Nova Regra");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBorder(new EmptyBorder(0, 0, 15, 0));
        wrapperTopo.add(lblTitulo, BorderLayout.NORTH);

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setOpaque(false);

        txtNomeRegra = adicionarCampoTexto(form, "Nome de Identificação");
        txtValorBase = adicionarCampoTexto(form, "Valor por Hora");

        cbDiaSemana = adicionarComboBox(form, "Dia da Semana", new String[]{"Todos os Dias", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado", "Domingo"});
        cbTurno = adicionarComboBox(form, "Turno", new String[]{"Qualquer Turno", "Manhã (08-12)", "Tarde (13-18)", "Noite (19-23)"});
        cbMes = adicionarComboBox(form, "Mês Específico", new String[]{"Qualquer Mês", "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"});

        JPanel painelHoras = new JPanel(new GridLayout(1, 2, 10, 0));
        painelHoras.setOpaque(false);
        painelHoras.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        painelHoras.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel pnlHoraIn = new JPanel(new BorderLayout(0, 5));
        pnlHoraIn.setOpaque(false);
        JLabel lblHoraIn = new JLabel("Hora Início (HH:MM)");
        lblHoraIn.setForeground(Color.LIGHT_GRAY);
        pnlHoraIn.add(lblHoraIn, BorderLayout.NORTH);
        txtHoraInicio = new JTextField();
        pnlHoraIn.add(txtHoraInicio, BorderLayout.CENTER);

        JPanel pnlHoraFim = new JPanel(new BorderLayout(0, 5));
        pnlHoraFim.setOpaque(false);
        JLabel lblHoraFim = new JLabel("Hora Fim (HH:MM)");
        lblHoraFim.setForeground(Color.LIGHT_GRAY);
        pnlHoraFim.add(lblHoraFim, BorderLayout.NORTH);
        txtHoraFim = new JTextField();
        pnlHoraFim.add(txtHoraFim, BorderLayout.CENTER);

        painelHoras.add(pnlHoraIn);
        painelHoras.add(pnlHoraFim);

        form.add(painelHoras);

        wrapperTopo.add(form, BorderLayout.CENTER);
        painel.add(wrapperTopo, BorderLayout.NORTH);

        JPanel painelBotoes = new JPanel(new GridLayout(1, 2, 10, 0));
        painelBotoes.setOpaque(false);
        painelBotoes.setBorder(new EmptyBorder(15, 0, 0, 0));

        JButton btnLimpar = new JButton("Limpar");
        btnLimpar.setBackground(Color.GRAY);
        btnLimpar.setForeground(Color.WHITE);
        btnLimpar.setFocusPainted(false);
        btnLimpar.addActionListener(e -> {
            limparCampos();
        });

        JButton btnSalvar = new JButton("Salvar Regra");
        btnSalvar.setBackground(new Color(46, 204, 113));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFocusPainted(false);
        btnSalvar.addActionListener(e -> {

            float valor = Float.parseFloat(txtValorBase.getText().replace(",", "."));
            RegraDePreco novaRegra = new RegraDePreco(valor);

            String diaStr = (String) cbDiaSemana.getSelectedItem();
            novaRegra.setDiaDaSemana(converterDia(diaStr));

            int mesIndex = cbMes.getSelectedIndex();
            novaRegra.setMes(mesIndex == 0 ? null : mesIndex);

            String turno = (String) cbTurno.getSelectedItem();
            novaRegra.setTurno(turno.equals("Qualquer Turno") ? null : turno);

            String hIn = txtHoraInicio.getText().trim();
            String hFim = txtHoraFim.getText().trim();

            if (!hIn.isEmpty() && !hFim.isEmpty()) {
                novaRegra.setHoraInicio(LocalTime.parse(hIn));
                novaRegra.setHoraFim(LocalTime.parse(hFim));
            } else if (!hIn.isEmpty() || !hFim.isEmpty()) {
                throw new RuntimeException("Horário incompleto: preencha Início e Fim.");
            }

            central.getTodasAsRegras().add(novaRegra);
            Persistencia p = new Persistencia();
            p.salvarCentral(central, "central.xml");

            JOptionPane.showMessageDialog(this, "Regra salva com sucesso!");
            atualizarTabela();
            limparCampos();
        });



        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnSalvar);

        painel.add(painelBotoes, BorderLayout.SOUTH);

        return painel;
    }

    private JPanel criarPainelTabela() {
        JPanel painel = new JPanel(new BorderLayout(0, 15));
        painel.setOpaque(false);
        painel.setBorder(new EmptyBorder(0, 10, 0, 0));

        JLabel lblTitulo = new JLabel("Regras Cadastradas");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTitulo.setForeground(Color.WHITE);
        painel.add(lblTitulo, BorderLayout.NORTH);

        String[] colunas = {"ID", "Dia", "Turno", "Mês", "Valor (R$)"};
        modelo = new DefaultTableModel(colunas, 0);
        tabelaRegras = new JTable(modelo);
        atualizarTabela();
        JScrollPane scroll = new JScrollPane(tabelaRegras);
        scroll.getViewport().setBackground(PANEL_COLOR);
        painel.add(scroll, BorderLayout.CENTER);

        JPanel painelAcoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelAcoes.setOpaque(false);

        JButton btnEditar = new JButton("Editar Regra"); // falta fazer
        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.setBackground(new Color(200, 50, 50));
        btnExcluir.setForeground(Color.WHITE);

        btnExcluir.addActionListener(e -> {
            int linha = tabelaRegras.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione uma regra na tabela para excluir!");
                return;
            }

            // Pegando o ID que ta na primeira coluna indice 0
            long id = (long) modelo.getValueAt(linha, 0);

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Deseja excluir a regra " + id + "?",
                    "Confirmar Exclusão",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                central.getTodasAsRegras().removeIf(r -> r.getId() == id); //ondeestaeste metodo removeif como t afuncionando
                Persistencia p = new Persistencia();
                p.salvarCentral(central, "central.xml");
                atualizarTabela();

                JOptionPane.showMessageDialog(this, "Regra excluída com sucesso!");
            }
        });

        painelAcoes.add(btnEditar);
        painelAcoes.add(btnExcluir);

        painel.add(painelAcoes, BorderLayout.SOUTH);

        return painel;
    }


    private JTextField adicionarCampoTexto(JPanel container, String nomeLabel) {
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

    private JComboBox<String> adicionarComboBox(JPanel container, String nomeLabel, String[] opcoes) {
        JLabel label = new JLabel(nomeLabel);
        label.setForeground(Color.LIGHT_GRAY);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        JComboBox<String> comboBox = new JComboBox<>(opcoes);
        comboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        comboBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        container.add(label);
        container.add(Box.createRigidArea(new Dimension(0, 5)));
        container.add(comboBox);
        container.add(Box.createRigidArea(new Dimension(0, 15)));

        return comboBox;
    }
    private DayOfWeek converterDia(String dia) {
        switch (dia) {
            case "Segunda": return DayOfWeek.MONDAY;
            case "Terça":   return DayOfWeek.TUESDAY;
            case "Quarta":  return DayOfWeek.WEDNESDAY;
            case "Quinta":  return DayOfWeek.THURSDAY;
            case "Sexta":   return DayOfWeek.FRIDAY;
            case "Sábado":  return DayOfWeek.SATURDAY;
            case "Domingo": return DayOfWeek.SUNDAY;
            default: return null;
        }
    }
    private void limparCampos() {
        txtNomeRegra.setText("");
        txtValorBase.setText("");
        txtHoraInicio.setText("");
        txtHoraFim.setText("");
        cbDiaSemana.setSelectedIndex(0);
        cbTurno.setSelectedIndex(0);
        cbMes.setSelectedIndex(0);
    }
    void atualizarTabela() {
        modelo.setRowCount(0); //

        List<RegraDePreco> regras = central.getTodasAsRegras();

        // atualizacao das regras na tabela
        if (regras != null) {
            for (RegraDePreco r : regras) {
                Object[] linha = {
                        r.getId(),
                        r.getDiaDaSemana() == null ? "Todos" : r.getDiaDaSemana(),
                        r.getTurno() == null ? "Qualquer" : r.getTurno(),
                        r.getMes() == null ? "Todos" : r.getMes(),
                        String.format("%.2f", r.getValorHora())
                };
                modelo.addRow(linha);
            }
        }

    }}