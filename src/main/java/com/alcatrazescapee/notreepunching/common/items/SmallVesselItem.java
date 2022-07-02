package com.alcatrazescapee.notreepunching.common.items;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.network.NetworkHooks;

import com.alcatrazescapee.notreepunching.common.ModTags;
import com.alcatrazescapee.notreepunching.common.container.SmallVesselContainer;
import com.alcatrazescapee.notreepunching.util.Helpers;
import com.alcatrazescapee.notreepunching.util.ItemStackItemHandler;

import static com.alcatrazescapee.notreepunching.NoTreePunching.MOD_ID;

public class SmallVesselItem extends Item
{
    public static final int SLOT_ROWS = 3, SLOT_COLUMNS = 3, SLOTS = SLOT_ROWS * SLOT_COLUMNS;

    public SmallVesselItem()
    {
        super(new Properties().tab(ModItems.Tab.ITEMS).stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        final ItemStack stack = player.getItemInHand(hand);
        if (player instanceof ServerPlayer serverPlayer && !player.isShiftKeyDown())
        {
            stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler ->
                NetworkHooks.openGui(serverPlayer,
                    new SimpleMenuProvider((windowID, playerInventory, playerIn) -> new SmallVesselContainer(windowID, playerInventory, hand), stack.getHoverName()),
                    buffer -> buffer.writeBoolean(hand == InteractionHand.MAIN_HAND)));
        }
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
    }

    /**
     * Copy pasta from {@link net.minecraft.world.level.block.ShulkerBoxBlock#appendHoverText(ItemStack, BlockGetter, List, TooltipFlag)}
     */
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag)
    {
        if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY != null)
        {
            stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(handler -> {
                int displayCount = 0;
                int totalCount = 0;

                for (int i = 0; i < handler.getSlots(); i++)
                {
                    ItemStack contentStack = handler.getStackInSlot(i);
                    if (!contentStack.isEmpty())
                    {
                        ++totalCount;
                        if (displayCount <= 4)
                        {
                            ++displayCount;
                            MutableComponent textComponent = contentStack.getHoverName().plainCopy();
                            textComponent.append(" x").append(String.valueOf(contentStack.getCount()));
                            tooltip.add(textComponent);
                        }
                    }
                }

                if (totalCount > displayCount)
                {
                    MutableComponent textComponent = new TranslatableComponent(MOD_ID + ".tooltip.small_vessel_more", totalCount - displayCount);
                    textComponent.setStyle(textComponent.getStyle().applyFormat(ChatFormatting.ITALIC));
                    tooltip.add(textComponent);
                }
            });
        }
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt)
    {
        return new SmallVesselItemStackHandler(nbt, stack, SLOTS);
    }

    static final class SmallVesselItemStackHandler extends ItemStackItemHandler
    {
        SmallVesselItemStackHandler(@Nullable CompoundTag capNbt, ItemStack stack, int slots)
        {
            super(capNbt, stack, slots);
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack)
        {
            return !Helpers.isItem(stack.getItem(), ModTags.Items.SMALL_VESSEL_BLACKLIST);
        }
    }
}