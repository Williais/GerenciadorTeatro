package br.edu.ifpb.teatro.view;

import br.edu.ifpb.teatro.dao.CentralDeInformacoes;
import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TelaCodigo extends JFrame {
    private CentralDeInformacoes centra;
    private String codigoVerdadeiro;

    public TelaCodigo(CentralDeInformacoes central, String codigoGerado){
        this.centra = central;
        this.codigoVerdadeiro = codigoGerado;

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

        JLabel lblSubtitulo = new JLabel("Digite o codigo que você recebeu no seu Email");
        lblSubtitulo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblSubtitulo.setForeground(new Color(160, 160, 160));
        lblSubtitulo.setBorder(new EmptyBorder(0, 0, 30, 0));
        gbc.gridy = 1;
        boxInterna.add(lblSubtitulo, gbc);

        JLabel lblCodigo = new JLabel("Seu Codigo");
        lblCodigo.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblCodigo.setForeground(new Color(180, 180, 180));
        gbc.insets = new Insets(5, 30, 0, 30);
        gbc.gridy = 2;
        boxInterna.add(lblCodigo, gbc);

        JTextField txtCodigo = new JTextField();
        txtCodigo.setPreferredSize(new Dimension(0, 40));
        gbc.insets = new Insets(5, 30, 15, 30);
        gbc.gridy = 3;
        boxInterna.add(txtCodigo, gbc);

        JButton btnSubmit = new JButton("Confirmar Codigo");
        btnSubmit.setPreferredSize(new Dimension(80, 45));
        btnSubmit.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnSubmit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 30, 15, 30);
        boxInterna.add(btnSubmit, gbc);

        btnSubmit.addActionListener(e -> {
            String codigoDigitado = txtCodigo.getText();

            // validação
            if (codigoDigitado.equals(codigoVerdadeiro)) {
                JOptionPane.showMessageDialog(this, "codigo validado com sucesso! Crie uma nova senha.");
                this.dispose();

                TelaRedefinirSenha redefinirSenha = new TelaRedefinirSenha(central);
                redefinirSenha.setVisible(true);

            } else {
            JOptionPane.showMessageDialog(this, "codigo incorreto", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        });
    }
}
