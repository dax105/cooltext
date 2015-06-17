package com.jboudny.cooltext;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

public class Cooltext extends Canvas {
	
	public static final int WIDTH = 320;
	public static final int HEIGHT = 240;
	
	public static final int SCALE = 2;
	
	public static final int TICKS_PER_SECOND = 64;
	public static final int TICK_TIME_NS = 1000000000 / TICKS_PER_SECOND;
	
	private static final long serialVersionUID = 1L;
	
	private JFrame frame;
	
	private Bitmap screen;
	
	private Bitmap text;
	
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	
	public void start() {
		setMinimumSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		setMaximumSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		
		this.frame = new JFrame("Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	
		this.text = genText();
		
		this.screen = new Bitmap(image);
		this.loop();
	}
	
	public Bitmap genText() {
		BufferedImage img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) img.getGraphics();
		Font f = new Font("Shoguns Clan", Font.PLAIN, 52);

		g.setColor(java.awt.Color.WHITE);
		g.setFont(f);
		
		FontMetrics fm = g.getFontMetrics();
		
		int y = (HEIGHT + fm.getHeight()/2)/2;
		int x = (WIDTH - fm.stringWidth("Hotline miami"))/2;
		
		g.drawString("Hotline miami", x, y);
		return new Bitmap(img);
	}

	public void loop() {
		long time = System.nanoTime();
		long lastTime = time;
		
		long tick = 0;
		
		while (true) {
			time = System.nanoTime();
			
			while (time - lastTime >= TICK_TIME_NS) {
				tick(tick++);
				lastTime += TICK_TIME_NS;
			}
			
			screen.clear(0); // Set all pixels to black
			renderTick(tick, (time - lastTime)/(float)TICK_TIME_NS);			
			flipBuffer(); // Repaint the window
			
			this.frame.setTitle(1000000000/(System.nanoTime() - time) + "fps");
		}
	}
	
	public void tick(long tick) {
		
	}
	
	public void renderTick(long tick, float ptt) {
		//screen.fillRect(0, 0, WIDTH-1, HEIGHT-1, Color.mix(0xB000B0, 0x4000F0, (Math.sin((tick+ptt)*0.1D)+1)*0.5f));

		for (int z = 15; z > 0; z--) {
			
			double w = (Math.sin((tick+ptt-z)*0.06)+1)/2;
			
			int c = Color.mix(0xFF00FF, 0x00FFFF, w);
			
			screen.drawBitmapScaledRotated(0, 0, this.text, Math.sin((tick+ptt-z*4)*0.01D)*10, 1-0.008*z, c);
		}
		
		screen.drawBitmapRotated(0, 0, this.text, Math.sin((tick+ptt)*0.01D)*10);
	}
	
	public void flipBuffer() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			requestFocus();
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.fillRect(0, 0, getWidth(), getHeight());

		g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args) {
		new Cooltext().start();
	}
	
}
