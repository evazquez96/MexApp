package dream.logsys.com.logsysdream;

import android.content.Context;
import android.content.Context;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.util.Log;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
public class template2 {
    private Context context;
    private File pdf2;
    private Document document;
    private PdfWriter pdfWriter;
    private Paragraph paragraph;

    private Font ftitle=new Font(Font.FontFamily.TIMES_ROMAN,24,Font.BOLDITALIC);
    private Font fstitle=new Font(Font.FontFamily.TIMES_ROMAN,22,Font.BOLDITALIC);
    private Font ftext=new Font(Font.FontFamily.TIMES_ROMAN,15,Font.BOLD);
    private Font fht=new Font(Font.FontFamily.TIMES_ROMAN,15,Font.BOLD);

    public template2(Context context) {

        this.context=context;
    }
    public void opendocument(){
        createfile();
        try {

            document=new Document(PageSize.A4);
            pdfWriter=PdfWriter.getInstance(document,new FileOutputStream(pdf2));
            document.open();

        }catch (Exception e){
            Log.e("open document",e.toString());
        }

    }

    private void createfile(){
        String names=new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new java.util.Date());
        String m=names;
        File folder= new File(Environment.getExternalStorageDirectory().toString(),"PDF");
        //File folder= new File(context.getFilesDir().toString(),"PDF");

        //if(!folder.exists())
        folder.mkdirs();
        pdf2=new File(folder,"respaldo.pdf");

    }
    public void closedocument(){

        document.close();
    }
    public void addMetaData(String title,String subjet,String author){
        document.addTitle(title);
        document.addSubject(subjet);
        document.addAuthor(author);
    }
    public void addtitle(String title,String subtitle,String date){

        try {
            paragraph=new Paragraph();
            addChildp(new Paragraph(title,ftitle));
            addChildp(new Paragraph(subtitle,fstitle));
            addChildp(new Paragraph(date,fht));
            paragraph.setSpacingAfter(30);
            document.add(paragraph);


        }catch (Exception e){
            Log.e("add titles",e.toString());
        }
    }
    private void addChildp(Paragraph childparagraph){
        childparagraph.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
        paragraph.add(childparagraph);

    }
    public void addparagraph(String text){
        try {
            paragraph= new Paragraph(text,ftext);
            paragraph.setSpacingAfter(5);
            paragraph.setSpacingBefore(5);
            document.add(paragraph);


        }catch (Exception e){
            Log.e("addparagraph",e.toString());
        }

    }

    public void createtable(String[]header, ArrayList <String[]>cliet){
        try {
            paragraph=new Paragraph();
            paragraph.setFont(ftext);
            PdfPTable pdfPTable=new PdfPTable(header.length);
            pdfPTable.setWidthPercentage(100);
            PdfPCell pdfPCell;
            int indexc=0;
            while (indexc<header.length){
                pdfPCell=new PdfPCell(new Phrase(header[indexc++],fstitle));
                pdfPCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                pdfPCell.setBackgroundColor(BaseColor.BLUE);
                pdfPTable.addCell(pdfPCell);
            }

            for (int i=0;i<cliet.size();i++){
                String[]row=cliet.get(i);
                for (int j=0;j<cliet.size();j++){
                    pdfPCell= new PdfPCell(new Phrase(row[j]));
                    pdfPCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
                    pdfPCell.setFixedHeight(40);
                    pdfPTable.addCell(pdfPCell);
                }

            }
            paragraph.add(pdfPTable);
            document.add(paragraph);

        }catch (Exception e){
            Log.e("createtable",e.toString());
        }


    }  public void lines(String texto) throws DocumentException {
        Paragraph p = new Paragraph(texto);
        p.getAlignment();
        DottedLineSeparator dottedline = new DottedLineSeparator();
        dottedline.setOffset(-2);
        dottedline.setGap(2f);
        p.add(dottedline);
        document.add(p);
    }



}
