package me.june.mvc.view;

import com.lowagie.text.Chapter;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * IText 를 활용하는 추상화 뷰
 * Excel/JExcel 등도 제공ㅎ나다.
 */
public class HelloPdfView extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(
            Map<String, Object> model,
            Document document,
            PdfWriter writer,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws Exception {
        // ... pdf 객체 생성
        Chapter chapter = new Chapter(new Paragraph("Spring Message"), 1);
        chapter.add(new Paragraph((String) model.get("message")));
        document.add(chapter);
    }
}
