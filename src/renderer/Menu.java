package renderer;

import java.awt.MediaTracker;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import playable.TypeOfPlayer;
import renderer.sprite.LvlSprite;

@SuppressWarnings("serial")
public class Menu extends JLabel {
	
	private int width;
	private int height;
		
	private LinkedList<LvlSprite> lvlSprites;

	public Menu(int width, int height){
		super();
		
		this.height = height;
		this.width = width;
				
		this.lvlSprites = new LinkedList<LvlSprite>();
	}
	
	public void init() throws IOException{
		//Load the menu background image
		ImageIcon bgMenuImage = new ImageIcon("./tex/MENU.png");
		if(bgMenuImage.getImageLoadStatus() != MediaTracker.COMPLETE){
		}
		this.setBounds(0, 0, this.width, this.height);
		this.setIcon(bgMenuImage);	
	}
	
	/**
	 * Add a level to the menu.
	 * @param pathOfTheLevel : path of the corresponding image of the level.
	 * @param numOfTheLevel : the number of the level, display in the menu.
	 * @param x : x position 
	 * @param y : y position
	 * @throws IOException 
	 */

	public int addLvlSprite(String pathOfTheLevel, String pathOfTheBackground, int numOfTheLevel, int x, int y) throws IOException{
		LvlSprite newSprite = new LvlSprite(pathOfTheLevel, pathOfTheBackground, numOfTheLevel);
		newSprite.setSize(50);
		newSprite.setImage(TypeOfPlayer.NEUTRAL.getImageOfBase());
		newSprite.setBounds(x, y, 50, 50);
		lvlSprites.add(newSprite);
		return newSprite.getId();
	}
	
	public void resetLvlSelected(){
		this.getLvlSelected().resetIsSelected();
	}
	
	public void addLvlsToTheMenu() throws IOException{
		//currently 3 levels
		this.addLvlSprite(Level.LVL_1.getPath(), Level.LVL_1.getBackgroundPath(), Level.LVL_1.getId(), 170, this.height - 200);
		this.addLvlSprite(Level.LVL_2.getPath(), Level.LVL_2.getBackgroundPath(), Level.LVL_2.getId(), 270, this.height - 200);
		this.addLvlSprite(Level.LVL_3.getPath(), Level.LVL_3.getBackgroundPath(), Level.LVL_3.getId(), 370, this.height - 200);
		this.addLvlSprite(Level.LVL_4.getPath(), Level.LVL_4.getBackgroundPath(), Level.LVL_4.getId(), 470, this.height - 200);
		this.addLvlSprite(Level.LVL_5.getPath(), Level.LVL_5.getBackgroundPath(), Level.LVL_5.getId(), 570, this.height - 200);
		this.addLvlSprite(Level.LVL_6.getPath(), Level.LVL_6.getBackgroundPath(), Level.LVL_6.getId(), 170, this.height - 100);
		this.addLvlSprite(Level.LVL_7.getPath(), Level.LVL_7.getBackgroundPath(), Level.LVL_7.getId(), 270, this.height - 100);
		this.addLvlSprite(Level.LVL_8.getPath(), Level.LVL_8.getBackgroundPath(), Level.LVL_8.getId(), 370, this.height - 100);
		this.addLvlSprite(Level.LVL_9.getPath(), Level.LVL_9.getBackgroundPath(), Level.LVL_9.getId(), 470, this.height - 100);
		this.addLvlSprite(Level.LVL_10.getPath(), Level.LVL_10.getBackgroundPath(), Level.LVL_10.getId(), 570, this.height - 100);
	}
	
	//replace menu button
	public void reset(){
		int cpt = 0;
		int nbRow = 0;
		for(LvlSprite lSp:this.lvlSprites){
			lSp.changeTheNameOfTheLvlDisplay(String.valueOf(lSp.getNumLvl()));
			lSp.setLocation(170 + 100*(cpt-nbRow*5), this.height - 200 + 100*nbRow);
			if(cpt >= 4){
				nbRow = 1;
			}
			cpt++;			
		}
	}
	
	// GETTERS

	public LinkedList<LvlSprite> getLvlSprites(){
		return lvlSprites;
	}
	
	public LvlSprite getLvlSprite(int id){
		for(LvlSprite lvl:this.lvlSprites){
			if(lvl.getId() == id)
				return lvl;
		}
		return null;
	}
	
	/**
	 * This function returns the level selected in the menu, or null if the user doesn't make a choice yet.
	 * @return lvl;
	 */
	public LvlSprite getLvlSelected() {
		for(LvlSprite lvl:this.lvlSprites){
			if(lvl.isSelected())
				return lvl;
		}
		return null;
	}
	
	public ImageIcon getBackgroundImage(){
		for(LvlSprite lvl:this.lvlSprites){
			if(lvl.isSelected()){
				return lvl.getBackgroundImage();
			}
		}
		return this.lvlSprites.getLast().getBackgroundImage();
	}
}
