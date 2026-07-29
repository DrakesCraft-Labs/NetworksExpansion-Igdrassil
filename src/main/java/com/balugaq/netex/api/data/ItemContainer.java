package com.balugaq.netex.api.data;

import io.github.sefiraat.networks.network.stackcaches.ItemStackCache;
import io.github.sefiraat.networks.utils.StackUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@Getter
@ToString
public class ItemContainer extends ItemStackCache {

    private final int id;

    @Setter
    @Getter
    private int amount;

    public ItemContainer(int id, @NotNull ItemStack item, int amount) {
        super(StackUtils.getAsQuantity(item, 1));
        this.id = id;
        this.amount = amount;
    }

    public @NotNull ItemStack getSample() {
        return itemStack.clone();
    }

    public @NotNull ItemStack getSampleDirectly() {
        return itemStack;
    }

    public boolean isSimilar(ItemStack other) {
        return StackUtils.itemsMatch(this, other);
    }

    public void addAmount(int amount) {
        this.amount += amount;
    }

    /**
     * Remove specific amount from container
     *
     * @param amount: amount will be removed
     * @return amount that actual removed
     */
    public int removeAmount(int amount) {
        if (this.amount > amount) {
            this.amount -= amount;
            return amount;
        } else {
            int re = this.amount;
            this.amount = 0;
            return re;
        }
    }
}
