package io.sommers.configurablelever.blocks;

import com.teamacronymcoders.base.blocks.BlockBase;
import com.teamacronymcoders.base.blocks.IHasBlockColor;
import com.teamacronymcoders.base.blocks.IHasBlockStateMapper;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class BlockConfigurableLever extends BlockBase implements IHasBlockColor, IHasBlockStateMapper {
    public static final PropertyInteger POWER = PropertyInteger.create("power", 0, 15);

    public BlockConfigurableLever() {
        super(Material.IRON, "configurable_lever");
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            boolean increase = true;

            if (facing == EnumFacing.DOWN) {
                increase = false;
            } else if (facing.getAxis() != EnumFacing.Axis.Y) {
                increase = hitY > 0.5;
            }

            int powerLevel = state.getValue(POWER);
            if (increase) {
                if (player.isSneaking()) {
                    powerLevel = 15;
                } else {
                    powerLevel++;
                }
            } else {
                if (player.isSneaking()) {
                    powerLevel = 0;
                } else {
                    powerLevel--;
                }
            }
            if (powerLevel > 15) {
                powerLevel = 0;
            } else if (powerLevel < 0) {
                powerLevel = 15;
            }
            state = state.withProperty(POWER, powerLevel);
            world.setBlockState(pos, state, 3);
            world.notifyNeighborsOfStateChange(pos, this, false);
        }
        return true;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        if (state.getValue(POWER) > 0) {
            world.notifyNeighborsOfStateChange(pos, this, false);
        }

        super.breakBlock(world, pos, state);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canProvidePower(IBlockState state) {
        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return blockState.getValue(POWER);
    }

    @Override
    @SuppressWarnings("deprecation")
    public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return blockState.getValue(POWER);
    }

    @Override
    @Nonnull
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, POWER);
    }

    @Override
    public int getMetaFromState(IBlockState blockState) {
        return blockState.getValue(POWER);
    }

    @Override
    @Nonnull
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(POWER, meta);
    }

    @Override
    public int colorMultiplier(IBlockState state, @Nullable IBlockAccess world, @Nullable BlockPos pos, int tintIndex) {
        //Pulled from Redstone wire itself
        int power = state.getValue(POWER);
        float f = (float) power / 15.0F;
        float f1 = f * 0.6F + 0.4F;

        if (power == 0) {
            f1 = 0.3F;
        }

        float f2 = f * f * 0.7F - 0.5F;
        float f3 = f * f * 0.6F - 0.7F;

        if (f2 < 0.0F) {
            f2 = 0.0F;
        }

        if (f3 < 0.0F) {
            f3 = 0.0F;
        }

        int i = MathHelper.clamp((int) (f1 * 255.0F), 0, 255);
        int j = MathHelper.clamp((int) (f2 * 255.0F), 0, 255);
        int k = MathHelper.clamp((int) (f3 * 255.0F), 0, 255);
        return -16777216 | i << 16 | j << 8 | k;
    }
}
