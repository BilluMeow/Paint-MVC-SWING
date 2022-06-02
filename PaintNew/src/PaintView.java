import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;
import javax.swing.text.NumberFormatter;

public class PaintView {
	
	private JFrame frame;
	private JPanel Menu;
	private JButton ClearCanvas;
	private JLabel SizeLabel;
	private JRadioButton BrushRadio;
	private JRadioButton EraserRadio;
	private JLabel Meow;
	private JLabel Meow1;
	private JButton ColorChoose;
	private MyCanvas canvas;
	private NumberFormatter Formatter;
	private JFormattedTextField Size;
	private ButtonGroup RadioButtonGroup;
	
	
	
	PaintView(){
		
		// Found This line after 1 hour of googling
		// Made this project work, kinda magic
		System.setProperty("sun.awt.noerasebackground", "true");
		
		Border greenline = BorderFactory.createLineBorder(Color.green);

		frame = new JFrame();
		frame.setBounds(0, 0, 1500, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Menu = new JPanel();
		Menu.setBounds(0, 0, 0, 0);
		Menu.setBorder(greenline);
		Menu.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		frame.getContentPane().add(Menu, BorderLayout.NORTH);
		
		ColorChoose = new JButton("Choose A Color");
		Menu.add(ColorChoose);

		SizeLabel = new JLabel("Size");

		BrushRadio = new JRadioButton("Brush", true);
		BrushRadio.setActionCommand("Brush");
		Menu.add(BrushRadio);

		EraserRadio = new JRadioButton("Eraser");
		EraserRadio.setActionCommand("Eraser");
		Menu.add(EraserRadio);
		
		RadioButtonGroup = new ButtonGroup();
		RadioButtonGroup.add(BrushRadio);
		RadioButtonGroup.add(EraserRadio);

		Meow = new JLabel("              ");
		Menu.add(Meow);
		Menu.add(SizeLabel);

		Formatter = new NumberFormatter(NumberFormat.getInstance());
		Formatter.setValueClass(Integer.class);
		Formatter.setMinimum(0);
		Formatter.setMaximum(Integer.MAX_VALUE);
		Formatter.setAllowsInvalid(false);
		Size = new JFormattedTextField(Formatter);
		Size.setColumns(10);

		Menu.add(Size);

		Meow1 = new JLabel("                  ");
		Menu.add(Meow1);

		ClearCanvas = new JButton("Clear Canvas");
		Menu.add(ClearCanvas);
		
		
		canvas = new MyCanvas();
		canvas.setBackground(Color.WHITE);
		frame.getContentPane().add(canvas, BorderLayout.CENTER);
		
		frame.setResizable(false);
		frame.setVisible(true);
		
	}
	
	/* 
	 *     All Action Listeners are added here
	 */
	
	public void addMouse(MouseMotionAdapter D, MouseAdapter M) {
		canvas.addMouseMotionListener(D);
		canvas.addMouseListener(M);
	}
	
	
	public void addRadioButtonActionListener(ActionListener A) {
		EraserRadio.addActionListener(A);
		BrushRadio.addActionListener(A);
		
	}
	
	public void addColorActionListener(ActionListener C) {
		this.ColorChoose.addActionListener(C);
	}

	public void addClearCanvasActionListener(ActionListener A) {
		ClearCanvas.addActionListener(A);
	}
	
	
	public void addSizeTextActionListener(PropertyChangeListener A) {
		Size.addPropertyChangeListener(A);;
	}
	
	public void addSizeMouseWheelEvent(PaintController.MouseWheelSizeControl mouseWheelSizeControl) {
		canvas.addMouseWheelListener(mouseWheelSizeControl);
	}

	
	/* 
	 *     All Supporting Functions are defined here, no logic
	 */
	

	public Color getNewColor() {
		new JColorChooser();
		return JColorChooser.showDialog(null, "Choose A Color", Color.WHITE);
	}
	
	public MyCanvas getMyCanvas() {
		return canvas;
	}
	
	public void ChangeColor(Color C) {
		this.canvas.setColor(C);
	}



	public void useEraser(boolean b) {
		this.canvas.Eraser(b);
	}


	public void clearCanvas() {
		canvas.ClearCanvas();
	}
	
	public void incrementSize(int size) {
		Size.setValue(size);
	}

	public void SetInitialColorAndSize(Color col, int size) {
		canvas.setColor(col);
		Size.setValue(size);
	}
	
	
	@SuppressWarnings("serial")
	class MyCanvas extends Canvas {
		
		private Boolean eraser, init;
		private int x, y, size;
		private Color color;
		
		MyCanvas(){
			eraser = false;
			init = true;
		}
		
		public void setColor(Color color){
			this.color = color;
		}

		public void setX(int x) {
			this.x = x;
		}

		public void setSize(int size) {
			this.size = size;
		}

		public void setY(int y) {
			this.y = y;
		}
		
		public void Eraser(boolean b) {
			eraser = b;
		}
		
		public void ClearCanvas() {
			this.getGraphics().clearRect(0, 0, this.getBounds().width, this.getBounds().height);
		}

		
		@Override
		public void paint(Graphics g) {
			
			if(init) {
				init = false;
				g.setFont(new Font("TimesRoman", Font.PLAIN, 40)); 
				g.drawString("PAINT BY 19K-1069 Syed Abeer Hussain", 100, 100);
				g.drawString("USE SCROLL TO CHANGE THE SIZE OF BRUSH(EXTRA FEATURE)", 100, 300);
				g.drawString("CLEAR CANVAS BUTTON IS ALSO AN EXTRA FEATURE", 100, 500);
				g.drawString("FULL MVC USED", 100, 700);
				return;
			}
			
			if(eraser) {
				g.setColor(Color.WHITE);
				g.fillRect(x-(size/2), y-(size/2), size, size);
			}
			
			else {
				g.setColor(color);
				g.fillOval(x, y, size, size);
			}
			
		}
		
		@Override
		public void update(Graphics g){
			paint(g);
		}
		
	}
	
}
