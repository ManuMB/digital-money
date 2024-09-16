package com.manumb.digital_money_service.business.accounts.transactions.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.manumb.digital_money_service.business.accounts.transactions.Transaction;
import com.manumb.digital_money_service.business.accounts.transactions.TransactionDirection;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class PdfGenerator {
    public byte[] generateTransactionPdf(Transaction transaction, TransactionDirection direction) throws IOException, DocumentException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, out);
        document.open();

        document.add(new Paragraph("Transaction Details"));
        document.add(new Paragraph("Transaction ID: " + transaction.getId()));
        document.add(new Paragraph("Type: " + transaction.getTransactionType()));
        document.add(new Paragraph("Direction: " + direction));
        document.add(new Paragraph("Amount: " + transaction.getAmount()));
        document.add(new Paragraph("Date: " + transaction.getTransactionDate()));
        document.add(new Paragraph("From Account"));
        document.add(new Paragraph("Name: " + transaction.getFromAccount().getUser().getFullName()));
        document.add(new Paragraph("CVU: " + transaction.getFromAccount().getCvu()));
        document.add(new Paragraph("To Account"));
        document.add(new Paragraph("Name: " + transaction.getToAccount().getUser().getFullName()));
        document.add(new Paragraph("CVU: " + transaction.getToAccount().getCvu()));

        document.close();
        return out.toByteArray();
    }
}
