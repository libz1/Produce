

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jxl.Workbook;
import jxl.write.BorderLineStyle;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class JFXMain extends Application {
	boolean is_debug = true;
    String ver = "0.01", systemName = "�ʲ��������ϵͳ";

    int WINDOWWIDTH = 700, WINDOWHEIGHT = 500;
    TextField tf_thingNo,tf_allNum,tf_BeginNo,tf_EndNo,tf_numPerBox;

    @Override
    public void start(Stage primaryStage) throws Exception {

        {
            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(25, 25, 25, 25));
            Scene scene = new Scene(grid, 400, 300);
            primaryStage.setScene(scene);

            Text scenetitle = new Text(systemName+"  ver " + ver);
            scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
            grid.add(scenetitle, 0, 0, 2, 1);

            Label label_x = new Label("��Ʒ���ϱ���");
            grid.add(label_x, 0, 1);
            tf_thingNo = new TextField();
            if (is_debug)
            	tf_thingNo.setText("123456");
            grid.add(tf_thingNo, 1, 1);
            tf_thingNo.requestFocus();

            label_x = new Label("��Ʒ������");
            grid.add(label_x, 0, 2);
            tf_allNum = new TextField();
            if (is_debug)
            	tf_allNum.setText("16");
            grid.add(tf_allNum, 1, 2);

            label_x = new Label("��ʼ��");
            grid.add(label_x, 0, 3);
            tf_BeginNo = new TextField();
            if (is_debug)
            	tf_BeginNo.setText("3530054005400010223805");
            tf_BeginNo.setMinWidth(200);
//            tf_BeginNo.setText("12345678901234567890");
            grid.add(tf_BeginNo, 1, 3);

            // ����ʹ�ý����ţ����û�н����ţ���ʹ�ò�Ʒ��������Ϣ
            label_x = new Label("������");
            grid.add(label_x, 0, 4);
            tf_EndNo = new TextField();
            if (is_debug)
            	tf_EndNo.setText("3530054005400010223905");
            grid.add(tf_EndNo, 1, 4);

            label_x = new Label("ÿ������");
            grid.add(label_x, 0, 5);
            tf_numPerBox = new TextField();
            if (is_debug)
            	tf_numPerBox.setText("4");
            grid.add(tf_numPerBox, 1, 5);

            // ����λ��Ч�������������
//          CheckBox cb2 = new CheckBox("����У��λ");
//          cb2.setSelected(true);
//          grid.add(cb2, 1, 6);

          Button btn = new Button("�����б�");
          HBox hbBtn = new HBox(10);
          hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
          hbBtn.getChildren().add(btn);// ����ť�ؼ���Ϊ�ӽڵ�
          grid.add(hbBtn, 1, 7);// ��HBox pane�ŵ�grid�еĵ�1�У���4��
          btn.setOnAction((event) -> dealData());

            primaryStage.setTitle(systemName);

            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {

                    // ����ر��˴˴��ڣ���ȫ���رճ���
                    Platform.exit();
                    System.exit(0);
                }
            });

            primaryStage.show();

        }
    }

    private Object dealData() {
//    	System.out.println("clicked!");

    	WritableWorkbook workbook;
    	String dateStr,filename;
		try {
			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
			dateStr = format.format(LocalDateTime.now());
			filename = "D:\\�ʲ�����б�"+dateStr+".xls";
			workbook = Workbook.createWorkbook(new File(filename));

	    	WritableSheet sheet = workbook.createSheet("First Sheet", 0);

	    	// ��1��
	    	WritableFont bold = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD);
	    	WritableCellFormat titleFormate = new WritableCellFormat(bold);//����һ����Ԫ����ʽ���ƶ���
	        titleFormate.setAlignment(jxl.format.Alignment.CENTRE);//��Ԫ���е�����ˮƽ�������
	        titleFormate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//��Ԫ������ݴ�ֱ�������
	        titleFormate.setBorder(jxl.format.Border.TOP, BorderLineStyle.THIN);
	        titleFormate.setBorder(jxl.format.Border.RIGHT, BorderLineStyle.THIN);
	        titleFormate.setBorder(jxl.format.Border.BOTTOM.RIGHT, BorderLineStyle.THIN);
	        titleFormate.setBorder(jxl.format.Border.LEFT, BorderLineStyle.THIN);

	    	jxl.write.Label label = new jxl.write.Label(0, 0, "�ʲ�����б�",titleFormate);
	    	sheet.addCell(label);
	    	//��Ӻϲ���Ԫ�񣬵�һ����������ʼ�У��ڶ�����������ʼ�У���������������ֹ�У����ĸ���������ֹ��
	    	sheet.mergeCells(0, 0, 3, 0);

	    	WritableFont Bwf = new WritableFont(WritableFont.ARIAL, 9,WritableFont.NO_BOLD, false);
	    	jxl.write.WritableCellFormat CBwcfF = new jxl.write.WritableCellFormat(Bwf);
	    	CBwcfF.setAlignment(jxl.write.Alignment.CENTRE);
//	    	CBwcfF.setVerticalAlignment(VerticalAlignment.CENTRE);
	    	CBwcfF.setBorder(jxl.format.Border.TOP, BorderLineStyle.THIN);
	    	CBwcfF.setBorder(jxl.format.Border.RIGHT, BorderLineStyle.THIN);
	    	CBwcfF.setBorder(jxl.format.Border.BOTTOM, BorderLineStyle.THIN);
	    	CBwcfF.setBorder(jxl.format.Border.LEFT, BorderLineStyle.THIN);

	    	// ��2��
	    	int col = 0;
	    	label = new jxl.write.Label(col, 1, "",CBwcfF);
	    	sheet.addCell(label);

	    	col++;
	    	label = new jxl.write.Label(col, 1, "",CBwcfF);
	    	sheet.addCell(label);

	    	col++;
	    	label = new jxl.write.Label(col, 1, "��Ӧ���ϱ���",CBwcfF);
	    	sheet.addCell(label);

	    	col++;
	    	label = new jxl.write.Label(col, 1, tf_thingNo.getText(),CBwcfF);
	    	sheet.addCell(label);

	    	// ��3��
	    	col = 0;
	    	label = new jxl.write.Label(col, 2, "���",CBwcfF);
	    	sheet.addCell(label);

	    	col++;
	    	label = new jxl.write.Label(col, 2, "�ʲ����",CBwcfF);
	    	sheet.addCell(label);

	    	col++;
	    	label = new jxl.write.Label(col, 2, "�����",CBwcfF);
	    	sheet.addCell(label);

	    	col++;
	    	label = new jxl.write.Label(col, 2, "����",CBwcfF);
	    	sheet.addCell(label);

	    	// ���µ���
	    	Long long_begin = (long)0;
	    	Long long_end = (long)0;

	    	String begin = tf_BeginNo.getText();
	    	if (begin.length() == 22)
	    		begin = begin.substring(0,21);
	    	String end = tf_EndNo.getText();

	    	String prefix = begin.substring(0,7);
	    	begin = begin.substring(7);
	    	long_begin = Long.parseLong(begin);
	    	if (end.equals("")){
	    		String allnum = tf_allNum.getText();
	    		if (allnum.equals(""))
	    			allnum = "1";
	    		long_end = long_begin + Long.parseLong(allnum)-1;
	    	}
	    	else{
		    	if (end.length() == 22)
		    		end = end.substring(0,21);
		    	end = end.substring(7);
		    	long_end = Long.parseLong(end);
	    	}

	    	Integer boxNo = 0, numPerBox = Integer.parseInt(tf_numPerBox.getText());

	    	Integer row = 3,num = 1;
	    	String col1 = "";

	    	for( Long i=long_begin;i<=long_end;i++ ){
	    		if (num % numPerBox ==1 ) boxNo++;
	    		col1 = "00000000000000"+i.toString();
	    		col1 = col1.substring(col1.length()-14,col1.length());
	    		col1 = prefix+col1;
	    		if (col1.length()==21)
	    			col1 += getCS(col1);
	    		col = 0;
		    	label = new jxl.write.Label(col,row,num.toString() ,CBwcfF);
		    	sheet.addCell(label);
	    		col++;
		    	label = new jxl.write.Label(col,row,col1 ,CBwcfF);
		    	sheet.addCell(label);
	    		col++;
		    	label = new jxl.write.Label(col, row, boxNo.toString(),CBwcfF);
		    	sheet.addCell(label);
	    		col++;
		    	label = new jxl.write.Label(col, row, "",CBwcfF);
		    	sheet.addCell(label);
		    	row++;
	    		num++;
	    	}


//	    	jxl.write.Number number = new jxl.write.Number(3, 5, 3.1459);//�ڵ�4�е�6���в���3.1459�����֣���Ϊ�к��е��±궼�Ǵ�0��ʼ�ģ����ҵ�һ����ʾ�У��ڶ�����ʾ��
//	    	sheet.addCell(number);
//	    	sheet.setRowView(0, 600, false);

//	    	WritableFont Bwf = new WritableFont(WritableFont.ARIAL, 12,WritableFont.NO_BOLD, false);
//	    	jxl.write.WritableCellFormat CBwcfF = new jxl.write.WritableCellFormat(Bwf);
//	    	CBwcfF.setAlignment(jxl.write.Alignment.CENTRE);
//	    	//���ô�ֱ����Ϊ���ж���
//	    	CBwcfF.setVerticalAlignment(VerticalAlignment.CENTRE);
//	    	//���ö����߿���Ϊʵ��(Ĭ���Ǻ�ɫ����Ҳ��������������ɫ)
//	    	CBwcfF.setBorder(jxl.format.Border.TOP, BorderLineStyle.MEDIUM);
//	    	//�����ұ߿���Ϊʵ��
//	    	CBwcfF.setBorder(jxl.format.Border.RIGHT, BorderLineStyle.MEDIUM);

	    	sheet.setRowView(0, 300);
	    	sheet.setRowView(1, 300);
	    	sheet.setRowView(2, 300);

	    	sheet.setColumnView(0, 10);
	    	sheet.setColumnView(1, 25);
	    	sheet.setColumnView(2, 15);
	    	sheet.setColumnView(3, 15);
	    	workbook.write();
	    	workbook.close();
	    	javafxutil.f_alert_informationDialog("�������", "��������"+filename);

		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
	}

    private static String getCS(String data){
    	Integer d1,sum=0;
    	for(int i=0;i<data.length();i++){
			d1 = Integer.parseInt(data.substring(i,i+1));
    		if (i % 2 == 0)
    			sum += d1*3;
    		else
    			sum += d1*1;
    	}
    	d1 = (10 -sum % 10) %10 ;
    	return d1.toString();
    }

	public static void main(String[] args) {
        launch(args);
//		String data = "3530054005400010218726".substring(0,21);
//		System.out.println(getCS(data));
//		data = "3530054005400010396271".substring(0,21);
//		System.out.println(getCS(data));

    }

}
