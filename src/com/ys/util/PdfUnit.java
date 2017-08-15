package com.ys.util;  

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;  
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import com.ys.system.common.BusinessConstants;
import com.ys.util.CalendarUtil;
import com.ys.util.TextFooterEventHandler;
  
  
/** 
 *  
 * @Title: 单纯输出PDF报表 
 * @Description: 
 * @Copyright: Copyright (c) 2014 
 * @Company: SinoSoft 
 *  
 * @author: ShaoMin 
 * @version: 1.0 
 * @CreateDate：Nov 5, 2014 
 */  
public class PdfUnit {  
  
    private static PdfFont bfChinese ;
    String pdfPath = "";
    String fileName = "";
    private static String errorImg="E:\\publish\\wtpwebapps\\YS-MES\\images\\errorphoto240.png";
    private static String errorImg2 = "D:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\YS-MES\\images\\errorphoto240.png";
    public String dest="";
    public Document doc;
    PdfDocument pdf;
    private HttpServletResponse response;
    private HttpSession session;
    
    public PdfUnit(){
    	
    }
    
    public PdfUnit(HttpSession session,HttpServletResponse response){
	 this.response = response;
	 this.session = session;
    }
    public PdfUnit(
    		HttpSession session,
    		HttpServletResponse response,
    		String YSId){
    	try {
    		this.response = response;

	        //处理中文问题  
    		bfChinese = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
    		
    		this.pdfPath = session
    				.getServletContext()
    				.getRealPath(BusinessConstants.PATH_PRODUCTDESIGNTEMP);
    		
    		//格式:17YS001_20170811121009.pdf
    		this.fileName = YSId + "_" + CalendarUtil.timeStempDate() + ".pdf";
    		this.dest = pdfPath + File.separator + fileName;
    		
    		PdfWriter writer = new PdfWriter(new FileOutputStream(dest)); 
    		
    		//Initialize PDF document
    		this.pdf = new PdfDocument(writer);
    		this.doc = new Document(pdf, PageSize.A4,true);
    		
	 	   	//设置页码
	 	   	TextFooterEventHandler eh= new TextFooterEventHandler(doc);
	 	   this.pdf.addEventHandler(PdfDocumentEvent.END_PAGE,eh);
	        
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
   
    /** 
     * 创建PDF 
     *  
     * @author ShaoMin 
     * @throws Exception 
     */  
    public void createPdfFile() throws Exception {  
         
        String timeStemp = CalendarUtil.fmtDate().replace(" ", "").replace(":", "").replace("-", "");
       
         
       // FileOutputStream out = new FileOutputStream("temp/pdf/practisePdfFile.pdf");  
       // PdfWriter.getInstance(doc, out);
        //doc..open();  
        
        Text text1 = new Text("人类的海洋").setFont(bfChinese)  
                .setFontSize((float) 7.41)  
                .setFontColor(new DeviceRgb(46, 46, 46)).setBold();// setBold()字体为加粗 
  
        Paragraph title = addTitle("受理材料收取凭证");
        
        doc.add(title);  
  
        StringBuffer strBuff = new StringBuffer();  
        strBuff.append("兹收到客户  " + "SAM-SHO");  
        strBuff.append("（先生/女士）提交的保单号为    " + "123456789009876");  
        strBuff.append("     的申请材料，共   " + " 2 " + "  张保单。");  
  
        Paragraph content;
        content = addContent(strBuff.toString());
       
        doc.add(content);  
  
        String cont2 = "申请保全项目    " + " CM-退保";  
        content = addContent(cont2);
         
        doc.add(content);  
  
        String cont3 = "所提供材料明细如下：";  
        content = addContent(cont3);
        
        //content.setAlignment(Rectangle.ALIGN_JUSTIFIED);  
        //content.setFirstLineIndent(15f);// 首行缩进  
        //content.setSpacingBefore(15f);// 上留白  
        //content.setSpacingAfter(15f);// 下留白  
        doc.add(content);  
  
        // 表格的处理是难点，特别是表格的跨行跨列  
        // 可以使用跨行也可以使用表格嵌套  
        int tCol = 5;  
        Table table = new Table(new float[] { 0.4f, 0.25f, 0.25f, 0.25f, 0.25f })
        		.setWidth(UnitValue.createPercentValue(100))
        		.setHorizontalAlignment(HorizontalAlignment.LEFT)
        		;  
       // table.setHorizontalAlignment(Element.ALIGN_LEFT);  
      //  table.setWidth(500f);  
       // table.setWidthPercent(new float[] { 0.4f, 0.25f, 0.25f, 0.25f, 0.25f });  
       // table.setWidthPercentage(100);  
        //table.setLockedWidth(true);  

        Cell cell = new Cell();  
        String strTableTitle = "申请材料名称";        
        Paragraph tableTitle = new Paragraph(strTableTitle)
        		.setFont(bfChinese)
        		.setFontSize(14)
        		.setBold()
        		.setTextAlignment(TextAlignment.CENTER)
        		.setFirstLineIndent(15f)
        		.setSpacingRatio(15f);

        cell = new Cell(2,1)
        		.add(tableTitle)
        		.setTextAlignment(TextAlignment.CENTER)
        		.setVerticalAlignment(VerticalAlignment.MIDDLE); 
        table.addCell(cell);  
  
        strTableTitle = "申请材料类型";         
        tableTitle = new Paragraph(strTableTitle)
        		.setFont(bfChinese)
        		.setFontSize(14)
        		.setBold()
        		.setTextAlignment(TextAlignment.CENTER)
        		.setFirstLineIndent(15f)
        		.setSpacingRatio(15f); 
        cell = new Cell(1,2)
        		.add(tableTitle)
        		.setTextAlignment(TextAlignment.CENTER)
        		.setVerticalAlignment(VerticalAlignment.MIDDLE); 
        table.addCell(cell);  
  
        strTableTitle = "收取页数/份数";          
        tableTitle = new Paragraph(strTableTitle)
        		.setFont(bfChinese)
        		.setFontSize(14)
        		.setBold()
        		.setTextAlignment(TextAlignment.CENTER)
        		.setFirstLineIndent(15f)
        		.setSpacingRatio(15f); 
        cell = new Cell(2,1)
        		.add(tableTitle)
        		.setTextAlignment(TextAlignment.CENTER)
        		.setVerticalAlignment(VerticalAlignment.MIDDLE); 
        table.addCell(cell);  
  
        strTableTitle = "备注";          
        tableTitle = new Paragraph(strTableTitle)
        		.setFont(bfChinese)
        		.setFontSize(14)
        		.setBold()
        		.setTextAlignment(TextAlignment.CENTER)
        		.setFirstLineIndent(15f)
        		.setSpacingRatio(15f); 
        cell = new Cell(2,1)
        		.add(tableTitle)
        		.setTextAlignment(TextAlignment.CENTER)
        		.setVerticalAlignment(VerticalAlignment.MIDDLE)
        		;
        table.addCell(cell);  
  
        // 这边属于第二行的表格  
        // 思路上，这点很关键  
        strTableTitle = "原件";  
        tableTitle = new Paragraph(strTableTitle)
        		.setFont(bfChinese)
        		.setFontSize(14)
        		.setBold()
        		.setTextAlignment(TextAlignment.CENTER)
        		.setFirstLineIndent(15f)
        		.setSpacingRatio(15f); ;  
        cell = new Cell()
        		.add(tableTitle)
        		.setTextAlignment(TextAlignment.CENTER)
        		.setVerticalAlignment(VerticalAlignment.MIDDLE);
        table.addCell(cell);  
   
        strTableTitle = "复印件";  
        tableTitle = tableTitle(strTableTitle);      
        table.addCell(addCell(tableTitle));  

        String[] arrTitle = { "保险合同", "保险合同收据或发票", "保单贷款申请书", "保全变更申请书", "被保险人变更清单", "健康及财务告知", "授权委托书", "投保人身份证件", "被保险人身份证件", "身故受益人身份证件", "受托人身份证件", "投保人账号", "被保险人/监护人账号 ", "生存证明", "关系证明 ",  
                "其他受理材料 " };  
        int tRow = arrTitle.length;  
  
        for (int i = 0; i < tRow; i++) {  
            for (int j = 0; j < tCol; j++) {  
                if (j == 0) {  
                    // 左侧标题  
                    strTableTitle = arrTitle[i];  
                    tableTitle = new Paragraph(strTableTitle)
                    		.setFont(bfChinese);  
                    cell = new Cell()
                    		.add(tableTitle)
                    		.setTextAlignment(TextAlignment.CENTER)
                    		.setVerticalAlignment(VerticalAlignment.MIDDLE); 
                    if(i%2==0){
                    	cell.setBackgroundColor(new DeviceRgb(221,234,238));
                    	
                    }
                    //cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 水平居中  
                    //cell.setVerticalAlignment(Element.ALIGN_MIDDLE);// 垂直居中  
                    table.addCell(cell);  
                } else {  
                    strTableTitle = i + "--" + j;  
                    tableTitle = new Paragraph(strTableTitle)
                    		.setFont(bfChinese);  
                    cell = new Cell()
                    		.add(tableTitle)
                    		.setTextAlignment(TextAlignment.CENTER)
                    		.setVerticalAlignment(VerticalAlignment.MIDDLE);  
                    if(i%2==0){
                    	cell.setBackgroundColor(new DeviceRgb(221,234,238));
                    	
                    }
                    
                    //cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 水平居中  
                    //cell.setVerticalAlignment(Element.ALIGN_MIDDLE);// 垂直居中  
                    table.addCell(cell);  
                }  
            }  
        }  
        table.setSpacingRatio(15f);// 下留白  
        doc.add(table);  
  
        // 所有的都要指定中文，不然显示不出来  
        // Phrase没有位置的操作,但是空格会被保留  
        // 也可以使用表格处理，隐藏边框即可  
       // Phrase tPhrase = new Phrase("    申请人:                                     " + "                                         " + "                                         " + "保全试算金额:",  
       // 		bfChinese);  
       // Paragraph tParagraph = new Paragraph(tPhrase);  
        //doc.add(tParagraph);  
  
        // 尾部表格处理  
        Table footTable = new Table(new float[] { 4.6f, 1f })
        		.setWidth(760f)
        		.setHorizontalAlignment(HorizontalAlignment.CENTER);  
        //footTable.setTotalWidth(760f);  
        //footTable.setWidths(new float[] { 4.6f, 1f });  
        //footTable.setHorizontalAlignment(Element.ALIGN_LEFT);  
  
        strTableTitle = "   申请日期：";  
        tableTitle = new Paragraph(strTableTitle);  
        cell = new Cell()
        		.add(tableTitle)
        		.setTextAlignment(TextAlignment.CENTER)
        		.setVerticalAlignment(VerticalAlignment.MIDDLE)
        		.setBorder(Border.NO_BORDER);   
        //cell.setHorizontalAlignment(Element.ALIGN_LEFT);// 水平居中  
        //cell.setVerticalAlignment(Element.ALIGN_MIDDLE);// 垂直居中  
        //cell.setBorderWidth(0);  
        footTable.addCell(cell);  
  
        strTableTitle = "受理人：";
        tableTitle = new Paragraph(strTableTitle);  
        cell = new Cell()
        		.add(tableTitle)
        		.setTextAlignment(TextAlignment.LEFT)
        		.setVerticalAlignment(VerticalAlignment.MIDDLE)
        		.setBorder(Border.NO_BORDER); 
        footTable.addCell(cell);  
  
        strTableTitle = "   申请人电话："; 
        tableTitle = new Paragraph(strTableTitle);  
        cell = new Cell()
        		.add(tableTitle)
        		.setTextAlignment(TextAlignment.LEFT)
        		.setVerticalAlignment(VerticalAlignment.MIDDLE)
        		.setBorder(Border.NO_BORDER);  
        footTable.addCell(cell);  
  
        strTableTitle = "受理日期：";  
        tableTitle = new Paragraph(strTableTitle);  
        cell = new Cell()
        		.add(tableTitle)
        		.setTextAlignment(TextAlignment.LEFT)
        		.setVerticalAlignment(VerticalAlignment.MIDDLE)
        		.setBorder(Border.NO_BORDER);   
        footTable.addCell(cell);  
  
        doc.add(footTable);  
          /*
        tParagraph = new Paragraph();  
        Chunk tChunk = new Chunk("   说明:  ");  
        tParagraph.add(tChunk);  
          
        tChunk = new Chunk("                                                                               ",conyentFont);  
        tChunk.setUnderline(0.1f, -2f);  
        tParagraph.add(tChunk);  
        tChunk = new Chunk("                                                                           ",conyentFont);  
        tChunk.setUnderline(0.1f, -2f);  
        tParagraph.add(tChunk);  
        tChunk = new Chunk("                                                                        ",conyentFont);  
        tChunk.setUnderline(0.1f, -2f);  
        tParagraph.add(tChunk);  
          
        tParagraph.setLeading(30f);  
        tChunk = new Chunk("                                                                          ",conyentFont);  
        tChunk.setUnderline(0.1f, -2f);  
        tParagraph.add(tChunk);  
        tChunk = new Chunk("                                                                          ",conyentFont);  
        tChunk.setUnderline(0.1f, -2f);  
        tParagraph.add(tChunk);  
          
        doc.add(tParagraph);  
          */
        doc.close();  
        System.out.println("结束.....");  
    }  
  
    /** 
     *  
     * @author ShaoMin 
     *  
     */  /*
    public void createPDFFile() {  
  
        Document document = new Document(PageSize.A4, 80, 79, 20, 45); // A4纸大小,//  
                                                                       // 左、右、上、下  
        try {  
            // 中文处理  
            BaseFont bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);  
  
            Font FontChinese = new Font(bfChinese, 14, Font.COURIER); // 其他所有文字字体  
            Font BoldChinese = new Font(bfChinese, 14, Font.BOLD); // 粗体  
            Font titleChinese = new Font(bfChinese, 20, Font.BOLD); // 模板抬头的字体  
            Font moneyFontChinese = new Font(bfChinese, 8, Font.COURIER); // 币种和租金金额的小一号字体  
            Font subBoldFontChinese = new Font(bfChinese, 8, Font.BOLD); // 币种和租金金额的小一号字体  
  
            // 使用PDFWriter进行写文件操作  
            PdfWriter.getInstance(document, new FileOutputStream("temp/pdf/pdfFile.pdf"));  
            document.open(); // 打开文档  
  
            // ------------开始写数据-------------------  
            Paragraph title = new Paragraph("起租通知书", titleChinese);// 抬头  
            title.setAlignment(Element.ALIGN_CENTER); // 居中设置  
            title.setLeading(1f);// 设置行间距//设置上面空白宽度  
            document.add(title);  
  
            title = new Paragraph("致：XXX公司", BoldChinese);// 抬头  
            title.setSpacingBefore(25f);// 设置上面空白宽度  
            document.add(title);  
  
            title = new Paragraph("         贵我双方签署的编号为 XXX有关起租条件已满足，现将租赁合同项下相关租赁要素明示如下：", FontChinese);  
            title.setLeading(22f);// 设置行间距  
            document.add(title);  
  
            float[] widths = { 10f, 25f, 30f, 30f };// 设置表格的列宽和列数 默认是4列  
  
            PdfPTable table = new PdfPTable(widths);// 建立一个pdf表格  
            table.setSpacingBefore(20f);// 设置表格上面空白宽度  
            table.setTotalWidth(500);// 设置表格的宽度  
            table.setWidthPercentage(100);// 设置表格宽度为%100  
            // table.getDefaultCell().setBorder(0);//设置表格默认为无边框  
  
            String[] tempValue = { "1", "2011-07-07", "2222元", "233元", "2014-12-22", "3000元", "9999元" }; // 租金期次列表  
            int rowCount = 1; // 行计数器  
            PdfPCell cell = null;  
            // ---表头  
            cell = new PdfPCell(new Paragraph("期次", subBoldFontChinese));// 描述  
            cell.setFixedHeight(20);  
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示  
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 设置垂直居中  
            table.addCell(cell);  
            cell = new PdfPCell(new Paragraph("租金日", subBoldFontChinese));// 描述  
            cell.setFixedHeight(20);  
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示  
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 设置垂直居中  
            table.addCell(cell);  
            cell = new PdfPCell(new Paragraph("各期租金金额", subBoldFontChinese));// 描述  
            cell.setFixedHeight(20);  
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示  
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 设置垂直居中  
            table.addCell(cell);  
  
            cell = new PdfPCell(new Paragraph("各期租金后\n剩余租金", subBoldFontChinese));// 描述  
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示  
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 设置垂直居中  
            cell.setFixedHeight(20);  
            table.addCell(cell);  
  
            for (int j = 1; j < tempValue.length; j++) {  
                if (j % 2 == 1) { // 第一列 日期  
                    cell = new PdfPCell(new Paragraph(rowCount + "", moneyFontChinese));// 描述  
                    cell.setFixedHeight(20);  
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示  
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 设置垂直居中  
                    table.addCell(cell);  
                    rowCount++;  
                }  
                cell = new PdfPCell(new Paragraph(tempValue[j], moneyFontChinese));// 描述  
                cell.setFixedHeight(20);  
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示  
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 设置垂直居中  
                table.addCell(cell);  
            }  
            document.add(table);  
  
            title = new Paragraph("                租金总额：XXX", FontChinese);  
            title.setLeading(22f);// 设置行间距  
            document.add(title);  
            title = new Paragraph("         特此通知！", FontChinese);  
            title.setLeading(22f);// 设置行间距  
            document.add(title);  
            // -------此处增加图片和日期，因为图片会遇到跨页的问题，图片跨页，图片下方的日期就会脱离图片下方会放到上一页。  
            // 所以必须用表格加以固定的技巧来实现  
            float[] widthes = { 50f };// 设置表格的列宽和列数  
            PdfPTable hiddenTable = new PdfPTable(widthes);// 建立一个pdf表格  
            hiddenTable.setSpacingBefore(11f); // 设置表格上空间  
            hiddenTable.setTotalWidth(500);// 设置表格的宽度  
            hiddenTable.setWidthPercentage(100);// 设置表格宽度为%100  
            hiddenTable.getDefaultCell().disableBorderSide(1);  
            hiddenTable.getDefaultCell().disableBorderSide(2);  
            hiddenTable.getDefaultCell().disableBorderSide(4);  
            hiddenTable.getDefaultCell().disableBorderSide(8);  
  
            Image upgif = Image.getInstance("source/imag/bage.png");  
            upgif.scalePercent(7.5f);// 设置缩放的百分比%7.5  
            upgif.setAlignment(Element.ALIGN_RIGHT);  
  
            cell = new PdfPCell(new Paragraph("", FontChinese));// 描述  
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);// 设置内容水平居中显示  
            cell.addElement(upgif);  
            cell.setPaddingTop(0f); // 设置内容靠上位置  
            cell.setPaddingBottom(0f);  
            cell.setPaddingRight(20f);  
            cell.setBorder(Rectangle.NO_BORDER);// 设置单元格无边框  
            hiddenTable.addCell(cell);  
  
            cell = new PdfPCell(new Paragraph("XX 年 XX 月 XX 日                    ", FontChinese));// 金额  
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);// 设置内容水平居中显示  
            cell.setPaddingTop(0f);  
            cell.setPaddingRight(20f);  
            cell.setBorder(Rectangle.NO_BORDER);  
            hiddenTable.addCell(cell);  
            document.add(hiddenTable);  
            System.out.println("拼装起租通知书结束...");  
            document.close();  
        } catch (DocumentException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
  */
    /** 
     * 支持中文 
     *  
     * @return 
     */  /*
    public Font getChineseFont() {  
        PdfFont bfChinese;  
        Font fontChinese = null;  
        try {  
            bfChinese = PdfFont.createFont("STSongStd-Light", "UniGB-UCS2-H", false);  
            // fontChinese = new Font(bfChinese, 12, Font.NORMAL);  
            fontChinese = new Font(bfChinese, 12, Font.NORMAL, BaseColor.BLUE);  
        } catch (DocumentException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return fontChinese;  
  
    }  
  */
    

	/** 
	 * Table
	 *  
	 * @return  Paragraph
	 */  
    public Table addTable( UnitValue[] unitValue) {  
        
    	Table table = new Table(unitValue)
        		.setWidthPercent(100)
        		.setHorizontalAlignment(HorizontalAlignment.LEFT)
        		;    	
        return table;   
    } 
    
	/** 
	 * tableTitle
	 *  
	 * @return  Paragraph
	 */  
    public Paragraph tableTitleRight(String strTableTitle) {  
        
    	Paragraph tableTitle = new Paragraph(strTableTitle)
       		.setFont(bfChinese)
       		.setFontSize(8)
       		//.setBold()
       		.setTextAlignment(TextAlignment.RIGHT)
       		//.setFirstLineIndent(15f)
       		////.setSpacingRatio(15f)
       		; 
    	
        return tableTitle;   
    } 
    
    /** 
	 * tableTitle
	 *  
	 * @return  Paragraph
	 */  
    public Paragraph tableTitle(String strTableTitle) {  
        
    	Paragraph tableTitle = new Paragraph(strTableTitle)
       		.setFont(bfChinese)
       		.setFontSize(8)
       		//.setBold()
       		.setTextAlignment(TextAlignment.LEFT)
       		//.setFirstLineIndent(15f)
       		////.setSpacingRatio(15f)
       		; 
    	
        return tableTitle;   
    } 
    
	/** 
	 * Cell
	 *  
	 * @return Cell
	 */ 
    public Cell addCell(Paragraph tableTitle) {  
        
    	Cell cell = new Cell()
         		.add(tableTitle)
         		
         		//.setTextRenderingMode(TextRenderingMode.FILL_STROKE)
         		.setTextAlignment(TextAlignment.LEFT)
         		//.setVerticalAlignment(VerticalAlignment.MIDDLE)
         		//.setBorder(Border.NO_BORDER)
         		;   
    	
        return cell;   
    }
    
    /** 
	 * Cell
	 *  
	 * @return Cell
     * @throws MalformedURLException 
	 */ 
    public Image addLargeImage(String path) throws MalformedURLException {  

    	Image img = new Image(ImageDataFactory.create(path))
				.scaleToFit(500, 200)
				.setHorizontalAlignment(HorizontalAlignment.CENTER)
         		;    	
        return img;   
    }
    
    /** 
 	 * Image
 	 *  
 	 * @return Image
      * @throws MalformedURLException 
 	 */ 
     public Image addImage(String path) {
     	Image img = null;
		try {
			img = new Image(ImageDataFactory.create(path))
					//.setMaxHeight(180)
					.scaleToFit(250, 180)
					.setHorizontalAlignment(HorizontalAlignment.CENTER);
		} catch (Exception e) {
				e.printStackTrace();
			try {
				img = new Image(ImageDataFactory.create(errorImg))
				.setHorizontalAlignment(HorizontalAlignment.CENTER);
			} catch (Exception e1) {
				try {
					img = new Image(ImageDataFactory.create(errorImg2))
					.setHorizontalAlignment(HorizontalAlignment.CENTER);
				} catch (Exception e2) {
					try {
						img = new Image(ImageDataFactory.create(errorImg))
								.setHorizontalAlignment(HorizontalAlignment.CENTER);
					} catch (Exception e3) {
						e3.printStackTrace();
					}
					e2.printStackTrace();
				}
			}
			//e.printStackTrace();
		}     	
         return img;
     }
     
    /** 
	 * Cell
	 *  
	 * @return Cell
	 */ 
    public Cell addCell(Image img) {  
        
    	Cell cell = new Cell()
         		.add(img)
         		.setTextAlignment(TextAlignment.CENTER)
         		.setHorizontalAlignment(HorizontalAlignment.CENTER)
         		.setVerticalAlignment(VerticalAlignment.MIDDLE)
         		//.setWidth(50)
         		//.setHeight(250)
         		;   
    	
        return cell;   
    }
    
   	/** 
   	 * Cell
   	 *  
   	 * @return Cell
   	 */ 
       public Cell addCell(String tableTitle) {  
           
       	Cell cell = new Cell()
            		.add(tableTitle)            		
            		//.setTextRenderingMode(TextRenderingMode.FILL_STROKE)
            		.setTextAlignment(TextAlignment.LEFT)
            		//.setVerticalAlignment(VerticalAlignment.MIDDLE)
            		//.setBorder(Border.NO_BORDER)
            		;   
       	
           return cell;   
       }
    
   	/** 
   	 * Cell
   	 *  
   	 * @return Cell
   	 */ 
       public Cell addCellEven(Paragraph tableTitle) {  
           
       	Cell cell = new Cell()
            		.add(tableTitle)
            		.setTextAlignment(TextAlignment.LEFT)
            		//.setVerticalAlignment(VerticalAlignment.MIDDLE)
            		//.setBorder(Border.SOLID)
            		.setBackgroundColor(new DeviceRgb(221,234,238))
            		;   
       	
           return cell;   
       }
       
  	/** 
  	 * Cell
  	 *  
  	 * @return Cell
  	 */ 
	public Cell addCellForNumber(Paragraph tableTitle) {  
	      
	  	Cell cell = new Cell()
	       		.add(tableTitle)
	       		.setTextAlignment(TextAlignment.RIGHT)
	       		//.setVerticalAlignment(VerticalAlignment.MIDDLE)
	       		//.setBorder(Border.SOLID)
	       		;   
	  	
	  	return cell;   
	}
	 
  	/** 
  	 * content
  	 *  
  	 * @return Paragraph
  	 */ 
	public Paragraph addContent(String cont) {  
		
		Paragraph content = new Paragraph(cont)
        		.setFont(bfChinese)
        		.setFontSize(8)
        		//.setBold()
        		.setTextAlignment(TextAlignment.JUSTIFIED)
        		//.setFirstLineIndent(15f)
        		//.setSpacingRatio(15f)
	       		;   
	  	
	  	return content;   
	}
	
	/** 
  	 * content
  	 *  
  	 * @return Paragraph
  	 */ 
	public Paragraph addTitleForCenter(String cont) {  

        Paragraph title = new Paragraph(cont)
        		.setFont(bfChinese)
        		.setBold()
        		.setFontSize(12)
        		.setTextAlignment(TextAlignment.CENTER)
        		; 
	  	
	  	return title;   
	}
	
	/** 
  	 * content
  	 *  
  	 * @return Paragraph
  	 */ 
	public Paragraph addTitle(String cont) {  

        Paragraph title = new Paragraph(cont)
        		.setFont(bfChinese)
        		.setBold()
        		.setFontSize(8)
        		.setTextAlignment(TextAlignment.LEFT)
        		; 
	  	
	  	return title;   
	}
	
	public void downloadFile() throws IOException{
		
		//设置响应头，控制浏览器下载该文件
		response.setHeader(
				"content-disposition",
				"attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
		//读取要下载的文件，保存到文件输入流
		FileInputStream in = new FileInputStream(dest);
		//创建输出流
		OutputStream out = response.getOutputStream();
		//创建缓冲区
		byte buffer[] = new byte[BusinessConstants.MAX_BUFFER_SIZE];
		int len = 0;
		//循环将输入流中的内容读取到缓冲区当中
		while((len=in.read(buffer))>0){
			//输出缓冲区的内容到浏览器，实现文件下载
			out.write(buffer, 0, len);
		}
		
		in.close();//关闭文件输入流		
		out.close();//关闭输出流
		
		//删除临时文件
		File file = new File(dest);
		file.delete();
	}
	
	public void downloadFile(String dest,String fileName) throws IOException{
			
		this.dest = dest;
		this.fileName = fileName;
		downloadFile();
	}
}  