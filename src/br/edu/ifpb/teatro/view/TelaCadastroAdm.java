package br.edu.ifpb.teatro.view;
import br.edu.ifpb.teatro.dao.CentralDeInformacoes;
import br.edu.ifpb.teatro.dao.Persistencia;
import br.edu.ifpb.teatro.model.ADM;
import br.edu.ifpb.teatro.security.ValidadorSenha;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroAdm extends JFrame{

    private CentralDeInformacoes central;

    public TelaCadastroAdm(CentralDeInformacoes central){
        this.central = central;

        setTitle("Cadastro do Administrador");
        setSize(800, 650); // defini o tamanho da tela
        setDefaultCloseOperation(EXIT_ON_CLOSE); // botao de fechar
        setLocationRelativeTo(null); // centraliza

        JPanel painel = new JPanel(new GridLayout(4,2));

        JLabel labelNome = new JLabel("Digite seu Nome: ");
        JTextField textoNome = new JTextField();

        JLabel labelEmail = new JLabel("Digite seu E-mail: ");
        JTextField textoEmail = new JTextField();

        JLabel labelSenha = new JLabel("Digite sua senha: (8 digitos no minimo) ");
        JPasswordField senha = new JPasswordField();

        JButton botaoCadastro = new JButton("Cadastrar");

        painel.add(labelNome);
        painel.add(textoNome);
        painel.add(labelEmail);
        painel.add(textoEmail);
        painel.add(labelSenha);
        painel.add(senha);
        painel.add(new JLabel("")); // to forçando ele ficar na direita
        painel.add(botaoCadastro);

        add(painel);

        botaoCadastro.addActionListener(e -> {
            String nome = textoNome.getText();
            String email = textoEmail.getText();
            String senhaCapturada = new String(senha.getPassword());
            ValidadorSenha.validandoSenha(senhaCapturada);

            if(nome.trim().isEmpty() || email.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "é obrigatório todos os campos preenchido!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try{
            ADM admin = new ADM(email, senhaCapturada, nome);
            central.setAdministrador(admin);
            Persistencia p = new Persistencia();
            p.salvarCentral(central, "central.xml");

            this.dispose();

            TelaLoginAdm telaLogin = new TelaLoginAdm(central);
            telaLogin.setVisible(true);

            }catch (RuntimeException error){
                JOptionPane.showMessageDialog(this, error.getMessage(), "erro na Senha", JOptionPane.ERROR_MESSAGE);
            }
        });
    }


}
