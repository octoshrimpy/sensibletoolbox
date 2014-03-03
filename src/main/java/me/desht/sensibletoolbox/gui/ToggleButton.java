package me.desht.sensibletoolbox.gui;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ToggleButton extends ClickableGadget {
	private final ItemStack texture1;
	private final ItemStack texture2;
	private final ToggleListener callback;
	boolean value;

	public ToggleButton(InventoryGUI gui, boolean value, ItemStack texture1, ItemStack texture2, ToggleListener callback) {
		super(gui);
		this.texture1 = texture1;
		this.texture2 = texture2;
		this.callback = callback;
		this.value = value;
	}

	@Override
	public void onClicked(InventoryClickEvent event) {
		boolean newValue = !value;

		if (callback.run(event.getRawSlot(), newValue)) {
			value = newValue;
			event.setCurrentItem(getTexture());
		} else {
			// vetoed!
			if (event.getWhoClicked() instanceof Player) {
				((Player)event.getWhoClicked()).playSound(event.getWhoClicked().getLocation(), Sound.NOTE_BASS, 1.0f, 1.0f);
			}
		}
	}

	@Override
	public ItemStack getTexture() {
		return value ? texture1 : texture2;
	}

	public interface ToggleListener {
		public boolean run(int slot, boolean newValue);
	}
}