package engine;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.io.IOException;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Font {
	private Texture texture;
	private int numChars;
	private float widthScale;
	private float heightScale;

	public Font(String file, int numChars, int pixels) {
		try {
			texture = TextureLoader.getTexture("PNG",
					ResourceLoader.getResourceAsStream(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.numChars = numChars;
		if (pixels == 16) {
			widthScale = 30.0f;
			heightScale = 20.0f;
		} else if (pixels == 8) {
			widthScale = 80.0f;
			heightScale = 45.0f;
		}
	}

	public void renderText(String text, float x, float y) {
		texture.bind();
		float width = Display.getWidth() / widthScale;
		float height = Display.getHeight() / heightScale;
		for (int i = 0; i < text.length(); i++) {
			float c = (int) text.charAt(i) - 32;
			if (c > 0) {
				glBegin(GL_QUADS);
				glTexCoord2f(c / numChars * texture.getWidth(),
						texture.getHeight());
				glVertex2f(x + i * width, y);
				glTexCoord2f((c + 1) / numChars * texture.getWidth(),
						texture.getHeight());
				glVertex2f(x + i * width + width, y);
				glTexCoord2f((c + 1) / numChars * texture.getWidth(), 0.0f);
				glVertex2f(x + i * width + width, y + height);
				glTexCoord2f(c / numChars * texture.getWidth(), 0.0f);
				glVertex2f(x + i * width, y + height);
				glEnd();
			}
		}
	}

	public float getCharacterWidth() {
		return Display.getWidth() / widthScale;
	}

	public float getCharacterHeight() {
		return Display.getHeight() / heightScale;
	}

}
