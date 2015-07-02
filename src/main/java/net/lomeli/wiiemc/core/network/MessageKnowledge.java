package net.lomeli.wiiemc.core.network;

import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;

import java.util.List;
import java.util.Set;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

import net.lomeli.wiiemc.WIIEMC;

public class MessageKnowledge implements IMessage, IMessageHandler<MessageKnowledge, IMessage> {
    private List<ItemStack> knownTransmutations;

    public MessageKnowledge() {
        this.knownTransmutations = Lists.newArrayList();
    }

    public MessageKnowledge(Set<ItemStack> knowledge) {
        this();
        this.knownTransmutations.addAll(knowledge);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        NBTTagCompound tag = ByteBufUtils.readTag(buf);
        if (tag != null && tag.hasKey("KnownItems", 9)) {
            NBTTagList tagList = tag.getTagList("KnownItems", 10);
            for (int i = 0; i < tagList.tagCount(); i++) {
                NBTTagCompound itemTag = tagList.getCompoundTagAt(i);
                this.knownTransmutations.add(ItemStack.loadItemStackFromNBT(itemTag));
            }
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        NBTTagCompound tag = new NBTTagCompound();
        NBTTagList tagList = new NBTTagList();
        for (ItemStack stack : knownTransmutations) {
            if (stack != null) {
                NBTTagCompound itemTag = new NBTTagCompound();
                stack.writeToNBT(itemTag);
                tagList.appendTag(itemTag);
            }
        }
        tag.setTag("KnownItems", tagList);
        ByteBufUtils.writeTag(buf, tag);
    }

    @Override
    public IMessage onMessage(MessageKnowledge message, MessageContext ctx) {
        if (message.knownTransmutations != null)
            WIIEMC.proxy.setKnowledge(message.knownTransmutations);
        return null;
    }
}
