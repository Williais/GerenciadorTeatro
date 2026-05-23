package br.edu.ifpb.teatro.view.panes;

import javax.swing.*;
import java.awt.*;

public class VendaIngresso extends JPanel {
    public VendaIngresso(){
        setBackground(Color.ORANGE);

        JLabel lblTitle = new JLabel("Aq vai Ser a Venda de Ingressos");
        lblTitle.setFont(new Font("Poppins", Font.BOLD, 35));
        lblTitle.setForeground(Color.BLACK);
        this.add(lblTitle);
    }
}
