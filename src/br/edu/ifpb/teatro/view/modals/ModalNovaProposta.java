package br.edu.ifpb.teatro.view.modals;

import br.edu.ifpb.teatro.dao.CentralDeInformacoes;

import javax.swing.*;
import java.awt.*;

public class ModalNovaProposta extends JDialog {
    private CentralDeInformacoes central;
    private JTextField txtCpf;
    private JTextField txtNomeArtista;
    private JTextField txtTelefone;
    private JTextField txtEmail;
    private JTextField txtGenero;
    private JTextField txtDataNascimento;
    private JTextField txtNomePeca;
    private JTextField txtDataEvento;
    private JTextField txtHoraInicio;
    private JTextField txtHoraFim;
    private JTextField txtPrecoIngresso;

    public ModalNovaProposta(Window parente, CentralDeInformacoes central){
        super(parente, "Cadastrar Nova Proposta", Dialog.ModalityType.APPLICATION_MODAL);

        setSize(500, 600);
        setLocationRelativeTo(parente);
        setLayout(new BorderLayout());

        txtCpf = new JTextField();
        txtNomeArtista = new JTextField();
        txtTelefone = new JTextField();
        txtEmail = new JTextField();
        txtGenero = new JTextField();
        txtDataNascimento = new JTextField();

        JTextField txtNomePeca = new JTextField();
        JTextField txtDataEvento = new JTextField();
        JTextField txtHoraInicio = new JTextField();
        JTextField txtHoraFim = new JTextField();
        JTextField txtPrecoIngresso = new JTextField();


        JPanel painelFormulario = new JPanel(new GridLayout(0, 2, 10, 10));
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        painelFormulario.add(new JLabel("CPF do Artista (Busca Automática):"));
        painelFormulario.add(txtCpf);

        painelFormulario.add(new JLabel("Nome Completo:"));
        painelFormulario.add(txtNomeArtista);

        painelFormulario.add(new JLabel("Telefone:"));
        painelFormulario.add(txtTelefone);

        painelFormulario.add(new JLabel("E-mail:"));
        painelFormulario.add(txtEmail);

        painelFormulario.add(new JLabel("Gênero:"));
        painelFormulario.add(txtGenero);

        painelFormulario.add(new JLabel("Data de Nascimento (DD/MM/AAAA):"));
        painelFormulario.add(txtDataNascimento);

        painelFormulario.add(new JSeparator());
        painelFormulario.add(new JSeparator());

        painelFormulario.add(new JLabel("Nome da Peça:"));
        painelFormulario.add(txtNomePeca);

        painelFormulario.add(new JLabel("Data do Evento (DD/MM/AAAA):"));
        painelFormulario.add(txtDataEvento);

        painelFormulario.add(new JLabel("Hora de Início (HH:MM):"));
        painelFormulario.add(txtHoraInicio);

        painelFormulario.add(new JLabel("Hora de Fim (HH:MM):"));
        painelFormulario.add(txtHoraFim);

        painelFormulario.add(new JLabel("Preço do Ingresso (R$):"));
        painelFormulario.add(txtPrecoIngresso);

        JScrollPane scrollFormulario = new JScrollPane(painelFormulario);
        scrollFormulario.setBorder(null);

        this.add(scrollFormulario, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton btnCancelar = new JButton("Cancelar");
        JButton btnSalvar = new JButton("Salvar");

        btnSalvar.setBackground(new Color(53, 132, 228));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFocusPainted(false);

        btnCancelar.addActionListener(e -> {
            this.dispose();
        });

        btnSalvar.addActionListener(e -> {
            System.out.println("salvamento...");
        });

        painelBotoes.add(btnCancelar);
        painelBotoes.add(btnSalvar);
        this.add(painelBotoes, BorderLayout.SOUTH);
    }
}
