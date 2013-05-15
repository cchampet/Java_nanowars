package renderer;


import java.awt.Container;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class UIRenderer {

	private final int UI_LAYER = 200;
	private JLabel background;
	private JLabel radialMenuMovment;
	private Container container;
	private int width;
	private int height;
	private static boolean choosingUnitFlag = false;
	private static double unitPercentChosen = 0.5;
	
	public UIRenderer(Container c, int width, int height){
		super();
		this.background = new JLabel();
		this.radialMenuMovment = new JLabel();
		this.container = c;
		this.height=height;
		this.width=width;
	}
	
	public void init() throws IOException{
		//Load the background image
		ImageIcon bgImage = new ImageIcon("./tex/youWin.png");
		if(bgImage.getImageLoadStatus() != MediaTracker.COMPLETE){
			throw new IOException();
		}		
		this.background.setBounds(0, 0, this.width, this.height);
		this.background.setIcon(bgImage);
		
		//Load the menu image
		ImageIcon rmImage = new ImageIcon("./tex/radialmenu_movment.png");
		if(rmImage.getImageLoadStatus() != MediaTracker.COMPLETE){
			throw new IOException();
		}
		this.radialMenuMovment.setIcon(rmImage);
		this.radialMenuMovment.setSize(rmImage.getIconWidth(), rmImage.getIconHeight());
		
		//Manage events
		this.radialMenuMovment.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseClicked(MouseEvent arg0) {
				UIRenderer.choosingUnitFlag = false;
				JLabel radialMenu = (JLabel) arg0.getComponent();
				Point rmPosition = new Point(radialMenu.getWidth()/2, radialMenu.getHeight()/2);
				Point mousePosition = arg0.getPoint();
				
				if(rmPosition.distance(mousePosition) < 10){ //click on the center 50%
					UIRenderer.unitPercentChosen = 0.5;
				}else{
					if(mousePosition.x > mousePosition.y && radialMenu.getWidth()-mousePosition.x > mousePosition.y){ //click on top quarter 50%
						UIRenderer.unitPercentChosen = 0.5;
					}else if(mousePosition.x > mousePosition.y && radialMenu.getWidth()-mousePosition.x < mousePosition.y){ //click on right quarter 75%
						UIRenderer.unitPercentChosen = 0.75;						
					}else if(mousePosition.x < mousePosition.y && radialMenu.getWidth()-mousePosition.x > mousePosition.y){ //click on left quarter 25%
						UIRenderer.unitPercentChosen = 0.25;
					}else if(mousePosition.x < mousePosition.y && radialMenu.getWidth()-mousePosition.x < mousePosition.y){ //click on bottom quarter 99%
						UIRenderer.unitPercentChosen = 1.;						
					}
				}
			}
		});
	}
	
	/**
	 * Display a "WINNER" message when the player win and before exit program
	 * @throws IOException
	 */
	public void displayWinner(){
		this.container.add(this.background, new Integer(UI_LAYER));
	}
	
	/**
	 * Display or hide a radial menu to choose how many units send 
	 * @param mousePosition Position of the mouse, where the radial menu will appear
	 * @throws IOException
	 */
	public void refreshRadialMenuMovment(Point mousePosition){
		//if the player choose how many units to send
		if(!UIRenderer.choosingUnitFlag){
			if(this.radialMenuMovment.getParent() == null){
				UIRenderer.choosingUnitFlag = true;
				SwingUtilities.convertPointFromScreen(mousePosition, this.container);
				
				this.radialMenuMovment.setLocation(mousePosition.x - this.radialMenuMovment.getWidth()/2,
												 mousePosition.y - this.radialMenuMovment.getHeight()/2);
				this.container.add(this.radialMenuMovment, new Integer(UI_LAYER));
			}else{
				//remove the radial menu
				this.container.remove(this.radialMenuMovment);
			}
		}
	}
	
	public void updateChoosingUnitFlag(boolean state){
		UIRenderer.choosingUnitFlag = state;
	}
	
	public boolean getChoosingUnitFlag(){
		return UIRenderer.choosingUnitFlag;
	}
	
	public double getUnitPercentChosen(){
		return UIRenderer.unitPercentChosen;
	}
}



