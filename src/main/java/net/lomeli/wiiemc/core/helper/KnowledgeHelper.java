package net.lomeli.wiiemc.core.helper;

import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

import com.pahimar.ee3.api.knowledge.AbilityRegistryProxy;

public class KnowledgeHelper {
    public static Comparator<ItemStack> idComparator = new Comparator<ItemStack>() {
        public int compare(ItemStack itemStack1, ItemStack itemStack2) {
            if (itemStack1 != null && itemStack2 != null) {
                // Sort on itemID
                if (Item.getIdFromItem(itemStack1.getItem()) - Item.getIdFromItem(itemStack2.getItem()) == 0) {
                    // Sort on item
                    if (itemStack1.getItem() == itemStack2.getItem()) {
                        // Then sort on meta
                        if ((itemStack1.getItemDamage() == itemStack2.getItemDamage()) || itemStack1.getItemDamage() == OreDictionary.WILDCARD_VALUE || itemStack2.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
                            // Then sort on NBT
                            if (itemStack1.hasTagCompound() && itemStack2.hasTagCompound()) {
                                // Then sort on stack size
                                if (ItemStack.areItemStackTagsEqual(itemStack1, itemStack2))
                                    return (itemStack1.stackSize - itemStack2.stackSize);
                                else
                                    return (itemStack1.getTagCompound().hashCode() - itemStack2.getTagCompound().hashCode());
                            } else if (!(itemStack1.hasTagCompound()) && itemStack2.hasTagCompound())
                                return -1;
                            else if (itemStack1.hasTagCompound() && !(itemStack2.hasTagCompound()))
                                return 1;
                            else
                                return (itemStack1.stackSize - itemStack2.stackSize);
                        } else
                            return (itemStack1.getItemDamage() - itemStack2.getItemDamage());
                    } else
                        return itemStack1.getItem().getUnlocalizedName(itemStack1).compareToIgnoreCase(itemStack2.getItem().getUnlocalizedName(itemStack2));
                } else
                    return Item.getIdFromItem(itemStack1.getItem()) - Item.getIdFromItem(itemStack2.getItem());
            } else if (itemStack1 != null)
                return -1;
            else if (itemStack2 != null)
                return 1;
            else
                return 0;
        }
    };
    private Set<ItemStack> knownTransmutations;

    public KnowledgeHelper() {
        this.knownTransmutations = new TreeSet<ItemStack>(idComparator);
    }

    public boolean doesPlayerKnow(ItemStack stack) {
        if (stack != null && knownTransmutations != null) {
            ItemStack unitItemStack = stack.copy();
            unitItemStack.stackSize = 1;
            return this.knownTransmutations.contains(unitItemStack);
        }
        return false;
    }

    public boolean canPlayerLearn(ItemStack stack) {
        if (AbilityRegistryProxy.isLearnable(stack))
            return !doesPlayerKnow(stack);
        return false;
    }

    public Set<ItemStack> getPlayerKnowledge() {
        return knownTransmutations;
    }

    public void setPlayerKnowledge(Collection<ItemStack> items) {
        this.knownTransmutations.clear();
        this.knownTransmutations.addAll(items);
    }
}
