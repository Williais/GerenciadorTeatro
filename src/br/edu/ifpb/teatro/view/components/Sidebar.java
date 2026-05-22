package br.edu.ifpb.teatro.view.components;

import br.edu.ifpb.teatro.dao.CentralDeInformacoes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static br.edu.ifpb.teatro.view.TelaHome.*;
import static br.edu.ifpb.teatro.view.TelaHome.TEXT_MAIN;
import static br.edu.ifpb.teatro.view.TelaHome.ACCENT_COLOR;
import static br.edu.ifpb.teatro.view.TelaHome.PANEL_COLOR;

public class Sidebar extends JPanel {

    public Sidebar(CentralDeInformacoes central){

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

        this.add(createMenuButton("📊 Dashboard", true));
        this.add(createMenuButton("💲 Regras de Preço", false));
        this.add(createMenuButton("📅 Aluguéis e Peças", false));
        this.add(createMenuButton("🎟️ Venda de Ingressos", false));
        this.add(createMenuButton("📈 Relatórios", false));

        this.add(Box.createVerticalGlue()); // faz com o que o botão sair fique para baixo

        JButton btnSair = createMenuButton("🚪 Sair do Sistema", false);
        btnSair.setForeground(new Color(255, 100, 100));
        this.add(btnSair);
        this.add(Box.createRigidArea(new Dimension(0, 20)));
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
}
