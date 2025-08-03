package de.hysky.skyblocker.skyblock.profileviewer.rework.widgets;

import de.hysky.skyblocker.SkyblockerMod;
import de.hysky.skyblocker.skyblock.profileviewer.rework.ProfileViewerScreenRework;
import de.hysky.skyblocker.skyblock.profileviewer.rework.ProfileViewerWidget;
import de.hysky.skyblocker.skyblock.profileviewer.utils.LevelFinder;
import de.hysky.skyblocker.utils.NEURepoManager;
import de.hysky.skyblocker.utils.render.HudHelper;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.OptionalInt;

import static de.hysky.skyblocker.utils.Formatters.INTEGER_NUMBERS;
import static de.hysky.skyblocker.utils.Formatters.SHORT_INTEGER_NUMBERS;

public class ItemWidget implements ProfileViewerWidget {

	private static final Identifier BACKGROUND = Identifier.of(SkyblockerMod.NAMESPACE, "profile_viewer/generic_background");

	public static final int WIDTH = 22;
	public static final int HEIGHT = 22;

	private final ItemStack item;
	private final Boolean background;

	public ItemWidget(ItemStack item, Boolean background) {
		this.item = item;
		this.background = background;
	}

	@Override
	public void render(DrawContext drawContext, int x, int y, int mouseX, int mouseY, float deltaTicks) {
		if (background) HudHelper.renderNineSliceColored(drawContext, BACKGROUND, x, y, WIDTH, HEIGHT, Colors.WHITE);
		drawContext.drawItem(item, x + 2, y + 2);
	}

	@Override
	public int getHeight() {
		return HEIGHT;
	}

	@Override
	public int getWidth() {
		return WIDTH;
	}

}
