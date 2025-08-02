package de.hysky.skyblocker.skyblock.profileviewer.rework.pages;

import de.hysky.skyblocker.annotations.Init;
import de.hysky.skyblocker.skyblock.profileviewer.model.*;
import de.hysky.skyblocker.skyblock.profileviewer.rework.ProfileLoadState;
import de.hysky.skyblocker.skyblock.profileviewer.rework.ProfileViewerPage;
import de.hysky.skyblocker.skyblock.profileviewer.rework.ProfileViewerScreenRework;
import de.hysky.skyblocker.skyblock.profileviewer.rework.ProfileViewerWidget;
import de.hysky.skyblocker.skyblock.profileviewer.rework.widgets.BarWidget;
import de.hysky.skyblocker.skyblock.profileviewer.rework.widgets.BoxedTextWidget;
import de.hysky.skyblocker.skyblock.tabhud.util.Ico;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class CrimsonPage implements ProfileViewerPage {

	List<ProfileViewerWidget.Instance> widgets = new ArrayList<>();

	public CrimsonPage(ProfileLoadState.SuccessfulLoad load) {
		var crimsonIsleData = load.member().netherIslandPlayerData;
		List<Text> tiers = new ArrayList<>();
		// TODO: Add kuudra key icons
		for (NetherIslandPlayerData.KuudraCompletedTiers.Tier tier : NetherIslandPlayerData.KuudraCompletedTiers.Tier.values()) {
			tiers.add(Text.of(tier.getName() + ": " + (tier.getCompletions(crimsonIsleData.kuudraCompletedTiers))));
			tiers.add(Text.of("Highest Wave: " + (tier.getHighestWave(crimsonIsleData.kuudraCompletedTiers) != 0 ? tier.getHighestWave(crimsonIsleData.kuudraCompletedTiers) : "N/A")));
		}
		tiers.add(Text.of("Total Runs: " + NetherIslandPlayerData.KuudraCompletedTiers.getTotalCompletions(crimsonIsleData.kuudraCompletedTiers)));

		var tiersWidget = widget(0, 0, BoxedTextWidget.boxedText(BarWidget.WIDTH, tiers));
		widgets.add(tiersWidget);
		widgets.add(widget(
				0, tiersWidget.getHeight() + 5, BoxedTextWidget.boxedText(BarWidget.WIDTH,
						List.of(
								Text.of("Faction: " + (crimsonIsleData.selectedFaction != null ? crimsonIsleData.selectedFaction : "N/A")),
								Text.of("Mage Reputation: " + (crimsonIsleData.magesReputation)),
								Text.of("Barbarian Reputation: " + (crimsonIsleData.barbariansReputation))
						))
		));

		List<Text> dojos = new ArrayList<>();
		for (NetherIslandPlayerData.Dojo.DojoTest test : NetherIslandPlayerData.Dojo.DojoTest.values()) {
			dojos.add(Text.of("Test of " + test.getName() + ": " + test.getScore(crimsonIsleData.dojo) + " (" + NetherIslandPlayerData.Dojo.ScoreRank.fromScore(test.getScore(crimsonIsleData.dojo)).name() + ")"));
		}
		dojos.add(Text.of("Points: " + NetherIslandPlayerData.Dojo.getTotalScore(crimsonIsleData.dojo)));
		dojos.add(Text.of("Belt: " + NetherIslandPlayerData.Dojo.Belt.fromScore(NetherIslandPlayerData.Dojo.getTotalScore(crimsonIsleData.dojo)).getName()));
		widgets.add(widget(BoxedTextWidget.PADDING + BarWidget.WIDTH + 5, 0, BoxedTextWidget.boxedText(BarWidget.WIDTH, dojos)));

	}

	@Init
	public static void init() {
		ProfileViewerScreenRework.PAGE_CONSTRUCTORS.add(CrimsonPage::new);
	}

	@Override
	public int getSortIndex() {
		return 3;
	}

	@Override
	public ItemStack getIcon() {
		return Ico.NETHERRACK;
	}

	@Override
	public String getName() {
		return "Crimson Isle";
	}

	@Override
	public List<ProfileViewerWidget.Instance> getWidgets() {
		// TODO: add player widget to the left only on this page.
		return widgets;
	}
}
