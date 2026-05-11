package br.edu.ifpb.teatro.view;

import br.edu.ifpb.teatro.dao.CentralDeInformacoes;
import br.edu.ifpb.teatro.dao.Persistencia;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Persistencia persistencia = new Persistencia();
        CentralDeInformacoes central = persistencia.recuperarCentral("central.xml");
        Scanner s = new Scanner(System.in);

        String op = "";

        while(op.equalsIgnoreCase("s")){

            System.out.println("\n=== MENU DO GERENCIADOR DE TEATRO ===");
            System.out.println("1 - Nova pessoa");
            System.out.println("2 - Listar todas as pessoas");
            System.out.println("3 - Exibir informações de uma pessoa específica");
            System.out.println("4 - Nova proposta");
            System.out.println("5 - Informar quantidade de propostas cadastradas");
            System.out.println("6 - Detalhar uma proposta");
            System.out.println("7 - Ativar uma proposta");
            System.out.println("S - Sair");
            System.out.print("Escolha uma opção: ");

            op = s.nextLine();
        }
    }
}