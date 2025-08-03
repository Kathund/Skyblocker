package de.hysky.skyblocker.skyblock.profileviewer.rework.pages;

import de.hysky.skyblocker.annotations.Init;
import de.hysky.skyblocker.skyblock.profileviewer.model.*;
import de.hysky.skyblocker.skyblock.profileviewer.rework.ProfileLoadState;
import de.hysky.skyblocker.skyblock.profileviewer.rework.ProfileViewerPage;
import de.hysky.skyblocker.skyblock.profileviewer.rework.ProfileViewerScreenRework;
import de.hysky.skyblocker.skyblock.profileviewer.rework.ProfileViewerWidget;
import de.hysky.skyblocker.skyblock.profileviewer.rework.widgets.BarWidget;
import de.hysky.skyblocker.skyblock.profileviewer.rework.widgets.BoxedTextWidget;
import de.hysky.skyblocker.skyblock.profileviewer.rework.widgets.ItemWidget;
import de.hysky.skyblocker.skyblock.tabhud.util.Ico;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class CrimsonPage implements ProfileViewerPage {

	List<ProfileViewerWidget.Instance> widgets = new ArrayList<>();

	public CrimsonPage(ProfileLoadState.SuccessfulLoad load) {
		var crimsonIsleData = load.member().netherIslandPlayerData;
		List<Text> tiers = new ArrayList<>();
		// TODO: Add kuudra key icons
		for (NetherIslandPlayerData.KuudraCompletedTiers.Tier tier : NetherIslandPlayerData.KuudraCompletedTiers.Tier.values()) {
			tiers.add(Text.literal(tier.getName() + ": ").formatted(Formatting.RED).copy().append(Text.literal(String.valueOf(tier.getCompletions(crimsonIsleData.kuudraCompletedTiers))).formatted(Formatting.WHITE)));
			tiers.add(Text.literal("Highest Wave: ").formatted(Formatting.RED).copy().append(Text.literal(tier.getHighestWave(crimsonIsleData.kuudraCompletedTiers) != 0 ? String.valueOf(tier.getHighestWave(crimsonIsleData.kuudraCompletedTiers)) : "N/A").formatted(Formatting.WHITE)));
		}
		tiers.add(Text.literal("Total Runs: ").formatted(Formatting.RED).copy().append(Text.literal(String.valueOf(NetherIslandPlayerData.KuudraCompletedTiers.getTotalCompletions(crimsonIsleData.kuudraCompletedTiers))).formatted(Formatting.WHITE)));

		var tiersWidget = widget(0, 0, BoxedTextWidget.boxedText(BarWidget.WIDTH + ItemWidget.WIDTH, tiers, ItemWidget.WIDTH));
		widgets.add(tiersWidget);

		int tierIndex = 0;
		var textRenderer = MinecraftClient.getInstance().textRenderer;
		for (NetherIslandPlayerData.KuudraCompletedTiers.Tier tier : NetherIslandPlayerData.KuudraCompletedTiers.Tier.values()) {
			widgets.add(widget(0,  ((textRenderer.fontHeight * 2) + 2) * tierIndex, new ItemWidget(tier.getIcon(), false)));
			tierIndex++;
		}
		widgets.add(widget(
				0, tiersWidget.getHeight() + 5, BoxedTextWidget.boxedText(BarWidget.WIDTH,
						List.of(
								Text.literal("Faction: ").formatted(Formatting.GREEN).copy().append(crimsonIsleData.selectedFaction != null ? Text.literal(crimsonIsleData.selectedFaction).formatted(crimsonIsleData.selectedFaction.equals("mages") ? Formatting.DARK_PURPLE : Formatting.RED) : Text.literal("N/A").formatted(Formatting.WHITE)),
								Text.literal("Mage Reputation: ").formatted(Formatting.DARK_PURPLE).copy().append(Text.literal(String.valueOf(crimsonIsleData.magesReputation)).formatted(Formatting.WHITE)),
								Text.literal("Barbarian Reputation: ").formatted(Formatting.RED).copy().append(Text.literal(String.valueOf(crimsonIsleData.barbariansReputation)).formatted(Formatting.WHITE))
						))
		));

		List<Text> dojos = new ArrayList<>();
		for (NetherIslandPlayerData.Dojo.DojoTest test : NetherIslandPlayerData.Dojo.DojoTest.values()) {
			dojos.add(Text.literal("Test of " + test.getName() + ": " + test.getScore(crimsonIsleData.dojo)).formatted(test.getColor()).copy().append(Text.literal(" (" + NetherIslandPlayerData.Dojo.ScoreRank.fromScore(test.getScore(crimsonIsleData.dojo)).name() + ")").formatted(Formatting.WHITE)));
		}
		dojos.add(Text.of("Points: ").copy().append(Text.literal(String.valueOf(NetherIslandPlayerData.Dojo.getTotalScore(crimsonIsleData.dojo))).formatted(Formatting.GOLD)));
		dojos.add(Text.of("Belt: ").copy().append(Text.literal(NetherIslandPlayerData.Dojo.Belt.fromScore(NetherIslandPlayerData.Dojo.getTotalScore(crimsonIsleData.dojo)).getName()).formatted(NetherIslandPlayerData.Dojo.Belt.fromScore(NetherIslandPlayerData.Dojo.getTotalScore(crimsonIsleData.dojo)).getColor())));
		widgets.add(widget(BoxedTextWidget.PADDING + BarWidget.WIDTH + 5 + ItemWidget.WIDTH, 0, BoxedTextWidget.boxedText(BarWidget.WIDTH, dojos)));

//		widgets.add(widget(0, 0, new ItemWidget(NetherIslandPlayerData.KuudraCompletedTiers.Tier.HOT.getIcon(), true)));

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
