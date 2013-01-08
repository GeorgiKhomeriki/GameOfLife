package game;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import util.Random;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_QUADS;

import engine.IGameState;

public class GameState implements IGameState {
	public static final String STATE_NAME = "STATE_GAME";

	private boolean[][] cells;

	@Override
	public String getName() {
		return STATE_NAME;
	}

	@Override
	public void init() {
		cells = new boolean[100][100];
		initRandomGrid();
	}

	private void initRandomGrid() {
		int gridWidth = cells.length;
		int gridHeight = cells[0].length;
		for (int i = 0; i < gridWidth; i++) {
			for (int j = 0; j < gridHeight; j++) {
				cells[i][j] = Random.get().nextFloat() <= 0.5f;
			}
		}
	}

	@Override
	public void start() {

	}

	@Override
	public void stop() {

	}

	@Override
	public void render(int delta) {
		glLoadIdentity();
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		renderGrid();
		renderCells();
		
	}

	private void renderCells() {
		int gridWidth = cells.length;
		int gridHeight = cells[0].length;
		float cellWidth = Display.getWidth() / gridWidth;
		float cellHeight = Display.getHeight() / gridHeight;
		glColor3f(0.0f, 1.0f, 0.0f);
		for (int i = 0; i < gridWidth; i++) {
			for (int j = 0; j < gridHeight; j++) {
				if (cells[i][j]) {
					float x = i * cellWidth;
					float y = j * cellHeight;
					glBegin(GL_QUADS);
					glVertex2f(x, y);
					glVertex2f(x + cellWidth, y);
					glVertex2f(x + cellWidth, y + cellHeight);
					glVertex2f(x, y + cellHeight);
					glEnd();
				}
			}
		}
	}

	private void renderGrid() {
		int gridWidth = cells.length;
		int gridHeight = cells[0].length;
		int screenWidth = Display.getWidth();
		int screenHeight = Display.getHeight();
		float cellWidth = screenWidth / gridWidth;
		float cellHeight = screenHeight / gridHeight;
		for (int x = 0; x <= screenWidth; x += cellWidth) {
			glBegin(GL_LINES);
			glColor3f(0.0f, 0.0f, 1.0f);
			glVertex2f(x, 0.0f);
			glColor3f(1.0f, 0.0f, 0.0f);
			glVertex2f(x, screenHeight);
			glEnd();
		}
		for (int y = 0; y <= screenHeight; y += cellHeight) {
			glBegin(GL_LINES);
			glColor3f(0.0f, 0.0f, 1.0f);
			glVertex2f(0, y);
			glColor3f(1.0f, 0.0f, 0.0f);
			glVertex2f(screenWidth, y);
			glEnd();
		}
	}

	@Override
	public void update(int delta) {
		updateCells();
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			initRandomGrid();
		}
	}

	private void updateCells() {
		int gridWidth = cells.length;
		int gridHeight = cells[0].length;
		boolean[][] newCells = new boolean[gridWidth][gridHeight];
		for (int i = 1; i < gridWidth - 1; i++) {
			for (int j = 1; j < gridHeight - 1; j++) {
				boolean isAlive = cells[i][j];
				int neighbors = countNeighbors(i, j);
				newCells[i][j] = neighbors == 2 && isAlive || neighbors == 3;
			}
		}
		this.cells = newCells;
	}

	private int countNeighbors(int i, int j) {
		int n = 0;
		// lower row
		if (cells[i - 1][j - 1])
			n++;
		if (cells[i][j - 1])
			n++;
		if (cells[i + 1][j - 1])
			n++;

		// middle row
		if (cells[i - 1][j])
			n++;
		if (cells[i + 1][j])
			n++;

		// upper row
		if (cells[i - 1][j + 1])
			n++;
		if (cells[i][j + 1])
			n++;
		if (cells[i + 1][j + 1])
			n++;

		return n;
	}

}
