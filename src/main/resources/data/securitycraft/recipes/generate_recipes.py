#!/usr/bin/env python3
"""Generate all SecurityCraft recipes"""

import json
import os

output_dir = os.path.dirname(os.path.abspath(__file__))

recipes = {
    "key": {
        "type": "minecraft:crafting_shaped",
        "pattern": ["II ", "I  ", "   "],
        "key": {"I": {"item": "minecraft:iron_ingot"}},
        "result": {"id": "securitycraft:key", "count": 3}
    },
    "keycard_1": {
        "type": "minecraft:crafting_shaped",
        "pattern": ["IGI", "IRI", "III"],
        "key": {
            "I": {"item": "minecraft:iron_ingot"},
            "G": {"item": "minecraft:gold_ingot"},
            "R": {"item": "minecraft:redstone"}
        },
        "result": {"id": "securitycraft:keycard_1", "count": 1}
    },
    "keycard_2": {
        "type": "minecraft:crafting_shaped",
        "pattern": ["DGD", "IKI", "DID"],
        "key": {
            "D": {"item": "minecraft:diamond"},
            "G": {"item": "minecraft:gold_ingot"},
            "I": {"item": "minecraft:iron_ingot"},
            "K": {"item": "securitycraft:keycard_1"}
        },
        "result": {"id": "securitycraft:keycard_2", "count": 1}
    },
    "keycard_3": {
        "type": "minecraft:crafting_shaped",
        "pattern": ["DGD", "IKI", "DID"],
        "key": {
            "D": {"item": "minecraft:diamond"},
            "G": {"item": "minecraft:gold_ingot"},
            "I": {"item": "minecraft:iron_ingot"},
            "K": {"item": "securitycraft:keycard_2"}
        },
        "result": {"id": "securitycraft:keycard_3", "count": 1}
    },
    "keycard_4": {
        "type": "minecraft:crafting_shaped",
        "pattern": ["EGE", "IKI", "EIE"],
        "key": {
            "E": {"item": "minecraft:emerald"},
            "G": {"item": "minecraft:gold_ingot"},
            "I": {"item": "minecraft:iron_ingot"},
            "K": {"item": "securitycraft:keycard_3"}
        },
        "result": {"id": "securitycraft:keycard_4", "count": 1}
    },
    "keycard_5": {
        "type": "minecraft:crafting_shaped",
        "pattern": ["NEN", "IKI", "NIN"],
        "key": {
            "N": {"item": "minecraft:netherite_ingot"},
            "E": {"item": "minecraft:emerald"},
            "I": {"item": "minecraft:iron_ingot"},
            "K": {"item": "securitycraft:keycard_4"}
        },
        "result": {"id": "securitycraft:keycard_5", "count": 1}
    },
    "keycard_reader": {
        "type": "minecraft:crafting_shaped",
        "pattern": ["SIS", "SRS", "SIS"],
        "key": {
            "S": {"item": "minecraft:stone"},
            "I": {"item": "minecraft:iron_ingot"},
            "R": {"item": "minecraft:redstone"}
        },
        "result": {"id": "securitycraft:keycard_reader", "count": 1}
    },
    "inventory_scanner": {
        "type": "minecraft:crafting_shaped",
        "pattern": ["IRI", "RDR", "IRI"],
        "key": {
            "I": {"item": "minecraft:iron_ingot"},
            "R": {"item": "minecraft:redstone"},
            "D": {"item": "minecraft:detector_rail"}
        },
        "result": {"id": "securitycraft:inventory_scanner", "count": 1}
    },
    "block_change_detector": {
        "type": "minecraft:crafting_shaped",
        "pattern": ["SOS", "OEO", "SOS"],
        "key": {
            "S": {"item": "minecraft:stone"},
            "O": {"item": "minecraft:observer"},
            "E": {"item": "minecraft:ender_eye"}
        },
        "result": {"id": "securitycraft:block_change_detector", "count": 1}
    },
    "rift_stabilizer": {
        "type": "minecraft:crafting_shaped",
        "pattern": ["IEI", "EDE", "IEI"],
        "key": {
            "I": {"item": "minecraft:iron_ingot"},
            "E": {"item": "minecraft:ender_pearl"},
            "D": {"item": "minecraft:diamond"}
        },
        "result": {"id": "securitycraft:rift_stabilizer", "count": 1}
    },
    "trophy_system": {
        "type": "minecraft:crafting_shaped",
        "pattern": ["ILI", "IDI", "III"],
        "key": {
            "I": {"item": "minecraft:iron_ingot"},
            "L": {"item": "minecraft:lightning_rod"},
            "D": {"item": "minecraft:diamond"}
        },
        "result": {"id": "securitycraft:trophy_system", "count": 1}
    },
    "laser_mine": {
        "type": "minecraft:crafting_shaped",
        "pattern": ["GRG", "RTR", "GRG"],
        "key": {
            "G": {"item": "minecraft:glass"},
            "R": {"item": "minecraft:redstone"},
            "T": {"item": "minecraft:tnt"}
        },
        "result": {"id": "securitycraft:laser_mine", "count": 4}
    },
    "stone_mine": {
        "type": "minecraft:crafting_shaped",
        "pattern": ["SSS", "STS", "SSS"],
        "key": {
            "S": {"item": "minecraft:stone"},
            "T": {"item": "minecraft:tnt"}
        },
        "result": {"id": "securitycraft:stone_mine", "count": 4}
    },
    "cobblestone_mine": {
        "type": "minecraft:crafting_shaped",
        "pattern": ["CCC", "CTC", "CCC"],
        "key": {
            "C": {"item": "minecraft:cobblestone"},
            "T": {"item": "minecraft:tnt"}
        },
        "result": {"id": "securitycraft:cobblestone_mine", "count": 4}
    },
    "dirt_mine": {
        "type": "minecraft:crafting_shaped",
        "pattern": ["DDD", "DTD", "DDD"],
        "key": {
            "D": {"item": "minecraft:dirt"},
            "T": {"item": "minecraft:tnt"}
        },
        "result": {"id": "securitycraft:dirt_mine", "count": 4}
    },
    "sand_mine": {
        "type": "minecraft:crafting_shaped",
        "pattern": ["SSS", "STS", "SSS"],
        "key": {
            "S": {"item": "minecraft:sand"},
            "T": {"item": "minecraft:tnt"}
        },
        "result": {"id": "securitycraft:sand_mine", "count": 4}
    },
    "fake_water": {
        "type": "minecraft:crafting_shaped",
        "pattern": ["GGG", "GWG", "GGG"],
        "key": {
            "G": {"item": "minecraft:glass"},
            "W": {"item": "minecraft:water_bucket"}
        },
        "result": {"id": "securitycraft:fake_water", "count": 2}
    },
    "fake_lava": {
        "type": "minecraft:crafting_shaped",
        "pattern": ["GGG", "GLG", "GGG"],
        "key": {
            "G": {"item": "minecraft:glass"},
            "L": {"item": "minecraft:lava_bucket"}
        },
        "result": {"id": "securitycraft:fake_lava", "count": 2}
    },
    "universal_block_modifier": {
        "type": "minecraft:crafting_shaped",
        "pattern": ["IDI", "DGD", "IDI"],
        "key": {
            "I": {"item": "minecraft:iron_ingot"},
            "D": {"item": "minecraft:diamond"},
            "G": {"item": "minecraft:gold_ingot"}
        },
        "result": {"id": "securitycraft:universal_block_modifier", "count": 1}
    },
    "universal_block_reinforcer_1": {
        "type": "minecraft:crafting_shaped",
        "pattern": ["IDI", "DID", "IDI"],
        "key": {
            "I": {"item": "minecraft:iron_ingot"},
            "D": {"item": "minecraft:diamond"}
        },
        "result": {"id": "securitycraft:universal_block_reinforcer_1", "count": 1}
    },
    "universal_block_reinforcer_2": {
        "type": "minecraft:crafting_shaped",
        "pattern": ["NDN", "DUD", "NDN"],
        "key": {
            "N": {"item": "minecraft:netherite_ingot"},
            "D": {"item": "minecraft:diamond"},
            "U": {"item": "securitycraft:universal_block_reinforcer_1"}
        },
        "result": {"id": "securitycraft:universal_block_reinforcer_2", "count": 1}
    },
    "universal_key_remover": {
        "type": "minecraft:crafting_shaped",
        "pattern": [" I ", "IRI", " I "],
        "key": {
            "I": {"item": "minecraft:iron_ingot"},
            "R": {"item": "minecraft:redstone"}
        },
        "result": {"id": "securitycraft:universal_key_remover", "count": 1}
    },
    "codebreaker": {
        "type": "minecraft:crafting_shaped",
        "pattern": ["IDI", "DRD", "IDI"],
        "key": {
            "I": {"item": "minecraft:iron_ingot"},
            "D": {"item": "minecraft:diamond"},
            "R": {"item": "minecraft:redstone"}
        },
        "result": {"id": "securitycraft:codebreaker", "count": 1}
    },
    "mine_remote_access_tool": {
        "type": "minecraft:crafting_shaped",
        "pattern": ["IRI", "RAR", "IRI"],
        "key": {
            "I": {"item": "minecraft:iron_ingot"},
            "R": {"item": "minecraft:redstone"},
            "A": {"item": "minecraft:arrow"}
        },
        "result": {"id": "securitycraft:mine_remote_access_tool", "count": 1}
    },
    "portable_radar": {
        "type": "minecraft:crafting_shaped",
        "pattern": ["IDI", "DRD", "ILI"],
        "key": {
            "I": {"item": "minecraft:iron_ingot"},
            "D": {"item": "minecraft:diamond"},
            "R": {"item": "minecraft:redstone"},
            "L": {"item": "minecraft:lightning_rod"}
        },
        "result": {"id": "securitycraft:portable_radar", "count": 1}
    },
    "allowlist_module": {
        "type": "minecraft:crafting_shaped",
        "pattern": ["GPG", "PBP", "GPG"],
        "key": {
            "G": {"item": "minecraft:gold_ingot"},
            "P": {"item": "minecraft:paper"},
            "B": {"item": "minecraft:book"}
        },
        "result": {"id": "securitycraft:allowlist_module", "count": 1}
    },
    "denylist_module": {
        "type": "minecraft:crafting_shaped",
        "pattern": ["RPR", "PBP", "RPR"],
        "key": {
            "R": {"item": "minecraft:redstone"},
            "P": {"item": "minecraft:paper"},
            "B": {"item": "minecraft:book"}
        },
        "result": {"id": "securitycraft:denylist_module", "count": 1}
    },
    "redstone_module": {
        "type": "minecraft:crafting_shaped",
        "pattern": ["GRG", "RGR", "GRG"],
        "key": {
            "G": {"item": "minecraft:gold_ingot"},
            "R": {"item": "minecraft:redstone"}
        },
        "result": {"id": "securitycraft:redstone_module", "count": 1}
    },
    "smart_module": {
        "type": "minecraft:crafting_shaped",
        "pattern": ["GEG", "EQE", "GEG"],
        "key": {
            "G": {"item": "minecraft:gold_ingot"},
            "E": {"item": "minecraft:ender_pearl"},
            "Q": {"item": "minecraft:quartz"}
        },
        "result": {"id": "securitycraft:smart_module", "count": 1}
    },
    "speed_module": {
        "type": "minecraft:crafting_shaped",
        "pattern": ["GFG", "FSF", "GFG"],
        "key": {
            "G": {"item": "minecraft:gold_ingot"},
            "F": {"item": "minecraft:feather"},
            "S": {"item": "minecraft:sugar"}
        },
        "result": {"id": "securitycraft:speed_module", "count": 1}
    },
    "harming_module": {
        "type": "minecraft:crafting_shaped",
        "pattern": ["GBG", "BTB", "GBG"],
        "key": {
            "G": {"item": "minecraft:gold_ingot"},
            "B": {"item": "minecraft:blaze_powder"},
            "T": {"item": "minecraft:tnt"}
        },
        "result": {"id": "securitycraft:harming_module", "count": 1}
    },
    "storage_module": {
        "type": "minecraft:crafting_shaped",
        "pattern": ["GCG", "CHC", "GCG"],
        "key": {
            "G": {"item": "minecraft:gold_ingot"},
            "C": {"item": "minecraft:chest"},
            "H": {"item": "minecraft:hopper"}
        },
        "result": {"id": "securitycraft:storage_module", "count": 1}
    },
    "crowd_control_module": {
        "type": "minecraft:crafting_shaped",
        "pattern": ["GNG", "NSN", "GNG"],
        "key": {
            "G": {"item": "minecraft:gold_ingot"},
            "N": {"item": "minecraft:nether_star"},
            "S": {"item": "minecraft:spectral_arrow"}
        },
        "result": {"id": "securitycraft:crowd_control_module", "count": 1}
    }
}

for name, recipe in recipes.items():
    path = os.path.join(output_dir, f"{name}.json")
    with open(path, "w") as f:
        json.dump(recipe, f, indent=2)
    print(f"Generated {name}.json")

print("Done!")
