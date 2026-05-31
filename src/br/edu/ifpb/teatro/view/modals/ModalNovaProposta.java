package br.edu.ifpb.teatro.view.modals;

import br.edu.ifpb.teatro.dao.CentralDeInformacoes;
import br.edu.ifpb.teatro.dao.Persistencia;
import br.edu.ifpb.teatro.enums.PessoaSexo;
import br.edu.ifpb.teatro.exception.*;
import br.edu.ifpb.teatro.model.Pessoa;
import br.edu.ifpb.teatro.model.PropostaDeAluguel;
import br.edu.ifpb.teatro.security.ValidadorDocumento;
import br.edu.ifpb.teatro.util.CalculadoraPreco;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ModalNovaProposta extends JDialog {
    private CentralDeInformacoes central;
    private JTextField txtCpf;
    private JTextField txtNomeArtista;
    private JTextField txtTelefone;
    private JTextField txtEmail;
    private JTextField txtGenero; //WILLIAM, TEM QUE ALTERAR PARA OPÇÕES
    private JTextField txtDataNascimento;
    private JTextField txtNomePeca;
    private JTextField txtDataEvento;
    private JTextField txtHoraInicio;
    private JTextField txtHoraFim;
    private JTextField txtPrecoIngresso;

    public ModalNovaProposta(Window parente, CentralDeInformacoes central){
        super(parente, "Cadastrar Nova Proposta", Dialog.ModalityType.APPLICATION_MODAL);
        this.central = central;

        setSize(500, 600);
        setLocationRelativeTo(parente);
        setLayout(new BorderLayout());

        txtCpf = new JTextField();
        txtCpf.addFocusListener(new OuvinteFocoCpf());
        txtNomeArtista = new JTextField();
        txtTelefone = new JTextField();
        txtEmail = new JTextField();
        txtGenero = new JTextField();
        txtDataNascimento = new JTextField();

        txtNomePeca = new JTextField();
        txtDataEvento = new JTextField();
        txtHoraInicio = new JTextField();
        txtHoraFim = new JTextField();
        txtPrecoIngresso = new JTextField();


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
        painelFormulario.add(txtGenero); //ALTERAR PARA OPÇÕES

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

        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) throws NumberFormatException, DateTimeException {
                try{
                    DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Achei meio-redundante criar uma Exception já que o DateTimeParseException vai fazer tudo.

                    LocalDate data = LocalDate.parse(txtDataEvento.getText(), formatador);
                    LocalTime horaI = LocalTime.parse(txtHoraInicio.getText());
                    LocalTime horaF = LocalTime.parse(txtHoraFim.getText());

                    float valorIngresso;

                        try {

                            valorIngresso = Float.parseFloat(txtPrecoIngresso.getText());

                        } catch (NumberFormatException erro) {
                            throw new ValorIngressoInvalidoException("Digite um valor numérico válido para o preço do ingresso.");
                        }

                        central.validarHorarioLocacao(data, horaI, horaF); // Will, mantive o tratamento como foi feito lá na central, já que o metodo já possui Exceptions para o que foi pedido


                    String cpf = txtCpf.getText();

                    ValidadorDocumento.validarCPF(cpf);

                    if(central.recuperarPessoaPorCPF(cpf) == null){
                        String cpfD = txtCpf.getText();
                        String nome= txtNomeArtista.getText();
                        String telefone = txtTelefone.getText();
                        String email= txtEmail.getText();
                        String textoGenero = txtGenero.getText().toUpperCase();
                        PessoaSexo sexo = PessoaSexo.valueOf(textoGenero);
                        String dataN = txtDataNascimento.getText();

                        Pessoa p = new Pessoa(nome, cpfD, email, sexo, telefone, dataN);
                        central.adicionarPessoa(p);
                    }

                    LocalDateTime inicioVerdadeiro = LocalDateTime.of(data, horaI);
                    LocalDateTime finalVerdadeiro = LocalDateTime.of(data, horaF);
                    LocalDateTime horaAtual = inicioVerdadeiro;
                    float valorTotalDoAluguel = 0.0f;

                    while (horaAtual.isBefore(finalVerdadeiro)){
                        float valorDaCalculadora = CalculadoraPreco.recuperarPrecoVigente(central.getTodasAsRegras(), horaAtual);
                        valorTotalDoAluguel += valorDaCalculadora;
                        horaAtual = horaAtual.plusHours(1);
                    }

                    Pessoa p = central.recuperarPessoaPorCPF(cpf);
                    String nomeEvento = txtNomePeca.getText();
                    LocalDateTime agora = LocalDateTime.now();

                    PropostaDeAluguel novaP = new PropostaDeAluguel(agora, horaI, horaF, nomeEvento, valorTotalDoAluguel, valorIngresso, p, data);
                    central.adicionarProposta(novaP);

                    Persistencia persistencia = new Persistencia();
                    persistencia.salvarCentral(central, "central.xml");

                    ModalNovaProposta.this.dispose();

                    JOptionPane.showMessageDialog(null, "Proposta de Aluguel Realizado");

                }catch (DateTimeParseException erro) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Por favor, verifique se o formato DD/MM/AAAA ou HH:MM está correto.",
                            "Formatação inválida",
                            JOptionPane.ERROR_MESSAGE
                    );

                } catch (CPFInvalidoException erro) {

                    JOptionPane.showMessageDialog(
                            null,
                            erro.getMessage(),
                            "CPF inválido",
                            JOptionPane.ERROR_MESSAGE
                    );

                } catch (ValorIngressoInvalidoException erro) {

                    JOptionPane.showMessageDialog(
                            null,
                            erro.getMessage(),
                            "Valor do ingresso inválido",
                            JOptionPane.ERROR_MESSAGE
                    );

                } catch (
                        HorarioForaDoTurnoPermitidoException |
                        HorarioIndisponivelException erro
                ) {

                    JOptionPane.showMessageDialog(
                            null,
                            erro.getMessage(),
                            "Horário inválido",
                            JOptionPane.ERROR_MESSAGE
                    );

                } catch (
                        PessoaJaCadastradaException |
                        PropostaJaCadastradaException erro
                ) {

                    JOptionPane.showMessageDialog(
                            null,
                            erro.getMessage(),
                            "Erro de cadastro",
                            JOptionPane.ERROR_MESSAGE
                    );

                }
            }
        });

        painelBotoes.add(btnCancelar);
        painelBotoes.add(btnSalvar);
        this.add(painelBotoes, BorderLayout.SOUTH);
    }

    public class OuvinteFocoCpf implements FocusListener {

        public void focusGained(FocusEvent e) {

        }

        public void focusLost(FocusEvent e) {
            String cpf = txtCpf.getText();

            if (cpf.isEmpty()){
                return;
            }

            Pessoa pessoa = central.recuperarPessoaPorCPF(cpf);

            if ( pessoa != null){
                txtNomeArtista.setText(pessoa.getNome());
                txtTelefone.setText(pessoa.getTelefone());
                txtEmail.setText(pessoa.getEmail());
                txtGenero.setText(pessoa.getSexo().name());
                txtDataNascimento.setText(pessoa.getDataNascimento());

                txtNomeArtista.setEditable(false);
                txtTelefone.setEditable(false);
                txtEmail.setEditable(false);
                txtGenero.setEditable(false);
                txtDataNascimento.setEditable(false);
            }else {
                txtNomeArtista.setText("");
                txtTelefone.setText("");
                txtEmail.setText("");
                txtGenero.setText("");
                txtDataNascimento.setText("");

                txtNomeArtista.setEditable(true);
                txtTelefone.setEditable(true);
                txtEmail.setEditable(true);
                txtGenero.setEditable(true);
                txtDataNascimento.setEditable(true);
            }
        }
    }
}
