package br.edu.ifpb.teatro.view.panes;

import javax.swing.*;

import java.awt.*;

import static br.edu.ifpb.teatro.view.TelaHome.BG_COLOR;

public class DashboardPanel extends JPanel {

    public DashboardPanel() {
        setBackground(Color.PINK);

        JLabel lblTitle = new JLabel("Aq vai Ser o DashBoard");
        lblTitle.setFont(new Font("Poppins", Font.BOLD, 35));
        lblTitle.setForeground(Color.BLACK);
        this.add(lblTitle);
    }
}
