package core;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Logic {
	
	//Game Levels
	Level currentLevel, test1;
	//Level List
	Level[] levelList;
	
	//Registered Players and Currently Processed Character
	Player currentCharacter, player1, player2, player3;
	//Character List
	Player[] characterList;
	//Character List Position
	int characterListPosition = 0;
	
	//Registered Enemies
	Enemy enemy1, enemy2, enemy3;
	//Currently Processed Enemies
	Enemy[] enemyList;
	
	//Debug Flags
	Boolean debugHitbox = false, debugPhysRend = false, debugJBox2D = false;
	
	//Movement Flags
	Boolean left = false, right = false;
	
	//Camera Offsets
	float camX, camY;
	
	//Camera Focus Offsets
	float focusX = 0, focusY = 0;
	
	//Action Timer
	float timer = 0;
	
	//Projectile Co-Ordinates
	float projectileX = 0, projectileY = 0, updateProjectile = 0;
	//Projectile Flags
	Boolean shotFired = false;

	public Logic(GameContainer gc) throws SlickException{
		
		//Levels\\
		//A level in the game that generates its own collision detection, camera system and Jump Reset system
		
			//Test Level1 || Mainly here to test each element of the system
			test1 = new Level(gc, "res/TestLevel6.tmx");
				
			//Level List || A List of Every Level in the Game
			levelList = new Level[]{
				test1
			};
		
			//Set Current Level || The level the game will load into
			currentLevel = test1;
				
			//Sets the Camera Focus Offset
			focusX = currentLevel.cameraFOffsetX;
			focusY = currentLevel.cameraFOffsetY;
		
		//Player Characters\\
		//A playable character within the game
		
			//Test Jazz || Simply used to test character system elements
			
				//|Players Constructor Structure|
					//-Name
					//-Sprite 
					//-Number of Sprites Needed for Movement, Width of Sample Sprite Tile, Height of Sample Sprite Tile
					//-Jump Animation: Width of Sample Sprite Tile, Height of Sample Sprite Tile
					//-Idle Animation: Width of Sample Sprite Tile, Height of Sample Sprite Tile
					//-Camera Focus Offset for X and Y
					//-Sprite Offset for X and Y
					//-Projectile Switch
					//-Friction, Restitution, Density
					//-Speed, Jump Power, Maximum Number of Jumps
					//-Initial Speed Multiplier
			
			player1 = new Player("Test Jazz", 
					"res/Jazz SS.png", 
					4, 32, 36, 
					29, 36,
					30, 36,
					focusX, focusY,
					0, 3,
					true,
					0.5f, -0.3f, 3f,
					5, 20, 1, 
					0.03f);
			
				//Speed Modifier Control || Controls how a character increases their Speed Modifier
					// p - Player
						//p.topSpeed - Player's Top Speed
						//p.speedMultiplier - Player's Current Speed Multiplier
						//p.minumumSpeed - The Player's Base Speed
						//p.current.setSpeed - Sets the speed Player's currently displayed animation is played at
			
				player1.addSMControl(new Action(){
				
					@Override
					public void actionEffect(Player p, Enemy e, int delta) {				
					}
				
					@Override
					public void lingeringEffect(Player p, Enemy e, int delta) {
						if((p.current == p.movingLeft || p.current == p.movingRight
								|| p.current == p.jumpLeft || p.current == p.jumpRight)
								&& p.speedMultiplier <= p.topSpeed){
							p.speedMultiplier += 0.003;
						}
						else{
							if(p.speedMultiplier > p.minimumSpeed){
								p.speedMultiplier -= 0.01;
							}
							if(p.speedMultiplier <= p.minimumSpeed){
								p.speedMultiplier = 0;
							}
						}
						
						p.current.setSpeed(p.speedMultiplier);
					}					
				});
			
				//Add A Action || Adds an action to the A Button
					//Action Effect - Activated immediately and only when the button is pressed				
					//Lingering Effect - Activated with each new update cycle

						// p - Player
							//p.actionA - Controls whether the Action Effect is activated
							//p.linger - Controls whether the Lingering Effect is activated
							//p.hitA - Controls the Hit Box for the A Action
							//p.setTimer - Sets the Effects Action Timer, which is used to time how long an effect lasts
							//p.current - Currently Displayed Sprite Animation
							//p.attackBoxOffset - Offset for the effect's hit box
							//p.body.applyForce - Applies a force upon the player body
						
						// e - Enemy
							//e.decreaseHP - Decreases the enemy's HP
							//e.body.applyForce - Applies a force upon the enemy body
							//e.body.setActive - Switches off whether the physics model will continue to apply to the character body
						
				player1.addAAction(new Action(){
					@Override
					public void actionEffect(Player p, Enemy e, int delta) {
						p.actionA = true;
						p.lingerA = true;
						p.hitA = true;
						p.setTimer(0);
					}
				
					@Override
					public void lingeringEffect(Player p, Enemy e, int delta) {
						
						if(p.current == p.movingLeft || p.current == p.idleLeft || p.current == p.jumpLeft){
							p.attackBoxOffset = -40;
							p.attackBox.setX(((-10 + p.playerOffsetX) + focusX) + p.attackBoxOffset);
						}
						else{
						    p.attackBoxOffset = 30;
						    p.attackBox.setX(((-10 + p.playerOffsetX) + focusX) + p.attackBoxOffset);
						}
						
						if(p.hitboxACheck(e)){
				        	e.decreaseHP(10);
					       	if(p.current == p.movingLeft || p.current == p.idleLeft || p.current == p.jumpLeft){
					       		e.body.applyForce(-20f, -60f);
					       		p.body.applyForce(50f, 0);
					       	}
					       	else{
					       		e.body.applyForce(20f, -60);
					       		p.body.applyForce(-50f, 0);
					       	}
				        }
						
				        if(p.getHP() == 0){
				       	 	e.body.setActive(false);
				        }
						
						if(p.getTimer() > 100){
							p.actionA = false;
							p.lingerA = false;
							p.hitA = false;
							
						}
					}
				});
				
				//Add B Action || Adds an action to the B Button
				//Action Effect - Activated immediately and only when the button is pressed				
				//Lingering Effect - Activated with each new update cycle

					// p - Player
						//p.actionB - Controls whether the Action Effect is activated
						//p.linger - Controls whether the Lingering Effect is activated
						//p.hitB - Controls the Hit Box for the B Action
						//p.setTimer - Sets the Effects Action Timer, which is used to time how long an effect lasts
						//p.current - Currently Displayed Sprite Animation
						//p.attackBoxOffset - Offset for the effect's hit box
						//p.body.applyForce - Applies a force upon the player body
					
					// e - Enemy
						//e.decreaseHP - Decreases the enemy's HP
						//e.body.applyForce - Applies a force upon the enemy body
						//e.body.setActive - Switches off whether the physics model will continue to apply to the character body
				
				player1.addBAction(new Action(){
					@Override
					public void actionEffect(Player p, Enemy e, int delta) {
						if(!shotFired){
							p.actionB = true;
							p.lingerB = true;
							p.setTimer(0);
							if(!p.projectileB){
								p.projectileOffsetY = - 10;
								if(p.current == p.movingLeft || p.current == p.idleLeft || p.current == p.jumpLeft){
									p.projectileOffsetX = -40;
									p.attackBox.setX(((-10 + p.playerOffsetX) + focusX) + p.attackBoxOffset);
								}
								else{
								    p.projectileOffsetX = 30;
								    p.attackBox.setX(((-10 + p.playerOffsetX) + focusX) + p.attackBoxOffset);
								}
								p.projectileB = true;
							}
						}
					}
			
					@Override
					public void lingeringEffect(Player p, Enemy e, int delta) {
			    		p.actionB = false;
						
						if(p.projectileCheck(e)){
				        	e.decreaseHP(5);
					       	if(p.current == p.movingLeft || p.current == p.idleLeft || p.current == p.jumpLeft){
					       		e.body.applyForce(-50f, -10f);
					       	}
					       	else{
					       		e.body.applyForce(50f, -10);
					       	}
				        }
				        if(p.getHP() == 0){
				       	 	e.body.setActive(false);
				        }
					        
				    	if(p.getTimer() > 300){
				    		p.lingerB = false;
				    		p.projectileB = false;
				    		shotFired = false;
				    		timer = 0;
				    	}
					}
					
				});
							
			//Test Sonic || Simply used to test character system elements
			
				//|Players Constructor Structure|
					//-Name
					//-Sprite 
					//-Number of Sprites Needed for Movement, Width of Sample Sprite Tile, Height of Sample Sprite Tile
					//-Jump Animation: Width of Sample Sprite Tile, Height of Sample Sprite Tile
					//-Idle Animation: Width of Sample Sprite Tile, Height of Sample Sprite Tile
					//-Camera Focus Offset for X and Y
					//-Sprite Offset for X and Y
					//-Projectile Switch
					//-Friction, Restitution, Density
					//-Speed, Jump Power, Maximum Number of Jumps
					//-Initial Speed Multiplier
			
			player2 = new Player("Test Sonic", 
					"res/Test Sonic.png", 
					12, 36, 38, 
					30, 39,
					27, 39,
					camX, camY,
					0, 0,
					false,
					0.25f, -0.3f, 2f,
					7, 17, 1,
					0.05f);
			
				//Speed Modifier Control || Controls how a character increases their Speed Modifier
					// p - Player
						//p.topSpeed - Player's Top Speed
						//p.speedMultiplier - Player's Current Speed Multiplier
						//p.minumumSpeed - The Player's Base Speed
						//p.current.setSpeed - Sets the speed Player's currently displayed animation is played at
				player2.addSMControl(new Action(){
				
					@Override
					public void actionEffect(Player p, Enemy e, int delta) {				
					}
				
					@Override
					public void lingeringEffect(Player p, Enemy e, int delta) {
						if((p.current == p.movingLeft || p.current == p.movingRight
								|| p.current == p.jumpLeft || p.current == p.jumpRight)
								&& p.speedMultiplier <= p.topSpeed){
							p.speedMultiplier += 0.006;
						}
						else{
							if(p.speedMultiplier > p.minimumSpeed){
								p.speedMultiplier -= 0.09;
							}
							if(p.speedMultiplier <= p.minimumSpeed){
								p.speedMultiplier = 0;
							}
						}
						
						p.current.setSpeed(p.speedMultiplier);
					}					
				});
				
				//Add A Action || Adds an action to the A Button
				//Action Effect - Activated immediately and only when the button is pressed				
				//Lingering Effect - Activated with each new update cycle
	
					// p - Player
						//p.actionA - Controls whether the Action Effect is activated
						//p.linger - Controls whether the Lingering Effect is activated
						//p.hitA - Controls the Hit Box for the A Action
						//p.setTimer - Sets the Effects Action Timer, which is used to time how long an effect lasts
						//p.current - Currently Displayed Sprite Animation
						//p.attackBoxOffset - Offset for the effect's hit box
						//p.body.applyForce - Applies a force upon the player body
					
					// e - Enemy
						//e.decreaseHP - Decreases the enemy's HP
						//e.body.applyForce - Applies a force upon the enemy body
						//e.body.setActive - Switches off whether the physics model will continue to apply to the character body
			
				player2.addAAction(new Action(){
					@Override
					public void actionEffect(Player p, Enemy e, int delta) {
						p.actionA = true;
						p.lingerA = true;
						p.hitA = true;
						p.setTimer(0);
					}
			
					@Override
					public void lingeringEffect(Player p, Enemy e, int delta) {
						p.attackBox.setBounds((p.playerOffsetX - 35) + p.attackBoxOffset, (p.playerOffsetY - 21), 70, 20);
						
				       	if(p.current == p.movingLeft || p.current == p.idleLeft || p.current == p.jumpLeft){
				       		p.attackBoxOffset = 15;
				       	}
				       	else{
				       		p.attackBoxOffset = 0;
				       	}
						
						if(p.attackBox.intersects(e.hitbox) && p.getHP() > 0){
				        	e.decreaseHP(5);
					       	if(p.current == p.movingLeft || p.current == p.idleLeft || p.current == p.jumpLeft){
					       		e.body.applyForce(-80f, -10f);
					       	}
					       	else{
					       		e.body.applyForce(80f, -10);
					       	}
				        }
						
				        if(p.getHP() == 0){
				       	 	e.body.setActive(false);
				        }
				        
						if(p.getTimer() > 100){
							p.actionA = false;
							p.lingerA = false;
							p.hitA = false;
						}
					}
				});
				
				//Add B Action || Adds an action to the B Button
				//Action Effect - Activated immediately and only when the button is pressed				
				//Lingering Effect - Activated with each new update cycle

					// p - Player
						//p.actionB - Controls whether the Action Effect is activated
						//p.linger - Controls whether the Lingering Effect is activated
						//p.hitB - Controls the Hit Box for the B Action
						//p.setTimer - Sets the Effects Action Timer, which is used to time how long an effect lasts
						//p.current - Currently Displayed Sprite Animation
						//p.attackBoxOffset - Offset for the effect's hit box
						//p.body.applyForce - Applies a force upon the player body
					
					// e - Enemy
						//e.decreaseHP - Decreases the enemy's HP
						//e.body.applyForce - Applies a force upon the enemy body
						//e.body.setActive - Switches off whether the physics model will continue to apply to the character body
				
				player2.addBAction(new Action(){
					@Override
					public void actionEffect(Player p, Enemy e, int delta) {
						p.actionB = true;
						p.lingerB = true;
						p.hitB = true;
						p.movementOverride = true;
						p.setTimer(0);
					}
			
					@Override
					public void lingeringEffect(Player p, Enemy e, int delta) {
						p.attackBoxB.setBounds((p.playerOffsetX - 35) + p.attackBoxOffset, (p.playerOffsetY - 21), 70, 20);
						
				       	if(p.current == p.movingLeft || p.current == p.idleLeft || p.current == p.jumpLeft){
				       		p.attackBoxOffset = 15;
				       	}
				       	else{
				       		p.attackBoxOffset = 0;
				       	}
						
					    if(p.getTimer() < 1200){
							if(p.attackBoxB.intersects(e.hitbox) && p.getHP() > 0){
					        	e.decreaseHP(20);
						       	if(p.current == p.movingLeft || p.current == p.idleLeft || p.current == p.jumpLeft){
						       		e.body.applyForce(-100f, -100f);
						       		p.body.applyForce(10, -5);
						       	}
						       	else{
						       		e.body.applyForce(100f, -100);
						       		p.body.applyForce(-10, -5);
						       	}
					        }
					    }
				    	if(p.getTimer() > 1200){
				    		if(p.getTimer() < 1500){
					    		p.hitB = false;
					    		p.movementOverride = false;
						       	if(p.current == p.movingLeft || p.current == p.idleLeft || p.current == p.jumpLeft){
						       		p.body.applyForce(-100f, 0);
						       	}
						       	else{
						       		p.body.applyForce(100f, 0);
						       	}
								if(p.attackBoxB.intersects(e.hitbox) && p.getHP() > 0){
						        	e.decreaseHP(15);
						        }
				    		}
				    	}
				    	
			    		if(p.getTimer() > 1500){
				    		p.actionB = false;
				    		p.lingerB = false;
			    		}
					}
					
				});
						
			//Test Wario|| Simply used to test character system elements
				
				//|Players Constructor Structure|
					//-Name
					//-Sprite 
					//-Number of Sprites Needed for Movement, Width of Sample Sprite Tile, Height of Sample Sprite Tile
					//-Jump Animation: Width of Sample Sprite Tile, Height of Sample Sprite Tile
					//-Idle Animation: Width of Sample Sprite Tile, Height of Sample Sprite Tile
					//-Camera Focus Offset for X and Y
					//-Sprite Offset for X and Y
					//-Projectile Switch
					//-Friction, Restitution, Density
					//-Speed, Jump Power, Maximum Number of Jumps
					//-Initial Speed Multiplier
					
			player3 = new Player("Test Wario", 
				"res/Test Wario.png", 
					4, 46, 58, 
					46, 55,
					40, 58,
					camX, camY,
					0, -19,
					false,
					0.5f, -0.3f, 5f,
					7, 30, 1,
					0.1f);
			
				//Speed Modifier Control || Controls how a character increases their Speed Modifier
					// p - Player
						//p.topSpeed - Player's Top Speed
						//p.speedMultiplier - Player's Current Speed Multiplier
						//p.minumumSpeed - The Player's Base Speed
						//p.current.setSpeed - Sets the speed Player's currently displayed animation is played at
				player3.addSMControl(new Action(){
				
					@Override
					public void actionEffect(Player p, Enemy e, int delta) {				
					}
				
					@Override
					public void lingeringEffect(Player p, Enemy e, int delta) {
						if((p.current == p.movingLeft || p.current == p.movingRight
								|| p.current == p.jumpLeft || p.current == p.jumpRight)
								&& p.speedMultiplier <= p.topSpeed){
							p.speedMultiplier += 0.0023;
						}
						else{
							if(p.speedMultiplier > p.minimumSpeed){
								p.speedMultiplier -= 0.04;
							}
							if(p.speedMultiplier <= p.minimumSpeed){
								p.speedMultiplier = 0;
							}
						}
						
						p.current.setSpeed(p.speedMultiplier);
					}					
				});
			
				//Add A Action || Adds an action to the A Button
				//Action Effect - Activated immediately and only when the button is pressed				
				//Lingering Effect - Activated with each new update cycle
	
					// p - Player
						//p.actionA - Controls whether the Action Effect is activated
						//p.linger - Controls whether the Lingering Effect is activated
						//p.hitA - Controls the Hit Box for the A Action
						//p.setTimer - Sets the Effects Action Timer, which is used to time how long an effect lasts
						//p.current - Currently Displayed Sprite Animation
						//p.attackBoxOffset - Offset for the effect's hit box
						//p.body.applyForce - Applies a force upon the player body
					
					// e - Enemy
						//e.decreaseHP - Decreases the enemy's HP
						//e.body.applyForce - Applies a force upon the enemy body
						//e.body.setActive - Switches off whether the physics model will continue to apply to the character body
			
				player3.addAAction(new Action(){
					@Override
					public void actionEffect(Player p, Enemy e, int delta) {
						p.actionA = true;
						p.lingerA = true;
						p.hitA = true;
						p.setTimer(0);
					}
			
					@Override
					public void lingeringEffect(Player p, Enemy e, int delta) {
						
						if(p.getTimer() > 50){
							if(p.current == p.movingLeft || p.current == p.idleLeft || p.current == p.jumpLeft){
								p.attackBoxOffset = -40;
								p.attackBox.setX(((-10 + p.playerOffsetX) + focusX) + p.attackBoxOffset);
							}
							else{
							    p.attackBoxOffset = 30;
							    p.attackBox.setX(((-10 + p.playerOffsetX) + focusX) + p.attackBoxOffset);
							}
							
							if(p.hitboxACheck(e)){
					        	e.decreaseHP(10);
						       	if(p.current == p.movingLeft || p.current == p.idleLeft || p.current == p.jumpLeft){
						       		e.body.applyForce(-100f, -20f);
						       	}
						       	else{
						       		e.body.applyForce(100f, -20);
						       	}
					        }
							
					        if(p.getHP() == 0){
					       	 	e.body.setActive(false);
					        }
						}
						
						if(p.getTimer() > 100){
							p.actionA = false;
							p.lingerA = false;
							p.hitA = false;
							
						}
					}
				});
				
				//Add B Action || Adds an action to the B Button
				//Action Effect - Activated immediately and only when the button is pressed				
				//Lingering Effect - Activated with each new update cycle

					// p - Player
						//p.actionB - Controls whether the Action Effect is activated
						//p.linger - Controls whether the Lingering Effect is activated
						//p.hitB - Controls the Hit Box for the B Action
						//p.setTimer - Sets the Effects Action Timer, which is used to time how long an effect lasts
						//p.current - Currently Displayed Sprite Animation
						//p.attackBoxOffset - Offset for the effect's hit box
						//p.body.applyForce - Applies a force upon the player body
					
					// e - Enemy
						//e.decreaseHP - Decreases the enemy's HP
						//e.body.applyForce - Applies a force upon the enemy body
						//e.body.setActive - Switches off whether the physics model will continue to apply to the character body
				
				player3.addBAction(new Action(){
					@Override
					public void actionEffect(Player p, Enemy e, int delta) {
						p.actionB = true;
						p.lingerB = true;
						p.hitB = true;
						p.movementOverride = true;
						p.setTimer(0);
					}
			
					@Override
					public void lingeringEffect(Player p, Enemy e, int delta) {
						p.attackBoxB.setBounds((p.playerOffsetX - 35) + p.attackBoxOffsetB, (p.playerOffsetY - 31), 20, 50);
						
					    if(p.getTimer() < 500){
					       	if(p.current == p.movingRight || p.current == p.idleRight || p.current == p.jumpRight){
					       		p.attackBoxOffsetB = 60;
					       		p.body.applyForce(30, -5);
					       	}
					       	else{
					       		p.attackBoxOffsetB = 0;
					       		p.body.applyForce(-30, -5);
					       	}
						
							if(p.attackBoxB.intersects(e.hitbox) && p.getHP() > 0){
					        	e.decreaseHP(20);
						       	if(p.current == p.movingLeft || p.current == p.idleLeft || p.current == p.jumpLeft){
						       		e.body.applyForce(-100f, -100f);
						       	}
						       	else{
						       		e.body.applyForce(100f, -100);
						       	}
					        }
					    }
			
			    		if(p.getTimer() > 500){
				    		p.actionB = false;
				    		p.lingerB = false;
				    		p.hitB = false;
				    		p.movementOverride = false;
			    		}
					}
					
				});
			
			//Player List || A list of playable characters available
			characterList = new Player[]{
					player1,
					player2,
					player3
			};
		
			//Set Current Player|| The character the player is currently using
			currentCharacter = characterList[characterListPosition];
		
			//Add Player to Level || Adds the player's Physics Body to the level
			for (int i = 0; i < characterList.length; i++) {
				currentLevel.world.add(characterList[i].body);
				if(i == characterListPosition){
					characterList[i].body.setActive(true);
				}
				else{
					characterList[i].body.setActive(false);
					
				}
				
				if(characterList[i].projectileSet){
					currentLevel.world.add(characterList[i].bullet.getBody());
				}
			}
		
		//Enemy Characters\\
			//Test Enemy1
		enemy1 = new Enemy("Bad Jazz", 
				"res/Jazz SS.png",
				3, 32, 36, 
				29, 36,
				30, 36,
				camX, camY, -500, 0);
		
		enemy2 = new Enemy("Bad Jazz", 
				"res/Jazz SS.png",
				3, 32, 36, 
				29, 36,
				30, 36,
				camX, camY, -1400, 0);
		
		enemy3 = new Enemy("Bad Jazz", 
				"res/Jazz SS.png",
				3, 32, 36, 
				29, 36,
				30, 36,
				camX, camY, -1600, 0);
		
		//Enemy List || List of Registered Enemies		
		enemyList = new Enemy[]{
				enemy1,
				enemy2, 
				enemy3
		};
		
		//Adds enemy bodies to the current level's Physics Model
		for (int i = 0; i < enemyList.length; i++) {
			currentLevel.world.add(enemyList[i].body);
		}
		
		//Camera\\
			//Camera Focus Center
			currentLevel.getCamera().centerOn(currentCharacter.body.getX() + focusX, currentCharacter.body.getY() + focusY);
			//Camera Offset
			camX = currentLevel.getCamera().getControlX();
			camY = currentLevel.getCamera().getControlY();
	}
	
	
	/**
	 * Render
	 * Draws all assets
	 * @param g
	 */
	
	public void render(Graphics g){	
		//Displays the Current Character's Timer
		g.drawString("Timer: " + currentCharacter.getTimer(), 50, 100);
		
		//Displays the Current Character's Speed Multiplier
		g.drawString("Speed Multiplier: " + currentCharacter.speedMultiplier, 50, 120);
		
		//Displays the Current Character's Speed
		g.drawString("Current Speed: " + currentCharacter.currentSpeed, 50, 140);
		
		//Renders Current Level
		currentLevel.render(g, (int)currentCharacter.body.getX(), (int)currentCharacter.body.getY());
		
		//Renders Each Enemy
		for (int i = 0; i < enemyList.length; i++) {
			enemyList[i].render(g, camX, camY);
		}
		
		//Renders the Current Character
		currentCharacter.render(g, focusX, focusY, camX, camY);
		
	}
	
	/**
	 * Update
	 * Updates all assets
	 * @param delta
	 * @param left
	 * @param right
	 */
	
	public void update(int delta, boolean left, boolean right){
		//Updates Current Character
		currentCharacter = characterList[characterListPosition];
		
		//Turns off all character bodies except for the one the player is controlling
		for (int i = 0; i < characterList.length; i++) {
			if(i == characterListPosition){
				characterList[i].body.setActive(true);
			}
			else{
				characterList[i].body.setActive(false);
			}
		}
		
		//Updates camera focus offset
		focusX = currentLevel.cameraFOffsetX;
		focusY = currentLevel.cameraFOffsetY;
		
		//Updates camera offset
		camX = currentLevel.getCamera().getControlX();
		camY = currentLevel.getCamera().getControlY();
		
		//Updates left and right flags
		this.left = left;
		this.right = right;
		
		//Updates the timer for lingering effects
		if(currentCharacter.lingerA || currentCharacter.lingerB){
			currentCharacter.setTimer(currentCharacter.getTimer() + delta);
		}
		
		//Updates Enemies, Enemy Debug Displays, Damage Checks for the Current Character and Lingering Effects
		for (int i = 0; i < enemyList.length; i++) {
			enemyList[i].update(delta, camX, camY);
			enemyList[i].setDebug(debugHitbox);
			currentCharacter.damageCheck(enemyList[i]);
			currentCharacter.lingeringActionsCheck(enemyList[i], delta);
		}
		
		//Updates Projectiles
		//Checks if the character can fire projectiles
		if(currentCharacter.projectileSet){
			//checks which effect is created
			if(currentCharacter.projectileA || currentCharacter.projectileB){
				//Activates projectile body
				currentCharacter.bullet.body.setActive(true);
				
				//Shot fired check
				if(!shotFired){
					//Updates projectile speed in the left direction.
			       	if(currentCharacter.current == currentCharacter.movingLeft 
			       			|| currentCharacter.current == currentCharacter.idleLeft 
			       			|| currentCharacter.current == currentCharacter.jumpLeft){
			       		updateProjectile = -800;
			       	}
			       	//Updates projectile speed in the right direction
			       	else{
			       		updateProjectile = 800;
			       	}
			       	
			       	//Sets character bullet position for the A Action
			       	if(currentCharacter.projectileA){
			       		currentCharacter.bullet.translate(currentCharacter.body.getX() + currentCharacter.projectileOffsetX, 
			       				currentCharacter.body.getY() + currentCharacter.projectileOffsetY);
			       	}
			       	//Sets character bullet position for the B Action
			       	if(currentCharacter.projectileB){
				       	currentCharacter.bullet.translate(currentCharacter.body.getX() + currentCharacter.projectileOffsetX, 
				       			currentCharacter.body.getY() + currentCharacter.projectileOffsetY);
			       	}
			       	//Declares a shot ready to fire
			       	shotFired = true;
				}
				
				//Fires Bullet 
				currentCharacter.bullet.fire(updateProjectile, -1);
				//Updates the bullet
				currentCharacter.bullet.update(camX, camY);
			}
			else{
				//Deactivates the projectile body
				currentCharacter.bullet.body.setActive(false);
			}
		}
		
		//Updates the camera center
		currentLevel.getCamera().centerOn(currentCharacter.body.getX() + focusX, currentCharacter.body.getY() + focusY);
		//Updates level assets
		currentLevel.update(delta);
		//Updates Debut settings for the Level
		currentLevel.setDebug(debugPhysRend, debugJBox2D);
		
		//Checks whether the character is touching the floor
		currentCharacter.checkJumpReset(currentLevel.camera.map, currentLevel.grid, currentLevel.getSlopeList());
		//Updates sprite
		currentCharacter.updateSprite(left, right);
		//Updates character Debug
		currentCharacter.setDebug(debugHitbox);
		
	}
	
	/**
	 * Removed Old Character
	 * Turns off the Current Character's body
	 */
	public void removeOldCharacter(){
		currentCharacter.body.setActive(false);		
	}
	
	/**
	 * New Character Setup
	 * Turns on the Current Character's Body
	 */
	public void newCharacterSetup(){
		currentCharacter.body.setActive(true);
	}
	
	/**
	 * Get Current Player
	 * Returns the Currently Controlled Character
	 * @return
	 */
	public Player getCurrentPlayer(){
		return currentCharacter;
	}
	
	
	/**
	 * Get Current Level
	 * Returns the Currently Played Level
	 * @return
	 */
	public Level getCurrentLevel(){
		return currentLevel;
	}
	
}
