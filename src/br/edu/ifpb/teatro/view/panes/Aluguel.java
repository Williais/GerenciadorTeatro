package br.edu.ifpb.teatro.view.panes;

import javax.swing.*;
import java.awt.*;

public class Aluguel extends JPanel {

    public Aluguel(){
        setBackground(Color.BLACK);

        JLabel lblTitle = new JLabel("Aq vai Ser As Propostas de Alugueis");
        lblTitle.setFont(new Font("Poppins", Font.BOLD, 35));
        lblTitle.setForeground(Color.WHITE);
        this.add(lblTitle);
    }
}
