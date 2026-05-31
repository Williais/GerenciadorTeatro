package br.edu.ifpb.teatro.view;

import br.edu.ifpb.teatro.dao.CentralDeInformacoes;
import br.edu.ifpb.teatro.view.components.Header;
import br.edu.ifpb.teatro.view.components.Sidebar;
import br.edu.ifpb.teatro.view.panes.*;

import javax.swing.*;
import java.awt.*;

public class TelaHome extends JFrame {
    private CentralDeInformacoes central;

    //algumas cores que vamos usar como padrão do nosso projeto
    public static final Color BG_COLOR = new Color(30, 30, 30);
    public static final Color PANEL_COLOR = new Color(45, 45, 45);
    public static final Color BORDER_COLOR = new Color(67, 67, 67);
    public static final Color ACCENT_COLOR = new Color(53, 132, 228);
    public static final Color TEXT_MAIN = new Color(230, 230, 230);
    public static final Color TEXT_MUTED = new Color(150, 150, 150);

    TelaHome(CentralDeInformacoes central){
        this.central = central;

        setTitle("Home - Gerenciador");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // full Screen
        setLayout(new BorderLayout());
        getContentPane().setBackground(BG_COLOR);

        // vamos usar a regra do SPA (Single Page Application)
        // isso significa que quando eu clicar em algum botão, apenas o que for necessario vai mudar
        CardLayout layout = new CardLayout();

        JPanel painelCentral = new JPanel(layout);
        painelCentral.add(new DashboardPanel(), "DASHBOARD");
        painelCentral.add(new RegrasPrecoPanel(central), "REGRAS");
        painelCentral.add(new Aluguel(central), "ALUGUEL");
        painelCentral.add(new Relatorio(central), "RELATORIO");
        painelCentral.add(new VendaIngresso(central), "VENDA");

        Sidebar menuLateral = new Sidebar(central, painelCentral, layout);
        this.add(menuLateral, BorderLayout.WEST);

        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(BG_COLOR);

        Header cabecalho = new Header(central);
        painel.add(cabecalho, BorderLayout.NORTH);



        painel.add(painelCentral, BorderLayout.CENTER);

        this.add(painel, BorderLayout.CENTER);
    }
}
