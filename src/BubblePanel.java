import java.awt.Color;
//import java.awt.Component;
//import java.awt.Graphics2D;
//import java.awt.Polygon;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JTextField;
//import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JSlider;
import java.awt.FlowLayout;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class BubblePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Random rand = new Random();
	ArrayList<Bubble> bubbleList;
	int size = 25;
	Timer timer;
	int shapeForm;
	int delay = 33;
	JSlider slider;
	private JTextField txtX;
	private JTextField txtY;
	private JTextField txtXspeed;
	private JTextField txtYspeed;

	public BubblePanel() {
		timer = new Timer(delay, new BubbleListener());
		bubbleList = new ArrayList<Bubble>();
		setBackground(Color.BLACK);

		JPanel panel = new JPanel();
		add(panel);

		JButton btnPause = new JButton("Pause"); //$NON-NLS-1$
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton btn = (JButton)e.getSource();
				if (btn.getText().equals("Pause")) { //$NON-NLS-1$
					timer.stop();
					btn.setText("Start"); //$NON-NLS-1$
				} else {
					timer.start();
					btn.setText("Pause"); //$NON-NLS-1$
				}
			}
		});
		
		JLabel lblSpeed = new JLabel("Speed "); //$NON-NLS-1$
		panel.add(lblSpeed);
		
		slider = new JSlider();
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				int speed = slider.getValue() + 1;
				int delay = 1000 / speed;
				timer.setDelay(delay);
			}
		});	
		
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setMajorTickSpacing(30);
		slider.setMinorTickSpacing(5);
		slider.setValue(30);
		slider.setMaximum(120);
		panel.add(slider);
		panel.add(btnPause);

		JButton btnClear = new JButton("Clear"); //$NON-NLS-1$
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				txtX.setText("0"); //$NON-NLS-1$
				txtY.setText("0"); //$NON-NLS-1$
				txtXspeed.setText("0"); //$NON-NLS-1$
				txtYspeed.setText("0"); //$NON-NLS-1$
				bubbleList = new ArrayList<Bubble>();
				repaint();
			}
		});

		panel.add(btnClear);		
		
		JLabel lblNewLabel = new JLabel("Shape "); //$NON-NLS-1$
		panel.add(lblNewLabel);
		
		JSlider slider_1 = new JSlider();
		slider_1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				shapeForm = slider_1.getValue();
			}
		});
		slider_1.setValue(0);
		slider_1.setPaintTicks(true);
		slider_1.setPaintLabels(true);
		slider_1.setMinorTickSpacing(1);
		slider_1.setMaximum(5);
		slider_1.setMajorTickSpacing(5);
		panel.add(slider_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setVisible(false);
		add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		txtX = new JTextField();
		txtX.setText("X= "); //$NON-NLS-1$
		panel_1.add(txtX);
		txtX.setColumns(4);
		
		txtY = new JTextField();
		txtY.setText("Y= "); //$NON-NLS-1$
		panel_1.add(txtY);
		txtY.setColumns(4);
		
		JPanel panel_2 = new JPanel();
		panel_2.setVisible(false);
		add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		txtXspeed = new JTextField();
		txtXspeed.setText("xSpeed= "); //$NON-NLS-1$
		panel_2.add(txtXspeed);
		txtXspeed.setColumns(6);
		
		txtYspeed = new JTextField();
		txtYspeed.setText("ySpeed= "); //$NON-NLS-1$
		panel_2.add(txtYspeed);
		txtYspeed.setColumns(6);
		//testBubbles();
		addMouseListener(new BubbleListener());
		addMouseMotionListener(new BubbleListener());
		addMouseWheelListener(new BubbleListener());
		timer.start();
	}

	public void paintComponent(Graphics canvas) {
		super.paintComponent(canvas);
		for(Bubble b: bubbleList) {
			b.draw(canvas);
		}		
	}

	public void testBubbles() {
		for(int n = 0; n < 100; n++) {
			int x = rand.nextInt(900);
			int y = rand.nextInt(600);
			int size = rand.nextInt(50);
			bubbleList.add(new Bubble(x, y, size));
		}
		repaint();
	}

	private class BubbleListener extends MouseAdapter implements ActionListener {
		public void mousePressed(MouseEvent e) {
			bubbleList.add(new Bubble(e.getX(), e.getY(), size));
			repaint();
		}
		public void mouseDragged(MouseEvent e) {
			bubbleList.add(new Bubble(e.getX(), e.getY(), size));
			repaint();
		}
		public void mouseWheelMoved(MouseWheelEvent e) {
			if(System.getProperty("os.name").startsWith("Mac")) //$NON-NLS-1$ //$NON-NLS-2$
				size += e.getUnitsToScroll();
			else
				size -= e.getUnitsToScroll();
		}
		public void actionPerformed(ActionEvent e) {
			for (Bubble b: bubbleList) // for each в массиве bubblelist (b) выполнить обновление
				b.update();
			repaint();
			//	testBubbles();
		}
	}

	private class Bubble {
		private int x;
		private int y;
		private int size;
		private Color color;
		private int xspeed, yspeed;
		private final int MAX_SPEED = 5;

		public Bubble(int newX, int newY, int newSize) {
			x = newX;
			y = newY;
			size = newSize;
			color = new Color(rand.nextInt(256), // R
					rand.nextInt(256), // G
					rand.nextInt(256), // B
					rand.nextInt(256)); //A
			xspeed = rand.nextInt(MAX_SPEED *2 + 1) - MAX_SPEED;
			yspeed = rand.nextInt(MAX_SPEED *2 + 1) - MAX_SPEED;			
		}
		public void draw(Graphics canvas) {
			canvas.setColor(color);
			
			switch (shapeForm) {
			case 0:								
				canvas.fillOval(x - size/2, y - size/2, size, size/2); // Овал (круг)
				break;
			case 1:
				canvas.fill3DRect(x - size/2, y - size/2, size, size/2, true);  // Объемный квадрат
				break;
			case 2:
				canvas.fillArc(x - size/2, y - size/2, size, size, 0, x); // Сектор
				break;
			case 3:
				canvas.fillRect(x - size/2, y - size/2, size, size); // Обычный квадрат
				break;
			case 4:
				canvas.fillRoundRect(x - size/2, y - size/2, size, size, size/2, size/2); // Квадрат со скругленными углами
				break;
			default:
				canvas.fillOval(x - size/2, y - size/2, size, size); // Овал (круг)
				break;
			}		
			
			/*int[] arrayX = {20, 100, 100, 95, 95, 20, 40, 20}; // Рисует многоугольник в виде флага и закрашивает его
			int[] arrayY = {180, 180, 400, 400, 220, 220, 200, 180};
			Polygon poly = new Polygon(arrayX, arrayY, 8);
			//canvas.fillPolygon(poly);*/
						
		}
		public void update() {			
			x += xspeed;
			y += yspeed;
			
			if ((xspeed == 0) || (yspeed == 0)) {				
				x += xspeed + 5;
				y += yspeed + 5;
				xspeed++;
				yspeed++;
				txtX.setText("X= "+x); //$NON-NLS-1$
				txtXspeed.setText("xSpeed= "+xspeed); //$NON-NLS-1$
				txtY.setText("Y= "+y); //$NON-NLS-1$
				txtYspeed.setText("ySpeed= "+yspeed); //$NON-NLS-1$								
			}
			
			
			if (x - size/2 <= 0 || x + size/2 >= getWidth())
				xspeed = -xspeed;
			if (y - size/2 <= 0 || y + size/2 >= getHeight())
				yspeed = -yspeed;
		}
	}
}
