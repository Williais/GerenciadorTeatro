package br.edu.ifpb.teatro.util;

import br.edu.ifpb.teatro.model.Ingresso;
import br.edu.ifpb.teatro.model.Pessoa;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.List;

import java.io.FileOutputStream;

public class GeradorDeIngressos {
    public static void gerarPdfDaVenda(Pessoa comprador, List<Ingresso> ingressosComprados, float totalPago) {

        Document documento = new Document();

        try {

            String nomeArquivo = "Ingressos_" + LimpadorCPF.limparCPF(comprador.getCpf()) + ".pdf";

            PdfWriter.getInstance(documento, new FileOutputStream(nomeArquivo));
            documento.open();

            documento.add(new Paragraph("RECIBO E INGRESSOS OFICIAIS"));

            documento.add(new Paragraph("DADOS DO COMPRADOR"));
            documento.add(new Paragraph("Nome: " + comprador.getNome()));
            documento.add(new Paragraph("CPF: " + comprador.getCpf()));
            documento.add(new Paragraph(String.format("Valor Total Pago: R$ %.2f", totalPago)));
            documento.add(new Paragraph(" "));
            documento.add(new Paragraph("=========================================="));
            documento.add(new Paragraph(" "));

            documento.add(new Paragraph("DETALHAMENTO DOS INGRESSOS"));
            documento.add(new Paragraph(" "));

            for (Ingresso ing : ingressosComprados) {
                documento.add(new Paragraph("PEÇA: " + ing.getEvento().getNomeDaPeca()));
                documento.add(new Paragraph("Data do Evento: " + ing.getEvento().getDataEvento()));
                documento.add(new Paragraph("Tipo: " + ing.getTipo()));
                documento.add(new Paragraph("Quantidade Comprada: " + ing.getQuantidade()));
                documento.add(new Paragraph(String.format("Valor Unitário (com descontos): R$ %.2f", ing.getValorIngresso())));
                documento.add(new Paragraph("Emitido em: " + ing.getDataEmissao()));
                documento.add(new Paragraph("------------------------------------------"));
                documento.add(new Paragraph(" "));
            }

        } catch (Exception e) {
            System.out.println("erro ao gerar oa PDF dos ingressos: " + e.getMessage());
        } finally {
            if (documento != null && documento.isOpen()) {
                documento.close();
            }
        }
    }
}
