package br.edu.ifpb.teatro.view;

import br.edu.ifpb.teatro.dao.CentralDeInformacoes;
import br.edu.ifpb.teatro.dao.Persistencia;
import br.edu.ifpb.teatro.enums.PessoaSexo;
import br.edu.ifpb.teatro.enums.StatusProposta;
import br.edu.ifpb.teatro.model.Pessoa;
import br.edu.ifpb.teatro.model.PropostaDeAluguel;
import br.edu.ifpb.teatro.security.ValidadorDocumento;
import br.edu.ifpb.teatro.util.GeradorDeContratos;
import br.edu.ifpb.teatro.util.Mensageiro;


import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Persistencia persistencia = new Persistencia();
        CentralDeInformacoes central = persistencia.recuperarCentral("central.xml");
        //Scanner s = new Scanner(System.in);

        // aq eu preciso verificar se existe algum adm cadastrado
        if(central.getAdministrador() == null){
            // se caso nao tiver nenhum adm, vai pra tela de cadastro
            TelaCadastroAdm telaCadastro = new TelaCadastroAdm(central);
            telaCadastro.setVisible(true); // esse comando exibe a tela
        }else{
           // TelaLoginAdm telaLogin = new TelaLoginAdm();
           // telaLogin.setVisible(true);

        }
/*
        String op = "";

        while (!op.equals("S")) {

            System.out.println("1 - Nova pessoa");
            System.out.println("2 - Listar todas as pessoas");
            System.out.println("3 - Exibir informações de uma pessoa específica");
            System.out.println("4 - Nova proposta");
            System.out.println("5 - Informar quantidade de propostas cadastradas");
            System.out.println("6 - Detalhar uma proposta");
            System.out.println("7 - Ativar uma proposta");
            System.out.println("S - Sair");
            System.out.print("Escolha uma opção: ");

            op = s.nextLine().toUpperCase();

            switch (op) {
                case "1":
                    try {
                        System.out.print("Digite o Nome: ");
                        String nome = s.nextLine();

                        System.out.print("Digite o CPF: ");
                        String CPF = s.nextLine();
                        ValidadorDocumento.validarCPF(CPF); //to validando...


                        System.out.print("Digite o E-mail: ");
                        String email = s.nextLine();

                        System.out.print("Digite o Sexo: (ex: MASCULINO, FEMININO, OUTRO)");
                        String sexo = s.nextLine();
                        PessoaSexo sexoConvertido = PessoaSexo.valueOf(sexo.toUpperCase());

                        System.out.print("Digite o Telefone: ");
                        String telefone = s.nextLine();

                        System.out.print("Digite sua data de Nascimento: (dd-mm-aaaa)");
                        String data = s.nextLine();


                        Pessoa novaPessoa = new Pessoa(nome, CPF, email, sexoConvertido, telefone, data);

                        if (central.adicionarPessoa(novaPessoa)) {
                            persistencia.salvarCentral(central, "central.xml");
                            System.out.println("pessoa cadastrada e salva com sucesso!");
                        } else {
                            System.out.println("erro pq já existe uma pessoa cadastrada com este CPF.");
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Erro: Opção de sexo invalida! use MASCULINO, FEMININO ou OUTRO.");
                    }
                    break;

                case "2":
                    System.out.println("LISTA DE PESSOAS");

                    if (central.getTodasAsPessoas().isEmpty()) {
                        System.out.println("Nenhuma pessoa cadastrada no sistema.");
                    } else {
                        for (Pessoa pessoa : central.getTodasAsPessoas()) {
                            System.out.println("Nome: " + pessoa.getNome() + " | CPF: " + pessoa.getCpf());
                        }
                    }
                    break;

                case "3":
                    System.out.println("BUSCA DE PESSOA");
                    System.out.print("Digite o CPF da pessoa: ");
                    String cpfBusca = s.nextLine();

                    Pessoa pessoaEncontrada = central.recuperarPessoaPorCPF(cpfBusca);

                    if (pessoaEncontrada != null) {
                        System.out.println("Informaçoes encontradas:");
                        System.out.println(pessoaEncontrada.toString());
                    } else {
                        System.out.println("pessoa não existe no sistema.");
                    }
                    break;

                case "4":
                    System.out.println("NOVA PROPOSTA DE ALUGUEL");
                    System.out.print("digite o CPF do Locatario (Artista): ");
                    String cpfLocatario = s.nextLine();

                    Pessoa locatario = central.recuperarPessoaPorCPF(cpfLocatario);

                    if (locatario == null) {
                        System.out.println("Erro: pessoa não cadastrada. cadastre primeiro.");
                        break;
                    }

                    System.out.print("nome da Peça: ");
                    String nomePeca = s.nextLine();

                    System.out.print("data de Inicio (Formato AAAA-MM-DD): ");
                    LocalDate dataInicio = LocalDate.parse(s.nextLine());

                    System.out.print("data de Fim (Formato AAAA-MM-DD): ");
                    LocalDate dataFim = LocalDate.parse(s.nextLine());

                    System.out.print("valor Total do Aluguel (ex: 500.00): ");
                    float valor = Float.parseFloat(s.nextLine());

                    LocalDate dataCadastro = LocalDate.now();

                    PropostaDeAluguel novaProposta = new PropostaDeAluguel(dataCadastro, dataInicio, dataFim, nomePeca, valor, locatario);

                    if (central.adicionarProposta(novaProposta)) {
                        persistencia.salvarCentral(central, "central.xml");
                        System.out.println("Proposta cadastrada e salva!");
                    } else {
                        System.out.println("nao foi possível cadastrar a proposta.");
                    }
                    break;

                case "5":
                    System.out.println("ESTATÍSTICAS");
                    int qntContratado = 0;
                    int qntContAlterado = 0;
                    int qntEmContratacao = 0;
                    int qntEncerrado = 0;
                    int qtdPropostas = central.getTodasAsPropostas().size();

                    for (PropostaDeAluguel p : central.getTodasAsPropostas()) {
                        if (p.getStatus() == StatusProposta.CONTRATADO) {
                            qntContratado++;
                        } else if (p.getStatus() == StatusProposta.EM_CONTRATACAO) {
                            qntEmContratacao++;
                        } else if (p.getStatus() == StatusProposta.CONTRATADO_COM_ALTERACAO) {
                            qntContAlterado++;
                        } else if (p.getStatus() == StatusProposta.ENCERRADO) {
                            qntEncerrado++;
                        }
                    }
                    System.out.println("qantidade total de propostas cadastradas: " + qtdPropostas);
                    System.out.println("propostas Contratados: " + qntContratado);
                    System.out.println("Propostas em Contratação: " + qntEmContratacao);
                    System.out.println("Propostas De Contratos Alterados: " + qntContAlterado);
                    System.out.println("Propostas Encerradas: " + qntEncerrado);
                    break;

                case "6":
                    System.out.println("DETALHES DA PROPOSTA");
                    System.out.print("digite o ID da proposta: ");
                    try {
                        long idDetalhe = Long.parseLong(s.nextLine());
                        PropostaDeAluguel propDetalhe = central.recuperarPropostaPorId(idDetalhe);

                        if (propDetalhe != null) {
                            System.out.println("resumo da Proposta:");
                            System.out.println("ID: " + propDetalhe.getId());
                            System.out.println("Peça: " + propDetalhe.getNomeDaPeca());
                            System.out.println("Locatário: " + propDetalhe.getLocatario().getNome());
                            System.out.println("Período: " + propDetalhe.getDataDeInicioDoAluguel() + " até " + propDetalhe.getDataDeFimDoAluguel());
                            System.out.println("Status atual: " + propDetalhe.getStatus());
                        } else {
                            System.out.println("proposta não encontrada no sistema.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("erro: O ID deve ser um número válido.");
                    }
                    break;

                case "7":
                    System.out.println("ATIVAR PROPOSTA E GERAR CONTRATO");
                    System.out.print("digite o ID da proposta para ativar: ");
                    try {
                        long idAtivar = Long.parseLong(s.nextLine());
                        PropostaDeAluguel propAtivar = central.recuperarPropostaPorId(idAtivar);

                        if (propAtivar != null) {

                            propAtivar.setStatus(StatusProposta.CONTRATADO);

                            persistencia.salvarCentral(central, "central.xml");
                            System.out.println("[OK] - Status alterado para ATIVO e salvo no XML.");

                            System.out.println("Gerando contrato em PDF...");
                            GeradorDeContratos.obterContrato(idAtivar, central);

                            String emailDestino = propAtivar.getLocatario().getEmail();
                            System.out.println("estabelecendo conexão com o servidor de e-mail");
                            System.out.println("iniciando envio para " + emailDestino + " (Aguarde, isso pode levar alguns segundos)...");

                            Mensageiro.enviarEmail(emailDestino, "contrato.pdf");

                            System.out.println("[SUCESSO] Fluxo de ativação concluído.");

                        } else {
                            System.out.println("erro: Proposta não encontrada.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("erro: O ID deve ser um número numérico.");
                    }
                    break;

                case "S":
                    System.out.println("encerrando o Gerenciador de Teatro.");
                    break;

                default:
                    System.out.println("Opção inválida. Por favor, escolha um item válido do menu.");
                    break;
            }
        }*/
    }
}