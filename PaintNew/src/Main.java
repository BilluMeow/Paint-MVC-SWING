
public class Main {

	public static void main(String[] args) {
		
		Paint model = new Paint();
		PaintView view = new PaintView();
		
		@SuppressWarnings("unused")
		PaintController controller = new PaintController(model, view);

	}

}
