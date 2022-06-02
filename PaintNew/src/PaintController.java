import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFormattedTextField;
import java.awt.Color;

public class PaintController {
	
	private Paint model;
	private PaintView view;
	
	PaintController(Paint model, PaintView view){
		
		this.model = model;
		this.view = view;
		
		this.view.SetInitialColorAndSize(model.getCol(), model.getSize());
		
		this.view.addMouse(new MouseMotionDrawer(), new MouseDrawer());
		this.view.addColorActionListener(new ChangeColorActionListener());
		this.view.addRadioButtonActionListener(new RadioButtonActionListener());
		this.view.addClearCanvasActionListener(new ClearCanvasActionListener());
		this.view.addSizeTextActionListener(new SizeActionListener());
		this.view.addSizeMouseWheelEvent(new MouseWheelSizeControl());
	}
	
	class MouseDrawer extends MouseAdapter{
		
		@Override
		public void mouseClicked(MouseEvent e) {
			PaintView.MyCanvas can = (PaintView.MyCanvas) e.getSource();
			
			can.setSize(model.getSize());
			can.setX(e.getX());
			can.setY(e.getY());
			
			can.paint(can.getGraphics());
		}
	}
	
	
	class MouseMotionDrawer extends MouseMotionAdapter{

		@Override
		public void mouseDragged(java.awt.event.MouseEvent e) {
			PaintView.MyCanvas can = (PaintView.MyCanvas) e.getSource();
			
			can.setSize(model.getSize());
			can.setX(e.getX());
			can.setY(e.getY());
			
			can.paint(can.getGraphics());
		}
		
	}
	
	class MouseWheelSizeControl implements MouseWheelListener{

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			int size = model.getSize();
			if(e.getWheelRotation() < 0)
				size += 1;
			else
				size -= 1;
			
			if(size > 1) {
				model.setSize(size);
				view.incrementSize(size);
			}
		}
		
	}
	
	class ChangeColorActionListener implements ActionListener{
		

		@Override
		public void actionPerformed(ActionEvent e) {
			Color newColor = view.getNewColor();
			model.setCol(newColor);
			PaintView.MyCanvas can = (PaintView.MyCanvas) view.getMyCanvas();
			can.setColor(newColor);
		}
	}
	
	class RadioButtonActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand() == "Eraser") {
				view.useEraser(true);
			}
			else {
				view.useEraser(false);
			}
			
		}
		
	}
	
	class ClearCanvasActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			view.clearCanvas();
		}
		
	}
	
	class SizeActionListener implements PropertyChangeListener{

		@Override
		public void propertyChange(PropertyChangeEvent e) {
			int size = model.getSize();
			try {
				size = (int) ((JFormattedTextField) e.getSource()).getValue();
			}
			catch(Exception m){}
			
			if(size < 2)
				size = 2;
			
			model.setSize(size);
		}
	}
	
}
