package br.edu.ifpb.teatro.view;

import br.edu.ifpb.teatro.dao.CentralDeInformacoes;
import br.edu.ifpb.teatro.model.ADM;
import br.edu.ifpb.teatro.security.ValidadorSenha;
import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TelaLoginAdm extends JFrame {
    private CentralDeInformacoes central;

    private JTextField txtEmail;
    private JPasswordField txtSenha;

    public TelaLoginAdm(CentralDeInformacoes central) {
        this.central = central;

        setTitle("Gerenciador de Teatro - Acesso");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);

        JPanel containerPrincipal = new JPanel(new GridBagLayout());
        containerPrincipal.setBackground(new Color(30, 30, 30)); // defini a cor de fundo da janela
        add(containerPrincipal);

        JPanel cartaoLogin = new JPanel();
        cartaoLogin.setLayout(new GridBagLayout()); // alinhando os campos de dentro
        cartaoLogin.setPreferredSize(new Dimension(380, 480));
        cartaoLogin.setBackground(new Color(45, 45, 45)); // aq é a cor tambem


        // adicionando cantos arredondados e uma sombra projetada
        cartaoLogin.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc: 25;" + // arredondando os cantos
                "background: #2D2D2D;" +
                "border: 0,0,0,0,shadow"); //sombra ao redor

        containerPrincipal.add(cartaoLogin); //aq eh a adição do cartao de login dentro do container


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 30, 10, 30); //margens internas
        gbc.fill = GridBagConstraints.HORIZONTAL; //estica horizontalmente para preencher o cartão
        gbc.gridx = 0; //mesma coluna - coluna 0

        JLabel lblTitulo = new JLabel("BEM-VINDO");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 26));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setForeground(new Color(220, 220, 220));
        lblTitulo.setBorder(new EmptyBorder(0, 0, 5, 0));
        gbc.gridy = 0;
        cartaoLogin.add(lblTitulo, gbc);


        JLabel lblSubtitulo = new JLabel("Faça login para acessar");
        lblSubtitulo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblSubtitulo.setForeground(new Color(160, 160, 160));
        lblSubtitulo.setBorder(new EmptyBorder(0, 0, 30, 0));
        gbc.gridy = 1;
        cartaoLogin.add(lblSubtitulo, gbc);


        JLabel lblEmail = new JLabel("E-mail do Administrador");
        lblEmail.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblEmail.setForeground(new Color(180, 180, 180));
        gbc.insets = new Insets(5, 30, 0, 30);
        gbc.gridy = 2;
        cartaoLogin.add(lblEmail, gbc);


        txtEmail = new JTextField();
        txtEmail.setPreferredSize(new Dimension(0, 40));
        txtEmail.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "exemplo@email.com");
        gbc.insets = new Insets(5, 30, 15, 30);
        gbc.gridy = 3;
        cartaoLogin.add(txtEmail, gbc);

        JLabel lblSenha = new JLabel("Senha de Acesso");
        lblSenha.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblSenha.setForeground(new Color(180, 180, 180));
        gbc.insets = new Insets(5, 30, 0, 30);
        gbc.gridy = 4;
        cartaoLogin.add(lblSenha, gbc);

        txtSenha = new JPasswordField();
        txtSenha.setPreferredSize(new Dimension(0, 40));
        txtSenha.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "********");
        txtSenha.putClientProperty(FlatClientProperties.STYLE, "showRevealButton: true"); // olhinho para visualizar a senha
        gbc.insets = new Insets(5, 30, 25, 30);
        gbc.gridy = 5;
        cartaoLogin.add(txtSenha, gbc);

        JButton btnLogin = new JButton("Entrar");
        btnLogin.setPreferredSize(new Dimension(0, 45));
        btnLogin.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton btnEsqueciSenha = new JButton("Esqueci minha senha");
        btnEsqueciSenha.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEsqueciSenha.putClientProperty(FlatClientProperties.STYLE, // deixei com aspecto de "botão padrão esqueci a senha"
                "borderPainted: false; " +
                        "contentAreaFilled: false; " +
                        "foreground: #4BA3E3");
        gbc.gridy = 7;
        gbc.insets = new Insets(0, 30, 15, 30);
        cartaoLogin.add(btnEsqueciSenha, gbc);

        btnEsqueciSenha.addActionListener(e -> {
            this.dispose();
            TelaEsqueciSenha esqueciSenha = new TelaEsqueciSenha(central);
            esqueciSenha.setVisible(true);
        });


        btnLogin.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc: 10;" +
                "background: $Button.accentBackground;" +
                "foreground: $Button.accentForeground");
        gbc.gridy = 6;
        cartaoLogin.add(btnLogin, gbc);

        btnLogin.addActionListener(e -> realizarLogin());
    }

    private void realizarLogin() {
        String email = txtEmail.getText();
        String senha = new String(txtSenha.getPassword());

        if (email.trim().isEmpty() || senha.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ADM adminSalvo = central.getAdministrador();

        if (adminSalvo == null) {
            JOptionPane.showMessageDialog(this, "Admin Inexistente.", "Erro Fatal", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try{
            ValidadorSenha.validandoSenha(senha);
            if (email.equals(adminSalvo.getEmail()) && senha.equals(adminSalvo.getSenha())) {
                this.dispose();
                JOptionPane.showMessageDialog(null, "Login realizado com sucesso! (Abrindo Home...)");
                TelaHome telaHome = new TelaHome(central);
                telaHome.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Email e/ou senha incorretos!", "Acesso Negado", JOptionPane.ERROR_MESSAGE);
            }

        }catch (RuntimeException error){
            JOptionPane.showMessageDialog(this, "Email e/ou senha incorretos!", "Erro ao tentar acessar o sistema", JOptionPane.ERROR_MESSAGE);

        }

    }
}