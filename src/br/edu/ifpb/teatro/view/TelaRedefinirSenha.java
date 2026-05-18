package br.edu.ifpb.teatro.view;

import br.edu.ifpb.teatro.dao.CentralDeInformacoes;
import br.edu.ifpb.teatro.dao.Persistencia;
import br.edu.ifpb.teatro.model.ADM;
import br.edu.ifpb.teatro.security.ValidadorSenha;
import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TelaRedefinirSenha extends JFrame {
    private final CentralDeInformacoes central;

    public TelaRedefinirSenha(CentralDeInformacoes central){
        this.central = central;

        setTitle("Gerenciador de Teatro - Esqueci a Senha");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(900, 700);

        JPanel containerPrincipal = new JPanel(new GridBagLayout());
        containerPrincipal.setBackground(new Color(30, 30, 30));
        add(containerPrincipal);

        JPanel boxInterna = new JPanel();
        boxInterna.setLayout(new GridBagLayout());
        boxInterna.setPreferredSize(new Dimension(380, 480));
        boxInterna.setBackground(new Color(45, 45, 45));
        boxInterna.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc: 25;" +
                "background: #2D2D2D;" +
                "border: 0,0,0,0,shadow");
        containerPrincipal.add(boxInterna);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 30, 10, 30);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel lblTitulo = new JLabel("Recuperar Senha");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 26));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setForeground(new Color(220, 220, 220));
        lblTitulo.setBorder(new EmptyBorder(0, 0, 5, 0));
        gbc.gridy = 0;
        boxInterna.add(lblTitulo, gbc);

        JLabel lblSubtitulo = new JLabel("Digite sua Nova senha e Confirme ela");
        lblSubtitulo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblSubtitulo.setForeground(new Color(160, 160, 160));
        lblSubtitulo.setBorder(new EmptyBorder(0, 0, 30, 0));
        gbc.gridy = 1;
        boxInterna.add(lblSubtitulo, gbc);

        JLabel lblSenha = new JLabel("Senha");
        lblSenha.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblSenha.setForeground(new Color(180, 180, 180));
        gbc.insets = new Insets(5, 30, 0, 30);
        gbc.gridy = 2;
        boxInterna.add(lblSenha, gbc);

        JPasswordField txtSenha = new JPasswordField();
        txtSenha.setPreferredSize(new Dimension(0, 40));
        gbc.insets = new Insets(5, 30, 15, 30);
        gbc.gridy = 3;
        boxInterna.add(txtSenha, gbc);

        JLabel lblConfirmarSenha = new JLabel("Confirmar Senha");
        lblConfirmarSenha.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblConfirmarSenha.setForeground(new Color(180, 180, 180));
        gbc.insets = new Insets(5, 30, 0, 30);
        gbc.gridy = 4;
        boxInterna.add(lblConfirmarSenha, gbc);

        JPasswordField txtConfirmarSenha = new JPasswordField();
        txtConfirmarSenha.setPreferredSize(new Dimension(0, 40));
        gbc.insets = new Insets(5, 30, 15, 30);
        gbc.gridy = 5;
        boxInterna.add(txtConfirmarSenha, gbc);

        JButton btnSubmit = new JButton("Salvar Nova Senha");
        btnSubmit.setPreferredSize(new Dimension(0, 45));
        btnSubmit.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnSubmit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 30, 15, 30);
        boxInterna.add(btnSubmit, gbc);

        btnSubmit.addActionListener(e -> {
            String novaSenha = new String(txtSenha.getPassword());
            String novaConfirmarSenha = new String(txtConfirmarSenha.getPassword());

            if(!novaSenha.equals(novaConfirmarSenha)){
                JOptionPane.showMessageDialog(this, "Senhas nao Coicidem.", "Erro Fatal", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try{
                ValidadorSenha.validandoSenha(novaSenha);

                central.getAdministrador().setSenha(novaSenha);
                Persistencia p = new Persistencia();
                p.salvarCentral(central, "central.xml");
                JOptionPane.showMessageDialog(this, "Senha atualizada. Faça seu login.");
                this.dispose();

                TelaLoginAdm login = new TelaLoginAdm(central);
                login.setVisible(true);
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro Fatal", JOptionPane.ERROR_MESSAGE);
            }


        });
    }

}
