package net.geforcemods.securitycraft;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.geforcemods.securitycraft.blockentities.*;
import net.geforcemods.securitycraft.blocks.*;
import net.geforcemods.securitycraft.items.*;
import net.geforcemods.securitycraft.modules.ModuleType;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class SCContent {

    // ==================== BLOCK ENTITY TYPES ====================

    public static BlockEntityType<KeypadBlockEntity> KEYPAD_BLOCK_ENTITY;
    public static BlockEntityType<SecurityCameraBlockEntity> SECURITY_CAMERA_BLOCK_ENTITY;
    public static BlockEntityType<SentryBlockEntity> SENTRY_BLOCK_ENTITY;
    public static BlockEntityType<InventoryScannerBlockEntity> INVENTORY_SCANNER_BLOCK_ENTITY;
    public static BlockEntityType<KeycardReaderBlockEntity> KEYCARD_READER_BLOCK_ENTITY;
    public static BlockEntityType<MineBlockEntity> MINE_BLOCK_ENTITY;
    public static BlockEntityType<OwnableBlockEntity> FAKE_LIQUID_BLOCK_ENTITY;
    public static BlockEntityType<BlockChangeDetectorBlockEntity> BLOCK_CHANGE_DETECTOR_BLOCK_ENTITY;
    public static BlockEntityType<RiftStabilizerBlockEntity> RIFT_STABILIZER_BLOCK_ENTITY;
    public static BlockEntityType<TrophySystemBlockEntity> TROPHY_SYSTEM_BLOCK_ENTITY;

    // ==================== BLOCKS ====================

    // Security Devices
    public static KeypadBlock KEYPAD;
    public static SecurityCameraBlock SECURITY_CAMERA;
    public static SentryBlock SENTRY;
    public static InventoryScannerBlock INVENTORY_SCANNER;
    public static KeycardReaderBlock KEYCARD_READER;
    public static BlockChangeDetectorBlock BLOCK_CHANGE_DETECTOR;
    public static RiftStabilizerBlock RIFT_STABILIZER;
    public static TrophySystemBlock TROPHY_SYSTEM;

    // Mines
    public static LaserBlock LASER_MINE;
    public static MineBlock STONE_MINE;
    public static MineBlock COBBLESTONE_MINE;
    public static MineBlock DEEPSLATE_MINE;
    public static MineBlock DIRT_MINE;
    public static MineBlock SAND_MINE;
    public static MineBlock GRAVEL_MINE;
    public static MineBlock COAL_ORE_MINE;
    public static MineBlock IRON_ORE_MINE;
    public static MineBlock GOLD_ORE_MINE;
    public static MineBlock DIAMOND_ORE_MINE;
    public static MineBlock EMERALD_ORE_MINE;
    public static MineBlock LAPIS_ORE_MINE;
    public static MineBlock REDSTONE_ORE_MINE;
    public static MineBlock GRASS_MINE;

    // Fake liquids
    public static FakeLiquidBlock FAKE_WATER;
    public static FakeLiquidBlock FAKE_LAVA;

    // Reinforced Blocks
    public static ReinforcedBlock REINFORCED_STONE;
    public static ReinforcedBlock REINFORCED_COBBLESTONE;
    public static ReinforcedBlock REINFORCED_STONE_BRICKS;
    public static ReinforcedBlock REINFORCED_OAK_PLANKS;
    public static ReinforcedBlock REINFORCED_SPRUCE_PLANKS;
    public static ReinforcedBlock REINFORCED_BIRCH_PLANKS;
    public static ReinforcedBlock REINFORCED_JUNGLE_PLANKS;
    public static ReinforcedBlock REINFORCED_ACACIA_PLANKS;
    public static ReinforcedBlock REINFORCED_DARK_OAK_PLANKS;
    public static ReinforcedBlock REINFORCED_GLASS;
    public static ReinforcedBlock REINFORCED_IRON_BARS;
    public static ReinforcedBlock REINFORCED_OBSIDIAN;
    public static ReinforcedBlock REINFORCED_DIRT;
    public static ReinforcedBlock REINFORCED_GRAVEL;
    public static ReinforcedBlock REINFORCED_SAND;
    public static ReinforcedBlock REINFORCED_BRICKS;
    public static ReinforcedBlock REINFORCED_NETHER_BRICKS;
    public static ReinforcedBlock REINFORCED_IRON_BLOCK;
    public static ReinforcedBlock REINFORCED_DEEPSLATE;
    public static ReinforcedBlock REINFORCED_POLISHED_DEEPSLATE;
    public static ReinforcedBlock REINFORCED_DEEPSLATE_BRICKS;
    public static ReinforcedBlock REINFORCED_DEEPSLATE_TILES;
    public static ReinforcedBlock REINFORCED_OAK_LOG;
    public static ReinforcedBlock REINFORCED_SPRUCE_LOG;
    public static ReinforcedBlock REINFORCED_BOOKSHELF;
    public static ReinforcedBlock REINFORCED_WOOL;
    public static ReinforcedBlock REINFORCED_WHITE_STAINED_GLASS;
    public static ReinforcedBlock REINFORCED_SANDSTONE;
    public static ReinforcedBlock REINFORCED_SMOOTH_STONE;

    // ==================== ITEMS ====================

    // Keycards
    public static KeycardItem KEYCARD_1;
    public static KeycardItem KEYCARD_2;
    public static KeycardItem KEYCARD_3;
    public static KeycardItem KEYCARD_4;
    public static KeycardItem KEYCARD_5;

    // Keys
    public static KeyItem KEY;

    // Tools
    public static UniversalBlockModifier UNIVERSAL_BLOCK_MODIFIER;
    public static UniversalBlockReinforcer UNIVERSAL_BLOCK_REINFORCER_1;
    public static UniversalBlockReinforcer UNIVERSAL_BLOCK_REINFORCER_2;
    public static UniversalBlockReinforcer UNIVERSAL_BLOCK_REINFORCER_3;
    public static UniversalKeyRemover UNIVERSAL_KEY_REMOVER;
    public static CodebreakerItem CODEBREAKER;
    public static MineRemoteAccessTool MINE_REMOTE_ACCESS_TOOL;
    public static PortableRadar PORTABLE_RADAR;

    // Modules
    public static ModuleItem ALLOWLIST_MODULE;
    public static ModuleItem DENYLIST_MODULE;
    public static ModuleItem REDSTONE_MODULE;
    public static ModuleItem SMART_MODULE;
    public static ModuleItem SPEED_MODULE;
    public static ModuleItem DISGUISE_MODULE;
    public static ModuleItem HARMING_MODULE;
    public static ModuleItem STORAGE_MODULE;
    public static ModuleItem COVERT_MODULE;
    public static ModuleItem BLANKING_MODULE;
    public static ModuleItem SCHEDULE_MODULE;
    public static ModuleItem CROWD_CONTROL_MODULE;

    // Block items (auto-registered below)

    // Item Group
    public static ItemGroup ITEM_GROUP;

    // ==================== REGISTRATION ====================

    public static void registerAll() {
        registerBlocks();
        registerItems();
        registerBlockEntityTypes();
        registerItemGroup();
    }

    private static void registerBlocks() {
        AbstractBlock.Settings stoneSettings = AbstractBlock.Settings.copy(Blocks.STONE);
        AbstractBlock.Settings ironSettings = AbstractBlock.Settings.copy(Blocks.IRON_BLOCK);

        // Security devices
        KEYPAD = register("keypad", new KeypadBlock(stoneSettings.luminance(s -> s.get(KeypadBlock.POWERED) ? 7 : 0)));
        SECURITY_CAMERA = register("security_camera", new SecurityCameraBlock(AbstractBlock.Settings.copy(Blocks.IRON_BARS)));
        SENTRY = register("sentry", new SentryBlock(ironSettings));
        INVENTORY_SCANNER = register("inventory_scanner", new InventoryScannerBlock(stoneSettings));
        KEYCARD_READER = register("keycard_reader", new KeycardReaderBlock(stoneSettings));
        BLOCK_CHANGE_DETECTOR = register("block_change_detector", new BlockChangeDetectorBlock(stoneSettings));
        RIFT_STABILIZER = register("rift_stabilizer", new RiftStabilizerBlock(ironSettings));
        TROPHY_SYSTEM = register("trophy_system", new TrophySystemBlock(ironSettings));

        // Mines
        LASER_MINE = register("laser_mine", new LaserBlock(AbstractBlock.Settings.copy(Blocks.GLASS).nonOpaque(), 4.0f));
        STONE_MINE = register("stone_mine", new MineBlock(stoneSettings, Blocks.STONE, 4.0f));
        COBBLESTONE_MINE = register("cobblestone_mine", new MineBlock(AbstractBlock.Settings.copy(Blocks.COBBLESTONE), Blocks.COBBLESTONE, 4.0f));
        DEEPSLATE_MINE = register("deepslate_mine", new MineBlock(AbstractBlock.Settings.copy(Blocks.DEEPSLATE), Blocks.DEEPSLATE, 4.0f));
        DIRT_MINE = register("dirt_mine", new MineBlock(AbstractBlock.Settings.copy(Blocks.DIRT), Blocks.DIRT, 2.0f));
        SAND_MINE = register("sand_mine", new MineBlock(AbstractBlock.Settings.copy(Blocks.SAND), Blocks.SAND, 3.0f));
        GRAVEL_MINE = register("gravel_mine", new MineBlock(AbstractBlock.Settings.copy(Blocks.GRAVEL), Blocks.GRAVEL, 3.0f));
        COAL_ORE_MINE = register("coal_ore_mine", new MineBlock(AbstractBlock.Settings.copy(Blocks.COAL_ORE), Blocks.COAL_ORE, 5.0f));
        IRON_ORE_MINE = register("iron_ore_mine", new MineBlock(AbstractBlock.Settings.copy(Blocks.IRON_ORE), Blocks.IRON_ORE, 5.0f));
        GOLD_ORE_MINE = register("gold_ore_mine", new MineBlock(AbstractBlock.Settings.copy(Blocks.GOLD_ORE), Blocks.GOLD_ORE, 5.0f));
        DIAMOND_ORE_MINE = register("diamond_ore_mine", new MineBlock(AbstractBlock.Settings.copy(Blocks.DIAMOND_ORE), Blocks.DIAMOND_ORE, 6.0f));
        EMERALD_ORE_MINE = register("emerald_ore_mine", new MineBlock(AbstractBlock.Settings.copy(Blocks.EMERALD_ORE), Blocks.EMERALD_ORE, 6.0f));
        LAPIS_ORE_MINE = register("lapis_ore_mine", new MineBlock(AbstractBlock.Settings.copy(Blocks.LAPIS_ORE), Blocks.LAPIS_ORE, 5.0f));
        REDSTONE_ORE_MINE = register("redstone_ore_mine", new MineBlock(AbstractBlock.Settings.copy(Blocks.REDSTONE_ORE), Blocks.REDSTONE_ORE, 5.0f));
        GRASS_MINE = register("grass_mine", new MineBlock(AbstractBlock.Settings.copy(Blocks.GRASS_BLOCK), Blocks.GRASS_BLOCK, 2.0f));

        // Fake liquids
        FAKE_WATER = register("fake_water", new FakeLiquidBlock(
            AbstractBlock.Settings.copy(Blocks.WATER).luminance(s -> 0).nonOpaque(), FakeLiquidBlock.LiquidType.WATER));
        FAKE_LAVA = register("fake_lava", new FakeLiquidBlock(
            AbstractBlock.Settings.copy(Blocks.LAVA).luminance(s -> 15).nonOpaque(), FakeLiquidBlock.LiquidType.LAVA));

        // Reinforced blocks
        REINFORCED_STONE = registerReinforced("reinforced_stone", Blocks.STONE);
        REINFORCED_COBBLESTONE = registerReinforced("reinforced_cobblestone", Blocks.COBBLESTONE);
        REINFORCED_STONE_BRICKS = registerReinforced("reinforced_stone_bricks", Blocks.STONE_BRICKS);
        REINFORCED_OAK_PLANKS = registerReinforced("reinforced_oak_planks", Blocks.OAK_PLANKS);
        REINFORCED_SPRUCE_PLANKS = registerReinforced("reinforced_spruce_planks", Blocks.SPRUCE_PLANKS);
        REINFORCED_BIRCH_PLANKS = registerReinforced("reinforced_birch_planks", Blocks.BIRCH_PLANKS);
        REINFORCED_JUNGLE_PLANKS = registerReinforced("reinforced_jungle_planks", Blocks.JUNGLE_PLANKS);
        REINFORCED_ACACIA_PLANKS = registerReinforced("reinforced_acacia_planks", Blocks.ACACIA_PLANKS);
        REINFORCED_DARK_OAK_PLANKS = registerReinforced("reinforced_dark_oak_planks", Blocks.DARK_OAK_PLANKS);
        REINFORCED_GLASS = registerReinforced("reinforced_glass", Blocks.GLASS);
        REINFORCED_IRON_BARS = registerReinforced("reinforced_iron_bars", Blocks.IRON_BARS);
        REINFORCED_OBSIDIAN = registerReinforced("reinforced_obsidian", Blocks.OBSIDIAN);
        REINFORCED_DIRT = registerReinforced("reinforced_dirt", Blocks.DIRT);
        REINFORCED_GRAVEL = registerReinforced("reinforced_gravel", Blocks.GRAVEL);
        REINFORCED_SAND = registerReinforced("reinforced_sand", Blocks.SAND);
        REINFORCED_BRICKS = registerReinforced("reinforced_bricks", Blocks.BRICKS);
        REINFORCED_NETHER_BRICKS = registerReinforced("reinforced_nether_bricks", Blocks.NETHER_BRICKS);
        REINFORCED_IRON_BLOCK = registerReinforced("reinforced_iron_block", Blocks.IRON_BLOCK);
        REINFORCED_DEEPSLATE = registerReinforced("reinforced_deepslate", Blocks.DEEPSLATE);
        REINFORCED_POLISHED_DEEPSLATE = registerReinforced("reinforced_polished_deepslate", Blocks.POLISHED_DEEPSLATE);
        REINFORCED_DEEPSLATE_BRICKS = registerReinforced("reinforced_deepslate_bricks", Blocks.DEEPSLATE_BRICKS);
        REINFORCED_DEEPSLATE_TILES = registerReinforced("reinforced_deepslate_tiles", Blocks.DEEPSLATE_TILES);
        REINFORCED_OAK_LOG = registerReinforced("reinforced_oak_log", Blocks.OAK_LOG);
        REINFORCED_SPRUCE_LOG = registerReinforced("reinforced_spruce_log", Blocks.SPRUCE_LOG);
        REINFORCED_BOOKSHELF = registerReinforced("reinforced_bookshelf", Blocks.BOOKSHELF);
        REINFORCED_WOOL = registerReinforced("reinforced_white_wool", Blocks.WHITE_WOOL);
        REINFORCED_WHITE_STAINED_GLASS = registerReinforced("reinforced_white_stained_glass", Blocks.WHITE_STAINED_GLASS);
        REINFORCED_SANDSTONE = registerReinforced("reinforced_sandstone", Blocks.SANDSTONE);
        REINFORCED_SMOOTH_STONE = registerReinforced("reinforced_smooth_stone", Blocks.SMOOTH_STONE);
    }

    private static void registerItems() {
        Item.Settings settings = new Item.Settings();

        // Register block items
        registerBlockItem("keypad", KEYPAD);
        registerBlockItem("security_camera", SECURITY_CAMERA);
        registerBlockItem("sentry", SENTRY);
        registerBlockItem("inventory_scanner", INVENTORY_SCANNER);
        registerBlockItem("keycard_reader", KEYCARD_READER);
        registerBlockItem("block_change_detector", BLOCK_CHANGE_DETECTOR);
        registerBlockItem("rift_stabilizer", RIFT_STABILIZER);
        registerBlockItem("trophy_system", TROPHY_SYSTEM);
        registerBlockItem("laser_mine", LASER_MINE);
        registerBlockItem("stone_mine", STONE_MINE);
        registerBlockItem("cobblestone_mine", COBBLESTONE_MINE);
        registerBlockItem("deepslate_mine", DEEPSLATE_MINE);
        registerBlockItem("dirt_mine", DIRT_MINE);
        registerBlockItem("sand_mine", SAND_MINE);
        registerBlockItem("gravel_mine", GRAVEL_MINE);
        registerBlockItem("coal_ore_mine", COAL_ORE_MINE);
        registerBlockItem("iron_ore_mine", IRON_ORE_MINE);
        registerBlockItem("gold_ore_mine", GOLD_ORE_MINE);
        registerBlockItem("diamond_ore_mine", DIAMOND_ORE_MINE);
        registerBlockItem("emerald_ore_mine", EMERALD_ORE_MINE);
        registerBlockItem("lapis_ore_mine", LAPIS_ORE_MINE);
        registerBlockItem("redstone_ore_mine", REDSTONE_ORE_MINE);
        registerBlockItem("grass_mine", GRASS_MINE);
        registerBlockItem("fake_water", FAKE_WATER);
        registerBlockItem("fake_lava", FAKE_LAVA);

        // Reinforced block items
        registerBlockItem("reinforced_stone", REINFORCED_STONE);
        registerBlockItem("reinforced_cobblestone", REINFORCED_COBBLESTONE);
        registerBlockItem("reinforced_stone_bricks", REINFORCED_STONE_BRICKS);
        registerBlockItem("reinforced_oak_planks", REINFORCED_OAK_PLANKS);
        registerBlockItem("reinforced_spruce_planks", REINFORCED_SPRUCE_PLANKS);
        registerBlockItem("reinforced_birch_planks", REINFORCED_BIRCH_PLANKS);
        registerBlockItem("reinforced_jungle_planks", REINFORCED_JUNGLE_PLANKS);
        registerBlockItem("reinforced_acacia_planks", REINFORCED_ACACIA_PLANKS);
        registerBlockItem("reinforced_dark_oak_planks", REINFORCED_DARK_OAK_PLANKS);
        registerBlockItem("reinforced_glass", REINFORCED_GLASS);
        registerBlockItem("reinforced_iron_bars", REINFORCED_IRON_BARS);
        registerBlockItem("reinforced_obsidian", REINFORCED_OBSIDIAN);
        registerBlockItem("reinforced_dirt", REINFORCED_DIRT);
        registerBlockItem("reinforced_gravel", REINFORCED_GRAVEL);
        registerBlockItem("reinforced_sand", REINFORCED_SAND);
        registerBlockItem("reinforced_bricks", REINFORCED_BRICKS);
        registerBlockItem("reinforced_nether_bricks", REINFORCED_NETHER_BRICKS);
        registerBlockItem("reinforced_iron_block", REINFORCED_IRON_BLOCK);
        registerBlockItem("reinforced_deepslate", REINFORCED_DEEPSLATE);
        registerBlockItem("reinforced_polished_deepslate", REINFORCED_POLISHED_DEEPSLATE);
        registerBlockItem("reinforced_deepslate_bricks", REINFORCED_DEEPSLATE_BRICKS);
        registerBlockItem("reinforced_deepslate_tiles", REINFORCED_DEEPSLATE_TILES);
        registerBlockItem("reinforced_oak_log", REINFORCED_OAK_LOG);
        registerBlockItem("reinforced_spruce_log", REINFORCED_SPRUCE_LOG);
        registerBlockItem("reinforced_bookshelf", REINFORCED_BOOKSHELF);
        registerBlockItem("reinforced_white_wool", REINFORCED_WOOL);
        registerBlockItem("reinforced_white_stained_glass", REINFORCED_WHITE_STAINED_GLASS);
        registerBlockItem("reinforced_sandstone", REINFORCED_SANDSTONE);
        registerBlockItem("reinforced_smooth_stone", REINFORCED_SMOOTH_STONE);

        // Keycards
        KEYCARD_1 = registerItem("keycard_1", new KeycardItem(settings, 1));
        KEYCARD_2 = registerItem("keycard_2", new KeycardItem(settings, 2));
        KEYCARD_3 = registerItem("keycard_3", new KeycardItem(settings, 3));
        KEYCARD_4 = registerItem("keycard_4", new KeycardItem(settings, 4));
        KEYCARD_5 = registerItem("keycard_5", new KeycardItem(settings, 5));

        // Keys
        KEY = registerItem("key", new KeyItem(settings));

        // Tools
        UNIVERSAL_BLOCK_MODIFIER = registerItem("universal_block_modifier", new UniversalBlockModifier(new Item.Settings().maxCount(1)));
        UNIVERSAL_BLOCK_REINFORCER_1 = registerItem("universal_block_reinforcer_1", new UniversalBlockReinforcer(new Item.Settings(), 1));
        UNIVERSAL_BLOCK_REINFORCER_2 = registerItem("universal_block_reinforcer_2", new UniversalBlockReinforcer(new Item.Settings(), 2));
        UNIVERSAL_BLOCK_REINFORCER_3 = registerItem("universal_block_reinforcer_3", new UniversalBlockReinforcer(new Item.Settings(), 3));
        UNIVERSAL_KEY_REMOVER = registerItem("universal_key_remover", new UniversalKeyRemover(new Item.Settings()));
        CODEBREAKER = registerItem("codebreaker", new CodebreakerItem(new Item.Settings()));
        MINE_REMOTE_ACCESS_TOOL = registerItem("mine_remote_access_tool", new MineRemoteAccessTool(new Item.Settings()));
        PORTABLE_RADAR = registerItem("portable_radar", new PortableRadar(new Item.Settings()));

        // Modules
        ALLOWLIST_MODULE = registerItem("allowlist_module", new ModuleItem(settings, ModuleType.ALLOWLIST));
        DENYLIST_MODULE = registerItem("denylist_module", new ModuleItem(settings, ModuleType.DENYLIST));
        REDSTONE_MODULE = registerItem("redstone_module", new ModuleItem(settings, ModuleType.REDSTONE));
        SMART_MODULE = registerItem("smart_module", new ModuleItem(settings, ModuleType.SMART));
        SPEED_MODULE = registerItem("speed_module", new ModuleItem(settings, ModuleType.SPEED));
        DISGUISE_MODULE = registerItem("disguise_module", new ModuleItem(settings, ModuleType.DISGUISE));
        HARMING_MODULE = registerItem("harming_module", new ModuleItem(settings, ModuleType.HARMING));
        STORAGE_MODULE = registerItem("storage_module", new ModuleItem(settings, ModuleType.STORAGE));
        COVERT_MODULE = registerItem("covert_module", new ModuleItem(settings, ModuleType.COVERT));
        BLANKING_MODULE = registerItem("blanking_module", new ModuleItem(settings, ModuleType.BLANKING));
        SCHEDULE_MODULE = registerItem("schedule_module", new ModuleItem(settings, ModuleType.SCHEDULE));
        CROWD_CONTROL_MODULE = registerItem("crowd_control_module", new ModuleItem(settings, ModuleType.CROWD_CONTROL));
    }

    private static void registerBlockEntityTypes() {
        KEYPAD_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, id("keypad"),
            FabricBlockEntityTypeBuilder.create(KeypadBlockEntity::new, KEYPAD).build());

        SECURITY_CAMERA_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, id("security_camera"),
            FabricBlockEntityTypeBuilder.create(SecurityCameraBlockEntity::new, SECURITY_CAMERA).build());

        SENTRY_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, id("sentry"),
            FabricBlockEntityTypeBuilder.create(SentryBlockEntity::new, SENTRY).build());

        INVENTORY_SCANNER_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, id("inventory_scanner"),
            FabricBlockEntityTypeBuilder.create(InventoryScannerBlockEntity::new, INVENTORY_SCANNER).build());

        KEYCARD_READER_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, id("keycard_reader"),
            FabricBlockEntityTypeBuilder.create(KeycardReaderBlockEntity::new, KEYCARD_READER).build());

        MINE_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, id("mine"),
            FabricBlockEntityTypeBuilder.create(MineBlockEntity::new,
                LASER_MINE, STONE_MINE, COBBLESTONE_MINE, DEEPSLATE_MINE, DIRT_MINE,
                SAND_MINE, GRAVEL_MINE, COAL_ORE_MINE, IRON_ORE_MINE, GOLD_ORE_MINE,
                DIAMOND_ORE_MINE, EMERALD_ORE_MINE, LAPIS_ORE_MINE, REDSTONE_ORE_MINE, GRASS_MINE
            ).build());

        FAKE_LIQUID_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, id("fake_liquid"),
            FabricBlockEntityTypeBuilder.create((pos, state) -> new OwnableBlockEntity(FAKE_LIQUID_BLOCK_ENTITY, pos, state) {}, FAKE_WATER, FAKE_LAVA).build());

        BLOCK_CHANGE_DETECTOR_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, id("block_change_detector"),
            FabricBlockEntityTypeBuilder.create(BlockChangeDetectorBlockEntity::new, BLOCK_CHANGE_DETECTOR).build());

        RIFT_STABILIZER_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, id("rift_stabilizer"),
            FabricBlockEntityTypeBuilder.create(RiftStabilizerBlockEntity::new, RIFT_STABILIZER).build());

        TROPHY_SYSTEM_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, id("trophy_system"),
            FabricBlockEntityTypeBuilder.create(TrophySystemBlockEntity::new, TROPHY_SYSTEM).build());
    }

    private static void registerItemGroup() {
        ITEM_GROUP = Registry.register(Registries.ITEM_GROUP, id("general"),
            FabricItemGroup.builder()
                .displayName(Text.translatable("itemGroup.securitycraft.general"))
                .icon(() -> new ItemStack(KEYPAD))
                .entries((context, entries) -> {
                    // Security devices
                    entries.add(KEYPAD);
                    entries.add(SECURITY_CAMERA);
                    entries.add(SENTRY);
                    entries.add(INVENTORY_SCANNER);
                    entries.add(KEYCARD_READER);
                    entries.add(BLOCK_CHANGE_DETECTOR);
                    entries.add(RIFT_STABILIZER);
                    entries.add(TROPHY_SYSTEM);
                    // Mines
                    entries.add(LASER_MINE);
                    entries.add(STONE_MINE);
                    entries.add(COBBLESTONE_MINE);
                    entries.add(DEEPSLATE_MINE);
                    entries.add(DIRT_MINE);
                    entries.add(SAND_MINE);
                    entries.add(GRAVEL_MINE);
                    entries.add(COAL_ORE_MINE);
                    entries.add(IRON_ORE_MINE);
                    entries.add(GOLD_ORE_MINE);
                    entries.add(DIAMOND_ORE_MINE);
                    entries.add(EMERALD_ORE_MINE);
                    entries.add(LAPIS_ORE_MINE);
                    entries.add(REDSTONE_ORE_MINE);
                    entries.add(GRASS_MINE);
                    // Fake liquids
                    entries.add(FAKE_WATER);
                    entries.add(FAKE_LAVA);
                    // Reinforced blocks
                    entries.add(REINFORCED_STONE);
                    entries.add(REINFORCED_COBBLESTONE);
                    entries.add(REINFORCED_STONE_BRICKS);
                    entries.add(REINFORCED_OAK_PLANKS);
                    entries.add(REINFORCED_SPRUCE_PLANKS);
                    entries.add(REINFORCED_BIRCH_PLANKS);
                    entries.add(REINFORCED_JUNGLE_PLANKS);
                    entries.add(REINFORCED_ACACIA_PLANKS);
                    entries.add(REINFORCED_DARK_OAK_PLANKS);
                    entries.add(REINFORCED_GLASS);
                    entries.add(REINFORCED_IRON_BARS);
                    entries.add(REINFORCED_OBSIDIAN);
                    entries.add(REINFORCED_DIRT);
                    entries.add(REINFORCED_GRAVEL);
                    entries.add(REINFORCED_SAND);
                    entries.add(REINFORCED_BRICKS);
                    entries.add(REINFORCED_NETHER_BRICKS);
                    entries.add(REINFORCED_IRON_BLOCK);
                    entries.add(REINFORCED_DEEPSLATE);
                    entries.add(REINFORCED_POLISHED_DEEPSLATE);
                    entries.add(REINFORCED_DEEPSLATE_BRICKS);
                    entries.add(REINFORCED_DEEPSLATE_TILES);
                    entries.add(REINFORCED_OAK_LOG);
                    entries.add(REINFORCED_SPRUCE_LOG);
                    entries.add(REINFORCED_BOOKSHELF);
                    entries.add(REINFORCED_WOOL);
                    entries.add(REINFORCED_WHITE_STAINED_GLASS);
                    entries.add(REINFORCED_SANDSTONE);
                    entries.add(REINFORCED_SMOOTH_STONE);
                    // Keycards
                    entries.add(KEYCARD_1);
                    entries.add(KEYCARD_2);
                    entries.add(KEYCARD_3);
                    entries.add(KEYCARD_4);
                    entries.add(KEYCARD_5);
                    entries.add(KEY);
                    // Tools
                    entries.add(UNIVERSAL_BLOCK_MODIFIER);
                    entries.add(UNIVERSAL_BLOCK_REINFORCER_1);
                    entries.add(UNIVERSAL_BLOCK_REINFORCER_2);
                    entries.add(UNIVERSAL_BLOCK_REINFORCER_3);
                    entries.add(UNIVERSAL_KEY_REMOVER);
                    entries.add(CODEBREAKER);
                    entries.add(MINE_REMOTE_ACCESS_TOOL);
                    entries.add(PORTABLE_RADAR);
                    // Modules
                    entries.add(ALLOWLIST_MODULE);
                    entries.add(DENYLIST_MODULE);
                    entries.add(REDSTONE_MODULE);
                    entries.add(SMART_MODULE);
                    entries.add(SPEED_MODULE);
                    entries.add(DISGUISE_MODULE);
                    entries.add(HARMING_MODULE);
                    entries.add(STORAGE_MODULE);
                    entries.add(COVERT_MODULE);
                    entries.add(BLANKING_MODULE);
                    entries.add(SCHEDULE_MODULE);
                    entries.add(CROWD_CONTROL_MODULE);
                })
                .build());
    }

    // ==================== HELPERS ====================

    private static <T extends Block> T register(String name, T block) {
        return Registry.register(Registries.BLOCK, id(name), block);
    }

    private static ReinforcedBlock registerReinforced(String name, Block copyFrom) {
        return register(name, new ReinforcedBlock(AbstractBlock.Settings.copy(copyFrom)));
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, id(name), new BlockItem(block, new Item.Settings()));
    }

    private static <T extends Item> T registerItem(String name, T item) {
        return Registry.register(Registries.ITEM, id(name), item);
    }

    public static Identifier id(String path) {
        return Identifier.of(SecurityCraft.MOD_ID, path);
    }

    // ==================== SCREEN OPENERS (stub for now) ====================
    public static void openKeypadScreen(ServerPlayerEntity player, BlockPos pos) {
        player.sendMessage(Text.translatable("gui.securitycraft.keypad.open"), false);
    }

    public static void openCameraView(ServerPlayerEntity player, BlockPos pos) {
        player.sendMessage(Text.translatable("gui.securitycraft.camera.open"), false);
    }

    public static void openInventoryScannerScreen(ServerPlayerEntity player, BlockPos pos) {
        player.sendMessage(Text.translatable("gui.securitycraft.inventory_scanner.open"), false);
    }

    public static void openBlockChangeDetectorScreen(ServerPlayerEntity player, BlockPos pos) {
        player.sendMessage(Text.translatable("gui.securitycraft.bcd.open"), false);
    }
}
