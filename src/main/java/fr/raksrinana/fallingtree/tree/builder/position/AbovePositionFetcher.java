package fr.raksrinana.fallingtree.tree.builder.position;

import fr.raksrinana.fallingtree.tree.builder.ToAnalyzePos;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import java.util.Collection;
import java.util.Objects;
import static fr.raksrinana.fallingtree.utils.FallingTreeUtils.getTreePart;
import static java.util.stream.Collectors.toList;

public class AbovePositionFetcher implements IPositionFetcher{
	private static AbovePositionFetcher INSTANCE;
	
	private AbovePositionFetcher(){
	}
	
	@Override
	public Collection<ToAnalyzePos> getPositions(World world, ToAnalyzePos parent){
		BlockPos parentPos = parent.getCheckPos();
		Block parentBlock = world.getBlockState(parentPos).getBlock();
		return BlockPos.getAllInBox(parentPos.up().north().east(), parentPos.south().west())
				.filter(pos -> !Objects.equals(pos, parentPos))
				.map(checkPos -> {
					Block checkBlock = world.getBlockState(checkPos).getBlock();
					return new ToAnalyzePos(BasicPositionFetcher.getInstance(), parentPos, parentBlock, checkPos.toImmutable(), checkBlock, getTreePart(checkBlock), parent.getSequence() + 1);
				})
				.collect(toList());
	}
	
	public static AbovePositionFetcher getInstance(){
		if(Objects.isNull(INSTANCE)){
			INSTANCE = new AbovePositionFetcher();
		}
		return INSTANCE;
	}
}
