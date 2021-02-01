package info.iconmaster.minethecrafting.blocks;

import javax.annotation.Nullable;

import info.iconmaster.minethecrafting.tes.TileEntitySpellcraftersDesk;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

public class BlockSpellcraftersDesk extends Block {

  public BlockSpellcraftersDesk() {
    super(Block.Properties.create(Material.ANVIL).hardnessAndResistance(3).harvestTool(ToolType.AXE).setRequiresTool());
  }

  @Override
  public boolean hasTileEntity(BlockState state) {
    return true;
  }

  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new TileEntitySpellcraftersDesk();
  }

  @Override
  public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
      Hand handIn, BlockRayTraceResult hit) {
    if (!worldIn.isRemote) {
      TileEntitySpellcraftersDesk te = (TileEntitySpellcraftersDesk) this.getContainer(state, worldIn, pos);
      if (te != null) {
        NetworkHooks.openGui((ServerPlayerEntity) player, te, packet -> {

        });
      }
    }
    return ActionResultType.SUCCESS;
  }

  @Override
  @Nullable
  public INamedContainerProvider getContainer(BlockState state, World world, BlockPos pos) {
    TileEntity tileentity = world.getTileEntity(pos);
    return tileentity instanceof TileEntitySpellcraftersDesk ? (INamedContainerProvider) tileentity : null;
  }

  @Override
  public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
    if (state.getBlock() != newState.getBlock()) {
      TileEntity tileentity = worldIn.getTileEntity(pos);

      if (tileentity instanceof TileEntitySpellcraftersDesk) {
        InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntitySpellcraftersDesk) tileentity);
      }

      super.onReplaced(state, worldIn, pos, newState, isMoving);
    }
  }
}
