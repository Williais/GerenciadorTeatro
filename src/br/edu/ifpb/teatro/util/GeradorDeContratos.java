package br.edu.ifpb.teatro.util;

import br.edu.ifpb.teatro.dao.CentralDeInformacoes;
import br.edu.ifpb.teatro.model.PropostaDeAluguel;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;


public class GeradorDeContratos {

    public static void obterContrato(long idProposta, CentralDeInformacoes info){

        PropostaDeAluguel p = info.recuperarPropostaPorId(idProposta);

        if(p == null){
            System.out.println("Proposta inexistente");
            return;
        }

        Document documento = new Document();

        try{
            PdfWriter.getInstance(documento, new FileOutputStream("contrato.pdf"));

            documento.open();

            documento.add(new Paragraph("Contrato de Aluguel Do Teatro"));
            documento.add(new Paragraph(" "));

            documento.add(new Paragraph("Nome da Peça: " + p.getNomeDaPeca()));
            documento.add(new Paragraph("Locatário: " + p.getLocatario().getNome()));
            documento.add(new Paragraph("CPF do Locatário: " + p.getLocatario().getCpf())); //seria bom a gente fazer a validação do cpf depois para ser um adicionar para nosso projeto
            documento.add(new Paragraph("Valor do Aluguel: R$ " + p.getValorTotalDoAluguel()));
            documento.add(new Paragraph("Data de Início: " + p.getDataDeInicioDoAluguel()));

            documento.add(new Paragraph(" "));
            documento.add(new Paragraph("Assinatura: " + p.getLocatario().getNome()));

        }catch (Exception e){
            System.out.println("erro ao gerar o PDF: " + e.getMessage());
        }finally {
            if(documento != null && documento.isOpen()){
                documento.close();
            }
        }
    }
}
