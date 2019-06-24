import javax.swing.JFrame;

public class BubbleDrawGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("Александр Ерёмин - BubbleDraw GUI App"); //$NON-NLS-1$
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new BubblePanel());
		frame.setSize(new java.awt.Dimension(900,600));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
