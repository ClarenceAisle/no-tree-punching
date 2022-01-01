/*
 * Part of the No Tree Punching mod by AlcatrazEscapee.
 * Work under copyright. See the project LICENSE.md for details.
 */

package com.alcatrazescapee.notreepunching.common.tileentity;

import javax.annotation.Nullable;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;

/**
 * A tile entity that handles saving NBT data and default update packets
 */
public abstract class ModTileEntity extends BlockEntity
{
    public ModTileEntity(BlockEntityType<?> type)
    {
        super(type);
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket()
    {
        return new ClientboundBlockEntityDataPacket(getBlockPos(), 1, save(new CompoundTag()));
    }


    @Override
    public CompoundTag getUpdateTag()
    {
        return save(super.getUpdateTag());
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt)
    {
        load(getBlockState(), pkt.getTag());
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundTag nbt)
    {
        load(state, nbt);
    }

    /**
     * Syncs the TE data to client via means of a block update
     * Use for stuff that is updated infrequently, for data that is analogous to changing the state.
     * DO NOT call every tick
     */
    public void markForBlockUpdate()
    {
        if (level != null)
        {
            BlockState state = level.getBlockState(worldPosition);
            level.sendBlockUpdated(worldPosition, state, state, 3);
            setChanged();
        }
    }

    /**
     * Marks a tile entity for syncing without sending a block update.
     * Use preferentially over {@link InventoryTileEntity#markForBlockUpdate()} if there's no reason to have a block update.
     * For container based integer synchronization, see ITileFields
     * DO NOT call every tick
     */
    public void markForSync()
    {
        sendVanillaUpdatePacket();
        setChanged();
    }

    /**
     * Marks the tile entity dirty without updating comparator output.
     * Useful when called a lot for TE's that don't have a comparator output
     */
    protected void markDirtyFast()
    {
        if (level != null)
        {
            getBlockState();
            level.blockEntityChanged(worldPosition, this);
        }
    }

    protected void sendVanillaUpdatePacket()
    {
        ClientboundBlockEntityDataPacket packet = getUpdatePacket();
        BlockPos pos = getBlockPos();

        if (packet != null && level instanceof ServerLevel)
        {
            ((ServerChunkCache) level.getChunkSource()).chunkMap.getPlayers(new ChunkPos(pos), false).forEach(e -> e.connection.send(packet));
        }
    }
}
