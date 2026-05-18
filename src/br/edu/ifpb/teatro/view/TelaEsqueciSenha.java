package br.edu.ifpb.teatro.view;

import br.edu.ifpb.teatro.dao.CentralDeInformacoes;
import br.edu.ifpb.teatro.util.GeradorDeCodigo;
import br.edu.ifpb.teatro.util.Mensageiro;
import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TelaEsqueciSenha extends JFrame {
    private final CentralDeInformacoes central;
    public TelaEsqueciSenha(CentralDeInformacoes central){
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

        JLabel lblSubtitulo = new JLabel("Digite seu email para receber o codigo e alterar a senha");
        lblSubtitulo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblSubtitulo.setForeground(new Color(160, 160, 160));
        lblSubtitulo.setBorder(new EmptyBorder(0, 0, 30, 0));
        gbc.gridy = 1;
        boxInterna.add(lblSubtitulo, gbc);

        JLabel lblEmail = new JLabel("Seu E-mail");
        lblEmail.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblEmail.setForeground(new Color(180, 180, 180));
        gbc.insets = new Insets(5, 30, 0, 30);
        gbc.gridy = 2;
        boxInterna.add(lblEmail, gbc);

        JTextField txtEmail = new JTextField();
        txtEmail.setPreferredSize(new Dimension(0, 40));
        txtEmail.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "exemplo@email.com");
        gbc.insets = new Insets(5, 30, 15, 30);
        gbc.gridy = 3;
        boxInterna.add(txtEmail, gbc);

        JButton btnSubmit = new JButton("Enviar Codigo");
        btnSubmit.setPreferredSize(new Dimension(80, 45));
        btnSubmit.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnSubmit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 30, 15, 30);
        boxInterna.add(btnSubmit, gbc);

        btnSubmit.addActionListener(e -> {
            String email = txtEmail.getText();

            if (!email.equals(central.getAdministrador().getEmail())) {
                JOptionPane.showMessageDialog(this, "E-mail Inexistente.", "Erro Fatal", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String codigoGerado = GeradorDeCodigo.gerar();

            // muda o texto do botao e desativa
            btnSubmit.setText("Enviando...");
            btnSubmit.setEnabled(false);

            //cria uma linha de execucao separada para não congelar a tela
            new Thread(() -> {
                try {

                    Mensageiro.enviarCodigoRecuperacao(email, codigoGerado);

                    // fechar a tela e abrir a próxima
                    SwingUtilities.invokeLater(() -> {
                        this.dispose();

                        TelaCodigo telaCodigo = new TelaCodigo(central, codigoGerado);
                        telaCodigo.setVisible(true);
                    });

                } catch (RuntimeException ex) {
                    //  avisamdo erro e reativando o botão
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
                        btnSubmit.setText("Enviar Código");
                        btnSubmit.setEnabled(true);
                    });
                }
            }).start();
        });
    }
}
