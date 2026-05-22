package br.edu.ifpb.teatro.view.components;

import br.edu.ifpb.teatro.dao.CentralDeInformacoes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static br.edu.ifpb.teatro.view.TelaHome.BG_COLOR;
import static br.edu.ifpb.teatro.view.TelaHome.TEXT_MUTED;

public class Header extends JPanel {


    public Header(CentralDeInformacoes central){

        this.setLayout(new BorderLayout());
        this.setBackground(BG_COLOR);
        this.setBorder(new EmptyBorder(25, 25, 20, 25));

        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setOpaque(false);

        JLabel title = new JLabel("Bem-vindo, " + central.getAdministrador().getNome());
        title.setFont(new Font("Poppins", Font.BOLD, 22));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Visão geral do teatro hoje.");
        subtitle.setFont(new Font("Poppins", Font.PLAIN, 14));
        subtitle.setForeground(TEXT_MUTED);

        textPanel.add(title);
        textPanel.add(subtitle);
        this.add(textPanel, BorderLayout.WEST);
    }
}
