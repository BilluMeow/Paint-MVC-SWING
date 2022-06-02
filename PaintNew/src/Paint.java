import java.awt.Color;

public class Paint {
	
	Paint(){
		size = 10;
		col = Color.BLACK;
	}
	
	public int getSize() {
		return size;
	}
	public void setSize(int font) {
		this.size = font;
	}
	public Color getCol() {
		return col;
	}
	public void setCol(Color col) {
		this.col = col;
	}
	
	private int size;
	private Color col;
}
