package notreepunching.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import notreepunching.NoTreePunching;

public class ModItems {

    // Declare Instances of all items

    public static ItemBase rock = new ItemBase("rock");
    public static ItemBase grassFiber = new ItemBase("grass_fiber");
    public static ItemBase poorIron = new ItemBase("poor_iron");
    public static ItemFireStarter fireStarter = new ItemFireStarter("fire_starter");

    public static ItemKnife stoneKnife = new ItemKnife(NoTreePunching.toolMaterialCrudeStone,"stone_knife");
    public static ItemKnife ironKnife = new ItemKnife(Item.ToolMaterial.IRON,"iron_knife");
    public static ItemKnife goldKnife = new ItemKnife(Item.ToolMaterial.GOLD,"gold_knife");
    public static ItemKnife diamondKnife = new ItemKnife(Item.ToolMaterial.DIAMOND,"diamond_knife");

    public static ItemMattock ironMattock = new ItemMattock(Item.ToolMaterial.IRON,"iron_mattock");
    public static ItemMattock goldMattock = new ItemMattock(Item.ToolMaterial.GOLD,"gold_mattock");
    public static ItemMattock diamondMattock = new ItemMattock(Item.ToolMaterial.DIAMOND,"diamond_mattock");

    public static ItemCrudeAxe crudeHatchet = new ItemCrudeAxe(NoTreePunching.toolMaterialFlint,"crude_axe");
    public static ItemCrudePick crudePick = new ItemCrudePick(NoTreePunching.toolMaterialFlint,"crude_pick");

    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                rock,
                grassFiber,
                poorIron,
                fireStarter,
                stoneKnife,
                ironKnife,
                goldKnife,
                ironMattock,
                goldMattock,
                diamondMattock,
                diamondKnife,
                crudeHatchet,
                crudePick
        );
    }

    public static void registerItemModels(){

        NoTreePunching.proxy.registerItemModel(rock,0,rock.name);
        NoTreePunching.proxy.registerItemModel(grassFiber,0,grassFiber.name);
        NoTreePunching.proxy.registerItemModel(poorIron,0,poorIron.name);
        NoTreePunching.proxy.registerItemModel(fireStarter,0,fireStarter.name);

        NoTreePunching.proxy.registerItemModel(stoneKnife,0,stoneKnife.name);
        NoTreePunching.proxy.registerItemModel(ironKnife,0,ironKnife.name);
        NoTreePunching.proxy.registerItemModel(goldKnife,0,goldKnife.name);
        NoTreePunching.proxy.registerItemModel(diamondKnife,0,diamondKnife.name);

        NoTreePunching.proxy.registerItemModel(ironMattock,0,ironMattock.name);
        NoTreePunching.proxy.registerItemModel(goldMattock,0,goldMattock.name);
        NoTreePunching.proxy.registerItemModel(diamondMattock,0,diamondMattock.name);

        NoTreePunching.proxy.registerItemModel(crudePick,0,crudePick.name);
        NoTreePunching.proxy.registerItemModel(crudeHatchet,0,crudeHatchet.name);
    }
}

