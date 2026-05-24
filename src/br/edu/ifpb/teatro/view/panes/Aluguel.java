package br.edu.ifpb.teatro.view.panes;

import br.edu.ifpb.teatro.dao.CentralDeInformacoes;
import br.edu.ifpb.teatro.view.modals.ModalNovaProposta;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static br.edu.ifpb.teatro.view.TelaHome.*;

public class Aluguel extends JPanel {
    private CentralDeInformacoes central;

    public Aluguel(CentralDeInformacoes central){
        this.central = central;
        this.setLayout(new BorderLayout(0, 20));
        this.setBackground(BG_COLOR);
        this.setBorder(new EmptyBorder(25, 25, 25, 25));

        this.add(criarBarraSuperior(), BorderLayout.NORTH);
        this.add(criarPainelTabela(), BorderLayout.CENTER);
    }

    private JPanel criarBarraSuperior() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.X_AXIS));
        painel.setOpaque(false);

        JLabel lblTitulo = new JLabel("Propostas de Aluguéis");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);

        JButton btnNovo = new JButton("+ Novo");
        btnNovo.setBackground(ACCENT_COLOR);
        btnNovo.setForeground(Color.WHITE);
        btnNovo.setFocusPainted(false);
        btnNovo.putClientProperty("JButton.buttonType", "borderless");
        btnNovo.putClientProperty("Component.arc", 15);

        painel.add(lblTitulo);
        painel.add(Box.createHorizontalGlue()); //título pra esquerda e o botão pra direita
        painel.add(btnNovo);

        btnNovo.addActionListener(e -> {
            Window janelaPai = SwingUtilities.getWindowAncestor(this);

            ModalNovaProposta modal = new ModalNovaProposta(janelaPai, central);
            modal.setVisible(true);
        });

        return painel;
    }

    private JPanel criarPainelTabela() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(PANEL_COLOR);

        // Dados estáticos temporqrio
        String[] colunas = {"Data", "Turno", "Evento", "Artista", "Status"};
        Object[][] dados = {
                {"20/06/2026", "Noite", "O Auto da Compadecida", "Cia Suassuna", "Contratado"},
                {"25/06/2026", "Tarde", "Romeu e Julieta", "Grupo Galpão", "Em Contratação"}
        };

        JTable tabela = new JTable(dados, colunas);
        JScrollPane scroll = new JScrollPane(tabela);
        painel.add(scroll, BorderLayout.CENTER);

        return painel;
    }
}