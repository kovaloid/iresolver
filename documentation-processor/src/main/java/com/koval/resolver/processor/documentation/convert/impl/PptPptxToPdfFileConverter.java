package com.koval.resolver.processor.documentation.convert.impl;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;

import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.koval.resolver.processor.documentation.convert.FileConverter;


public class PptPptxToPdfFileConverter implements FileConverter {
  private static final Logger LOGGER = LoggerFactory.getLogger(WordToPdfFileConverter.class);


  @Override
  public void convert(File inputFile) {
    String pptFileName = inputFile.getName();
    String pdfFileName = pptFileName.substring(0, pptFileName.lastIndexOf('.')).concat(".pdf");

    Document document = null;
    PdfWriter pdfWriter = null;

    try (InputStream doc = new FileInputStream(inputFile)) {
      OutputStream out = new FileOutputStream(new File("../docs", pdfFileName));
      document = new Document();

      PdfPTable table = new PdfPTable(1);
      document.open();
      pdfWriter = pdfWriter.getInstance(document, out);
      pdfWriter.open();
      pdfWriter.setCloseStream(false);

      if (inputFile.getName().endsWith(".ppt")) {
        HSLFSlideShow ppt = new HSLFSlideShow(doc);
        doc.close();
        Dimension pgsize = ppt.getPageSize();

        HSLFSlide[] slides = new HSLFSlide[ppt.getSlides().size()];
        ppt.getSlides().toArray(slides);


        document.setPageSize(new Rectangle((float)pgsize.getWidth(), (float)pgsize.getHeight()));
        pdfWriter = PdfWriter.getInstance(document, out);

        pdfWriter.open();
        document.open();

        for (int i = 0; i < slides.length; i++) {
          BufferedImage image = new BufferedImage((int)Math.ceil(pgsize.width), (int)Math.ceil(pgsize.height),
              BufferedImage.TYPE_INT_RGB);

          Graphics2D graphics2D = image.createGraphics();
          AffineTransform affineTransform = new AffineTransform();
          graphics2D.setTransform(affineTransform);

          graphics2D.setColor(Color.white);
          graphics2D.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
          slides[i].draw(graphics2D);
          graphics2D.getPaint();
          Image slideImage = Image.getInstance(image, null);
          table.addCell(new PdfPCell(slideImage, true));
        }
      }

      if (inputFile.getName().endsWith(".pptx")) {
        XMLSlideShow pptx = new XMLSlideShow(doc);
        doc.close();
        Dimension pgsize = pptx.getPageSize();

        XSLFSlide[] slides = new XSLFSlide[pptx.getSlides().size()];
        pptx.getSlides().toArray(slides);

        document.setPageSize(new Rectangle((float)pgsize.getWidth(), (float)pgsize.getHeight()));

        pdfWriter.open();
        document.open();

        for (int i = 0; i < slides.length; i++) {
          BufferedImage image = new BufferedImage((int)Math.ceil(pgsize.width), (int)Math.ceil(pgsize.height),
              BufferedImage.TYPE_INT_RGB);

          Graphics2D graphics2D = image.createGraphics();
          AffineTransform affineTransform = new AffineTransform();
          graphics2D.setTransform(affineTransform);

          graphics2D.setColor(Color.white);
          graphics2D.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
          slides[i].draw(graphics2D);
          graphics2D.getPaint();
          Image slideImage = Image.getInstance(image, null);
          table.addCell(new PdfPCell(slideImage, true));
        }
      }

      document.add(table);
      document.close();
      pdfWriter.close();
      LOGGER.info("PDF file created: " + pdfFileName);

    } catch (IOException | DocumentException e) {
        LOGGER.error("Could not convert word file " + pptFileName + " to pdf " + pdfFileName, e);
    }
  }
}
