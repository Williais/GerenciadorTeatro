package br.edu.ifpb.teatro.view.panes;

import javax.swing.*;
import java.awt.*;

public class Relatorio extends JPanel {
    public Relatorio(){
        setBackground(Color.MAGENTA);

        JLabel lblTitle = new JLabel("Aq vai Ser os Relatorios");
        lblTitle.setFont(new Font("Poppins", Font.BOLD, 35));
        lblTitle.setForeground(Color.BLACK);
        this.add(lblTitle);
    }
}
