## Features

### Rules

**moreChunkLoaders**

This rule adds two `ChunkTicket` for hopper and piston. 

- **For hopper**: when a hopper checks its output inventory and it is facing an air block in a different chunk, a `hopper` ticket with 31 level lasting 1 tick will be added in the facing chunk.
- **For piston(normal and sticky)**: When a piston executes a block event and is facing a defferent chunk, a `piston` ticket with 32 level lasting 6 ticks will be added in the facing chunk.

**insertBlockToMinecart**

Right-click a minecart with hopper, chest, furnace, tnt to transfer it to the corresponding minecart.

**debugStickNoBlockUpdate**

Debug stick action will not send block update.

**itemPickUpRangeHorizontal**

Changes the horizontaledge length of item pick up range for player.

**itemPickUpRangeVertical**

Changes the vertical edge length of item pick up range for player. In vanilla, whether the player is riding a vehicle or not will affect the vertical range of item pick up, this rule will not consider and will override that.

### Commands

**commandUpdateBlock**

Enables `/updateBlock` command to update blocks in a box area. For both NC and PP updates, the source of the update is `Blocks.AIR`. For PP update, update will be send in every direction.

**updateBlockCommandLimit**

Sets the maximum amount of blocks can be updated by command `/updateBlock`.

**commandRemoveBlock**

Enables `/removeBlock` command to remove block. The removal and replacement will not send block/light update, the original block entity will be kept and no new block entity will be added.

**commandItemShadowShow**

(This command is still WIP) Shows the status of shadowed items by command `/itemshadow show`.

**commandItemShadowCreate**

An item shadow of the item in the main-hand will be created in the off-hand.

**commandChunkSaveState**

Manages chunks for save-stating by command `/chunksavestate`.

### Bugfix

**unstableOnGroundTagFix**

Fixes [MC-254307](https://bugs.mojang.com/browse/MC-254307), and the same issue with beds.
