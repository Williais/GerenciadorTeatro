package br.edu.ifpb.teatro.view;

import br.edu.ifpb.teatro.dao.CentralDeInformacoes;
import br.edu.ifpb.teatro.dao.Persistencia;

import com.formdev.flatlaf.FlatDarculaLaf;

public class Main {
    public static void main(String[] args) {

        try {
            FlatDarculaLaf.setup();
        } catch (Exception ex) {
            System.err.println("FlatLaf falhou. O sistema vai usar o tema padrão (Feio demais).");
        }

        Persistencia persistencia = new Persistencia();
        CentralDeInformacoes central = persistencia.recuperarCentral("central.xml");

        // aq eu preciso verificar se existe algum adm cadastrado
        if(central.getAdministrador() == null){
            // se caso nao tiver nenhum adm, vai pra tela de cadastro
            TelaCadastroAdm telaCadastro = new TelaCadastroAdm(central);
            telaCadastro.setVisible(true);
        }else{
            TelaHome home = new TelaHome(central);
            home.setVisible(true);

           //TelaLoginAdm telaLogin = new TelaLoginAdm(central);
           //telaLogin.setVisible(true);

        }

    }
}