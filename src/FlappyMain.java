
import javax.swing.JFrame;

public class FlappyMain {

	public static void main(String[] args) {
		
		JFrame frame = new JFrame("FlappyMain");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.getContentPane().add(new FlappyPanel());
		
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		FlappyPanel backgroundmusic = new FlappyPanel();
		//backgroundmusic.music();
	}

}
