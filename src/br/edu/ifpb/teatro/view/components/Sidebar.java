package br.edu.ifpb.teatro.view.components;

import br.edu.ifpb.teatro.dao.CentralDeInformacoes;
import br.edu.ifpb.teatro.view.TelaLoginAdm;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import java.util.ArrayList;
import java.util.List;

import static br.edu.ifpb.teatro.view.TelaHome.*;
import static br.edu.ifpb.teatro.view.TelaHome.TEXT_MAIN;
import static br.edu.ifpb.teatro.view.TelaHome.ACCENT_COLOR;
import static br.edu.ifpb.teatro.view.TelaHome.PANEL_COLOR;

public class Sidebar extends JPanel {
    private List<JButton> botoesMenu = new ArrayList<>();

    public Sidebar(CentralDeInformacoes central, JPanel painelCentral, CardLayout layout){

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPreferredSize(new Dimension(210, 0));
        this.setBackground(PANEL_COLOR);
        this.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, BORDER_COLOR));

        JPanel logoPanel = new JPanel(new BorderLayout());
        logoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        logoPanel.setOpaque(false);
        logoPanel.setBorder(new EmptyBorder(20, 10, 20, 10));

        JLabel logoTitle = new JLabel("🎭 Gerenciador do Teatro");
        logoTitle.setFont(new Font("SansSerif", Font.BOLD, 15));
        logoTitle.setForeground(ACCENT_COLOR);

        JLabel subtitle = new JLabel("Sistema Gerenciador");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subtitle.setForeground(TEXT_MUTED);

        logoPanel.add(logoTitle, BorderLayout.CENTER);
        logoPanel.add(subtitle, BorderLayout.SOUTH);
        logoPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 80));

        this.add(logoPanel);
        this.add(Box.createRigidArea(new Dimension(0, 10)));

        JButton btnDashboard = createMenuButton("📊 Dashboard", true);
        JButton btnRegras = createMenuButton("💲 Regras de Preço", false);
        JButton btnAlugueis = createMenuButton("📅 Aluguéis e Peças", false);
        JButton btnVendas = createMenuButton("🎟️ Venda de Ingressos", false);
        JButton btnRelatorios = createMenuButton("📈 Relatórios", false);

        this.add(btnDashboard);
        this.add(btnRegras);
        this.add(btnAlugueis);
        this.add(btnVendas);
        this.add(btnRelatorios);

        botoesMenu.add(btnDashboard);
        botoesMenu.add(btnRegras);
        botoesMenu.add(btnAlugueis);
        botoesMenu.add(btnVendas);
        botoesMenu.add(btnRelatorios);

        this.add(Box.createVerticalGlue()); // faz com o que o botão sair fique para baixo

        JButton btnSair = createMenuButton("🚪 Sair do Sistema", false);
        btnSair.setForeground(new Color(255, 100, 100));
        this.add(btnSair);
        this.add(Box.createRigidArea(new Dimension(0, 20)));

        btnSair.addActionListener(e -> {
            int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja sair do sistema?", "Exclusão", JOptionPane.YES_NO_OPTION);

            if(resposta == JOptionPane.YES_OPTION){
                Window janela = SwingUtilities.getWindowAncestor(this); //aq é pq preciso saber quem é a tela que ta atualmente, considerando que um JPanel NÃO TEM .dispose()

                janela.dispose();

                TelaLoginAdm telaLogin = new TelaLoginAdm(central);
                telaLogin.setVisible(true);
            }
        });

        //é aq que o SPA é aplicado usando o .show
        btnDashboard.addActionListener(e -> {
            layout.show(painelCentral, "DASHBOARD");
            ativarBotao(btnDashboard);
        });

        btnRegras.addActionListener(e -> {
            layout.show(painelCentral, "REGRAS");
            ativarBotao(btnRegras);
        });

        btnAlugueis.addActionListener(e -> {
            layout.show(painelCentral, "ALUGUEL");
            ativarBotao(btnAlugueis);
        });

        btnVendas.addActionListener(e -> {
            layout.show(painelCentral, "VENDA");
            ativarBotao(btnVendas);
        });

        btnRelatorios.addActionListener(e -> {
            layout.show(painelCentral, "RELATORIO");
            ativarBotao(btnRelatorios);
        });
    }

    public JButton createMenuButton(String nome, boolean ativo){
        JButton btn = new JButton(nome);

        // vou usar operação ternario para mudar o estado do botão dependendo se ele ta ativo ou n
        btn.setFont(new Font("SansSerif", ativo ? Font.BOLD : Font.PLAIN, 12));
        btn.setForeground(ativo ? Color.WHITE : TEXT_MAIN);
        btn.setBackground(ativo ? ACCENT_COLOR : PANEL_COLOR);

        btn.setHorizontalAlignment(SwingConstants.LEFT); //alinha os botão totalmente a esquerda
        btn.setMaximumSize(new Dimension(Short.MAX_VALUE, 45)); //tamanho maximo
        btn.setBorder(new EmptyBorder(10, 15, 10, 10)); //padding
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // oropriedade do FlatLaf
        // remove bordas padrao e arredonda
        btn.putClientProperty("JButton.buttonType", "borderless");
        btn.putClientProperty("Component.arc", 15);

        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        return btn;
    }

    private void ativarBotao(JButton botaoClicado) {
        for (JButton btn : botoesMenu) {
            // se o botao atual for igual ao botao clicado, ele fica ativo
            boolean ativo = (btn == botaoClicado);

            btn.setFont(new Font("SansSerif", ativo ? Font.BOLD : Font.PLAIN, 12));
            btn.setForeground(ativo ? Color.WHITE : TEXT_MAIN);
            btn.setBackground(ativo ? ACCENT_COLOR : PANEL_COLOR);
        }
    }
}
