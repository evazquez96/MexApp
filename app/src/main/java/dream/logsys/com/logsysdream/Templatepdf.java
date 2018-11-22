package dream.logsys.com.logsysdream;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Document;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
public class Templatepdf {

    private Context context;
    private File pdf2;
    private Document document;
    private PdfWriter pdfWriter;
    private Paragraph paragraph;
    private Font ftitle=new Font(Font.FontFamily.TIMES_ROMAN,20,Font.BOLDITALIC);
    private Font fstitle=new Font(Font.FontFamily.TIMES_ROMAN,24,Font.BOLDITALIC);
    private Font ftext=new Font(Font.FontFamily.TIMES_ROMAN,15);
    private Font fht=new Font(Font.FontFamily.TIMES_ROMAN,12);
    private Font fht1=new Font(Font.FontFamily.TIMES_ROMAN,16,Font.BOLD);

    public Templatepdf(Context context) {

        this.context=context;
    }


    public void opendocument(){

        try {

            document=new Document(PageSize.A4);
            pdfWriter=PdfWriter.getInstance(document,new FileOutputStream(pdf2));
            document.open();

        }catch (Exception e){
            Log.e("open document",e.toString());
        }

    }

    public void createfile(String nombres){

        File folder= new File(Environment.getExternalStorageDirectory().toString(),"PDF");
        //File folder= new File(context.getFilesDir().toString(),"PDF");

        //if(!folder.exists())
       folder.mkdirs();
        pdf2=new File(folder,nombres+".pdf");

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
        ftitle.setColor(191,147,13);//(191,147,13);
        fstitle.setColor(128,0,0);
        fht.setColor(4,46,94);
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
        childparagraph.setAlignment(Element.ALIGN_RIGHT);
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
    public void addparagraph1(String text){

        ftext.setColor(73,103,141);
        try {
            paragraph= new Paragraph(text,ftext);
            paragraph.setSpacingAfter(5);
            paragraph.setSpacingBefore(5);
            paragraph.setAlignment(Element.ALIGN_LEFT);
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


    }
    public void lines(String texto) throws DocumentException {

        fht1.setColor(128,0,0);
        Paragraph p = new Paragraph(texto,fht1);
        p.setAlignment(Element.ALIGN_LEFT);
        DottedLineSeparator dottedline = new DottedLineSeparator();
        dottedline.setOffset(-2);
        dottedline.setGap(2f);
        p.add(dottedline);
        document.add(p);
    }



    public void imagenes()  throws IOException, DocumentException {
        String hola;
        Drawable image  = ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.logo_mexamerik);
        hola= String.valueOf(image);
        Image imagen = Image.getInstance(image.toString());
        imagen.setAlignment(Element.ALIGN_CENTER);
        document.add(imagen);
        imagen.setAbsolutePosition(150f, 650f);
        imagen.setAbsolutePosition(0f, 0f);

    }
    public  void titulos(String texto)throws DocumentException{
        fstitle.setColor(191,147,13);
        Paragraph p = new Paragraph(texto,fht1);
        p.setAlignment(Element.ALIGN_LEFT);
        document.add(p);

    }

}
