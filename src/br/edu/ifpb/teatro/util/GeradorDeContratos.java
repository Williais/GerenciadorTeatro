package br.edu.ifpb.teatro.util;

import br.edu.ifpb.teatro.dao.CentralDeInformacoes;
import br.edu.ifpb.teatro.model.PropostaDeAluguel;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.table.DefaultTableModel;
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
            documento.add(new Paragraph("CPF do Locatário: " + p.getLocatario().getCpf()));
            documento.add(new Paragraph("Valor do Aluguel: R$ " + p.getValorTotalDoAluguel()));
            documento.add(new Paragraph("Data de Início: " + p.getDataEvento()));

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

    public static void gerarRelatorioFinanceiro(String dataI, String dataF, String receitaTotal,
                                                String totalIngressos, String totalEventos,
                                                javax.swing.table.DefaultTableModel modeloTabela) {

        Document documento = new Document();

        try {
            PdfWriter.getInstance(documento, new FileOutputStream("relatorio_financeiro.pdf"));
            documento.open();


            documento.add(new Paragraph("Relatório Financeiro e de Ocupação do Teatro"));
            documento.add(new Paragraph("Período de Análise: " + dataI + " até " + dataF));
            documento.add(new Paragraph(" "));

            documento.add(new Paragraph("RESUMO GERAL"));
            documento.add(new Paragraph("Receita Bruta Total: " + receitaTotal));
            documento.add(new Paragraph("Ingressos Emitidos: " + totalIngressos));
            documento.add(new Paragraph("Eventos Realizados: " + totalEventos));
            documento.add(new Paragraph(" "));
            documento.add(new Paragraph("DETALHAMENTO POR EVENTO"));
            documento.add(new Paragraph(" "));

            com.itextpdf.text.pdf.PdfPTable tabelaPdf = new com.itextpdf.text.pdf.PdfPTable(4);

            tabelaPdf.addCell("Data / Evento");
            tabelaPdf.addCell("Ingressos");
            tabelaPdf.addCell("Receita");
            tabelaPdf.addCell("Status");

            for (int linha = 0; linha < modeloTabela.getRowCount(); linha++) {
                for (int coluna = 0; coluna < modeloTabela.getColumnCount(); coluna++) {
                    String valorCelula = modeloTabela.getValueAt(linha, coluna).toString();
                    tabelaPdf.addCell(valorCelula);
                }
            }

            documento.add(tabelaPdf);

        } catch (Exception e) {
            System.out.println("Erro ao gerar o relatório PDF: " + e.getMessage());
        } finally {
            if (documento != null && documento.isOpen()) {
                documento.close();
            }
        }
    }
}
