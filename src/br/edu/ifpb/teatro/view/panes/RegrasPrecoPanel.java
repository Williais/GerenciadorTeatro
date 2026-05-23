package br.edu.ifpb.teatro.view.panes;

import javax.swing.*;

import java.awt.*;

import static br.edu.ifpb.teatro.view.TelaHome.BG_COLOR;

public class RegrasPrecoPanel extends JPanel {
    public RegrasPrecoPanel(){
        setBackground(Color.cyan);

        JLabel lblTitle = new JLabel("Aq vai Ser a Regra dos Preco");
        lblTitle.setFont(new Font("Poppins", Font.BOLD, 35));
        lblTitle.setForeground(Color.BLACK);
        this.add(lblTitle);
    }
}
