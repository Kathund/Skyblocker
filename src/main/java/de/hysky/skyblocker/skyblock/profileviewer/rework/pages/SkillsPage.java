package de.hysky.skyblocker.skyblock.profileviewer.rework.pages;

import de.hysky.skyblocker.annotations.Init;
import de.hysky.skyblocker.skyblock.profileviewer.model.PlayerData;
import de.hysky.skyblocker.skyblock.profileviewer.rework.ProfileLoadState;
import de.hysky.skyblocker.skyblock.profileviewer.rework.ProfileViewerPage;
import de.hysky.skyblocker.skyblock.profileviewer.rework.ProfileViewerScreenRework;
import de.hysky.skyblocker.skyblock.profileviewer.rework.ProfileViewerWidget;
import de.hysky.skyblocker.skyblock.profileviewer.rework.widgets.BarWidget;
import de.hysky.skyblocker.skyblock.profileviewer.utils.LevelFinder;
import de.hysky.skyblocker.skyblock.tabhud.util.Ico;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

public class SkillsPage implements ProfileViewerPage {

	List<ProfileViewerWidget.Instance> widgets = new ArrayList<>();

	public SkillsPage(ProfileLoadState.SuccessfulLoad load) {
		var playerData = load.member().playerData;
		List<ProfileViewerWidget> skills = new ArrayList<>();
		for (PlayerData.Skill skill : PlayerData.Skill.values()) {
			OptionalInt max = OptionalInt.empty();

			if (skill == PlayerData.Skill.FARMING || skill == PlayerData.Skill.FORAGING || skill == PlayerData.Skill.TAMING) {
				max = OptionalInt.of(50);
			}

			LevelFinder.LevelInfo levelInfo = playerData.getSkillLevel(skill);

			if (skill == PlayerData.Skill.SOCIAL) {
				levelInfo = PlayerData.Skill.SOCIAL.getLevelInfo(load.profile().members.values().stream().mapToDouble(m -> m.playerData.getSkillExperience(PlayerData.Skill.SOCIAL)).sum());
			} else if (skill == PlayerData.Skill.CATACOMBS) {
				levelInfo = PlayerData.Skill.CATACOMBS.getLevelInfo(load.member().dungeons.dungeonInfo.catacombs.experience);
			}

			skills.add(new BarWidget(skill.getName(), skill.getIcon(), levelInfo, OptionalInt.empty(), max));
		}

		int i = 0;
		for (var skill : skills) {
			int x = i < 6 ? 88 : 88 + 113;
			int y = (i % 6) * (2 + 26);
			i++;
			widgets.add(widget(
					x, y, skill
			));
		}
		widgets.add(widget(0, 0, new EntityViewerWidget(load.mainMemberId())));
		widgets.add(widget(0, 112, new PlayerMetaWidget(load)));
	}

	@Init
	public static void init() {
		ProfileViewerScreenRework.PAGE_CONSTRUCTORS.add(SkillsPage::new);
	}

	@Override
	public int getSortIndex() {
		return 0;
	}

	@Override
	public ItemStack getIcon() {
		return Ico.IRON_SWORD;
	}

	@Override
	public String getName() {
		return "Skills";
	}

	@Override
	public List<ProfileViewerWidget.Instance> getWidgets() {
		// TODO: add player widget to the left only on this page.
		return widgets;
	}
}
