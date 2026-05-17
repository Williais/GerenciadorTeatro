package br.edu.ifpb.teatro.view;

import br.edu.ifpb.teatro.dao.CentralDeInformacoes;
import br.edu.ifpb.teatro.dao.Persistencia;
import br.edu.ifpb.teatro.model.ADM;
import br.edu.ifpb.teatro.security.ValidadorSenha;
import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TelaCadastroAdm extends JFrame {

    private CentralDeInformacoes central;

    public TelaCadastroAdm(CentralDeInformacoes central) {
        this.central = central;

        setTitle("Cadastro do Administrador");
        setSize(900, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel containerPrincipal = new JPanel(new GridBagLayout());
        containerPrincipal.setBackground(new Color(30, 30, 30));
        add(containerPrincipal);

        JPanel cartaoCadastro = new JPanel(new GridBagLayout());
        cartaoCadastro.setPreferredSize(new Dimension(400, 580));

        cartaoCadastro.putClientProperty(FlatClientProperties.STYLE,
                "arc: 25; " +
                        "border: 0,0,0,0,shadow");

        containerPrincipal.add(cartaoCadastro);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 30, 5, 30);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel lblTitulo = new JLabel("CRIAR CONTA");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBorder(new EmptyBorder(10, 0, 20, 0));
        gbc.gridy = 0;
        cartaoCadastro.add(lblTitulo, gbc);

        JLabel lblSubtitulo = new JLabel("Cadastre sua Conta de Administrador");
        lblSubtitulo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblSubtitulo.setForeground(new Color(160, 160, 160));
        lblSubtitulo.setBorder(new EmptyBorder(0, 0, 30, 0));
        gbc.gridy = 1;

        cartaoCadastro.add(lblSubtitulo, gbc);


        JLabel labelNome = new JLabel("Nome Completo");
        labelNome.setFont(new Font("SansSerif", Font.BOLD, 12));
        gbc.gridy = 2;
        cartaoCadastro.add(labelNome, gbc);

        JTextField textoNome = new JTextField();
        textoNome.setPreferredSize(new Dimension(0, 40));
        gbc.insets = new Insets(5, 30, 15, 30);
        gbc.gridy = 3;
        cartaoCadastro.add(textoNome, gbc);

        JLabel labelEmail = new JLabel("E-mail de Acesso");
        labelEmail.setFont(new Font("SansSerif", Font.BOLD, 12));
        gbc.insets = new Insets(5, 30, 5, 30);
        gbc.gridy = 4;
        cartaoCadastro.add(labelEmail, gbc);

        JTextField textoEmail = new JTextField();
        textoEmail.setPreferredSize(new Dimension(0, 40));
        gbc.insets = new Insets(5, 30, 15, 30);
        gbc.gridy = 5;
        cartaoCadastro.add(textoEmail, gbc);

        JLabel labelSenha = new JLabel("Senha (Mínimo 8 dígitos)");
        labelSenha.setFont(new Font("SansSerif", Font.BOLD, 12));
        gbc.insets = new Insets(5, 30, 5, 30);
        gbc.gridy = 6;
        cartaoCadastro.add(labelSenha, gbc);

        JPasswordField senha = new JPasswordField();
        senha.setPreferredSize(new Dimension(0, 40));
        senha.putClientProperty(FlatClientProperties.STYLE, "showRevealButton: true");
        gbc.insets = new Insets(5, 30, 25, 30);
        gbc.gridy = 7;
        cartaoCadastro.add(senha, gbc);

        JButton botaoCadastro = new JButton("CADASTRAR");
        botaoCadastro.setPreferredSize(new Dimension(10, 45));
        botaoCadastro.setFont(new Font("SansSerif", Font.BOLD, 14));
        botaoCadastro.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botaoCadastro.putClientProperty(FlatClientProperties.STYLE,
                "arc: 10; " +
                        "background: $Button.accentBackground; " +
                        "foreground: $Button.accentForeground");
        gbc.gridy = 8;
        cartaoCadastro.add(botaoCadastro, gbc);

        botaoCadastro.addActionListener(e -> {
            String nome = textoNome.getText();
            String email = textoEmail.getText();
            String senhaCapturada = new String(senha.getPassword());

            if (nome.trim().isEmpty() || email.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "É obrigatório preencher todos os campos!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                ValidadorSenha.validandoSenha(senhaCapturada);

                ADM admin = new ADM(email, senhaCapturada, nome);
                central.setAdministrador(admin);
                Persistencia p = new Persistencia();
                p.salvarCentral(central, "central.xml");

                this.dispose();

                TelaLoginAdm telaLogin = new TelaLoginAdm(central);
                telaLogin.setVisible(true);

            } catch (RuntimeException error) {
                JOptionPane.showMessageDialog(this, error.getMessage(), "Erro na Senha", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}