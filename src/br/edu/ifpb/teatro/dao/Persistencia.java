package br.edu.ifpb.teatro.dao;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;

public class Persistencia {

    // config para nao da erro nessa nova versão do xstream
    private XStream configurarXStream() {
        XStream xstream = new XStream(new DomDriver());

        xstream.allowTypesByWildcard(new String[] {
                "br.edu.ifpb.teatro.**"
        });

        return xstream;
    }

    public void salvarCentral(CentralDeInformacoes central, String nomeArquivo) {
        XStream xstream = configurarXStream();

        String xml = xstream.toXML(central);

        try (PrintWriter writer = new PrintWriter(new File(nomeArquivo))) {
            writer.print(xml);
        } catch (Exception e) {
            System.out.println("Erro ao salvar o arquivo XML: " + e.getMessage());
        }
    }

    public CentralDeInformacoes recuperarCentral(String nomeArquivo) {
        File arquivo = new File(nomeArquivo);

        if (!arquivo.exists()) {
            return new CentralDeInformacoes();
        }

        try (FileInputStream fis = new FileInputStream(arquivo)) {
            XStream xstream = configurarXStream();

            return (CentralDeInformacoes) xstream.fromXML(fis);

        } catch (Exception e) {
            System.out.println("Erro ao ler o arquivo XML. Retornando uma central vazia por segurança. Erro: " + e.getMessage());
            return new CentralDeInformacoes();
        }
    }
}