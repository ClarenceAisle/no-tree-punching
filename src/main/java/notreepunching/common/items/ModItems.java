/*
 *  Part of the No Tree Punching Mod by alcatrazEscapee
 *  Work under Copyright. Licensed under the GPL-3.0.
 *  See the project LICENSE.md for more information.
 */

package notreepunching.common.items;

import net.minecraft.item.Item;

import alcatrazcore.util.RegistryHelper;

import static notreepunching.NoTreePunching.MOD_ID;

public class ModItems
{
    public static Item FLINT_SHARD;

    public static void preInit()
    {
        RegistryHelper r = RegistryHelper.get(MOD_ID);
    }
}
