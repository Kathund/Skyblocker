package de.hysky.skyblocker.skyblock.profileviewer.rework.pages;

import de.hysky.skyblocker.annotations.Init;
import de.hysky.skyblocker.skyblock.profileviewer.model.SlayerData;
import de.hysky.skyblocker.skyblock.profileviewer.rework.*;
import de.hysky.skyblocker.skyblock.profileviewer.rework.widgets.BarWidget;
import de.hysky.skyblocker.skyblock.profileviewer.rework.widgets.SlayerWidget;
import de.hysky.skyblocker.skyblock.tabhud.util.Ico;
import net.minecraft.item.ItemStack;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

public class SlayersPage implements ProfileViewerPage {

	List<ProfileViewerWidget.Instance> widgets = new ArrayList<>();

	public SlayersPage(ProfileLoadState.SuccessfulLoad load) {
		var slayerData = load.member().slayer;
		List<ProfileViewerWidget> slayers = new ArrayList<>();
		for (SlayerData.Slayer slayer : SlayerData.Slayer.values()) {
			slayers.add(new BarWidget(slayer.getName(), slayer.getIcon(), slayerData.getSkillLevel(slayer), OptionalInt.empty(), OptionalInt.empty()));
		}
		for (SlayerData.Slayer slayer : SlayerData.Slayer.values()) {
			slayers.add(new SlayerWidget(slayer, slayerData.getSlayerData(slayer)));
		}

		int i = 0;
		for (var slayer : slayers) {
			int x = i < 6 ? 88 : 88 + 113;
			int y = (i % 6) * (2 + 26);
			i++;
			widgets.add(widget(
					x, y, slayer
			));
		}
		widgets.add(widget(0, 0, new EntityViewerWidget(load.mainMemberId())));
		widgets.add(widget(0, 112, new PlayerMetaWidget(load)));
	}

	@Init
	public static void init() {
		ProfileViewerScreenRework.PAGE_CONSTRUCTORS.add(SlayersPage::new);
	}

	@Override
	public int getSortIndex() {
		return 1;
	}

	@Override
	public ItemStack getIcon() {
		return Ico.AATROX_BATPHONE_SKULL;
	}

	@Override
	public String getName() {
		return "Slayers";
	}

	@Override
	public List<ProfileViewerWidget.Instance> getWidgets() {
		// TODO: add player widget to the left only on this page.
		return widgets;
	}
}
