package net.mcatlas.economy;

import org.bukkit.Material;

public enum Worth {

    GOLD_INGOT(Material.GOLD_INGOT, 1);

    final Material material;
    final int amount;

    private Worth(Material material, int amount) {
        this.material = material;
        this.amount = amount;
    }

    public Material getMaterial() {
        return this.material;
    }

    public int getAmount() {
        return this.amount;
    }

}

