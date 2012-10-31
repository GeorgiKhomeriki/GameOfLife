package engine;

public interface IGameState {
	public String getName();
	
	public void init();
	
	public void start();
	
	public void stop();
	
	public void render(int delta);
	
	public void update(int delta);
}
