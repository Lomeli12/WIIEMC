package net.lomeli.wiiemc.core;

import java.util.Collection;

import net.minecraft.item.ItemStack;

import net.lomeli.wiiemc.core.helper.KnowledgeHelper;

public class ClientProxy extends Proxy {
    private KnowledgeHelper knowledgeHelper;

    @Override
    public void preInit() {
        super.preInit();
        knowledgeHelper = new KnowledgeHelper();
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void setKnowledge(Collection<ItemStack> itemStacks) {
        knowledgeHelper.setPlayerKnowledge(itemStacks);
    }

    @Override
    public boolean doesPlayerKnow(ItemStack stack) {
        return knowledgeHelper.doesPlayerKnow(stack);
    }

    @Override
    public boolean canPlayerLearn(ItemStack stack) {
        return knowledgeHelper.canPlayerLearn(stack);
    }

    @Override
    public KnowledgeHelper getKnowledgeHelper() {
        return knowledgeHelper;
    }
}
